
/**
 * Animals.java
 * 
 * This is the super class that contains all of the methods used by the subclasses,
 * as well as the variables
 * 
 * Usage instructions:
 * 
 * Construct an Animal
 * Animals animal = new Animals();
 * 
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Animals {
    public static final String[] VALS = new String[] { "left", "right", "up",
            "down" };
    // This set is used to determine if a move is of the correct nature
    public static final Set<String> MOVES = new HashSet<>(Arrays.asList(VALS));
    protected int birdNum;
    protected int reproduces = 5;
    protected int moves = 0;
    protected int lastEaten = 0;
    protected String direction;
    protected int cordX;
    protected int cordY;
    protected Ecosystem ecosystem;
    protected String sex;
    protected char letter;

    /*
     * This is the constructor for the animal class
     */
    public Animals() {
    }

    /*
     * This method increments the number of moves and the last time the
     * animal has eaten by 1
     */
    void incrementMoves() {
        this.moves += 1;
        this.lastEaten += 1;
    }

    /*
     * This method is used to set the animals coordinates to x, y
     * PARAMS: x -- The x value of the animal
     * y -- The y value of the animal
     */
    void setCords(int x, int y) {
    }

    /*
     * This method is used to move the animal based on its type
     */
    void move() {
    }

    /*
     * This method sets the ecosystem of the animal to ecosystem e
     * PARAMS e -- The ecosystem the animal is in
     */
    void setEcosystem(Ecosystem e) {
    }

    /*
     * This method adds the animal into its ecosystem
     */
    void addInto() {
    }

    /*
     * This method trys to make the animal reproduce, and if possible,
     * reproduces
     */
    void reproduce(Animals other) {
    }

    /*
     * This method returns the type of the animal as a string
     */
    String type() {
        return null;
    }

    /*
     * This method tries to make the animal eat, and if possible, eats
     */
    void eat(Animals other) {
    }

    /*
     * This method returns the color of the animal
     */
    public String getColor() {
        return "black";
    }

    /*
     * This returns a random value between 0 and 4
     * RETURN -- A random value between 0 and 4 as a string
     */
    public String getRandDir() {
        Random rand = new Random();
        int val = rand.nextInt(50);
        return VALS[val % 4];
    }

    /*
     * This method returns a random number between 1 and 10
     * RETURN -- A number between 1 and 10 as a string
     */
    public String getRandInt() {
        Random rand = new Random();
        return String.valueOf((rand.nextInt(9) + 1));
    }

    /*
     * This method returns a string of either male or female, randomly
     * RETURN -- A string of male or female, randomly decided
     */
    public String getRandGender() {
        Random rand = new Random();
        int val = rand.nextInt(50);
        if (val % 2 == 0) {
            return "female";
        } else {
            return "male";
        }
    }

    /*
     * This method returns a random boolean value
     * RETURN -- A random boolean value
     */
    public String getRandBool() {
        Random rand = new Random();
        int val = rand.nextInt(50);
        return String.valueOf((val % 2) == 0);
    }
}
