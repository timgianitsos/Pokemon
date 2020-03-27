import java.util.Scanner;
import java.util.EnumMap;
import java.util.Map;
import java.util.ArrayList;

public class EliteFour {
    static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        final int playerPartySize = 6;
        final int opponentPartySize = 3;
        final Map<Type, Boolean> battleRooms = generateRooms();
        final Map<Pokemon.Item, Integer> myItems = new EnumMap<>(Pokemon.Item.class);
        myItems.put(Pokemon.Item.MAX_POTION, 1);
        myItems.put(Pokemon.Item.REVIVE, 1);
        myItems.put(Pokemon.Item.POTION, 4);
        myItems.put(Pokemon.Item.MAX_ELIXIR, 6);

        final AePlayWave battleMusic = PokemonBattle.intro(scan, AePlayWave.BATTLE_MUSIC_PETIT_CUP, AePlayWave.PETIT_CUP_BUFFER_SIZE);
        PokemonBattle.displayPokemon();
        System.out.println("Choose " + playerPartySize + " pokemon.");
        final Pokemon[] party = new Pokemon[playerPartySize];
        for (int i = 0; i < party.length; i++) {
            party[i] = PokemonBattle.askForPokemon(scan);
        }
        boolean isDefeated = false;
        for (int i = 0; i < 4 && !isDefeated; i++) {
            Type nextRoom = postBattleMenu(party, myItems, battleRooms);
            TrainerAI opponent = new TrainerAI(opponentPartySize, nextRoom);
            isDefeated = !TrainerBattle.battle(scan, party, opponent);
        }
        

        if (battleMusic != null){
            battleMusic.quit();
        }
    }

    public static Type postBattleMenu(Pokemon[] party, Map<Pokemon.Item, Integer> myItems, Map<Type, Boolean> battleRooms) {
        System.out.println("Do you want to (0)Heal or (1)Continue");
        int choice = TrainerBattle.getIntFromInput(scan, 0, 1);
        if (choice == 0) {
            boolean chooseItem = true;
            boolean getHeal = false;
            while (chooseItem && !getHeal) {
                int counter = 0;
                System.out.print("Choose item: ");
                for (Pokemon.Item i : myItems.keySet()) {
                    System.out.print(counter + "|" + i.toString() + " x" + myItems.get(i) + "|        ");
                    counter++;
                }
                System.out.print(counter + "|GO BACK|");
                System.out.println();
                int getItem = TrainerBattle.getIntFromInput(scan, 0, counter);
                if (getItem < counter) {
                    System.out.println("Which Pokemon Do You Heal\n");
                    for (int i = 0; i < party.length; i++) {
                        System.out.println("|" + i + "|" + party[i]);
                    }
                    ArrayList<Pokemon.Item> items = new ArrayList<>();
                    for (Pokemon.Item i : myItems.keySet()) {
                        items.add(i);
                    }
                    Pokemon.Item item = items.get(getItem);
                    Pokemon mon = party[TrainerBattle.getIntFromInput(scan, 0, party.length)];
                    getHeal = item.use(mon);
                }
                else {
                    chooseItem = false;
                }
                System.out.println();
            }
            
        }

        ArrayList<Type> rooms = new ArrayList<>();
        System.out.println("Which room do you choose");
        int counter = 0;
        for (Type room: battleRooms.keySet()) {
            if (!battleRooms.get(room)) {
                rooms.add(room);
                System.out.print(counter + "|" + room + "|    ");
                counter++;
            }
        }
        System.out.println();
        int path = TrainerBattle.getIntFromInput(scan, 0, counter - 1);
        System.out.println("You chose " + rooms.get(path));
        battleRooms.put(rooms.get(path), true);
        return rooms.get(path);
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
