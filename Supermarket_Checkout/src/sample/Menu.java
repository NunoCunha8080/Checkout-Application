package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    //Buttons
    @FXML private Button btnShop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Method to Go to Shop Screen on Button Action
        btnShop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root;

                try{
                    root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("supermarket Checkout - Shop");
                    stage.setScene(new Scene(root));
                    stage.show();

                    //Hide the Menu Window
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException e){e.printStackTrace();}
            }
        });

    }

    /*private void openShop() throws IOException {
        Parent shopScreen = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene shoScreenScene = new Scene(shopScreen);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(shoScreenScene);
        window.show();

    }*/
}
