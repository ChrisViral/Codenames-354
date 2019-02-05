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
    private ArrayList<T> listeners = new ArrayList<>();

    public void register(T listener)
    {
        this.listeners.add(listener);
    }
    public void unregister(T listener)
    {
        this.listeners.remove(listener);
    }

    public void notify(U data)
    {
        for (T listener : this.listeners)
        {
            update(listener, data);
        }
    }

    protected abstract void update(T listener, U data);
}
