package takoyaki;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Card hand;
    private Card discardedPile;
    private List<Card> cardsAtTable = new ArrayList<Card>();
    private CardDeck cardDeck;
    private Player player;

    @BeforeEach
	public void setup() {
        cardDeck = new CardDeck(13,2);

        for (int i = 0; i < 10; i++) {
			cardsAtTable.add(cardDeck.getCard(cardDeck.getCardCount() - (i + 1)));
		}
        
        // skal bruke give new hand til å fjerne 
        discardedPile = cardDeck.getCard(cardDeck.getCardCount() - 12);
        hand = cardDeck.getCard(cardDeck.getCardCount() - 13);

        player = new Player();
    }

    // er denne unødvendig?
    @Test
	@DisplayName("Sjekker at tom konstruktør eksisterer")
	public void testConstructor() {
        player = new Player();
    }

    @Test
	@DisplayName("Sjekker at konstruktør for å laste inn et spill fungerer ")
	public void testConstructorForLoad() {

        player = new Player(hand, discardedPile, cardsAtTable);

        assertEquals(player.getHand(), hand);
        assertEquals(player.getDiscardedPile(), discardedPile);
        assertEquals(player.getCardsAtTable(), cardsAtTable);
    }

    @Test
	@DisplayName("Sjekker at kort blir lagt til card at table ")
	public void testAddCardAtTable() {
        cardDeck.dealCardsAtTable(player);
        assertEquals(player.getCardsAtTable(), cardsAtTable);

        Assertions.assertThrows(IllegalStateException.class, () -> {
			player.addCardAtTable(hand);
		}, "Det skal bare legges 10 Cards på bordet");
    }

    @Test
	@DisplayName("Sjekker at kort blir lagt til card at table ")
	public void testSwitchCard() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
			player.switchCard(1);
		}, "Cards må ha blitt delt før man kan bytte dem");


    }

}
