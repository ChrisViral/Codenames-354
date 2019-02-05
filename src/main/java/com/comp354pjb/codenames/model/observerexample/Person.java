package com.comp354pjb.codenames.model.observerexample;

public class Person implements AgeObserver, NameObserver
{
    private int age = 15;
    private String name = "Tom";

    @Override
    public void updateAge(Integer age)
    {
        this.age = age;
    }

    @Override
    public void updateName(String name)
    {
        this.name = name;
    }
}
