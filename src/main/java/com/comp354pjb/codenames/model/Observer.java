/*
 * FileName.java
 * Created by: Christopher Savard
 * Created on: 17/01/19
 *
 * Contributors:
 * Benjamin Therrien
 * Steven Zanga
 * ...
 */

package com.comp354pjb.codenames.model;

public interface Observer
{
    //update the observer
    public void update();

    //attach the observer
    public void setSubject(Subject sub);
}
