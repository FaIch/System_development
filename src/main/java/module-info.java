module edu.ntnu.idatt1002.sysdev_k1_05_ets {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    opens edu.ntnu.idatt1002.sysdev_k1_05_ets to javafx.fxml;

    exports edu.ntnu.idatt1002.sysdev_k1_05_ets.tournament;
    opens edu.ntnu.idatt1002.sysdev_k1_05_ets.tournament to javafx.fxml;
    exports edu.ntnu.idatt1002.sysdev_k1_05_ets;
    exports edu.ntnu.idatt1002.sysdev_k1_05_ets.controllers;
    opens edu.ntnu.idatt1002.sysdev_k1_05_ets.controllers to javafx.fxml;
    exports edu.ntnu.idatt1002.sysdev_k1_05_ets.readersAndWriters;
    opens edu.ntnu.idatt1002.sysdev_k1_05_ets.readersAndWriters to javafx.fxml;
    exports edu.ntnu.idatt1002.sysdev_k1_05_ets.scenes;
    opens edu.ntnu.idatt1002.sysdev_k1_05_ets.scenes to javafx.fxml;
}
