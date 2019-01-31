package com.comp354pjb.app;

import com.comp354pjb.app.Model.Board.Board;
import com.comp354pjb.app.Model.Board.CardType;
import com.comp354pjb.app.Model.Game;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller
{
    @FXML
    private GridPane grid;
    private HBox[][] boxes;
    private Game game;
    private boolean isSetup = false;

    @FXML
    private void initialize()
    {
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

    @FXML
    private void onClicked(MouseEvent data)
    {
        Node box = (Node)data.getSource();
        int x = GridPane.getRowIndex(box) - 1;
        int y = GridPane.getColumnIndex(box) - 1;
        this.game.getBoard().revealAt(x, y);
    }

    public void setup()
    {
        if (!this.isSetup)
        {
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
            System.out.println("Game board has already been setup.");
        }
    }

    public void flip(int x, int y, CardType type)
    {
        ObservableList<String> styles = this.boxes[x][y].getStyleClass();
        styles.remove("unknown");
        styles.add(type.toString().toLowerCase());
    }
}
