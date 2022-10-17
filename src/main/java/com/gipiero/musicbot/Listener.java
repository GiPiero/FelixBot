package com.gipiero.musicbot;

import com.gipiero.musicbot.commands.EchoCommand;
import com.gipiero.musicbot.commands.PlayCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


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
            EchoCommand.onEchoCommand(event);
        }
        else if (content.startsWith("!play ")){
            String arg = content.substring("!play ".length());
            PlayCommand.onPlayCommand(event, arg);
        }
    }


}
