package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.enemyPlanes.EnemyPlane;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import com.example.demo.actors.additionalUnits.Coins;
import com.example.demo.levels.LevelParent;
import javafx.geometry.Bounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.mock;

class CollisionHandlerTest {

    private CollisionHandler collisionHandler;
    private LevelParent mockLevelParent;
    private UserPlane mockUserPlane;
    private Coins mockCoin;
    private ActorManager actorManager;

    @BeforeEach
    void setUp() {
        mockLevelParent = mock(LevelParent.class);
        collisionHandler = new CollisionHandler(mockLevelParent);
        actorManager = ActorManager.getInstance(); // Mock ActorManager
        mockUserPlane = mock(UserPlane.class); // Simulate the user's plane
        mockCoin = mock(Coins.class); // Simulate a collectible coin

        actorManager = ActorManager.getInstance();
        // Mock the actorManager within mockLevelParent
        when(actorManager.getUserVehicle()).thenReturn(mockUserPlane);

        // Mock the behavior of the user vehicle in LevelParent
        when(mockUserPlane.getBoundsInParent()).thenReturn(mock(Bounds.class));
        when(mockCoin.getBoundsInParent()).thenReturn(mock(Bounds.class));
    }


    @Test
    void testCheckCollisionsWithCollectible() {
        // Arrange
        Bounds mockUserBounds = mock(Bounds.class);
        Bounds mockCoinBounds = mock(Bounds.class);

        // Mock intersection logic
        when(mockUserBounds.intersects(mockCoinBounds)).thenReturn(true);
        when(mockUserPlane.getBoundsInParent()).thenReturn(mockUserBounds);
        when(mockCoin.getBoundsInParent()).thenReturn(mockCoinBounds);

        when(mockUserPlane.isCollectible()).thenReturn(false);
        when(mockCoin.isCollectible()).thenReturn(true);

        List<GameEntity> activeActors = new ArrayList<>();
        activeActors.add(mockUserPlane);
        activeActors.add(mockCoin);

        // Act
        collisionHandler.checkCollisions(activeActors);

        // Assert
        verify(mockCoin, times(1)).takeDamage(); // Ensure the collectible is consumed
    }

    @Test
    void testHandleNonCollectibleCollision() {
        // Arrange
        EnemyPlane mockEnemyPlane = mock(EnemyPlane.class);

        Bounds mockUserBounds = mock(Bounds.class);
        Bounds mockEnemyBounds = mock(Bounds.class);

        // Mock intersection logic
        when(mockUserBounds.intersects(mockEnemyBounds)).thenReturn(true);
        when(mockUserPlane.getBoundsInParent()).thenReturn(mockUserBounds);
        when(mockEnemyPlane.getBoundsInParent()).thenReturn(mockEnemyBounds);

        when(mockUserPlane.isFriendly()).thenReturn(true);
        when(mockEnemyPlane.isFriendly()).thenReturn(false);

        List<GameEntity> activeActors = new ArrayList<>();
        activeActors.add(mockUserPlane);
        activeActors.add(mockEnemyPlane);

        // Act
        collisionHandler.checkCollisions(activeActors);

        // Assert
        verify(mockUserPlane, times(1)).takeDamage(); // Verify the user's plane took damage
        verify(mockEnemyPlane, times(1)).takeDamage(); // Verify the enemy plane took damage
    }
}
