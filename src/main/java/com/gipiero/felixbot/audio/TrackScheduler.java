package com.gipiero.felixbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public class TrackScheduler extends AudioEventAdapter implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public TrackScheduler(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
    }

    public void queue(AudioTrack track)
    {
        System.out.println("Track queued");
        if (!audioPlayer.startTrack(track, false)) {
            System.out.println("Track failed to play");
        }
        else {
            System.out.println("Track should be playing");
        }
    }

    // AudioEventAdapter overrides
    @Override
    public void onPlayerPause(AudioPlayer player)
    {
        // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player)
    {
        // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track)
    {
        // A Track Started Playing

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        System.out.println("The track ended for reason: " + endReason.name());

        if (endReason.mayStartNext) {

        }
    }

    // AudioSendHandler overrides
    @Override
    public boolean canProvide()
    {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio()
    {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
