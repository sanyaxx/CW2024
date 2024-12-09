package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumMap;
import java.util.function.Consumer;

public class InputHandler {

    private final EnumMap<KeyCode, Consumer<GameEntity>> keyPressActions = new EnumMap<>(KeyCode.class);
    private final EnumMap<KeyCode, Consumer<GameEntity>> keyReleaseActions = new EnumMap<>(KeyCode.class);

    // Add key actions for press and release
    public void addKeyAction(KeyCode keyCode, Consumer<GameEntity> pressAction, Consumer<GameEntity> releaseAction) {
        if (pressAction != null) {
            keyPressActions.put(keyCode, pressAction);
        }
        if (releaseAction != null) {
            keyReleaseActions.put(keyCode, releaseAction);
        }
    }

    // Generate key press event handler
    public EventHandler<KeyEvent> getKeyPressHandler(GameEntity entity) {
        return event -> {
            Consumer<GameEntity> action = keyPressActions.get(event.getCode());
            if (action != null) {
                action.accept(entity);
            }
        };
    }

    // Generate key release event handler
    public EventHandler<KeyEvent> getKeyReleaseHandler(GameEntity entity) {
        return event -> {
            Consumer<GameEntity> action = keyReleaseActions.get(event.getCode());
            if (action != null) {
                action.accept(entity);
            }
        };
    }

    // Generalized setup for input handlers with press and release actions
    public void setupInputHandlers(GameEntity actor,
                                   Runnable moveUpPressAction,
                                   Runnable moveUpReleaseAction,
                                   Runnable moveDownPressAction,
                                   Runnable moveDownReleaseAction,
                                   Runnable moveRightPressAction,
                                   Runnable moveRightReleaseAction,
                                   Runnable moveLeftPressAction,
                                   Runnable moveLeftReleaseAction,
                                   Runnable spacePressAction) {
        addKeyAction(KeyCode.UP,
                moveUpPressAction != null ? entity -> moveUpPressAction.run() : null,
                moveUpReleaseAction != null ? entity -> moveUpReleaseAction.run() : null);
        addKeyAction(KeyCode.DOWN,
                moveDownPressAction != null ? entity -> moveDownPressAction.run() : null,
                moveDownReleaseAction != null ? entity -> moveDownReleaseAction.run() : null);
        addKeyAction(KeyCode.RIGHT,
                moveRightPressAction != null ? entity -> moveRightPressAction.run() : null,
                moveRightReleaseAction != null ? entity -> moveRightReleaseAction.run() : null);
        addKeyAction(KeyCode.LEFT,
                moveLeftPressAction != null ? entity -> moveLeftPressAction.run() : null,
                moveLeftReleaseAction != null ? entity -> moveLeftReleaseAction.run() : null);
        addKeyAction(KeyCode.SPACE,
                spacePressAction != null ? entity -> spacePressAction.run() : null,
                null); // No release action for SPACE in this case
    }
}
