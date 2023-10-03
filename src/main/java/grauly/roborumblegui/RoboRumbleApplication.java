package grauly.roborumblegui;

import grauly.roborumblegui.networking.ConnectionHandler;
import grauly.roborumblegui.visuals.RoboRumbleViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static grauly.roborumblegui.visuals.RoboRumbleViewController.resolution;

public class RoboRumbleApplication extends Application {
    public static String IP;
    public static int PORT;
    public static RoboRumbleGame GAME;
    private static RoboRumbleViewController viewController;

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            IP = args[0];
            PORT = Integer.parseInt(args[1]);
            //8000
            //10.10.42.186
            System.out.println("Connnection info set: " + IP + ":" + PORT);
        } else {
            System.out.println("Incorrect connection info provided, exiting.");
            Platform.exit();
            return;
        }
        try {
            ConnectionHandler connectionHandler = new ConnectionHandler();
            GAME = new RoboRumbleGame(connectionHandler);
            new Thread(GAME).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        launch();
        viewController.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RoboRumbleApplication.class.getResource("roborumble-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        viewController = fxmlLoader.getController();
        scene.setOnKeyTyped(keyEvent -> {
            if(keyEvent.getCharacter().equals("w")) {
                resolution.addAndGet(1);
            }
            if(keyEvent.getCharacter().equals("s")) {
                resolution.addAndGet(-1);
            }
        });
        stage.setTitle("Robo Rumble Viewer");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            try {
                GAME.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }
}