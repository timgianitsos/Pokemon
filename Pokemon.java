import java.util.*;
import java.io.File;

//TODO improve damage calculation, levels, hidden pokemon, parameterized randomizaetion, consider using REST API, STAB, natures, IVs, EVs
/*
---------Custom Pokemon---------
Paste the following lines when prompted to enter the name of a pokemon


custom
MEGA_CHARIZARD_X FIRE DRAGON
78 130 111 130 85 100
FLAMETHROWER DRAGON_CLAW AIR_SLASH SOLAR_BEAM

custom
MEGA_CHARIZARD_Y FIRE FLYING
78 104 78 159 115 100
FLAMETHROWER DRAGON_CLAW AIR_SLASH SOLAR_BEAM

custom
MEGA_MEWTWO_X PSYCHIC FIGHTING
106 190 100 154 100 130
PSYSTRIKE AURA_SPHERE

custom
MEGA_MEWTWO_Y PSYCHIC
106 150 70 194 120 140
PSYSTRIKE SHADOW_BALL

custom
SHEDINJA NONE
1 90 45 30 30 40
X_SCISSOR, SHADOW_BALL

custom
PRIMAL_KYOGRE WATER
100 150 90 180 160 90
SURF ICE_BEAM

custom
PRIMAL_GROUDON GROUND FIRE
100 180 160 150 90 90
EARTHQUAKE FLAMETHROWER

*/
public class Pokemon {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Pokemon p1;
        Pokemon p2;
        boolean skipSteps = false;
        if (args!= null && args.length >= 2) {
            try {
                p1 = new Pokemon(PokemonEnum.valueOf(args[0].toUpperCase()));
            }
            catch (Exception e) {
                System.out.println("Invalid argument 1. Generating default..");
                p1 = new Pokemon(PokemonEnum.MAGIKARP);
            }
            try {
                p2 = new Pokemon(PokemonEnum.valueOf(args[1].toUpperCase()));
            }
            catch (Exception e) {
                System.out.println("Invalid argument 2. Generating default..");
                p2 = new Pokemon(PokemonEnum.MAGIKARP);
            }
            skipSteps = args.length >= 3 && args[2].equalsIgnoreCase("skip");
        }
        else {
            displayPokemon();
            System.out.println("Choose player 1's Pokemon");
            p1 = askForPokemon(scan);
            System.out.println("Choose player 2's Pokemon");
            p2 = askForPokemon(scan);
        }

        System.out.println("\n" + p1.name + " with HP:" + p1.getCurrentHP() + "   vs   " 
                    + p2.name + " with HP:" + p2.getCurrentHP() + "\n");
        scan.nextLine();
        while (doTurn(scan, p1, p2, skipSteps) && doTurn(scan, p2, p1, skipSteps)) {
        }
    }

    private static void displayPokemon() {
        System.out.println("\nAvailable Pokemon");
        for (PokemonEnum poke: PokemonEnum.values()) {
            System.out.printf("%-12s Type 1:%-12s Type 2:%-12s Attacks:", poke.name(), poke.type1.name(), 
                    (poke.type2 == null ? "": poke.type2.name()));
            for (Attack a: poke.attacks) {
                System.out.print(a.name() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static Pokemon askForPokemon(Scanner scan) {
        String choice = scan.nextLine().trim().toUpperCase();
        if (choice.equals("CUSTOM")) {
            try {
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
            catch (Exception e) {
                System.out.println("Invalid arguments. Generating default..");
                return new Pokemon(PokemonEnum.MAGIKARP);
            }    
        }
        else {
            try {
                return new Pokemon(PokemonEnum.valueOf(choice)); 
            }
            catch (Exception e) {
                System.out.println("Invalid argument. Generating default..");
                return new Pokemon(PokemonEnum.MAGIKARP);
            }
        }
    }

    //return true if opponent Pokemon did NOT faint from the turn
    private static boolean doTurn(Scanner scan, Pokemon p1, Pokemon p2, boolean skipSteps) {
        Attack p1Attack = null;
        double bestDamage = -1;
        for (Attack a: p1.attackToPP.keySet()) {
            double damageFactor = a.baseDamage * a.type.getScaleFactor(p1.type1, p1.type2, p2.type1, p2.type2) * a.baseAccuracy;
            if (p1.attackToPP.get(a) > 0 && damageFactor > bestDamage) {
                p1Attack = a;
                bestDamage = damageFactor;
            }
        }
        boolean keepBattling = !p1.useAttack(p1Attack, p2);
        if (keepBattling && !skipSteps) {
            scan.nextLine(); //Makes user press enter to progress the turn
        }
        return keepBattling;
    }


    //Start of Pokemon Object code
    public final String name;
    public final Type type1;
    public final Type type2;
    public final int level = 50;
    private final EnumMap<Stat, Integer> statToBaseValue = new EnumMap<Stat, Integer>(Stat.class);
    private final EnumMap<Stat, Integer> statToIV = new EnumMap<Stat, Integer>(Stat.class);
    private final EnumMap<Attack, Integer> attackToPP = new EnumMap<Attack, Integer>(Attack.class);
    private int currentHP;

    public Pokemon(PokemonEnum poke) {
        this(poke.name(), poke.type1, poke.type2, poke.baseStats, poke.attacks);
    }

    public Pokemon(String name, Type type1, Type type2, int[] baseStats, EnumSet<Attack> attacks) {
        this.name = name;
        if (new File("cries/" + this.name + ".wav").exists()) {
            new AePlayWave("cries/" + name + ".wav").start();
        }
        this.type1 = type1;
        this.type2 = type2;
        for (int i = 0; i < baseStats.length; i++) {
            this.statToBaseValue.put(Stat.values()[i], baseStats[i]);
        }
        for (Stat s: Stat.values()) {
            this.statToIV.put(s, (int)(Math.random() * 32));
        }
        for (Attack a: attacks) {
            this.attackToPP.put(a, a.basePP);
        }
        currentHP = statToBaseValue.get(Stat.HP) == 1 ? 1: 
                ((2 * statToBaseValue.get(Stat.HP) + statToIV.get(Stat.HP) + 252 / 4) * level / 100) + level + 10;
    }

    public int getBaseStat(Stat s) {
        return this.statToBaseValue.get(s);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public Attack[] getAttacks() {
        Attack[] attks = new Attack[attackToPP.keySet().size()];
        int i = 0;
        for (Attack a: attackToPP.keySet()) {
            attks[i++] = a;
        }
        return attks;
    }

    //Returns the number of pp of the given Attack, or -1 if this Pokemon does not know the Attack
    public int getAttackPP(Attack a) {
        Integer pp = attackToPP.get(a);
        return pp != null ? pp: -1;
    }

    //Returns true if the opponent faints from using the attack
    public boolean useAttack(Attack att, Pokemon opponent) {
        Attack attack;
        if (this.attackToPP.get(att) == null || this.attackToPP.get(att) <= 0) {
            System.out.println(this.name + " has run out of attacks!");
            attack = Attack.STRUGGLE;
        }
        else {
            attack = att;
        }

        System.out.println(this.name + " used " + attack.name());
        if (Math.random() * 100 < attack.baseAccuracy) {
            double scaleFactor = attack.type.getScaleFactor(this.type1, this.type2, opponent.type1, opponent.type2);
            System.out.println(
                scaleFactor >= 2 ? "It's super effective!": 
                scaleFactor < 1 && scaleFactor > 0 ? "It's not very effective..": 
                scaleFactor == 0 ? (opponent.name + " is unaffected!"): 
                (opponent.name + " was hit"));
            if (scaleFactor != 0 && Math.random() < 0.0625) {
                System.out.println("It's a critical hit!");
                scaleFactor *= 2;
            }
            int damageDealt = (int)(attack.baseDamage * scaleFactor);
            opponent.currentHP -= damageDealt <= opponent.currentHP ? damageDealt: opponent.currentHP;
        }
        else {
            System.out.println("The attack missed!");
        }
        if (this.attackToPP.get(att) != null && this.attackToPP.get(att) > 0) {
            this.attackToPP.put(attack, this.attackToPP.get(att) - 1);
        }

        boolean opponentFainted = opponent.currentHP <= 0;
        System.out.println(opponent.name + " has " + opponent.currentHP + " hp left\n" + (opponentFainted ? opponent.name + " fainted!\n": ""));

        return opponentFainted;
    }

}

enum PokemonEnum {
    //Add new pokemon here

    //TODO make legendaries hidden
    MEWTWO(Type.PSYCHIC, null, new int[]{106,110,90,154,90,130}, EnumSet.of(Attack.PSYSTRIKE)), 
    MEW(Type.PSYCHIC, null, new int[]{100,100,100,100,100,100}, EnumSet.allOf(Attack.class)), 
    ZAPDOS(Type.ELECTRIC, Type.FLYING, new int[]{90,90,85,125,90,100}, EnumSet.of(Attack.THUNDER_BOLT, Attack.DRILL_PECK)), 
    REGIGIGAS(Type.NORMAL, null, new int[]{110, 160, 110, 80, 110, 110}, EnumSet.of(Attack.DIZZY_PUNCH)), 

    VENUSAUR(Type.GRASS, Type.POISON, new int[]{80, 82, 83, 100, 100, 80}, EnumSet.of(Attack.ENERGY_BALL, Attack.BODY_SLAM)), 
    CHARIZARD(Type.FIRE, Type.FLYING, new int[]{78, 84, 78, 109, 85, 100}, EnumSet.of(Attack.FLAMETHROWER, Attack.DRAGON_CLAW)), 
    BLASTOISE(Type.WATER, null, new int[]{79, 83, 100, 85, 105, 78}, EnumSet.of(Attack.SURF, Attack.BRICK_BREAK)), 
    PIDGEOT(Type.NORMAL, Type.FLYING, new int[]{83, 80, 75, 70, 70, 101}, EnumSet.of(Attack.DRILL_PECK)), 
    RAICHU(Type.ELECTRIC, null, new int[]{60, 90, 55, 90, 80, 110}, EnumSet.of(Attack.THUNDER_BOLT, Attack.IRON_TAIL)), 
    MACHAMP(Type.FIGHTING, null, new int[]{90, 130, 80, 65, 85, 55}, EnumSet.of(Attack.BRICK_BREAK)), 
    GOLEM(Type.ROCK, Type.GROUND, new int[]{80, 120, 130, 55, 65, 45}, EnumSet.of(Attack.ROCK_SLIDE)), 
    ALAKAZAM(Type.PSYCHIC, null, new int[]{55, 50, 45, 135, 95, 120}, EnumSet.of(Attack.PSYCHIC)), 
    GENGAR(Type.GHOST, Type.POISON, new int[]{60, 65, 60, 130, 75, 110}, EnumSet.of(Attack.SHADOW_BALL)), 
    MAGIKARP(Type.WATER, null, new int[]{20, 10, 55, 15, 20, 80}, EnumSet.of(Attack.SPLASH, Attack.TACKLE)), 
    SNORLAX(Type.NORMAL, null, new int[]{160, 110, 65, 65, 110, 30}, EnumSet.of(Attack.BODY_SLAM)), 
    DRAGONITE(Type.DRAGON, Type.FLYING, new int[]{91, 134, 95, 100, 100, 80}, EnumSet.of(Attack.DRAGON_CLAW)), 
    STEELIX(Type.STEEL, Type.GROUND, new int[]{75, 85, 200, 55, 65, 30}, EnumSet.of(Attack.IRON_HEAD)), 
    GARCHOMP(Type.DRAGON, Type.GROUND, new int[]{108, 130, 95, 80, 85, 102}, EnumSet.of(Attack.DRAGON_CLAW, Attack.EARTHQUAKE)), 
    GLACEON(Type.ICE, null, new int[]{65, 60, 110, 130, 95, 65}, EnumSet.of(Attack.ICE_BEAM));

    public final Type type1;
    public final Type type2;
    protected final int[] baseStats;
    protected final EnumSet<Attack> attacks;

    PokemonEnum(Type type1, Type type2, int[] baseStats, EnumSet<Attack> attacks) {
        if (baseStats.length != Stat.values().length) {
            throw new IllegalStateException("Attempted to construct Pokemon with " + baseStats.length 
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

//TODO Need physical vs special
enum Attack {
    //Add new attacks here
    PSYSTRIKE(100, 100, 10, Type.PSYCHIC), 
    THUNDER(110, 70, 10, Type.ELECTRIC), 
    AURA_SPHERE(90, 100, 20, Type.FIGHTING), 
    TACKLE(40, 100, 35, Type.NORMAL), 
    DIZZY_PUNCH(70, 100, 10, Type.NORMAL), 

    ENERGY_BALL(90, 100, 10, Type.GRASS), 
    FLAMETHROWER(90, 100, 15, Type.FIRE), 
    SURF(90, 100, 15, Type.WATER), 
    THUNDER_BOLT(90, 100, 15, Type.ELECTRIC), 
    ICE_BEAM(90, 100, 10, Type.ICE), 
    SOLAR_BEAM(120, 50, 10, Type.GRASS), 
    DRILL_PECK(80, 100, 20, Type.FLYING), 
    ROCK_SLIDE(75, 90, 10, Type.ROCK), 
    SHADOW_BALL(80, 100, 15, Type.GHOST), 
    BRICK_BREAK(75, 100, 15, Type.FIGHTING), 
    PSYCHIC(90, 100, 10, Type.PSYCHIC), 
    BODY_SLAM(85, 100, 15, Type.NORMAL), 
    DRAGON_CLAW(80, 100, 15, Type.DRAGON),
    IRON_HEAD(80, 100, 15, Type.STEEL), 
    IRON_TAIL(100, 75, 15, Type.STEEL), 
    AIR_SLASH(75, 95, 20, Type.FLYING), 
    EARTHQUAKE(100, 100, 10, Type.GROUND), 
    X_SCISSOR(80, 100, 15, Type.BUG), 
    SPLASH(0, 0, 40, Type.NORMAL), 

    STRUGGLE(30, Integer.MAX_VALUE, -1, Type.NONE);
    
    public final int baseDamage;
    public final int baseAccuracy;
    public final int basePP;
    public final Type type;

    Attack(int damage, int accuracy, int pp, Type type) {
        this.baseDamage = damage;
        this.baseAccuracy = accuracy;
        this.basePP = pp;
        this.type = type;
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

    public double getScaleFactor(Type userType1, Type userType2, Type opponentType1, Type opponentType2) {
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
