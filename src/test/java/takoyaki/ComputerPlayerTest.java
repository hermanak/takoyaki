package takoyaki;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {
    private ComputerPlayer computerPlayer;
    private Card hand;
    private Card discardedPile;
    private List<Card> cardsAtTable = new ArrayList<Card>();
    private List<Integer> possibleMoves = new ArrayList<Integer>();
    private CardDeck cardDeck;

    @BeforeEach
	public void setup() {
        cardDeck = new CardDeck(13,2);

        for (int i = 0; i < 10; i++) {
			cardsAtTable.add(cardDeck.getCard(cardDeck.getCardCount() - (i + 1)));
		}
        for (int i = 0; i < 10; i++) {
            
            possibleMoves.add(i + 1);
        }

        // må gis egne verdier. Dette er fordi å bruke flipCard mens de fortsatt befinner seg i cardDeck skaper problemer man ellers ikke møter
        discardedPile = new Card('C',5);
        discardedPile.flipCardUp();
        hand = new Card('C',4);
        hand.flipCardUp();
    }

    @Test
	@DisplayName("Sjekker at tom konstruktør eksisterer")
	public void testConstructor() {
        computerPlayer = new ComputerPlayer();
    }

    @Test
	@DisplayName("Sjekker at konstruktør for å laste inn et spill fungerer ")
	public void testConstructorForLoad() {
        computerPlayer = new ComputerPlayer(hand, discardedPile, cardsAtTable);

        assertEquals(computerPlayer.getHand(), hand);
        assertEquals(computerPlayer.getDiscardedPile(), discardedPile);
        assertEquals(computerPlayer.getCardsAtTable(), cardsAtTable);

        hand = new Card('C', 4, false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
			computerPlayer = new ComputerPlayer(hand, discardedPile, cardsAtTable);
		}, "Ingen slike spill bør ha blitt lagret");
    }

    @Test
	@DisplayName("Sjekker at possibleMoves gir forventede verdier")
	public void testPossibleMoves() {
        computerPlayer = new ComputerPlayer(hand, discardedPile, cardsAtTable);
        // alle trekk bør være mulig fordi ingenting Cards har blitt byttet
        assertEquals(computerPlayer.possibleMoves(), possibleMoves);

        computerPlayer.switchCard(4);
        possibleMoves.remove(3);
        // etter at kort ved posisjon 4 er blitt byttet så bør det ikke lenger være et mulig trekk
        assertEquals(computerPlayer.possibleMoves(), possibleMoves);
    }

}
