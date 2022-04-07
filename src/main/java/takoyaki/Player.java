package takoyaki;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Card hand;
    // bare et ubrukt kort skal vises om gangen
    private Card discardedPile;
    private List<Card> cardsAtTable = new ArrayList<Card>();

    public Player() {

    }

    // brukes til å lagre og laste inn
    public Player(Card hand, Card discardedPile, List<Card> cardsAtTable) {
        if(hand != null){
            if(!hand.getFaceUp()){
                throw new IllegalArgumentException("Hand skal alltid være synlige");
            }
        }
        if(discardedPile != null){
            if(!discardedPile.getFaceUp()){
                throw new IllegalArgumentException("discardedPile skal alltid være synlige");
            }
        }
        this.hand = hand;
        this.discardedPile = discardedPile;
        this.cardsAtTable = cardsAtTable;
    }
  
    public Card getHand() {
        return this.hand;
    }

    public void setHand(Card hand) {
        this.hand = hand;
    }


    // gir alle korta
    public List<Card> getCardsAtTable() {
        return this.cardsAtTable;
    }

    // gir et bestemt kort
    public Card getCardAtTable(int n){
        return this.cardsAtTable.get(n);
    }


    public Card getDiscardedPile() {
        return this.discardedPile;
    }

    public void setDiscardedPile(Card discardedPile) {
        this.discardedPile = discardedPile;
    }

    public void addCardAtTable(Card card1) {
        if(cardsAtTable.size() >= 10){
            throw new IllegalStateException("Det er for mange kort!");
        }
        cardsAtTable.add(card1);
    }

    public void setCardAtTable(int n, Card card1) {
        cardsAtTable.set(n, card1);
    }


    public void switchCard(int position) {
        if(hand == null || cardsAtTable == null){
            throw new IllegalStateException("Kort er ikke fordelt");
        }
        // spillet starter med kort 1, ikke null. Så for å lettere samsvare med spillet regner jeg ut posisjonen minus 1
        if(position > 0 && position < 11) {
            if(position == hand.getFace() || hand.getFace() == 0) {
                if(!cardAlreadyFlipped(position)){
                    Card temp;
                    temp = hand;
                    hand = cardsAtTable.get(position - 1);
                    hand.flipCardUp();
                    setCardAtTable(position - 1, temp);
                }
            }
        } 
        else {
            throw new IllegalArgumentException("Det er ingen kort der");
        }
    }

    public void discardHand() {
        if(hand != null) {
            // 0 betyr at det er joker som må ikke kastes!
            if(hand.getFace() != 0) {
                // må skjekke dette først siden cardAlreadyFlipped fungerer bare når kortet er under 11
                if(hand.getFace() > 10 ){
                    discardedPile = hand;
                    hand = null;
                }
                else if (cardAlreadyFlipped(hand.getFace())) {
                    discardedPile = hand;
                    hand = null;
                }
            }   
        }  
    }

    private Boolean cardAlreadyFlipped(int position) {
        if(position < 1 || position > 10){
            throw new IllegalArgumentException("Man skal ikke skjekke disse posisjonene! Noe har gått galt"); 
        }
        return this.getCardAtTable(position - 1).getFaceUp();
    }

    public Boolean allCardsFlipped() {
        for (Card card : cardsAtTable) {
            if(!card.getFaceUp()){
                return false;    
            }
        }
        return true;
    }
}
