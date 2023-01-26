package pk;

import java.util.ArrayList;
import java.util.Random;

public class CardDeck {

    private ArrayList<String> cards;

    public CardDeck(){
        resetCards();
    }

    private void resetCards(){
        cards = new ArrayList<>();
        cards.add("Sea Battle 2");
        cards.add("Sea Battle 2");
        cards.add("Sea Battle  3");
        cards.add("Sea Battle  3");
        cards.add("Sea Battle  4");
        cards.add("Sea Battle  4");
        for (int i = 0; i < 29; i++){
            cards.add("nop");
        }
    }

    // Shuffles an input number of cards from the deck.
    public void shuffleCards(int shuffleTimes){
        Random bag = new Random();
        for (int i = 0; i < shuffleTimes; i++){
            int removedIndex = bag.nextInt(cards.size());
            String removedCard = cards.get(removedIndex);
            cards.remove(removedIndex);
            cards.add(removedCard);
        }
    }

    // Draws a card from the pile, removing it once drawn.
    public String drawCard(){

        // If no cards left, resets the cards and shuffles them.
        if (cards.size() == 0){
            resetCards();
            shuffleCards(50);
        }

        String returnCard = cards.get(0);
        cards.remove(0);
        return returnCard;
    }


}
