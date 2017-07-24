

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class controller {

    @FXML
    private Label sourceLabel;

    @FXML
    public void openSourceFolder() {
        sourceLabel.setText("Hi!");
    }

    @FXML
    public void locateFile(ActionEvent event) {
        /**
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
         */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(new Stage());
    }



}
