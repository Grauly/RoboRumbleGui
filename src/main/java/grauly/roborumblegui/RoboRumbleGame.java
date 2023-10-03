package grauly.roborumblegui;

import grauly.roborumblegui.data.*;
import grauly.roborumblegui.networking.ConnectionHandler;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class RoboRumbleGame implements Runnable, Closeable {

    private final ConnectionHandler connectionHandler;
    private final SynchronousArrayListWrapper<RoboData> data = new SynchronousArrayListWrapper<>();
    private final Thread selfThread;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private long timerStartMoment;
    private int nextActionTime;


    public RoboRumbleGame(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        this.selfThread = new Thread(this);
        resetGame();
        selfThread.start();
        selfThread.setName("Game Thread");
        System.out.println("RoboRumble Game ready.");
    }

    public void resetGame() {
        data.clear();
        data.add(new RoboData(RoboColor.RED, RoboDirection.RIGHT, RoboRumbleConstants.RED_START_POS.clone()));
        data.add(new RoboData(RoboColor.BLUE, RoboDirection.LEFT, RoboRumbleConstants.BLUE_START_POS.clone()));
        System.out.println("Data reset.");
    }

    public RoboData[][] getRepresentation() {
        var field = new RoboData[7][7];
        data.forEach(d -> {
            var pos = d.getPosition();
            if(pos.isInBoundsRect(0,6)) {
                field[pos.getX()][pos.getY()] = d;
            }
        });
        return field;
    }

    @Override
    public void run() {
        while (running.get()) {
            if(timerStartMoment + nextActionTime > System.currentTimeMillis()) continue;
            if(!connectionHandler.commandHandler.hasWaitingCommand()) continue;
            var action = connectionHandler.commandHandler.retrieveNewCommand();
            if(action == null) continue;

            timerStartMoment = System.currentTimeMillis();
            nextActionTime = action.animationLengthMillis();
            System.out.println("Performing action: " + action.operation() + " with cooldown of: " + nextActionTime + ".");

            if (action.operation().equals(RoboOperation.DIE) || action.operation().equals(RoboOperation.KILL)) {
                resetGame();
                continue;
            }
            data.forEach(roboData -> roboData.actOnAction(action));
        }
        System.out.println("Game Thread ran out.");
    }

    @Override
    public void close() throws IOException {
        System.out.println("Game Thread close called from " + Arrays.toString(Thread.currentThread().getStackTrace()));
        running.set(false);
        connectionHandler.close();
        try {
            System.out.println("Attempting close of game Thread (" + selfThread.getName() +") with " + Thread.currentThread().getName() + ".");
            selfThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Game Thread closed.");
    }
}
