package grauly.roborumblegui.data;

import grauly.roborumblegui.networking.CommandHandler;

import java.util.ArrayList;

public class RoboData {
    private final ArrayList<CommandHandler.RoboAction> moveHistory = new ArrayList<>();
    private final RoboColor color;
    private RoboDirection direction;
    private final Vector2 position;

    public RoboData(RoboColor color, RoboDirection direction, Vector2 position) {
        this.color = color;
        this.direction = direction;
        this.position = position;
    }

    public void actOnAction(CommandHandler.RoboAction roboAction) {
        if (!roboAction.color().equals(color)) return;

        moveHistory.add(roboAction);
        switch (roboAction.operation()) {
            case MOVE -> position.add(Vector2.fromDirAndLength(direction, 1));
            case ROTATE_CLOCK -> direction = RoboDirection.getClockwise(direction);
            case ROTATE_COUNTERCLOCK -> direction = RoboDirection.getCounterClockwise(direction);
        }
    }

    public RoboColor getColor() {
        return color;
    }

    public RoboDirection getDirection() {
        return direction;
    }

    public Vector2 getPosition() {
        return position;
    }
}
