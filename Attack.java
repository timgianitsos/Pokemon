enum Attack {
    //Add new attacks here
    SOLAR_BEAM(120, 80, 10, Type.GRASS, false), 
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
    FLARE_BLITZ(120, 80, 15, Type.FIRE, true), 
    BRAVE_BIRD(120, 80, 15, Type.FLYING, true), 
    VOLT_TACKLE(120, 80, 15, Type.ELECTRIC, true), 
    PSYCHO_CUT(70, 100, 20, Type.PSYCHIC, true), 
    FOUL_PLAY(95, 100, 15, Type.DARK, true), 

    TACKLE(40, 100, 35, Type.NORMAL, true), 
    ENERGY_BALL(90, 100, 10, Type.GRASS, false), 
    FLAMETHROWER(90, 100, 15, Type.FIRE, false), 
    SURF(90, 100, 15, Type.WATER, false), 
    THUNDERBOLT(90, 100, 15, Type.ELECTRIC, false), 
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
    CRUNCH(80, 100, 15, Type.DARK, true), 
    CROSS_POISON(70, 100, 20, Type.POISON, true), 

    STRUGGLE(50, 100, 1, Type.NONE, true);

    //List of attacks that differ from official
    //SOLARBEAM (accuracy)
    //AURA_SPHERE (accuracy)
    //HYPER_BEAM (accuracy)
    //AERIAL_ACE (accuracy)
    //FLARE_BLITZ (accuracy)
    //BRAVE_BIRD (accuracy)
    //VOLT_TACKLE (accuracy)
    //FOUL_PLAY (use opponents attack stat)
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
