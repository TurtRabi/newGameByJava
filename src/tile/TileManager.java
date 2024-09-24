package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/maps/world01.txt");

    }
    public void getTileImage(){
        try{
            tiles[0]=new Tile();
            tiles[0].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));

            tiles[1]=new Tile();
            tiles[1].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));

            tiles[2]=new Tile();
            tiles[2].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));

            tiles[3]=new Tile();
            tiles[3].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));

            tiles[4]=new Tile();
            tiles[4].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));

            tiles[5]=new Tile();
            tiles[5].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col<gp.maxWorldCol && row<gp.maxWorldRow){
                String line = br.readLine();
                while (col<gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col==gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        int worldCol =0;
        int worldRow =0;

        while ((worldCol <gp.maxWorldCol) && worldRow <gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol*gp.tileSize;
            int worldY = worldRow*gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if(worldX + gp.tileSize>gp.player.worldX-gp.player.screenX
                    && worldX - gp.tileSize<gp.player.worldX+gp.player.screenX
                    && worldY + gp.tileSize>gp.player.worldY-gp.player.screenY
                    && worldY - gp.tileSize<gp.player.worldY+gp.player.screenY){
                g2d.drawImage(tiles[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }

            worldCol++;

            if (worldCol ==gp.maxWorldCol){
                worldCol =0;

                worldRow++;

            }
        }

    }
}
