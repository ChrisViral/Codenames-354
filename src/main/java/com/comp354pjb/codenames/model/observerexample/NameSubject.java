package com.comp354pjb.codenames.model.observerexample;

import com.comp354pjb.codenames.model.Subject;

public class NameSubject extends Subject<NameObserver, String>
{
    @Override
    protected void update(NameObserver listener, String data)
    {
        listener.updateName(data);
    }
}
