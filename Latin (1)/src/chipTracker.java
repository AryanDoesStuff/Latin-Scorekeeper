import java.util.*;

public class chipTracker {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Give starting chip balance for all players ");
        int cc = scn.nextInt();
        List<Person> personList = new ArrayList<>();
        List<String> personNames = new ArrayList<>();
        List<Person> onDeck = new ArrayList<>();
        Set<Person> played = new HashSet<>();
        System.out.println();
        System.out.print("Give the standard amount of betting on a player ");
        int std = scn.nextInt();
        System.out.println();
        System.out.print("How many people will be at the bench at one time ");
        int ppl = scn.nextInt();
        System.out.println();
        System.out.println("Will keep asking for names until STOP is entered case irrespective");
        String in;
        scn.nextLine();
        do {
            System.out.print("Name ");
            in = scn.nextLine();
            personNames.add(in);
            personList.add( new Person(cc, in));
            System.out.println();
        } while (!in.equalsIgnoreCase("STOP"));
        personList.remove(personList.size()-1);
        personNames.remove(personNames.size()-1);
        System.out.println("Names acquired...");
        System.out.println("Starting game loop...");
        boolean isGameActive = true;



        while (isGameActive) {
            Game game = new Game(std);
            System.out.println("Finding next participants --------");
            for (int i = 0; i < ppl; i++) {
                int random = (int)(Math.random() * personList.size());
                while (played.contains(personList.get(random))) {
                    random = (int)(Math.random() * personList.size());
                }
                played.add(personList.get(random));
                onDeck.add(personList.get(random));
                System.out.println("Player "+ i + " is " + onDeck.get(i).getName());
            }
            boolean isWinner = false;
            System.out.println("Game state is turning on...");
            while (!isWinner) {
                int n = scn.nextInt();
                String[] involved;
                if (n == 0) { // 0 - Bet on a player, format: 0 *Player 1* *Player 2*.
                    String str = scn.nextLine();
                    involved = str.split(" ");
                    game.betOnPlayer(personList.get(personNames.indexOf(involved[1])), personList.get(personNames.indexOf(involved[2])));
                } else if (n == 1) { // 1 - Player may choose to raise or standardize their wager, format: 1 *Player 1* *Amount Raised*.
                    String str = scn.next();
                    int amt = scn.nextInt();
                    game.addWager(personList.get(personNames.indexOf(str.trim())), amt);
                    scn.nextLine();
                } else if (n == 2) { // 2 - Winner, format: 2 *Winning Player*. *Did they translate right*
                    String str = scn.nextLine();
                    String[] full = str.split(" ");
                    game.setWinner(personList.get(personNames.indexOf(full[1])), full[2].equalsIgnoreCase("yes"));
                    isWinner = true;
                }
            }
            game.accountForWagers();
            System.out.println(game.printLeaderboard(personList));
            System.out.print("Another round? ");
            String TorF = scn.nextLine();
            onDeck.clear();
            if (TorF.equalsIgnoreCase("f")) {
                isGameActive = false;
            }
        }
    }
}
