package takoyaki;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSaveHandler {
    private String tempPath;
    private Takoyaki t1;
    private SaveHandler sH1;

    private void checkCardWithFaceUP(Card card, char suit, int face, Boolean faceUp) {
		Assertions.assertEquals(card.getSuit(), suit);
		Assertions.assertEquals(card.getFace(), face);
		Assertions.assertEquals(card.getFaceUp(), faceUp);
	}

    private void checkCardDeck(CardDeck cD1, CardDeck cD2) {

        for (int i = 0; i < cD1.getCardCount(); i++) {
            checkCardWithFaceUP(cD1.getCard(i), 
            cD2.getCard(i).getSuit(),
            cD2.getCard(i).getFace(),
            cD2.getCard(i).getFaceUp()
            );
        }
	}

    private void checkPlayer(Player p1, Player p2) {
        for (int i = 0; i < 10; i++) {
            checkCardWithFaceUP(p1.getCardAtTable(i), 
            p2.getCardAtTable(i).getSuit(), 
            p2.getCardAtTable(i).getFace(), 
            p2.getCardAtTable(i).getFaceUp()
            );
        }
    }

    @BeforeEach
	public void setup() {
        t1 = new Takoyaki();
        sH1 = new SaveHandler();

    }

    @Test
	@DisplayName("Sjekker at SaveHandler kan laste inn en Takoyaki")
	public void testLoad() throws FileNotFoundException {
        sH1.save("save_file", t1);

        checkCardDeck(sH1.load("save_file").getCardDeck(), t1.getCardDeck());

        checkPlayer(sH1.load("save_file").getHuman(), t1.getHuman());
        checkPlayer(sH1.load("save_file").getComp(), t1.getComp());

        assertFalse(sH1.load("save_file").gameOver());
    }

    @Test
	public void testLoadNonExistingFile() {
		assertThrows(
				FileNotFoundException.class,
				() -> t1 = sH1.load("foo"),
				"FileNotFoundException bør kastes når file ikke finnes");
	}

    @Test
	public void testLoadInvalidFile() {
		assertThrows(
				Exception.class,
				() -> t1 = sH1.load("empty"),
				"empty.txt skal ikke være en fungerende fil");
        
	}

    @Test
	public void testEmptyProtected() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			sH1.save("empty", t1);
		}, "empty skal ikke kunne endres");
	}

    @Test
	@DisplayName("Sjekker at metoden skriver ut forventet liste")
	public void testSave() {
        try {
		    sH1.save("save_file", t1);
		} catch (FileNotFoundException e) {
			fail("Klarte ikk å lagre filen");
		}

        try {
		    sH1.save("save_file_test", t1);
		} catch (FileNotFoundException e) {
			fail("Klarte ikk å lagre filen");
		}

        byte[] testFile = null, newFile = null;

        // må fjerne første / for at koden skal fungere
        tempPath = SaveHandler.getFilePath("save_file").substring(1);
        

        try {
			testFile = Files.readAllBytes(Path.of(tempPath));
		} catch (IOException e) {
			fail("Could not load test file");
		}

        // må fjerne første / for at koden skal fungere
        tempPath = SaveHandler.getFilePath("save_file_test").substring(1);

		try {
			newFile = Files.readAllBytes(Path.of(tempPath));
		} catch (IOException e) {
			fail("Could not load saved file");
		}
        

        assertNotNull(testFile);
		assertNotNull(newFile);

		assertTrue(Arrays.equals(testFile, newFile));

		// tvinger AI til å handle slik at filene blir forskjellige
		t1.setHumanTurn(false);
		t1.theAIMove();

		try {
		    sH1.save("save_file_test", t1);
		} catch (FileNotFoundException e) {
			fail("Klarte ikk å lagre filen");
		}

		try {
			newFile = Files.readAllBytes(Path.of(tempPath));
		} catch (IOException e) {
			fail("Could not load saved file");
		}

		assertFalse(Arrays.equals(testFile, newFile));
    }

    @AfterAll
	static void teardown() {
		File newTestSaveFile = new File(SaveHandler.getFilePath("save_file"));
		newTestSaveFile.delete();
	}

}
