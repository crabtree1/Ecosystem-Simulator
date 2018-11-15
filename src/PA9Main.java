
/* NAME: Christopher Crabtree
 * FILE: PA9Main.java
 * ASSIGNEMNT: Crispr-GUI
 * CSC 210 Fall 2018
 * The purpose of this program reads in commands from the user through a GUI and place animal
 * objects on a map, and allow those animals to interact. There are 4 types of animals, mammals, 
 * insects, birds and reptiles. The Crispr animal is the mosquito. When it reproduces, 
 * it uses the data from its two parents to decide the behavior of the new mosquito. The 
 * program outputs data to the user in a GUI format, with a text box and map. Each animal
 * in the ecosystem has a different color. The ecosystem is created by putting the number 
 * of rows followed by the number of columns in the command line. 
 * 
 * COMMANDS:
 * CREATE 
 * MOVE
 * EAT
 * REPRODUCE
 * PRINT
 * 
 * EXAMPLE COMMAND LINE INPUT:
 * 10 20
 * 

EXAMPLE OUTPUT:
(Output will be shown in GUI) The output will show the command in the text box 
and then will print out the corresponding ecosystem after the user inputs a command
and presses enter or the process button.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PA9Main extends Application {

    // constants for the program
    private GraphicsContext gc;
    private static TextArea command;
    private static Ecosystem ecosystem;
    private static int rows;
    private static int cols;
    private final static int TEXT_SIZE = 120;
    protected final static int CELL_SIZE = 20;
    protected static int SIZE_ACROSS;
    protected static int SIZE_DOWN;

    // The main function controls the flow of the program, using functions
    // call and returns to call other functions
    public static void main(String[] args) {
        rows = Integer.parseInt(args[0]);
        cols = Integer.parseInt(args[1]);
        SIZE_ACROSS = cols * CELL_SIZE;
        SIZE_DOWN = rows * CELL_SIZE;
        ecosystem = new Ecosystem(rows, cols);
        launch(args);
    }

    /**
     * This method creates the canvas and adds in all the different parts of the
     * GUI into the pane. Once it creates the stage, it then shows the stage
     * to the user. It also updates the GUI based on the user input.
     * 
     * @param primaryStage
     *            The stage that all elements will be put on
     */
    @Override
    public void start(Stage primaryStage) {
        // ===== set up the scene with a text box and button for input
        BorderPane p = new BorderPane();
        command = new TextArea();
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);
        TextField cmd_in = new TextField();
        HBox input_box = new HBox(7);
        VBox vBox = new VBox(3);
        p.setCenter(canvas);
        setupNodes(input_box, cmd_in, vBox);
        vBox.getChildren().add(command);
        p.setBottom(vBox);
        gc = canvas.getGraphicsContext2D();
        ecosystem.ecosystemDraw(gc);
        // Connect the border pane into the scene and show the window.
        primaryStage.setTitle("Ecosystem");
        primaryStage.setScene(new Scene(p));
        primaryStage.show();
    }

    /**
     * This method creates all the buttons and labels in the GUI, the adds them
     * to a horizontal box. Then the horizontal box and the canvas are added to
     * a vertical box.
     * 
     * @param hb
     *            The horizontal box for all the buttons and text area
     * @param cmd_in
     *            The text field that reads in the commands
     * @param vb
     *            The vertical box that hold the hb and the canvas
     */
    public void setupNodes(HBox hb, TextField cmd_in, VBox vb) {
        Button process_button = new Button("Process");
        Button move_button = new Button("Move");
        Button reproduce_button = new Button("Reproduce");
        Button eat_button = new Button("Eat");
        Label cmdLabel = new Label("Type Command Here");
        // Setting actions for all buttons
        process_button.setOnAction(new HandleTextInput(cmd_in));
        reproduce_button.setOnAction(new reproduceButton());
        eat_button.setOnAction(new eatButton());
        move_button.setOnAction(new moveAllButton());
        cmd_in.setOnKeyPressed((event) -> {
            if (event.getCode().toString().equalsIgnoreCase("enter")) {
                process_button.fire();
            } else if (event.getCode().toString().equalsIgnoreCase("escape")) {
                System.exit(1);
            } else if (event.getCode().toString().equalsIgnoreCase("f1")) {
                printHelp();
            }
        });
        // Setting up the hb and vb.
        hb.getChildren().add(cmdLabel);
        hb.getChildren().add(cmd_in);
        hb.getChildren().add(process_button);
        hb.getChildren().add(move_button);
        hb.getChildren().add(reproduce_button);
        hb.getChildren().add(eat_button);
        vb.getChildren().add(hb);
    }

    /**
     * This method controls the flow of the GUI output as well as controls the
     * delay in the program. Once the method is called, it uses a lambda
     * function to run the program, then wait the number of seconds in the
     * delay, then run the program again until there is no commands left in
     * the program.
     * 
     * @param cmd_Text
     *            A string of the command inputed by the user
     */
    private void simulateEcosystem(String cmd_Text) {
        String[] line = cmd_Text.split("\\s+");
        String current = line[0].toLowerCase();
        if (current.equals("create")) {
            createAnimal(line);
        } else if (current.equals("move")) {
            move(line);
        } else if (current.toLowerCase().equals("eat")) {
            eat(line);
        } else if (current.toLowerCase().equals("reproduce")) {
            reproduce(line);
        } else {
            ecosystem.print();
        }
        command.appendText(cmd_Text.toLowerCase() + "\n");
        ecosystem.ecosystemDraw(gc);
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
     * @param command
     *            Reference to a TextArea that will be setup.
     * @param p
     *            The borderpane that all is drawn to.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, TextArea command,
            BorderPane p) {

        // Canvas(pixels across, pixels down)
        // Note this is opposite order of parameters of the Ecosystem in PA6.
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);

        // Place the canvas and command output areas in pane.
        p.setTop(canvas);
        p.setBottom(command);

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
     * This method prints out the help text when the user prints F1
     * 
     */
    public static void printHelp() {
        command.appendText(
                "Type in one of the commands, and press 'process' or" + "\n");
        command.appendText(
                "hit enter to process the command. Pressing escape" + "\n");
        command.appendText("will exit the program. Press F1 for help." + "\n");
    }

    /**
     * This is an inner class that contains the handler for the process button
     */
    class HandleTextInput implements EventHandler<ActionEvent> {
        public HandleTextInput(TextField cmd_in) {
            this.cmd_in = cmd_in;
        }

        // This is the handle function that makes the process button work
        @Override
        public void handle(ActionEvent event) {
            String cmd_Text = cmd_in.getText();
            System.out.println(cmd_Text);
            simulateEcosystem(cmd_Text);
            cmd_in.clear();
        }

        private TextField cmd_in;
    }

    /**
     * This is an inner class that contains the handler for the move button
     */
    class moveAllButton implements EventHandler<ActionEvent> {
        public moveAllButton() {
        }

        // This is the handle function that makes the move button work
        @Override
        public void handle(ActionEvent event) {
            System.out.println("move");
            simulateEcosystem("move");
        }
    }

    /**
     * This is an inner class that contains the handler for the reproduce button
     */
    class reproduceButton implements EventHandler<ActionEvent> {
        public reproduceButton() {
        }

        // This is the handle function that makes the reproduce button work
        @Override
        public void handle(ActionEvent event) {
            System.out.println("reproduce");
            simulateEcosystem("reproduce");
        }
    }

    /**
     * This is an inner class that contains the handler for the eat button
     */
    class eatButton implements EventHandler<ActionEvent> {
        public eatButton() {
        }

        // This is the handle function that makes the eat button work
        @Override
        public void handle(ActionEvent event) {
            System.out.println("eat");
            simulateEcosystem("eat");
        }
    }
}
