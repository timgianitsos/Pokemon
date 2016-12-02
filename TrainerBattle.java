class TrainerBattle {
	public static void main(String[] args) {
		Pokemon p1 = new Pokemon(PokemonEnum.CHARIZARD);
		Pokemon p2 = new Pokemon(PokemonEnum.DRAGONITE);
		p1.useAttack(Attack.DRAGON_CLAW, p2);
	}
}

