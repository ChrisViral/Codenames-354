package com.comp354pjb.codenames.model.observerexample;

/**
 * An observer listening to name updates
 */
public interface NameObserver
{
    /**
     * Updates the name
     * @param name New name
     */
    void updateName(String name);
}
