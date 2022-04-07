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

        // må gis egne verdier. Dette er fordi å bruke flipCard mens de fortsatt befinner seg i cardDeck skaper problemer man ellers ikke møter
        discardedPile = new Card('C',5);
        discardedPile.flipCardUp();
        hand = new Card('C',4);
        hand.flipCardUp();
        
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

        hand = new Card('C', 4, false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			player = new Player(hand, discardedPile, cardsAtTable);
		}, "Ingen slike spill bør ha blitt lagret");
    }

    @Test
	@DisplayName("Sjekker at kort blir lagt til card at table ")
	public void testAddCardsAtTable() {
        cardDeck.dealCardsAtTable(player);
        assertEquals(player.getCardsAtTable(), cardsAtTable);

        Assertions.assertThrows(IllegalStateException.class, () -> {
			player.addCardAtTable(hand);
		}, "Det skal bare legges 10 Cards på bordet");
    }

    @Test
	@DisplayName("Sjekker at kort blir lagt til man bytter kort når man har en ")
	public void testSwitchCard() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
			player.switchCard(1);
		}, "Cards må ha blitt delt før man kan bytte dem");

        cardDeck.dealCardsAtTable(player);
        

        cardDeck.giveNewHand(player);

        hand = player.getHand();
        // er ikke mulig å sammenligne kort direkte så må bruke toString() og getFaceUp()
        assertEquals(player.getHand(), hand);
        assertEquals(player.getHand().getFaceUp(), true);
        assertEquals(player.getCardAtTable(3).getFaceUp(), false);

        // player.getHand() er "C5" som er mulig å bytte
        player.switchCard(player.getHand().getFace());
        assertNotEquals(player.getHand(), hand);
        assertEquals(player.getHand().getFaceUp(), true);
        assertEquals(player.getCardAtTable(4).getFaceUp(), true);

        // hand endres til å passe med player for testing av senere kode
        hand = player.getHand();
        // player.getHand() er "C12" kan ikke byttes for noen plasser
        for (int i = 0; i < 10; i++) {
            player.switchCard(i+1);
        }
        assertEquals(player.getHand(), hand);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			player.switchCard(-1);
		}, "Ingen kort bør være der");
    }

    @Test
	@DisplayName("Sjekker at ubrukelig hand blir overført til discardePile")
	public void testDiscardHand() {
        player = new Player(hand, discardedPile, cardsAtTable);
        // hand er C4 og kortet på posisjon 4 er fortsatt ned
        player.discardHand();
        assertEquals(player.getHand(), hand);

        // bytter kortet på posisjon 4
        player.switchCard(4);
        hand = player.getHand();
        // hand er nå C12 som vil bli kastet
        player.discardHand();
        assertEquals(player.getHand(), null);
        assertEquals(player.getDiscardedPile(), hand);

        // player vil få joker som aldri vil kastes
        cardDeck.giveNewHand(player);
        hand = player.getHand();
        player.discardHand();
        assertEquals(player.getHand(), hand);

        // vil sjekke at en hand for en posisjon som allerede er snudd blir kastet
        hand = new Card('S', 4, true);
        player.setHand(hand);
        player.discardHand();
        assertEquals(player.getHand(), null);
        assertEquals(player.getDiscardedPile(), hand);
    }
    
}
