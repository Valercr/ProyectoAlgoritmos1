module ac.cr.ucr.proyecto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jdom2;
    requires java.mail;
    requires java.logging;
    requires java.xml;
    requires javax.inject;
    requires itextpdf;


    opens ucr.proyecto1 to javafx.fxml;
    exports ucr.proyecto1;
    exports controller;
    opens controller to javafx.fxml;
    opens ucr.proyecto1.domain.data to javafx.base;
}