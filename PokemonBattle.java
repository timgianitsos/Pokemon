import java.util.Scanner;
import java.util.EnumSet;

public class PokemonBattle {

    protected static AePlayWave intro(Scanner scan, String musicFilename, int bufferSize) {
        AePlayWave battleMusic = null;
        double random = Math.random();
        System.out.println(random < 0.2 ? machampString: random < 0.4 ? scytherString: random < 0.6 ? alakazamString: 
            random < 0.8 ? rapidashString: charizardString);
        System.out.println("\nMusic? [y] - yes, [blank] - no");
        String response = scan.nextLine();
        if (response != null && response.length() > 0 && response.charAt(0) == 'y') {
            battleMusic = new AePlayWave(musicFilename, bufferSize);
            battleMusic.start();
        }
        return battleMusic;
    }

    /*
     * Displays a list of non-hidden Pokemon
     */
    public static void displayPokemon() {
        System.out.println("\nAvailable Pokemon");
        for (int pokeEnumIndex = 0; pokeEnumIndex < PokemonTemplate.numberOfPokemonTemplates(); pokeEnumIndex++) {
            PokemonTemplate poke = PokemonTemplate.getPokemonTemplateAtIndex(pokeEnumIndex);
            int baseStatTotal = 0;
            for (int i = 0; i < poke.baseStats.length; i++) {
                baseStatTotal += poke.baseStats[i];
            }
            if (baseStatTotal < Pokemon.BASE_STAT_TOTAL_DISPLAY_THRESHHOLD && !poke.name().contains("MEGA_") && poke.type1 != Type.NONE 
                    && poke.type2 != Type.NONE && poke.attacks.size() < Pokemon.MOVE_QUANTITY_DISPLAY_THRESHOLD) {
                System.out.printf("%-12s Type 1:%-12s Type 2:%-12s Attacks:", poke.name(), poke.type1.name(), 
                        (poke.type2 == null ? "": poke.type2.name()));
                for (Attack a: poke.attacks) {
                    System.out.print(a.name() + " ");
                }
                System.out.println();
                System.out.print("\t\t");
                for (int statIndex = 0; statIndex < Stat.numberOfStats(); statIndex++) {
                    Stat s = Stat.getStatAtIndex(statIndex);
                    System.out.print(String.format("%-19s ", s.name() + ":" + poke.getBaseStat(s)));
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /*
     * Asks the user for a Pokemon and returns it
     */
    public static Pokemon askForPokemon(Scanner scan) {
        try {
            String choice = scan.nextLine().trim().toUpperCase();
            if (choice.equals("CUSTOM")) {
                System.out.println("Enter name, first type, and second type (or leave second type blank if not applicable)" 
                        + " each separated by commas or spaces");
                String[] specs = scan.nextLine().split("[,\\s]+");
                if (specs[0].length() == 0) {
                    throw new Exception();
                }
                String name = specs[0].toUpperCase();
                Type type1 = Type.valueOf(specs[1].toUpperCase());
                Type type2 = specs.length == 3 ? Type.valueOf(specs[2].toUpperCase()): null;

                System.out.println("Enter the " + Stat.numberOfStats() + " base stats separated by commas or spaces");
                Scanner statScan = new Scanner(scan.nextLine());
                statScan.useDelimiter("[,\\s]+");
                int[] customBaseStats = new int[Stat.numberOfStats()];
                for (int i = 0; i < customBaseStats.length; i++) {
                    customBaseStats[i] = statScan.nextInt();
                }

                System.out.println("Enter attacks separated by commas or spaces");
                String[] stringAttacks = scan.nextLine().split("[,\\s]+");
                EnumSet<Attack> customAttacks = EnumSet.noneOf(Attack.class);
                for (int i = 0; i < stringAttacks.length; i++) {
                    customAttacks.add(Attack.valueOf(stringAttacks[i].toUpperCase()));
                }
                return new Pokemon(name, type1, type2, customBaseStats, customAttacks);   
            }
            else {
                return new Pokemon(choice);
            }
        }
        catch (Exception e) {
            System.out.println("Invalid argument. Generating default..");
            return new Pokemon(Pokemon.DEFAULT_POKEMON);
        }
    }

    /*
     * Performs a turn where each Pokemon attacks each other
     */
    public static void doTurn(Pokemon p1, Pokemon p2) {
        if (p1 == null || p2 == null || p1.getCurrentHP() == 0 || p2.getCurrentHP() == 0) {
            throw new IllegalStateException("Combatants must not be null and must have positive HP");
        }
        Pokemon first;
        Pokemon second;
        if (p1.getStat(Stat.SPEED) == p2.getStat(Stat.SPEED)) {
            double randomNumber = Math.random();
            first = randomNumber >= 0.5 ? p1: p2;
            second = randomNumber >= 0.5 ? p2: p1;
        }
        else {
            first = p1.getStat(Stat.SPEED) > p2.getStat(Stat.SPEED) ? p1: p2;
            second = p1.getStat(Stat.SPEED) > p2.getStat(Stat.SPEED) ? p2: p1;
        }
        assert first != second: "Error attempting turn";
        first.useAttack(first.getBestAttack(second), second);
        if (second.getCurrentHP() != 0) {
            second.useAttack(second.getBestAttack(first), first);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Pokemon p1;
        Pokemon p2;
        AePlayWave battleMusic = null;
        boolean pauseEachStep = true;
        if (args!= null && args.length >= 2) {
            pauseEachStep = args.length < 3 || !args[2].equalsIgnoreCase("skip");
            Pokemon.PLAY_SOUND = pauseEachStep;
            if (pauseEachStep) {
                battleMusic = intro(scan, AePlayWave.BATTLE_MUSIC_PRIME_CUP, AePlayWave.PRIME_CUP_BUFFER_SIZE);
            }
            p1 = new Pokemon(args[0].toUpperCase());
            p2 = new Pokemon(args[1].toUpperCase());
        }
        else {
            battleMusic = intro(scan, AePlayWave.BATTLE_MUSIC_PRIME_CUP, AePlayWave.PRIME_CUP_BUFFER_SIZE);
            displayPokemon();
            System.out.println("Choose player 1's Pokemon");
            p1 = askForPokemon(scan);
            System.out.println(p1);
            System.out.println("\nChoose player 2's Pokemon");
            p2 = askForPokemon(scan);
            System.out.println(p2);
        }

        System.out.println("\n" + p1.name + " with HP:" + p1.getCurrentHP() + "   vs   " 
                    + p2.name + " with HP:" + p2.getCurrentHP() + "\n");
        if (pauseEachStep) {
            scan.nextLine();
        }

        int turn = 1;
        while (p1.getCurrentHP() != 0 && p2.getCurrentHP() != 0) {
            System.out.println("Turn " + (turn++) + " ---------------------------");
            doTurn(p1, p2);
            if (pauseEachStep && p1.getCurrentHP() != 0 && p2.getCurrentHP() != 0) {
                scan.nextLine();
            }
        }
        if (battleMusic != null) {
            battleMusic.quit();
        }
    }

    public static final String charizardString =
            "                 .\"-,.__\n                 `.     `.  ,\n"
            + "              .--'  .._,'\"-' `.\n             .    .'         `'\n             "
            + "`.   /          ,'\n               `  '--.   ,-\"'\n                `\"`   |  \\"
            + "\n                   -. \\, |\n                    `--Y.'      ___.\n           "
            + "              \\     L._, \\\n               _.,        `.   <  <\\             "
            + "   _\n             ,' '           `, `.   | \\            ( `\n          ../, `."
            + "            `  |    .\\`.           \\ \\_\n         ,' ,..  .           _.,'   "
            + " ||\\l            )  '\".\n        , ,'   \\           ,'.-.`-._,'  |           "
            + ".  _._`.\n      ,' /      \\ \\        `' ' `--/   | \\          / /   ..\\\n   "
            + " .'  /        \\ .         |\\__ - _ ,'` `        / /     `.`.\n    |  '        "
            + "  ..         `-...-\"  |  `-'      / /        . `.\n    | /           |L__      "
            + "     |    |          / /          `. `.\n   , /            .   .          |    |"
            + "         / /             ` `\n  / /          ,. ,`._ `-_       |    |  _   ,-' /"
            + "               ` \\\n / .           \\\"`_/. `-_ \\_,.  ,'    +-' `-'  _,       "
            + " ..,-.    \\`.\n.  '         .-f    ,'   `    '.       \\__.---'     _   .'   ' "
            + "    \\ \\\n' /          `.'    l     .' /          \\..      ,_|/   `.  ,'`     "
            + "L`\n|'      _.-\"\"` `.    \\ _,'  `            \\ `.___`.'\"`-.  , |   |    | \\"
            + "\n||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|\n||  ,"
            + "'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||\n|| '        "
            + "      V      / /           `   | `   ,'   ,' '.    !  `. ||\n||/            _,--"
            + "-----7 '              . |  `-'    l         /    `||\n. |          ,' .-   ,' ||"
            + "               | .-.        `.      .'     ||\n `'        ,'    `\".'    |      "
            + "         |    `.        '. -.'       `'\n          /      ,'      |             "
            + "  |,'    \\-.._,.'/'\n          .     /        .               .       \\    .''"
            + "\n        .`.    |         `.             /         :_,'.'\n          \\ `...\\ "
            + "  _     ,'-.        .'         /_.-'\n           `-.__ `,  `'   .  _.>----''.  _"
            + "  __  /\n                .'        /\"'          |  \"'   '_\n               /_|"
            + ".-'\\ ,\".             '.'`__'-( \\\n                 / ,\"'\"\\,'              "
            + " `/  `-.|\" mh";
    public static final String machampString =
            "                 __.\"`. .-.                    ,-..__\n    "
            + "          ,-.  \\  |-| |               ,-\"+' ,\"'  `.\n              \\  \\  \\"
            + "_' `.'             .'  .|_.|_.,--'.\n               \\.'`\"     `.              "
            + "`-' `.   .  _,'.\n                \\_     `\"\"\"-.             .\"--+\\   '\"  "
            + " |\n                | `\"\"+..`..,'             `-._ |        |\n               "
            + "j     |                       '.       _/.\n              /   ,' `.      _.----."
            + "_          `\"-.  '   \\\n             |   |     |   ,'  ,.-\"\"\"`.           |"
            + "  .    \\\n    __       |   '    /-._.  ,'        `.         |   \\    \\\n   ( "
            + " `.     `.     .'    | /  _,.-----. \\       j     .    \\\n    `. |.  __  `,   "
            + "      |j ,'\\        `|\"+---._|          ,\n .-\"-|\"' \\\"  |   \". '.    ||/d"
            + " |_-\"\"`.    /     ,'.          )\n `._. |  '.,.'     '  `  ,||_.-\"      |  j "
            + "    '   `        .\n.\"'--:' .  )        `.  (     _.-+    |  |                 "
            + " |\n`-,..'  ` <_          `-.`..+\"   '   ./,  ._         |      |\n `.__|   |  "
            + "`-._     _.-\"`. |   /  ,'j      `. `....' ____..'\n   `-.,.'    \\  `. ,'     ,"
            + "-|_,'  /  |        `.___,-'   )\n      `.      `.  Y       `-..__.',-'    __,.' "
            + "          '\n        `         '   ,--.    |  /            `+\"\"       `.\n    "
            + "     `.       ,--+   '  .-+-\"  _,'   ,--  /     '.    |\n           `-..   \\  "
            + "   __,'           .'    /        `.  |\n               `---)   |  ____,'      ,."
            + "...-'           `,'\n                  '                 ,' _,-----.         /\n"
            + "                   `.____,.....___.\\ _...______________/\n                     "
            + "             __\\:+.`'O O  O O  O |\n                              ,-\"'  _,|:;|"
            + "\"\"\"\"\"\"\"\"\"\"\"\"|\n                            ,'   ,-'  `._/    _.\"  ."
            + "`-|\n                         .-\"    '      \\    .'      `.`.\n               "
            + "         :      .        \\   |        / |\n                         .      \\._"
            + "_   _,`-.|       /  |\n                         `.      \\  \"\"'     `.        "
            + " `....\n                           .     |            \\             `.\n       "
            + "                   .'   ,'              \\              |\n                  ,--"
            + "----'     `.               `-...._  '\"-. '.\n                 / ,'\"'\"`       "
            + " |                  `--`._      `.\n                 `\"......---\"\"--'        "
            + "                 \\       .\n                                                   "
            + "       |        `.\n                                                         (  "
            + " -..     .\n                                                          `\"\"\"' `"
            + "....' mh";
    public static final String scytherString = 
            "           ______\n       _.-\"______`._             ,.\n   "
            + "  ,\"_,\"'      `-.`._         /.|\n   ,',\"   ____      `-.`.___   // |\n  /.' "
            + ",-\"'    `-._     `.   | j.  |  /|\n // .'   __...._  `\"--.. `. ' |   | ' '\nj/"
            + "  _.-\"'       `._,.\"\".   |  |   |/ '\n|.-'                    `.'/| |   | /\n"
            + "'                        '/ | |   |/\n                         /  ' '   '\n     "
            + "              |.   ` .'/.   /\n                   | `. ,','.  ,'\n              "
            + "     |   \\.' j.-'/\n                   '   '   '. /\n                  |       "
            + "   `\"-...__\n                  |             _..-'\n                 ,|'      _"
            + "_.-7'   _......____\n                . |    ,\"/   ,'`.'__........___`-...__\n  "
            + "               .    '-'_..' .-\"\"-._         `\"\"'-----`---...___\n           "
            + "      |____.-','\" /      /`.._,\"\".                 _.-'\n              ,\"`| "
            + ",'   '   |      .,--. ;--|             _,-\"\n             |   '.| `-.|   `.    "
            + " ||   /   '`---.....--\"'.\n             '     `._  |     `+----`._;'.   `-..___"
            + "_..--'\"\n              `.    | \"'|__...-|,|       /     `.\n                |-"
            + "..|`-.7    /   '      /   |  '|\n                ' |' `.||`--'    |      \\   | "
            + ". |\n                        |        |       \\  ' | |\n                       "
            + " `.      .'        .   ' '\n                          `'-+-\"|`.       '  ' /\n "
            + "                            |`-'  \\     /  /.'\n                             ` "
            + "  _ ,.   / ,'/\n                              ||'.'`.  / /,'\n                  "
            + "             `      ' .'\n                                     /.' mh";
    public static final String alakazamString =
            "                                               _,'|\n      "
            + "                                       .'  /\n                    __            "
            + "         ,'   '\n                   `  `.                 .'    '\n             "
            + "       \\   `.             ,'     '\n                     \\    `.          ,   "
            + "   /\n                      .     `.       /      ,\n                      '    "
            + "   ..__../'     /\n                       \\     ,\"'   '      . _.._\n         "
            + "               \\  ,'             |'    `\"._\n                         |/      "
            + "         ,---.._   `.\n                       ,-|           .   '       `-.  \\\n"
            + "                     ,'  |     ,   ,'   :           '__\\_\n                    "
            + " |  /,_   /  ,U|    '            |   .__\n                     `,' `.\\ `./..-' "
            + " __ \\           |   `. `.\n                       `\",_|  /     ,\"  `.`._     "
            + "  .|     \\ |\n                      / /_.| j  ---'.     `._`-----`.`     | |\n "
            + "                    / // ,|`'  `-/' `.      `\"/-+--'    ,'  `.\n               "
            + "  _,.`,'| / |.'  -,' \\  \\       \\ '._    /     |\n .--.      _,.-\"'   `| L \\"
            + " \\__ ,^.__.\\  `.  _,--`._,>+-'  __,-'\n:    \\   ,'          |  | \\          "
            + "/.   `'      '.  `--'| \\\n'    | ,-.. `'   _,--' ,'  \\        `.\\            "
            + "7      |,.\\\n `._ '.  .`.    .>  `-.-    |-.\"\"---..-\\        _>`       `.-'\n"
            + "    `.,' | l  ,' ,>         | `.___,....\\._    ,--``-.\n   j | .'|_|.'  /_     "
            + "    /   _|         \\`\"--+--.   ` ,..._\n   |_`-'/  |     ,' ,.._,.'\"\"\"'\\  "
            + "         `--'    `-..'     `\".\n     \"-'_,+'\\    '^-     |      \\           "
            + "         /         |\n          |_/         __ \\       .                   `.`."
            + "._  ,'`.\n                  _.:'__`'        `,.                  |   `'   |\n   "
            + "              `--`-..`\"        /--`               ,-`        |\n               "
            + "    `---'---------'                   \"\"| `#     '.\n                         "
            + "                              `._,       `:._\n                                 "
            + "                        `|   ,..  |  '.\n                                       "
            + "                  j   '.  `-+---'\n                                             "
            + "            |,.. |\n                                                          `."
            + " `;\n                                                            `' mh";
    public static final String rapidashString = 
            "                     :`./\n                    _|  ,-\n    "
            + "           ,'\"\"'    ,`\n             ,'.\\       `.    __  ,.-.\n          . ."
            + "/ `'    __  '. ,'  \\ `.|\n          \\\\  \\   .\"'  L   \"     `\" `\\        "
            + "                  _,-.\n           \\` |\\.`      7     .,   :._|   --'`.       "
            + "          ` |\n          ` \\`+ `'\\      \\^--\"  `. |    ,'     `.            "
            + ",..' |\n           | ,.    |              ` `.  |    ..  '.          |    /\n   "
            + "        ':P'     '.    ,..      \\  `-+`\"-'  `._ \\     -`,- ..,'\n          / "
            + "       / `-,-'  ,'`.    `.   ; .--'   `+    '.   | ,\n         /     _..     .  "
            + " `-.  \\,.   `-'  '.  `.^  `\".__|   ' |\n        '   , / |       `.   \\    |  "
            + "      ,'     \\           /\n         `\"' \" .         \\   |  __ \\    ,-'    "
            + "   `----.   _,'\n              /           |  `\"' _} `\"\"'                `-'\n"
            + "             /.'         /     .-.         ,\".\n     .._,.  /           /     '"
            + "-.,'    ,'-. .'.\n    /  `. \\/             `-.      `.   /`.  :\n   /  __ `.'  "
            + "              '-.     `-+_.'  .'          ,__\n  / .'  `.___                  `,"
            + "..__      <__          \\ (\n / /       \"..   /                   `-.     .' .-"
            + "'\"`--.'  \\\n/  |       /-'  /                       \\ ,._|  |          /'\n\\"
            + ".'|+.+.  (`..,'                         \\`._ _,'           \\__\n \\ |||| \\ _`"
            + ".^ `.            .            |  \"    .'`\"-.       `.\n  `+'|/ `( \\'    `-..."
            + ".__    |            |._,\".,'     `,        |\n                         `:-.|   "
            + "         `           ..'   ,'`.,-\n                          |  |            |`."
            + "        '-..    . /\n                          '  |           /  /           `. "
            + "  |\n                           ` '          /  ',.         ,     `._\n         "
            + "                   \\|        ,'   \\'|         :  __    '\n                    "
            + "         `,     ,`     .._`..       `'  `-,.`.\n                       _`'`\".  "
            + "`.   ``-._ /   F   )        ,._\\ `\n                      '-\"'`, \\   \\ ,. )."
            + "-'-.^,|_,'         `  '.\n                          '.. \\___j  `\"'            "
            + "   ,..  | .'\n                             \\            ___       ,. `\\ \\,+-'"
            + "\n                              7.._   .--+`.  |_    |  `,'\n                   "
            + "        _,'  .'`--'  '    7 ` v.-\n                         .\"._  /-.  -.   \\."
            + "^-`\n                       .'  __+'...`'  `--'\n                        `\"\" m"
            + "h";

}