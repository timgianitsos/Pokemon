import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Comparator;

class TrainerAI {
    //TODO consider more granularity with disabling 
    //TODO simulation method to analyze correctness
    //Array of PokemonTemplates that is sorted by ascending difficulty as determined by the simulation
    private static final PokemonTemplate[] ascendingDifficulty = PokemonTemplate.values();
    //Mapping from a PokemonTemplate to a set of PokemonTemplates that it performed the worst against
    private static final EnumMap<PokemonTemplate, EnumSet<PokemonTemplate>> pokeToWorstMatchup = new EnumMap<>(PokemonTemplate.class);
    private static final int SIMULATIONS_PER_POKEMON = 500;
    public static final int MAX_DIFFICULTY = 3;
    private Pokemon[] party;

    /*
     * Runs simulations to collect data about matchups
     */
    static {
        assert MAX_DIFFICULTY >= 1: "The maximum difficulty must be positive";
        boolean oldDisplayTextSetting = Pokemon.DISPLAY_BATTLE_TEXT;
        boolean oldPlaySoundSetting = Pokemon.PLAY_SOUND;
        Pokemon.DISPLAY_BATTLE_TEXT = false;
        Pokemon.PLAY_SOUND = false;

        EnumMap<PokemonTemplate, Integer> pokeToWins = new EnumMap<>(PokemonTemplate.class);
        EnumMap<PokemonTemplate, Integer> pokeToFewestWinsMatchup = new EnumMap<>(PokemonTemplate.class);
        for (int p1Index = 0; p1Index < PokemonTemplate.numberOfPokemonTemplates() - 1; p1Index++) {
            PokemonTemplate p1Enum = PokemonTemplate.getPokemonTemplateAtIndex(p1Index);
            Pokemon p1 = new Pokemon(Pokemon.STAT_MAXIMIZER_PREFIX + p1Enum.name());
            for (int p2Index = p1Index + 1; p2Index < PokemonTemplate.numberOfPokemonTemplates(); p2Index++) {
                PokemonTemplate p2Enum = PokemonTemplate.getPokemonTemplateAtIndex(p2Index);
                Pokemon p2 = new Pokemon(Pokemon.STAT_MAXIMIZER_PREFIX + p2Enum.name());
                int p1Wins = 0;
                int p2Wins = 0;
                for (int battleRound = 0; battleRound < SIMULATIONS_PER_POKEMON; battleRound++) {
                    while (p1.getCurrentHP() != 0 && p2.getCurrentHP() != 0) {
                        PokemonBattle.doTurn(p1, p2);
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

                //TODO clean this if possible
                pokeToWins.put(p1Enum, (pokeToWins.get(p1Enum) == null ? 0: pokeToWins.get(p1Enum)) + p1Wins);
                pokeToWins.put(p2Enum, (pokeToWins.get(p2Enum) == null ? 0: pokeToWins.get(p2Enum)) + p2Wins);
                if (pokeToFewestWinsMatchup.get(p1Enum) == null) {
                    pokeToFewestWinsMatchup.put(p1Enum, p1Wins);
                    pokeToWorstMatchup.put(p1Enum, EnumSet.of(p2Enum));
                }
                else {
                    if (p1Wins < pokeToFewestWinsMatchup.get(p1Enum)) {
                        pokeToFewestWinsMatchup.put(p1Enum, p1Wins);
                        pokeToWorstMatchup.get(p1Enum).clear();
                        pokeToWorstMatchup.get(p1Enum).add(p2Enum);
                    }
                    else if (p1Wins == pokeToFewestWinsMatchup.get(p1Enum)) {
                        pokeToWorstMatchup.get(p1Enum).add(p2Enum);
                    }
                }
                if (pokeToFewestWinsMatchup.get(p2Enum) == null) {
                    pokeToFewestWinsMatchup.put(p2Enum, p2Wins);
                    pokeToWorstMatchup.put(p2Enum, EnumSet.of(p1Enum));
                }
                else {
                    if (p2Wins < pokeToFewestWinsMatchup.get(p2Enum)) {
                        pokeToFewestWinsMatchup.put(p2Enum, p2Wins);
                        pokeToWorstMatchup.get(p2Enum).clear();
                        pokeToWorstMatchup.get(p2Enum).add(p1Enum);
                    }
                    else if (p2Wins == pokeToFewestWinsMatchup.get(p2Enum)) {
                        pokeToWorstMatchup.get(p2Enum).add(p1Enum);
                    }
                }
            }
        }

        Arrays.sort(ascendingDifficulty, new Comparator<PokemonTemplate>() {
            public int compare(PokemonTemplate p1, PokemonTemplate p2) {
                return pokeToWins.get(p1).compareTo(pokeToWins.get(p2));
            }
        });
        Pokemon.PLAY_SOUND = oldPlaySoundSetting;
        Pokemon.DISPLAY_BATTLE_TEXT = oldDisplayTextSetting;
    }

    //The default constructor leaves the party blank to signify a "Pokemon Master"
    public TrainerAI() {}

    public TrainerAI(int partySize, int difficulty) {
        //TODO currently omits strongest pokemon from the highest tier because of integer division. Should this be a feature or a bug?
        if (partySize < 1) {
            throw new IllegalArgumentException("Party size must be a positive integer");
        }
        if (difficulty < 1 || difficulty > MAX_DIFFICULTY) {
            throw new IllegalArgumentException("Difficulty must be a positive integer"
                + " less than or equal to " + MAX_DIFFICULTY);
        }
        int pokemonPerDifficultyTier = PokemonTemplate.numberOfPokemonTemplates() / MAX_DIFFICULTY;
        if (pokemonPerDifficultyTier < partySize) {
            throw new IllegalStateException("Cannot create a party of size " + partySize 
            + " because the difficulty granularity is too high - there are not enough unique Pokemon per difficulty"
            + " level. You can either.. \n(1) Decrease the max difficulty\n(2) Increase the number of available Pokemon"
            + "\n(3) Decrease the party size");
        }

        //TODO sound disable and enable into separate function?
        boolean oldDisplayTextSetting = Pokemon.DISPLAY_BATTLE_TEXT;
        boolean oldPlaySoundSetting = Pokemon.PLAY_SOUND;
        Pokemon.DISPLAY_BATTLE_TEXT = false;
        Pokemon.PLAY_SOUND = false;

        //TODO check tier threshold logic through testing
        party = new Pokemon[partySize];

        //Find the minimum index in the difficulty array that correspond to the current difficulty tier
        int minIndex = (difficulty - 1) * pokemonPerDifficultyTier;

        //TODO brainstorm on more efficent random algorithm
        HashSet<Integer> usedIndices = new HashSet<>();
        for (int i = 0; i < this.party.length; i++) {
            int newPokemonIndex;
            do {
                newPokemonIndex = (int)(Math.random() * pokemonPerDifficultyTier) + minIndex;
            } while (usedIndices.contains(newPokemonIndex));
            party[i] = new Pokemon(Pokemon.STAT_MAXIMIZER_PREFIX + ascendingDifficulty[newPokemonIndex].name());
            usedIndices.add(newPokemonIndex);
        }

        Pokemon.PLAY_SOUND = oldPlaySoundSetting;
        Pokemon.DISPLAY_BATTLE_TEXT = oldDisplayTextSetting;
    }

    public TrainerAI(int partySize, Type type) {
        boolean oldDisplayTextSetting = Pokemon.DISPLAY_BATTLE_TEXT;
        boolean oldPlaySoundSetting = Pokemon.PLAY_SOUND;
        Pokemon.DISPLAY_BATTLE_TEXT = false;
        Pokemon.PLAY_SOUND = false;
        //Fill this.party with pokemon of the given type
        this.party = new Pokemon[partySize];
        int partyIndex = 0;
        PokemonTemplate[] mons = PokemonTemplate.values();
        for (int i = 0; i < mons.length && partyIndex < partySize; i++) {
            if (mons[i].type1 == type || mons[i].type2 == type) {
                this.party[partyIndex] = new Pokemon(Pokemon.STAT_MAXIMIZER_PREFIX + mons[i].name());
                partyIndex++;
            }
        }
        if (this.party[this.party.length - 1] == null) {
            throw new IllegalStateException("Not enough Pokemon of type " + type
                + " to fill a party of size " + partySize);
        }

        Pokemon.PLAY_SOUND = oldPlaySoundSetting;
        Pokemon.DISPLAY_BATTLE_TEXT = oldDisplayTextSetting;
    }

    public Pokemon getNextPokemon(Pokemon opponentPokemon) {
        Pokemon result = null;
        if (this.party != null) {
            boolean found = false;
            for (int i = 0; i < party.length && !found; i++) {
                if (party[i].getCurrentHP() != 0) {
                    result = party[i];
                    found = true;
                }
            }
        }
        else {
            //Party is null signifying "Pokemon Master" setting.
            //Generate the best matchup against given opponent.

            boolean oldPlaySoundSetting = Pokemon.PLAY_SOUND;
            Pokemon.PLAY_SOUND = false;
            try {
                //Obtain set of Pokemon that have the best probability of beating the opponent, and choose a random one in the set
                //TODO make sure selects correctly
                EnumSet<PokemonTemplate> choices = pokeToWorstMatchup.get(
                    PokemonTemplate.valueOf(opponentPokemon.name));
                PokemonTemplate pe = null;
                double i = choices.size();
                for (PokemonTemplate choice: choices) {
                    pe = choice;
                    if (Math.random() < 1.0 / i) {
                        break;
                    }
                    i--;
                }
                result = new Pokemon(Pokemon.STAT_MAXIMIZER_PREFIX + pe.name());
            }
            catch (Exception e) {
                //TODO custom opponents with valid names don't get paired with shedinja
                result = new Pokemon(
                    Pokemon.STAT_MAXIMIZER_PREFIX + "shedinja",
                    Type.NONE, null, new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 
                    Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}, EnumSet.allOf(Attack.class)
                );
            }
            Pokemon.PLAY_SOUND = oldPlaySoundSetting;
        }
        return result;
    }
}
