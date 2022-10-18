package com.gipiero.felixbot;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class MusicBot {
    private final ShardManager shardManager;

    public MusicBot() throws LoginException {
        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_VOICE_STATES
        );
        String token = System.getenv("BOT_TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token, intents);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new Listener());
        shardManager = builder.build();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting MusicBot");
        try {
            MusicBot bot = new MusicBot();
        } catch (LoginException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}