package com.cailliaud.chatbot;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

import java.util.List;

import static com.cailliaud.chatbot.DiscordProperties.BOT_TOKEN;

@Slf4j
public class Main {


    public static void main(String... args) throws Exception {
        JDA api = JDABuilder
                .createDefault(BOT_TOKEN.getValue())
                .setActivity(Activity.watching("Pornhub"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setAutoReconnect(true)
                .build();

        api.addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(MessageReceivedEvent event) {
                if (event.getAuthor().isBot()) return;
                Message message = event.getMessage();
                MessageChannel channel = event.getChannel();
                String content = message.getContentRaw();
                Guild guild = message.getGuild();


                if (event.getAuthor().getName().equals("Shiro Shisui")) {

                    channel.sendMessage("Shiro Shisui -> Toi je t'écoutes pas déjà !").queue();
                }

                if (event.getAuthor().getName().equals("Toinou")) {
                    if (content.equals("!feed")) {
                        Member armand = guild.getMemberByTag("kirarmand", "6070");


                        List<VoiceChannel> channels = guild.getVoiceChannelsByName("Toplaner feeder", true);
                        if (channels.isEmpty()) {
                            ChannelAction<VoiceChannel> action = guild.createVoiceChannel("Toplaner feeder");
                            VoiceChannel topLaner = action.complete();
                            channels.add(topLaner);
                        }

                        guild.moveVoiceMember(armand, channels.get(0)).queue();


                    }
                }


                if (event.getAuthor().getName().equals("kirarmand")) {
                    if (content.equals("!leave")) {
                        Member shiro = guild.getMemberByTag("Shiro Shisui", "5106");
                        VoiceChannel voiceChannel = shiro.getVoiceState().getChannel();
                        AudioManager manager = guild.getAudioManager();
                    }
                }

                if (event.getAuthor().equals(guild.getOwner().getUser())) {
                    if (content.equals("!join")) {
                        Member member = event.getMember();
                        VoiceChannel voiceChannel = member.getVoiceState().getChannel();
                        AudioManager manager = guild.getAudioManager();

                        manager.openAudioConnection(voiceChannel);

                    } else if (content.equals("!leave")) {
                        AudioManager manager = guild.getAudioManager();
                        manager.closeAudioConnection();
                    } else {
                        channel.sendMessage("Oh grand seigneur Cailliaud, je ne puis répondre à votre requête !").queue();
                    }
                }
            }
        });
    }
}
