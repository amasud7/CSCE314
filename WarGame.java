import java.util.*;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
/*
Ayad Masud 4/3/25
*/

interface Shufflable {
    void shuffle();
}

interface PlayableEntity {
    void takeTurn();
}

abstract class GamePiece {
    abstract void play();
}

class Card extends GamePiece implements Comparable<Card> {
    private final char rank;
    private final char suit;

    public Card(char rank, char suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public char getRank() {
        return rank;
    }

    @Override
    public void play() {
        // Implement how a card is played
        // pull card from deck, display it and remove it from deck
       
        System.out.println("Playing card: " + this);
    }

    @Override
    public int compareTo(Card other) {
        // Implement comparison logic based on rank
        // Assuming ranks are ordered as '2' < '3' < ... < '9' < 'T' < 'J' < 'Q' < 'K' < 'A'
        String rankOrder = "23456789TJQKA";
        int thisRankIndex = rankOrder.indexOf(this.rank);
        int otherRankIndex = rankOrder.indexOf(other.rank);
        return Integer.compare(thisRankIndex, otherRankIndex);
    }

    @Override
    public String toString() {
        return rank + "-" + suit;
    }
}

class Deck implements Shufflable {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        // Initialize deck with all 52 cards
        char[] suits = {'H', 'D', 'C', 'S'}; // Hearts, Diamonds, Clubs, Spades
        char[] ranks = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
        for (char suit : suits) {
            for (char rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    @Override
    public void shuffle() {
        // Collection is built-in java function for shuffling a container (array or list) 
        Collections.shuffle(cards);
    }

    public List<Card> splitDeck() {
        // Return the first half of the deck
        int halfSize = cards.size() / 2;
        List<Card> firstHalf = new ArrayList<>(cards.subList(0, halfSize));
        // cards.subList(0, halfSize).clear(); // Remove the first half from the deck
        return firstHalf;
    }   

    public List<Card> getRemainingHalf() {
        // Return the other half of the deck
        List<Card> secondHalf = new ArrayList<>(cards.subList(cards.size() / 2, cards.size()));
        return secondHalf;
    }
}

class Player implements PlayableEntity {
    private final Queue<Card> hand;

    public Player(List<Card> cards) {
        hand = new LinkedList<>(cards);
    }

    public boolean hasCards() {
        return !hand.isEmpty();
    }

    public Card drawCard() {
        // Remove and return the top card from the hand
        //.poll() pops head of queue 
        return hand.poll();
    }

    public void collectCards(List<Card> wonCards) {
        // Implement logic to collect won cards
        hand.addAll(wonCards);
    }

    public int getCardCount() {
        return hand.size();
    }

    @Override
    public void takeTurn() {
        if (hasCards()) {
            Card playedCard = drawCard();
            System.out.println("Player plays: " + playedCard);
        } else {
            System.out.println("Player has no cards left to play.");
        }
    }
}

class WarGame {
    private final Player player1;
    private final Player player2;

    public WarGame() {
        Deck deck = new Deck();
        deck.shuffle(); // Ensure the deck is shuffled before splitting
        player1 = new Player(deck.splitDeck());
        player2 = new Player(deck.getRemainingHalf());
    }

    public void playGame() {
        while (player1.hasCards() && player2.hasCards()) {
            playRound();
        }
        declareWinner();
    }

    private void playRound() {
        List<Card> roundPile = new ArrayList<>();
        Card card1 = player1.drawCard();
        Card card2 = player2.drawCard();

        if (card1 != null) roundPile.add(card1);
        if (card2 != null) roundPile.add(card2);

        System.out.println("Player 1 plays: " + card1);
        System.out.println("Player 2 plays: " + card2);

        if (card1 != null && card2 != null) {
            int comparison = card1.compareTo(card2);
            if (comparison > 0) {
                System.out.println("Player 1 wins the round!");
                player1.collectCards(roundPile);
            } else if (comparison < 0) {
                System.out.println("Player 2 wins the round!");
                player2.collectCards(roundPile);
            } else {
                System.out.println("War!");
                handleWar(roundPile);
            }
        }
    }

    private void handleWar(List<Card> warPile) {
        // Implement war scenario
        if (!player1.hasCards() || !player2.hasCards()) {
            System.out.println("A player has run out of cards during war!");
            return;
        }

        // Each player places three cards face down and one card face up
        for (int i = 0; i < 3; i++) {
            if (player1.hasCards()) warPile.add(player1.drawCard());
            if (player2.hasCards()) warPile.add(player2.drawCard());
        }

        if (!player1.hasCards() || !player2.hasCards()) {
            System.out.println("A player has run out of cards during war!");
            return;
        }

        Card warCard1 = player1.drawCard();
        Card warCard2 = player2.drawCard();

        if (warCard1 != null) warPile.add(warCard1);
        if (warCard2 != null) warPile.add(warCard2);

        System.out.println("Player 1 plays (war): " + warCard1);
        System.out.println("Player 2 plays (war): " + warCard2);

        if (warCard1 != null && warCard2 != null) {
            int comparison = warCard1.compareTo(warCard2);
            if (comparison > 0) {
                System.out.println("Player 1 wins the war!");
                player1.collectCards(warPile);
            } else if (comparison < 0) {
                System.out.println("Player 2 wins the war!");
                player2.collectCards(warPile);
            } else {
                System.out.println("War continues!");
                handleWar(warPile);
            }
        }
    }

    private void declareWinner() {
        int player1CardCount = player1.getCardCount();
        int player2CardCount = player2.getCardCount();

        System.out.println("Game Over!");
        System.out.println("Player 1 has " + player1CardCount + " cards.");
        System.out.println("Player 2 has " + player2CardCount + " cards.");

        if (player1CardCount > player2CardCount) {
            System.out.println("Player 1 wins the game!");
        } else if (player2CardCount > player1CardCount) {
            System.out.println("Player 2 wins the game!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        try {
            // Create a file to store the output
            PrintStream fileOut = new PrintStream(new FileOutputStream("wargame_output.txt"));
            
            // Store the original System.out
            PrintStream originalOut = System.out;
            
            // Redirect standard output to the file
            System.setOut(fileOut);
            
            // Create and play the game (output goes to file)
            System.out.println("Starting War Game...");
            WarGame game = new WarGame();
            game.playGame();
            
            // Restore original standard output
            System.setOut(originalOut);
            System.out.println("Game results have been written to wargame_output.txt");
            
            // Close the file output stream
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}