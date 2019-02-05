package com.comp354pjb.codenames.model.observerexample;

import com.comp354pjb.codenames.model.Subject;

/**
 * A subject sending age updates
 */
public class AgeSubject extends Subject<AgeObserver, Integer>
{
    /**
     * Updates the listener to the new Age
     * @param listener Listener to notify
     * @param age      New age
     */
    @Override
    protected void update(AgeObserver listener, Integer age)
    {
        listener.updateAge(age);
    }
}
