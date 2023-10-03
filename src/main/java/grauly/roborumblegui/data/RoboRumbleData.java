package grauly.roborumblegui.data;

import grauly.roborumblegui.networking.CommandHandler;

public class RoboRumbleData {

    private boolean changed;
    public void processCommand(CommandHandler.MessageContainer container) {
        changed = true;
    }

    public boolean isChanged() {
        return changed;
    }
}
