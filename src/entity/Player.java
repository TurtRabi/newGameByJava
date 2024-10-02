package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtiilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    //public int hasKey=0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        screenX = gp.screenWith/2-(gp.tileSize/2);
        screenY = gp.screenHeight/2-(gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32);

        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;


        setDefaultValue();
        getPlayerImage();
    }
    public void setDefaultValue(){
        worldX =gp.tileSize*23;
        worldY =gp.tileSize*21;
        speed=4;
        direction="down";
    }

    public void getPlayerImage(){
        up1=setup("boy_up_1");
        up2=setup("boy_up_2");
        down1=setup("boy_down_1");
        down2=setup("boy_down_2");
        left1=setup("boy_left_1");
        left2=setup("boy_left_2");
        right1=setup("boy_right_1");
        right2=setup("boy_right_2");
    }

    public BufferedImage setup(String imageName){
        UtiilityTool utiilityTool = new UtiilityTool();
        BufferedImage scaleImage = null;

        try{
            scaleImage = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName + ".png"));
            scaleImage=utiilityTool.scaleImage(scaleImage, gp.tileSize, gp.tileSize);
        }catch (Exception e){
            e.printStackTrace();
        }

        return scaleImage;
    }

    public void update(){
        if(keyHandler.upPressed || keyHandler.downPressed
                || keyHandler.leftPressed || keyHandler.rightPressed){
            if(keyHandler.upPressed){
                direction = "up";

            }else if(keyHandler.downPressed){
                direction = "down";

            }else if(keyHandler.leftPressed){
                direction = "left";

            }else if(keyHandler.rightPressed){
                direction = "right";

            }
            //check type collision
            collisionOn= false;
            gp.collisionChecker.checkTile(this);

            //check object collision
            int objIndex=gp.collisionChecker.checkObject(this,true);
            pickObject(objIndex);
            //if collision is false, player can move
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

    }

    public void pickObject(int index){
        if(index!=999){

        }
    }


    public void draw(Graphics2D g2d,int tileSize){
        //g2d.setColor(Color.WHITE);
        //g2d.fillRect(x, y, tileSize, tileSize);
        BufferedImage image = null;
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


        int x= screenX;
        int y=screenY;
        if(screenX>worldX){
            x=worldX;
        }
        if(screenY>worldY){
            y=worldY;
        }

        int rightOffset = gp.screenWith-screenX;
        if(rightOffset>gp.worldWidth-worldX){
            x =gp.screenWith -(gp.worldWidth-worldX);
        }

        int bottomOffset = gp.screenHeight-screenY;
        if(bottomOffset>gp.worldHeight-worldY){
            y =gp.screenHeight -(gp.worldHeight-worldY);
        }
        g2d.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
