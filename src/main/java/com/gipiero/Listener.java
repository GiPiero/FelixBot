package com.gipiero;

import com.gipiero.musicbot.audio.EchoHandler;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Listener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue();
        }
        else if (content.equals("!echo"))
        {
            onEchoCommand(event);
        }
    }

    private void onEchoCommand(MessageReceivedEvent event)
    {
        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();
        VoiceChannel channel = voiceState.getChannel().asVoiceChannel();
        if (channel != null)
        {
            connectTo(channel);
            onConnecting(channel, event.getChannel().asTextChannel());
        }
        else
        {
            onUnknownChannel(event.getChannel(), "your voice channel");
        }
    }

    private void connectTo(VoiceChannel channel)
    {
        Guild guild = channel.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        EchoHandler handler = new EchoHandler();

        audioManager.setSendingHandler(handler);
        audioManager.setReceivingHandler(handler);
        audioManager.openAudioConnection(channel);
    }

    private void onConnecting(VoiceChannel channel, TextChannel textChannel)
    {
        textChannel.sendMessage("Connecting to " +channel.getName()).queue();
    }

    private void onUnknownChannel(MessageChannel channel, String comment)
    {
        channel.sendMessage("Unable to connect to ``" + comment + "``, no such channel!").queue();
    }
}
