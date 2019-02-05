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
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.observer.events.CardFlippedObserver;
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
public class Controller implements CardFlippedObserver
{
    //region Fields
    @FXML
    private GridPane grid;
    private HBox[][] boxes;
    private Game game;
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

        //Create game object
        this.game = new Game();

        //Register to all events
        this.game.getBoard().onFlip.register(this);

        //Setup all the text boxes in the view to their correct word
        Board board = this.game.getBoard();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                ((Text)this.boxes[i][j].getChildren().get(0)).setText(board.getCard(i, j).getWord());
            }
        }
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
     * Card flipped event listener
     * @param card Card being flipped
     */
    @Override
    public void onFlip(Card card)
    {
        ObservableList<String> styles = this.boxes[card.getX()][card.getY()].getStyleClass();
        styles.remove("unknown");
        styles.add(card.getType().toString().toLowerCase());
    }
    //endregion
}
