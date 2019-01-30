package com.comp354pjb.app;

import com.comp354pjb.app.Model.Board.Board;
import com.comp354pjb.app.Model.Board.CardType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ViewController
{
    private App app;
    /**
     * Sets main app
     * @param app Main app
     */
    public void setApp(App app)
    {
        this.app = app;
    }

    @FXML
    private GridPane grid;
    private HBox[][] cards;

    @FXML
    private void initialize()
    {
        this.cards = new HBox[5][5];
        for (Node node : grid.getChildren())
        {
            int x = GridPane.getRowIndex(node) - 1;
            int y = GridPane.getColumnIndex(node) - 1;

            if (x >= 0 && x < 5 && y >= 0 && y < 5)
            {
                this.cards[x][y] = (HBox)node;
            }
        }
    }

    @FXML
    private void onClicked(MouseEvent data)
    {
        HBox box = (HBox)data.getSource();
        int x = GridPane.getRowIndex(box) - 1;
        int y = GridPane.getColumnIndex(box) - 1;
        App.getGame().getBoard().revealAt(x, y);
    }

    public void setBoard(Board board)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                ((Text)this.cards[i][j].getChildren().get(0)).setText(board.getCard(i, j).getWord());
            }
        }
    }

    public void reveal(int x, int y, CardType type)
    {
        ObservableList<String> styles = this.cards[x][y].getStyleClass();
        styles.remove("unknown");
        styles.add(type.toString().toLowerCase());
    }
}
