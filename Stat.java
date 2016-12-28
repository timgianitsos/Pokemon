import java.util.EnumSet;

enum Stat {
    HP, ATTACK, DEFENCE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED;

    private static final Stat[] statArray;

    static {
        //Only applicable stats are the 6 official stats used in Pokemon
        assert EnumSet.of(HP, ATTACK, DEFENCE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED).equals(EnumSet.allOf(Stat.class)): 
            "Only the stats HP, Attack, Defense, Special Attack, Special Defence, and Speed are allowed";
        statArray = values();
    }

    public static Stat getStatAtIndex(int i) {
        return statArray[i];
    }

    public static int numberOfStats() {
        return statArray.length;
    }
}
