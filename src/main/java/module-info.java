module pl.aleksandra.piatek {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.aleksandra.piatek.visualisation to javafx.fxml;
    exports pl.aleksandra.piatek.visualisation;
    exports pl.aleksandra.piatek.logistics;
    exports pl.aleksandra.piatek;
    opens pl.aleksandra.piatek to javafx.fxml;
}