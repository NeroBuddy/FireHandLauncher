package net.firehand.firehandlauncher.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordManager {

    private DiscordRPC lib;
    private DiscordEventHandlers handlers;
    private DiscordRichPresence presence;

    public DiscordManager(){
        lib = DiscordRPC.INSTANCE;
        handlers = new DiscordEventHandlers();
        lib.Discord_Initialize("ODQzMzc0MDg1NDU1MDg1NTY4.YKC7Uw.mwKdNdrLV2wAKPHj5PGyOAuCtpE", handlers, true, "");
        presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "logo";
        presence.largeImageText = "";
        presence.smallImageKey = "logo";
        presence.smallImageText = "";
        presence.details = "details";
        presence.state = "state";
        lib.Discord_UpdatePresence(presence);
    }

    public void run(){
        new Thread("RPC-Callback-Handler"){

            @Override
            public void run(){
                while (!Thread.currentThread().isInterrupted()){
                    lib.Discord_UpdatePresence(presence);
                    lib.Discord_RunCallbacks();
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException exception){
                        Thread.currentThread().interrupt();
                    }
                }
            }

        }.start();
    }

    public void updateTime(){
        presence.startTimestamp = System.currentTimeMillis() / 1000;

    }

}
