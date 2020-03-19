import java.util.EnumSet;

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
        //Same Type Attack Bonus (STAB)
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
        assert scaleFactor >= 0: "Attack effectiveness should never be negative";
        return scaleFactor;
    }

}
