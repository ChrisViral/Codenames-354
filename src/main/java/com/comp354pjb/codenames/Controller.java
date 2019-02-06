/*
 * Controller.java
 * Created by: Mottel Zirkind
 * Created on: 28/01/19
 *
 * Contributors:
 * Christophe Savard
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.observer.events.CardFlippedObserver;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    //FXML Fields
    @FXML
    private GridPane grid;
    @FXML
    private Button undoButton, redoButton, nextMoveButton;

    //Data
    private HBox[][] boxes;
    private Game game;
    private Commander commander;
    //endregion

    //region FXML Methods
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

        //Create the Commander object
        this.commander = new Commander(this, this.game);

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
     * Plays the next AI game turn
     */
    @FXML
    private void onNextMove()
    {
        this.game.enterNextGameTurn();
    }

    /**
     * Triggers the Undo action in the Commander
     */
    @FXML
    private void onUndo()
    {
        this.commander.undo();
    }

    /**
     * Triggers the redo action in the Commander
     */
    @FXML
    private void onRedo()
    {
        this.commander.redo();
    }
    //endregion

    //region Methods
    /**
     * Card flipped event listener
     * @param card Card being flipped
     */
    @Override
    public void onFlip(Card card)
    {
        switchStyles(this.boxes[card.getX()][card.getY()], "unknown", card.getType().name().toLowerCase());
    }

    /**
     * Unflips a card on the View
     * @param card Card to unflip
     */
    public void unFlip(Card card)
    {
        switchStyles(this.boxes[card.getX()][card.getY()], card.getType().name().toLowerCase(), "unknown");
    }

    /**
     * Switches the CSS style of one of the HBoxes
     * @param box  Box to change the style for
     * @param from Style to remove
     * @param to   Style to add
     */
    private void switchStyles(HBox box, String from, String to)
    {
        ObservableList<String> styles = box.getStyleClass();
        styles.remove(from);
        styles.add(to);
    }

    /**
     * Sets the enabled/disabled state of the Undo button
     * @param disabled If the Undo button is disabled or not
     */
    public void setUndoDisabled(boolean disabled)
    {
        this.undoButton.setDisable(disabled);
    }

    /**
     * Sets the enabled/disabled state of the Redo button
     * @param disabled If the Redo button is disabled or not
     */
    public void setRedoDisabled(boolean disabled)
    {
        this.redoButton.setDisable(disabled);
    }

    /**
     * Performs all cleanup actions as the app closes
     */
    public void close()
    {
        this.commander.close();
    }
    //endregion
}
