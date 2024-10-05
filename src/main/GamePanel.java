package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {


    final int originalTileSize =16;
    final int scale =3;
    public final int tileSize = originalTileSize*scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWith = maxScreenCol*tileSize;
    public final int screenHeight = maxScreenRow*tileSize;
    public KeyHandler keyHandler = new KeyHandler(this);
    //FPS
    int FPS = 60;
    //World Settings

    public  final  int maxWorldCol=50;
    public  final  int maxWorldRow=50;

    public final  int maxWorldWith = tileSize*maxWorldCol;
    public final  int maxWorldHeight = maxWorldRow*tileSize;



    public final int worldWidth = maxWorldCol*tileSize;
    public final int worldHeight = maxWorldRow*tileSize;

    TileManager tileManager = new TileManager(this);
    AssetSetter assetSetter = new AssetSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Thread gameThread;
    //ENTITY AND OBJECT

    public Player player = new Player(this,keyHandler);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[]= new Entity[10];

    //Sound of Game
    Sound music = new Sound();
    Sound se = new Sound();

    public  UI ui = new UI(this);


    //Game State
    public int gameState;
    public final int titleState=0;
    public  final int playerState = 1;
    public  final int pauseState = 2;
    public final int dialogueState =3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWith, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setNPC();
        playMusic(0);
        gameState= titleState;
    }
    public  void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer=0;
        int drawCount = 0;


        while(gameThread!=null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime-lastTime);
            lastTime = currentTime;
            if(delta >= 1) {
                //UPDATE : update information;
                update();
                //DRAW : draw the screen with the update infomation
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }


    public void update(){
        if(gameState == playerState){
            player.update();
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].Update();
                }
            }
        }
        if(gameState == pauseState){
            return;
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2d);
            return;
        }
        else{
            //TITLE
            tileManager.draw(g2d);
            //OBJECT
            for(int i=0;i<obj.length;i++){
                if(obj[i]!=null){
                    obj[i].draw(g2d,this);
                }
            }
            //NPC
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].draw(g2d);
                }
            }
            //PLAYER
            player.draw(g2d,tileSize);

            ui.draw(g2d);
            g2d.dispose();
        }

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
