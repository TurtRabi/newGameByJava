package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        this.gp = gp;
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
            Utool.scaleImage(image,gp.tileSize , gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
