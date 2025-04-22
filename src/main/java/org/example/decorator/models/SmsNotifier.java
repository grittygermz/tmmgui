package org.example.decorator.models;

public class SmsNotifier extends BaseNotifier{

    public SmsNotifier(Notifier wrapee) {
        super(wrapee);
    }
    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("sms message sent > " + message);
    }
}
