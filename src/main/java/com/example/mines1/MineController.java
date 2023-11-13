package com.example.mines1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class MineController {
    // setting our variables.
    // creating our grid
    GridPane ourGrid;
    int width, height, mines,flag = 0,flag2=0;

    @FXML
    private Button StartResetB;

    @FXML
    private RowConstraints gridInV;

    @FXML
    private HBox hB;

    @FXML
    private TextField textHeight;

    @FXML
    private TextField textMines;

    @FXML
    private TextField textWidth;

    @FXML
    private VBox vB;



    //in this method we staring a new game or we restarting and exist one
    // first we getting the values of high width and number of mines from the text field's by using Integer.parseInt to convert from string to int(primitive int)
    // we checking if its new game by asking if flag=0 if yes we setting new grid
    // and adding him as a sun to the hbox and we setting our flag to 1
    // then we adding the buttons to the grid and handling with the right left click

    @FXML
    void StartAction(ActionEvent event) {
// get values from our text labels
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something Wrong, try different properties");
        try {
            width = Integer.parseInt(textWidth.getText());
            height = Integer.parseInt(textHeight.getText());
            mines = Integer.parseInt(textMines.getText());
            if (mines <= 0 || mines > width * height) {
                alert.showAndWait();

            }
        } catch (NumberFormatException e) {
            alert.showAndWait();
        }

        if (flag == 0) {
            startGame();
        }


        ourGrid.getChildren().clear();// we clearing the grid child's.

        if (width <= 10 && height <= 10 && mines > 0 && mines < width * height && width > 0 && height > 0) {
            //create a new game
            logic game = new logic(height, width, mines);

            //in this double for we adding the buttons to the grid
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    String t = game.get(i, j); // getting the button represent in string (X/./F)
                    CreateButton b = new CreateButton(i, j, t);
                    b.setPrefSize(50, 50);
                    b.setFont(new Font("Ariel", 14));
                    b.setOnMouseClicked(e -> {  //here we handling with press right click or left event
                        // The left click
                        if (e.getButton() == MouseButton.PRIMARY) {
                            gamePlay(game, b.getX(), b.getY());
                        }
                        // The right click
                        if (e.getButton() == MouseButton.SECONDARY) {
                            game.toggleFlag(b.getX(), b.getY());
                            b.setText(game.get(b.getX(), b.getY()));
                            if (game.isDone()) { // if won
                                changeButton(game); // show board
                                disableButton(true);
                            }
                        }
                    });
                    ourGrid.add(b, j, i); // add buttons to grid
                }
            }


        }
    }

    // a subclass of Button that also return x and y values we will use this class do add buttons more easy
    private class CreateButton extends Button {

        private int x, y;


        private CreateButton(int x, int y, String t) {
            super(t);
            this.x = x;
            this.y = y;
        }

        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }
    }

    // add grid to the complete grid to the hbox
    private void startGame() {
        ourGrid = new GridPane();
        ourGrid.setPadding(new Insets(20));
        hB.getChildren().add(ourGrid);

        flag = 1;
        return;
    }

    //in this method we basically implements the logic of the game
    //we getting the game board and the button by x and y coordinates
    //if player open mine we revaling the board and using disblebutton method
    //else its operate to two situatuin , one if he won we show the game board and displaybutton true, else if the game still running we continue showing and disblaybutton false
    private void gamePlay(logic game, int x, int y) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Message");
        alert.setHeaderText(":(  :(  Nice Try ,you can try again :(  :( ");
        game.open(x, y);
        game.setShowAll(true);
        if (game.get(x, y) == "X") {
            changeButton(game);
            disableButton(true);
            alert.showAndWait();
        }

        else {
            if (game.isDone()) {
                changeButton(game);
                disableButton(true);
                alert.setTitle("Message");
                alert.setHeaderText(":) :) Bravo ,you did it :) :)");
                alert.showAndWait();

            } else {
                game.setShowAll(false);
                changeButton(game);
                disableButton(false);

            }
        }

    }

    // in this method we changing text of button according to get function
    void changeButton(logic game) {

        for (Node b : ourGrid.getChildren()) {
            Button b2 = (Button) b;
            if (!b2.isDisabled()) {
                b2.setText(game.get(GridPane.getRowIndex(b), GridPane.getColumnIndex(b)));
            }
        }

    }

    // this method make sure that once opened button is disabled and can't be clicked
    //by setdisable method
    void disableButton(boolean mine) {

        if (mine) {
            for (Node b : ourGrid.getChildren()) {
                Button b2 = (Button) b;
                b2.setDisable(true);
            }
        } else {
            for (Node b : ourGrid.getChildren()) {
                Button b2 = (Button) b;
                if (b2.getText() != "." && b2.getText() != "F") {
                    b2.setDisable(true);
                }
            }
        }
    }
}
