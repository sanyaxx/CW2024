package com.example.demo.controller;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class AppStageTest {

    @Test
    void testSingletonInstance() {
        AppStage instance1 = AppStage.getInstance();
        AppStage instance2 = AppStage.getInstance();
        assertSame(instance1, instance2, "AppStage should return the same instance for the singleton.");
    }

    @Test
    void testSetAndGetPrimaryStage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> { // Ensures JavaFX is initialized
            try {
                AppStage appStage = AppStage.getInstance();
                Stage mockStage = new Stage();
                appStage.setPrimaryStage(mockStage);

                assertSame(mockStage, appStage.getPrimaryStage(), "The retrieved primary stage should match the set stage.");
            } finally {
                latch.countDown();
            }
        });

        // Wait for the JavaFX Application Thread to finish
        latch.await();
    }
}
