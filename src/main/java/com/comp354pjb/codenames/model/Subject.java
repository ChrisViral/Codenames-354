/*
 * Subject.java
 * Created by: Christopher Savard
 * Created on: 17/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * ...
 */

package com.comp354pjb.codenames.model;

public interface Subject
{
    public void register(Observer obj);
    public void unregister(Observer obj);

    public void notifyObservers();

    public Object getUpdate(Observer obj);
}
