package takoyaki;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTakoyaki {
    private Takoyaki t1;
    private CardDeck cardDeck;
    private Card compHand;

    @BeforeEach
	public void setup() {
        t1 = new Takoyaki();
    }

    // usikker på nødvendig
    @Test
	@DisplayName("Sjekker at tom konstruktør eksisterer")
	public void testConstructor() {
        t1 = new Takoyaki();
    }

    @Test
	@DisplayName("Sjekker at spilllet kan bekrefte at Takoyaki spillet er ferdig")
	public void testGameOver() {
        assertEquals(t1.gameOver(), false);

        // endrer spillet slik at human vinner
        for (Card card : t1.getHuman().getCardsAtTable()) {
            card.flipCardUp();
        }

        assertEquals(t1.gameOver(), true);

        // resetter t1 slik at gameOver() kan testes for andre tilfeller
        t1 = new Takoyaki();

        // endrer spillet slik at comp vinner
        for (Card card : t1.getComp().getCardsAtTable()) {
            card.flipCardUp();
        }

        assertEquals(t1.gameOver(), true);

        // resetter t1 slik at gameOver() kan testes for andre tilfeller
        t1 = new Takoyaki();
        // lager den minst mulige CardDeck for å fjerne alle kortene raskt
        cardDeck = new CardDeck(1);

        for (int i = 0; i < 4; i++) {
            t1.getHuman().setHand(null);
            cardDeck.giveNewHand(t1.getHuman());
        }

        // spillet er over hvis kortstokken er tom
        t1.setCardDeck(cardDeck);
        assertEquals(t1.gameOver(), true);
    }

    @Test
	@DisplayName("Sjekker at AI kan gjøre trekk når det er dens tur")
	public void testTheAIMove() {
        compHand = t1.getComp().getHand();
        assertEquals(t1.getComp().getHand(), compHand);
        t1.theAIMove();
        assertEquals(t1.getComp().getHand(), compHand);

        // metoden handler når det ikke er human sin tur
        t1.setHumanTurn(false);
        t1.theAIMove();
        assertEquals(t1.getComp().getHand(), null);

        // metoden har satt human til sann igjen
        assertEquals(t1.getHumanTurn(), true);

        // bekrefter at metoden klarer å handtere en joker 
        compHand = new Card(true);
        t1.getComp().setHand(compHand);
        assertEquals(t1.getComp().getHand(), compHand);
        t1.setHumanTurn(false);
        t1.theAIMove();
        assertEquals(t1.getComp().getHand(), null);

    }
}
