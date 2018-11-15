
/**
 * Ecosystem.java
 * 
 * This is the class that creates an ecosystem object that holds and 
 * interacts with the animals in that ecosystem.
 * 
 * Usage instructions:
 * 
 * Construct an ecosystem
 * Ecosystem e = new Ecosystem(rows, cols);
 * 
 * Useful methods:
 * Ecosystem.add(Animals animal)
 * 
 * Ecosystem.ecosystemDraw(GraphicsContext gc)
 * 
 * Ecosystem.remove(int x, int y, Animals animal)
 * 
 * Ecosystem.ecosystemDraw(GraphicsContext gc)
 * 
 * Ecosystem.eat()
 * Ecosystem.eat(int x, int y)
 * Ecosystem.eat(Type)
 * 
 * Ecosystem.reproduce()
 * Ecosystem.reproduce(int x, int y)
 * 
 */

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ecosystem {
    private List<List<List<Animals>>> ecosystem;
    private int rows;
    private int cols;

    public Ecosystem(int row, int col) {
        this.rows = row;
        this.cols = col;
        this.ecosystem = new ArrayList<List<List<Animals>>>();
        this.initEcosystem();
    }

    /*
     * This method creates a new blank ecosystem
     */
    private void initEcosystem() {
        List<List<List<Animals>>> cur = new ArrayList<List<List<Animals>>>();
        for (int i = 0; i < rows; i++) {
            cur.add(i, new ArrayList<List<Animals>>());
            for (int j = 0; j < cols; j++) {
                cur.get(i).add(j, new ArrayList<Animals>());
            }
        }
        this.ecosystem = cur;
    }

    /*
     * This method adds in a new animal, and also checks that its location is
     * valid, and it it is not valid, moves it to the correct location
     */
    void add(Animals animal) {
        if (animal.cordY >= (this.cols)) {
            animal.cordY = (animal.cordY - cols);
        }
        if (animal.cordX >= (this.rows)) {
            animal.cordX = (animal.cordX - rows);
        }
        if (animal.cordY < 0) {
            animal.cordY = (this.cols - Math.abs(animal.cordY));
        }
        if (animal.cordX < 0) {
            animal.cordX = (this.rows - Math.abs(animal.cordX));
        }
        this.ecosystem.get(animal.cordX).get(animal.cordY).add(animal);
    }

    /*
     * This method removes the animal object at position x, y
     */
    void remove(int x, int y, Animals animal) {
        this.ecosystem.get(x).get(y).remove(animal);
    }

    /*
     * This method loops through the ecosystem and has all animals eat
     */
    void eat() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = this.ecosystem.get(i).get(j);
                if (cur.size() >= 2) {
                    cur.get(0).eat(cur.get(1));
                }
            }
        }
    }

    /*
     * This method has only the first 2 animals at location x, y eat
     */
    void eat(int x, int y) {
        List<Animals> curList = this.ecosystem.get(x).get(y);
        if (curList.size() >= 2) {
            curList.get(0).eat(curList.get(1));
        }
    }

    /*
     * This method loops through the ecosystem and has all animals of
     * a cetian type eat
     */
    void eat(String type) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = this.ecosystem.get(i).get(j);
                if (cur.size() >= 2) {
                    // This if statment checks if the animal is of the correct
                    // type
                    if (cur.get(0).type() == type.toLowerCase()) {
                        cur.get(0).eat(cur.get(1));
                    } else if (cur.get(1).type() == type.toLowerCase()) {
                        cur.get(1).eat(cur.get(0));
                    }
                }
            }
        }
    }

    /*
     * This method has all animals at location x,y move
     */
    void move(int x, int y) {
        List<Animals> curList = this.ecosystem.get(x).get(y);
        for (int i = 0; i < curList.size(); i++) {
            curList.get(i).move();
        }
        this.ecosystem.get(x).get(y).removeAll(curList);
    }

    /*
     * This method loops through the ecosystem and has any animals of a
     * certain type move
     */
    void move(String type) {
        List<List<List<Animals>>> curEcosystem = new ArrayList<List<List<Animals>>>(
                this.ecosystem);
        this.initEcosystem();
        type = type.toLowerCase();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = curEcosystem.get(i).get(j);
                for (int k = 0; k < cur.size(); k++) {
                    // This if statment checks if the animal is of the correct
                    // type
                    if (type.equals(cur.get(k).toString())
                            || type.equals(cur.get(k).type())) {
                        cur.get(k).move();
                    } else {
                        this.add(cur.get(k));
                    }
                }
            }
        }
    }

    /*
     * This method loops through the ecosystem and has all animals move
     */
    void move() {
        List<List<List<Animals>>> curEcosystem = new ArrayList<List<List<Animals>>>(
                this.ecosystem);
        this.initEcosystem();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = curEcosystem.get(i).get(j);
                for(int k = 0; k < cur.size(); k++) {
                    cur.get(k).move();
                    ;
                }
            }
        }
    }

    /*
     * This method loops through the ecosystem and has the first 2 animals at
     * try to reproduce
     */
    void reproduce() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = this.ecosystem.get(i).get(j);
                if (cur.size() >= 2) {
                    if (cur.get(0).type().equals(cur.get(1).type())
                            && (!cur.get(0).sex.equals(cur.get(1).sex))) {
                        cur.get(0).reproduce(cur.get(1));
                    }
                }
            }
        }
    }

    /*
     * This method gets the animals at x,y and has the first 2 animals
     * try to reproduce
     */
    void reproduce(int x, int y) {
        List<Animals> cur = this.ecosystem.get(x).get(y);
        if (cur.size() >= 2) {
            if (cur.get(0).type().equals(cur.get(1).type())
                    && (!cur.get(0).sex.equals(cur.get(1).sex))) {
                cur.get(0).reproduce(cur.get(1));
            }
        }
    }

    /*
     * This method loops through the ecosystem and prints out the
     * first animal at all locations to the canvas
     */
    void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = this.ecosystem.get(i).get(j);
                if (cur.size() > 0) {
                    System.out.print(cur.get(0).letter);
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     * This method updates the graphics context with the current state of the
     * ecosystem map
     * PARAMS -- gc: A graphicsContext object that contains the panel to be
     * drawn on
     */
    public void ecosystemDraw(GraphicsContext gc) {
        int size = PA8Main.CELL_SIZE;
        gc.setFill(Color.AQUA);
        gc.fillRect(0, 0, PA9Main.SIZE_ACROSS, PA9Main.SIZE_DOWN);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<Animals> cur = this.ecosystem.get(i).get(j);
                if (cur.size() > 0) {
                    gc.setFill(Color.valueOf(cur.get(0).getColor()));
                    gc.fillRect(j * size, i * size, size, size);
                }
            }
        }
    }
}

