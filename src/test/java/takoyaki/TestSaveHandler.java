package takoyaki;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class TestSaveHandler {
    private Takoyaki t1;
    private SaveHandler sH1;
    private List<Card> cards = new ArrayList<Card>();

    private void checkCardWithFaceUP(Card card, char suit, int face, Boolean faceUp) {
		Assertions.assertEquals(card.getSuit(), suit);
		Assertions.assertEquals(card.getFace(), face);
		Assertions.assertEquals(card.getFaceUp(), faceUp);
	}

    private void checkCardCardDeck(CardDeck cD1, CardDeck cD2) {

        for (int i = 0; i < cD1.getCardCount(); i++) {
            checkCardWithFaceUP(cD1.getCard(i), 
            cD2.getCard(i).getSuit(),
            cD2.getCard(i).getFace(),
            cD2.getCard(i).getFaceUp()
            );
        }
	}

    

    /*
    @BeforeEach
	public void setup() {
        sH1 = new SaveHandler();
    }
    */

    @Test
	@DisplayName("Sjekker at metoden skriver ut forventet liste")
	public void testSave() {

    }

    @Test
	@DisplayName("Sjekker at metoden laster inn forventet Takoyaki")
	public void testLoad() {

    }

    @Test
	@DisplayName("Sjekker at SaveHandler kan lagre en Takoyaki og laste den inn igjen")
	public void testSaveAndLoad() throws FileNotFoundException {
        t1 = new Takoyaki();
        sH1 = new SaveHandler();

        sH1.save("save_file", t1);

        checkCardCardDeck(sH1.load("save_file").getCardDeck(), t1.getCardDeck());

        //assertEquals(sH1.load("save_file").getCardDeck(), t1.getCardDeck());

        //assertEquals(sH1.load("save_file"), t1);
    }
}