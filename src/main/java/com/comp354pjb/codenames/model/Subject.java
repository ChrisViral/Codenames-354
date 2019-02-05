/*
 * Subject.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 */

package com.comp354pjb.codenames.model;

import java.util.ArrayList;

/**
 * Subject base class
 * @param <T> Type of Observer being targeted
 * @param <U> Type of information passed to the Observer
 */
public abstract class Subject<T, U>
{
    /**
     * List of all currently registered listeners
     */
    private final ArrayList<T> listeners = new ArrayList<>();

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
     */
    public void unregister(T listener)
    {
        this.listeners.remove(listener);
    }

    /**
     * Notify all the current listeners of the changed data
     * @param data Data being passed
     */
    public void notify(U data)
    {
        for (T listener : this.listeners)
        {
            update(listener, data);
        }
    }

    /**
     * Notifies a single listener of the changed data
     * @param listener Listener to notify
     * @param data     Data being passed
     */
    protected abstract void update(T listener, U data);
}
