package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Door extends  SuperObject{

    GamePanel gp;
    public OBJ_Door(GamePanel gp) {
        this.gp = gp;
        name = "Dor";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            Utool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        collision=true;
    }

}
