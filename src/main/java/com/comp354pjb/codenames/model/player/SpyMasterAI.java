package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;
import com.comp354pjb.codenames.model.board.CardType;

public class SpyMasterAI implements IPlayer
{
    private PlayerType teamColor;
    public SpyMasterAI(PlayerType team)
    {
        teamColor = team;
    }

    public void playTurn(Game game)
    {
       game.setCurrentHint("random");
       game.setHintNum(3);
    }
}
