package com.comp354pjb.codenames.model.observerexample;

import com.comp354pjb.codenames.model.Subject;

/**
 * A subject updating someone's name
 */
public class NameSubject extends Subject<NameObserver, String>
{
    /**
     * Updates the listener to the new Name
     * @param listener Listener to notify
     * @param name     New name
     */
    @Override
    protected void update(NameObserver listener, String name)
    {
        listener.updateName(name);
    }
}
