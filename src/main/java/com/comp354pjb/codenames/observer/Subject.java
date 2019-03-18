/*
 * Subject.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Christophe Savard
 */

package com.comp354pjb.codenames.observer;

import java.util.ArrayList;

/**
 * Subject base class
 * @param <T> Type of Observer being targeted
 * @param <U> Type of information passed to the Observer
 */
public abstract class Subject<T, U>
{
    //region Fields
    /**
     * List of all currently registered listeners
     */
    protected final ArrayList<T> listeners = new ArrayList<>();
    //endregion

    //region Methods

    /**
     * Registers a listener
     * @param listener Listener to register
     */
    public void register(T listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Unregisters a listener
     * @param listener Listener to unregister
     * @return If the listener was successfully unregistered
     */
    public boolean unregister(T listener)
    {
        return this.listeners.remove(listener);
    }

    /**
     * Notify all the current listeners of the changed data
     * @param data Data being passed
     */
    public void invoke(U data)
    {
        for (T listener : this.listeners)
        {
            update(listener, data);
        }
    }
    //endregion

    //region Abstract methods

    /**
     * Notifies a single listener of the changed data
     * Inheriting subjects should override this class and make it interact with the Observer it is using
     * @param listener Listener to notify
     * @param data     Data being passed
     */
    protected abstract void update(T listener, U data);
    //endregion
}
