package net.firehand.firehandlauncher;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;
import net.firehand.firehandlauncher.discord.DiscordManager;

import javax.swing.*;

public class FireHandLauncher extends JFrame {

    private static FireHandLauncher instance;
    public static DiscordManager discordManager;
    private LauncherPanel launcherPanel;

    public FireHandLauncher(){
        setTitle("FireHandLauncher");
        setSize(1080, 720);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(Swinger.TRANSPARENT);
        setIconImage(Swinger.getResource("fire_logo.png"));
        launcherPanel = new LauncherPanel();
        setContentPane(launcherPanel);

        WindowMover mover = new WindowMover(this);
        addMouseListener(mover);
        addMouseMotionListener(mover);
        Animator.fadeInFrame(this, 10);
        setVisible(true);
    }

    public static void main(String[] args) {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/net/firehand/firehandlauncher/ressources");
        instance = new FireHandLauncher();
        discordManager = new DiscordManager();
    }

    public static FireHandLauncher getInstance(){
        return instance;
    }

    public LauncherPanel getLauncherPanel() { return launcherPanel; }

}
