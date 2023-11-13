package com.example.mines1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.io.IOException;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;

public class MineApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HBox hbox = null; // set to null all widgets in a hbox , we will set the right size during the
        // process
        @SuppressWarnings("unused")
        MineController controller;
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("mines.fxml")); // load our fxml file
            hbox = loader.load();
            controller = loader.getController();
            stage.setTitle("The Amazing Mines Sweeper");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene s = new Scene(hbox);
        //adding background color
        BackgroundFill background_fill = new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        hbox.setBackground(background);
        stage.setScene(s);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}