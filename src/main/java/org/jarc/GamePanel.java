package org.jarc;

import org.jarc.entity.MainCharacter;
import org.jarc.tile.TilesManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public final int originalTileSize = 32; //pixel size of all game tiles.
    public final int upScaleFactor = 2; // scaling the original 2d assets to render on modern screens.
    final int panelBorderSpace = 2; // panel size is set with 2more pixels extra to have some space between actual content and border.
    public final int upScaledTileSize = originalTileSize * upScaleFactor;
    public final int gameRows = 10, gameColumns = 10;
    final int gameScreenWidth = upScaledTileSize * gameColumns;
    final int gameScreenHeight = upScaledTileSize * gameRows;

    private Thread gameThread;

    final UserInputHandler inputHandler = new UserInputHandler();

    final int targetFps = 60;

    private TilesManager bg = new TilesManager(this);

    private MainCharacter mc = new MainCharacter(this, inputHandler);


    public GamePanel(){

        this.setPreferredSize(new Dimension(this.gameScreenWidth + panelBorderSpace, this.gameScreenHeight + panelBorderSpace));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

    }


    // this is related to thread, when we start or initiate the thread this 'run()' method will be called.
    @Override
    public void run() {

        double drawInterval = 1000000000.0 / targetFps,
                lastTime = System.nanoTime(),
                timer = 0,
                delta = 0.0,
                currentTime;
        int fpsCounter = 0;

        // we will build game loop here.
        while(gameThread != null){

             currentTime = System.nanoTime() + drawInterval;

             delta += (currentTime - lastTime) / drawInterval;
             timer += (currentTime - lastTime);
             lastTime = currentTime;

             if(delta >= 1){

                 // two main things that usually happen in game loops
                 // update call,to update information according to user actions.
                 update();

                 // draw, render the latest state of game as per updated actions.
                 repaint();

                 delta--;
                 fpsCounter++;
             }

             if(timer >= 1000000000.0){

                 System.out.println("Current FPS: " + fpsCounter);
                 fpsCounter = 0;
                 timer = 0;
             }
        }
    }

    public void update(){

        mc.update(gameScreenWidth, gameScreenHeight);
    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        bg.draw(graphics2D);
        mc.draw(graphics2D);

        graphics2D.dispose();
    }

}
