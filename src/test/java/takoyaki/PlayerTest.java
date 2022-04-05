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
    }

    @Test
	@DisplayName("Sjekker at tom konstruktør eksisterer")
	public void testConstructor() {
        player = new Player();
    }

    @Test
	@DisplayName("Sjekker at konstruktør for å laste inn et spill fungerer ")
	public void testConstructorForLoad() {
        player = new Player(hand, discardedPile, cardsAtTable);
    }

}
