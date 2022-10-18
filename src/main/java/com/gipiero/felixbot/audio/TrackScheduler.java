package com.gipiero.felixbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.nio.ByteBuffer;

public class TrackScheduler extends AudioEventAdapter implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;
    private TextChannel textChannel;

    public TrackScheduler(AudioPlayer audioPlayer, TextChannel textChannel) {
        this.audioPlayer = audioPlayer;
        this.textChannel = textChannel;
    }

    public void queue(AudioTrack track) {
        if (!audioPlayer.startTrack(track, false)) {
            System.out.println("Track failed to queue");
        } else {
            System.out.println("Track queued");
        }
    }

    // AudioEventAdapter overrides
    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        System.out.println("Track should be playing");

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        System.out.println("The track ended for reason: " + endReason.name());
        textChannel.sendMessage("The track ended for reason: " + endReason.name()).queue();
        if (endReason.mayStartNext) {

        }
    }

    // AudioSendHandler overrides
    @Override
    public boolean canProvide() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
