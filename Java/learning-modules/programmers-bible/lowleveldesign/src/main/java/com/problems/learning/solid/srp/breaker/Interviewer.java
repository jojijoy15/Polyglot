package com.problems.learning.solid.srp.breaker;

import java.util.Map;
import java.util.Random;

//Bad design
public class Interviewer {

    private String name;

    public Interviewer(String name) {
        this.name = name;
    }

    public void assignScore(Interviewee interviewee, double score) {
        interviewee.setScore(score);
    }

    //Only responsibility of Interviewer
    public void conductInterview(Interviewee interviewee,
                                 Map<String, Double> scoreBoard, Manager manager) {
        System.out.printf("Interviewer: %s conducting interview for interviewee : %s\n",
                interviewee.getName(), interviewee.getName());
        this.evaluateInterviewee(interviewee);
        this.assignScore(interviewee, interviewee.getScore());
        this.publishResult(interviewee,scoreBoard);
        this.notifyManager(interviewee, manager);
    }

    /*
        What's wrong?
         Is it responsible for evaluating result?
         What if evaluation logic changes?
     */
    public void evaluateInterviewee(Interviewee interviewee){
        Random rand = new Random();
        double score = rand.nextDouble(0.0, 4.0); //
        this.assignScore(interviewee, score);
    }

    /*
        What's wrong?
         Is it responsible for publishing result?
         What if mechanism of publishing result changes?
     */
    public void publishResult(Interviewee interviewee, Map<String, Double> scoreBoard) {
        scoreBoard.put(interviewee.getName(), interviewee.getScore());
    }

    /*
       What's wrong?
        Is it responsible for notifying about result?
        What if mechanism of notifying result changes?
    */
    public void notifyManager(Interviewee interviewee, Manager manager) {
        InterviewNotificationSystem interviewNotificationSystem = new InterviewNotificationSystem();
        interviewNotificationSystem.notifyManager(interviewee, manager);
    }


}
