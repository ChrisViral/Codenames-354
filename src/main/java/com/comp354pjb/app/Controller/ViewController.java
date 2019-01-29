package com.comp354pjb.app.Controller;

import com.comp354pjb.app.App;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public void setWords(String[][] words)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                this.words[i][j].setText(words[i][j]);
            }
        }
    }
}
