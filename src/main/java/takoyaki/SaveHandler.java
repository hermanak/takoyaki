package takoyaki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveHandler implements ISaveHandler {
    
    @Override
    public void save(String filename, Takoyaki game) throws FileNotFoundException {
        if(filename.endsWith("empty")) {
            throw new IllegalArgumentException("empty.txt skal bare brukes for at saves dukker opp i target. Den skal være tom og ubrukelig");
        }
        try (PrintWriter writer = new PrintWriter(new File(getFilePath(filename)))) {
            int deckSize = 0;

			writer.println("# cardDeck");
            deckSize = game.getCardDeck().getCardCount();
            for (int index = 0; index < deckSize; index++) {
                if(game.getCardDeck().getCard(index).getSuit() == 'J'){
                    writeJoker(writer, index);
                }
                else {
                    Card card = game.getCardDeck().getCard(index);
                    writeCardDeck(writer, card, index);
                }
            }
            writer.println("! slutt på cardDeck"); // har den for å summere listen sammen
            writer.println("# human");

            // skriver kortene på bordet for human
            for (int index = 0; index < 10; index++) {
                if(game.getHuman().getCardAtTable(index).getSuit() == 'J'){
                    writeJokerWithFaceUp(writer, game.getHuman().getCardAtTable(index), index);
                }
                else {
                    Card card = game.getHuman().getCardAtTable(index);
                    writeCard(writer, card, index);
                }
                
            }

            // skriver hånden til human
            writer.println("* hand");  
            
            if(game.getHuman().getHand() == null){
                writer.println("null");
            }
            else {
                if(game.getHuman().getHand().getSuit() == 'J'){
                    writer.println("J");
                }
                else {
                    
                    writer.println(game.getHuman().getHand().getSuit());
                    writer.println(game.getHuman().getHand().getFace());
                }   
            }  
            

            // skriver discardpile. Skjekker om det er null først i tilfelle spillet akkurat har startet
            // discard trenger aldri å ta hensyn til jokere fordi en joker vil adlri bli kastet
            writer.println("* discard");
            if(game.getHuman().getDiscardedPile() == null){
                writer.println("null");
            }
            else {
                writer.println(game.getHuman().getDiscardedPile().getSuit());
                writer.println(game.getHuman().getDiscardedPile().getFace());
            }
            

            writer.println("!  slutt på human"); // har den for å summere listen sammen

            writer.println("# computer");
            // skriver kortene på bordet for computer
            for (int index = 0; index < 10; index++) {
                if(game.getComp().getCardAtTable(index).getSuit() == 'J'){
                    writeJokerWithFaceUp(writer, game.getComp().getCardAtTable(index), index);
                }
                else {
                    Card card = game.getComp().getCardAtTable(index);
                    writeCard(writer, card, index);
                }
                
            }

            // skriver hånden til computer
            writer.println("* hand");  
            if(game.getComp().getHand() == null){
                writer.println("null");
            }
            else {
                if(game.getComp().getHand().getSuit() == 'J'){
                    writer.println("J");
                }
                else {
                    
                    writer.println(game.getComp().getHand().getSuit());
                    writer.println(game.getComp().getHand().getFace());
                }   
            } 
                
                
            

            // skriver discardpile. Skjekker om det er null først i tilfelle spillet akkurat har startet
            // discard trenger aldri å ta hensyn til jokere fordi en joker vil aldri bli kastet
            writer.println("* discard");
            if(game.getComp().getDiscardedPile() == null){
                writer.println("null");
            }
            else {
                writer.println(game.getComp().getDiscardedPile().getSuit());
                writer.println(game.getComp().getDiscardedPile().getFace());
            }
            

            writer.println("!  slutt på computer"); // har den for å summere listen sammen

		}
    }

    // laster inn tekstfilen
    @Override
    public Takoyaki load(String filename) throws FileNotFoundException {
        Takoyaki t1 = null;
        CardDeck cD1 = null;
        Player player = null;
        ComputerPlayer computerPlayer = null;
        List<Card> cards = new ArrayList<Card>();
        Card hand = null;
        Card tempCard = null;
        char type = ' ';
        int value = -1;
        Boolean faceUp = null;
        // Booleans holder styr på hvilke verdier man bestemmer for t1
        Boolean loadCardDeck = true;
        Boolean loadHuman = true;
        Boolean handChecked = false;
        try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
            t1 = new Takoyaki();
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().stripTrailing();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                else if ( line.startsWith("*")) {
                    // alle if kodene trenger type og den kommer alltid først etter *
                    type = scanner.nextLine().charAt(0);
                    // koden her er for kort til CardDeck
                    if(loadCardDeck) {                          
                        // joker har alltid samme verdi
                        if(type == 'J') {
                            tempCard = new Card();
                            cards.add(tempCard);
                        }
                        else {
                            value = scanner.nextInt();
                            tempCard = new Card(type, value);
                            cards.add(tempCard);
                        }
                    }
                    // koden for å finne kortene human og computer skal ha. Først defineres human og deretter computer
                    else {
                        // definerer hand
                        if(cards.size() == 10 && !handChecked ) {
                            // n blir ikke brukt andre steder og type er en enkel char så det er lettere å sammenligne det direkte
                            if(type == 'n'){
                                tempCard = null;
                            }
                            else {
                                // koden klarer ikke å godta at hand er joker
                                if(type == 'J') {
                                    // her er Joker oppe fordi det gir ikke mening at man ikke kan se hånden
                                    hand = new Card(true);
                                }
                                else {
                                    value = scanner.nextInt();
                                    hand = new Card(type, value, true);;
                                } 
                            }            
                            handChecked = true;
                        }
                        // definerer discard
                        else if (cards.size() >= 10 ) {
                            // n blir ikke brukt andre steder og type er en enkel char så det er lettere å sammenligne det direkte
                            if(type == 'n'){
                                tempCard = null;
                            }
                            else {
                                value = scanner.nextInt();
                                tempCard = new Card(type, value, true);;                               
                            }
                            // slik at koden kjøres riktig flere ganger
                            handChecked = false;
                        }
                        // det under definerer kortene på bordet
                        else {

                            // jokeren står sammen med om den er snudd opp eller ned
                            if(type == 'J') {
                                faceUp = scanner.nextBoolean();
                                tempCard = new Card(faceUp);
                                cards.add(tempCard);
                            }
                            else {
                                value = scanner.nextInt();
                                faceUp = scanner.nextBoolean();
                                tempCard = new Card(type, value, faceUp);
                                cards.add(tempCard);
                            }    
                        }
                    }
                }
                else if (line.startsWith("!")) {
                    if(loadCardDeck){
                        cD1 = new CardDeck(cards);
                        t1.setCardDeck(cD1);
                        loadCardDeck = false;
                        cards = new ArrayList<Card>();
                    }
                    else if(loadHuman) {
                        player = new Player(hand, tempCard, cards);
                        t1.setHuman(player);
                        loadHuman = false;
                        cards = new ArrayList<Card>();
                        player = null;
                        hand = null;
                    }
                    else {
                        computerPlayer = new ComputerPlayer(hand, tempCard, cards);
                        t1.setComp(computerPlayer);
                    }
                }
                
            }
        }

        validNewGame(t1);

        return t1;
    }

    // trenger ikke å endre faceUp sann siden alle kortene har det som usant
    private void writeCardDeck(PrintWriter writer, Card c1, int i) throws FileNotFoundException {
        writer.println("*" + (i + 1)); // CardDeck har get metode som setter verdiene sine inn direkte
        writer.println(c1.getSuit());
        writer.println(c1.getFace());
    }

    private void writeCard(PrintWriter writer, Card c1, int i) throws FileNotFoundException {
        writer.println("*" + (i + 1));
        writer.println(c1.getSuit());
        writer.println(c1.getFace());
        writer.println(c1.getFaceUp());
    }

    private void writeJoker(PrintWriter writer, int i) {
        writer.println("*" + (i + 1));
        writer.println("J");
    }

    private void writeJokerWithFaceUp(PrintWriter writer, Card c1, int i) {
        writer.println("*" + (i + 1));
        writer.println("J");
        writer.println(c1.getFaceUp());
    }
    
    
	public static String getFilePath(String filename) {
        // lagret ting på skyen der mellomrom skapte problemer
        String tempString = SaveHandler.class.getResource("saves/").getFile();
        tempString = insertSpace(tempString);
        // Onedrive liker ikke lagringsfilene
		return tempString + filename + ".txt";
	}

    // for å unngå at problemer skapes ved at koden ikke aksepterer %20 som mellomrom
    private static String insertSpace(String s) {
        String[] stringArray = s.split("%20");
        StringBuffer sb = new StringBuffer();
        for(String s3 : stringArray) {
            sb.append(s3);
            sb.append(" ");
        }

        return sb.toString();
    }

    // sier om noen trekk er gjort i spillet eller ikke. Brukes for å forhindre lasting av ugyldige spill
    private void validNewGame(Takoyaki t1) { 
        boolean start = true;

        for (Card c1 : t1.getHuman().getCardsAtTable()) {
            if(c1.getFaceUp()){
                start = false;
            }
        }

        // så lenge menneskets discardPile er tom eller ingen kort er snudd er det et nytt spill
        if(t1.getHuman().getDiscardedPile() == null && !start){
            if(t1.getComp().getDiscardedPile() != null) {
                throw new IllegalArgumentException("computer sin discardPile skal være tom ved starten av et spill");
            }

            for (Card c : t1.getHuman().getCardsAtTable()) {
                if(c.getFaceUp()){
                    throw new IllegalArgumentException("Ingen kort skal være snudd på starten");
                }
            }

            for (Card c : t1.getComp().getCardsAtTable()) {
                if(c.getFaceUp()){
                    throw new IllegalArgumentException("human skal alltid ha gjort et trekk før computer. Dette spillet er ikke gyldig");
                }
            }

            if(t1.getHuman().getHand() == null || t1.getComp().getHand() == null ){
                throw new IllegalArgumentException("Alle skal ha et Card på Hand i starten");
            }
        }
    }

    // brukes til å teste, slett senere
    public static void main(String[] args) throws FileNotFoundException {
        
        

        Takoyaki t1 = new Takoyaki();
        Takoyaki t2 = new Takoyaki();
        Takoyaki t3 = null;

        SaveHandler s1 = new SaveHandler();;

        s1.load("empty");

        s1.save("save_file", t1);
        s1.save("save_file1", t2);
        s1.save("xd", t1);

        t3 = s1.load("save_file");
        System.out.println(t3);
        System.out.println("Deck: " + t3.getCardDeck().getCard(0));
        System.out.println("Deck: " + t3.getCardDeck().getCard(1));
        System.out.println("DeckCount: " + t3.getCardDeck().getCardCount());
        
        System.out.println("Human: " + t3.getHuman().getCardAtTable(0));
        System.out.println("Human: " + t3.getHuman().getCardAtTable(1));
        System.out.println("HumanHand: " + t3.getHuman().getHand());
        System.out.println("Discard: " + t3.getHuman().getDiscardedPile());

        for (int index = 0; index < 10; index++) {
            System.out.println("Computer: " + t3.getComp().getCardAtTable(index));
        }
        
        System.out.println("ComputerHand: " + t3.getComp().getHand());
        System.out.println("Discard: " + t3.getComp().getDiscardedPile());

        // koden her er for å løse problemet med å laste og lagre joker for hand.
        Takoyaki t4 = new Takoyaki();
        Takoyaki t5;
        t4.getHuman().setHand(new Card(true));

        System.out.println("human hand: " + t4.getHuman().getHand());
        s1.save("save_file40", t4);

        t5 = s1.load("save_file40");

        System.out.println("human hand: " + t5.getHuman().getHand());

    }
}