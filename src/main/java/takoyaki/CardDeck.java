package takoyaki;
import java.util.ArrayList;
import java.util.List;

public class CardDeck {
    
    private List<Card> cards = new ArrayList<Card>();

    private char[] JavaCharArray = {'S', 'H', 'D', 'C'};

    // konstruktør med bare vanlige kort
    public CardDeck(int n) {
        if(n < 0 || n > 13) {
            throw new IllegalArgumentException("Ugyldig kort verdi");
        }

        if(n != 0) {
            for (char c : JavaCharArray) {
                for (int i = 0; i < n; i++) {
                    Card newCard = new Card(c, i + 1);
                    cards.add(newCard);
                }    
            }
        }
    }

    // kounstruktør med joker
    public CardDeck(int n, int m) {
        if(n < 0 || n > 13) {
            throw new IllegalArgumentException("Ugyldig kort verdi");
        }

        if(n != 0) {
            for (char c : JavaCharArray) {
                for (int i = 0; i < n; i++) {
                    Card newCard = new Card(c, i + 1);
                    cards.add(newCard);
                }    
            }
        }

        for (int i = 0; i < m; i++) {
            Card newCard = new Card();
            cards.add(newCard);
        }
    }

    // kounstruktør avhengig av Card liste. Brukes for lagring
    public CardDeck(List<Card> cards) {
        this.cards = cards;
    }

    public int getCardCount(){
        return cards.size();
    }

    public Card getCard(int n){
        return cards.get(n);
    }

    public void shufflePerfectly() {
        int size = cards.size();
        List<Card> first = new ArrayList<>(cards.subList(0, (size + 1)/2));
        List<Card> second = new ArrayList<>(cards.subList((size + 1)/2, size));


        cards.clear();
        
        for (int i = 0; i < size / 2; i++){
            cards.add(first.get(i));
            cards.add(second.get(i));
        }
    }

    // denne koden klarer ikke å håndtere kortstokk med oddetall. Er ikke et problem for proskjetet siden to jokere brukes alltid
    public void switchTopAndBottom() {
        int size = cards.size();
        List<Card> first = new ArrayList<>(cards.subList(0, (size + 1)/2));
        List<Card> second = new ArrayList<>(cards.subList((size + 1)/2, size));

        cards.clear();

        for (int i = 0; i < size / 2; i++) {
            cards.add(second.get(i));
        }
        for (int i = 0; i < size / 2; i++) {
            cards.add(first.get(i));
        }
    }

    public void dealBeforeStart(Player cardsAtTable){
        if(cardsAtTable.getCardsAtTable().size() != 0){
            throw new IllegalArgumentException("Det har allerede blitt gitt kort!");
        }
        for (int index = 0; index < 10; index++) {
            cardsAtTable.addCardAtTable(this.getCard(this.getCardCount() - 1));
            cards.remove(this.getCardCount() - 1);
        }
    }

    public void giveNewHand(Player hand){
        if(cards.size() == 0){
            throw new IllegalArgumentException("Det er tomt for kort!");
        }
        this.getCard(this.getCardCount() - 1).flipCardUp();
        hand.setHand(this.getCard(this.getCardCount() - 1));
        cards.remove(this.getCardCount() - 1);
    }

    public static void main(String[] args) {
        CardDeck stokk1 = new CardDeck(13, 0);
        //System.out.println(stokk1.getCardCount());

        CardDeck stokk2 = new CardDeck(13, 2);
        //System.out.println(stokk2.getCardCount());
        //System.out.println(stokk2.getCard(0));
        //System.out.println(stokk2.getCard(53));

        CardDeck stokk3 = new CardDeck(2, 2);
        stokk3.switchTopAndBottom();

        for (int i = 0; i < stokk3.getCardCount(); i++) {
            System.out.println(stokk3.getCard(i));
        }

    }
}
