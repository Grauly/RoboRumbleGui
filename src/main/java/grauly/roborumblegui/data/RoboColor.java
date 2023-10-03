package grauly.roborumblegui.data;

public enum RoboColor {
    RED,
    BLUE,
    NONE;

    public static String getMessageString(RoboColor color) {
        return switch (color) {
            case RED -> RoboRumbleConstants.RED_IDENTIFIER;
            case BLUE -> RoboRumbleConstants.BLUE_IDENTIFIER;
            case NONE -> "";
        };
    }
}
