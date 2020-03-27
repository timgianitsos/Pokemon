import java.util.EnumSet;
import java.util.EnumMap;
import java.io.File;

//TODO check parameterized print, put stat calculation explanation in comments
//TODO replace some asserts with exceptions?
//TODO refactor display and sound out of this class
//TODO capability of downloading music at runtime
public class Pokemon {

    public final String name;
    public final Type type1;
    public final Type type2;
    private final EnumMap<Stat, Integer> statToValue = new EnumMap<Stat, Integer>(Stat.class);
    private final EnumMap<Attack, Integer> attackToPP = new EnumMap<Attack, Integer>(Attack.class);
    private int currentHP;

    public static final PokemonTemplate DEFAULT_POKEMON = PokemonTemplate.MAGIKARP;
    public static final int LEVEL = 100;
    public static final double CRITICAL_HIT_PROBABILITY = 0.0625;
    public static final String STAT_MAXIMIZER_PREFIX = "_";
    public static int BASE_STAT_TOTAL_DISPLAY_THRESHHOLD = 580;
    public static int MOVE_QUANTITY_DISPLAY_THRESHOLD = 5;
    public static int BASE_SPEED_MAXIMIZER_THRESHHOLD = 80;
    public static boolean DISPLAY_BATTLE_TEXT = true;
    public static boolean PLAY_SOUND = true;

    static enum Item {
        MAX_ELIXIR((mon) -> {
            int maxHP = mon.statToValue.get(Stat.HP);
            boolean isUsed = false;
            for (Attack a: mon.attackToPP.keySet()) {
                if (mon.attackToPP.get(a) != a.basePP) {
                    mon.attackToPP.put(a, a.basePP);
                    isUsed = true;
                }
            }
            if (!isUsed) {
                System.out.println(mon.name + " already has full pp for every attack!");
            }
            return isUsed;
        }),
        MAX_POTION((mon) -> {
            int maxHP = mon.statToValue.get(Stat.HP);
            if (mon.currentHP == 0 || mon.currentHP == maxHP) {
                System.out.println("A max potion can't be used on " + mon.name + "; it has "
                    + (mon.currentHP == 0 ? "no": "full") + " health.");
                return false;
            }
            mon.currentHP = maxHP;
            return true;
        }),
        POTION((mon) -> {
            int maxHP = mon.statToValue.get(Stat.HP);
            if (mon.currentHP == 0 || mon.currentHP == maxHP) {
                System.out.println("A potion can't be used on " + mon.name + "; it has "
                    + (mon.currentHP == 0 ? "no": "full") + " health.");
                return false;
            }
            mon.currentHP = maxHP - mon.currentHP >= maxHP / 2 ? mon.currentHP + maxHP / 2 : maxHP;
            return true;
        }),
        REVIVE((mon) -> {
            int maxHP = mon.statToValue.get(Stat.HP);
            if (mon.currentHP != 0) {
                System.out.println("This pokemon is already alive!");
                return false;
            }
            mon.currentHP = maxHP / 2;
            return true;
        });

        private static interface UseItem {
            boolean heal(Pokemon mon);
        }

        private final UseItem useFunc;

        public boolean use(Pokemon mon) {
            return this.useFunc.heal(mon);
        }

        Item(UseItem u) {
            this.useFunc = u;
        }
    }

    /*
     * Create a Pokemon by using a predefined enumeration from PokemonTemplate
     */
    public Pokemon(PokemonTemplate poke) {
        this(poke.name(), poke.type1, poke.type2, poke.baseStats, poke.attacks);
    }

    /*
     * Create a Pokemon from a name. The name must match a PokemonTemplate or else it will create a default 
     * (the stat maximizer prefix at the beginning is permitted as this is used to maximize stats)
     */
    public Pokemon(String name) {
        boolean maximizeStats = false;
        PokemonTemplate p;
        try {
            if (name.startsWith(STAT_MAXIMIZER_PREFIX)) {
                maximizeStats = true;
                name = name.substring(1, name.length());
            }
            p = PokemonTemplate.valueOf(name.toUpperCase());
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
     * Create a Pokemon by providing necessary parameters. This allows us to create custom Pokemon not listed in PokemonTemplate
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
     * Determines which of this Pokemons attacks is best
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
            throw new IllegalStateException(this.name + " does not know the attack " + attack);
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
                display(Color.ANSI_CYAN +  "It's super effective!" + Color.ANSI_RESET + "\n");
            }
            else if (effectiveness < 1 && effectiveness > 0) {
                if (PLAY_SOUND) {new AePlayWave(AePlayWave.NOT_EFFECTIVE, AePlayWave.DEFAULT_BUFFER_SIZE).start();}
                display(Color.ANSI_RED +  "It's not very effective.." + Color.ANSI_RESET + "\n");
            }
            else if (effectiveness == 0) {
                display(Color.ANSI_PURPLE +  opponent.name + " is unaffected!" + Color.ANSI_RESET + "\n");
            }
            else {
                if (PLAY_SOUND) {new AePlayWave(AePlayWave.NORMAL_EFFECTIVE, AePlayWave.DEFAULT_BUFFER_SIZE).start();}
                display(opponent.name + " was hit\n");
            }

            //Scale factor includes critical hits and random scaling between 85-100%
            double scaleFactor = effectiveness == 0 ? 0: 1;
            if (effectiveness != 0 && Math.random() < CRITICAL_HIT_PROBABILITY) {
                display(Color.ANSI_YELLOW + "It's a critical hit!" + Color.ANSI_RESET + "\n");
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
        display(opponent.name + " has "
            + Color.severityColor(opponent.currentHP, opponent.getStat(Stat.HP))
            + opponent.currentHP + " hp" + Color.ANSI_RESET + " left\n"
            + (opponent.currentHP <= 0 ? Color.ANSI_RED_HIGHLIGHT + opponent.name
                + " fainted!" + Color.ANSI_RESET + "\n\n": "\n"));
    }

    /*
     * Calculates the damage the attack will do on the opponent, not factoring in critical hits and
     * random pertubation
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

        double damageDealt = (((2.0 * LEVEL + 10.0) / 250.0 * this.statToValue.get(offensiveStat) 
            / opponent.statToValue.get(defensiveStat) 
            * att.baseDamage + 2.0) 
            * att.type.getEffectiveness(this.type1, this.type2, opponent.type1, opponent.type2));
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
     * Returns the number of remaining uses for the given Attack, or 0 if this 
     * Pokemon does not know the Attack
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
        int maxHP = this.statToValue.get(Stat.HP);
        sb.append("Current HP: "
            + Color.severityColor(this.currentHP, maxHP)
            + this.currentHP + Color.ANSI_RESET + "\n");
        sb.append("Stats: ");

        //Get length of longest stat name for formatting purposes
        int longestNameLen = 0;
        for (Stat s: Stat.values()) {
            longestNameLen = longestNameLen < s.name().length() ? s.name().length(): longestNameLen;
        }
        for (Stat s: statToValue.keySet()) {
            sb.append(String.format("%-" + (longestNameLen + 5) + "s  ", s.name() + "("
                + statToValue.get(s) + ")"));
        }
        sb.append('\n');
        sb.append("Attacks: ");

        //Get length of longest attack name for formatting purposes
        longestNameLen = 0;
        for (Attack a: Attack.values()) {
            longestNameLen = longestNameLen < a.name().length() ? a.name().length(): longestNameLen;
        }
        for (Attack a: attackToPP.keySet()) {
            int pp = this.attackToPP.get(a);
            sb.append(String.format("%-" + (longestNameLen + 4) + "s  ", a.name() + " "
                + Color.severityColor(pp, a.basePP)
                + pp + Color.ANSI_RESET + "/" + a.basePP));
        }
        sb.append('\n');
        return sb.toString();
    }

    /*
     * Helper method for the constructors
     */
    private void createInstanceMappings(boolean maximizeStats, int[] baseStats, EnumSet<Attack> attacks) {
        calculateStats(maximizeStats, baseStats);
        for (Attack a: attacks) {
            this.attackToPP.put(a, a.basePP);
        }
        this.currentHP = statToValue.get(Stat.HP);
    }

    /*
     * Helper method for the constructors
     */
    private void calculateStats(boolean maximizeStats, int[] baseStats) {
        assert baseStats.length == Stat.numberOfStats(): "Attempted to create " + baseStats.length + " stats when " 
            + Stat.numberOfStats() + " are required";

        boolean maximizeAttack = baseStats[Stat.ATTACK.ordinal()] >= baseStats[Stat.SPECIAL_ATTACK.ordinal()];
        boolean maximizeSpeed = baseStats[Stat.SPEED.ordinal()] >= BASE_SPEED_MAXIMIZER_THRESHHOLD
            || baseStats[Stat.HP.ordinal()] == 1; //no EVs should be allocated defensively if base HP is 1

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
            //Putting remaining EVs in speed prevents one of the defensive stats from becoming too large
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

}
