import java.util.Scanner;
import java.util.EnumSet;
import java.util.EnumMap;
//TODO BUG: we should be able to exit the Pokemon selection menu if switching (but
//not if a Pokemon fainted). Currently, if there is only one Pokemon left and the user 
//tries to switch, to program gets stuck because there is no other Pokemon to switch to
/*
 * A class to simulate a Trainer battle using Pokemon objects
 */
class TrainerBattle {

    static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        final int PARTY_SIZE = 3;
        AePlayWave battleMusic = PokemonBattle.intro(scan, AePlayWave.BATTLE_MUSIC_PETIT_CUP, AePlayWave.PETIT_CUP_BUFFER_SIZE);
        PokemonBattle.displayPokemon();
        Pokemon[] playerParty = new Pokemon[PARTY_SIZE];
        for (int i = 0; i < playerParty.length; i++) {
            System.out.println("Enter pokemon " + (i + 1));
            playerParty[i] = PokemonBattle.askForPokemon(scan);
            System.out.println(playerParty[i].toString());
        }

        System.out.println("Select difficulty between 1 and " + TrainerAI.MAX_DIFFICULTY);
        int difficulty = getIntFromInput(scan, 1, Integer.MAX_VALUE);
        System.out.println("The computer is generating your opponent...");
        TrainerAI opponent = difficulty > TrainerAI.MAX_DIFFICULTY ?
            new TrainerAI():
            new TrainerAI(PARTY_SIZE, difficulty);
        if (difficulty > TrainerAI.MAX_DIFFICULTY) {
            System.out.println(Color.ANSI_RED_HIGHLIGHT
                + "The Pokemon Master would like to battle you!" + Color.ANSI_RESET);
        }
        battle(scan, playerParty, opponent);
        if (battleMusic != null){
            battleMusic.quit();
        }
    }

    static boolean  battle(Scanner scan, Pokemon[] playerParty, TrainerAI opponent) {
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
                    PokemonBattle.doTurn(playerPokemon, opponentPokemon);
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
        assert playerPokemon == null ^ opponentPokemon == null: "Both trainers cannot lose";
        System.out.println((playerPokemon == null ?
            Color.ANSI_RED_HIGHLIGHT + "The opponent trainer has won the battle!":
            Color.ANSI_BLUE_HIGHLIGHT + "The player has won the battle!") + Color.ANSI_RESET);
        return playerPokemon != null;
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

}
