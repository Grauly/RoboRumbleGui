package grauly.roborumblegui.visuals;

import grauly.roborumblegui.data.RoboColor;
import grauly.roborumblegui.data.RoboDirection;
import grauly.roborumblegui.data.RoboRumbleConstants;
import javafx.scene.image.Image;

public class ImageHandler {

    private final Image[] redImages = new Image[4];
    private final Image[] bluImages = new Image[4];
    private final Image[][] arrayArray = {redImages, bluImages};
    private final Image tileImage = new Image(RoboRumbleConstants.TILE_IMAGE_PATH);

    private void loadImage(RoboColor color, RoboDirection direction) {
        String imagePath = RoboRumbleConstants.CHARACTER_IMAGE_ROOT_DIR + RoboColor.getMessageString(color) + "_" + "000" + direction.ordinal() + ".png";
        Image loadedImage = new Image(imagePath, false);
        arrayArray[color.ordinal()][direction.ordinal()] = loadedImage;
    }
    private boolean existsImage(RoboColor color, RoboDirection direction) {
        return arrayArray[color.ordinal()][direction.ordinal()] != null;
    }

    public synchronized Image getImage(RoboColor color, RoboDirection direction) {
        if(color.equals(RoboColor.NONE)) {
            return tileImage;
        }
        if(!existsImage(color,direction)) {
            loadImage(color,direction);
        }
        return arrayArray[color.ordinal()][direction.ordinal()];
    }
}
