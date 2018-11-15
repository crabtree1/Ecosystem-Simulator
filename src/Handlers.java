import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class Handlers {
    class HandleTextInput implements EventHandler<ActionEvent> {
        public HandleTextInput(TextField cmd_in) {
            this.cmd_in = cmd_in;
        }

        @Override
        public void handle(ActionEvent event) {
            System.out.println(cmd_in.getText());
        }

        private TextField cmd_in;
    }
}
