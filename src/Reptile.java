
/**
 * Reptile.java
 * 
 * This is my special animal class
 * This is the class that creates an reptile object
 * 
 * Usage instructions:
 * 
 * Construct a Reptile
 * Reptile I = new Reptile(x, y, type, sex, moves);
 * 
 * Useful methods:
 * Reptile.setEcosystem(Ecosystem e)
 * 
 * Reptile.move()
 * 
 * Reptile.getColor()
 * 
 * Reptile.eat(Animals other)
 * 
 * Reptile.reproduce(Animals other)
 * 
 * Reptile.addInto();
 * 
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Reptile extends Animals {
    public int reproduces = 1;
    private String[] colors = new String[] { "lavender", "wheat", "firebrick" };
    private String type;
    public static final String[] VALS = new String[] { "snake", "lizard",
            "crocodile" };
    public final Set<String> REPTILES = new HashSet<>(
            Arrays.asList(VALS));

    /*
     * This is the constructor for the Reptile class
     */
    public Reptile(int x, int y, String type, String sex, String moves) {
        super.cordX = x;
        super.cordY = y;
        this.type = type.toLowerCase();
        super.sex = sex.toLowerCase();
        super.letter = type.charAt(0);
        super.direction = "right";
        this.moves = Integer.parseInt(moves);
    }

    /*
     * This is the default constructor for the Reptile class
     */
    public Reptile() {
    }

    /*
     * Set this object to the ecosystem object e
     */
    void setEcosystem(Ecosystem e) {
        super.ecosystem = e;
    }

    /*
     * This method returns the color of the animal as a string
     */
    public String getColor() {
        int i = 0;
        for (String reptile : this.REPTILES) {
            if (this.type.equals(reptile)) {
                return this.colors[i];
            } else {
                i++;
            }
        }
        return null;
    }

    /*
     * Overriding the toString method for this class
     */
    public String toString() {
        return "reptile";
    }

    /*
     * This method returns the type of the bird as a string
     */
    public String type() {
        return this.type;
    }

    /*
     * This method is the reproduce method for the reptile class
     */
    void reproduce(Animals other) {
        if (this.reproduces > 0 && other.reproduces > 0) {
            this.reproduces -= 1;
            other.reproduces -= 1;
            Animals newReptile = new Reptile(cordX, cordY, type, sex, "2");
            newReptile.setEcosystem(this.ecosystem);
            this.ecosystem.add(newReptile);
        }
    }

    /*
     * This is the move method for the reptile class. Reptiles move
     * alternating between up an down num times, starting with right
     */
    void move() {
        if (super.moves >= 40 || super.lastEaten >= 5) {
            this.ecosystem.remove(cordX, cordY, this);
            return;
        }
        if (super.direction == "right") {
            super.cordY += 1;
            super.direction = "up";
        } else {
            super.cordX -= 1;
            super.direction = "right";
        }
        this.addInto();
        super.incrementMoves();
    }

    /*
     * This method adds the Reptile into its ecosystem
     */
    void addInto() {
        ecosystem.add(this);
    }

    /*
     * This eat command will only work if the bird tries to
     * eat an insect
     * PARAMS -- other: The animal trying to be eaten
     */
    void eat(Animals other) {
        if (this.type == "snake" && other.toString() == "bird") {
            super.lastEaten = 0;
            this.ecosystem.remove(other.cordX, other.cordY, other);
        } else if (other.toString() == "insect") {
            super.lastEaten = 0;
            this.ecosystem.remove(other.cordX, other.cordY, other);
        }
    }
}