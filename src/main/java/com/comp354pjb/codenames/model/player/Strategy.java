package com.comp354pjb.codenames.model.player;

abstract public class Strategy {
    protected PlayerType team;
    protected boolean finished = false;

    //region Methods
    /**
     * Plays a given player's turn according to rules defined in the method
     */
    abstract void execute();

    public void setTeam(PlayerType team)
    {
        this.team = team;
    }

    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }
    //endregion
}
