package grauly.roborumblegui.visuals;

import grauly.roborumblegui.RoboRumbleApplication;
import grauly.roborumblegui.data.RoboColor;
import grauly.roborumblegui.data.RoboData;
import grauly.roborumblegui.data.RoboDirection;
import grauly.roborumblegui.data.RoboRumbleConstants;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.TimerTask;

public class GameUpdateTimerTask extends TimerTask {

    RoboRumbleViewController view;
    ImageHandler imageHandler;

    public GameUpdateTimerTask(RoboRumbleViewController roboRumbleViewController) {
        view = roboRumbleViewController;
        imageHandler = new ImageHandler();
    }

    /**
     * Wrapper to run a timer on the JavaFX Thread
     */
    @Override
    public void run() {
        Platform.runLater(new UpdateViewRunnable(view,imageHandler));
    }


    /**
     * Actual View Updater
     */
    static class UpdateViewRunnable implements Runnable {

        RoboRumbleViewController view;
        ImageHandler imageHandler;
        public UpdateViewRunnable(RoboRumbleViewController view, ImageHandler imageHandler) {
            this.view = view;
            this.imageHandler = imageHandler;
        }

        @Override
        public void run() {
            if(view.mainGrid == null) return;

            var grid = view.mainGrid;
            var field = RoboRumbleApplication.GAME.getRepresentation();
            grid.getChildren().clear();
            int x = 0;
            int y = 0;
            for (RoboData[] dataArray : field) {
                for (RoboData data: dataArray) {
                    grid.add(getNode(data),x,y);
                    x++;
                }
                x = 0;
                y++;
            }
        }

        private Node getNode(RoboData data) {
            var targetRes = RoboRumbleViewController.resolution.get();
            Pane pane = new Pane();
            pane.setMinHeight(targetRes);
            pane.setMinWidth(targetRes);

            var backgroundImage = new ImageView(imageHandler.getImage(RoboColor.NONE, RoboDirection.DOWN));
            //backgroundImage.prefWidth(Double.POSITIVE_INFINITY);
            backgroundImage.setFitWidth(targetRes);
            backgroundImage.setPreserveRatio(true);
            pane.getChildren().add(backgroundImage);

            if(data == null || data.getColor() == RoboColor.NONE) {
                pane.setStyle(RoboRumbleConstants.NONE_PANE_STYLE);
                return pane;
            }
            if(data.getColor() == RoboColor.RED) {
                pane.setStyle(RoboRumbleConstants.RED_PANE_STYLE);
            }
            if(data.getColor() == RoboColor.BLUE) {
                pane.setStyle(RoboRumbleConstants.BLUE_PANE_STYLE);
            }
            var botImage = new ImageView(imageHandler.getImage(data.getColor(), data.getDirection()));
            //botImage.prefWidth(Double.POSITIVE_INFINITY);
            botImage.setFitWidth(targetRes);
            botImage.setPreserveRatio(true);
            pane.getChildren().add(botImage);
            return pane;
        }
    }
}
