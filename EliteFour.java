import java.util.Scanner;
import java.util.EnumMap;
import java.util.Map;
import java.util.ArrayList;

//TODO bug when choosing to use an item and selecting index `playerPartySize`
public class EliteFour {
    static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        final int playerPartySize = 6;
        final int opponentPartySize = 3;
        final Map<Type, Boolean> battleRooms = generateRooms();
        final Map<Pokemon.Item, Integer> myItems = new EnumMap<>(Pokemon.Item.class);
        myItems.put(Pokemon.Item.MAX_POTION, 2);
        myItems.put(Pokemon.Item.REVIVE, 2);
        myItems.put(Pokemon.Item.POTION, 4);
        myItems.put(Pokemon.Item.MAX_ELIXIR, 3);

        final AePlayWave battleMusic = PokemonBattle.intro(scan, AePlayWave.BATTLE_MUSIC_PETIT_CUP, AePlayWave.PETIT_CUP_BUFFER_SIZE);
        PokemonBattle.displayPokemon();
        System.out.println("Choose " + playerPartySize + " pokemon.");
        final Pokemon[] party = new Pokemon[playerPartySize];
        for (int i = 0; i < party.length; i++) {
            party[i] = PokemonBattle.askForPokemon(scan);
        }
        boolean isDefeated = false;
        for (int i = 0; i < battleRooms.size() && !isDefeated; i++) {
            Type nextRoom = battleMenu(party, myItems, battleRooms, isDefeated);
            TrainerAI opponent = new TrainerAI(opponentPartySize, nextRoom);
            isDefeated = !TrainerBattle.battle(scan, party, opponent);
        }
        if (!isDefeated) {
            System.out.println("You have beat the elite four. Now you must face the champion! (Enter)");
            scan.nextLine();
            battleMenu(party, myItems, battleRooms, isDefeated);
        }

        if (battleMusic != null) {
            battleMusic.quit();
        }
    }

    public static Type battleMenu(Pokemon[] party, Map<Pokemon.Item, Integer> myItems, Map<Type, Boolean> battleRooms, Boolean isDefeated) {
        System.out.println("Do you want to (0)Heal or (1)Continue");
        int choice = TrainerBattle.getIntFromInput(scan, 0, 1);
        if (choice == 0) {
            healMenu(party, myItems);
        }

        ArrayList<Type> rooms = new ArrayList<>();
        int counter = 0;
        for (Type room: battleRooms.keySet()) {
            if (!battleRooms.get(room)) {
                rooms.add(room);
                System.out.print(counter + "|" + room + "|    ");
                counter++;
            }
        }
        System.out.println();
        if (counter == 0) {
            TrainerAI champion = new TrainerAI(6, TrainerAI.MAX_DIFFICULTY - 1);
            isDefeated = !TrainerBattle.battle(scan, party, champion);
            if (!isDefeated) {
                System.out.println("Congratulations you won!!! You are the new champion!");
            }
            return null;
        }
        else {
            System.out.println("Which room do you choose?");
            int path = TrainerBattle.getIntFromInput(scan, 0, counter - 1);
            System.out.println("You chose " + rooms.get(path));
            battleRooms.put(rooms.get(path), true);
            return rooms.get(path);
        }
        
    }

    static void healMenu(Pokemon[] party, Map<Pokemon.Item, Integer> myItems) {
        ArrayList<Pokemon.Item> items = new ArrayList<>();
        for (Pokemon.Item i : myItems.keySet()) {
            items.add(i);
        }
        boolean chooseItem = true;
        while (chooseItem) {
            int counter = 0;
            System.out.print("Choose item: ");
            for (Pokemon.Item i : myItems.keySet()) {
                System.out.print(counter + "|" + i.toString() + " x" + myItems.get(i) + "|        ");
                counter++;
            }
            System.out.print(counter + "|GO BACK|");
            System.out.println();
            int itemIndex = TrainerBattle.getIntFromInput(scan, 0, counter);
            if (itemIndex != counter && myItems.get(items.get(itemIndex)) == 0) {
                System.out.println("There are no more " + items.get(itemIndex) + "s to use.");
            }
            else {
                if (itemIndex < counter) {
                    System.out.println("Which Pokemon Do You Heal\n");
                    for (int i = 0; i < party.length; i++) {
                        System.out.println("|" + i + "|" + party[i]);
                    }
                    Pokemon.Item item = items.get(itemIndex);
                    Pokemon mon = party[TrainerBattle.getIntFromInput(scan, 0, party.length)];
                    boolean useItem = item.use(mon);
                    if (useItem) {
                        myItems.put(items.get(itemIndex), myItems.get(items.get(itemIndex)) - 1);
                    }
                }
                else {
                    chooseItem = false;
                }
                System.out.println();
            }
        }
    }

    public static Map<Type, Boolean> generateRooms() {
        Map<Type, Boolean> battleRooms = new EnumMap<>(Type.class);
        for (int i = 0; i < 4; i++) {
            Type type;
            do {
                int ran = (int)(Math.random() * Type.values().length);
                type = Type.values()[ran];
            } while (battleRooms.containsKey(type) || (type == Type.NONE));
            battleRooms.put(type, false);
        }
        return battleRooms;
    }
}
