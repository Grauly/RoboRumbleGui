package grauly.roborumblegui.networking;

import grauly.roborumblegui.data.RoboColor;
import grauly.roborumblegui.data.RoboOperation;
import grauly.roborumblegui.data.RoboRumbleConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class CommandHandler {

    private final ConnectionHandler connectionHandler;
    private final ArrayBlockingQueue<RoboAction> actionQueue = new ArrayBlockingQueue<>(1000);

    public CommandHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public synchronized void registerIncomingCommand(MessageContainer message) {
        actionQueue.addAll(convertMessageToAction(message));
    }

    public synchronized boolean hasWaitingCommand() {
        return !actionQueue.isEmpty();
    }

    public synchronized RoboAction retrieveNewCommand() {
        var action = actionQueue.poll();
        if(action != null) {
            if(action.needsAck) {
                connectionHandler.sendCommandAck(action.color);
            }
        }
        return action;
    }

    public synchronized void reset() {
        actionQueue.clear();
    }

    private List<RoboAction> convertMessageToAction(MessageContainer message) {
        switch (message.message()) {
            case RoboRumbleConstants.ROTATE_LEFT_STRING -> {
                return List.of(new RoboAction(message.color, RoboOperation.ROTATE_COUNTERCLOCK, RoboRumbleConstants.STANDARD_ANIMATION_TIME, true));
            }
            case RoboRumbleConstants.ROTATE_RIGHT_STRING -> {
                return List.of(new RoboAction(message.color, RoboOperation.ROTATE_CLOCK, RoboRumbleConstants.STANDARD_ANIMATION_TIME, true));
            }
            case RoboRumbleConstants.DIE_STRING -> {
                return List.of(new RoboAction(message.color, RoboOperation.DIE, RoboRumbleConstants.DEATH_ANIMATION_TIME, true));
            }
            case RoboRumbleConstants.KILL_STRING -> {
                return List.of(new RoboAction(message.color, RoboOperation.KILL, RoboRumbleConstants.DEATH_ANIMATION_TIME, true));
            }
            default -> {
                var moves = Integer.parseInt(message.message());
                ArrayList<RoboAction> actions = new ArrayList<>();
                for (int i = 0; i < moves -1; i++) {
                    actions.add(new RoboAction(message.color, RoboOperation.MOVE, RoboRumbleConstants.STANDARD_ANIMATION_TIME / moves, false));
                }
                actions.add(new RoboAction(message.color, RoboOperation.MOVE, RoboRumbleConstants.STANDARD_ANIMATION_TIME / moves, true));
                return actions;
            }
        }
    }

    public record MessageContainer(RoboColor color, String message) {};
    public record RoboAction(RoboColor color, RoboOperation operation, int animationLengthMillis, boolean needsAck) {};

    /*
    Connect to Server
    Message Terminate: "\n"
    send either "red" "blu"

    listen.
    "1" move 1
    "2" move 2
    "3" move 3
    "r" rotate clock
    "l" rotate counterclock

    after above send "ok"

    either of these end game -> reset state
    "k" kill (other player) -> is winner
    "s" sudoku (kill self) -> is loser
     */

}
