package com.gipiero.felixbot.listeners;

import com.gipiero.felixbot.commands.EchoCommand;
import com.gipiero.felixbot.commands.PlayCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;


public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();

        if (command.equals("ping"))
        {
            event.reply("pong!").queue();
        }
        else if (command.equals("echo"))
        {
            EchoCommand.onEchoCommand(event.getMember(), event.getChannel().asTextChannel());
        }
        else if (command.equals("play"))
        {
            Member member = event.getMember();
            TextChannel tc = event.getChannel().asTextChannel();
            String arg = event.getOption("input").getAsString();
            PlayCommand.onPlayCommand(member, tc, arg);
        }
    }

    // When first joining a guild - max 100 commands TODO: use onReady instead
    @Override
    public void onReady(ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<CommandData>();
        commandData.add(Commands.slash("ping", "Get a pong."));
        commandData.add(Commands.slash("play", "Play something.").addOption(OptionType.STRING, "input", "URL or search query"));
        commandData.add(Commands.slash("echo", "Echo voice channel audio."));
        event.getJDA().updateCommands().addCommands(commandData).queue(); // Using queue throws an exception for some reason.
    }
}
