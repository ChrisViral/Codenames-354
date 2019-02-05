package com.comp354pjb.codenames.model.observerexample;

import com.comp354pjb.codenames.model.Subject;

public class AgeSubject extends Subject<AgeObserver, Integer>
{
    @Override
    protected void update(AgeObserver listener, Integer data)
    {
        listener.updateAge(data);
    }
}
