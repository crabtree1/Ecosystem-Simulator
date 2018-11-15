
/* NAME: Christopher Crabtree
 * FILE: PA8Main.java
 * ASSIGNEMNT: Crispr-GUI
 * CSC 210 Fall 2018
 * The purpose of this program reads in commands from a file and place animal objects 
 * on a map, and allow those animals to interact. There are 4 types of animals, mammals, 
 * insects, birds and reptiles. The Crispr animal is the mosquito. When it reproduces, 
 * it uses the data from its two parents to decide the behavior of the new mosquito. The 
 * program outputs data to the user in a GUI format, with a text box and map. Each animal
 * in the ecosystem has a different color.The program also reads in a delay constant that
 * control how long the program waits before it executes the next command. 
 * 
 * COMMANDS:
 * CREATE 
 * MOVE
 * EAT
 * REPRODUCE
 * PRINT
 * 
 * EXAMPLE COMMAND LINE INPUT:
 * MyEcosystem.in
 * 
 * EXAMPLE INPUT FILE:
rows: 5
cols: 5
delay: 1

CREATE (1,1) lion female right
PRINT
MOVE
print

EXAMPLE OUTPUT:
(Output will be shown in GUI) The output will show the command in the text box 
and then will print out the corresponding ecosystem after the command is executed.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PA8Main extends Application {

    // variables that will be read in from file
    private static Ecosystem ecosystem;
    private static Scanner inputFile;
    private static double delay = 2;
    private static int rows;
    private static int cols;

    // constants for the program
    private final static int TEXT_SIZE = 120;
    protected final static int CELL_SIZE = 20;
    protected static int SIZE_ACROSS;
    protected static int SIZE_DOWN;

    // The main function controls the flow of the program, using functions
    // call and returns to call other functions
    public static void main(String[] args) {
        inputFile = readFile(args[0]);
        ecosystem = createEcosystem(inputFile);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SIZE_ACROSS = cols * CELL_SIZE;
        SIZE_DOWN = rows * CELL_SIZE;
        TextArea command = new TextArea();
        GraphicsContext gc = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN,
                command);
        primaryStage.show();
        simulateEcosystem(gc, command);
    }

    /**
     * This method controls the flow of the GUI output as well as controls the
     * delay in the program. Once the method is called, it uses a lambda
     * function to run the program, then wait the number of seconds in the
     * delay, then run the program again until there is no commands left in
     * the program.
     * 
     * @param gc
     *            GraphicsContext for drawing ecosystem to.
     * @param command
     *            Reference to text area to show simulation commands.
     */
    private void simulateEcosystem(GraphicsContext gc, TextArea command) {
            // Update GUI based on value of delay(seconds to wait)
        PauseTransition wait = new PauseTransition(Duration.seconds(delay));
        wait.setOnFinished((e) -> {
            // This if check is used to see if the file has any commands left
            if (inputFile.hasNextLine()) {
                String curLine = inputFile.nextLine();
                String[] line = curLine.split("\\s+");
                if (line[0].toLowerCase().equals("create")) {
                    createAnimal(line);
                } else if (line[0].toLowerCase().equals("move")) {
                    move(line);
                } else if (line[0].toLowerCase().equals("eat")) {
                    eat(line);
                } else if (line[0].toLowerCase().equals("reproduce")) {
                    reproduce(line);
                } else {
                    ecosystem.print();
                }
                command.appendText(curLine.toLowerCase() + "\n");
                ecosystem.ecosystemDraw(gc);
                wait.playFromStart();
            } else {
                wait.stop();
            }
        });
        wait.play();
    }

    /**
     * This method is used to all the different versions of the reproduce
     * command
     * PARAMS: line -- A string array of the commands at the current line
     * RETURN: NONE
     */
    public static void reproduce(String[] line) {
        if (line.length == 1) {
            ecosystem.reproduce();
        } else if (line[1].charAt(0) == '(') {
            int x = Integer.parseInt(line[1].substring(1, 2));
            int y = Integer.parseInt(line[1].substring(3, 4));
            ecosystem.reproduce(x, y);
        }
    }

    /**
     * This method is used to all the different versions of the move command
     * PARAMS: line -- A string array of the commands at the current line
     * RETURN: NONE
     */
    public static void move(String[] line) {
        if (line.length == 1) {
            ecosystem.move();
            // MOVE (x,y)
        } else if (line[1].charAt(0) == '(') {
            String[] index = line[1].replace('(', ' ').replace(')', ' ').trim()
                    .split(",");
            int x = Integer.parseInt(index[0]);
            int y = Integer.parseInt(index[1]);
            ecosystem.move(x, y);
            // MOVE(Type / Species)
        } else {
            ecosystem.move(line[1]);
        }
    }

    /**
     * This method is used to all the different versions of the eat command
     * PARAMS: line -- A string array of the commands at the current line
     * RETURN: inputFile -- A scanner of the inputed file
     */
    public static void eat(String[] line) {
        if (line.length == 1) {
            ecosystem.eat();
        } else if (line[1].charAt(0) == '(') {
            String[] index = line[1].replace('(', ' ').replace(')', ' ').trim()
                    .split(",");
            int x = Integer.parseInt(index[0]);
            int y = Integer.parseInt(index[1]);
            ecosystem.eat(x, y);
        } else {
            ecosystem.eat(line[1]);
        }
    }

    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * PA8 Notes: You shouldn't need to modify this method.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param command
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
            int canvas_height, TextArea command) {
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Canvas(pixels across, pixels down)
        // Note this is opposite order of parameters of the Ecosystem in PA6.
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);

        // Place the canvas and command output areas in pane.
        p.setCenter(canvas);
        p.setBottom(command);

        // Title the stage and place the pane into the scene into the stage.
        primaryStage.setTitle("Ecosystem");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }

    /**
     * This method creates the animal object specified in the file, and adds it
     * to the ecosystem object.
     * PARAMS: line -- A string array of the commands at the current line
     * RETURN: NONE
     */
    public static void createAnimal(String[] line) {
        Animals current = null;
        String[] index = line[1].replace('(', ' ').replace(')', ' ').trim()
                .split(",");
        int x = Integer.parseInt(index[0]);
        int y = Integer.parseInt(index[1]);
        if (new Mammal().retrunTypes().contains(line[2].toLowerCase())) {
            current = new Mammal(line[2].toLowerCase(), x, y, line[4], line[3]);
        } else if (new Bird().retrunTypes().contains(line[2].toLowerCase())) {
            current = new Bird(x, y, line[2].toLowerCase(), line[3], line[4]);
        } else if (new Insect().retrunTypes().contains(line[2].toLowerCase())) {
            current = new Insect(x, y, line[2].toLowerCase(), line[3], line[4]);
        } else {
            current = new Reptile(x, y, line[2].toLowerCase(), line[3],
                    line[4]);
        }
        current.setEcosystem(ecosystem);
        ecosystem.add(current);
    }

    /**
     * This method reads in the file and returns a scanner object of the
     * input file
     * PARAMS: fname -- A string of the file name from the command line
     * RETURN: inputFile -- A scanner of the inputed file
     */
    public static Scanner readFile(String fname) {
        Scanner inputFile = null;
        try {
            inputFile = new Scanner(new File(fname));
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + fname);
            System.exit(1);
        }
        return inputFile;
    }

    /**
     * This method creates an ecosystem object to hold all the animals
     * PARAMS: inputFile-- A scanner of the inputed file
     * RETURN: An ecosystem object with the size specified in the file
     */
    public static Ecosystem createEcosystem(Scanner inputFile) {
        for (int i = 0; i < 3; i++) {
            String[] line = inputFile.nextLine().split("\\s+");
            if (i == 0) {
                rows = Integer.parseInt(line[1]);
            } else if (i == 1) {
                cols = Integer.parseInt(line[1]);
            } else {
                delay = Double.parseDouble(line[1]);
            }
        }
        inputFile.nextLine();
        return new Ecosystem(rows, cols);
    }
}
