package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font maruMonica,purisaB;

    BufferedImage heart_full, heart_half,heart_blank;

    public boolean messageOn=false;
    public String message="";
    public boolean gameOver=false;

    public String currentDialogue="";
    public int commandNum=0;
    public  int tiltleScreenState=0; //0: the first Screen, 1: the second screen

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is =getClass().getResourceAsStream("/res/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT,is);
            is = getClass().getResourceAsStream("/res/fonts/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Create Hud Objeatc
        SuperObject heart = new OBJ_Heart(gp);

        heart_full=heart.image;
        heart_half=heart.image2;
        heart_blank=heart.image3;

    }
    public void showMessage(String msg) {
        message = msg;
        messageOn=true;
    }
    public void draw(Graphics2D g2){
        this.g2d=g2;

        g2.setFont(maruMonica);
        //g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //Player state
        if(gp.gameState==gp.playerState){
            drawPlayerLife();
        }
        //Pause state
        if(gp.gameState==gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //Dialogue state
        if(gp.gameState==gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

    }

    public void drawPlayerLife(){
        gp.player.life=5;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int  i=0;

        //Draw Blank Heart
        while (i<gp.player.maxlife/2){
            g2d.drawImage(heart_blank,x,y,null);
            i++;
            x+= gp.tileSize;

        }

        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i=0;
        //Draw current life
        while (i<gp.player.life){
            g2d.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life){
                g2d.drawImage(heart_full,x,y,null);
            }
            i++;
            x+= gp.tileSize;
        }
    }

    public void drawTitleScreen(){
        if(tiltleScreenState==0){
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillRect(0,0,gp.screenWith,gp.screenHeight);
            //TITLE NAME
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,96F));
            String text = "Blue Boy Adventure";
            int x = getXForCenterText(text);
            int y =gp.tileSize*3;
            //SHADOW
            g2d.setColor(Color.GRAY);
            g2d.drawString(text,x+5,y+5);
            //MAIN COLOR
            g2d.setColor(Color.WHITE);
            g2d.drawString(text,x,y);

            //Blue Boy Image

            x=gp.screenWith/2-(gp.tileSize*2)/2;
            y+=gp.tileSize*2;
            g2d.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

            //Menu
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,48F));
            text ="NEW GAME";
            x = getXForCenterText(text);
            y+=gp.tileSize*3.5;
            g2d.drawString(text,x,y);
            if(commandNum==0){
                g2d.drawString(">",x-gp.tileSize,y);
            }

            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,32F));
            text ="LOAD GAME";
            x = getXForCenterText(text);
            y+=gp.tileSize;
            g2d.drawString(text,x,y);
            if(commandNum==1){
                g2d.drawString(">",x-gp.tileSize,y);
            }

            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,32F));
            text ="QUIT";
            x = getXForCenterText(text);
            y+=gp.tileSize;
            g2d.drawString(text,x,y);
            if(commandNum==2){
                g2d.drawString(">",x-gp.tileSize,y);
            }
        }else if(tiltleScreenState==1){
            //Class Selection Screen;
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXForCenterText(text);
            int y =gp.tileSize*3;

            g2d.drawString(text,x,y);

            text ="Fighter";
            x = getXForCenterText(text);
            y+=gp.tileSize*3;
            g2d.drawString(text,x,y);
            if(commandNum==0){
                g2d.drawString(">",x-gp.tileSize,y);
            }


            text ="Thief";
            x = getXForCenterText(text);
            y+=gp.tileSize;
            g2d.drawString(text,x,y);
            if(commandNum==1){
                g2d.drawString(">",x-gp.tileSize,y);
            }

            text ="Sorcerer";
            x = getXForCenterText(text);
            y+=gp.tileSize;
            g2d.drawString(text,x,y);
            if(commandNum==2){
                g2d.drawString(">",x-gp.tileSize,y);
            }

            text ="Back";
            x = getXForCenterText(text);
            y+=gp.tileSize*2;
            g2d.drawString(text,x,y);
            if(commandNum==3){
                g2d.drawString(">",x-gp.tileSize,y);
            }

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
