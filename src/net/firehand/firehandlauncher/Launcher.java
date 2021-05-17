package net.firehand.firehandlauncher;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.ProcessLogManager;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;

import java.io.File;
import java.util.Arrays;

public class Launcher {

    public static final GameVersion VERSION = new GameVersion("1.16.5", GameType.V1_13_HIGHER_FORGE);
    public static final GameInfos INFOS = new GameInfos("FireHand", VERSION, new GameTweak[0]);
    public static final File DIR = INFOS.getGameDir();
    private static AuthInfos authInfos = new AuthInfos("null", "null", "null");
    private static Thread updateThread;
    private static Process process;

    public static void update() throws Exception{
        SUpdate sUpdate = new SUpdate("https://valkyria.fr/launcher/launcher/", DIR);
        sUpdate.getServerRequester().setRewriteEnabled(true);
        sUpdate.addApplication(new FileDeleter());

        updateThread = new Thread(){
            private int value;
            private int max;

            public void run(){
                while (!isInterrupted()){
                    FireHandLauncher.getInstance().getLauncherPanel().setVisible(true);
                    this.value = ((int)(BarAPI.getNumberOfTotalDownloadedBytes() / 1000L));
                    this.max = ((int)(BarAPI.getNumberOfTotalBytesToDownload() / 1000L));
                    FireHandLauncher.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
                    FireHandLauncher.getInstance().getLauncherPanel().getProgressBar().setValue(value);
                }
            }
        };
        updateThread.start();
        sUpdate.start();
        updateThread.interrupt();
    }

    public static void launch() throws LaunchException{
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(INFOS, GameFolder.BASIC, authInfos);
        profile.getVmArgs().addAll(Arrays.asList(FireHandLauncher.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        process = launcher.launch();
        ProcessLogManager manager = new ProcessLogManager(process.getInputStream(), new File(DIR, "logs.txt"));
        manager.start();
        try {
            Thread.sleep(500L);
            FireHandLauncher.getInstance().setVisible(false);
            process.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FireHandLauncher.getInstance().setVisible(false);

    }

    public static void interruptThread(){
        updateThread.interrupt();
    }

}
