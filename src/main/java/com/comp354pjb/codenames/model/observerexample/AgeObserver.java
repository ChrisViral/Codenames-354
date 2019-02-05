package com.comp354pjb.codenames.model.observerexample;

/**
 * An observer updating someone's age
 */
public interface AgeObserver
{
    /**
     * Updates the age
     * @param age New age
     */
    void updateAge(Integer age);
}
