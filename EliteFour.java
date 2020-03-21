import java.util.Scanner;
import java.util.EnumMap;
import java.util.Map;
import java.util.ArrayList;

enum Item {
    FULL_RESTORE, REVIVE, POTION;
}


public class EliteFour {
    public static void main(String[] args) {
        Map<Item, Integer> myItems = new EnumMap<>(Item.class);
        myItems.put(Item.FULL_RESTORE, 1);
        myItems.put(Item.REVIVE, 1);
        myItems.put(Item.POTION, 2);
        Map<Type, Boolean> battleRooms = generateRooms();
        postBattleMenu(new Pokemon[]{new Pokemon("_charizard")}, myItems, battleRooms);
        
        

    }

    public static void postBattleMenu(Pokemon[] party, Map<Item, Integer> myItems, Map<Type, Boolean> battleRooms) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Do you want to (0)Heal or (1)Continue");
        int choice = TrainerBattle.getIntFromInput(scan, 0, 1);
        if (choice == 0) {
            int counter = 0;
            System.out.print("Choose item: ");
            for (Item i : myItems.keySet()) {
                System.out.print(counter + "|" + i.toString() + " x" + myItems.get(i) + "|        ");
                counter++;
            }
            System.out.print(counter + "|GO BACK|");
            System.out.println();
            int item = TrainerBattle.getIntFromInput(scan, 0, counter);
            if (item < counter) {
                System.out.println("Which Pokemon Do You Heal\n");
                for (int i = 0; i < party.length; i++) {
                    System.out.println(party[i] + "       ");
                }
            }
            System.out.println();
        }

        else if (choice == 1) {
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

        }

        else {

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
