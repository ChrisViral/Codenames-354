package com.comp354pjb.codenames.observer.events;

import com.comp354pjb.codenames.observer.Subject;

public class ButtonStateChangedEvent extends Subject<ButtonStateChangedObserver, Boolean>
{
    //region Methods
    /**
     * Notifies a single listener of the changed data
     * Inheriting subjects should override this class and make it interact with the Observer it is using
     * @param listener Listener to notify
     * @param data     Data being passed
     */
    @Override
    protected void update(ButtonStateChangedObserver listener, Boolean data)
    {
        listener.updateState(data);
    }
    //endregion
}
