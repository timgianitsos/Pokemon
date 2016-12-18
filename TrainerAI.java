import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Comparator;

class TrainerAI {

    //Array of PokemonEnums that is sorted by ascending difficulty as determined by the simulation
    private static final PokemonEnum[] ascendingDifficulty;
    //Mapping from a PokemonEnum to a set of PokemonEnums that it performed the worst against
    private static final EnumMap<PokemonEnum, EnumSet<PokemonEnum>> pokeToWorstMatchup = new EnumMap<>(PokemonEnum.class);
    private static final int SIMULATIONS_PER_POKEMON = 500;
    private static int MAX_DIFFICULTY = 3;
    private static int CURRENT_DIFFICULTY = 1;
    private final boolean isPokemonMaster;
    private Pokemon[] party;

    static {
        assert MAX_DIFFICULTY >= 1: "The maximum difficulty must be positive";
        assert CURRENT_DIFFICULTY >= 1: "The current difficulty must be positive";

        boolean oldDisplayTextSetting = Pokemon.DISPLAY_BATTLE_TEXT;
        boolean oldPlaySoundSetting = Pokemon.PLAY_SOUND;
        Pokemon.DISPLAY_BATTLE_TEXT = false;
        Pokemon.PLAY_SOUND = false;
        
        EnumMap<PokemonEnum, Integer> pokeToWins = new EnumMap<PokemonEnum, Integer>(PokemonEnum.class);
        for (int pokeEnumIndex = 0; pokeEnumIndex < PokemonEnum.numberOfPokemonEnums(); pokeEnumIndex++) {
            PokemonEnum pe = PokemonEnum.getPokemonEnumAtIndex(pokeEnumIndex);
            pokeToWins.put(pe, 0);
        }
        for (int i = 0; i < PokemonEnum.numberOfPokemonEnums() - 1; i++) {
            Pokemon p1 = new Pokemon("_" + PokemonEnum.getPokemonEnumAtIndex(i).name());
            for (int j = i + 1; j < PokemonEnum.numberOfPokemonEnums(); j++) {
                int p1Wins = 0;
                int p2Wins = 0;
                for (int k = 0; k < SIMULATIONS_PER_POKEMON; k++) {
                    Pokemon p2 = new Pokemon("_" + PokemonEnum.getPokemonEnumAtIndex(j).name());
                    while (p1.getCurrentHP() != 0 && p2.getCurrentHP() != 0) {
                        Pokemon.doTurn(p1, p2);
                    }
                    if (p1.getCurrentHP() == 0) {
                        assert p2.getCurrentHP() != 0 : "Both combatant Pokemon should not be able to faint";
                        p2Wins++;
                    }
                    else {
                        p1Wins++;   
                    }
                    p1.heal();
                    p2.heal();
                }
                pokeToWins.put(PokemonEnum.getPokemonEnumAtIndex(i), pokeToWins.get(PokemonEnum.getPokemonEnumAtIndex(i)) + p1Wins);
                pokeToWins.put(PokemonEnum.getPokemonEnumAtIndex(j), pokeToWins.get(PokemonEnum.getPokemonEnumAtIndex(j)) + p2Wins);
            }
        }

        ascendingDifficulty = pokeToWins.keySet().toArray(new PokemonEnum[PokemonEnum.numberOfPokemonEnums()]);
        Arrays.sort(ascendingDifficulty, new Comparator<PokemonEnum>() {
            public int compare(PokemonEnum p1, PokemonEnum p2) {
                return pokeToWins.get(p1).compareTo(pokeToWins.get(p2));
            }
        });

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
        int pokemonPerDifficultyTier = PokemonEnum.numberOfPokemonEnums() / MAX_DIFFICULTY;
        if (pokemonPerDifficultyTier < 3) {
            throw new IllegalStateException("Cannot create a party of size " + 3 
            + " because the difficulty granularity is too high - there are not enough unique Pokemon per difficulty"
            + " level. You can either.. \n(1) Decrease the max difficulty\n(2) Increase the number of available Pokemon"
            + "\n(3) Decrease the party size");
        }
        if (CURRENT_DIFFICULTY <= MAX_DIFFICULTY) {
            //TODO check tier threshold logic through testing
            isPokemonMaster = false;
            party = new Pokemon[3];

            //Find the minimum index in the difficulty array that correspond to the current difficulty tier
            int minIndex = (CURRENT_DIFFICULTY - 1) * pokemonPerDifficultyTier;

            //TODO brainstorm on more efficent random algorithm
            HashSet<Integer> usedIndices = new HashSet<>();
            for (int i = 0; i < this.party.length; i++) {
                int newPokemonIndex;
                do {
                    newPokemonIndex = (int)(Math.random() * pokemonPerDifficultyTier) + minIndex;
                } while (usedIndices.contains(newPokemonIndex));
                party[i] = new Pokemon("_" + ascendingDifficulty[newPokemonIndex].name());
                usedIndices.add(newPokemonIndex);
            }
        }
        else {
            //Pokemon master setting - chooses most optimal Pokemon to challenge opponent based on simulations
            isPokemonMaster = true;
        }
    }

    public Pokemon getNextPokemon(Pokemon opponentPokemon) {
        if (!isPokemonMaster) {
            for (int i = 0; i < party.length; i++) {
                if (party[i].getCurrentHP() != 0) {
                    return party[i];
                }
            }
            return null;
        }
        else {
            return new Pokemon(Pokemon.DEFAULT_POKEMON);
        }
    }
}
