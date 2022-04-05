package takoyaki;

import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardDeckTest {
    
	private CardDeck cardDeck;
	private CardDeck jokerCardDeck;
	private CardDeck gameCardDeck;
	private CardDeck tempCardDeck;
	private List<Card> savedCards = new ArrayList<Card>();
	private Player player;

	private void checkDeck(CardDeck deck, String deckAsString) {
		Collection<String> toStrings = Arrays.asList(deckAsString.split(","));
		Assertions.assertEquals(toStrings.size(), deck.getCardCount(), "CardDeck har ikke korrekt størrelse");
		int i = 0;
		for (String toString : toStrings) {
			Card card = deck.getCard(i);
			String cardString = String.valueOf(card.getSuit()) + card.getFace();
			Assertions.assertEquals(toString, cardString,
					String.format("Card på plass %d var feil. CardDeck skulle vært %s", i + 1, toStrings));
			i++;
		}
	}

	@BeforeEach
	public void setup() {
		cardDeck = new CardDeck(2);
		jokerCardDeck = new CardDeck(2, 2);
		gameCardDeck = new CardDeck(13, 2);
		player = new Player();
	}

	@Test
	@DisplayName("Sjekk at Deck blir initialisert til to S1,S2,H1,H2,D1,D2,C1,C2")
	public void testConstructor() {
		checkDeck(cardDeck, "S1,S2,H1,H2,D1,D2,C1,C2");
	}

	@Test
	@DisplayName("Sjekk at Deck med Joker blir initialisert til to S1,S2,H1,H2,D1,D2,C1,C2,J0,J0")
	public void testConstructorWithJoker() {
		checkDeck(jokerCardDeck, "S1,S2,H1,H2,D1,D2,C1,C2,J0,J0");
	}

	@Test
	@DisplayName("Sjekk at Deck med Joker kan lages fra en lagret tilstand")
	public void testConstructorForSaving() {
		// er ikke i BeforeEach fordi den brukes bare her
		for (int i = 0; i < jokerCardDeck.getCardCount(); i++) {
			savedCards.add(jokerCardDeck.getCard(i));
		}

		tempCardDeck = new CardDeck(savedCards);

		// kan ikke sammenligne objektene direkte så må sammenligne kortene
		for (int i = 0; i < jokerCardDeck.getCardCount(); i++) {
			assertEquals(jokerCardDeck.getCard(i), tempCardDeck.getCard(i));
		}
		
	}

	@Test
	@DisplayName("Sjekk at CardDeck blir stokket til S1,D1,S2,D2,H1,C1,H2,C2")
	public void testShufflePerfectly() {
		cardDeck.shufflePerfectly();
		checkDeck(cardDeck, "S1,D1,S2,D2,H1,C1,H2,C2");
	}

	@Test
	@DisplayName("Sjekk at jokerCardDeck blir stokket til D2,C1,C2,J0,J0,S1,S2,H1,H2,D1")
	public void testSwitchTopAndBottom() {
		jokerCardDeck.switchTopAndBottom();
		
		checkDeck(jokerCardDeck, "D2,C1,C2,J0,J0,S1,S2,H1,H2,D1");
	}

	@Test
	@DisplayName("Sjekk at kort ble fordelt til Player riktig") 
	public void testDealCardsAtTable() {
		// tenket å lage en liste av kortene som fjernes av koden
		// deretter sammenligne de kortene med player sine
		// burde jeg også sjekke at kortene er borte fra den originale bunken?
		
		gameCardDeck.dealCardsAtTable(player);

		
	}
}
