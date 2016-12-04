import java.util.*;
import java.io.File;
import java.net.URL;

public class Pokemon {

    static final String charizard = "                 .\"-,.__\n                 `.     `.  ,\n              .--'  .._,'\"-' `.\n             .    .'         `'\n             `.   /          ,'\n               `  '--.   ,-\"'\n                `\"`   |  \\\n                   -. \\, |\n                    `--Y.'      ___.\n                         \\     L._, \\\n               _.,        `.   <  <\\                _\n             ,' '           `, `.   | \\            ( `\n          ../, `.            `  |    .\\`.           \\ \\_\n         ,' ,..  .           _.,'    ||\\l            )  '\".\n        , ,'   \\           ,'.-.`-._,'  |           .  _._`.\n      ,' /      \\ \\        `' ' `--/   | \\          / /   ..\\\n    .'  /        \\ .         |\\__ - _ ,'` `        / /     `.`.\n    |  '          ..         `-...-\"  |  `-'      / /        . `.\n    | /           |L__           |    |          / /          `. `.\n   , /            .   .          |    |         / /             ` `\n  / /          ,. ,`._ `-_       |    |  _   ,-' /               ` \\\n / .           \\\"`_/. `-_ \\_,.  ,'    +-' `-'  _,        ..,-.    \\`.\n.  '         .-f    ,'   `    '.       \\__.---'     _   .'   '     \\ \\\n' /          `.'    l     .' /          \\..      ,_|/   `.  ,'`     L`\n|'      _.-\"\"` `.    \\ _,'  `            \\ `.___`.'\"`-.  , |   |    | \\\n||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|\n||  ,'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||\n|| '              V      / /           `   | `   ,'   ,' '.    !  `. ||\n||/            _,-------7 '              . |  `-'    l         /    `||\n. |          ,' .-   ,' ||               | .-.        `.      .'     ||\n `'        ,'    `\".'    |               |    `.        '. -.'       `'\n          /      ,'      |               |,'    \\-.._,.'/'\n          .     /        .               .       \\    .''\n        .`.    |         `.             /         :_,'.'\n          \\ `...\\   _     ,'-.        .'         /_.-'\n           `-.__ `,  `'   .  _.>----''.  _  __  /\n                .'        /\"'          |  \"'   '_\n               /_|.-'\\ ,\".             '.'`__'-( \\\n                 / ,\"'\"\\,'               `/  `-.|\" mh";
    static final String machamp = "                 __.\"`. .-.                    ,-..__\n              ,-.  \\  |-| |               ,-\"+' ,\"'  `.\n              \\  \\  \\_' `.'             .'  .|_.|_.,--'.\n               \\.'`\"     `.              `-' `.   .  _,'.\n                \\_     `\"\"\"-.             .\"--+\\   '\"   |\n                | `\"\"+..`..,'             `-._ |        |\n               j     |                       '.       _/.\n              /   ,' `.      _.----._          `\"-.  '   \\\n             |   |     |   ,'  ,.-\"\"\"`.           |  .    \\\n    __       |   '    /-._.  ,'        `.         |   \\    \\\n   (  `.     `.     .'    | /  _,.-----. \\       j     .    \\\n    `. |.  __  `,         |j ,'\\        `|\"+---._|          ,\n .-\"-|\"' \\\"  |   \". '.    ||/d |_-\"\"`.    /     ,'.          )\n `._. |  '.,.'     '  `  ,||_.-\"      |  j     '   `        .\n.\"'--:' .  )        `.  (     _.-+    |  |                  |\n`-,..'  ` <_          `-.`..+\"   '   ./,  ._         |      |\n `.__|   |  `-._     _.-\"`. |   /  ,'j      `. `....' ____..'\n   `-.,.'    \\  `. ,'     ,-|_,'  /  |        `.___,-'   )\n      `.      `.  Y       `-..__.',-'    __,.'           '\n        `         '   ,--.    |  /            `+\"\"       `.\n         `.       ,--+   '  .-+-\"  _,'   ,--  /     '.    |\n           `-..   \\     __,'           .'    /        `.  |\n               `---)   |  ____,'      ,....-'           `,'\n                  '                 ,' _,-----.         /\n                   `.____,.....___.\\ _...______________/\n                                  __\\:+.`'O O  O O  O |\n                              ,-\"'  _,|:;|\"\"\"\"\"\"\"\"\"\"\"\"|\n                            ,'   ,-'  `._/    _.\"  .`-|\n                         .-\"    '      \\    .'      `.`.\n                        :      .        \\   |        / |\n                         .      \\.__   _,`-.|       /  |\n                         `.      \\  \"\"'     `.         `....\n                           .     |            \\             `.\n                          .'   ,'              \\              |\n                  ,------'     `.               `-...._  '\"-. '.\n                 / ,'\"'\"`        |                  `--`._      `.\n                 `\"......---\"\"--'                         \\       .\n                                                          |        `.\n                                                         (   -..     .\n                                                          `\"\"\"' `....' mh";
    static final String scyther = "           ______\n       _.-\"______`._             ,.\n     ,\"_,\"'      `-.`._         /.|\n   ,',\"   ____      `-.`.___   // |\n  /.' ,-\"'    `-._     `.   | j.  |  /|\n // .'   __...._  `\"--.. `. ' |   | ' '\nj/  _.-\"'       `._,.\"\".   |  |   |/ '\n|.-'                    `.'/| |   | /\n'                        '/ | |   |/\n                         /  ' '   '\n                   |.   ` .'/.   /\n                   | `. ,','.  ,'\n                   |   \\.' j.-'/\n                   '   '   '. /\n                  |          `\"-...__\n                  |             _..-'\n                 ,|'      __.-7'   _......____\n                . |    ,\"/   ,'`.'__........___`-...__\n                 .    '-'_..' .-\"\"-._         `\"\"'-----`---...___\n                 |____.-','\" /      /`.._,\"\".                 _.-'\n              ,\"`| ,'   '   |      .,--. ;--|             _,-\"\n             |   '.| `-.|   `.     ||   /   '`---.....--\"'.\n             '     `._  |     `+----`._;'.   `-..____..--'\"\n              `.    | \"'|__...-|,|       /     `.\n                |-..|`-.7    /   '      /   |  '|\n                ' |' `.||`--'    |      \\   | . |\n                        |        |       \\  ' | |\n                        `.      .'        .   ' '\n                          `'-+-\"|`.       '  ' /\n                             |`-'  \\     /  /.'\n                             `   _ ,.   / ,'/\n                              ||'.'`.  / /,'\n                               `      ' .'\n                                     /.' mh";
    static final String alakazam = "                                               _,'|\n                                             .'  /\n                    __                     ,'   '\n                   `  `.                 .'    '\n                    \\   `.             ,'     '\n                     \\    `.          ,      /\n                      .     `.       /      ,\n                      '       ..__../'     /\n                       \\     ,\"'   '      . _.._\n                        \\  ,'             |'    `\"._\n                         |/               ,---.._   `.\n                       ,-|           .   '       `-.  \\\n                     ,'  |     ,   ,'   :           '__\\_\n                     |  /,_   /  ,U|    '            |   .__\n                     `,' `.\\ `./..-'  __ \\           |   `. `.\n                       `\",_|  /     ,\"  `.`._       .|     \\ |\n                      / /_.| j  ---'.     `._`-----`.`     | |\n                     / // ,|`'  `-/' `.      `\"/-+--'    ,'  `.\n                 _,.`,'| / |.'  -,' \\  \\       \\ '._    /     |\n .--.      _,.-\"'   `| L \\ \\__ ,^.__.\\  `.  _,--`._,>+-'  __,-'\n:    \\   ,'          |  | \\          /.   `'      '.  `--'| \\\n'    | ,-.. `'   _,--' ,'  \\        `.\\            7      |,.\\\n `._ '.  .`.    .>  `-.-    |-.\"\"---..-\\        _>`       `.-'\n    `.,' | l  ,' ,>         | `.___,....\\._    ,--``-.\n   j | .'|_|.'  /_         /   _|         \\`\"--+--.   ` ,..._\n   |_`-'/  |     ,' ,.._,.'\"\"\"'\\           `--'    `-..'     `\".\n     \"-'_,+'\\    '^-     |      \\                    /         |\n          |_/         __ \\       .                   `.`.._  ,'`.\n                  _.:'__`'        `,.                  |   `'   |\n                 `--`-..`\"        /--`               ,-`        |\n                   `---'---------'                   \"\"| `#     '.\n                                                       `._,       `:._\n                                                         `|   ,..  |  '.\n                                                         j   '.  `-+---'\n                                                         |,.. |\n                                                          `. `;\n                                                            `' mh";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /*
     * This main method allows us to test Pokemon objects
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Pokemon p1;
        Pokemon p2;
        AePlayWave battleMusic = null;
        boolean skipSteps = false;
        if (args!= null && args.length >= 2) {
            skipSteps = args.length >= 3 && args[2].equalsIgnoreCase("skip");
            SKIP_SOUND = skipSteps;
            if (!skipSteps) {
                battleMusic = new AePlayWave("prime-cup1-3.wav", AePlayWave.BATTLE_MUSIC_BUFFER_SIZE);
                battleMusic.start();
                double random = Math.random();
                System.out.println(random < 0.25 ? machamp: random < 0.5 ? scyther: random < 0.75 ? alakazam: charizard);
            }
            p1 = new Pokemon(args[0].toUpperCase());
            p2 = new Pokemon(args[1].toUpperCase());
        }
        else {
            double random = Math.random();
            System.out.println(random < 0.25 ? machamp: random < 0.5 ? scyther: random < 0.75 ? alakazam: charizard);
            System.out.println("\nMusic? [y] - yes, [blank] - no");
            String response = scan.nextLine();
            if (response != null && response.length() > 0 && response.charAt(0) == 'y') {
                battleMusic = new AePlayWave("prime-cup1-3.wav", AePlayWave.BATTLE_MUSIC_BUFFER_SIZE);
                battleMusic.start();
            }
            displayPokemon();
            System.out.println("Choose player 1's Pokemon");
            p1 = askForPokemon(scan);
            System.out.println("\nChoose player 2's Pokemon");
            p2 = askForPokemon(scan);
        }

        System.out.println("\n" + p1.name + " with HP:" + p1.getCurrentHP() + "   vs   " 
                    + p2.name + " with HP:" + p2.getCurrentHP() + "\n");
        if (!skipSteps) {
            scan.nextLine();
        }

        int i = 1;
        while (p1.currentHP != 0 && p2.currentHP != 0) {
            System.out.println("Turn " + (i++) + " ---------------------------");
            doTurn(p1, p2);
            if (!skipSteps && p1.currentHP != 0 && p2.currentHP != 0) {
                scan.nextLine();
            }
        }
        if (p1.soundPlayer != null) {
            p1.soundPlayer.quit();
        }
        if (p2.soundPlayer != null) {
            p2.soundPlayer.quit();
        }
        if (battleMusic != null) {
            battleMusic.quit();
        }
    }

    /*
     * Pokemon instance variables - each Pokemon has its own copy. The public variables can be referenced using reference.variable
     */
    public final String name;
    public final Type type1;
    public final Type type2;
    private final EnumMap<Stat, Integer> statToValue = new EnumMap<Stat, Integer>(Stat.class);
    private final EnumMap<Attack, Integer> attackToPP = new EnumMap<Attack, Integer>(Attack.class);
    private final AePlayWave soundPlayer;
    private int currentHP;

    /*
     * Pokemon class variables - there is only one global copy of these. They can be referenced by using Pokemon.variable
     */
    public static final PokemonEnum DEFAULT_POKEMON = PokemonEnum.MAGIKARP;
    public static final int LEVEL = 50;
    public static final double CRITICAL_HIT_PROBABILITY = 0.0625;
    public static final int BASE_STAT_TOTAL_DISPLAY_THRESHHOLD = 580;
    public static final String STAT_MAXIMIZER_PREFIX = "_";
    public static boolean SKIP_SOUND = false;

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
            System.out.println("Invalid Pokemon name" + (name == null ? "": " \"" + name + "\"") + ". Generating default..");
            maximizeStats = false;
            p = DEFAULT_POKEMON;
        }

        this.name = p.name();
        this.type1 = p.type1;
        this.type2 = p.type2;
        int[] baseStats = p.baseStats;
        EnumSet<Attack> attacks = p.attacks;

        if (!SKIP_SOUND && new File("cries/" + this.name + ".wav").exists()) {
            this.soundPlayer = new AePlayWave("cries/" + this.name + ".wav");
            this.soundPlayer.start();
        }
        else {
            this.soundPlayer = null;
        }

        createInstanceMappings(maximizeStats, baseStats, attacks);
    }

    /*
     * Create a Pokemon by providing necessary parameters. This allows us to create custom Pokemon not listed in PokemonEnum
     */
    public Pokemon(String name, Type type1, Type type2, int[] baseStats, EnumSet<Attack> attacks) {
        boolean invalidArguments = false;
        if (name == null || name.length() == 0) {
            System.out.println("Name must be non null and non empty. Generating default..");
            invalidArguments = true;
        }
        else if (type1 == null) {
            System.out.println("A Pokemon must have a valid primary type. Generating default..");
            invalidArguments = true;
        }
        else if (type1 == type2) {
            System.out.println("A Pokemon may not have two identical types " + type1.name() + ". Generating default..");
            invalidArguments = true;
        }
        else if (baseStats.length != Stat.values().length) {
            System.out.println("Attempted to construct Pokemon with " + baseStats.length 
                + " stats when " + Stat.values().length + " stats are required. Generating default..");
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

        if (!SKIP_SOUND && new File("cries/" + this.name + ".wav").exists()) {
            this.soundPlayer = new AePlayWave("cries/" + this.name + ".wav");
            this.soundPlayer.start();
        }
        else {
            this.soundPlayer = null;
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
        Stat attackStatToMaximize = !maximizeStats ? null: baseStats[Stat.ATTACK.ordinal()] >= baseStats[Stat.SPECIAL_ATTACK.ordinal()] 
            ? Stat.ATTACK: Stat.SPECIAL_ATTACK;
        Stat attackStatToMinimize = !maximizeStats ? null: baseStats[Stat.ATTACK.ordinal()] < baseStats[Stat.SPECIAL_ATTACK.ordinal()] 
            ? Stat.ATTACK: Stat.SPECIAL_ATTACK;
        for (int i = 0; i < baseStats.length; i++) {
            Stat s = Stat.values()[i];
            if (s == Stat.HP) {
                this.statToValue.put(Stat.HP, baseStats[i] == 1 ? 1: 
                    (int)(((2.0 * baseStats[i] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
                    + (maximizeStats ? 4.0: 0.0) / 4.0) * LEVEL / 100.0) + LEVEL + 10.0));
            }
            else {
                this.statToValue.put(s, 
                    (int)((((2.0 * baseStats[i] + (maximizeStats ? 31.0: (int)(Math.random() * 32.0)) 
                    + (maximizeStats && (s == Stat.SPEED || s == attackStatToMaximize) ? 252.0: 0.0) 
                    / 4.0) * LEVEL / 100.0) + 5.0) * (s == Stat.SPEED ? 1.1: s == attackStatToMinimize ? 0.9: 1.0)));
            }
        }
    }

    /*
     * Displays a list of non-hidden Pokemon
     */
    public static void displayPokemon() {
        System.out.println("\nAvailable Pokemon");
        for (PokemonEnum poke: PokemonEnum.values()) {
            int baseStatTotal = 0;
            for (int i = 0; i < poke.baseStats.length; i++) {
                baseStatTotal += poke.baseStats[i];
            }
            if (baseStatTotal < BASE_STAT_TOTAL_DISPLAY_THRESHHOLD && !poke.name().contains("MEGA_") && poke.type1 != Type.NONE 
                    && !poke.name().startsWith(STAT_MAXIMIZER_PREFIX)) {
                System.out.printf("%-12s Type 1:%-12s Type 2:%-12s Attacks:", poke.name(), poke.type1.name(), 
                        (poke.type2 == null ? "": poke.type2.name()));
                for (Attack a: poke.attacks) {
                    System.out.print(a.name() + " ");
                }
                System.out.println();
                System.out.print("\t");
                for (Stat s: Stat.values()) {
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

                System.out.println("Enter the " + Stat.values().length + " base stats separated by commas or spaces");
                Scanner statScan = new Scanner(scan.nextLine());
                statScan.useDelimiter("[,\\s]+");
                int[] customBaseStats = new int[Stat.values().length];
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
        else if (p1.getStat(Stat.SPEED) > p2.getStat(Stat.SPEED)) {
            first = p1;
            second = p2;
        }
        else {
            first = p2;
            second = p1;
        }
        assert (first != second);
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
            assert (attackScore >= 0);
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
            System.out.println("Invalid attack!");
            attack = Attack.STRUGGLE;
        }
        else if (attack == Attack.STRUGGLE || this.attackToPP.get(attack) <= 0) {
            System.out.println(this.name + " has run out of attacks!");
            attack = Attack.STRUGGLE;
        }

        System.out.println(this.name + " used " + attack.name());
        if (Math.random() * 100 < attack.baseAccuracy) {
            double effectiveness = attack.type.getEffectiveness(this.type1, this.type2, opponent.type1, opponent.type2);
            System.out.println(
                effectiveness >= 2 ? ANSI_CYAN +  "It's super effective!" + ANSI_RESET: 
                effectiveness < 1 && effectiveness > 0 ? ANSI_RED +  "It's not very effective.." + ANSI_RESET: 
                effectiveness == 0 ? (ANSI_PURPLE +  opponent.name + " is unaffected!" + ANSI_RESET): 
                (opponent.name + " was hit"));

            //Scale factor includes critical hits and random scaling between 85-100%
            double scaleFactor = effectiveness == 0 ? 0: 1;
            if (effectiveness != 0 && Math.random() < CRITICAL_HIT_PROBABILITY) {
                System.out.println(ANSI_YELLOW + "It's a critical hit!" + ANSI_RESET);
                scaleFactor *= 2;
            }
            scaleFactor *= (100 - (int)(Math.random() * 16)) / 100.0;

            int damageDealt = (int)(attackDamage(attack, opponent) * scaleFactor);
            opponent.currentHP -= damageDealt <= opponent.currentHP ? damageDealt: opponent.currentHP;
        }
        else {
            System.out.println("The attack missed!");
        }

        if (this.attackToPP.get(attack) != null) {
            assert (this.attackToPP.get(attack) > 0);
            this.attackToPP.put(attack, this.attackToPP.get(attack) - 1);
        }

        boolean opponentFainted = opponent.currentHP <= 0;
        if (opponentFainted && opponent.soundPlayer != null) {
            opponent.soundPlayer.quit();
        }
        System.out.println(opponent.name + " has " + ANSI_GREEN + opponent.currentHP + " hp " + ANSI_RESET + "left\n" + 
            (opponentFainted ? opponent.name + " fainted!\n": ""));
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
            sb.append(String.format("%-5s ", s.name() + "(" + statToValue.get(s) + ")"));
        }
        sb.append('\n');
        sb.append("Attacks: ");
        for (Attack a: attackToPP.keySet()) {
            sb.append(a.name() + ' ');
        }
        sb.append('\n');
        return sb.toString();
    }

}

enum PokemonEnum {
    //Add new pokemon here
    ZAPDOS(Type.ELECTRIC, Type.FLYING, new int[]{90,90,85,125,90,100}, EnumSet.of(Attack.THUNDER_BOLT, Attack.DRILL_PECK)), 
    DRAGONITE(Type.DRAGON, Type.FLYING, new int[]{91, 134, 95, 100, 100, 80}, EnumSet.of(Attack.DRAGON_CLAW, Attack.AERIAL_ACE)), 
    MEWTWO(Type.PSYCHIC, null, new int[]{106,110,90,154,90,130}, EnumSet.of(Attack.PSYSTRIKE, Attack.SHADOW_BALL, Attack.BLIZZARD)), 
    MEW(Type.PSYCHIC, null, new int[]{100,100,100,100,100,100}, EnumSet.complementOf(EnumSet.of(Attack.STRUGGLE))), 
    SHEDINJA(Type.NONE, null, new int[]{1, 90, 45, 30, 30, 40}, EnumSet.of(Attack.X_SCISSOR, Attack.SHADOW_BALL)), 
    METAGROSS(Type.STEEL, Type.PSYCHIC, new int[]{80, 135, 130, 95, 90, 70}, EnumSet.of(Attack.IRON_HEAD, Attack.PSYCHIC)), 
    GARCHOMP(Type.DRAGON, Type.GROUND, new int[]{108, 130, 95, 80, 85, 102}, EnumSet.of(Attack.DRAGON_CLAW, Attack.EARTHQUAKE)), 
    REGIGIGAS(Type.NORMAL, null, new int[]{110,160,110,80,110,100}, EnumSet.of(Attack.DIZZY_PUNCH, Attack.BRICK_BREAK)), 
    VICTINI(Type.PSYCHIC, Type.FIRE, new int[]{100, 100, 100, 100, 100, 100}, EnumSet.of(Attack.FLARE_BLITZ, Attack.PSYCHIC)), 

    MEGA_VENUSAUR(Type.GRASS, Type.POISON, new int[]{80,100,123,122,120,80}, 
    	EnumSet.of(Attack.ENERGY_BALL, Attack.SLUDGE_BOMB, Attack.EARTHQUAKE, Attack.BODY_SLAM)),
    MEGA_CHARIZARD_X(Type.FIRE, Type.DRAGON, new int[]{78, 130, 111, 130, 85, 100}, 
        EnumSet.of(Attack.FLARE_BLITZ, Attack.DRAGON_CLAW, Attack.EARTHQUAKE, Attack.STEEL_WING)), 
    MEGA_CHARIZARD_Y(Type.FIRE, Type.FLYING, new int[]{78, 104, 78, 159, 115, 100}, 
        EnumSet.of(Attack.FLAMETHROWER, Attack.DRAGON_PULSE, Attack.AIR_SLASH, Attack.SOLAR_BEAM)), 
    MEGA_BLASTOISE(Type.WATER, null, new int[]{79,103,120,135,115,78}, 
    	EnumSet.of(Attack.SURF, Attack.ICE_BEAM, Attack.AURA_SPHERE, Attack.DRAGON_PULSE)), 
    MEGA_PIDGEOT(Type.NORMAL, Type.FLYING, new int[]{83, 80, 80, 135, 80, 121}, EnumSet.of(Attack.HYPER_BEAM, Attack.HURRICANE)), 
    MEGA_MEWTWO_X(Type.PSYCHIC, Type.FIGHTING, new int[]{106, 190, 100, 154, 100, 130}, EnumSet.of(Attack.PSYSTRIKE, Attack.AURA_SPHERE)), 
    MEGA_MEWTWO_Y(Type.PSYCHIC, null, new int[]{106, 150, 70, 194, 120, 140}, EnumSet.of(Attack.PSYSTRIKE, Attack.SHADOW_BALL)), 
    MEGA_SCIZOR(Type.BUG, Type.STEEL, new int[]{70,150,140,65,100,75}, EnumSet.of(Attack.X_SCISSOR, Attack.IRON_HEAD)), 
    PRIMAL_KYOGRE(Type.WATER, null, new int[]{100, 150, 90, 180, 160, 90}, EnumSet.of(Attack.SURF, Attack.ICE_BEAM)), 
    PRIMAL_GROUDON(Type.GROUND, Type.FIRE, new int[]{100, 180, 160, 150, 90, 90}, EnumSet.of(Attack.EARTHQUAKE, Attack.FLAMETHROWER)), 

    VENUSAUR(Type.GRASS, Type.POISON, new int[]{80, 82, 83, 100, 100, 80}, EnumSet.of(Attack.ENERGY_BALL, Attack.SLUDGE_BOMB)), 
    CHARIZARD(Type.FIRE, Type.FLYING, new int[]{78, 84, 78, 109, 85, 100}, EnumSet.of(Attack.FLAMETHROWER, Attack.DRAGON_PULSE, 
        Attack.AIR_SLASH, Attack.SOLAR_BEAM)), 
    BLASTOISE(Type.WATER, null, new int[]{79, 83, 100, 85, 105, 78}, EnumSet.of(Attack.SURF, Attack.ICE_BEAM)), 
    PIDGEOT(Type.NORMAL, Type.FLYING, new int[]{83, 80, 75, 70, 70, 101}, EnumSet.of(Attack.AIR_SLASH, Attack.STEEL_WING)), 
    RAICHU(Type.ELECTRIC, null, new int[]{60, 90, 55, 90, 80, 110}, EnumSet.of(Attack.THUNDER_BOLT, Attack.IRON_TAIL)), 
    ALAKAZAM(Type.PSYCHIC, null, new int[]{55, 50, 45, 135, 95, 120}, EnumSet.of(Attack.PSYCHIC)), 
    MACHAMP(Type.FIGHTING, null, new int[]{90, 130, 80, 65, 85, 55}, EnumSet.of(Attack.BRICK_BREAK)), 
    GOLEM(Type.ROCK, Type.GROUND, new int[]{80, 120, 130, 55, 65, 45}, EnumSet.of(Attack.ROCK_SLIDE)), 
    GENGAR(Type.GHOST, Type.POISON, new int[]{60, 65, 60, 130, 75, 110}, EnumSet.of(Attack.SHADOW_BALL)), 
    SCYTHER(Type.BUG, Type.FLYING, new int[]{70,110,80,55,80,105}, EnumSet.of(Attack.X_SCISSOR)), 
    ELECTABUZZ(Type.ELECTRIC, null, new int[]{65, 83, 57, 95, 85, 105}, EnumSet.of(Attack.THUNDER_BOLT)), 
    MAGMAR(Type.FIRE, null, new int[]{65, 95, 57, 100, 85, 93}, EnumSet.of(Attack.FLAMETHROWER)), 
    MAGIKARP(Type.WATER, null, new int[]{20, 10, 55, 15, 20, 80}, EnumSet.of(Attack.TACKLE)), 
    SNORLAX(Type.NORMAL, null, new int[]{160, 110, 65, 65, 110, 30}, EnumSet.of(Attack.BODY_SLAM)), 
    STEELIX(Type.STEEL, Type.GROUND, new int[]{75, 85, 200, 55, 65, 30}, EnumSet.of(Attack.IRON_HEAD)), 
    SPIRITOMB(Type.GHOST, Type.DARK, new int[]{50, 92, 108, 92, 108, 35}, EnumSet.of(Attack.SHADOW_BALL)), 
    TOGEKISS(Type.FAIRY, Type.FLYING, new int[]{85,50,95,120,115,80}, EnumSet.of(Attack.DAZZLING_GLEAM)), 
    GLACEON(Type.ICE, null, new int[]{65, 60, 110, 130, 95, 65}, EnumSet.of(Attack.ICE_BEAM)), 
    ZOROARK(Type.DARK, null, new int[]{60,105,60,120,60,105}, EnumSet.of(Attack.DARK_PULSE));

    public final Type type1;
    public final Type type2;
    protected final int[] baseStats;
    protected final EnumSet<Attack> attacks;

    PokemonEnum(Type type1, Type type2, int[] baseStats, EnumSet<Attack> attacks) {
        if (type1 == null) {
            throw new IllegalStateException(this.name() + " must have a valid primary type");
        }
        if (type1 == type2) {
            throw new IllegalStateException(this.name() + " may not have two identical types " + type1.name());
        }
        if (baseStats.length != Stat.values().length) {
            throw new IllegalStateException("Attempted to construct " + this.name() + " with " + baseStats.length 
                + " stats when " + Stat.values().length + " stats are required.");
        }
        this.type1 = type1;
        this.type2 = type2;
        this.baseStats = baseStats;
        this.attacks = attacks;
    }

    public int getBaseStat(Stat s) {
        return this.baseStats[s.ordinal()];
    }

}

enum Stat {
    HP, ATTACK, DEFENCE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED;
}

enum Attack {
    //Add new attacks here
    SOLAR_BEAM(120, 70, 10, Type.GRASS, false), 
    PSYSTRIKE(100, 100, 10, Type.PSYCHIC, false), 
    THUNDER(110, 70, 10, Type.ELECTRIC, false), 
    AURA_SPHERE(90, 100, 20, Type.FIGHTING, false), 
    DIZZY_PUNCH(70, 100, 10, Type.NORMAL, true), 
    IRON_TAIL(100, 75, 15, Type.STEEL, true), 
    AIR_SLASH(75, 95, 20, Type.FLYING, false), 
    EARTHQUAKE(100, 100, 10, Type.GROUND, true), 
    HURRICANE(110, 70, 10, Type.FLYING, false),
    BLIZZARD(110, 70, 5, Type.ICE, false), 
    HYPER_BEAM(150, 60, 5, Type.NORMAL, false), 
    AERIAL_ACE(60, 100, 20, Type.FLYING, true), 
    FLARE_BLITZ(120, 85, 15, Type.FIRE, true), 

    TACKLE(40, 100, 35, Type.NORMAL, true), 
    ENERGY_BALL(90, 100, 10, Type.GRASS, false), 
    FLAMETHROWER(90, 100, 15, Type.FIRE, false), 
    SURF(90, 100, 15, Type.WATER, false), 
    THUNDER_BOLT(90, 100, 15, Type.ELECTRIC, false), 
    ICE_BEAM(90, 100, 10, Type.ICE, false), 
    DRILL_PECK(80, 100, 20, Type.FLYING, true), 
    ROCK_SLIDE(75, 90, 10, Type.ROCK, true), 
    SHADOW_BALL(80, 100, 15, Type.GHOST, false), 
    BRICK_BREAK(75, 100, 15, Type.FIGHTING, true), 
    PSYCHIC(90, 100, 10, Type.PSYCHIC, false), 
    BODY_SLAM(85, 100, 15, Type.NORMAL, true), 
    DRAGON_CLAW(80, 100, 15, Type.DRAGON, true), 
    DRAGON_PULSE(85, 100, 10, Type.DRAGON, false), 
    IRON_HEAD(80, 100, 15, Type.STEEL, true), 
    X_SCISSOR(80, 100, 15, Type.BUG, true), 
    STEEL_WING(70, 90, 25, Type.STEEL, true), 
    SLUDGE_BOMB(90, 100, 10, Type.POISON, false), 
    DAZZLING_GLEAM(80, 100, 10, Type.FAIRY, false), 
    DARK_PULSE(80, 100, 15, Type.DARK, false), 

    STRUGGLE(50, 100, 1, Type.NONE, true);

    //List of attacks that differ from official
    //SOLARBEAM (accuracy)
    //AURA_SPHERE (accuracy)
    //HYPER_BEAM (accuracy)
    //AERIAL_ACE (accuracy)
    //FLARE_BLITZ (accuracy)
    //STRUGGLE (accuracy)
    
    public final int baseDamage;
    public final int baseAccuracy;
    public final int basePP;
    public final Type type;
    public final boolean isPhysical;

    Attack(int damage, int accuracy, int pp, Type type, boolean isPhysical) {
        this.baseDamage = damage;
        this.baseAccuracy = accuracy;
        this.basePP = pp;
        this.type = type;
        this.isPhysical = isPhysical;
    }

}

enum Type {
    NORMAL, 
    FIRE, 
    WATER, 
    ELECTRIC, 
    GRASS, 
    ICE, 
    FIGHTING, 
    POISON, 
    GROUND, 
    FLYING, 
    PSYCHIC, 
    BUG, 
    ROCK, 
    GHOST, 
    DRAGON, 
    DARK, 
    STEEL, 
    FAIRY, 
    NONE;

    //Offensive Type attributes
    private EnumSet<Type> superEffective;
    private EnumSet<Type> notVeryEffective;
    private EnumSet<Type> noEffect;

    static {
        NORMAL.superEffective = EnumSet.noneOf(Type.class);
        NORMAL.notVeryEffective = EnumSet.of(ROCK, STEEL);
        NORMAL.noEffect = EnumSet.of(GHOST, NONE);

        FIRE.superEffective = EnumSet.of(GRASS, ICE, BUG, STEEL);
        FIRE.notVeryEffective = EnumSet.of(FIRE, WATER, ROCK, DRAGON);
        FIRE.noEffect = EnumSet.of(NONE);

        WATER.superEffective = EnumSet.of(FIRE, GROUND, ROCK);
        WATER.notVeryEffective = EnumSet.of(WATER, GRASS, DRAGON);
        WATER.noEffect = EnumSet.of(NONE);

        ELECTRIC.superEffective = EnumSet.of(WATER, FLYING);
        ELECTRIC.notVeryEffective = EnumSet.of(ELECTRIC, GRASS, DRAGON);
        ELECTRIC.noEffect = EnumSet.of(GROUND, NONE);

        GRASS.superEffective = EnumSet.of(WATER, GROUND, ROCK);
        GRASS.notVeryEffective = EnumSet.of(FIRE, GRASS, POISON, FLYING, BUG, DRAGON, STEEL);
        GRASS.noEffect = EnumSet.of(NONE);

        ICE.superEffective = EnumSet.of(GRASS, GROUND, FLYING, DRAGON);
        ICE.notVeryEffective = EnumSet.of(FIRE, WATER, ICE, STEEL);
        ICE.noEffect = EnumSet.of(NONE);

        FIGHTING.superEffective = EnumSet.of(NORMAL, ICE, ROCK, DARK, STEEL);
        FIGHTING.notVeryEffective = EnumSet.of(POISON, FLYING, PSYCHIC, BUG, FAIRY);
        FIGHTING.noEffect = EnumSet.of(GHOST, NONE);

        POISON.superEffective = EnumSet.of(GRASS, FAIRY);
        POISON.notVeryEffective = EnumSet.of(POISON, GROUND, ROCK, GHOST);
        POISON.noEffect = EnumSet.of(STEEL, NONE);

        GROUND.superEffective = EnumSet.of(FIRE, ELECTRIC, POISON, ROCK, STEEL);
        GROUND.notVeryEffective = EnumSet.of(GRASS, BUG);
        GROUND.noEffect = EnumSet.of(FLYING, NONE);

        FLYING.superEffective = EnumSet.of(GRASS, FIGHTING, BUG);
        FLYING.notVeryEffective = EnumSet.of(ELECTRIC, ROCK, STEEL);
        FLYING.noEffect = EnumSet.of(NONE);

        PSYCHIC.superEffective = EnumSet.of(FIGHTING, POISON);
        PSYCHIC.notVeryEffective = EnumSet.of(PSYCHIC, STEEL);
        PSYCHIC.noEffect = EnumSet.of(DARK, NONE);

        BUG.superEffective = EnumSet.of(GRASS, PSYCHIC, DARK);
        BUG.notVeryEffective = EnumSet.of(FIRE, FIGHTING, POISON, FLYING, GHOST, STEEL, FAIRY);
        BUG.noEffect = EnumSet.of(NONE);

        ROCK.superEffective = EnumSet.of(FIRE, ICE, FLYING, BUG);
        ROCK.notVeryEffective = EnumSet.of(FIGHTING, GROUND, STEEL);
        ROCK.noEffect = EnumSet.of(NONE);

        GHOST.superEffective = EnumSet.of(PSYCHIC, GHOST);
        GHOST.notVeryEffective = EnumSet.of(DARK);
        GHOST.noEffect = EnumSet.of(NORMAL, NONE);

        DRAGON.superEffective = EnumSet.of(DRAGON);
        DRAGON.notVeryEffective = EnumSet.of(STEEL);
        DRAGON.noEffect = EnumSet.of(FAIRY, NONE);

        DARK.superEffective = EnumSet.of(PSYCHIC, GHOST);
        DARK.notVeryEffective = EnumSet.of(FIGHTING, DARK, FAIRY);
        DARK.noEffect = EnumSet.of(NONE);

        STEEL.superEffective = EnumSet.of(ICE, ROCK, FAIRY);
        STEEL.notVeryEffective = EnumSet.of(FIRE, WATER, ELECTRIC, STEEL);
        STEEL.noEffect = EnumSet.of(NONE);

        FAIRY.superEffective = EnumSet.of(FIGHTING, DRAGON, DARK);
        FAIRY.notVeryEffective = EnumSet.of(FIRE, POISON, STEEL);
        FAIRY.noEffect = EnumSet.of(NONE);

        NONE.superEffective = EnumSet.noneOf(Type.class);
        NONE.notVeryEffective = EnumSet.noneOf(Type.class);
        NONE.noEffect = EnumSet.noneOf(Type.class);

    }

    /*
     * Return scale factor of using a move of this Type on the opponent. This depends on Same Type Attack Bonus, 
     * and how effective the move is against the opponent's types
     */
    public double getEffectiveness(Type userType1, Type userType2, Type opponentType1, Type opponentType2) {
        double scaleFactor =  this == userType1 || this == userType2 ? 1.5: 1;

        //Calculate scale for opponent type 1
        if (superEffective.contains(opponentType1)) {
            scaleFactor *= 2;
        }
        else if (notVeryEffective.contains(opponentType1)) {
            scaleFactor *= 0.5;
        }
        else if (noEffect.contains(opponentType1)) {
            scaleFactor *= 0;
        }

        //Calculate scale for opponent type 2
        if (superEffective.contains(opponentType2)) {
            scaleFactor *=  2;
        }
        else if (notVeryEffective.contains(opponentType2)) {
            scaleFactor *= 0.5;
        }
        else if (noEffect.contains(opponentType2)) {
            scaleFactor *= 0;
        }
        
        return scaleFactor;
    }

}
