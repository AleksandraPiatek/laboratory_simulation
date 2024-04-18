module pl.edu.pwr.apiatek {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.apiatek.visualisation to javafx.fxml;
    exports pl.edu.pwr.apiatek.visualisation;
    exports pl.edu.pwr.apiatek.logistics;
    exports pl.edu.pwr.apiatek;
    opens pl.edu.pwr.apiatek to javafx.fxml;
}