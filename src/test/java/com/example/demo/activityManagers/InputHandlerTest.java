package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputHandlerTest {

    private InputHandler inputHandler;
    private GameEntity mockEntity;

    @BeforeEach
    void setUp() {
        inputHandler = new InputHandler();
        mockEntity = mock(UserPlane.class); // Create a mock GameEntity
    }

    @Test
    void testAddKeyAction() {
        AtomicBoolean actionTriggered = new AtomicBoolean(false);

        // Add a key press action for KeyCode.LEFT
        inputHandler.addKeyAction(KeyCode.LEFT, entity -> actionTriggered.set(true), null);

        // Simulate key press event for LEFT key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        inputHandler.getKeyPressHandler(mockEntity).handle(keyEvent);

        assertTrue(actionTriggered.get(), "KeyCode.LEFT action should trigger on key press.");
    }

    @Test
    void testSetupInputHandlersForSpace() {
        AtomicBoolean spacePressed = new AtomicBoolean(false);

        // Setup input handler for SPACE key press
        inputHandler.setupInputHandlers(
                mockEntity,
                null, null, null, null, null, null, null, null, // No actions for other keys
                () -> spacePressed.set(true) // SPACE key press action
        );

        // Simulate SPACE key press event
        KeyEvent spaceEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);
        inputHandler.getKeyPressHandler(mockEntity).handle(spaceEvent);

        assertTrue(spacePressed.get(), "SPACE key press action should have been triggered.");
    }
}
