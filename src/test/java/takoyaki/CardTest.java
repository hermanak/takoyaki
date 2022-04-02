package takoyaki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardTest {
    private void checkCard(Card card, char suit, int face) {
		Assertions.assertEquals(card.getSuit(), suit);
		Assertions.assertEquals(card.getFace(), face);
	}

	private void checkCardWithFaceUP(Card card, char suit, int face, Boolean faceUp) {
		Assertions.assertEquals(card.getSuit(), suit);
		Assertions.assertEquals(card.getFace(), face);
		Assertions.assertEquals(card.getFaceUp(), faceUp);
	}

	@Test
	@DisplayName("Sjekk at joker konstruktøren opprettes med riktige verdier")
	public void testJokerConstructor() {
		checkCard(new Card(), 'J', 0);
	}

	@Test
	@DisplayName("Sjekk at joker konstruktøren med FaceUp opprettes med riktige verdier")
	public void testJokerConstructorWithFaceUp() {
		checkCardWithFaceUP(new Card(true), 'J', 0, true);
		checkCardWithFaceUP(new Card(false), 'J', 0, false);
	}


	@Test
	@DisplayName("Sjekk at konstruktøren oppretter Card-objekter med riktige verdier")
	public void testConstructor() {
		checkCard(new Card('S', 1), 'S', 1);
		checkCard(new Card('S', 13), 'S', 13);
		checkCard(new Card('H', 1), 'H', 1);
		checkCard(new Card('H', 13), 'H', 13);
		checkCard(new Card('D', 1), 'D', 1);
		checkCard(new Card('D', 13), 'D', 13);
		checkCard(new Card('C', 1), 'C', 1);
		checkCard(new Card('C', 13), 'C', 13);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Card('X', 1);
		}, "Skal ikke kunne lage et kort av typen X");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Card('S', 0);
		}, "Skal ikke kunne lage et kort med verdi 0");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Card('C', 14);
		}, "Skal ikke kunne lage et kort med verdi 14");
	}

	@Test
	@DisplayName("Sjekk at konstruktøren med FaceUp oppretter Card-objekter med riktige verdier")
	public void testConstructorWithFaceUp() {
		checkCardWithFaceUP(new Card('S', 1, true), 'S', 1, true);
		checkCardWithFaceUP(new Card('S', 13, false), 'S', 13, false);
		checkCardWithFaceUP(new Card('H', 1, true), 'H', 1, true);
		checkCardWithFaceUP(new Card('H', 13, true), 'H', 13, true);
		checkCardWithFaceUP(new Card('D', 1, false), 'D', 1, false);
		checkCardWithFaceUP(new Card('D', 13, true), 'D', 13, true);
		checkCardWithFaceUP(new Card('C', 1, false), 'C', 1, false);
		checkCardWithFaceUP(new Card('C', 13, true), 'C', 13, true);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Card('S', 1, null);
		}, "Skal ikke kunne lage et kort som har null som faceUp");
	}

	@Test
	@DisplayName("Sjekk at flipCardUp() fungerer som forventet")
	public void testFlipCardUp()  {
		Card c1 = new Card();
		checkCardWithFaceUP(c1, 'J', 0, false);
		c1.flipCardUp();
		checkCardWithFaceUP(c1, 'J', 0, true);
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			c1.flipCardUp();
		}, "Skal ikke snu opp et kort som allered er synlig");
		
	}

	@Test
	@DisplayName("Sjekk at toString fungerer som forventet")
	public void testToString() {
		Assertions.assertEquals("S1", new Card('S', 1).toString());
		Assertions.assertEquals("H13", new Card('H', 13).toString());
	}
}
