package grauly.roborumblegui.visuals;

import grauly.roborumblegui.data.RoboRumbleConstants;
import grauly.roborumblegui.visuals.GameUpdateTimerTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

public class RoboRumbleViewController implements Closeable, Initializable {

    private Timer gameUpdateTimer;
    public static final AtomicInteger resolution = new AtomicInteger(RoboRumbleConstants.PANE_SIZE);
    @FXML
    GridPane mainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameUpdateTimer = new Timer();
        gameUpdateTimer.schedule(new GameUpdateTimerTask(this),0, RoboRumbleConstants.VIEW_UPDATE_TIME);
        System.out.println("Game Update Timer started.");
    }



    @Override
    public void close() throws IOException {
        gameUpdateTimer.cancel();
    }
}