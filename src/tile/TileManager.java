package tile;

import main.GamePanel;
import main.UtiilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/maps/world01.txt");

    }
    public void getTileImage(){
        try{
            setup(0,"grass",false);
            setup(1,"wall",true);
            setup(2,"water",true);
            setup(3,"earth",false);
            setup(4,"tree",true);
            setup(5,"sand",false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void setup(int index,String imagePath, boolean collision){
        UtiilityTool uTool = new UtiilityTool();
        try{
            tiles[index]=new Tile();
            tiles[index].image= ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+imagePath+".png"));
            tiles[index].image= uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
            tiles[index].collision=collision;
        }catch (Exception e){
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
                g2d.drawImage(tiles[tileNum].image,screenX,screenY,null);
            }



            worldCol++;

            if (worldCol ==gp.maxWorldCol){
                worldCol =0;

                worldRow++;

            }
        }

    }
}
