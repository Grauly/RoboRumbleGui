package grauly.roborumblegui.data;

public class RoboRumbleConstants {
    public static final String TERMINATION_CHARACTER = "";
    public static final String ACK_STRING = "OK";
    public static final String ROTATE_LEFT_STRING = "l";
    public static final String ROTATE_RIGHT_STRING = "r";
    public static final String DIE_STRING = "s";
    public static final String KILL_STRING = "k";
    public static final String RED_IDENTIFIER = "red";
    public static final String BLUE_IDENTIFIER = "blu";
    public static final Vector2 RED_START_POS = new Vector2(3,0);
    public static final Vector2 BLUE_START_POS = new Vector2(3,6);
    public static final int STANDARD_ANIMATION_TIME = 1000;
    public static final int DEATH_ANIMATION_TIME = STANDARD_ANIMATION_TIME * 10;
    public static final int VIEW_UPDATE_TIME = 100;
    public static final int PANE_SIZE = 92;
    public static final String NONE_PANE_STYLE = "-fx-background-color: gray;";
    public static final String RED_PANE_STYLE = "-fx-background-color: red;";
    public static final String BLUE_PANE_STYLE = "-fx-background-color: blue;";
    public static final String CHARACTER_IMAGE_ROOT_DIR = "/images/characters/";
    public static final String TILE_IMAGE_PATH = "/images/tiles/tile.png";
}
