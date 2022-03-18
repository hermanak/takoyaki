package takoyaki;
import java.util.Random;


public class Takoyaki {
    private CardDeck cardDeck;
    private Player human;
    private Player comp;
    private Boolean humanTurn;


    public Takoyaki() {
        cardDeck = new CardDeck(13, 2);
        human = new Player();
        comp = new Player();
        humanTurn = true;
        int randomShuffle = getRandomNumberInRange(1, 5);
        for (int i = 0; i < randomShuffle; i++) {
            cardDeck.shufflePerfectly();
            cardDeck.switchTopAndBottom();
        } 
           
        // gir spiller hånden
        this.getCardDeck().giveNewHand(this.getHuman());
        // gir datamaskin hånden
        this.getCardDeck().giveNewHand(this.getComp());
        // gir spillerens kortene på bordet
        this.getCardDeck().dealBeforeStart(this.getHuman());
        // gir datmaskinens kortene på bordet
        this.getCardDeck().dealBeforeStart(this.getComp());

    }

    public CardDeck getCardDeck() {
        return this.cardDeck;
    }

    public void setCardDeck(CardDeck cardDeck) {
        this.cardDeck = cardDeck;
    }

    public Player getHuman() {
        return this.human;
    }

    public void setHuman(Player human) {
        this.human = human;
    }

    public Player getComp() {
        return this.comp;
    }

    public void setComp(Player comp) {
        this.comp = comp;
    }

    public Boolean getHumanTurn() {
        return humanTurn;
    }

    public void setHumanTurn(Boolean humanTurn) {
        this.humanTurn = humanTurn;
    }

    public Boolean gameOver() {
        if(cardDeck.getCardCount() == 0 || human.allCardsFlipped() || comp.allCardsFlipped()){
            return true;
        }
        return false;
    }

    

    // hentet fra her: https://mkyong.com/java/java-generate-random-integers-in-a-range/
    public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

    public static void main(String[] args) throws InterruptedException {
        Takoyaki t1 = new Takoyaki();
        
        System.out.println(t1.getHuman().getHand());
        System.out.println(t1.getHuman().getCardsAtTable());
        System.out.println(t1.getComp().getHand());
        System.out.println(t1.getComp().getCardsAtTable());

        t1.getHuman().switchCard(1);
        System.out.println(t1.getHuman().getHand());
        System.out.println(t1.getHuman().getCardsAtTable());
    }
}
