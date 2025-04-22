package org.example.decorator.models;

public class FacebookNotifier extends BaseNotifier{
    public FacebookNotifier(Notifier wrapee) {
        super(wrapee);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("facebook message sent > " + message);
    }
}
