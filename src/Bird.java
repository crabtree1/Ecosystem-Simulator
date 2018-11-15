
/**
 * Bird.java
 * 
 * This is the class that is used to create a Bird in the ecosystem.
 * 
 * Usage instructions:
 * 
 * Construct an Bird
 * Bird b = new Bird(x, y, type, sex, moves);
 * 
 * Useful methods:
 * Bird.setEcosystem(Ecosystem e)
 * 
 * Bird.getColor()
 * 
 * Bird.move()
 * 
 * Bird.eat(Animals other)
 * 
 * Bird.reproduce(Animals other)
 * 
 * Bird.addInto();
 */

import java.util.HashSet;
import java.util.Set;

enum birdTypes {
    thrush, owl, warbler, dove, shrike;
}

public class Bird extends Animals {
    private int currentMoves = 0;
    private birdTypes type;
    private String[] colors = new String[] { "tan", "magenta", "white",
            "black", "gray" };

    /*
     * This is the constructor for the bird class
     */
    public Bird(int x, int y, String type, String sex, String num) {
        super.direction = "down";
        super.cordX = x;
        super.cordY = y;
        super.sex = sex;
        super.letter = type.charAt(0);
        this.type = this.findType(type.toLowerCase());
        super.birdNum = Integer.parseInt(num);
    }

    /*
     * This is the defualt constructor for the bird class
     */
    public Bird() {
    }

    /*
     * This method returns the color of the animal as a string
     */
    public String getColor() {
        int i = 0;
        for (birdTypes bt : birdTypes.values()) {
            if (this.type.toString().equals(bt.toString())) {
                return this.colors[i];
            } else {
                i++;
            }
        }
        return null;
    }

    /*
     * This method adds the bird into its ecosystem
     */
    void addInto() {
        ecosystem.add(this);
    }

    /*
     * This method returns the type of the bird as a string
     */
    String type() {
        return this.type.toString();
    }

    /*
     * This method is used to set the animals coordinates to x, y
     * PARAMS: x -- The x value of the animal
     * y -- The y value of the animal
     */
    void setCords(int x, int y) {
        this.ecosystem.remove(cordX, cordY, this);
        super.cordX = x;
        super.cordY = y;
        this.addInto();
    }

    /*
     * Overriding the toString method for this class
     */
    public String toString() {
        return "bird";
    }

    /*
     * This method sets the ecosystem of the animal to ecosystem e
     * PARAMS e -- The ecosystem the animal is in
     */
    void setEcosystem(Ecosystem ecosystem) {
        super.ecosystem = ecosystem;
    }

    /*
     * This method returns the enum of the type of bird this object is
     * PARAMS type -- A string of the type of bird this is
     * RETURN -- An enum of the bird type
     */
    private birdTypes findType(String type) {
        for (birdTypes bt : birdTypes.values()) {
            if (type.equals(bt.toString())) {
                return bt;
            }
        }
        return null;
    }

    /*
     * This method returns a set of strings of all types of birds
     * RETURN -- A set of string with all possible types of birds
     */
    public Set<String> retrunTypes() {
        Set<String> cur = new HashSet<String>();
        for (birdTypes bt : birdTypes.values()) {
            cur.add(bt.toString().toLowerCase());
        }
        return cur;
    }
    
    /*
     * This eat command will only work if the bird tries to
     * eat an insect
     */
    void eat(Animals other) {
        if (other.toString() == "insect") {
            super.lastEaten = 0;
            this.ecosystem.remove(other.cordX, other.cordY, other);
        }
    }

    /*
     * This method is the reproduce method for the bird class
     */
    void reproduce(Animals other) {
        if (!(super.direction.equals("down")
                && other.direction.equals("down"))) {
            Animals newBird = new Bird(super.cordX, super.cordY, super.type(),
                    super.getRandGender(), super.getRandInt());
            newBird.setEcosystem(this.ecosystem);
            this.ecosystem.add(newBird);
        }
    }

    /*
     * This method is the move method for the bird class
     */
    void move() {
        // checks to see if the bird is dead before moving it
        if (super.lastEaten >= 10 | super.moves > 50) {
            this.ecosystem.remove(cordX, cordY, this);
            return;
        }
        if (super.direction.equals("right")) {
            super.cordY += 1;
            this.currentMoves += 1;
            if (this.currentMoves == super.birdNum) {
                this.direction = "up";
            }
        } else if (super.direction.equals("down")) {
            super.cordX += 1;
            this.currentMoves += 1;
            if (this.currentMoves == super.birdNum) {
                this.direction = "right";
            }
        } else {
            super.cordX -= 1;
            this.currentMoves += 1;
            if (this.currentMoves == super.birdNum) {
                this.direction = "down";
            }
        }
        if (this.currentMoves == super.birdNum) {
            this.currentMoves = 0;
        }
        this.addInto();
        super.incrementMoves();
    }
}