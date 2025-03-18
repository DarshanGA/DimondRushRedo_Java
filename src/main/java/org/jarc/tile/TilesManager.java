package org.jarc.tile;

import org.jarc.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;

public class TilesManager {

    private GamePanel gamePanel;

    private Tile[] tiles;

    public TilesManager(GamePanel givenGp){

        this.gamePanel = givenGp;
        this.tiles = new Tile[2];
        loadTiles();
    }

    private void loadTiles(){

        try{

            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/envirnomental/Envirnoment_VinesWithLeaves.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/envirnomental/GroundBrickBlock.png"));
        }
        catch(IOException e){

            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D){

        AffineTransform at = AffineTransform.getScaleInstance(gamePanel.upScaleFactor, gamePanel.upScaleFactor);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        for(int i = 0; i < tiles.length; i++){

            for (int j = 0; j < gamePanel.gameRows; j++){

                graphics2D.drawImage(tiles[i].image, scaleOp, (j * gamePanel.upScaledTileSize), (i * gamePanel.upScaledTileSize));
            }
        }

    }
}
