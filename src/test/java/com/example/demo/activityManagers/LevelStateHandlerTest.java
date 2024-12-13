package com.example.demo.activityManagers;

import com.example.demo.actors.Planes.friendlyPlanes.UserParent;
import com.example.demo.controller.AppStage;
import com.example.demo.controller.GameLoop;
import com.example.demo.displays.StartScreen;
import com.example.demo.displays.YouWinScreen;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelStateHandlerTest {

    private LevelStateHandler levelStateHandler;
    private LevelManager levelManagerMock;
    private OverlayHandler overlayHandlerMock;
    private Timeline timelineMock;
    private UserParent userMock;
    private Group groupMock;

    @BeforeEach
    void setUp() {
        // Mocking dependencies
        levelManagerMock = mock(LevelManager.class);
        overlayHandlerMock = mock(OverlayHandler.class);
        timelineMock = mock(Timeline.class);
        userMock = mock(UserParent.class);
        groupMock = mock(Group.class);

        // Mock GameLoop
        GameLoop gameLoopMock = mock(GameLoop.class);
        when(gameLoopMock.getTimeline()).thenReturn(timelineMock);

        // Mock Stage and AppStage
        Stage stage = mock(Stage.class);
        AppStage appStageMock = mock(AppStage.class);
        when(appStageMock.getPrimaryStage()).thenReturn(stage);
        when(appStageMock.getPrimaryStage().getWidth()).thenReturn(800.0);
        when(appStageMock.getPrimaryStage().getHeight()).thenReturn(600.0);

        // Set up singleton instances
        levelManagerMock = LevelManager.getInstance();
        overlayHandlerMock = OverlayHandler.getInstance();
        levelStateHandler = LevelStateHandler.getInstance();
    }

    @Test
    void testSingletonInstance() {
        LevelStateHandler instance1 = LevelStateHandler.getInstance();
        LevelStateHandler instance2 = LevelStateHandler.getInstance();
        assertSame(instance1, instance2, "LevelStateHandler should return the same instance.");
    }
}
