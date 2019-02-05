package com.comp354pjb.codenames.model.player;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.CardType;
import java.lang.*;

public class OperativeAI implements IPlayer
{
    private CardType teamColor;

    OperativeAI(CardType team)
    {
        teamColor = team;
    }

    @Override
    /**
     * The following method uses a randomizer to determine which card to pick
     */
    public void playTurn(Game game)
    {

        for(int i=0; i<game.getHintNum(); i++)
        {
            boolean isComplete= false;
            while (!isComplete)
            {
                int row = (int)(5 * Math.random()), col = (int)(5 * Math.random());
                if (! game.getBoard().getCard(row, col).isRevealed())
                {
                    //update the board here
                    if(game.getBoard().getCard(row, col).getType() == CardType.CIVILIAN)
                    {
                        i = game.getHintNum();
                    }
                    else if(game.getBoard().getCard(row, col).getType() == CardType.ASSASSIN)
                    {
                        i = game.getHintNum();
                        game.setAssassinRevealed(true);
                        game.setLoser(teamColor);
                    }
                    else if(game.getBoard().getCard(row, col).getType() != teamColor)
                    {
                        if(teamColor==CardType.BLUE)
                            ;

                    }
                    isComplete = true;
                }
            }
        }

    }


}
