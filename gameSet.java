import java.util.ArrayList;

/*
--ToDo: Fill this in:
Ayad Masud
3/28/25
*/

public class gameSet {
    public static void main(String[] args) {
        String[] dominoes;

        //the following code is what we did in class:
        dominoes = MyDominoes.buildSet(6);
        //We are adding dice and decks of cards


        MyDominoes.shuffleSet(dominoes);

        for (String tile : dominoes) {
            System.out.print(tile);
            System.out.println();
        }

        // --ToDo  Create a MyDice class that accepts the following code:

        System.out.println("I rolled a  " + myDice.roll() + " with a standard 6 sided die.\n");
        System.out.println("I rolled a " + myDice.roll(24) + " with a cool 24 sided die.\n" );

        // -- ToDo Create a MyCards class that accepts the following code:
        
        ArrayList<String> stdDeck = myCards.buildDeck();
        ArrayList<String> jokerDeck = myCards.buildDeck(2);
        ArrayList<String> pinochleDeck = myCards.buildDeck('P');

        System.out.println("My standard deck is: ");
        for (String card : stdDeck) {
            System.out.print(card);
        }
        System.out.println();

        System.out.println("My joker deck is: ");
        for (String card : jokerDeck) {
            System.out.print(card);
        }
        System.out.println();

        System.out.println("My pinochle deck is: ");
        for (String card : pinochleDeck) {
            System.out.print(card);
        }
        System.out.println();
        
    }

}