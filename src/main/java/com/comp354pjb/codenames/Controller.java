/*
 * Controller.java
 * Created by: Mottel Zirkind
 * Created on: 28/01/19
 *
 * Contributors:
 * Christophe Savard
 * Rezza-Zairan Zaharin
 * Michael Wilgus
 */

package com.comp354pjb.codenames;

import com.comp354pjb.codenames.commander.Commander;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.Board;
import com.comp354pjb.codenames.model.board.Card;
import com.comp354pjb.codenames.model.player.Clue;
import com.comp354pjb.codenames.model.player.PlayerIntelligence;
import com.comp354pjb.codenames.observer.events.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller object, interacts between the View (FXML) and the Model
 */
public class Controller implements CardFlippedObserver, ClueGivenObserver, PhaseObserver, RoundObserver, ButtonStateChangedObserver, TurnEndObserver
{
    //region Constants
    /**
     * Unknown/non flipped card style class
     */
    private static final String UNKNOWN = "unknown";
    //endregion

    //region Fields
    //FXML Fields - Contain various components of the Graphical user Interface (GUI)
    @FXML
    private GridPane grid;
    @FXML
    private Button nextMoveButton, startGameBtn;
    @FXML
    private Text round, phase, red, blue, guesses, clue;
    @FXML
    private ComboBox<String> redSpymaster, redOperative, blueSpymaster, blueOperative;

    //Data
    private boolean initialized;
    private final HBox[][] boxes = new HBox[5][5];
    private Game game;
    private int maxGuesses, currentGuesses;
    private int currentBlue, maxBlue = 8, currentRed, maxRed = 8;
    //endregion

    //region FXML Methods
    /**
     * Initializes the controller
     * ==========
     * Updated by Christophe Savard 04/04/19
     * Put back initialization only stuff in here
     */
    @FXML
    private void initialize()
    {
        //Do not initialize more than once
        if (this.initialized) { return; }
        this.initialized = true;

        Commander.log("Controller initialization has begun");

        //Fetch all the card boxes
        for (Node node : grid.getChildren())
        {
            int x = GridPane.getRowIndex(node);
            int y = GridPane.getColumnIndex(node);

            if (x >= 1 && x <= 5 && y >= 1 && y <= 5)
            {
                this.boxes[x - 1][y - 1] = (HBox)node;
            }
        }


        Commander.log("Controller correctly identified all UI card boxes");

        //Create game object
        this.game = new Game();

        //Register to all events
        this.game.onClueGiven.register(this);
        this.game.onPhaseChange.register(this);
        this.game.onRoundChange.register(this);
        this.game.onButtonStateChanged.register(this);
        this.game.onTurnEnd.register(this);
        this.game.getBoard().onFlip.register(this);

        Commander.log("Game created and observers registered");

        //Setup the starting player
        switch (this.game.getStartTeam())
        {
            case RED:
                this.maxRed++;
                break;

            case BLUE:
                this.maxBlue++;
                break;
        }
        this.red.setText("0/" + this.maxRed);
        this.blue.setText("0/" + this.maxBlue);

        Commander.log("Card count text fields updated for starting players");

        //Setup all the text boxes in the view to their correct word
        Board board = this.game.getBoard();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                //Get child text component
                Text text = (Text)this.boxes[i][j].getChildren().get(0);
                text.setText(board.getCard(i, j).getWord());
            }
        }

        Commander.log("UI card boxes updated with their correct codenames");
    }

    /**
     * Update by Rezza-Zairan
     * ----------------------
     * Initializes the FXML as everything in initialize() was pushed towards this function
     * so that it responds to the start game button being clicked.
     * <p>
     * This function also passes PlayerIntelligence to the instancing of Game()
     * ==========
     * Updated by Christophe Savard 04/04/19
     * Removed initialization only stuff from in here
     */
    @FXML
    private void setup()
    {
        //Only run if all selections are valid
        if (redSpymaster.getValue() == null || redOperative.getValue() == null || blueSpymaster.getValue() == null || blueOperative.getValue() == null)
        {
            Commander.log("Incomplete Player type selection detected, retrying...");
            startGameBtn.setText("TRY AGAIN");
            return;
        }

        //Collect data from start menu
        PlayerIntelligence[] passInt =
        {
            PlayerIntelligence.parse(redSpymaster.getValue()),
            PlayerIntelligence.parse(redOperative.getValue()),
            PlayerIntelligence.parse(blueSpymaster.getValue()),
            PlayerIntelligence.parse(blueOperative.getValue())
        };

        Commander.log(String.format("Valid player type selection: %s, %s, %s, %s", passInt[0], passInt[1], passInt[2], passInt[3]));

        //Setup players then transfer control to game window
        this.game.setPlayers(passInt);
        ((Stage)startGameBtn.getScene().getWindow()).close();

        Commander.log("Players successfully initialized and selection window closed");
    }

    /**
     * Cancels player selection and closes the game
     */
    @FXML
    private void cancel()
    {
        Commander.log("Cancelling game and closing windows...");
        Platform.exit();
    }

    /**
     * Mouse click event, registered to from the cards in the View
     * ==========
     * Updated by Christophe Savard 04/04/19
     * Put back in order for human operative
     * @param data Event data from the mouse click
     */
    @FXML
    private void onClicked(MouseEvent data)
    {
        //Pass on the clicked card to the Model
        Node box = (Node)data.getSource();
        int x = GridPane.getRowIndex(box) - 1;
        int y = GridPane.getColumnIndex(box) - 1;
        Commander.log("UI Card box click detected at location (" + x + ", " + y + ")");
        this.game.informPlayer(x, y);
    }

    /**
     * Plays the next AI game turn
     */
    @FXML
    private void onNextMove()
    {
        Commander.log("Next move started from UI");
        this.game.enterNextGameTurn();
    }
    //endregion

    //region Methods
    /**
     * Turn end event observer
     * @param gameOver If the game is over or not
     */
    @Override
    public void updateTurn(boolean gameOver)
    {
        if (gameOver)
        {
            updateState(true);
            Commander.log(this.game.getWinner().niceName() + " team has won the game");
        }
    }

    /**
     * Sets the next game turn button to disabled or not
     * @param disabled If the button is disabled or not
     */
    @Override
    public void updateState(boolean disabled)
    {
        this.nextMoveButton.setDisable(disabled);
    }

    /**
     * Card flipped event listener
     * ==========
     * Update by Christophe Savard 05/04/19
     * Simplified code for switching styles
     * @param card Card being flipped
     */
    @Override
    public void onFlip(Card card)
    {
        ObservableList<String> styles = this.boxes[card.getX()][card.getY()].getStyleClass();
        styles.remove(UNKNOWN);
        styles.add(card.getType().name().toLowerCase());
        switch (card.getType())
        {
            case BLUE:
                this.currentBlue++;
                this.blue.setText(String.format("%d/%d", this.currentBlue, this.maxBlue));
                break;

            case RED:
                this.currentRed++;
                this.red.setText(String.format("%d/%d", this.currentRed, this.maxRed));
                break;
        }

        this.currentGuesses++;
        this.guesses.setText(String.format("%d/%d", this.currentGuesses, this.maxGuesses));
    }

    /**
     * Gets the new given clue
     * @param clue Clue given
     */
    @Override
    public void updateClue(Clue clue)
    {
        this.clue.setText(clue.toString());
        this.currentGuesses = 0;
        this.maxGuesses = clue.value;
        this.guesses.setText("0/" + clue.value);
    }

    /**
     * Gets the updated game phase
     * @param phase New phase
     */
    @Override
    public void updatePhase(String phase)
    {
        this.phase.setText(phase);
    }

    /**
     * Gets the new updated game round
     * @param round New game round
     */
    @Override
    public void updateRound(Integer round)
    {
        this.round.setText(round.toString());
    }
    //endregion
}
