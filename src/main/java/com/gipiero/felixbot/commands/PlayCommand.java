package com.gipiero.felixbot.commands;

import com.gipiero.felixbot.audio.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlayCommand {
    public static void onPlayCommand(MessageReceivedEvent event, String identifier) {
        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();
        VoiceChannel channel = voiceState.getChannel().asVoiceChannel();
        if (channel != null)
        {
            connectTo(channel, event.getChannel().asTextChannel(), identifier);
            onConnecting(channel, event.getChannel().asTextChannel());
        }
        else
        {
            onUnknownChannel(event.getChannel(), "your voice channel");
        }

    }

    private static void connectTo(VoiceChannel channel, TextChannel textChannel, String identifier)
    {
        Guild guild = channel.getGuild();
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager(); // make just one of these for whole app

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioPlayer player = playerManager.createPlayer();
        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        guild.getAudioManager().openAudioConnection(channel);
        guild.getAudioManager().setSendingHandler(trackScheduler);
        playerManager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                //textChannel.sendMessage("Track loaded").queue();
                System.out.println("Track loaded");
                trackScheduler.queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                //textChannel.sendMessage("Playlist loaded").queue();
                System.out.println("Playlist loaded");
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                //textChannel.sendMessage("Could not find match").queue();
                System.out.println("Could not find match");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                //textChannel.sendMessage("Load failed for some reason").queue();
                System.out.println("Load failed for some reason");
            }
        });
    }

    private static void onConnecting(VoiceChannel channel, TextChannel textChannel)
    {
        //textChannel.sendMessage("Connecting to " +channel.getName()).queue();
        System.out.println("Connecting to " +channel.getName());
    }

    private static void onUnknownChannel(MessageChannel channel, String comment)
    {
        //channel.sendMessage("Unable to connect to ``" + comment + "``, no such channel!").queue();
        System.out.println("Unable to connect to ``" + comment + "``, no such channel!");
    }

}
