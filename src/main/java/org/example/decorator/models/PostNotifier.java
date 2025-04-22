package org.example.decorator.models;

public class PostNotifier implements Notifier{

    @Override
    public void send(String message) {
        System.out.println("sending via snail mail > " + message);
    }
}
