package com.example.demo.activityManagers;

import com.example.demo.actors.GameEntity;
import com.example.demo.actors.Planes.friendlyPlanes.UserPlane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpawnHandlerTest {

    private SpawnHandler spawnHandler;
    private ActorManager actorManagerMock;
    private Supplier<GameEntity> actorSupplierMock;
    private List<GameEntity> activeActors;

    @BeforeEach
    void setUp() {
        // Mock the ActorManager and initialize the list of active actors
        actorManagerMock = mock(ActorManager.class);
        activeActors = new ArrayList<>();

        // Setup ActorManager to return the active actors list
        when(actorManagerMock.getActiveActors()).thenReturn(activeActors);

        // Initialize the SpawnHandler with screen dimensions (mocked, no need for real values here)
        spawnHandler = new SpawnHandler(actorManagerMock, 800, 600);

        // Mock the supplier for creating new GameEntity instances
        actorSupplierMock = mock(Supplier.class);
    }

    @Test
    void testSpawnActors_success() {
        // Create a mock UserPlane (which is a subclass of GameEntity) and mock the supplier to return it
        UserPlane mockActor = mock(UserPlane.class);
        when(actorSupplierMock.get()).thenReturn(mockActor);

        // Test that spawnActors spawns a valid actor
        int maxSpawnCount = 5;
        double spawnProbability = 1.0; // Always spawn
        int currentCount = 0;
        int maxTotal = 10;

        // Attempt to spawn actors
        int spawnedCount = spawnHandler.spawnActors(actorSupplierMock, maxSpawnCount, spawnProbability, currentCount, maxTotal);

        // Verify that one actor was spawned
        assertEquals(5, spawnedCount, "Five actors should be spawned.");

        // Verify that the actor was added to the actor manager
        verify(actorManagerMock, times(5)).addActor(mockActor);
    }

    @Test
    void testSpawnActors_maxSpawnCountLimit() {
        // Create mock UserPlane and set it to be spawned
        UserPlane mockActor = mock(UserPlane.class);
        when(actorSupplierMock.get()).thenReturn(mockActor);

        // Set spawn probability to 1.0 (always spawn)
        double spawnProbability = 1.0;
        int maxSpawnCount = 5;
        int currentCount = 4; // Only 1 actor should spawn because 4 are already spawned
        int maxTotal = 10;

        // Attempt to spawn actors
        int spawnedCount = spawnHandler.spawnActors(actorSupplierMock, maxSpawnCount, spawnProbability, currentCount, maxTotal);

        // Ensure that only 1 actor is spawned due to the max spawn count
        assertEquals(1, spawnedCount, "Only one actor should be spawned due to the max spawn count.");

        // Verify that addActor was called once
        verify(actorManagerMock, times(1)).addActor(mockActor);
    }

    @Test
    void testSpawnActors_probability() {
        // Create mock actor and mock its spawning behavior
        UserPlane mockActor = mock(UserPlane.class);
        when(actorSupplierMock.get()).thenReturn(mockActor);

        // Set spawn probability to a high value (e.g., 1)
        double spawnProbability = 1;
        int maxSpawnCount = 10; // Maximum spawn count per cycle
        int currentCount = 0; // No actors are already spawned
        int maxTotal = 10; // Maximum total actors

        // Call spawnActors once, attempting to spawn multiple actors at once
        int spawnedCount = spawnHandler.spawnActors(actorSupplierMock, maxSpawnCount, spawnProbability, currentCount, maxTotal);

        // The expected number of spawns should be 10, due to the maximum spawn count
        assertEquals(10, spawnedCount, "All attempts should result in a spawn due to full probability, up to maxSpawnCount.");

        // Verify that the actor was added the expected number of times
        verify(actorManagerMock, times(10)).addActor(mockActor);
    }
}
