

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class controller {

    @FXML
    private TextArea artistsTextArea;

    @FXML
    private Label sourceLabel;

    @FXML
    private File sourceFolderPath;

    private File logFile;



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

        this.artistsTextArea.clear();


    }

    @FXML
    public void checkApiArtists(){

        iTunesApi apiClass = new iTunesApi();
        String link = "";

        Scanner scanner = null;
        String tmp = "";
        String tmpFormat = "";

        BufferedWriter bw = null;
        FileWriter fw = null;

        //this.logFile = new File(this.sourceFolderPath + "/UpcChecker.txt");

        try {

            scanner = new Scanner(artistsTextArea.getText());
            fw = new FileWriter(this.sourceFolderPath);
            bw = new BufferedWriter(fw);

            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine();
                tmpFormat = String.format("%10s", tmp).replace(' ', '+');
                link = ("https://itunes.apple.com/search?term=" + tmpFormat + "&entity=allArtist&attribute=allArtistTerm");
                System.out.println("LINK: " + link);
                JsonObject json = apiClass.getJson(link);

                if(json == null){
                    //JOptionPane.showMessageDialog(this, "RATE LIMIT!\nWait a few seconds before resuming the API CALLS\nRefer to the developer for further info", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int result = json.get("resultCount").getAsInt();

                System.out.println("APIGUI JSON: " + json.toString());
                System.out.println("RESULTCOUNT: " + result);

                int artistId = 0;
                String checkEquality = "0";

                if (result > 0) {

                    //JSONObject jsonObj = new JSONObject(json);
                    JsonArray jArray = json.get("results").getAsJsonArray();

                    for (int i = 0; i < jArray.size(); i++) {

                        JsonObject jsonObjArr = jArray.get(i).getAsJsonObject();
                        System.out.println("JSON OBJECTS INSIDE ARRAY: " + jsonObjArr.toString());
                        String nameLowerCase = jsonObjArr.get("artistName").getAsString().toLowerCase();

                        if (nameLowerCase.equals(tmp.toLowerCase())) {

                            artistId = jsonObjArr.get("artistId").getAsInt();
                            System.out.println("ARTIST ID: " + artistId);
                            System.out.println("I ARRAY: " + i);


                            link = ("https://itunes.apple.com/lookup?id="+ artistId + "&entity=album");
                            json = apiClass.getJson(link);

                            int finalResult = json.get("resultCount").getAsInt();

                            if(finalResult > 1){
                                checkEquality = "1";

                            }
                            //JOptionPane.showMessageDialog(this, "ARTIST FOUND!\n" + nameLowerCase);
                            //return;
                        }

                    }

                    //if (checkEquality.equals("0")) {
                    //JOptionPane.showMessageDialog(this, "ARTIST NOT FOUND!\n");
                    //return;

                    //}

                    //JSONArray jArray = new JSONArray(json.get("results"));
                    System.out.println("JARRAY: " + jArray.toString());
                } //else {
                //JOptionPane.showMessageDialog(this, "ARTIST NOT FOUND!\n");
                //return;

                //}

                bw.write(tmp + "," + checkEquality + "\n");

            }

        } catch (IOException ex) {
            System.out.println("IOException");

        } finally {
            if (scanner != null) {
                scanner.close();
            }

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();




    }



}
