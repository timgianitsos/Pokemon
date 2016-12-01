import java.util.*;

//Pokemon have a single type
public class Pokemon {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Pokemon p1;
		Pokemon p2;
		boolean skipSteps = false;
		if (args!= null && args.length >= 2) {
			try {
				p1 = new Pokemon(PokemonEnum.valueOf(args[0].toUpperCase()));
			}
			catch (Exception e) {
				p1 = new Pokemon("NO_NAME", Type.NORMAL, 1, EnumSet.noneOf(Attack.class));
			}
			try {
				p2 = new Pokemon(PokemonEnum.valueOf(args[1].toUpperCase()));
			}
			catch (Exception e) {
				p2 = new Pokemon("NO_NAME", Type.NORMAL, 1, EnumSet.noneOf(Attack.class));
			}
			skipSteps = args.length >= 3 && args[2].equalsIgnoreCase("skip");
			System.out.println("\n" + p1.name + " vs " + p2.name + "\n");
		}
		else {
			displayPokemon();
			System.out.println("Choose player 1's Pokemon");
			p1 = askForPokemon(scan);
			System.out.println("Choose player 2's Pokemon");
			p2 = askForPokemon(scan);
			System.out.println();
		}

		while (doTurn(scan, p1, p2, skipSteps) && doTurn(scan, p2, p1, skipSteps)) {
		}
	}

	private static void displayPokemon() {
		System.out.println("\nAvailable Pokemon");
		for (PokemonEnum poke: PokemonEnum.values()) {
			System.out.printf("%-12s Type:%-12s HP:%-5d Attacks:", poke.name(), poke.type.name(), poke.hp);
			for (Attack a: poke.attacks) {
				System.out.print(a.name() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static Pokemon askForPokemon(Scanner scan) {
		String choice = scan.nextLine().trim().toUpperCase();
		if (choice.equals("CUSTOM")) {
			System.out.println("Enter name, type, and hp separated by commas or spaces");
			String[] specs = scan.nextLine().split("[,\\s]+");
			String name = specs.length >= 1 && specs[0].length() > 0 ? specs[0].toUpperCase(): "NO_NAME";
			Type type;
			int hp;
			try {
				type = Type.valueOf(specs[1].toUpperCase());
			}
			catch (Exception e) {
				type = Type.NORMAL;
			}
			try {
				hp = Integer.parseInt(specs[2]);
			}
			catch (Exception e) {
				hp = 1;
			}

			System.out.println("Enter attacks separated by commas or spaces");
			String[] stringAttacks = scan.nextLine().split("[,\\s]+");
			EnumSet<Attack> customAttacks = EnumSet.noneOf(Attack.class);
			for (int i = 0; i < stringAttacks.length; i++) {
				try {
					customAttacks.add(Attack.valueOf(stringAttacks[i].toUpperCase()));
				}
				catch (IllegalArgumentException e) {
				}
			}

			return new Pokemon(name, type, hp, customAttacks);
		}
		else {
			Pokemon p;
			try {
				p = new Pokemon(PokemonEnum.valueOf(choice)); 
			}
			catch (Exception e) {
				p = new Pokemon("NO_NAME", Type.NORMAL, 1, EnumSet.noneOf(Attack.class));
			}
			return p;
		}
	}

	//return true if opponent Pokemon did NOT faint from the turn
	private static boolean doTurn(Scanner scan, Pokemon p1, Pokemon p2, boolean skipSteps) {
		Attack p1Attack = null;
		double bestDamage = -1;
		for (Attack a: p1.attackToPP.keySet()) {
			if (p1.attackToPP.get(a) > 0 && a.damage * a.type.getScaleFactor(p2.type) > bestDamage) {
				p1Attack = a;
				bestDamage = a.damage * a.type.getScaleFactor(p2.type);
			}
		}
		boolean keepBattling = !p1.useAttack(p1Attack, p2);
		if (keepBattling && !skipSteps) {
			scan.nextLine(); //Makes user press enter to progress the turn
		}
		return keepBattling;
	}


	//Start of Pokemon Object code
	public final String name;
	public final Type type;
	private int currentHP;
	private final EnumMap<Attack, Integer> attackToPP = new EnumMap<Attack, Integer>(Attack.class);

	public Pokemon(PokemonEnum poke) {
		this.name = poke.name();
		this.type = poke.type;
		this.currentHP = poke.hp;
		for (Attack a: poke.attacks) {
			attackToPP.put(a, a.pp);
		}
	}

	public Pokemon(String name, Type type, int currentHP, EnumSet<Attack> attacks) {
		this.name = name;
		this.type = type;
		this.currentHP = currentHP;
		for (Attack a: attacks) {
			attackToPP.put(a, a.pp);
		}
	}

	public int getHP() {
		return this.currentHP;
	}

	//Returns true if the opponent faints from using the attack
	public boolean useAttack(Attack att, Pokemon opponent) {
		Attack attack;
		if (this.attackToPP.get(att) == null || this.attackToPP.get(att) <= 0) {
			System.out.println(this.name + " has run out of attacks!");
			attack = Attack.STRUGGLE;
		}
		else {
			attack = att;
		}

		System.out.println(this.name + " used " + attack.name());
		if (Math.random() * 100 < attack.accuracy) {
			double scaleFactor = attack.type.getScaleFactor(opponent.type);
			System.out.println(
				scaleFactor == 2 ? "It's super effective!": 
				scaleFactor == 0.5 ? "It's not very effective..": 
				scaleFactor == 0 ? (opponent.name + " is unaffected!"): 
				(opponent.name + " was hit"));
			boolean criticalHit = scaleFactor != 0 && Math.random() < 0.1;
			if (criticalHit) {
				System.out.println("It's a critical hit!");				
			}
			int damageDealt = (int)(attack.damage * scaleFactor * (criticalHit ? 2: 1));
			opponent.currentHP -= damageDealt <= opponent.currentHP ? damageDealt: opponent.currentHP;
		}
		else {
			System.out.println("The attack missed!");
		}
		if (this.attackToPP.get(att) != null && this.attackToPP.get(att) > 0) {
			this.attackToPP.put(attack, this.attackToPP.get(att) - 1);
		}

		boolean opponentFainted = opponent.currentHP <= 0;
		System.out.println(opponent.name + " has " + opponent.currentHP + " hp left\n" + (opponentFainted ? opponent.name + " fainted!\n": ""));

		return opponentFainted;
	}

}

enum PokemonEnum {
	//Add new pokemon here
	MEWTWO(Type.PSYCHIC, 200, EnumSet.of(Attack.PSYSTRIKE)), 

	VENUSAUR(Type.GRASS, 150, EnumSet.of(Attack.ENERGY_BALL, Attack.BODY_SLAM)), 
	CHARIZARD(Type.FIRE, 150, EnumSet.of(Attack.FLAMETHROWER, Attack.DRAGON_CLAW)), 
	BLASTOISE(Type.WATER, 150, EnumSet.of(Attack.SURF, Attack.BRICK_BREAK)), 
	PIDGEOT(Type.FLYING, 100, EnumSet.of(Attack.DRILL_PECK)), 
	GOLEM(Type.ROCK, 135, EnumSet.of(Attack.ROCK_SLIDE)), 
	GENGAR(Type.GHOST, 135, EnumSet.of(Attack.SHADOW_BALL)), 
	MACHAMP(Type.FIGHTING, 135, EnumSet.of(Attack.BRICK_BREAK)), 
	ALAKAZAM(Type.PSYCHIC, 135, EnumSet.of(Attack.PSYCHIC)), 
	SNORLAX(Type.NORMAL, 225, EnumSet.of(Attack.BODY_SLAM)), 
	DRAGONITE(Type.DRAGON, 180, EnumSet.of(Attack.DRAGON_CLAW)), 
	STEELIX(Type.STEEL, 135, EnumSet.of(Attack.IRON_HEAD));

	public final Type type;
	public final int hp;
	public final EnumSet<Attack> attacks;

	PokemonEnum(Type type, int hp, EnumSet<Attack> attacks) {
		this.type = type;
		this.hp = hp;
		this.attacks = attacks;
	}

}

enum Attack {
	//Add new attacks here
	PSYSTRIKE(120, 100, 10, Type.PSYCHIC), 

	ENERGY_BALL(90, 75, 5, Type.GRASS), 
	FLAMETHROWER(90, 75, 5, Type.FIRE), 
	SURF(90, 75, 5, Type.WATER), 
	DRILL_PECK(90, 75, 5, Type.FLYING), 
	ROCK_SLIDE(90, 75, 5, Type.ROCK), 
	SHADOW_BALL(90, 75, 5, Type.GHOST), 
	BRICK_BREAK(90, 75, 5, Type.FIGHTING), 
	PSYCHIC(90, 75, 5, Type.PSYCHIC), 
	BODY_SLAM(90, 75, 5, Type.NORMAL), 
	DRAGON_CLAW(90, 75, 5, Type.DRAGON),
	IRON_HEAD(90, 75, 5, Type.STEEL),
	STRUGGLE(30, 100, -1, Type.NONE);
	
	public final int damage;
	public final int accuracy;
	public final int pp;
	public final Type type;

	Attack(int damage, int accuracy, int pp, Type type) {
		this.damage = damage;
		this.accuracy = accuracy;
		this.pp = pp;
		this.type = type;
	}

}

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

	public double getScaleFactor(Type opponentType) {
		if (superEffective.contains(opponentType)) {
			return 2;
		}
		else if (notVeryEffective.contains(opponentType)) {
			return 0.5;
		}
		else if (noEffect.contains(opponentType)) {
			return 0;
		}
		else {
			return 1;
		}
	}

}
