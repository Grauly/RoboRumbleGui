module grauly.roborumblegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens grauly.roborumblegui to javafx.fxml;
    exports grauly.roborumblegui;
    exports grauly.roborumblegui.data;
    exports grauly.roborumblegui.networking;
    exports grauly.roborumblegui.visuals;
    opens grauly.roborumblegui.visuals to javafx.fxml;
}