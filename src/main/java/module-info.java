/**
 * This is the module descriptor for the "com.example.demo" module.
 * It defines the module's dependencies and the packages that are exposed to other modules.
 */

module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.levels.Level1 to javafx.fxml;
    opens com.example.demo.activityManagers to javafx.fxml;
    opens com.example.demo.levels.Level2 to javafx.fxml;
    opens com.example.demo.levels.Level3 to javafx.fxml;
    opens com.example.demo.levels.Level4 to javafx.fxml;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.actors.Planes to javafx.fxml;
    opens com.example.demo.actors.Planes.enemyPlanes to javafx.fxml;
    opens com.example.demo.actors.Planes.friendlyPlanes to javafx.fxml;
    opens com.example.demo.actors.Projectiles to javafx.fxml;
    opens com.example.demo.actors.Projectiles.enemyProjectiles to javafx.fxml;
    opens com.example.demo.actors.Projectiles.userProjectiles to javafx.fxml;
    opens com.example.demo.actors.additionalUnits to javafx.fxml;
    opens com.example.demo.notUsed to javafx.fxml;
    opens com.example.demo.displays to javafx.fxml;
//    opens com.example.demo.functionalClasses to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo;
}