package com.problems.learning.solid.dip.complier;

class SMSService implements MessageSender {

    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}