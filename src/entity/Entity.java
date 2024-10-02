package entity;

import main.GamePanel;
import main.UtiilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX,worldY;
    public  int speed;

    public BufferedImage up1,up2,down1,down2, left1, right1, left2, right2;
    public  String direction;

    public int spriteCounter =0;
    public  int spriteNum=1;

    public int actionLockCounter=0;

    //collision
    public Rectangle solidArea = new Rectangle(0,0,48,48);

    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn = false;
    GamePanel gp;


    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setup(String imageName){
        UtiilityTool utiilityTool = new UtiilityTool();
        BufferedImage scaleImage = null;

        try{
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage=utiilityTool.scaleImage(scaleImage, gp.tileSize, gp.tileSize);
        }catch (Exception e){
            e.printStackTrace();
        }

        return scaleImage;
    }

    public void setAction(){

    }
    public void Update(){
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
        gp.collisionChecker.checkPlayer(this);

        if(collisionOn==false){
            switch (direction){
                case "up":
                    worldY-=speed;
                    break;
                case "down":
                    worldY+=speed;
                    break;
                case "left":
                    worldX-=speed;
                    break;
                case "right":
                    worldX+=speed;
                    break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 10){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public  void draw(Graphics2D g2d){
        BufferedImage image =null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize>gp.player.worldX-gp.player.screenX
                && worldX - gp.tileSize<gp.player.worldX+gp.player.screenX
                && worldY + gp.tileSize>gp.player.worldY-gp.player.screenY
                && worldY - gp.tileSize<gp.player.worldY+gp.player.screenY){
            switch(direction){
                case "up":
                    if(spriteNum==1){
                        image = up1;
                    }
                    if(spriteNum==2){
                        image = up2;
                    }

                    break;
                case "down":
                    if(spriteNum==1){
                        image = down1;
                    }
                    if(spriteNum==2){
                        image = down2;
                    }

                    break;
                case "left":
                    if(spriteNum==1){
                        image = left1;
                    }
                    if(spriteNum==2){
                        image = left2;
                    }

                    break;
                case "right":
                    if(spriteNum==1){
                        image = right1;
                    }
                    if(spriteNum==2){
                        image = right2;
                    }

                    break;

            }
            g2d.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
        }
    }
}
