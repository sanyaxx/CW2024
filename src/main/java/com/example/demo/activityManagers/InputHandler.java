package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumMap;
import java.util.function.Consumer;

/**
 * Class responsible for handling key input actions, separating key press and key release functionality.
 * This refactor replaces the direct key event handling from LevelParent to provide more modular and maintainable input handling.
 */
public class InputHandler {

    /** Map to store key press actions associated with each KeyCode */
    private final EnumMap<KeyCode, Consumer<GameEntity>> keyPressActions = new EnumMap<>(KeyCode.class);

    /** Map to store key release actions associated with each KeyCode */
    private final EnumMap<KeyCode, Consumer<GameEntity>> keyReleaseActions = new EnumMap<>(KeyCode.class);

    /**
     * Default constructor for InputHandler.
     * Initializes the key action maps for key press and release actions.
     * This constructor is used to create an instance of InputHandler to handle input events.
     */
    public InputHandler() {
        // Initialize key action maps with KeyCode enumeration.
        keyPressActions.clear();
        keyReleaseActions.clear();
    }

    /**
     * Adds key press and key release actions for a specific KeyCode.
     * The actions are associated with key events and will be triggered when the keys are pressed or released.
     *
     * @param keyCode The key for which actions will be defined.
     * @param pressAction Action to be performed when the key is pressed.
     * @param releaseAction Action to be performed when the key is released.
     */
    public void addKeyAction(KeyCode keyCode, Consumer<GameEntity> pressAction, Consumer<GameEntity> releaseAction) {
        if (pressAction != null) {
            keyPressActions.put(keyCode, pressAction);  // Add the press action to the map
        }
        if (releaseAction != null) {
            keyReleaseActions.put(keyCode, releaseAction);  // Add the release action to the map
        }
    }

    /**
     * Generates the key press event handler, which triggers the associated action when a key is pressed.
     * The action is applied to the provided GameEntity.
     *
     * @param entity The GameEntity to apply actions to.
     * @return EventHandler that responds to key press events.
     */
    public EventHandler<KeyEvent> getKeyPressHandler(GameEntity entity) {
        return event -> {
            Consumer<GameEntity> action = keyPressActions.get(event.getCode());  // Get the action for the pressed key
            if (action != null) {
                action.accept(entity);  // Trigger the action if it exists
            }
        };
    }

    /**
     * Generates the key release event handler, which triggers the associated action when a key is released.
     * The action is applied to the provided GameEntity.
     *
     * @param entity The GameEntity to apply actions to.
     * @return EventHandler that responds to key release events.
     */
    public EventHandler<KeyEvent> getKeyReleaseHandler(GameEntity entity) {
        return event -> {
            Consumer<GameEntity> action = keyReleaseActions.get(event.getCode());  // Get the action for the released key
            if (action != null) {
                action.accept(entity);  // Trigger the action if it exists
            }
        };
    }

    /**
     * Generalized setup for input handlers, associating specific key actions to their respective keys.
     * <p>
     * This method replaces the individual key event handling logic found in the LevelParent class.
     * It allows handling movement and other actions (such as firing) in a modular way, with both press and release actions.
     *
     * @param actor The GameEntity that will respond to key inputs.
     * @param moveUpPressAction Action for pressing the UP key.
     * @param moveUpReleaseAction Action for releasing the UP key.
     * @param moveDownPressAction Action for pressing the DOWN key.
     * @param moveDownReleaseAction Action for releasing the DOWN key.
     * @param moveRightPressAction Action for pressing the RIGHT key.
     * @param moveRightReleaseAction Action for releasing the RIGHT key.
     * @param moveLeftPressAction Action for pressing the LEFT key.
     * @param moveLeftReleaseAction Action for releasing the LEFT key.
     * @param spacePressAction Action for pressing the SPACE key (firing a projectile).
     */
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
        // Setup the actions for each key, handling both press and release events
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
                null); // No release action for SPACE key in this context
    }
}
