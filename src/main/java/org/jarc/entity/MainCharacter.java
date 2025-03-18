package org.jarc.entity;

import org.jarc.GamePanel;
import org.jarc.UserInputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainCharacter extends Entity{

    private int playerOriginX = 0,
            playerOriginY = 0;
    private int currentPlayerPosX = playerOriginX,
            currentPlayerPosY = playerOriginY;

    public GamePanel gamePanel;
    public UserInputHandler inputHandler;

    private BufferedImage leftMoveSprite, rightMoveSprite;

    private Directions charDirection = Directions.STILL;

    public MainCharacter(GamePanel givenGp, UserInputHandler givenInpHand){

        this.gamePanel = givenGp;
        this.inputHandler = givenInpHand;
        this.movementSpeed = 8; // movement gets updated by 5px on each movement update.
        this.loadPlayerSprites();
    }

    public void update(int boundX, int boundY){

        if(inputHandler.moveUp){

            currentPlayerPosY = currentPlayerPosY == 0? 0 : currentPlayerPosY - movementSpeed;
            charDirection = Directions.UP;
        }
        else if(inputHandler.moveLeft){

            currentPlayerPosX = currentPlayerPosX == 0? 0 : currentPlayerPosX - movementSpeed;
            charDirection = Directions.LEFT;
        }
        else if(inputHandler.moveDown){

            currentPlayerPosY = currentPlayerPosY == boundY ? currentPlayerPosY : currentPlayerPosY + movementSpeed;
            charDirection = Directions.DOWN;
        }
        else if(inputHandler.moveRight){

            currentPlayerPosX = currentPlayerPosX == boundX? currentPlayerPosX : currentPlayerPosX + movementSpeed;
            charDirection = Directions.RIGHT;
        }
    }

    public void draw(Graphics2D given2dGraph){

        /*given2dGraph.setColor(Color.yellow);
        given2dGraph.drawRect(currentPlayerPosX,
                currentPlayerPosY,
                32 * 2, 32 * 2);*/

        BufferedImage placeholder = null;
        switch(charDirection){


            case UP, LEFT, STILL -> placeholder = leftMoveSprite;
            case DOWN, RIGHT -> placeholder = rightMoveSprite;
            default -> System.out.println("Unknown direction received!");

        }

        AffineTransform at = AffineTransform.getScaleInstance(gamePanel.upScaleFactor, gamePanel.upScaleFactor);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        given2dGraph.drawImage(placeholder, scaleOp, currentPlayerPosX, currentPlayerPosY);

    }

    public void loadPlayerSprites(){

        try{

            leftMoveSprite = ImageIO.read(getClass().getResourceAsStream("/mc/MainChar_Left.png"));
            rightMoveSprite = ImageIO.read(getClass().getResourceAsStream("/mc/MainChar_Right.png"));
        }
        catch(IOException e){

            e.printStackTrace();
        }
    }
}
