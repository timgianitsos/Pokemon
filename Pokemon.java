import java.util.Scanner;
import java.util.EnumSet;
import java.util.EnumMap;
import java.io.File;

//TODO check parameterized print, put stat calculation explanation in comments
//TODO limit number of pokemon used by master trainer
public class Pokemon {
    
    /*
     * This main method allows us to test Pokemon objects
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Pokemon p1;
        Pokemon p2;
        AePlayWave battleMusic = null;
        boolean pauseEachStep = true;
        if (args!= null && args.length >= 2) {
            pauseEachStep = args.length < 3 || !args[2].equalsIgnoreCase("skip");
            PLAY_SOUND = pauseEachStep;
            if (pauseEachStep) {
                battleMusic = intro(scan, AePlayWave.BATTLE_MUSIC_PRIME_CUP, AePlayWave.PRIME_CUP_BUFFER_SIZE);
            }
            p1 = new Pokemon(args[0].toUpperCase());
            p2 = new Pokemon(args[1].toUpperCase());
        }
        else {
            battleMusic = intro(scan, AePlayWave.BATTLE_MUSIC_PRIME_CUP, AePlayWave.PRIME_CUP_BUFFER_SIZE);
            displayPokemon();
            System.out.println("Choose player 1's Pokemon");
            p1 = askForPokemon(scan);
            System.out.println("\nChoose player 2's Pokemon");
            p2 = askForPokemon(scan);
        }

        System.out.println("\n" + p1.name + " with HP:" + p1.getCurrentHP() + "   vs   " 
                    + p2.name + " with HP:" + p2.getCurrentHP() + "\n");
        if (pauseEachStep) {
            scan.nextLine();
        }

        int turn = 1;
        while (p1.currentHP != 0 && p2.currentHP != 0) {
            System.out.println("Turn " + (turn++) + " ---------------------------");
            doTurn(p1, p2);
            if (pauseEachStep && p1.currentHP != 0 && p2.currentHP != 0) {
                scan.nextLine();
            }
        }
        if (battleMusic != null) {
            battleMusic.quit();
        }
    }

    protected static AePlayWave intro(Scanner scan, String musicFilename, int bufferSize) {
        AePlayWave battleMusic = null;
        double random = Math.random();
        System.out.println(random < 0.2 ? machampString: random < 0.4 ? scytherString: random < 0.6 ? alakazamString: 
            random < 0.8 ? rapidashString: charizardString);
        System.out.println("\nMusic? [y] - yes, [blank] - no");
        String response = scan.nextLine();
        if (response != null && response.length() > 0 && response.charAt(0) == 'y') {
            battleMusic = new AePlayWave(musicFilename, bufferSize);
            battleMusic.start();
        }
        return battleMusic;
    }

    /*
     * Pokemon instance variables - each Pokemon has its own copy. The public variables can be referenced using reference.variable
     */
    public final String name;
    public final Type type1;
    public final Type type2;
    private final EnumMap<Stat, Integer> statToValue = new EnumMap<Stat, Integer>(Stat.class);
    private final EnumMap<Attack, Integer> attackToPP = new EnumMap<Attack, Integer>(Attack.class);
    private int currentHP;

    /*
     * Pokemon class variables - there is only one global copy of these. They can be referenced by using Pokemon.variable
     */
    public static final PokemonEnum DEFAULT_POKEMON = PokemonEnum.MAGIKARP;
    public static final int LEVEL = 100;
    public static final double CRITICAL_HIT_PROBABILITY = 0.0625;
    public static final String STAT_MAXIMIZER_PREFIX = "_";
    public static int BASE_STAT_TOTAL_DISPLAY_THRESHHOLD = 580;
    public static int MOVE_QUANTITY_DISPLAY_THRESHOLD = 5;
    public static int BASE_SPEED_MAXIMIZER_THRESHHOLD = 80;
    public static boolean PLAY_SOUND = true;
    public static boolean DISPLAY_BATTLE_TEXT = true;

    /*
     * Create a Pokemon by using a predefined enumeration from PokemonEnum
     */
    public Pokemon(PokemonEnum poke) {
        this(poke.name(), poke.type1, poke.type2, poke.baseStats, poke.attacks);
    }

    /*
     * Create a Pokemon from a name. The name must match a PokemonEnum or else it will create a default 
     * (an underscore at the beginning is permitted as this is used to maximize stats)
     */
    public Pokemon(String name) {
        boolean maximizeStats = false;
        PokemonEnum p;
        try {
            if (name.startsWith(STAT_MAXIMIZER_PREFIX)) {
                maximizeStats = true;
                name = name.substring(1, name.length());
            }
            p = PokemonEnum.valueOf(name.toUpperCase());
        }
        catch (Exception e) {
            display("Invalid Pokemon name" + (name == null ? "": " \"" + name + "\"") + ". Generating default..\n");
            maximizeStats = false;
            p = DEFAULT_POKEMON;
        }

        this.name = p.name();
        this.type1 = p.type1;
        this.type2 = p.type2;
        int[] baseStats = p.baseStats;
        EnumSet<Attack> attacks = p.attacks;

        if (PLAY_SOUND && new File("cries/" + this.name + ".wav").exists()) {
            new AePlayWave("cries/" + this.name + ".wav", AePlayWave.DEFAULT_BUFFER_SIZE).start();
        }

        createInstanceMappings(maximizeStats, baseStats, attacks);
    }

    /*
     * Create a Pokemon by providing necessary parameters. This allows us to create custom Pokemon not listed in PokemonEnum
     */
    public Pokemon(String name, Type type1, Type type2, int[] baseStats, EnumSet<Attack> attacks) {
        boolean invalidArguments = false;
        if (name == null || name.length() == 0 || baseStats == null) {
            display("Initialization parameters must be non null and non empty. Generating default..\n");
            invalidArguments = true;
        }
        else if (type1 == null) {
            display("A Pokemon must have a valid primary type. Generating default..\n");
            invalidArguments = true;
        }
        else if (type1 == type2) {
            display("A Pokemon may not have two identical types " + type1.name() + ". Generating default..\n");
            invalidArguments = true;
        }
        else if (baseStats.length != Stat.numberOfStats()) {
            display("Attempted to construct Pokemon with " + baseStats.length 
                + " stats when " + Stat.numberOfStats() + " stats are required. Generating default..\n");
            invalidArguments = true;
        }
        if (invalidArguments) {
            name = DEFAULT_POKEMON.name();
            type1 = DEFAULT_POKEMON.type1;
            type2 = DEFAULT_POKEMON.type2;
            baseStats = DEFAULT_POKEMON.baseStats;
            attacks = DEFAULT_POKEMON.attacks;
        }

        boolean maximizeStats = false;
        if (name.startsWith(STAT_MAXIMIZER_PREFIX) && name.length() > 1) {
            maximizeStats = true;
            this.name = name.substring(1, name.length()).toUpperCase();
        }
        else {
            this.name = name.toUpperCase();
        }

        this.type1 = type1;
        this.type2 = type2;

        if (PLAY_SOUND && new File("cries/" + this.name + ".wav").exists()) {
            new AePlayWave("cries/" + this.name + ".wav", AePlayWave.DEFAULT_BUFFER_SIZE).start();
        }

        createInstanceMappings(maximizeStats, baseStats, attacks);
    }

    /*
     * Helper method for the constructors. It is private so it can be ignored from the outside
     */
    private void createInstanceMappings(boolean maximizeStats, int[] baseStats, EnumSet<Attack> attacks) {
        calculateStats(maximizeStats, baseStats);
        for (Attack a: attacks) {
            this.attackToPP.put(a, a.basePP);
        }
        this.currentHP = statToValue.get(Stat.HP);
    }

    /*
     * Helper method for the constructors. It is private so it can be ignored from the outside
     */
    private void calculateStats(boolean maximizeStats, int[] baseStats) {
        assert baseStats.length == Stat.numberOfStats(): "Attempted to create " + baseStats.length + " stats when " 
            + Stat.numberOfStats() + " are required";
        
        boolean maximizeAttack = baseStats[Stat.ATTACK.ordinal()] >= baseStats[Stat.SPECIAL_ATTACK.ordinal()];
        boolean maximizeSpeed = baseStats[Stat.SPEED.ordinal()] >= BASE_SPEED_MAXIMIZER_THRESHHOLD || baseStats[Stat.HP.ordinal()] == 1;
        
        this.statToValue.put(Stat.HP, baseStats[Stat.HP.ordinal()] == 1 ? 1: 
            (int)(((2.0 * baseStats[Stat.HP.ordinal()] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
            + (maximizeStats && maximizeSpeed ? 6.0: 0.0) / 4.0) * LEVEL / 100.0) + LEVEL + 10.0));
        this.statToValue.put(Stat.ATTACK, 
            (int)((((2.0 * baseStats[Stat.ATTACK.ordinal()] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
            + (maximizeStats && maximizeAttack ? 252.0: 0.0) / 4.0) * LEVEL / 100.0) + 5.0) 
            * (maximizeStats && maximizeAttack && !maximizeSpeed ? 1.1: maximizeStats && !maximizeAttack ? 0.9: 1.0)));
        this.statToValue.put(Stat.DEFENCE, 
            (int)((((2.0 * baseStats[Stat.DEFENCE.ordinal()] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
            + (0.0) / 4.0) * LEVEL / 100.0) + 5.0) * (1.0)));
        this.statToValue.put(Stat.SPECIAL_ATTACK, 
            (int)((((2.0 * baseStats[Stat.SPECIAL_ATTACK.ordinal()] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
            + (maximizeStats && !maximizeAttack ? 252.0: 0.0) / 4.0) * LEVEL / 100.0) + 5.0) 
            * (maximizeStats && !maximizeAttack && !maximizeSpeed ? 1.1: maximizeStats && maximizeAttack ? 0.9: 1.0)));
        this.statToValue.put(Stat.SPECIAL_DEFENCE, 
            (int)((((2.0 * baseStats[Stat.SPECIAL_DEFENCE.ordinal()] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
            + (0.0) / 4.0) * LEVEL / 100.0) + 5.0) * (1.0)));
        this.statToValue.put(Stat.SPEED, 
            (int)((((2.0 * baseStats[Stat.SPEED.ordinal()] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
            + (maximizeStats && maximizeSpeed ? 252.0: 0.0) / 4.0) * LEVEL / 100.0) + 5.0) 
            * (maximizeStats && maximizeSpeed ? 1.1: 1.0)));

        if (maximizeStats && !maximizeSpeed) {
            double hp = statToValue.get(Stat.HP);
            double def = statToValue.get(Stat.DEFENCE);
            double spdef = statToValue.get(Stat.SPECIAL_DEFENCE);
            for (int remainingEvs = 252; remainingEvs > 0; remainingEvs--) {
                if (hp <= def && hp <= spdef) {
                    hp += LEVEL / 400.0;
                }
                else if (def <= spdef) {
                    assert def < hp;
                    def += LEVEL / 400.0;
                }
                else {
                    assert spdef < hp && spdef < def;
                    spdef += LEVEL / 400.0;
                }
            }
            statToValue.put(Stat.HP, (int)hp);
            statToValue.put(Stat.DEFENCE, (int)def);
            statToValue.put(Stat.SPECIAL_DEFENCE, (int)spdef);
            //Putting some evs in speed prevents one of the defensive stats from becoming too large
            statToValue.put(Stat.SPEED, statToValue.get(Stat.SPEED) + (int)(6.0 * LEVEL / 400.0));
        }
    }

    /*
     * Private helper method to print Strings if configuration allows
     */
    private void display(String s) {
        if (DISPLAY_BATTLE_TEXT) {
            System.out.print(s);
        }
    }

    /*
     * Displays a list of non-hidden Pokemon
     */
    public static void displayPokemon() {
        System.out.println("\nAvailable Pokemon");
        for (int pokeEnumIndex = 0; pokeEnumIndex < PokemonEnum.numberOfPokemonEnums(); pokeEnumIndex++) {
            PokemonEnum poke = PokemonEnum.getPokemonEnumAtIndex(pokeEnumIndex);
            int baseStatTotal = 0;
            for (int i = 0; i < poke.baseStats.length; i++) {
                baseStatTotal += poke.baseStats[i];
            }
            if (baseStatTotal < BASE_STAT_TOTAL_DISPLAY_THRESHHOLD && !poke.name().contains("MEGA_") && poke.type1 != Type.NONE 
                    && poke.type2 != Type.NONE && poke.attacks.size() < MOVE_QUANTITY_DISPLAY_THRESHOLD) {
                System.out.printf("%-12s Type 1:%-12s Type 2:%-12s Attacks:", poke.name(), poke.type1.name(), 
                        (poke.type2 == null ? "": poke.type2.name()));
                for (Attack a: poke.attacks) {
                    System.out.print(a.name() + " ");
                }
                System.out.println();
                System.out.print("\t\t");
                for (int statIndex = 0; statIndex < Stat.numberOfStats(); statIndex++) {
                    Stat s = Stat.getStatAtIndex(statIndex);
                    System.out.print(String.format("%-19s ", s.name() + ":" + poke.getBaseStat(s)));
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /*
     * Asks the user for a Pokemon and returns it
     */
    public static Pokemon askForPokemon(Scanner scan) {
        try {
            String choice = scan.nextLine().trim().toUpperCase();
            if (choice.equals("CUSTOM")) {
                System.out.println("Enter name, first type, and second type (or leave second type blank if not applicable)" 
                        + " each separated by commas or spaces");
                String[] specs = scan.nextLine().split("[,\\s]+");
                if (specs[0].length() == 0) {
                    throw new Exception();
                }
                String name = specs[0].toUpperCase();
                Type type1 = Type.valueOf(specs[1].toUpperCase());
                Type type2 = specs.length == 3 ? Type.valueOf(specs[2].toUpperCase()): null;

                System.out.println("Enter the " + Stat.numberOfStats() + " base stats separated by commas or spaces");
                Scanner statScan = new Scanner(scan.nextLine());
                statScan.useDelimiter("[,\\s]+");
                int[] customBaseStats = new int[Stat.numberOfStats()];
                for (int i = 0; i < customBaseStats.length; i++) {
                    customBaseStats[i] = statScan.nextInt();
                }

                System.out.println("Enter attacks separated by commas or spaces");
                String[] stringAttacks = scan.nextLine().split("[,\\s]+");
                EnumSet<Attack> customAttacks = EnumSet.noneOf(Attack.class);
                for (int i = 0; i < stringAttacks.length; i++) {
                    customAttacks.add(Attack.valueOf(stringAttacks[i].toUpperCase()));
                }
                return new Pokemon(name, type1, type2, customBaseStats, customAttacks);   
            }
            else {
                return new Pokemon(choice);
            }
        }
        catch (Exception e) {
            System.out.println("Invalid argument. Generating default..");
            return new Pokemon(DEFAULT_POKEMON);
        }
    }

    /*
     * Performs a turn where each Pokemon attacks each other
     */
    public static void doTurn(Pokemon p1, Pokemon p2) {
        if (p1 == null || p2 == null || p1.currentHP == 0 || p2.currentHP == 0) {
            throw new IllegalStateException("Combatants must not be null and must have positive HP");
        }
        Pokemon first;
        Pokemon second;
        if (p1.getStat(Stat.SPEED) == p2.getStat(Stat.SPEED)) {
            double randomNumber = Math.random();
            first = randomNumber >= 0.5 ? p1: p2;
            second = randomNumber >= 0.5 ? p2: p1;
        }
        else {
            first = p1.getStat(Stat.SPEED) > p2.getStat(Stat.SPEED) ? p1: p2;
            second = p1.getStat(Stat.SPEED) > p2.getStat(Stat.SPEED) ? p2: p1;
        }
        assert first != second: "Error attempting turn";
        first.useAttack(first.getBestAttack(second), second);
        if (second.currentHP != 0) {
            second.useAttack(second.getBestAttack(first), first);
        }
    }

    /*
     * Determines which of this Pokemons attacks is best - example of AI
     * Each move recieves a score by multiplying damage with accuracy
     * returns STRUGGLE if no move is available
     */
    public Attack getBestAttack(Pokemon opponent) {
        if (opponent == null || opponent.currentHP == 0) {
            throw new IllegalStateException("Opponent must not be null and must have positive HP");
        }
        Attack attack = Attack.STRUGGLE;
        double bestAttackScore = -1;
        for (Attack a: this.attackToPP.keySet()) {
            double attackScore = this.attackDamage(a, opponent) * a.baseAccuracy;
            assert attackScore >= 0: "Attack score should never be negative";
            if (this.attackToPP.get(a) > 0 && attackScore > bestAttackScore) {
                attack = a;
                bestAttackScore = attackScore;
            }
        }
        return attack;
    }

    /*
     * Uses the attack on the opponent
     */
    public void useAttack(Attack attack, Pokemon opponent) {
        if (opponent == null || this.currentHP == 0 || opponent.currentHP == 0) {
            throw new IllegalStateException("Combatants must not be null and must have positive HP");
        }
        if (attack != Attack.STRUGGLE && this.attackToPP.get(attack) == null) {
            display("Invalid attack!\n");
            attack = Attack.STRUGGLE;
        }
        else if (attack == Attack.STRUGGLE || this.attackToPP.get(attack) <= 0) {
            display(this.name + " has run out of attacks!\n");
            attack = Attack.STRUGGLE;
        }

        display(this.name + " used " + attack.name() + "\n");
        if (Math.random() * 100 < attack.baseAccuracy) {
            double effectiveness = attack.type.getEffectiveness(this.type1, this.type2, opponent.type1, opponent.type2);
            if (effectiveness >= 2) {
                if (PLAY_SOUND) {new AePlayWave(AePlayWave.SUPER_EFFECTIVE, AePlayWave.DEFAULT_BUFFER_SIZE).start();}
                display(ANSI_CYAN +  "It's super effective!" + ANSI_RESET + "\n");
            }
            else if (effectiveness < 1 && effectiveness > 0) {
                if (PLAY_SOUND) {new AePlayWave(AePlayWave.NOT_EFFECTIVE, AePlayWave.DEFAULT_BUFFER_SIZE).start();}
                display(ANSI_RED +  "It's not very effective.." + ANSI_RESET + "\n");
            }
            else if (effectiveness == 0) {
                display(ANSI_PURPLE +  opponent.name + " is unaffected!" + ANSI_RESET + "\n");
            }
            else {
                if (PLAY_SOUND) {new AePlayWave(AePlayWave.NORMAL_EFFECTIVE, AePlayWave.DEFAULT_BUFFER_SIZE).start();}
                display(opponent.name + " was hit\n");
            }

            //Scale factor includes critical hits and random scaling between 85-100%
            double scaleFactor = effectiveness == 0 ? 0: 1;
            if (effectiveness != 0 && Math.random() < CRITICAL_HIT_PROBABILITY) {
                display(ANSI_YELLOW + "It's a critical hit!" + ANSI_RESET + "\n");
                scaleFactor *= 2;
            }
            scaleFactor *= (100 - (int)(Math.random() * 16)) / 100.0;

            int damageDealt = (int)(attackDamage(attack, opponent) * scaleFactor);
            opponent.currentHP -= damageDealt <= opponent.currentHP ? damageDealt: opponent.currentHP;
        }
        else {
            display("The attack missed!\n");
        }

        if (attack != Attack.STRUGGLE) {
            assert this.attackToPP.get(attack) > 0: "Cannot use an attack with non-positive PP";
            this.attackToPP.put(attack, this.attackToPP.get(attack) - 1);
        }

        assert opponent.currentHP >= 0: "Cannot allow negative HP";
        display(opponent.name + " has " + ANSI_GREEN + opponent.currentHP + " hp " + ANSI_RESET + "left\n" + 
            (opponent.currentHP <= 0 ? opponent.name + " fainted!\n\n": "\n"));
    }

    /*
     * Calculates the damage the attack will do on the opponent, not factoring in critical hits and random pertubation
     */
    public double attackDamage(Attack att, Pokemon opponent) {
        if (opponent == null) {
            throw new IllegalStateException("Opponent must not be null and must have positive HP");
        }
        if (this.attackToPP.get(att) == null || this.attackToPP.get(att) <= 0) {
            att = Attack.STRUGGLE;
        }
        
        Stat offensiveStat = att.isPhysical ? Stat.ATTACK: Stat.SPECIAL_ATTACK;
        Stat defensiveStat = att.isPhysical ? Stat.DEFENCE: Stat.SPECIAL_DEFENCE;

        double damageDealt = (((2.0 * LEVEL + 10.0) / 250.0 * this.statToValue.get(offensiveStat) / opponent.statToValue.get(defensiveStat) 
            * att.baseDamage + 2.0) * att.type.getEffectiveness(this.type1, this.type2, opponent.type1, opponent.type2));
        return damageDealt;
    }

    /*
     * Returns the numerical value of a given stat
     * If the Stat is HP, this will return the max HP, not the current HP
     */
    public int getStat(Stat s) {
        return this.statToValue.get(s);
    }

    /*
     * Returns the current number of hitpoints of a pokemon
     */
    public int getCurrentHP() {
        return this.currentHP;
    }

    /*
     * Resores HP and PP of this Pokemon
     */
    protected void heal() {
        this.currentHP = this.statToValue.get(Stat.HP);
        for (Attack a: attackToPP.keySet()) {
            this.attackToPP.put(a, a.basePP);
        }
    }

    /*
     * Returns an array of the attacks this Pokemon knows
     */
    public Attack[] getAttacks() {
        Attack[] attks = new Attack[attackToPP.keySet().size()];
        int i = 0;
        for (Attack a: attackToPP.keySet()) {
            attks[i++] = a;
        }
        return attks;
    }

    /*
     * Returns the number of remaining uses for the given Attack, or 0 if this Pokemon does not know the Attack
     */
    public int getAttackPP(Attack a) {
        Integer pp = attackToPP.get(a);
        return pp != null ? pp: 0;
    }

    /*
     * Prints a String representation of this Pokemon
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(this.name + ": %-1s %-1s%n", type1.name(), (type2 != null ? type2.name(): "")));
        sb.append("Stats: ");
        for (Stat s: statToValue.keySet()) {
            if (s != Stat.HP) {
                sb.append(String.format("%-20s ", s.name() + "(" + statToValue.get(s) + ")"));
            }
            else {
                sb.append(String.format("%-20s ", s.name() + "(" + this.currentHP + ")"));
            }
        }
        sb.append('\n');
        sb.append("Attacks: ");
        for (Attack a: attackToPP.keySet()) {
            sb.append(a.name() + ' ');
        }
        sb.append('\n');
        return sb.toString();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String charizardString = "                 .\"-,.__\n                 `.     `.  ,\n"
            + "              .--'  .._,'\"-' `.\n             .    .'         `'\n             "
            + "`.   /          ,'\n               `  '--.   ,-\"'\n                `\"`   |  \\"
            + "\n                   -. \\, |\n                    `--Y.'      ___.\n           "
            + "              \\     L._, \\\n               _.,        `.   <  <\\             "
            + "   _\n             ,' '           `, `.   | \\            ( `\n          ../, `."
            + "            `  |    .\\`.           \\ \\_\n         ,' ,..  .           _.,'   "
            + " ||\\l            )  '\".\n        , ,'   \\           ,'.-.`-._,'  |           "
            + ".  _._`.\n      ,' /      \\ \\        `' ' `--/   | \\          / /   ..\\\n   "
            + " .'  /        \\ .         |\\__ - _ ,'` `        / /     `.`.\n    |  '        "
            + "  ..         `-...-\"  |  `-'      / /        . `.\n    | /           |L__      "
            + "     |    |          / /          `. `.\n   , /            .   .          |    |"
            + "         / /             ` `\n  / /          ,. ,`._ `-_       |    |  _   ,-' /"
            + "               ` \\\n / .           \\\"`_/. `-_ \\_,.  ,'    +-' `-'  _,       "
            + " ..,-.    \\`.\n.  '         .-f    ,'   `    '.       \\__.---'     _   .'   ' "
            + "    \\ \\\n' /          `.'    l     .' /          \\..      ,_|/   `.  ,'`     "
            + "L`\n|'      _.-\"\"` `.    \\ _,'  `            \\ `.___`.'\"`-.  , |   |    | \\"
            + "\n||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|\n||  ,"
            + "'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||\n|| '        "
            + "      V      / /           `   | `   ,'   ,' '.    !  `. ||\n||/            _,--"
            + "-----7 '              . |  `-'    l         /    `||\n. |          ,' .-   ,' ||"
            + "               | .-.        `.      .'     ||\n `'        ,'    `\".'    |      "
            + "         |    `.        '. -.'       `'\n          /      ,'      |             "
            + "  |,'    \\-.._,.'/'\n          .     /        .               .       \\    .''"
            + "\n        .`.    |         `.             /         :_,'.'\n          \\ `...\\ "
            + "  _     ,'-.        .'         /_.-'\n           `-.__ `,  `'   .  _.>----''.  _"
            + "  __  /\n                .'        /\"'          |  \"'   '_\n               /_|"
            + ".-'\\ ,\".             '.'`__'-( \\\n                 / ,\"'\"\\,'              "
            + " `/  `-.|\" mh";
    public static final String machampString = "                 __.\"`. .-.                    ,-..__\n    "
            + "          ,-.  \\  |-| |               ,-\"+' ,\"'  `.\n              \\  \\  \\"
            + "_' `.'             .'  .|_.|_.,--'.\n               \\.'`\"     `.              "
            + "`-' `.   .  _,'.\n                \\_     `\"\"\"-.             .\"--+\\   '\"  "
            + " |\n                | `\"\"+..`..,'             `-._ |        |\n               "
            + "j     |                       '.       _/.\n              /   ,' `.      _.----."
            + "_          `\"-.  '   \\\n             |   |     |   ,'  ,.-\"\"\"`.           |"
            + "  .    \\\n    __       |   '    /-._.  ,'        `.         |   \\    \\\n   ( "
            + " `.     `.     .'    | /  _,.-----. \\       j     .    \\\n    `. |.  __  `,   "
            + "      |j ,'\\        `|\"+---._|          ,\n .-\"-|\"' \\\"  |   \". '.    ||/d"
            + " |_-\"\"`.    /     ,'.          )\n `._. |  '.,.'     '  `  ,||_.-\"      |  j "
            + "    '   `        .\n.\"'--:' .  )        `.  (     _.-+    |  |                 "
            + " |\n`-,..'  ` <_          `-.`..+\"   '   ./,  ._         |      |\n `.__|   |  "
            + "`-._     _.-\"`. |   /  ,'j      `. `....' ____..'\n   `-.,.'    \\  `. ,'     ,"
            + "-|_,'  /  |        `.___,-'   )\n      `.      `.  Y       `-..__.',-'    __,.' "
            + "          '\n        `         '   ,--.    |  /            `+\"\"       `.\n    "
            + "     `.       ,--+   '  .-+-\"  _,'   ,--  /     '.    |\n           `-..   \\  "
            + "   __,'           .'    /        `.  |\n               `---)   |  ____,'      ,."
            + "...-'           `,'\n                  '                 ,' _,-----.         /\n"
            + "                   `.____,.....___.\\ _...______________/\n                     "
            + "             __\\:+.`'O O  O O  O |\n                              ,-\"'  _,|:;|"
            + "\"\"\"\"\"\"\"\"\"\"\"\"|\n                            ,'   ,-'  `._/    _.\"  ."
            + "`-|\n                         .-\"    '      \\    .'      `.`.\n               "
            + "         :      .        \\   |        / |\n                         .      \\._"
            + "_   _,`-.|       /  |\n                         `.      \\  \"\"'     `.        "
            + " `....\n                           .     |            \\             `.\n       "
            + "                   .'   ,'              \\              |\n                  ,--"
            + "----'     `.               `-...._  '\"-. '.\n                 / ,'\"'\"`       "
            + " |                  `--`._      `.\n                 `\"......---\"\"--'        "
            + "                 \\       .\n                                                   "
            + "       |        `.\n                                                         (  "
            + " -..     .\n                                                          `\"\"\"' `"
            + "....' mh";
    public static final String scytherString = "           ______\n       _.-\"______`._             ,.\n   "
            + "  ,\"_,\"'      `-.`._         /.|\n   ,',\"   ____      `-.`.___   // |\n  /.' "
            + ",-\"'    `-._     `.   | j.  |  /|\n // .'   __...._  `\"--.. `. ' |   | ' '\nj/"
            + "  _.-\"'       `._,.\"\".   |  |   |/ '\n|.-'                    `.'/| |   | /\n"
            + "'                        '/ | |   |/\n                         /  ' '   '\n     "
            + "              |.   ` .'/.   /\n                   | `. ,','.  ,'\n              "
            + "     |   \\.' j.-'/\n                   '   '   '. /\n                  |       "
            + "   `\"-...__\n                  |             _..-'\n                 ,|'      _"
            + "_.-7'   _......____\n                . |    ,\"/   ,'`.'__........___`-...__\n  "
            + "               .    '-'_..' .-\"\"-._         `\"\"'-----`---...___\n           "
            + "      |____.-','\" /      /`.._,\"\".                 _.-'\n              ,\"`| "
            + ",'   '   |      .,--. ;--|             _,-\"\n             |   '.| `-.|   `.    "
            + " ||   /   '`---.....--\"'.\n             '     `._  |     `+----`._;'.   `-..___"
            + "_..--'\"\n              `.    | \"'|__...-|,|       /     `.\n                |-"
            + "..|`-.7    /   '      /   |  '|\n                ' |' `.||`--'    |      \\   | "
            + ". |\n                        |        |       \\  ' | |\n                       "
            + " `.      .'        .   ' '\n                          `'-+-\"|`.       '  ' /\n "
            + "                            |`-'  \\     /  /.'\n                             ` "
            + "  _ ,.   / ,'/\n                              ||'.'`.  / /,'\n                  "
            + "             `      ' .'\n                                     /.' mh";
    public static final String alakazamString = "                                               _,'|\n      "
            + "                                       .'  /\n                    __            "
            + "         ,'   '\n                   `  `.                 .'    '\n             "
            + "       \\   `.             ,'     '\n                     \\    `.          ,   "
            + "   /\n                      .     `.       /      ,\n                      '    "
            + "   ..__../'     /\n                       \\     ,\"'   '      . _.._\n         "
            + "               \\  ,'             |'    `\"._\n                         |/      "
            + "         ,---.._   `.\n                       ,-|           .   '       `-.  \\\n"
            + "                     ,'  |     ,   ,'   :           '__\\_\n                    "
            + " |  /,_   /  ,U|    '            |   .__\n                     `,' `.\\ `./..-' "
            + " __ \\           |   `. `.\n                       `\",_|  /     ,\"  `.`._     "
            + "  .|     \\ |\n                      / /_.| j  ---'.     `._`-----`.`     | |\n "
            + "                    / // ,|`'  `-/' `.      `\"/-+--'    ,'  `.\n               "
            + "  _,.`,'| / |.'  -,' \\  \\       \\ '._    /     |\n .--.      _,.-\"'   `| L \\"
            + " \\__ ,^.__.\\  `.  _,--`._,>+-'  __,-'\n:    \\   ,'          |  | \\          "
            + "/.   `'      '.  `--'| \\\n'    | ,-.. `'   _,--' ,'  \\        `.\\            "
            + "7      |,.\\\n `._ '.  .`.    .>  `-.-    |-.\"\"---..-\\        _>`       `.-'\n"
            + "    `.,' | l  ,' ,>         | `.___,....\\._    ,--``-.\n   j | .'|_|.'  /_     "
            + "    /   _|         \\`\"--+--.   ` ,..._\n   |_`-'/  |     ,' ,.._,.'\"\"\"'\\  "
            + "         `--'    `-..'     `\".\n     \"-'_,+'\\    '^-     |      \\           "
            + "         /         |\n          |_/         __ \\       .                   `.`."
            + "._  ,'`.\n                  _.:'__`'        `,.                  |   `'   |\n   "
            + "              `--`-..`\"        /--`               ,-`        |\n               "
            + "    `---'---------'                   \"\"| `#     '.\n                         "
            + "                              `._,       `:._\n                                 "
            + "                        `|   ,..  |  '.\n                                       "
            + "                  j   '.  `-+---'\n                                             "
            + "            |,.. |\n                                                          `."
            + " `;\n                                                            `' mh";
    public static final String rapidashString = "                     :`./\n                    _|  ,-\n    "
            + "           ,'\"\"'    ,`\n             ,'.\\       `.    __  ,.-.\n          . ."
            + "/ `'    __  '. ,'  \\ `.|\n          \\\\  \\   .\"'  L   \"     `\" `\\        "
            + "                  _,-.\n           \\` |\\.`      7     .,   :._|   --'`.       "
            + "          ` |\n          ` \\`+ `'\\      \\^--\"  `. |    ,'     `.            "
            + ",..' |\n           | ,.    |              ` `.  |    ..  '.          |    /\n   "
            + "        ':P'     '.    ,..      \\  `-+`\"-'  `._ \\     -`,- ..,'\n          / "
            + "       / `-,-'  ,'`.    `.   ; .--'   `+    '.   | ,\n         /     _..     .  "
            + " `-.  \\,.   `-'  '.  `.^  `\".__|   ' |\n        '   , / |       `.   \\    |  "
            + "      ,'     \\           /\n         `\"' \" .         \\   |  __ \\    ,-'    "
            + "   `----.   _,'\n              /           |  `\"' _} `\"\"'                `-'\n"
            + "             /.'         /     .-.         ,\".\n     .._,.  /           /     '"
            + "-.,'    ,'-. .'.\n    /  `. \\/             `-.      `.   /`.  :\n   /  __ `.'  "
            + "              '-.     `-+_.'  .'          ,__\n  / .'  `.___                  `,"
            + "..__      <__          \\ (\n / /       \"..   /                   `-.     .' .-"
            + "'\"`--.'  \\\n/  |       /-'  /                       \\ ,._|  |          /'\n\\"
            + ".'|+.+.  (`..,'                         \\`._ _,'           \\__\n \\ |||| \\ _`"
            + ".^ `.            .            |  \"    .'`\"-.       `.\n  `+'|/ `( \\'    `-..."
            + ".__    |            |._,\".,'     `,        |\n                         `:-.|   "
            + "         `           ..'   ,'`.,-\n                          |  |            |`."
            + "        '-..    . /\n                          '  |           /  /           `. "
            + "  |\n                           ` '          /  ',.         ,     `._\n         "
            + "                   \\|        ,'   \\'|         :  __    '\n                    "
            + "         `,     ,`     .._`..       `'  `-,.`.\n                       _`'`\".  "
            + "`.   ``-._ /   F   )        ,._\\ `\n                      '-\"'`, \\   \\ ,. )."
            + "-'-.^,|_,'         `  '.\n                          '.. \\___j  `\"'            "
            + "   ,..  | .'\n                             \\            ___       ,. `\\ \\,+-'"
            + "\n                              7.._   .--+`.  |_    |  `,'\n                   "
            + "        _,'  .'`--'  '    7 ` v.-\n                         .\"._  /-.  -.   \\."
            + "^-`\n                       .'  __+'...`'  `--'\n                        `\"\" m"
            + "h";
}
