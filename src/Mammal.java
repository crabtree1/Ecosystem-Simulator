
/**
 * Mammal.java
 * 
 * This is the class that creates a Mammal object
 * 
 * Usage instructions:
 * 
 * Construct a Mammal
 * Mammal m = new Mammal(type, x, y, direction, sex);
 * 
 * Useful methods:
 * Mammal.setEcosystem(Ecosystem e)
 * 
 * Mammal.move()
 * 
 * Mammal.getColor()
 * 
 * Mammal.eat(Animals other)
 * 
 * Mammal.reproduce(Animals other)
 * 
 * Mammal.addInto();
 * 
 */

import java.util.HashSet;
import java.util.Set;

enum mammalTypes {
    rhinoceros, lion, cheeta, giraffe, zebra, elephant;
}

public class Mammal extends Animals {
    private mammalTypes type;
    private String[] colors = new String[] { "blue", "green", "yellow",
            "oragne", "brown", "pink" };

    /*
     * This is the constructor for the mammal class
     */
    public Mammal(String type, int x, int y, String direction, String sex) {
        super.direction = null;
        if (MOVES.contains(direction.toLowerCase())) {
            super.direction = direction.toLowerCase();
        }
        super.sex = sex.toLowerCase();
        this.type = this.findType(type.toLowerCase());
        super.letter = type.charAt(0);
        super.cordX = x;
        super.cordY = y;
    }

    /*
     * This is the defualt constructor for the mammal class
     */
    public Mammal() {
    }

    /*
     * This method returns the enum of the type of mammal this object is
     * PARAMS type -- A string of the type of mammal this is
     * RETURN -- An enum of the mammal type
     */
    private mammalTypes findType(String animal) {
        for (mammalTypes mt : mammalTypes.values()) {
            if (animal.equals(mt.toString())) {
                return mt;
            }
        }
        return null;
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
        for (mammalTypes mt : mammalTypes.values()) {
            if (this.type.toString().equals(mt.toString())) {
                return this.colors[i];
            } else {
                i++;
            }
        }
        return null;
    }

    /*
     * This method returns a set of strings of all types of mammals
     * RETURN -- A set of string with all possible types of mammals
     */
    public Set<String> retrunTypes() {
        Set<String> cur = new HashSet<String>();
        for (mammalTypes mt : mammalTypes.values()) {
            cur.add(mt.toString().toLowerCase());
        }
        return cur;
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
        return "mammal";
    }

    /*
     * This method returns the type of the mammal as a string
     */
    String type() {
        return this.type.toString();
    }

    /*
     * This method adds the mammal into its ecosystem
     */
    void addInto() {
        ecosystem.add(this);
    }

    /*
     * This eat command will only work if a mammal tries
     * to eat another mammal
     */
    void eat(Animals other) {
        if (other.toString() == "mammal") {
            super.lastEaten = 0;
            this.ecosystem.remove(other.cordX, other.cordY, other);
        }
    }

    /*
     * This method is the reproduce method for the mammal class
     */
    void reproduce(Animals other) {
        if (this.reproduces > 0 && other.reproduces > 0) {
            this.reproduces -= 1;
            other.reproduces -= 1;
            Animals newMammal = new Mammal(this.type(), this.cordX, this.cordY,
                    super.getRandDir(), super.getRandGender());
            newMammal.setEcosystem(this.ecosystem);
            this.ecosystem.add(newMammal);
        }
    }

    /*
     * This method is the move method for the mammal class
     */
    void move() {
        // Checks if the mammal is dead before moving
        if (super.moves >= 100 || super.lastEaten >= 10) {
            this.ecosystem.remove(cordX, cordY, this);
        } else {
            if (super.direction.equals("right")) {
                super.cordX += 1;
                super.direction = "down";
            } else if (super.direction.equals("down")) {
                super.cordY += 1;
                super.direction = "right";
            } else if (super.direction.equals("left")) {
                super.cordY -= 1;
                super.direction = "up";
            } else {
                this.cordX -= 1;
                this.direction = "left";
            }
            this.addInto();
            super.incrementMoves();
        }
    }
}
