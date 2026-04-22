package com.problems.learning.designpatterns.behavioral.command.receivers;

public class Speaker {

    private final String location;
    private boolean playing;
    private int volume;      // 0 - 100
    private String currentTrack;

    public Speaker(String location) {
        this.location = location;
        this.volume = 50;
        this.playing = false;
    }

    public void play(String track) {
        this.playing = true;
        this.currentTrack = track;
        System.out.println("🎵 " + location + " speaker → PLAYING: " + track
                + " (vol: " + volume + ")");
    }

    public void stop() {
        this.playing = false;
        System.out.println("🎵 " + location + " speaker → STOPPED");
    }

    public void setVolume(int vol) {
        this.volume = vol;
        System.out.println("🎵 " + location + " speaker volume → " + vol);
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getVolume() {
        return volume;
    }

    public String getCurrentTrack() {
        return currentTrack;
    }

    public String getLocation() {
        return location;
    }
}