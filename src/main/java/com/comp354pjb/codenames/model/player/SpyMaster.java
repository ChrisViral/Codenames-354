/*
 * SpyMaster.java
 * Created by: Michael Wilgus
 * Created on: 06/02/19
 *
 * Contributors:
 * Michael Wilgus
 */

package com.comp354pjb.codenames.model.player;

import com.comp354pjb.codenames.model.Game;

public class SpyMaster extends Player {
    public SpyMaster(Game game, PlayerType team, IPlayer strategy, PlayerStrategy playerStrategy)
    {
        super(game, team, strategy, playerStrategy);
    }
}
