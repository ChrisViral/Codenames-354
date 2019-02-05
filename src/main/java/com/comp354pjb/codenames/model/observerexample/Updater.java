package com.comp354pjb.codenames.model.observerexample;

/**
 * An object sending out name and age updates
 */
public class Updater
{
    private AgeSubject ageEvent = new AgeSubject();
    private NameSubject nameEvent = new NameSubject();
    private Person testSubject1 = new Person();
    private Person testSubject2 = new Person();

    private void doStuff()
    {
        //Register everyone
        ageEvent.register(testSubject1);
        ageEvent.register(testSubject2);
        nameEvent.register(testSubject1);
        nameEvent.register(testSubject2);

        //Everyone's age is now set to 20
        ageEvent.notify(20);

        //Everyone is now named Sam
        nameEvent.notify("Sam");

        //Remove everyone's registration
        ageEvent.unregister(testSubject1);
        ageEvent.unregister(testSubject2);
        nameEvent.unregister(testSubject1);
        nameEvent.unregister(testSubject2);
    }

}
