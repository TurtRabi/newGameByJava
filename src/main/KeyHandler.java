package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public  boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp =gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //TITLE STATE

        if(gp.gameState == gp.titleState){
            if(gp.ui.tiltleScreenState==0){
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2){
                        gp.ui.commandNum = 0;
                    }
                }

                if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum==0){
                        gp.ui.tiltleScreenState=1;
                    }
                    if(gp.ui.commandNum==1){
                        //add late
                    }
                    if(gp.ui.commandNum==2){
                        System.exit(0);
                    }
                    gp.ui.commandNum = -1;
                }
            }


            if(gp.ui.tiltleScreenState==1){
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3){
                        gp.ui.commandNum = 0;
                    }
                }

                if(code == KeyEvent.VK_ENTER) {

                    if(gp.ui.commandNum==0){
                        gp.gameState=gp.playerState;
                        System.out.println("Do some fighter specific stuff!");
                    }
                    if(gp.ui.commandNum==1){
                        gp.gameState=gp.playerState;
                        System.out.println("Do some thief specific stuff!");
                    }
                    if(gp.ui.commandNum==2){
                        gp.gameState=gp.playerState;
                        System.out.println("Do some sorcerer specific stuff!");
                    }
                    if(gp.ui.commandNum==3){
                        gp.ui.tiltleScreenState=0;
                    }
                }
            }

        }

        //PLAYER STATE

        if(gp.gameState==gp.playerState){
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_P) {
                gp.gameState=gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed=true;
            }
        }
        else if(gp.gameState==gp.pauseState){
            if (code == KeyEvent.VK_P) {
                gp.gameState=gp.playerState;
            }
        }
        else if(gp.gameState==gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState=gp.playerState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }
}
