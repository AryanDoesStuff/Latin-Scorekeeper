import java.util.*;

public class Game {
    private int totalWinnings;
    private int nonBoardedPool;
    private final Map<Person, Integer> wagers;
    private final Map<Person, Person> nonPlayerWagers;
    private Person winner;
    private final int standardBettingAmount;

    public Game(int standardBettingAmount) {
        wagers = new HashMap<>();
        nonPlayerWagers = new HashMap<>();
        this.standardBettingAmount = standardBettingAmount;
    }

    public void addWager(Person p, int amount) {
        wagers.put(p, wagers.getOrDefault(p, 0) + amount);
        p.adjustChips(-amount);
        totalWinnings += amount;
    }

    public void betOnPlayer(Person a, Person b) {
        nonPlayerWagers.put(a,b);
        a.adjustChips(-standardBettingAmount);
        nonBoardedPool += standardBettingAmount;
    }

    public void setWinner(Person winner) {
        this.winner = winner;
    }

    public void accountForWagers() {
        int cnt = 0;
        winner.adjustChips(totalWinnings);

        for (Map.Entry<Person, Person> m : nonPlayerWagers.entrySet()) {
            if(m.getValue().equals(winner)) {
                cnt++;
            }
        }

        for (Map.Entry<Person, Person> m : nonPlayerWagers.entrySet()) {
            if(m.getValue().equals( winner)) {
                m.getKey().adjustChips(nonBoardedPool/cnt);
            }
        }
    }

    public String printLeaderboard(List<Person> plist) {
        String str = "";
        Person[] tbs = new Person[plist.size()];
        for (int i = 0; i < plist.size(); i++) {
            tbs[i] = plist.get(i);
        }

        Arrays.sort(tbs, Comparator.comparingInt(Person::getChipCount).reversed());
        //Arrays.stream(tbs).sorted(Comparator.comparingInt(Person::getChipCount).reversed()).forEach(System.out::println);
        for (int i = 0; i < plist.size(); i++) {
            str += (i + 1) + ". " + tbs[i] + "\n";
        }
        return str;
    }
}
