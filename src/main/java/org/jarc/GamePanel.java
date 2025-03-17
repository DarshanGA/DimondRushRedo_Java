package org.jarc;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 32; //pixel size of all game tiles.
    final int upScaleFactor = 2; // scaling the original 2d assets to render on modern screens.
    final int panelBorderSpace = 2; // panel size is set with 2more pixels extra to have some space between actual content and border.
    final int upScaledTileSize = originalTileSize * upScaleFactor;
    final int gameRows = 10;
    final int gameColumns = 10;
    final int gameScreenWidth = upScaledTileSize * gameColumns;
    final int gameScreenHeight = upScaledTileSize * gameRows;

    private Thread gameThread;

    final UserInputHandler inputHandler = new UserInputHandler();

    final int playerOriginX = 0,
            playerOriginY = 0;
    private int currentPlayerPosX = playerOriginX,
            currentPlayerPosY = playerOriginY;
    final int movementSpeed = 5; // movement gets updated by 5px on each movement update.
    final int targetFps = 60;


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

        if(inputHandler.moveUp){

            currentPlayerPosY = currentPlayerPosY == 0? 0 : currentPlayerPosY - movementSpeed;
        }
        else if(inputHandler.moveLeft){

            currentPlayerPosX = currentPlayerPosX == 0? 0 : currentPlayerPosX - movementSpeed;
        }
        else if(inputHandler.moveDown){

            currentPlayerPosY = currentPlayerPosY == gameScreenHeight ? currentPlayerPosY : currentPlayerPosY + movementSpeed;
        }
        else if(inputHandler.moveRight){

            currentPlayerPosX = currentPlayerPosX == gameScreenWidth? currentPlayerPosX : currentPlayerPosX + movementSpeed;
        }
    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.yellow);
        graphics2D.drawRect(currentPlayerPosX,
                currentPlayerPosY,
                upScaledTileSize, upScaledTileSize);

        graphics2D.dispose();
    }

}
