package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font maruMonica,purisaB;

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
