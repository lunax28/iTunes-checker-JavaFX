

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class controller {

    @FXML
    private TextArea upcTextArea;

    @FXML
    private Label sourceLabel;

    @FXML
    private File sourceFolderPath;



    @FXML
    public void openSourceFolder() {
        sourceLabel.setText("Hi!");
    }

    @FXML
    public void locateFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        this.sourceFolderPath = fileChooser.showOpenDialog(new Stage());

        //TODO
        //handle cancel option!!
        if(this.sourceFolderPath != null){
            this.sourceLabel.setText(this.sourceFolderPath.getAbsolutePath().toString());
        }


    }

    @FXML
    public void clearTextArea(){

        this.upcTextArea.clear();


    }



}
