package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

public class SpyMasterAI implements IPlayer
{
    private PlayerType teamColor;
    private String name;

    /**
     * initializes SpyMasterAI
     * @param team is the team association for this player
     */
    public SpyMasterAI(PlayerType team)
    {
        if(team == PlayerType.BLUE)
        {
            name = "Blue Spymaster";
        }
        else
        {
            name = "Red Spymaster";
        }
        teamColor = team;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * the following method sets the necessary from a spymasters turn
     * @param game
     */
    public void playTurn(Game game)
    {
        game.setPhase(name);
        game.setCurrentClue("random");
        game.setClueNum(3);
    }
}
