class TrainerAI {

	public static final int DIFFICULTY_LEVELS = 3;
	public static int CURRENT_DIFFICULTY = 1;

	static {
		assert DIFFICULTY_LEVELS >= 1: "The number of difficulty levels must be positive";
		assert CURRENT_DIFFICULTY >= 1: "The current difficulty must be a positive";
	}

	public TrainerAI() {

	}

	public Pokemon getNextPokemon(String opponentName) {
		return new Pokemon(Pokemon.DEFAULT_POKEMON);
	}
}