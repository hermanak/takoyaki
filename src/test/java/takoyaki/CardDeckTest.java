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
    private int count;
	private Card hand;
	private CardDeck cardDeck;
	private CardDeck jokerCardDeck;
	private CardDeck gameCardDeck;
	private CardDeck tempCardDeck;
	private List<Card> cards = new ArrayList<Card>();
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

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			cardDeck = new CardDeck(-1);
		}, "-1 kort skal ikke være lov");
	}

	@Test
	@DisplayName("Sjekk at Deck med Joker blir initialisert til to S1,S2,H1,H2,D1,D2,C1,C2,J0,J0")
	public void testConstructorWithJoker() {
		checkDeck(jokerCardDeck, "S1,S2,H1,H2,D1,D2,C1,C2,J0,J0");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			jokerCardDeck = new CardDeck(-1, 2);
		}, "-1 kort skal ikke være lov");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			jokerCardDeck = new CardDeck(1, 3);
		}, "jokere må være partall");
	}

	@Test
	@DisplayName("Sjekk at Deck med Joker kan lages fra en lagret tilstand")
	public void testConstructorForSaving() {
		// er ikke i BeforeEach fordi den brukes bare her
		for (int i = 0; i < jokerCardDeck.getCardCount(); i++) {
			cards.add(jokerCardDeck.getCard(i));
		}

		tempCardDeck = new CardDeck(cards);

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
		
		count = gameCardDeck.getCardCount();

		for (int i = 0; i < 10; i++) {
			cards.add(gameCardDeck.getCard(count - (i + 1)));
		}

		gameCardDeck.dealCardsAtTable(player);

		for (int j = 0; j < 10; j++) {
			assertEquals(cards.get(j), player.getCardAtTable(j));
		}
		
		if(count != gameCardDeck.getCardCount() + 10){
			throw new IllegalStateException("Det skal akkurat være 10 kort borte");
		}
		
		// må gå igjennom hele listen fordi det er ikke mulig å hente CardDeck sin cards direkte
		for (int i = 0; i < gameCardDeck.getCardCount(); i++) {
			for (Card card : cards) {
				// kan finnes joker flere ganger
				if(!card.toString().equals("J0")) {
					assertNotEquals(card, gameCardDeck.getCard(i));
				}
			}
		}

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			cardDeck.dealCardsAtTable(player);;
		}, "Skal ikke kunne gi nye kort på borde til en player som allerede har");
	}

	@Test
	@DisplayName("Sjekk at kort ble fordelt til Player riktig") 
	public void testGiveNewHand() {
		hand = cardDeck.getCard(cardDeck.getCardCount() - 1);
		cardDeck.giveNewHand(player);
		assertEquals(hand, player.getHand());

		if(!hand.toString().equals("J0")) {
			// må gå igjennom hele listen fordi det er ikke mulig å hente CardDeck sin cards direkte
			for (int i = 0; i < gameCardDeck.getCardCount(); i++) {
				
				assertNotEquals(hand, gameCardDeck.getCard(i));
			}
		}

		// setter hand til null slik at player kan få en ny en
		player.setHand(null);;
		hand = jokerCardDeck.getCard(jokerCardDeck.getCardCount() - 1);
		jokerCardDeck.giveNewHand(player);
		assertEquals(hand, player.getHand());

		if(!hand.toString().equals("J0")) {
			// må gå igjennom hele listen fordi det er ikke mulig å hente CardDeck sin cards direkte
			for (int i = 0; i < gameCardDeck.getCardCount(); i++) {
				
				assertNotEquals(hand, gameCardDeck.getCard(i));
			}
		}

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			cardDeck.giveNewHand(player);;
		}, "Skal ikke gi hand for en playr som fortsatt har hand");

		count = jokerCardDeck.getCardCount();
		// her skjekke at en tom cardDeck ikke gir player kort 
		for (int i = 0; i < count; i++) {
			player.setHand(null);
			jokerCardDeck.giveNewHand(player);
		}
		player.setHand(null);
		Assertions.assertThrows(IllegalStateException.class, () -> {
			jokerCardDeck.giveNewHand(player);;
		}, "Når det er tomt for kort så skal ikke flere hand bli gitt");

	}

}
