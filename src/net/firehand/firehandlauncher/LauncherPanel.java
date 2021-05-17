package net.firehand.firehandlauncher;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.theshark34.swinger.textured.STexturedProgressBar;
import net.firehand.firehandlauncher.auth.Auth;
import net.firehand.firehandlauncher.auth.AuthMineweb;
import net.firehand.firehandlauncher.auth.exceptions.AuthenticationUnavailableException;
import net.firehand.firehandlauncher.auth.exceptions.RequestException;
import net.firehand.firehandlauncher.auth.responses.AuthenticationResponse;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.File;
import java.net.Authenticator;
import java.util.concurrent.atomic.AtomicBoolean;

public class LauncherPanel extends JPanel implements SwingerEventListener {

    Image image = Swinger.getResource("launcher-background.png");
    private final STexturedButton play = new STexturedButton(Swinger.getResource("playbutton.png"),Swinger.getResource("playbutton.png"));
    private final STexturedButton launcherOverlayBlue = new STexturedButton(Swinger.getResource("launcher-overlay-blue.png"),Swinger.getResource("launcher-overlay-blue.png"));
    private final STexturedButton fireHandClientText = new STexturedButton(Swinger.getResource("Firehandclient-text.png"),Swinger.getResource("Firehandclient-text.png"));
    private final STexturedButton fireHandHand = new STexturedButton(Swinger.getResource("firehand-hand.png"),Swinger.getResource("firehand-hand.png"));
    private final STexturedButton accounts = new STexturedButton(Swinger.getResource("Accounts-button.png"),Swinger.getResource("Accounts-button.png"));
    private final STexturedProgressBar bar = new STexturedProgressBar(Swinger.getResource("lollollo.png"), Swinger.getResource("second.png"));
    private RamSelector ramSelector = new RamSelector(new File(Launcher.DIR, "ram.properties"));
    private Saver saver = new Saver(new File(Launcher.DIR, "credentials.properties"));
    private JTextField jTextField = new JTextField(saver.get("username"));
    private JPasswordField passwordField = new JPasswordField(saver.get("password"));
    AtomicBoolean connectedWithMojang = new AtomicBoolean(true);

    public LauncherPanel(){
        setLayout(null);
        setBackground(Swinger.TRANSPARENT);

        play.addEventListener(this);
        play.setBounds(380, 520, 325, 110);
        play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(play);

        accounts.addEventListener(this);
        accounts.setBounds(830, 10, 250, 65);
        accounts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(accounts);

        fireHandHand.setBounds(475, -5, 120, 164);
        add(fireHandHand);

        fireHandClientText.addEventListener(this);
        fireHandClientText.setBounds(370, 180, 325, 130);
        add(fireHandClientText);

        launcherOverlayBlue.setBounds(0, 0, 1080, 200);
        add(launcherOverlayBlue);

        bar.setBounds(380, 580, 325,11);
        add(bar);

        jTextField.setForeground(new Color(255, 255, 255));
        jTextField.setFont(new Font("Arial", Font.BOLD, 20));
        jTextField.setCaretColor(new Color(255, 255, 255));
        jTextField.setOpaque(false);
        jTextField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jTextField.setBounds(830, 100, 250, 50);
        add(jTextField);

        passwordField.setForeground(new Color(255, 255, 255));
        passwordField.setFont(new Font("Arial", Font.BOLD, 20));
        passwordField.setCaretColor(new Color(255, 255, 255));
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        passwordField.setBounds(830, 200, 250, 50);
        add(passwordField);
    }

    @Override
    public void onEvent(SwingerEvent event){
        if(event.getSource() == play){
            System.out.println("play");
        }else if(event.getSource() == ramSelector){
            ramSelector.display();
        }else if(event.getSource() == accounts){
            start();
        }
    }

    private void setFieldEnabled(boolean enabled){
        jTextField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        play.setEnabled(enabled);
        accounts.setEnabled(enabled);
        fireHandHand.setEnabled(enabled);
        fireHandClientText.setEnabled(enabled);
        launcherOverlayBlue.setEnabled(enabled);
        bar.setEnabled(enabled);
    }

    public void start(){
        setFieldEnabled(false);
        if(jTextField.getText().replaceAll(" ", "").length() == 5){
            JOptionPane.showMessageDialog(this, "Bitte gebe einen gültigen Spielernamen ein!", "Fehler 01", JOptionPane.ERROR_MESSAGE);
            setFieldEnabled(true);
            return;
        }
        Thread thread = new Thread(){
            @Override
            public void run() {

                if(connectedWithMojang.get()){
                    try {
                        AuthenticationResponse response = Auth.authenticate(jTextField.getText(), passwordField.getText());
                        System.out.println("===============Auth===============");
                        System.out.println("Acces token: " + response.getAccessToken());
                        System.out.println("Account name: " + response.getSelectedProfile().getName());
                        System.out.println("Account id: " + response.getSelectedProfile().getUUID());
                    }catch (RequestException | AuthenticationUnavailableException exception){
                        System.out.println(exception.getMessage());
                    }
                }else {
                    AuthMineweb authMineweb = new AuthMineweb(jTextField.getText(), passwordField.getText());
                    if (authMineweb.isConnected()){
                        System.out.println("connected" + authMineweb.getInfos("pseudo"));
                    }else {
                        System.out.println("disconnected");
                    }
                }

            }
        };
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Swinger.drawFullsizedImage(graphics, this, image);
    }

    public STexturedProgressBar getProgressBar(){ return bar; }
    public RamSelector getRamSelector(){ return  ramSelector; }

}
