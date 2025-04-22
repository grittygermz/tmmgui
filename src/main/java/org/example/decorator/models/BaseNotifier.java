package org.example.decorator.models;

public class BaseNotifier implements Notifier{

    private Notifier wrapee;

    public BaseNotifier(Notifier wrapee) {
        this.wrapee = wrapee;
    }

    @Override
    public void send(String message) {
        wrapee.send(message);
    }
}
