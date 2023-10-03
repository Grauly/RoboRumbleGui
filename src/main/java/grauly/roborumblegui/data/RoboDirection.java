package grauly.roborumblegui.data;

public enum RoboDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static RoboDirection getClockwise(RoboDirection direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            default -> UP;
        };
    }

    public static RoboDirection getCounterClockwise(RoboDirection direction) {
        return switch (direction) {
            case UP -> LEFT;
            case RIGHT -> UP;
            case DOWN -> RIGHT;
            default -> DOWN;
        };
    }

    public static Vector2 getVector(RoboDirection direction) {
        return switch (direction) {
            case UP -> new Vector2(-1,0);
            case LEFT -> new Vector2(0,-1);
            case RIGHT -> new Vector2(0,1);
            default -> new Vector2(1,0);
        };
    }
}
