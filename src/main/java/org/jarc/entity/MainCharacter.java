package org.jarc.entity;

import org.jarc.GamePanel;
import org.jarc.UserInputHandler;
import org.jarc.util.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainCharacter extends Entity{
    
    public GamePanel gamePanel;
    public UserInputHandler inputHandler;
    private BufferedImage leftMoveSprite, rightMoveSprite, downMoveSprite, upMoveSprite;
    private final ImageUtils imageUtils;
    public final int charScreenPosX, charScreenPosY;
    private Directions charDirection = Directions.STILL;

    public MainCharacter(GamePanel givenGp, UserInputHandler givenInpHand){

        this.gamePanel = givenGp;
        this.inputHandler = givenInpHand;
        this.worldPosX = 0 * givenGp.upScaledTileSize;
        this.worldPosY = 17 * givenGp.upScaledTileSize;
        this.movementSpeed = 8; // movement gets updated by Npx on each movement update.
        this.charScreenPosX = (gamePanel.gameScreenWidth / 2) - (gamePanel.upScaledTileSize / 2); // to place main char at the center of screen.
        this.charScreenPosY = (gamePanel.gameScreenHeight / 2) - (gamePanel.upScaledTileSize / 2); // to place main char at the center of screen.
        this.imageUtils = new ImageUtils(gamePanel.upScaleFactor);
        this.loadPlayerSprites();
    }

    public void update(){

        if(inputHandler.moveUp){

            worldPosY = worldPosY == 0? 0 : worldPosY - movementSpeed;
            charDirection = Directions.UP;
        }
        else if(inputHandler.moveLeft){

            worldPosX = worldPosX == 0? 0 : worldPosX - movementSpeed;
            charDirection = Directions.LEFT;
        }
        else if(inputHandler.moveDown){

            worldPosY = ((worldPosY + movementSpeed) == gamePanel.backGroundWorld.boundY) ? worldPosY : worldPosY + movementSpeed;
            charDirection = Directions.DOWN;
        }
        else if(inputHandler.moveRight){

            worldPosX = ((worldPosX + movementSpeed) == gamePanel.backGroundWorld.boundX) ? worldPosX : worldPosX + movementSpeed;
            charDirection = Directions.RIGHT;
        }
    }

    public void draw(Graphics2D graphics2D){

        BufferedImage placeholder = null;
        switch(charDirection){


            case LEFT, STILL -> placeholder = leftMoveSprite;
            case RIGHT -> placeholder = rightMoveSprite;
            case DOWN -> placeholder = downMoveSprite;
            case UP -> placeholder = upMoveSprite;
            default -> System.out.println("Unknown direction received!");

        }

        this.imageUtils.drawUpScaled(graphics2D, placeholder, charScreenPosX, charScreenPosY);

    }

    public void loadPlayerSprites(){

        try{

            leftMoveSprite = imageUtils.getRawImageFromRes("/mc/MainChar_Left.png");
            rightMoveSprite = imageUtils.getRawImageFromRes("/mc/MainChar_Right.png");
            downMoveSprite = imageUtils.getRawImageFromRes("/mc/MainCharClimbDown.png");
            upMoveSprite = imageUtils.getRawImageFromRes("/mc/MainCharClimbUp.png");
        }
        catch(IOException e){

            e.printStackTrace();
        }
    }
}
