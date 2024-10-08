package entity;

import main.GamePanel;
import main.UtiilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction ="down";
        speed = 1;
        getImage();
        setDialogue();
    }


    public void getImage(){
        up1=setup("/res/npc/oldman_up_1");
        up2=setup("/res/npc/oldman_up_2");
        down1=setup("/res/npc/oldman_down_1");
        down2=setup("/res/npc/oldman_down_2");
        left1=setup("/res/npc/oldman_left_1");
        left2=setup("/res/npc/oldman_left_2");
        right1=setup("/res/npc/oldman_right_1");
        right2=setup("/res/npc/oldman_right_2");
    }

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter==120){
            Random rand = new Random();
            int i = rand.nextInt(100)+1;
            if(i<=25){
                direction = "up";
            }else if(i<=50){
                direction = "down";
            }else if(i<=75){
                direction = "left";
            }else if(i<=100){
                direction = "right";
            }
            actionLockCounter=0;
        }

    }

    public void setDialogue(){
        dialogues[0]="Hello,MinhLe!!";
        dialogues[1]="so you've come to this island to \nfind the treasure?";
        dialogues[2]="I used to be a great wizard but now... \nI'm a bit to old for taking an adventure.";
        dialogues[3]="Well, good luck on you.";
    }

    public void speak(){
        super.speak();
    }

}
