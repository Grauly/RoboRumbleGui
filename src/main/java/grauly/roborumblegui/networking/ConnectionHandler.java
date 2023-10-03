package grauly.roborumblegui.networking;

import grauly.roborumblegui.data.RoboColor;

import java.io.Closeable;
import java.io.IOException;

import static grauly.roborumblegui.RoboRumbleApplication.IP;
import static grauly.roborumblegui.RoboRumbleApplication.PORT;

public class ConnectionHandler implements Closeable {

    private RoboConnection red;
    private RoboConnection blue;
    public CommandHandler commandHandler;

    public ConnectionHandler() throws IOException {
        System.out.println("Starting connection handler.");
        commandHandler = new CommandHandler(this);
        red = new RoboConnection(RoboColor.RED, IP, PORT, commandHandler);
        blue = new RoboConnection(RoboColor.BLUE, IP, PORT, commandHandler);
    }

    public void sendCommandAck(RoboColor color) {
        if(color.equals(RoboColor.RED)) {
            red.sendConfirm();
        } else {
            blue.sendConfirm();
        }
    }

    public void reset() throws IOException {
        commandHandler.reset();
    }

    @Override
    public void close() throws IOException {
        red.close();
        blue.close();
    }
}
