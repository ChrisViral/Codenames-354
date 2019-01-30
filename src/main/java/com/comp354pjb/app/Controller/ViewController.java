package com.comp354pjb.app.Controller;

import com.comp354pjb.app.App;
import com.comp354pjb.app.Model.Card;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    private Text[][] words;
    private Card[][] cards;

    @FXML
    private void initialize()
    {
        this.words = new Text[5][5];
        for (Node node : grid.getChildren())
        {
            int x = GridPane.getRowIndex(node) - 1;
            int y = GridPane.getColumnIndex(node) - 1;

            if (x >= 0 && x < 5 && y >= 0 && y < 5)
            {
                this.words[x][y] = (Text)((Pane)node).getChildren().get(0);
            }
        }
    }

    @FXML
    private void onClicked(MouseEvent data)
    {
        Pane pane = (Pane)data.getSource();
        int x = GridPane.getRowIndex(pane) - 1;
        int y = GridPane.getColumnIndex(pane) - 1;
        ObservableList<String> styles = pane.getStyleClass();
        styles.remove("unknown");
        //styles.add(this.cards[x][y].getType().toString());
        System.out.println("Setting style");
    }

    public void setWords(Card[][] cards)
    {
        this.cards = cards;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                this.words[i][j].setText(this.cards[i][j].getWord());
            }
        }
    }
}
