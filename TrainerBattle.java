import java.util.Scanner;
import java.util.EnumSet;
import java.util.EnumMap;
//TODO prevent switch if only one Pokemon remaining
/*
 * A class to simulate a Trainer battle using Pokemon objects
 */
class TrainerBattle {

    public static final int PARTY_SIZE = 3;

    public static void main(String[] args) {
        battle();
//        testCode();
//        practice();
    }

    static void battle() {
        if (PARTY_SIZE < 1) {
            throw new IllegalStateException("Party size must be a positive integer");
        }
        Scanner scan = new Scanner(System.in);
        AePlayWave battleMusic = Pokemon.intro(scan, AePlayWave.BATTLE_MUSIC_PETIT_CUP, AePlayWave.PETIT_CUP_BUFFER_SIZE);
        Pokemon.displayPokemon();
        Pokemon[] playerParty = new Pokemon[PARTY_SIZE];
        for (int i = 0; i < playerParty.length; i++) {
            System.out.println("Enter pokemon " + (i + 1));
            playerParty[i] = Pokemon.askForPokemon(scan);
            System.out.println(playerParty[i].toString());
        }
        
        System.out.println("The computer is generating your opponent...");
        System.out.println("Select difficulty between 1 and " + (TrainerAI.getMaxDifficulty() + 1));
        TrainerAI.setCurrentDifficulty(getIntFromInput(scan, 1, Integer.MAX_VALUE));
        System.out.print((TrainerAI.getCurrentDifficulty() - 1 >= TrainerAI.getMaxDifficulty() ? "The Pokemon Master " 
            + "would like to battle you!\n":""));
        TrainerAI opponent = new TrainerAI(PARTY_SIZE);
        int turn = 1;
        Pokemon playerPokemon = playerParty[0];
        Pokemon opponentPokemon = opponent.getNextPokemon(playerPokemon);
        System.out.println("\nReady for battle! The player sends " + playerPokemon.name + " with HP:" + playerPokemon.getCurrentHP() 
            + ". His opponent sends " + opponentPokemon.name + " with HP:" + opponentPokemon.getCurrentHP() + ". (press enter)");
        new AePlayWave("cries/" + playerPokemon.name + ".wav", AePlayWave.DEFAULT_BUFFER_SIZE).start();
        new AePlayWave("cries/" + opponentPokemon.name + ".wav", AePlayWave.DEFAULT_BUFFER_SIZE).start();
        scan.nextLine();

        while (playerPokemon != null && opponentPokemon != null) {
            while (playerPokemon.getCurrentHP() > 0 && opponentPokemon.getCurrentHP() > 0){
                System.out.println("Turn " + (turn++) + " ---------------------------");
                System.out.println("Press: (0) fight, (1) switch");
                int choice = getIntFromInput(scan, 0, 1);
                if (choice == 0) {
                    Pokemon.doTurn(playerPokemon, opponentPokemon);
                }
                else if (choice == 1) {
                    Attack attack = opponentPokemon.getBestAttack(playerPokemon);
                    playerPokemon = playerChooseNextPokemon(scan, playerParty, playerPokemon);
                    opponentPokemon.useAttack(attack, playerPokemon);
                }
                else {
                    throw new IllegalStateException("Invalid choice");
                }
            }
            if (playerPokemon.getCurrentHP() == 0) {
                playerPokemon = playerChooseNextPokemon(scan, playerParty, playerPokemon);
            }
            if (opponentPokemon.getCurrentHP() == 0) {
                opponentPokemon = opponent.getNextPokemon(playerPokemon);
                if (opponentPokemon != null) {
                    System.out.println("The opponent sent out " + opponentPokemon.name + "\n");
                    new AePlayWave("cries/" + opponentPokemon.name + ".wav", AePlayWave.DEFAULT_BUFFER_SIZE).start();
                }
            }
        }
        assert (playerPokemon == null || opponentPokemon == null) && (playerPokemon != opponentPokemon): "Both trainers cannot lose";
        if (battleMusic != null){
            battleMusic.quit();
        }
        System.out.println("The " + (playerPokemon == null ? "opponent trainer": "player") + " has won the battle!");
    }
    
    static Pokemon playerChooseNextPokemon(Scanner scan, Pokemon[] playerParty, Pokemon currentPokemon) {
        boolean allDead = true;
        for (int i = 0; i < playerParty.length; i++) {
            if (playerParty[i].getCurrentHP() > 0) {
                System.out.println("Enter " + i + " to choose " + playerParty[i].toString());
                allDead = false;
            }
        }

        if (allDead) {
            return null;
        }
        else {
            int chosenIndex;
            boolean invalidInput;
            do {
                invalidInput = false;
                chosenIndex = getIntFromInput(scan, 0, playerParty.length - 1);
                if (playerParty[chosenIndex].getCurrentHP() <= 0) {
                    invalidInput = true;
                    System.out.println("That Pokemon is unable to fight!");
                }
                else if (playerParty[chosenIndex] == currentPokemon) {
                    invalidInput = true;
                    System.out.println("That Pokemon is already on the field!");
                }
            } while (invalidInput);
            System.out.println("The player sent out " + playerParty[chosenIndex].name + "\n");
            new AePlayWave("cries/" + playerParty[chosenIndex].name + ".wav", AePlayWave.DEFAULT_BUFFER_SIZE).start();
            return playerParty[chosenIndex];
        }
    }

    //Lower and upper bounds are inclusive
    protected static int getIntFromInput(Scanner scan, int lowerBound, int upperBound) {
        assert lowerBound <= upperBound : "Lower bound must not be greater than upper bound";
        int input;
        boolean invalidInput;
        do {
            while (!scan.hasNextInt()) {
                System.out.println("Only integers are allowed. Try again");
                scan.nextLine();
            }
            input = scan.nextInt();
            scan.nextLine();
            invalidInput = input < lowerBound || input > upperBound;
            if (invalidInput) {
                System.out.println("Choice is out of the valid range. Try again");
            }
        } while (invalidInput);
        return input;
    }

    static void testCode() {
        Scanner scan = new Scanner(System.in);

        //Makes a few pokemon
        System.out.println("\nLets make some pokemon\n");
        
        //--------------------------------------------------------------------------------
        //Here are three different ways of making a pokemon - firstly you can just use a string name. 
        //If you use this method, the name must exist as an enum or else it will default to magikarp
        Pokemon p1 = new Pokemon("_steelix"); //the underscore at the beginning is a secret that maxes out IVs - this Pokemon is way 
                                              //better than any random one
        System.out.println(p1.toString()); //Calling toString on a pokemon prints out the info for it
        scan.nextLine(); //Press enter to proceed

        Pokemon p2 = new Pokemon(PokemonEnum.RAICHU); //We can use a pre-made PokemonEnum to create a Pokemon
        System.out.println(p2.toString());
        scan.nextLine(); //Press enter to proceed

        //We can create a custom pokemon. Notice that if we call this constructor, its name, stats, and attacks can be totally made up
        //Side note - remember that base stats are different than the ACTUAL stats at level 50. Base stats are just used to compute actual stats
        Pokemon p3 = new Pokemon("_primal_groudon", Type.NORMAL, Type.ELECTRIC, new int[]{10, 10, 10, 10, 10, 10}, EnumSet.of(Attack.DRILL_PECK)); 
        System.out.println(p3.toString());
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        //Use some methods to get info on Pokemon
        System.out.println("Lets get some info on " + p1.name);

        Attack bestAttack = p1.getBestAttack(p2); //Returns the Attack that is best against this opponent

        System.out.println(p1.name + "'s best attack against " + p2.name + " is " + bestAttack.name()); //bestAttack is an Attack enum. 
                                                                                                        //Call name() on an enum to get it as a String
        
        double damage = p1.attackDamage(bestAttack, p2); //Computes damage of using the attack
        
        System.out.println("Approximate damage of " + bestAttack.name() + " on " + p2.name + ": " + damage + "\n");
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        //A typical turn
        System.out.println("A typical turn-------------\n");
        Pokemon.doTurn(p1, p2); //Both Pokemon attack each other. The Pokemon with higher speed goes first
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("Enter the name of a pokemon \n"
            + "(Or type \"custom\" to make your own pokemon like latios or arceus)\n"
            + "(Even if the pokemon enum does not exist in the code, it will still make a cry if it "
            + "is spelled correctly)\n(The actual stats of a custom pokemon do NOT have to be authentic)");
        Pokemon userPoke = Pokemon.askForPokemon(scan); //Use this method to ask user to enter a pokemon name
        System.out.println("\nHere is the pokemon you chose:\n" + userPoke.toString());
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("\nNow lets use your pokemon to attack (hit enter)");
        scan.nextLine(); //Press enter to proceed
        Attack myAttack = userPoke.getBestAttack(p3);
        //The useAttack method does a single attack against an opponent - it does not initiate a whole turn so the opponent won't hit back
        userPoke.useAttack(myAttack, p3);
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("We can check the opponents health --> " + p3.getCurrentHP());
        scan.nextLine(); //Press enter to proceed

        //--------------------------------------------------------------------------------
        System.out.println("To display a list of all non-hidden pokemon use the displayPokemon() method");
        //This is a static method so we invoke it with Pokemon.method(), not variable.method()
        //This makes sense - displaying a list of all Pokemon should be a behavior of the whole class, not an instance of the class
        Pokemon.displayPokemon();
    }

    static void practice() {
        Scanner scan = new Scanner(System.in);

        Pokemon p1 = new Pokemon(PokemonEnum.CHARIZARD);
        System.out.println(p1.toString());
        scan.nextLine();

        Pokemon p2 = new Pokemon("_ARCANINE", Type.FIRE, null, new int[]{5,5,5,5,5,5}, EnumSet.of(Attack.THUNDERBOLT));
        System.out.println(p2.toString());
        scan.nextLine();

        Pokemon.doTurn(p1, p2);
        scan.nextLine();

        System.out.println("Press enter to stop the music (it may delay a few seconds before stopping - use \"command C\" to stop immediately)");
        AePlayWave battleMusic = new AePlayWave(AePlayWave.BATTLE_MUSIC_PETIT_CUP, AePlayWave.PETIT_CUP_BUFFER_SIZE);
        battleMusic.start();
        scan.nextLine();
        battleMusic.quit();
    }

}
