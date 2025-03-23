package org.jarc;

import org.jarc.entity.MainCharacter;
import org.jarc.tile.TilesManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    private final int originalTileSize = 32; //pixel size of all game tiles.
    public final int upScaleFactor = 2; // scaling the original 2d assets to render on modern screens.
    final int panelBorderSpace = 2; // panel size is set with 2more pixels extra to have some space between actual content and border.
    public final int upScaledTileSize = originalTileSize * upScaleFactor;
    public final int gameRows = 10, gameColumns = 10;
    public final int gameScreenWidth = upScaledTileSize * gameColumns;
    public final int gameScreenHeight = upScaledTileSize * gameRows;
    private boolean runLoop = true; // a flag to control game loop.

    private Thread gameThread;
    final UserInputHandler inputHandler = new UserInputHandler();

    final int targetFps = 60;

    public TilesManager backGroundWorld = new TilesManager(this);

    public MainCharacter mainCharacter = new MainCharacter(this, inputHandler);


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

    public void endGameThread(){

        runLoop = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        while(runLoop){

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

        mainCharacter.update();
    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        backGroundWorld.draw(graphics2D);
        mainCharacter.draw(graphics2D);

        graphics2D.dispose();
    }

}
