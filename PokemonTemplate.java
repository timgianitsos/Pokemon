import java.util.EnumSet;

enum PokemonTemplate {
    //Add new pokemon here
    ZAPDOS(Type.ELECTRIC, Type.FLYING, new int[]{90,90,85,125,90,100}, EnumSet.of(Attack.THUNDERBOLT, Attack.DRILL_PECK)), 
    DRAGONITE(Type.DRAGON, Type.FLYING, new int[]{91, 134, 95, 100, 100, 80}, EnumSet.of(Attack.DRAGON_CLAW, Attack.AERIAL_ACE)), 
    MEWTWO(Type.PSYCHIC, null, new int[]{106,110,90,154,90,130}, EnumSet.of(Attack.PSYSTRIKE, Attack.AURA_SPHERE)), 
    MEW(Type.PSYCHIC, null, new int[]{100,100,100,100,100,100}, EnumSet.complementOf(EnumSet.of(Attack.STRUGGLE))), 
    SMEARGLE(Type.NORMAL, null, new int[]{55,20,35,20,45,75}, EnumSet.complementOf(EnumSet.of(Attack.STRUGGLE))), 
    TYRANITAR(Type.ROCK, Type.DARK, new int[]{100,134,110,95,100,61}, EnumSet.of(Attack.ROCK_SLIDE, Attack.CRUNCH)), 
    METAGROSS(Type.STEEL, Type.PSYCHIC, new int[]{80, 135, 130, 95, 90, 70}, EnumSet.of(Attack.IRON_HEAD, Attack.PSYCHIC)), 
    RAYQUAZA(Type.DRAGON, Type.FLYING, new int[]{105,150,90,150,90,95}, EnumSet.of(Attack.DRAGON_CLAW, Attack.AERIAL_ACE)), 
    GARCHOMP(Type.DRAGON, Type.GROUND, new int[]{108, 130, 95, 80, 85, 102}, EnumSet.of(Attack.DRAGON_CLAW, Attack.EARTHQUAKE)), 
    REGIGIGAS(Type.NORMAL, null, new int[]{110,160,110,80,110,100}, EnumSet.of(Attack.DIZZY_PUNCH, Attack.BRICK_BREAK)), 
    ARCEUS(Type.NORMAL, null, new int[]{120,120,120,120,120,120}, EnumSet.of(Attack.BODY_SLAM, Attack.EARTHQUAKE)), 
    VICTINI(Type.PSYCHIC, Type.FIRE, new int[]{100, 100, 100, 100, 100, 100}, EnumSet.of(Attack.FLARE_BLITZ, Attack.PSYCHIC)), 
    HYDREIGON(Type.DARK, Type.DRAGON, new int[]{92,105,90,125,90,98}, EnumSet.of(Attack.DARK_PULSE, Attack.DRAGON_PULSE)), 

    MEGA_VENUSAUR(Type.GRASS, Type.POISON, new int[]{80,100,123,122,120,80}, 
    	EnumSet.of(Attack.ENERGY_BALL, Attack.SLUDGE_BOMB, Attack.EARTHQUAKE, Attack.BODY_SLAM)),
    MEGA_CHARIZARD_X(Type.FIRE, Type.DRAGON, new int[]{78, 130, 111, 130, 85, 100}, 
        EnumSet.of(Attack.FLARE_BLITZ, Attack.DRAGON_CLAW, Attack.EARTHQUAKE, Attack.STEEL_WING)), 
    MEGA_CHARIZARD_Y(Type.FIRE, Type.FLYING, new int[]{78, 104, 78, 159, 115, 100}, 
        EnumSet.of(Attack.FLAMETHROWER, Attack.DRAGON_PULSE, Attack.AIR_SLASH, Attack.SOLAR_BEAM)), 
    MEGA_BLASTOISE(Type.WATER, null, new int[]{79,103,120,135,115,78}, 
    	EnumSet.of(Attack.SURF, Attack.ICE_BEAM, Attack.AURA_SPHERE, Attack.DRAGON_PULSE)), 
    MEGA_PIDGEOT(Type.NORMAL, Type.FLYING, new int[]{83, 80, 80, 135, 80, 121}, EnumSet.of(Attack.HYPER_BEAM, Attack.HURRICANE)), 
    MEGA_MEWTWO_X(Type.PSYCHIC, Type.FIGHTING, new int[]{106, 190, 100, 154, 100, 130}, EnumSet.of(Attack.PSYCHO_CUT, Attack.BRICK_BREAK)), 
    MEGA_MEWTWO_Y(Type.PSYCHIC, null, new int[]{106, 150, 70, 194, 120, 140}, EnumSet.of(Attack.PSYSTRIKE, Attack.SHADOW_BALL)), 
    MEGA_SCIZOR(Type.BUG, Type.STEEL, new int[]{70,150,140,65,100,75}, EnumSet.of(Attack.X_SCISSOR, Attack.IRON_HEAD)), 
    PRIMAL_KYOGRE(Type.WATER, null, new int[]{100, 150, 90, 180, 160, 90}, EnumSet.of(Attack.SURF, Attack.ICE_BEAM)), 
    PRIMAL_GROUDON(Type.GROUND, Type.FIRE, new int[]{100, 180, 160, 150, 90, 90}, EnumSet.of(Attack.EARTHQUAKE, Attack.FLAMETHROWER)), 

    VENUSAUR(Type.GRASS, Type.POISON, new int[]{80, 82, 83, 100, 100, 80}, EnumSet.of(Attack.ENERGY_BALL, Attack.SLUDGE_BOMB)), 
    CHARIZARD(Type.FIRE, Type.FLYING, new int[]{78, 84, 78, 109, 85, 100}, EnumSet.of(Attack.FLAMETHROWER, Attack.DRAGON_PULSE, 
        Attack.AIR_SLASH, Attack.SOLAR_BEAM)), 
    BLASTOISE(Type.WATER, null, new int[]{79, 83, 100, 85, 105, 78}, EnumSet.of(Attack.SURF, Attack.ICE_BEAM)), 
    PIDGEOT(Type.NORMAL, Type.FLYING, new int[]{83, 80, 75, 70, 70, 101}, EnumSet.of(Attack.BRAVE_BIRD, Attack.STEEL_WING)), 
    RAICHU(Type.ELECTRIC, null, new int[]{60, 90, 55, 90, 80, 110}, EnumSet.of(Attack.VOLT_TACKLE, Attack.IRON_TAIL)), 
    ALAKAZAM(Type.PSYCHIC, null, new int[]{55, 50, 45, 135, 95, 120}, EnumSet.of(Attack.PSYCHIC)), 
    MACHAMP(Type.FIGHTING, null, new int[]{90, 130, 80, 65, 85, 55}, EnumSet.of(Attack.BRICK_BREAK)), 
    GOLEM(Type.ROCK, Type.GROUND, new int[]{80, 120, 130, 55, 65, 45}, EnumSet.of(Attack.ROCK_SLIDE)), 
    RAPIDASH(Type.FIRE, null, new int[]{65,100,70,80,80,105}, EnumSet.of(Attack.FLARE_BLITZ)), 
    GENGAR(Type.GHOST, Type.POISON, new int[]{60, 65, 60, 130, 75, 110}, EnumSet.of(Attack.SHADOW_BALL)), 
    STARMIE(Type.WATER, Type.PSYCHIC, new int[]{60,75,85,100,85,115}, EnumSet.of(Attack.SURF)), 
    SCYTHER(Type.BUG, Type.FLYING, new int[]{70,110,80,55,80,105}, EnumSet.of(Attack.X_SCISSOR)), 
    ELECTABUZZ(Type.ELECTRIC, null, new int[]{65, 83, 57, 95, 85, 105}, EnumSet.of(Attack.THUNDERBOLT)), 
    MAGMAR(Type.FIRE, null, new int[]{65, 95, 57, 100, 85, 93}, EnumSet.of(Attack.FLAMETHROWER)), 
    SNORLAX(Type.NORMAL, null, new int[]{160, 110, 65, 65, 110, 30}, EnumSet.of(Attack.BODY_SLAM)), 
    CROBAT(Type.POISON, Type.FLYING, new int[]{85,90,80,70,80,130}, EnumSet.of(Attack.CROSS_POISON)), 
    STEELIX(Type.STEEL, Type.GROUND, new int[]{75, 85, 200, 55, 65, 30}, EnumSet.of(Attack.IRON_HEAD)), 
    SCIZOR(Type.BUG, Type.STEEL, new int[]{70,130,100,55,80,65}, EnumSet.of(Attack.X_SCISSOR)), 
    BLISSEY(Type.NORMAL, null, new int[]{255,10,10,75,135,55}, EnumSet.of(Attack.HYPER_BEAM)), 
    AGGRON(Type.STEEL, Type.ROCK, new int[]{70,110,180,60,60,50}, EnumSet.of(Attack.IRON_HEAD)), 
    FLYGON(Type.GROUND, Type.DRAGON, new int[]{80,100,80,80,80,100}, EnumSet.of(Attack.DRAGON_CLAW)), 
    GLALIE(Type.ICE, null, new int[]{80,80,80,80,80,80}, EnumSet.of(Attack.ICE_BEAM)), 
    FLOATZEL(Type.WATER, null, new int[]{85,105,55,85,50,115}, EnumSet.of(Attack.SURF)), 
    SPIRITOMB(Type.GHOST, Type.DARK, new int[]{50, 92, 108, 92, 108, 35}, EnumSet.of(Attack.FOUL_PLAY)), 
    TOGEKISS(Type.FAIRY, Type.FLYING, new int[]{85,50,95,120,115,80}, EnumSet.of(Attack.DAZZLING_GLEAM)), 
    GLACEON(Type.ICE, null, new int[]{65, 60, 110, 130, 95, 65}, EnumSet.of(Attack.ICE_BEAM)), 
    CONKELDURR(Type.FIGHTING, null, new int[]{105,140,95,55,65,45}, EnumSet.of(Attack.BRICK_BREAK)), 
    ZOROARK(Type.DARK, null, new int[]{60,105,60,120,60,105}, EnumSet.of(Attack.DARK_PULSE)), 

    MAGIKARP(Type.WATER, null, new int[]{20, 10, 55, 15, 20, 80}, EnumSet.of(Attack.TACKLE));

    //List of Pokemon that differ from official
    //None

    public final Type type1;
    public final Type type2;
    //TODO consider using byte array since no pokemon has a base stat greater than 255
    protected final int[] baseStats;
    protected final EnumSet<Attack> attacks;
    private static final PokemonTemplate[] PokemonTemplateArray;

    static {
        PokemonTemplateArray = values();
    }

    PokemonTemplate(Type type1, Type type2, int[] baseStats, EnumSet<Attack> attacks) {
        if (type1 == null) {
            throw new IllegalStateException(this.name() + " must have a valid primary type");
        }
        if (type1 == type2) {
            throw new IllegalStateException(this.name() + " may not have two identical types " + type1.name());
        }
        if (baseStats.length != Stat.numberOfStats()) {
            throw new IllegalStateException("Attempted to construct " + this.name() + " with " + baseStats.length 
                + " stats when " + Stat.numberOfStats() + " stats are required.");
        }
        this.type1 = type1;
        this.type2 = type2;
        this.baseStats = baseStats;
        this.attacks = attacks;
    }

    public int getBaseStat(Stat s) {
        return this.baseStats[s.ordinal()];
    }

    /*
     * Returns the PokemonTemplate at the index specified by the explicit ordering of this enum
     */
    public static PokemonTemplate getPokemonTemplateAtIndex(int i) {
        return PokemonTemplateArray[i];
    }

    public static int numberOfPokemonTemplates() {
        return PokemonTemplateArray.length;
    }
}

