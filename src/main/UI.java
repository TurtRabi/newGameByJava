package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40,arial_80;

    public boolean messageOn=false;
    public String message="";
    public boolean gameOver=false;

    public String currentDialogue="";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80 = new Font("Arial", Font.BOLD, 80);


    }
    public void showMessage(String msg) {
        message = msg;
        messageOn=true;
    }
    public void draw(Graphics2D g2){
        this.g2d=g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //Player state
        if(gp.gameState==gp.playerState){
            //do playstate stuff later
        }
        //Pause state
        if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
        //Dialogue state
        if(gp.gameState==gp.dialogueState){
            drawDialogueScreen();
        }

    }

    public void drawDialogueScreen(){
        //Window
        int x =gp.tileSize*2;
        int y =gp.tileSize/2;
        int with = gp.screenWith-(gp.tileSize*4);
        int height=gp.tileSize*4;
        drawSubWindow(x,y,with,height);

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,32F));
        x+=gp.tileSize/2;
        y+=gp.tileSize;

        for(String line: currentDialogue.split("\n")){
            g2d.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawSubWindow(int x,int y,int with,int height){
        Color color =new Color(0,0,0,220);
        g2d.setColor(color);
        g2d.fillRoundRect(x,y,with,height,35,35);

        color = new Color(255,255,255);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x+5,y+5,with-10,height-10,25,25);
    }
    public void drawPauseScreen(){
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,80f));
        String text = "PAUSED";
        int x = getXForCenterText(text);
        int y=gp.screenHeight/2;

        g2d.drawString(text,x,y);
    }

    public int getXForCenterText(String text){
        int length=(int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x=gp.screenWith/2 -length/2;
        return x;
    }
}
