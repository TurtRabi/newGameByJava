package object;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    public OBJ_Boots() {
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
