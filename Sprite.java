package flap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
    public static BufferedImage getSprite(String fileName) throws IOException {
        return ImageIO.read(Sprite.class.getResourceAsStream(fileName));
        //done this way so if complete game is a .jar file,
        //it will include all images and be packaged as a 
        //standalone game
    }
    
}


//Sprite Images go into folder:
//C:\Users\Elliot\Documents\Coding\Java\MyFlappyBirdGame\src\flap