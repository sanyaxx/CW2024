package com.example.demo.controller;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppStageTest {

    private AppStage appStage;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSingletonInstance() {
        // Get the first instance of AppStage
        AppStage firstInstance = AppStage.getInstance();

        // Get the second instance of AppStage
        AppStage secondInstance = AppStage.getInstance();

        // Assert that both instances are the same (Singleton pattern)
        assertSame(firstInstance, secondInstance, "AppStage should return the same instance.");
    }


    @Test
    void testPrimaryStageNotSetInitially() {
        // Ensure that the primary stage is not set initially
        assertNull(AppStage.getInstance().getPrimaryStage(), "The primary stage should be null before it is set.");
    }
}
