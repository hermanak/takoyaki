package takoyaki;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer extends Player {

    public ComputerPlayer() {

    }

    // brukes til å lagre og laste inn
    public ComputerPlayer(Card hand, Card discardedPile, List<Card> cardsAtTable) {
        super(hand, discardedPile, cardsAtTable);
    }

    // gir hvilke mulige trekk Computer can velge for joker
    public List<Integer>  possibleMoves(){
        List<Integer> liste = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            if(!getCardAtTable(i).getFaceUp()) {
                liste.add(i + 1);
            }
        }
        return liste;
    }
}
