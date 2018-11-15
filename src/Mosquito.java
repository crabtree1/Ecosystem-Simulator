
/**
 * Mosquito.java
 * 
 * This is the class that creates a mosquito object. This object is
 * a subclass of the Insect class.
 * 
 * Usage instructions:
 * 
 * Construct a Mosquito
 * Mosquito m = new Mosquito(x, y, sex, boolean, boolean, boolean);
 * 
 */

public class Mosquito extends Insect {
    private boolean bool2;
    private boolean bool3;

    /*
     * This is the constructor for the mosquito class
     */
    public Mosquito(int x, int y, String sex, String bool1, String bool2,
            String bool3) {
        super.cordX = x;
        super.cordY = y;
        super.type = super.findType("mosquito");
        super.sex = sex;
        super.letter = 'm';
        super.insectBool = Boolean.parseBoolean(bool1);
        this.bool2 = Boolean.parseBoolean(bool2);
        this.bool3 = Boolean.parseBoolean(bool3);
        super.direction = "left";
    }

    /*
     * This is the defualt constructor for the mosquito class
     */
    public Mosquito() {
    }

    /*
     * Overriding the toString method for this class
     */
    public String toString() {
        return "mosquito";
    }

    /*
     * This method returns the color of the animal as a string
     */
    public String getColor() {
        return "silver";
    }

    /*
     * This method returns a string ture if both booleans are false and
     * false if not
     */
    private String findBoolValues(boolean bool1, boolean bool2) {
        if (bool1 == false && bool2 == false) {
            return "false";
        } else {
            return "true";
        }
    }

    /*
     * This method overrides the reproduce method in the insect
     * class because the Mosquito has a special case when it is
     * trying to reproduce
     */
    void reproduce(Animals other1) {
        Mosquito other = (Mosquito) other1;
        if(this.bool2 != true && this.bool3 != true) {
            if (other.bool2 != true && other.bool3 != true) {
                String newBool2 = this.findBoolValues(this.bool2, this.bool3);
                String newBool3 = this.findBoolValues(other.bool2,
                        other.bool3);
                Animals newMosquito = new Mosquito(this.cordX, this.cordY,
                        super.getRandGender(), "true", newBool2, newBool3);
                newMosquito.setEcosystem(this.ecosystem);
                this.ecosystem.add(newMosquito);
            }
        }
    }
}