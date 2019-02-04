/*
 * Controller.java
 * Created by: Mottel Zirkind
 * Created on: 28/01/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.CardType;
import com.comp354pjb.codenames.model.Game;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Controller object, interacts between the View (FXML) and the Model
 */
public class Controller
{
    //region Fields
    @FXML
    private GridPane grid;
    private HBox[][] boxes;
    private Game game;
    private boolean isSetup = false;
    //endregion

    //region Methods
    /**
     * Initializes the controller along with the FXML file
     */
    @FXML
    private void initialize()
    {
        //Fetch all the card boxes
        this.boxes = new HBox[5][5];
        for (Node node : grid.getChildren())
        {
            int x = GridPane.getRowIndex(node);
            int y = GridPane.getColumnIndex(node);

            if (x >= 1 && x <= 5 && y >= 1 && y <= 5)
            {
                this.boxes[x - 1][y - 1] = (HBox)node;
            }
        }

        this.game = new Game(this);
    }

    /**
     * Mouse click event, registered to from the cards in the View
     * @param data Event data from the mouse click
     */
    @FXML
    private void onClicked(MouseEvent data)
    {
        //Pass on the clicked card to the Model
        Node box = (Node)data.getSource();
        int x = GridPane.getRowIndex(box) - 1;
        int y = GridPane.getColumnIndex(box) - 1;
        this.game.getBoard().revealAt(x, y);
    }

    /**
     * Sets up the board originally in the view
     */
    public void setup()
    {
        //Only sets up the board if it hasn't been setup yet
        if (!this.isSetup)
        {
            //Setup all the text boxes in the view to their correct word
            Board board = this.game.getBoard();
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    ((Text)this.boxes[i][j].getChildren().get(0)).setText(board.getCard(i, j).getWord());
                }
            }
            this.isSetup = true;
        }
        else
        {
            //If already setup, notify
            System.out.println("Game board has already been setup.");
        }
    }

    /**
     * Flips a card
     * @param x X coordinate of the card
     * @param y Y coordinate of the card
     * @param type Type of card to flip to
     */
    public void flip(int x, int y, CardType type)
    {
        ObservableList<String> styles = this.boxes[x][y].getStyleClass();
        styles.remove("unknown");
        styles.add(type.toString().toLowerCase());
    }
    //endregion
}
