package com.problems.learning.designpatterns.structural.bridge.channel;

public class SmsChannel implements DeliveryChannel {
    @Override
    public void send(String recipient, String subject, String body) {
        // SMS has no subject — just a short message
        String sms = subject + ": " + body;
        if (sms.length() > 160)
            sms = sms.substring(0, 157) + "...";  // SMS limit
        System.out.println("[SMS]");
        System.out.println("  To  : " + recipient);
        System.out.println("  Msg : " + sms);
    }
}