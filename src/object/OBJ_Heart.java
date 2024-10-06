package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;
    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
            image2=ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_half.png"));
            image3=ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_blank.png"));
            image=Utool.scaleImage(image,gp.tileSize,gp.tileSize);
            image2=Utool.scaleImage(image2,gp.tileSize,gp.tileSize);
            image3=Utool.scaleImage(image3,gp.tileSize,gp.tileSize);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
