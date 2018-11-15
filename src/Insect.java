
/**
 * Insect.java
 * 
 * This is the class that creates an Insect object
 * 
 * Usage instructions:
 * 
 * Construct a Mammal
 * Insect I = new Insect(x, y, type, sex, boolean);
 * 
 * Useful methods:
 * Insect.setEcosystem(Ecosystem e)
 * 
 * Insect.move()
 * 
 * Insect.getColor()
 * 
 * Insect.eat(Animals other)
 * 
 * Insect.reproduce(Animals other)
 * 
 * Insect.addInto();
 * 
 */
import java.util.HashSet;
import java.util.Set;

enum insectTypes {
    mosquito, bee, fly, horsefly, ant
}

public class Insect extends Animals {
    protected insectTypes type;
    public boolean insectBool;
    private int sideLen = 0;
    private int squareSize = 0;
    private int movesLeft = 0;
    private String[] colors = new String[] { "lightblue", "crimson", "gold",
            "plum" };
    
    /*
     * This is the constructor for the insect class
     */
    public Insect(int x, int y, String type, String sex, String bool) {
        super.cordX = x;
        super.cordY = y;
        this.type = this.findType(type.toLowerCase());
        super.sex = sex;
        super.letter = type.charAt(0);
        this.insectBool = Boolean.parseBoolean(bool);
        super.direction = "left";
    }

    /*
     * This is the defualt constructor for the bird class
     */
    public Insect() {
    }

    /*
     * This method returns the color of the animal as a string
     */
    public String getColor() {
        int i = 0;
        for (insectTypes it : insectTypes.values()) {
            if (this.type.toString().equals(it.toString())) {
                return this.colors[i];
            } else {
                i++;
            }
        }
        return null;
    }

    /*
     * This method checks to see if the animal is dead or not, and
     * if it is dead, removes if from the ecosystem
     */
    private void checkDeath() {
        if (super.moves >= 20) {
            this.ecosystem.remove(cordX, cordY, this);
        }
    }

    /*
     * This method adds the insect into its ecosystem
     */
    void addInto() {
        ecosystem.add(this);
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
     * Set this object to the ecosystem object e
     */
    void setEcosystem(Ecosystem e) {
        super.ecosystem = e;
    }

    /*
     * This method returns the type of the insect as a string
     */
    public String type() {
        return this.type.toString();
    }

    /*
     * Overriding the toString method for this class
     */
    public String toString() {
        return "insect";
    }

    /*
     * This method returns a set of strings of all types of insects
     * RETURN -- A set of string with all possible types of insects
     */
    public Set<String> retrunTypes() {
        Set<String> cur = new HashSet<String>();
        for (insectTypes mt : insectTypes.values()) {
            cur.add(mt.toString().toLowerCase());
        }
        return cur;
    }

    /*
     * This eat command will only work if an insect tries
     * to eat a mammal
     */
    void eat(Animals other) {
        if (other.toString().equals("mammal")) {
            super.lastEaten = 0;
        }
    }

    /*
     * This method is the reproduce method for the Insect class
     */
    void reproduce(Animals other) {
        Animals newInsect = new Insect(this.cordX, this.cordY, this.type(),
                super.getRandGender(), "true");
        newInsect.setEcosystem(this.ecosystem);
        this.ecosystem.add(newInsect);
    }

    /*
     * This method is the move method for the Insect class
     */
    void move() {
        this.checkDeath();
        if (movesLeft == 0) {
            this.direction = "left";
            this.squareSize += 1;
            this.sideLen = this.squareSize;
            this.movesLeft = this.squareSize * 4;
        }
        if (super.direction.equals("right")) {
            super.cordY += 1;
        } else if (super.direction.equals("down")) {
            super.cordX += 1;
        } else if (super.direction.equals("left")) {
            super.cordY -= 1;
        } else {
            this.cordX -= 1;
        }
        this.movesLeft -= 1;
        this.sideLen -= 1;
        this.addInto();
        super.incrementMoves();
        if (this.sideLen == 0) {
            this.sideLen = this.squareSize;
            if (this.insectBool == true) {
                this.moveHelperClockWise();
            } else {
                this.moveHelperCounter();
            }
        }
    }

    /*
     * This method is a helper functions that changes the direction of the
     * insect
     */
    private void moveHelperCounter() {
        if (super.direction == "left") {
            super.direction = "down";
        } else if (super.direction == "right") {
            super.direction = "up";
        } else if (super.direction == "down") {
            super.direction = "right";
        }
    }

    /*
     * This method is a helper functions that changes the direction of the
     * insect
     */
    private void moveHelperClockWise() {
        if (super.direction == "left") {
            super.direction = "up";
        } else if (super.direction == "up") {
            super.direction = "right";
        } else if (super.direction == "right") {
            super.direction = "down";
        }
    }

    /*
     * This method returns the enum of the type of insect this object is
     * PARAMS type -- A string of the type of insect this is
     * RETURN -- An enum of the insect type
     */
    public insectTypes findType(String animal) {
        for (insectTypes it : insectTypes.values()) {
            if (animal.equals(it.toString())) {
                return it;
            }
        }
        return null;
    }
}