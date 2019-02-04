/*
 * FileName.java
 * Created by: Benjamin Therrien
 * Created on: 29/01/19
 *
 * Contributors:
 * Benjamin Therrien
 */

package com.comp354pjb.codenames.model;

public interface Observer
{
    //update the observer
    public void update();

    //attach the observer
    public void setSubject(Subject sub);
}
