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

    // for når joker må definere hvilken faceUp den har
    public Card(Boolean faceUp) {
        this.type = 'J';
        this.value = 0;
        this.faceUp = faceUp;
    }

    public Card(char type, int value) {
        for (char c : JavaCharArray) {
            if (c == type) {
                validType = true;
            }       
        }
        if(!validType){
            throw new IllegalArgumentException("Ugyldig verdiklasse for: " + type);
        }
        if(value < 1 || value > 13) {
            throw new IllegalArgumentException("Ugyldig kort verdi");
        }
        this.type = type;
        this.value = value;
    }

    // brukes for lagring av kort der det varier om de er snudd eller ikke
    public Card(char type, int value, Boolean faceUp ) {
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
        if(faceUp == null) {
            throw new IllegalArgumentException("Et kort må være snudd opp eller ned");
        }
        this.type = type;
        this.value = value;
        this.faceUp = faceUp;
    }

    public char getSuit() {
        return type;
    }


    public int getFace() {
        return value;
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
            throw new IllegalStateException("Kortet er allerede synlig!");
        }
    }

    @Override
    public String toString() {
        String res = (String.valueOf(type) + value);
        return res;
    }
    
}
