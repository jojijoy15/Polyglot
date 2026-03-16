package com.problems.learning.solid.dip.complier;

class EmailService implements MessageSender {

    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}