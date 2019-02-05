package com.comp354pjb.codenames.model.player;
import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.CardType;
public class SpyMasterAI implements IPlayer
{
    private CardType teamColor;

    SpyMasterAI(CardType team)
    {
        teamColor = team;
    }

    /**
     * The following method uses a randomizer to determine which class to
     */
    public void playTurn( Game game)
    {
        game.setCurrentHint("random");
        game.setHintNum(3);
        //find a way to pass this hint to the board/game


    }
}

