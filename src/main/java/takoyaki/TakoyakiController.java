package takoyaki;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class TakoyakiController {
    // skal brukes til å vise hvor mange kort som er ingen i bunken
    @FXML
    private Label Deck;

    // bruker knapper for at brukeren letter skal holde kontroll over kortene sine
    @FXML
    private Button humanCard1, humanCard2, humanCard3, humanCard4, humanCard5, humanCard6, humanCard7, humanCard8, humanCard9, humanCard10;


    // Trenger ikke å trykke på datamaskinen sine kort så de er lagret som Labels
    // også Label for søppelhaugene
    @FXML
    private Label deck, humanDiscardPile, computerDiscardPile, humanHand, computerHand, computerCard1, computerCard2, computerCard3, computerCard4, computerCard5, computerCard6, computerCard7, computerCard8, computerCard9, computerCard10, victor;

    @FXML
    private TextField filename;

    private Takoyaki takoyaki;
    private SaveHandler saveHandler;

    @FXML
    private void initialize(){
        takoyaki = new Takoyaki();
        saveHandler = new SaveHandler();
        setAllCards(); 
    }

    @FXML
    private void handleButtonClick1() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(1);
            setHumanHand();
            setHumanCard(humanCard1, 0);
            System.out.println("HumanHand: " + takoyaki.getHuman().getHand());
            throwUselessHumanHand();
            setHumanCard(humanCard1, 0);
        }   
        System.out.println("HumanHand: " + takoyaki.getHuman().getHand());
        endGame();
    }

    @FXML
    private void handleButtonClick2() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(2);
            setHumanHand();
            setHumanCard(humanCard2, 1);
            throwUselessHumanHand();
            setHumanCard(humanCard2, 1);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick3() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(3);
            setHumanHand();
            setHumanCard(humanCard3, 2);
            throwUselessHumanHand();
            setHumanCard(humanCard3, 2);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick4() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(4);
            setHumanHand();
            setHumanCard(humanCard4, 3);
            throwUselessHumanHand();
            setHumanCard(humanCard4, 3);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick5() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(5);
            setHumanHand();
            setHumanCard(humanCard5, 4);
            throwUselessHumanHand();
            setHumanCard(humanCard5, 4);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick6() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(6);
            setHumanHand();
            setHumanCard(humanCard6, 5);
            throwUselessHumanHand();
            setHumanCard(humanCard6, 5);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick7() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(7);
            setHumanHand();
            setHumanCard(humanCard7, 6);
            throwUselessHumanHand();
            setHumanCard(humanCard7, 6);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick8() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(8);
            setHumanHand();
            setHumanCard(humanCard8, 7);
            throwUselessHumanHand();
            setHumanCard(humanCard8, 7);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick9() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(9);
            setHumanHand();
            setHumanCard(humanCard9, 8);
            throwUselessHumanHand();
            setHumanCard(humanCard9, 8);
        }
        endGame(); 
    }

    @FXML
    private void handleButtonClick10() throws InterruptedException {
        if(takoyaki.getHumanTurn()) {
            takoyaki.getHuman().switchCard(10);
            setHumanHand();
            setHumanCard(humanCard10, 9);
            throwUselessHumanHand();
            setHumanCard(humanCard10, 9);
        }
        endGame(); 
    }

    @FXML
    private void handleLoad() throws FileNotFoundException  {
        takoyaki = saveHandler.load(filename.getText());
        setAllCards();
        // hvis spillet før man lastet inn var fulllført så må man fjerne resultat teksten
        victor.setText(""); 
    }

    @FXML
    private void handleSave() throws FileNotFoundException  {
        // save ble ikke laget for å takle tilstanden et spill er i etter at den er slutt. Derfor funker den ikke når spillet er over
        if(!takoyaki.gameOver()){
            saveHandler.save(filename.getText(), takoyaki);
        }    
        else {
            System.out.println("Ferdige spill kan ikke lagres");
        }
    }

    private void setHumanCard(Button currentButton, int position) {
        if(!takoyaki.getHuman().getCardAtTable(position).getFaceUp()){
            currentButton.setText("Down");
        }
        else{
            currentButton.setText(takoyaki.getHuman().getCardAtTable(position).toString());
        }
    }

    private void setHumanHand() {
        if(takoyaki.getHuman().getHand() != null) {
            humanHand.setText(takoyaki.getHuman().getHand().toString());
        }
        else {
            humanHand.setText("empty");
        }
        
    }

    // setter alle spillerens kort på bordet
    private void setAllHuman() {
        setHumanHand();
        setHumanCard(humanCard1, 0);
        setHumanCard(humanCard2, 1);
        setHumanCard(humanCard3, 2);
        setHumanCard(humanCard4, 3);
        setHumanCard(humanCard5, 4);
        setHumanCard(humanCard6, 5);
        setHumanCard(humanCard7, 6);
        setHumanCard(humanCard8, 7);
        setHumanCard(humanCard9, 8);
        setHumanCard(humanCard10, 9);
        setHumanDiscard();
    }

    // setter alle datamaskinens kort på bordet
    private void setAllComp() {
        setComputerHand();
        setComputerCard(computerCard1, 0);
        setComputerCard(computerCard2, 1);
        setComputerCard(computerCard3, 2);
        setComputerCard(computerCard4, 3);
        setComputerCard(computerCard5, 4);
        setComputerCard(computerCard6, 5);
        setComputerCard(computerCard7, 6);
        setComputerCard(computerCard8, 7);
        setComputerCard(computerCard9, 8);
        setComputerCard(computerCard10, 9);
        setComputerDiscard();
    }

    private void setComputerHand() {
        if(takoyaki.getComp().getHand() != null) {
            computerHand.setText(takoyaki.getComp().getHand().toString());     
        }
        else {
            computerHand.setText("empty");
        }
        
    }

    private void setHumanDiscard() {
        if(takoyaki.getHuman().getDiscardedPile() != null) {
            humanDiscardPile.setText(takoyaki.getHuman().getDiscardedPile().toString());    
        }
        else {
            humanDiscardPile.setText("empty");
        }
    }

    private void setComputerDiscard() {
        if(takoyaki.getComp().getDiscardedPile() != null) {
            computerDiscardPile.setText(takoyaki.getComp().getDiscardedPile().toString());   
        }
        else {
            computerDiscardPile.setText("empty");
        }
        
    }

    private void setComputerCard(Label currentLabel, int position) {
        if (takoyaki.getComp().getCardAtTable(position) == null) {
            currentLabel.setText("Empty");
        }

        if(!takoyaki.getComp().getCardAtTable(position).getFaceUp()) {
            currentLabel.setText("Down");
        }
        else{
            currentLabel.setText(takoyaki.getComp().getCardAtTable(position).toString());
        }
    }

    // klarte ikke å oppdatere kortene i samtid og kan ikke ha mye logikk her. Flytter mye over til Takoyaki senere
    // kanskje lage en ny klasse ComputerPlayer som arver fra Player
    private void setAfterAI() throws InterruptedException {
        takoyaki.theAIMove();
        TimeUnit.SECONDS.sleep(1);
        setAllCards();
    }

    private void throwUselessHumanHand() throws InterruptedException {
        takoyaki.getHuman().discardHand();
        setHumanHand();
        if(takoyaki.getHuman().getDiscardedPile() != null) {
            System.out.println("Nytt kast");
            setHumanDiscard();
        }
        setAfterAI(); 
        
    }

    private void updateDeck() {
        deck.setText(String.valueOf(takoyaki.getCardDeck().getCardCount()));
    }

    // setter alle kortene
    private void setAllCards() {
        updateDeck();
        // setter alle spillerens kort på bordet
        setAllHuman();

        // setter alle datamaskinens kort på bordet
        setAllComp();
    }

    private void endGame(){
        if(takoyaki.gameOver()) {
            // for å være sikker på at alle kort er oppdatert
            setAllCards();
            if(takoyaki.getHuman().allCardsFlipped()) {
                victor.setText("YOU WON!");
            }
            else if(takoyaki.getComp().allCardsFlipped()){
                victor.setText("YOU LOST");
            }
            else {
                victor.setText("TIE!");
            }
        }
    }
}
