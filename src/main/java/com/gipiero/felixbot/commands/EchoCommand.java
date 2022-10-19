package com.gipiero.felixbot.commands;

import com.gipiero.felixbot.audio.EchoHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class EchoCommand {
    public static void onEchoCommand(Member member, TextChannel textChannel) {
        GuildVoiceState voiceState = member.getVoiceState();
        VoiceChannel channel = voiceState.getChannel().asVoiceChannel();
        if (channel != null) {
            connectTo(channel);
            onConnecting(channel, textChannel);
        } else {
            onUnknownChannel(textChannel, "your voice channel");
        }
    }

    private static void connectTo(VoiceChannel channel) {
        Guild guild = channel.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        EchoHandler handler = new EchoHandler();

        audioManager.setSendingHandler(handler);
        audioManager.setReceivingHandler(handler);
        audioManager.openAudioConnection(channel);
    }

    private static void onConnecting(VoiceChannel channel, TextChannel textChannel) {
        textChannel.sendMessage("Connecting to " + channel.getName()).queue();
    }

    private static void onUnknownChannel(TextChannel textChannel, String comment) {
        textChannel.sendMessage("Unable to connect to ``" + comment + "``, no such channel!").queue();
    }
}
