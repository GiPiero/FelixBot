package com.gipiero.musicbot;

import com.gipiero.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

public class MusicBot {
    public static void main(String[] args) throws Exception{
        System.out.println("Starting MusicBot");
        String token = System.getenv("BOT_TOKEN");

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_VOICE_STATES
        );

        JDA api = JDABuilder.createDefault(token, intents)
                    .addEventListeners(new Listener())
                    .build();
    }
}