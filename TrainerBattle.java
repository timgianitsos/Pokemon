class TrainerBattle {
	public static void main(String[] args) {
		Pokemon p1 = new Pokemon(PokemonEnum.MEGA_CHARIZARD_Y);
		Pokemon p2 = new Pokemon(PokemonEnum.VENUSAUR);
		Pokemon.doTurn(p1, p2);
	}
}

