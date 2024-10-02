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
        tiles = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/maps/worldV2.txt");

    }
    public void getTileImage(){
        try{

            //PLAYCEHOLDER
            setup(0,"grass00",false);
            setup(1,"grass00",false);
            setup(2,"grass00",false);
            setup(3,"grass00",false);
            setup(4,"grass00",false);
            setup(5,"grass00",false);
            setup(6,"grass00",false);
            setup(7,"grass00",false);
            setup(8,"grass00",false);
            setup(9,"grass00",false);
            //PLAYCEHOLDER



            setup(10,"grass00",false);
            setup(11,"grass01",false);
            setup(12,"water00",true);
            setup(13,"water01",true);
            setup(14,"water02",true);
            setup(15,"water03",true);
            setup(16,"water04",true);
            setup(17,"water05",true);
            setup(18,"water06",true);
            setup(19,"water07",true);
            setup(20,"water08",true);
            setup(21,"water09",true);
            setup(22,"water10",true);
            setup(23,"water11",true);
            setup(24,"water12",true);
            setup(25,"water13",true);
            setup(26,"road00",false);
            setup(27,"road01",false);
            setup(28,"road02",false);
            setup(29,"road03",false);
            setup(30,"road04",false);
            setup(31,"road05",false);
            setup(32,"road06",false);
            setup(33,"road07",false);
            setup(34,"road08",false);
            setup(35,"road09",false);
            setup(36,"road10",false);
            setup(37,"road11",false);
            setup(38,"road12",false);
            setup(39,"earth",false);
            setup(40,"wall",true);
            setup(41,"tree",true);


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

            //stop moving camera at the edge
            if(gp.player.screenX>gp.player.worldX){
                screenX =worldX;
            }
            if(gp.player.screenY>gp.player.worldY){
                screenY =worldY;
            }

            int rightOffset = gp.screenWith-gp.player.screenX;
            if(rightOffset>gp.worldWidth-gp.player.worldX){
                screenX =gp.screenWith -(gp.worldWidth-worldX);
            }

            int bottomOffset = gp.screenHeight-gp.player.screenY;
            if(bottomOffset>gp.worldHeight-gp.player.worldY){
                screenY =gp.screenHeight -(gp.worldHeight-worldY);
            }


            if(worldX + gp.tileSize>gp.player.worldX-gp.player.screenX
                    && worldX - gp.tileSize<gp.player.worldX+gp.player.screenX
                    && worldY + gp.tileSize>gp.player.worldY-gp.player.screenY
                    && worldY - gp.tileSize<gp.player.worldY+gp.player.screenY){
                g2d.drawImage(tiles[tileNum].image,screenX,screenY,null);
            }else if(gp.player.screenX>gp.player.worldX||gp.player.screenY>gp.player.worldY
            ||rightOffset>gp.worldWidth-gp.player.worldX||bottomOffset>gp.worldHeight-gp.player.worldY){
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
