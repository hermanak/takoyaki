package takoyaki;

public class Card {
    private char type;
    private int value;
    private char[] JavaCharArray = {'S', 'H', 'D', 'C'};
    private Boolean validType = false;
    private Boolean faceUp = false;

    public Card() {
        this.type = 'J';
        this.value = 0;
    }

    public Card(char type, int value) {
        for (char c : JavaCharArray) {
            if (c == type) {
                validType = true;
            }       
        }
        if(!validType){
            throw new IllegalArgumentException("Ugyldig verdiklasse");
        }
        if(value < 1 || value > 13) {
            throw new IllegalArgumentException("Ugyldig kort verdi");
        }
        this.type = type;
        this.value = value;
    }

    public char getSuit() {
        return type;
    }


    public int getFace() {
        return value;
    }

    // gjøre det lettere å skaffe begge verdier
    public String getSuitAndFace() {
        return type + String.valueOf(value);
    }

    public Boolean getFaceUp() {
        return faceUp;
    }

    // har bare behov for  gjøre et kort synlig
    public void flipCardUp() {
        if(!faceUp){
            faceUp = true;
        }
        else{
            throw new IllegalArgumentException("Kortet er allerede synlig!");
        }
    }

    @Override
    public String toString() {
        String res = (String.valueOf(type) + value);
        return res;
    }
    
}
