package com.problems.learning.solid.dip.breaker;

class NotificationService {

    private EmailService emailService = new EmailService();

    public void notifyUser(String message) {
        emailService.sendEmail(message);
    }
}