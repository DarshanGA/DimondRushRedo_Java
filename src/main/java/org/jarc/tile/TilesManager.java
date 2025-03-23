package org.jarc.tile;

import org.jarc.GamePanel;
import org.jarc.util.ImageUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TilesManager {

    private GamePanel gamePanel;

    private final int mapRowTiles = 21, mapColumnTiles = 26;
    public final int boundX, boundY;

    private Tile[] tiles;
    private final int[][] map;

    private final ImageUtils imageUtils;

    public TilesManager(GamePanel givenGp){

        this.gamePanel = givenGp;
        this.imageUtils = new ImageUtils(this.gamePanel.upScaleFactor);
        this.tiles = new Tile[2];
        this.map = new int[mapRowTiles][mapColumnTiles];
        this.boundX = mapColumnTiles * givenGp.upScaledTileSize;
        this.boundY = mapRowTiles * givenGp.upScaledTileSize;
        loadTiles();
        loadMap("/levels/Level1Map.txt");
    }

    private void loadTiles(){

        try{

            tiles[0] = new Tile();
            tiles[0].image = imageUtils.getRawImageFromRes("/envirnomental/Envirnoment_VinesWithLeaves.png");

            tiles[1] = new Tile();
            tiles[1].image = imageUtils.getRawImageFromRes("/envirnomental/GroundBrickBlock.png");
        }
        catch(IOException e){

            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D){

        for(int i = 0; i < mapRowTiles; i++){

            for (int j = 0; j < mapColumnTiles; j++){

                imageUtils.drawUpScaled(
                        graphics2D,
                        tiles[map[i][j]].image,
                        gamePanel.mainCharacter.charScreenPosX - (gamePanel.mainCharacter.worldPosX - (j * gamePanel.upScaledTileSize)),
                        gamePanel.mainCharacter.charScreenPosY - (gamePanel.mainCharacter.worldPosY - (i * gamePanel.upScaledTileSize))
                );
                //imageUtils.drawUpScaled(graphics2D, tiles[map[i][j]].image, (j * gamePanel.upScaledTileSize), (i * gamePanel.upScaledTileSize));
            }
        }

    }

    public void loadMap(String mapDataPath){

        InputStream mapFile = getClass().getResourceAsStream(mapDataPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mapFile));
        String line = "";
        String[] tilesInRow;

        try {

            for(int i = 0; i < mapRowTiles; i++){

                line = bufferedReader.readLine();
                tilesInRow = line.split(",");
                for(int j = 0; j < mapColumnTiles; j++){

                    map[i][j] = Integer.parseInt(tilesInRow[j]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
