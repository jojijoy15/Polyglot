package com.problems.learning.solid.srp.complier;

import java.util.List;

//Good design
public class Interviewer {

    private final String name;
    private final Evaluator evaluator;
    private final Publisher publisher;
    private final InterviewNotificationSystem notificationSystem;

    public Interviewer(String name, Evaluator evaluator, Publisher publisher, InterviewNotificationSystem notification) {
        this.name = name;
        this.evaluator = evaluator;
        this.publisher = publisher;
        this.notificationSystem = notification;
    }

    public void conductInterview(Interviewee interviewee, Manager manager) {
        System.out.printf("Interviewer: %s conducting interview for interviewee : %s\n",
                this.name, interviewee.getName());
        double scored = evaluator.evaluate(interviewee, List.of(1, 4, 3, 2));
        interviewee.setScore(scored);
        publisher.publishResult(interviewee);
        notificationSystem.notifyManager(interviewee, manager);
        System.out.printf("Interviewer: %s completed interview for interviewee : %s\n",
                interviewee.getName(), interviewee.getName());
    }


}
