package org.jarc;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {

    public static String appName = "Diamond Rush";
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setTitle(appName);

        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();

        window.setVisible(true);

        panel.startGameThread();
    }
}
