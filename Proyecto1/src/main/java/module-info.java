module ac.cr.ucr.proyecto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jdom2;


    opens ucr.proyecto1 to javafx.fxml;
    exports ucr.proyecto1;
    exports controller;
    opens controller to javafx.fxml;
}