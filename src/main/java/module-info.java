module grauly.roborumblegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens grauly.roborumblegui to javafx.fxml;
    exports grauly.roborumblegui;
}