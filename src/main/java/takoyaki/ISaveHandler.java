package takoyaki;

import java.io.FileNotFoundException;

// kopi av snakebird, men koden som bruker interface måtte endres mye mer
public interface ISaveHandler {
    
    public void save(String filename, Takoyaki game) throws FileNotFoundException;

	public Takoyaki load(String filename) throws FileNotFoundException;
}
