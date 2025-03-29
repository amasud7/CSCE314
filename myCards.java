import java.util.ArrayList;

public class myCards {
    public static ArrayList<String> buildDeck() {
        String[] suits = {"C", "D", "H", "S"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K", "A"};
        ArrayList<String> deckList = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                deckList.add(rank + "-" + suit + "\n");
            }
        }
        return deckList;
    }

    public static ArrayList<String> buildDeck(int jokers) {
        ArrayList<String> deck = buildDeck();
        for (int i = 0; i < jokers; i++) {
            deck.add("Joker\n");
        }
        return deck;
    }

    public static ArrayList<String> buildDeck(char type) {
        ArrayList<String> deck = new ArrayList<>();
        if (type == 'P') {
            String[] suits = {"C", "D", "H", "S"};
            String[] ranks = {"9", "10", "J", "Q", "K", "A", "9", "10", "J", "Q", "K", "A"};
            for (String suit : suits) {
                for (String rank : ranks) {
                    deck.add(rank + "-" + suit + "\n");
                }
            }
        }
        return deck;
    }

    public static void main (String[] args) {
        // ArrayList<String> stdDeck = myCards.buildSet();
        // System.out.println(stdDeck);

        // ArrayList<String> jokerDeck = myCards.buildDeck(2);
        // System.out.println(jokerDeck);

        ArrayList<String> pinochleDeck = myCards.buildDeck('P');
        System.out.println(pinochleDeck);
    }

}
