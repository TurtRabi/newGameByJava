package object;

import javax.imageio.ImageIO;

public class OBJ_Door extends  SuperObject{

    public OBJ_Door() {
        name = "Dor";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        collision=true;
    }

}
