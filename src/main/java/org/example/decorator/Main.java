package org.example.decorator;

import org.example.decorator.models.FacebookNotifier;
import org.example.decorator.models.Notifier;
import org.example.decorator.models.PostNotifier;
import org.example.decorator.models.SmsNotifier;

public class Main {
    void sendMessage(String message) {
        Notifier postNotifier = new PostNotifier();

        Notifier smsNotifier = new SmsNotifier(postNotifier);
        Notifier facebookNotifier = new FacebookNotifier(smsNotifier);
        facebookNotifier.send(message);
    }
}
