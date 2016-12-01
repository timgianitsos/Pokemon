import java.util.*;

//Pokemon have a single type
public class Pokemon {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Pokemon p1;
		Pokemon p2;
		boolean skipSteps = false;
		if (args!= null && args.length >= 2) {
			p1 = new Pokemon(PokemonEnum.valueOf(args[0].toUpperCase()));
			p2 = new Pokemon(PokemonEnum.valueOf(args[1].toUpperCase()));
			skipSteps = args.length >= 3 && args[2].equalsIgnoreCase("skip");
			System.out.println("\n" + p1.name + " vs " + p2.name + "\n");
		}
		else {
			System.out.println("\nAvailable Pokemon");
			for (PokemonEnum poke: PokemonEnum.values()) {
				System.out.printf("%-12s Type:%-12s HP:%-5d Attacks:", poke.name(), poke.type.name(), poke.hp);
				for (Attack a: poke.attacks) {
					System.out.print(a.name() + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Choose player 1's Pokemon");
			p1 = new Pokemon(PokemonEnum.valueOf(scan.nextLine().trim().toUpperCase()));
			System.out.println("Choose player 2's Pokemon");
			p2 = new Pokemon(PokemonEnum.valueOf(scan.nextLine().trim().toUpperCase()));
			System.out.println();
		}

		boolean keepBattling = true;
		while (keepBattling) {
			Attack p1Attack = null;
			double bestDamage = -1;
			for (Attack a: p1.attackToPP.keySet()) {
				if (p1.attackToPP.get(a) > 0 && a.damage * a.type.getScaleFactor(p2.type) > bestDamage) {
					p1Attack = a;
					bestDamage = a.damage * a.type.getScaleFactor(p2.type);
				}
			}
			keepBattling = !p1.useAttack(p1Attack, p2);
			if (!keepBattling) {
				continue;
			}
			if (!skipSteps) {
				scan.nextLine();
			}

			Attack p2Attack = null;
			bestDamage = -1;
			for (Attack a: p2.attackToPP.keySet()) {
				if (p2.attackToPP.get(a) > 0 && a.damage * a.type.getScaleFactor(p1.type) > bestDamage) {
					p2Attack = a;
					bestDamage = a.damage * a.type.getScaleFactor(p1.type);
				}
			}
			keepBattling = !p2.useAttack(p2Attack, p1);
			if (!keepBattling) {
				continue;
			}
			if (!skipSteps) {
				scan.nextLine();
			}
		}
	}
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
			boolean criticalHit = Math.random() < 0.1;
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
	GRASS, 
	FIRE, 
	WATER, 
	FLYING, 
	ROCK, 
	GHOST, 
	FIGHTING, 
	PSYCHIC, 
	NORMAL, 
	DRAGON, 
	STEEL, 
	NONE;

	//Offensive Type attributes
	private EnumSet<Type> superEffective;
	private EnumSet<Type> notVeryEffective;
	private EnumSet<Type> noEffect;

	static {
		NORMAL.superEffective = EnumSet.noneOf(Type.class);
		NORMAL.notVeryEffective = EnumSet.of(ROCK, STEEL);
		NORMAL.noEffect = EnumSet.of(GHOST);

		GRASS.superEffective = EnumSet.of(WATER, ROCK);
		GRASS.notVeryEffective = EnumSet.of(GRASS, FIRE, FLYING, DRAGON, STEEL);
		GRASS.noEffect = EnumSet.noneOf(Type.class);

		FIRE.superEffective = EnumSet.of(GRASS, STEEL);
		FIRE.notVeryEffective = EnumSet.of(FIRE, WATER, ROCK, DRAGON);
		FIRE.noEffect = EnumSet.noneOf(Type.class);

		WATER.superEffective = EnumSet.of(FIRE, ROCK);
		WATER.notVeryEffective = EnumSet.of(GRASS, WATER, DRAGON);
		WATER.noEffect = EnumSet.noneOf(Type.class);

		FLYING.superEffective = EnumSet.of(GRASS, FIGHTING);
		FLYING.notVeryEffective = EnumSet.of(ROCK, STEEL);
		FLYING.noEffect = EnumSet.noneOf(Type.class);

		ROCK.superEffective = EnumSet.of(FIRE, FLYING);
		ROCK.notVeryEffective = EnumSet.of(STEEL, FIGHTING);
		ROCK.noEffect = EnumSet.noneOf(Type.class);

		GHOST.superEffective = EnumSet.of(GHOST, PSYCHIC);
		GHOST.notVeryEffective = EnumSet.noneOf(Type.class);
		GHOST.noEffect = EnumSet.of(NORMAL);

		FIGHTING.superEffective = EnumSet.of(ROCK, NORMAL, STEEL);
		FIGHTING.notVeryEffective = EnumSet.of(FLYING, PSYCHIC);
		FIGHTING.noEffect = EnumSet.of(GHOST);

		PSYCHIC.superEffective = EnumSet.of(FIGHTING);
		PSYCHIC.notVeryEffective = EnumSet.of(PSYCHIC, STEEL);
		PSYCHIC.noEffect = EnumSet.noneOf(Type.class);

		DRAGON.superEffective = EnumSet.of(DRAGON);
		DRAGON.notVeryEffective = EnumSet.of(STEEL);
		DRAGON.noEffect = EnumSet.noneOf(Type.class);

		STEEL.superEffective = EnumSet.of(ROCK);
		STEEL.notVeryEffective = EnumSet.of(FIRE, WATER, STEEL, FIGHTING);
		STEEL.noEffect = EnumSet.noneOf(Type.class);

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
