package main;

import entity.Player;
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
    KeyHandler keyHandler = new KeyHandler();
    //FPS
    int FPS = 60;
    //World Settings

    public  final  int maxWorldCol=50;
    public  final  int maxWorldRow=50;

    public final int worldWidth = maxWorldCol*tileSize;
    public final int worldHeight = maxWorldRow*tileSize;

    Thread gameThread;

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyHandler);
    TileManager tileManager = new TileManager(this);




    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWith, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d);
        player.draw(g2d,tileSize);

        g2d.dispose();
    }
}
