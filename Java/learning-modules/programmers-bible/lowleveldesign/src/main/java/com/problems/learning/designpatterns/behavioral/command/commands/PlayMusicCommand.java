package com.problems.learning.designpatterns.behavioral.command.commands;

import com.problems.learning.designpatterns.behavioral.command.receivers.Speaker;

public class PlayMusicCommand implements Command {

    private final Speaker speaker;
    private final String track;
    private boolean previouslyPlaying;
    private String previousTrack;
    private int previousVolume;

    public PlayMusicCommand(Speaker speaker, String track) {
        this.speaker = speaker;
        this.track = track;
    }

    @Override
    public void execute() {
        previouslyPlaying = speaker.isPlaying();
        previousTrack = speaker.getCurrentTrack();
        previousVolume = speaker.getVolume();
        speaker.play(track);
    }

    @Override
    public void undo() {
        if (previouslyPlaying && previousTrack != null) {
            speaker.play(previousTrack);
        } else {
            speaker.stop();
        }
        speaker.setVolume(previousVolume);
    }

    @Override
    public String getDescription() {
        return "Play '" + track + "' on " + speaker.getLocation() + " speaker";
    }
}