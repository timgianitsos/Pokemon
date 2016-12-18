import java.util.EnumMap;
import java.util.EnumSet;

class TrainerAI {

    //Array of PokemonEnums that is sorted by ascending difficulty as determined by the simulation
    private static final PokemonEnum[] ascendingDifficulty = new PokemonEnum[PokemonEnum.numberOfPokemonEnums()];
    //Mapping from a PokemonEnum to a set of PokemonEnums that it performed the worst against
    private static final EnumMap<PokemonEnum, EnumSet<PokemonEnum>> pokeToWorstMatchup = new EnumMap<>(PokemonEnum.class);
    private static final int SIMULATIONS_PER_POKEMON = 100;
    private static int MAX_DIFFICULTY = 3;
    private static int CURRENT_DIFFICULTY = 1;
    private Pokemon[] party;

    static {
        assert MAX_DIFFICULTY >= 1: "The maximum difficulty must be positive";
        assert CURRENT_DIFFICULTY >= 1: "The current difficulty must be positive";

        boolean oldDisplayTextSetting = Pokemon.DISPLAY_BATTLE_TEXT;
        boolean oldPlaySoundSetting = Pokemon.PLAY_SOUND;
        Pokemon.DISPLAY_BATTLE_TEXT = false;
        Pokemon.PLAY_SOUND = false;
        //TODO simulation logic
        Pokemon.PLAY_SOUND = oldPlaySoundSetting;
        Pokemon.DISPLAY_BATTLE_TEXT = oldDisplayTextSetting;
    }

    public static int getMaxDifficulty() {
        return MAX_DIFFICULTY;
    }

    public static void setMaxDifficulty(int newMax) {
        if (newMax <= 0) {
            throw new IllegalStateException("The maximum difficulty must be positive");
        }
        MAX_DIFFICULTY = newMax;
    }

    public static int getCurrentDifficulty() {
        return CURRENT_DIFFICULTY;
    }

    public static void setCurrentDifficulty(int newDifficulty) {
        if (newDifficulty <= 0) {
            throw new IllegalStateException("The current difficulty must be positive");
        }
        CURRENT_DIFFICULTY = newDifficulty;
    }

    public TrainerAI() {
    }

    public Pokemon getNextPokemon(String opponentName) {
        return new Pokemon(Pokemon.DEFAULT_POKEMON);
    }
}
