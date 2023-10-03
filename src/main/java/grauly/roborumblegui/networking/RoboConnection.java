package grauly.roborumblegui.networking;

import grauly.roborumblegui.data.RoboColor;
import grauly.roborumblegui.data.RoboRumbleConstants;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class RoboConnection implements Runnable, Closeable {

    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final Thread selfThread;
    private final RoboColor color;
    private final CommandHandler commandHandler;
    private boolean running = true;

    public RoboConnection(RoboColor color, String ip, int port, CommandHandler commandHandler) throws IOException{
        this.color = color;
        this.commandHandler = commandHandler;
        this.socket = new Socket(ip,port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.selfThread = new Thread(this);
        selfThread.setName(color.toString() + " - Connection");
        selfThread.start();
    }

    @Override
    public void run() {
        System.out.println("Starting " + color.toString() + " connection.");
        try {
            writer.write(RoboColor.getMessageString(color));
            writer.flush();

            while (socket.isConnected() && !socket.isClosed() && running) {
                int command = reader.read();
                System.out.println(color.toString() + " Received: " + ((char) command));
                commandHandler.registerIncomingCommand(new CommandHandler.MessageContainer(color, ((char) command) + ""));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Connection for " + color.toString() + " closed by exception.");
            System.err.println(color.toString() + " encountered: " + e.toString());
        }
        running = false;
        System.out.println("Connnection for " + color.toString() + " closed by running out of loop.");
    }

    public void sendConfirm() {
        try {
            writer.write(RoboRumbleConstants.ACK_STRING);
            writer.write(RoboRumbleConstants.TERMINATION_CHARACTER);
            writer.flush();
            System.out.println(color.toString() + " sent ack.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            selfThread.join();
        } catch (InterruptedException | IOException e) {
            selfThread.interrupt();
            System.err.println(e.toString());
        }
        System.out.println("Connnection for " + color.toString() + " closed by calling for it.");
    }

    public boolean isRunning() {
        return running;
    }
}
