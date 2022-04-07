package takoyaki;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Takoyaki {
    private CardDeck cardDeck;
    private Player human;
    private ComputerPlayer comp;
    private Boolean humanTurn;


    public Takoyaki() {
        cardDeck = new CardDeck(13, 2);
        human = new Player();
        comp = new ComputerPlayer();
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
        this.getCardDeck().dealCardsAtTable(this.getHuman());
        // gir datmaskinens kortene på bordet
        this.getCardDeck().dealCardsAtTable(this.getComp());

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

    public void setComp(ComputerPlayer comp) {
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

    public void theAIMove() {
        if(human.getHand() == null) {
            humanTurn = false;
            // gir maskinen ny Hand
            if(comp.getHand() == null) {
                cardDeck.giveNewHand(comp);
            }
        }

        while(!humanTurn && !gameOver() && comp.getHand() != null) {  
            // hva maskinen skal gjøre med jokeren
            // gjør "tilfeldige" valg
            if(comp.getHand().getFace() == 0) {
                List<Integer> liste = new ArrayList<Integer>();

                for (Integer n : comp.possibleMoves()) {
                    liste.add(n);
                }
                comp.switchCard(liste.get(Takoyaki.getRandomNumberInRange(0, liste.size() - 1)));
            }
            else if(comp.getHand().getFace() < 11) {               
                comp.switchCard(comp.getHand().getFace());
            }   

            // kaster ubrukelig hand
            comp.discardHand();
        }

        // for å gjøre humanTurn sann igjen
        humanTurn = true;

        // gir spilleren ny Hand
        if(human.getHand() == null && !gameOver()){
            cardDeck.giveNewHand(human);
        }
    }

    // hentet fra her: https://mkyong.com/java/java-generate-random-integers-in-a-range/
    private static int getRandomNumberInRange(int min, int max) {

        // er større for at hvis det bare er et alternativ går fint
		if (min > max) {
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

        System.out.println(t1.getHuman().getCardAtTable(1).getFaceUp());
    }
}
