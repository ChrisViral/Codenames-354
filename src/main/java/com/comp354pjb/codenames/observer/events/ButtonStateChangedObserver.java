package com.comp354pjb.codenames.observer.events;

public interface ButtonStateChangedObserver
{
    //region Methods
    /**
     * Card flipped event update method
     * @param disabled If the button is disabled or not
     */
    void updateState(boolean disabled);
    //endregion
}
