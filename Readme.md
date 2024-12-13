# **Game Features and Implementation Report**

**GitHub Link:** https://github.com/sanyaxx/CW2024

---
## Compilation Instructions
**Compilation Instructions:**

1. **Install IntelliJ IDEA Community** if not already installed.
2. **Open Project** in IntelliJ via `File` > `Open...`.
3. **Load Maven Dependencies**: IntelliJ auto-loads dependencies. If not, click the `Maven` tool window and refresh.
4. **Set Correct JDK**: Ensure the project uses a compatible JDK Version 23.
5. **Build Project**: Select `Build` > `Build Project`.
6. **Run Application**: Click the green `Run` button or press `Shift + F10`.
7. **Play the Game**: The game window will open. Enjoy!
**Dependencies**: Maven for dependencies, JavaFX for UI.

## **Implemented & Fully Functional Features**

### **Start Screen**
- **Purpose**:  
  The Start Screen serves as the entry point to the game, providing players with an intuitive and engaging interface to begin their journey. It ensures that users are introduced to the game in a visually appealing and accessible manner.

- **Feature**:  
  The Start Screen includes a background image, a title display, and two key buttons: *Start Game* and *Quit Game*. These buttons allow users to navigate into the game or exit the application.

- **How it Benefits the Game**:
   - Establishes a polished first impression with a visually cohesive design.
   - Enhances usability by offering clear and easy-to-understand navigation options.
   - Improves the player experience with a smooth transition into the gameplay, making the game feel professional and immersive.
   - Gives the player a moment to familiarize themselves with the game environment before starting.

---

### **Level Start Screen**
- **Purpose**:  
  The Level Start Screen provides a brief intermission between gameplay, introducing players to the specific objectives and mechanics of the upcoming level. This screen helps set expectations and prepare players for challenges.

- **Feature**:  
  The Level Start Screen displays:
   - **Level Title**: Indicates the current level.
   - **Level Aim**: Explains the objectives of the level.
   - **Instructions**: Lists the controls and special gameplay rules for the level.
   - **Start Prompt**: Players can press *SPACE* to begin the level.

- **How it Benefits the Game**:
   - Enhances player clarity by outlining level-specific goals and controls, reducing frustration during gameplay.
   - Creates a pacing mechanism, giving players time to mentally prepare for the level.
   - Improves immersion through visually distinct and informative transitions between levels.
   - Increases player engagement by providing clear and motivating goals.

---

### **Pause Functionality**
- **Purpose**:  
  The pause functionality allows players to temporarily halt the game, providing a break or a chance to adjust their strategy without losing progress. It ensures control and flexibility in gameplay.

- **Features**:
   - **Pause Button**: A clickable button, styled with a pause icon, located at the top-right corner of the screen.
   - **Pause Overlay**: When the game is paused, a menu overlay appears with the following options:
      - Resume: Continue the game.
      - Main Menu: Exit to the start screen.
      - Volume: Toggle sound settings.
      - Restart: Restart the current level.
   - **Timeline Management**: The game’s animation and physics timeline is paused during the pause state.

- **How it Benefits the Game**:
   - **Enhanced Player Experience**: Provides a sense of control, allowing players to take breaks or refocus without stress.
   - **Accessibility**: Helps players manage their attention and time, making the game more user-friendly.
   - **Flexibility**: Lets players choose different options (e.g., restart or toggle volume) mid-game.

---

### **Level Scoring System**
- **Purpose**:  
  The level scoring system evaluates and rewards the player's performance after completing a level, providing feedback and encouraging skillful gameplay.

- **Functionality**:
   - **Score Calculation**:  
     Scores are determined based on:
      - **Lives Remaining**: Rewards the player for surviving with extra lives.
      - **Bullets Remaining**: Encourages efficiency in using limited resources.
   - **Star Representation**:
      - Converts numerical scores into a star-based rating system:
         - **3 Stars**: Exceptional performance.
         - **2 Stars**: Moderate performance.
         - **1 Star**: Basic performance.
         - **0 Stars**: Minimal success or failure to meet key objectives.
   - **Feedback Visuals**:  
     Displays the appropriate star image at the end of each level to represent the score.

- **Key Implementation**:
   - The `calculateScore` method evaluates lives and bullets remaining to determine the score.
   - The `getStarImagePath` method maps the score to an appropriate image of stars.
   - A modular design ensures the system can easily be adjusted or expanded for future gameplay metrics.

- **Benefits**:
   - **Player Motivation**: Encourages strategic thinking and resource management by rewarding efficient performance.
   - **Clear Feedback**: The star-based representation provides an intuitive and visually engaging way for players to understand their level performance.
   - **Replay Value**: Encourages players to replay levels for better scores, improving engagement and game longevity.

This feature enhances the game by combining performance evaluation with rewarding feedback, driving both satisfaction and continuous improvement for players.

---

### **Dynamic Winning Parameters System**
- **Purpose**:  
  Defines and dynamically tracks the conditions required to win each level while providing real-time feedback to the player.

- **Functionality**:
   - **Parameter-Based Evaluation**:  
     Tracks various gameplay metrics tailored to each level. Examples include:
      - **Kill Count**: Number of enemies defeated.
      - **Bullet Count**: Remaining bullets, decreasing with each shot.
      - **Coin Count**: Total coins collected across all levels.
      - **Boss Health**: Health of a level-specific boss, decrementing with successful attacks.
      - **Survival Timer**: Time the player must survive in certain levels.
      - **Fuel Capacity Timer**: Duration the current quantity of fuel will last (in seconds).
   - **Dynamic Updates**:  
     Winning parameters are updated in real-time, ensuring players can monitor their progress seamlessly.
   - **Visual Feedback**:  
     Displays parameter values with clear visual elements like icons and text.

   - **Level-Specific Winning Conditions**:
      - **Level 1**: Kill count, bullet count, coin count.
      - **Level 2**: Boss plane health count, bullet count, coin count.
      - **Level 3**: Survival timer, bullet count.
      - **Level 4**: Fuel capacity timer, bullet count, coin count.

- **Benefits**:
   - **Player Clarity**: Real-time feedback keeps players informed about their progress toward the win condition.
   - **Level Customization**: Tailoring winning parameters to each level enhances gameplay variety and challenge.
   - **Engagement**: Dynamic tracking and visual representation of parameters provide an immersive and rewarding experience.
---

### **User Statistics Tracking System**
#### **Purpose**
The User Statistics Tracking System monitors and records various metrics of the player's progress and performance throughout the game. It consolidates all player-related information into a centralized system to ensure consistent tracking and easy access.

#### **Tracked Metrics**
1. **Kill Count**: Total number of enemies defeated across all levels.
2. **Total Coins Collected**: Aggregates the coins collected throughout the game.
3. **Bullet Count**: Tracks the number of bullets remaining for the player.
4. **Individual Level Scores**: Stores scores achieved in each level, enabling comparisons and progress tracking.
5. **Level-Won Conditions**: Tracks game conditions to determine if levels are completed successfully.

#### **Features and Functionality**
1. **Singleton Design Pattern**:
   - Ensures only one instance of the tracking system exists across the game lifecycle.
   - Provides global access to the statistics manager.

2. **Dynamic Metric Updates**:
   - Automatically increments and decrements tracked values such as coins, kills, and bullets based on in-game events.

3. **Level-Specific Score Storage**:
   - Stores and retrieves individual level scores dynamically.
   - Updates scores based on performance metrics (e.g., kill count, remaining bullets).

4. **Redemption Mechanism**:
   - Allows players to redeem coins (e.g., lose coins to revive).

#### **Benefits of This Implementation**
1. **Centralized Player Metrics**: A single location to manage all player-related statistics simplifies game logic and debugging.
2. **Dynamic and Scalable**: Allows easy addition of new metrics or levels without major architectural changes.
3. **Real-Time Updates**: Provides immediate feedback to the user through updated statistics in the game's user interface.
4. **Performance-Based Gameplay**: Tracks metrics critical to determining win conditions, adding depth to gameplay.
5. **Persistence**: Enables score storage for comparisons, achievements, or future integrations like leaderboards.

This system ensures a seamless and engaging experience for players by maintaining an accurate and dynamic record of their progress.

---

### **In-Between Level Transition Overlays**
#### **Level Completed Overlay**
**Purpose**:  
The **Level Completed Overlay** provides visual feedback and options to the player upon successfully completing a level. It serves as a congratulatory screen while offering navigation options for further gameplay.
**Key Features**:
1. **Next Level**: Advances the player to the next level to continue their journey.
2. **Retry Level**: Allows the player to replay the completed level to improve performance or score.
3. **Return to Start Page**: Redirects the player to the main menu for a break or to adjust settings.

#### **Level Lost Overlay**
**Purpose**:  
The **Level Lost Overlay** notifies the player of their defeat and provides actionable choices to maintain engagement or exit the game gracefully.
**Key Features**:
1. **Retry Level**: Restarts the current level, giving players another chance to succeed.
2. **Return to Start Page**: Offers a way back to the main menu for regrouping or revisiting earlier options.

#### **Benefits of This Implementation**:
1. **Clear Navigation**: Streamlines the player's choices after each level, maintaining the game's flow.
2. **Player Engagement**: Offers options to retry or advance without unnecessary delays.
3. **Flexible Gameplay**: Caters to both casual and competitive players by providing multiple post-level actions.
4. **Immersive Experience**: Reinforces a sense of progression or challenge with appropriate overlays.

---

### **Life Redemption and Level Revival Feature**
**Purpose**:  
This feature provides players with an opportunity to revive and continue the level after losing, adding an engaging second-chance mechanic to the game. It ensures that invested players have the chance to redeem their game progress by utilizing in-game currency (coins), encouraging resource management and providing a sense of control.

#### **Life Redemption Functionality**
**How It Works**:
1. **Triggered on Level Loss**:  
   When a player loses the game, the **Redeem Life Overlay** is displayed for a brief 5-second window.

2. **Options for Redemption**:
   - Players with at least **2 coins** can revive their game and restore one of the key gameplay parameters:
      - **Health**: If the player's health reached zero, it is restored.
      - **Bullet Count**: If the player ran out of ammunition, bullets are replenished.
      - **Fuel Capacity**: If fuel depletion caused the loss, fuel is refilled.
   - The coins required for revival (2 coins) are deducted from the player's total.

3. **Automatic Transition**:  
   If no action is taken within the 5-second timer, the player is considered to have forfeited the redemption, and the game transitions to the **Level Lost Overlay**.

#### **Insufficient Balance Notification**
**Scenario**:  
If the player attempts to redeem life but lacks sufficient coins:
1. The **Insufficient Balance Overlay** is displayed, notifying the player of their inability to continue due to inadequate resources.
2. This overlay offers a single actionable button to redirect user to LevelLostOverlay, maintaining clarity.

#### **Implementation Details**
1. **Redemption Logic**:
   - When the **Redeem Life Overlay** is shown, the system checks the player’s coin balance.
   - If coins >= 2:
      - Coins are decremented using the `decrementCoins()` method in the `UserStatsManager`.
      - The parameter responsible for the loss is restored:
         - `reviveUserLife()` restores health.
         - Additional bullets are added if the bullet count is zero.
         - Fuel is refilled using `reviveUserFuel()` if needed.
      - The game resumes with the timeline (`playTimeline()`).

   - If coins < 2:
      - The **Insufficient Balance Overlay** is triggered via `insufficientInventory()`.

2. **Post-Redemption Gameplay**:
   - Players who successfully redeem life continue from the same state but with restored resources, ensuring minimal disruption to immersion.
   - This mechanic discourages frivolous retries, as coins are a finite resource.

#### **Benefits of This Feature**
1. **Enhanced Engagement**:  
   The redemption system keeps players invested in their progress, reducing frustration and encouraging persistence.
2. **Resource Management**:  
   Players must balance spending coins for revival versus saving them for future upgrades or challenges.
3. **Second Chances**:  
   Offers a fair second-chance mechanic without compromising the game's challenge or stakes.
4. **Player Retention**:  
   By offering an in-game solution to failure, players are less likely to abandon the game after a loss.
---

### Coin Collection System Implementation

#### 1. Purpose
Coins serve as a collectible resource, contributing to overall player progression and offering incentives like increased scores or in-game rewards.

#### 2. Features and Functionality
- **Coin Spawn Mechanics**:
    - Coins are randomly spawned on the screen within specified boundaries.
    - Each coin is assigned a `magnetRadius` property, enabling attraction when a magnet is active.
    - There is a maximum of coins that can be present on the screen at once: this value is dynamically updated as coin instances are added and removed.
- **Magnet Integration**:
    - When the magnet is activated, coins within the radius are attracted to the user plane, simulating a magnetic pull.
- **Collision Detection**:
    - Collision between the user plane and coins triggers collection, increasing the player's coin count.
- **UI Updates**:
    - The collected coin count is displayed on the screen and updates dynamically during gameplay.

#### 3. Why These Features Were Implemented
- **Player Incentive**: Encourages exploration and risk-taking to collect coins while avoiding obstacles and enemies.
- **Replayability**: Collectibles motivate players to replay levels to maximize their rewards.
- **Progression System**: Coins provide a tangible measure of accomplishment, integrating seamlessly into a progression or reward system.

---

### **Magnet Feature for Coin Attraction**
The magnet feature adds a strategic gameplay element, enabling players to attract coins within a defined radius. It enhances engagement through intuitive mechanics and real-time feedback.

#### **Implementation Details**
1. **Attraction Mechanism**:
    - **Magnet Activation**: Players can activate the magnet for a limited duration to optimize coin collection.
    - **Radius of Effect**: Coins within a predefined radius are attracted, encouraging effective positioning.
    - **Feedback**: Activation triggers visual effects (e.g., enlarged magnet field) and audio cues for immediate feedback.

2. **Dynamic Updates**:
    - Real-time updates reflect the magnet’s activation status and radius, dynamically pulling coins towards the player.

3. **Collectible Interaction**:
    - Coins within the radius are automatically collected, simplifying the process and reducing reliance on precise movements.

4. **Integration with `LevelParent`**:
    - **Magnet Management**: Tracks and spawns magnets based on level progression.
    - **UI Integration**: Updates the interface to display magnet availability and active status.

#### **Benefits of This Feature**
- **Enhanced Gameplay**: Adds strategy by allowing players to maximize coin collection at optimal times.
- **Increased Engagement**: Immersive feedback ensures the feature feels impactful and satisfying.
- **Simplified Collection**: Eases collection, letting players focus on avoiding enemies or completing objectives.
- **Dynamic Challenges**: Supports in-game options like ‘Game Revival,’ making coins more accessible.

---

### **Collision Cooldown Feature**

#### **Purpose**
The **Collision Cooldown feature** prevents players from taking repeated damage during consecutive collisions, ensuring a fair gameplay experience and reducing the risk of rapid health depletion.

#### **Features and Functionality**
1. **Damage Mitigation**:  
   The player becomes temporarily invincible for **2 seconds** after a collision, avoiding consecutive damage from quick successive hits.
2. **Visual Feedback**:  
   A **fading animation** signals the cooldown period, clearly indicating the player's temporary invincibility to enhance player awareness.
3  **State Restoration**:  
   After the cooldown ends, the player's normal state is **restored automatically**, re-enabling damage reception.

#### **Key Implementations**
- **Cooldown Activation**: Triggered when a collision occurs, temporarily disabling damage reception for a precise duration.
- **Timer and Animation Synchronization**: The invincibility timer is synchronized with visual fading effects for seamless feedback.
- **Independent State Tracking**: A separate state ensures collision handling operates without interfering with other game mechanics.
- **Automatic Reset**: The cooldown ends automatically, restoring normal gameplay mechanics.

---

### **Level 3: Survival Challenge**

#### **Purpose**
Level 3 introduces a survival-based challenge where the player must endure incoming attacks from all directions without the ability to move, emphasizing strategic defense and precision shooting.
#### **Features and Functionality**
1. **Stationary Gameplay**:
    - The player's tank remains fixed in the center of the screen.
    - Rotation is used to aim and fire, controlled via arrow keys.

2. **Multi-Directional Enemy Attacks**:
    - Enemies spawn randomly from the north, south, east, and west, increasing the challenge.

3. **Survival Timer**:
    - Players must survive for a **15-second timer** to win the level.

4. **Additional Elements**:
    - **Magnets**: Rare spawns that pull coins toward the player.
    - **Coins**: Collectible items that enhance player rewards.

#### **Benefits for the Game**
1. **Enhanced Challenge**:  
   The stationary mechanics and multi-directional attacks provide a unique twist, testing the player's reflexes and decision-making skills.
2  **Gameplay Diversity**:  
   The stationary survival mode contrasts with previous levels, adding variety and keeping the game fresh for players.

---

### Level 4: Out of Fuel!
#### Purpose
Level 4 introduces unique gameplay mechanics that emphasize resource management, precision, and navigation under constraints. The primary objective is to survive with limited fuel while collecting fuel tokens, dodging obstacles, and earning coins. This level tests the player's multitasking and strategic thinking.

#### Features and Functionality
- **Fuel Management**:
    - Players start with 8 seconds of fuel, decreasing over time.
    - Fuel tokens are essential to continue gameplay, each adding 5 seconds of survival time.
- **Fuel Depletion and Collection**:
   - A decrement function reduces fuel every second.
   - Fuel tokens are spawned randomly, with a probability of 0.2, ensuring resource scarcity.
- **Obstacles**:
    - Players must avoid randomly spawned obstacles with predefined dimensions; COLLIDING with them damages user health.
- **Finish Line**:
    - A finish line is introduced as a survival goal, spawning when 7 seconds of the timer remain.
- **Enemy Units**:
    - Enemy rockets are periodically spawned, adding combat elements.
- **Winning and Losing**:
    - Winning Condition: Survive until the finish line.
    - Losing Conditions:
        - Health reaches zero.
        - Fuel depletes before time ends.
        - Bullet count depletes.
- **Multitasking**:
  - Combining multiple challenges (fuel, enemies, and obstacles) elevates the complexity and engagement level.
- **Enhanced Input Handling**:
    - Players control the user plane with up-arrow for ascending and automatic descent upon release, introducing a physics-like feel.

#### Benefits of This Implementation
- **Skill Testing**: Tests multitasking, precision, and quick decision-making.
- **Progression**: Smoothly transitions from earlier levels’ mechanics to more complex challenges, preparing players for higher difficulty levels.

---

### Implementation of the "You Win" Screen

#### Purpose
The **"You Win" screen** is a celebratory display presented when the player successfully completes all four levels. It highlights their achievements, provides detailed feedback on their performance, and allows seamless navigation to the main menu.

#### Features and Implementation
1. **Victory Announcement**:
    - Bold gold title **"YOU WON!"** with a black stroke emphasizes the player's achievement. .

2. **Performance Summary**:
    - Displays:
        - Total coins collected.
        - Total kills achieved.
    - Level-specific scores with corresponding star ratings, dynamically generated via the `LevelScoreGenerator` class.

3. **Interactive Navigation**:
    - A **Main Menu** button lets the player easily return to the main screen by initiating the `StartScreen`.

#### Why This Implementation?
This screen provides a polished and rewarding conclusion to the game while reinforcing player engagement. It showcases achievements, by breaking down level performance.

---

### UI Implementation: Enhancing User Experience

**Purpose**:  
Create a seamless, intuitive interface for easy navigation and engaging gameplay interaction.

**Features**:
- **Start Screen**: Interactive menu with options (Start, Settings).
- **Level Transitions**: Countdown and level info.
- **You Win Screen**: Displays score and progress.
- **Buttons**: Accessible and stylized for navigation.
- **Animations & Effects**: Confetti and smooth transitions.
- **Responsive Layout**: Adapts to various screen sizes.

**Implementation Reason**:
- Improve **navigation** and **engagement** with clear, interactive UI elements.
- Provide smooth flow between menus, gameplay, and victory states.

**Benefits**:
- **Enhanced user experience** with intuitive controls and animations.
- **Streamlined navigation** for a seamless game progression.

---

## Implemented but not working properly
#### Noted: There are currently no features in the game that aren't working properly. However, I encountered several issues during development. Below are a few notable problems and how I overcame them:

1) **Enemy Spawning Logic:**
    - **Issue:** The game had a maximum number of enemies that could spawn. Initially, I incremented `currentNumberOfEnemies` whenever an enemy spawned and decremented it when an enemy was killed or penetrated defenses. However, I realized that the `currentNumberOfEnemies` was going negative, causing the system to spawn more enemies than intended. This resulted in situations where negative values (e.g., -99) would allow a much higher number of enemies (e.g., 99) to spawn, bypassing the cap.
    - **Solution:** I added a check in the collision handler to ensure that `currentNumberOfEnemies` would only decrement if its value was greater than 0. This prevented negative values and ensured the spawning logic remained within the set maximum.

2) **Coin Attraction with Magnet:**
    - **Issue:** The magnet was supposed to attract coins towards the player, but the coins were moving to incorrect locations. I had mistakenly used `user.getTranslateX()` to obtain the player's position, which gave me the relative position of the user plane instead of the actual grid coordinates.
    - **Solution:** I updated the logic to use `getLayoutX()` combined with `getTranslateX()`, which provided the correct actual position on the grid. This fixed the coin attraction, and coins now properly follow the user.

3) **Lag in Level 3:**
    - **Issue:** Level 3 was experiencing significant lag and became unresponsive after some time. I discovered that many actors were being created but not destroyed, causing memory bloat and slowing the game.
    - **Solution:** I ensured that any enemy planes that left the screen boundaries were destroyed and removed from the active actors array. This eliminated the memory overload, improved performance, and removed the lag in Level 3.
---

## Features Not yet implemented 

1. **User Login and Progress System:**
    - **Implementation Plan:** I intended to develop a login and user progress tracking system, allowing players to save and load their progress (e.g., current level, score). Files were created, including `UserProgress` and `ProgressManager` classes, designed to serialize and store player data locally. The logic to track and update the user's progress was also mapped out, with mechanisms to save and load progress from files. Additionally, a registration system was drafted with functionality for new users to register, and existing users to log in and resume their game at the saved level.
    - **Reason for Incompletion:** Due to time constraints, the integration of these components with the game's main logic and UI was left unfinished, and further testing and finalization were required. The plan was to provide a seamless experience where users could pick up where they left off, but this feature remains a planned enhancement.

2. **Game Level Menu:**
    - **Implementation Plan:** I wanted to create a game menu that would allow users to select a specific level to play, showing a visual representation of completed levels and unlocked levels. Once a player finishes a level, they should be able to go back to the menu and choose any previously unlocked level. This would allow for non-linear progression where players could revisit any level they completed, adding replayability and a sense of achievement as they progress through the game.
    - **Reason for Incompletion:** The logic for the level progression and menu was outlined but not fully implemented. The menu interface was not created due to lack of time, and further work was required to integrate level unlocking, saving, and displaying progress dynamically.

3. **Multiplayer Mode:**
    - **Implementation Plan:** I wanted to incorporate a competitive multiplayer mode where one player controls the protagonist (the user) and the other controls the enemy. In this mode, the two players would compete in real-time, with the user completing objectives or attempting to defeat the other player. The enemy player would try to stop the user by setting traps, controlling enemies, or directly attacking. This would create an engaging PvP experience, providing a dynamic and competitive layer to the game.
    - **Reason for Incompletion:** Due to time constraints, the multiplayer mechanics, such as networking and synchronization of the players' actions, were not implemented. While the core concept was outlined, the development of features like matchmaking, real-time interaction, and balancing between the user and enemy roles was left unfinished and would require further work to be fully functional.

## New Java Classes

### 1. `AppStage`

- **Purpose**: The `AppStage` class is a singleton that manages the application's primary stage. It provides a centralized way to access and manipulate the main stage across different parts of the application.

- **Location**: `com.example.demo.controller.AppStage`

- **Key Implementation**:
    - **Singleton Design Pattern**: Ensures only one instance of `AppStage` exists during the application's lifecycle.
    - **Primary Stage Management**: Stores and provides access to the primary `Stage` instance using `setPrimaryStage()` and `getPrimaryStage()` methods.

- **Usage**:
    - **Main Class**: In the `Main` class (`com.example.demo.controller.Main`), `AppStage.getInstance().setPrimaryStage(stage)` is used to set the primary stage, making it accessible globally in the application.
    - Instance used in several other classes to get `stage` dimensions for class-specific usage.
**Benefits**:
- **Centralized Stage Management**: Ensures consistency and flexibility when accessing the main `Stage` throughout different screens and components of the app.
- **Improved Code Maintainability**: The singleton pattern allows for better control of the stage without needing to pass it through multiple methods or classes.
 
---

### 2. `GameLoop`

- **Purpose**: The `GameLoop` class is a singleton responsible for managing the game's update cycle, controlling the timeline, and updating entities that implement the `Updatable` interface. It provides centralized control over game updates, including the ability to pause and resume.

- **Location**: `com.example.demo.controller.GameLoop`

- **Key Implementation**:
    - **Singleton Design**: Ensures only one instance of the `GameLoop` exists, providing consistent access to the game loop across the application.
    - **Timeline Management**: Uses a `Timeline` to control the game's update cycle, invoking updates for all registered `Updatable` objects at regular intervals.
    - **Updatable Interface**: Entities that need to be updated (like planes, coins, etc.) implement the `Updatable` interface, which the game loop calls every cycle.

- **Usage**:
    - **Entity Updates**: Entities like `UserPlane`, `EnemyPlane`, and `Coins` implement `Updatable`, and their `update()` method is called each frame by the `GameLoop`.
    - **Pause/Resume**: The game loop can be paused or resumed externally via the singleton, allowing external components to control game flow.

**Benefits**:
- **Centralized Update Management**: The singleton `GameLoop` ensures consistent and coordinated updates across the game.
- **Modular and Flexible**: The `Updatable` interface allows entities to manage their own logic independently while being synchronized by the game loop.
- **External Control**: The ability to pause and resume the game externally enhances flexibility without modifying game logic.

---

### 3. **Interface:** `Updatable`

- **Purpose**: The `Updatable` interface is designed to ensure that any class implementing it provides an `update()` method, which is called every frame to update the object's state. This is essential for objects like game entities, which need periodic updates (e.g., movement, position changes, or interactions with other objects).

- **Location**: `com.example.demo.actors.Updatable`

- **Usage**:
    - **Updatable Interface**: The interface declares a single method, `update()`, which must be implemented by any class that needs to be updated regularly.
    - **Entity Updates**: Any class that represents a game entity, such as `UserPlane` or `EnemyPlane`, implements `Updatable` to ensure that its state is refreshed every frame. For example, the position of these entities may change based on user input or game logic.
    - **Centralized Actor Management**: The `ActorManager` uses its `update()` method to call `update()` on all active game entities. This centralizes the update logic, making it easier to manage and ensure consistent behavior across the game world. It iterates over the `activeActors` list and calls `update()` on each actor during every game frame. This ensures that all actors are synchronized and their states are updated accordingly.
    - **Level Management**: The `LevelParent` class uses the `update()` method to coordinate more complex updates, like collision detection, game-over checks, and spawning of new entities. The `Updatable` interface allows it to call `update()` on entities that need to be updated, thus decoupling the game logic and promoting scalability.

**Benefits**:
- **Consistency and Synchronization**: By implementing the `Updatable` interface, each entity in the game guarantees that its state is updated consistently on every frame. This leads to smooth and predictable game behavior.
- **Modular and Scalable**: The interface allows game entities to define their own update logic while leaving the responsibility for managing and updating them to higher-level components like `ActorManager` and `LevelParent`. This modular design makes it easy to scale the game as new entities or behaviors are introduced.
- **Efficient Game State Management**: The `ActorManager` centralizes the update process, ensuring that all active actors are updated efficiently, and their changes are reflected in the game world without requiring individual management in each class.

---

### 3. **Interface: `Collidable`**

- **Purpose**: The `Collidable` interface defines methods for handling collisions between game entities. It ensures that each object capable of collision can manage damage and identify its relationship to other objects (i.e., whether it is friendly or an enemy).

- **Location**: `com.example.demo.actors.Collidable`

- **Key Implementation**:
    - **takeDamage Method**: This method allows objects to respond to damage when colliding with others. It is implemented by game entities to define what happens to them (e.g., losing health, being destroyed) upon a collision.
    - **isFriendly Method**: This method checks whether an object is a friendly entity or an enemy. This distinction is crucial for ensuring that friendly entities don’t cause damage to each other, while enemy entities interact differently, such as being damaged or destroyed upon collision.
    - **GameEntity Class**: Implements `Collidable` along with other interfaces. Each `GameEntity` provides custom logic for the `takeDamage()` and `isFriendly()` methods. For example, a `UserPlane` may be destroyed upon collision with an enemy, while an enemy plane may be destroyed when hit by a friendly projectile.

**Benefits**:
  - **Collision Flexibility**: The `Collidable` interface allows different game entities to define their specific reactions to collisions, making it easier to manage diverse behaviors across various actor types.
  - **Streamlined Damage Handling**: By defining standardized methods for damage and entity classification, the interface simplifies the collision detection process. The game engine can check if objects are `Collidable`, and then delegate the appropriate response (e.g., applying damage, destruction).
  - **Scalability**: New types of collidable objects can be easily added by implementing the `Collidable` interface, ensuring that all future entities automatically handle damage and classification without modifying the existing system.

---

### 4. **Interface:** `Collectible`

- **Purpose**: The `Collectible` interface defines a method to check whether a game entity can be collected by the player, typically items like coins, fuel, or power-ups. It helps identify objects that are collectable and ensures that the game logic treats them differently, particularly during collision handling.

- **Location**: `com.example.demo.actors.Collectible`

- **Key Implementation**:
    - **isCollectible Method**: This method checks if an object is collectable. If an actor (e.g., coin, fuel) is collectable, it means the player can collect it without penalty (such as losing health). Non-collectable objects, such as enemy units, should not be collected and should trigger damage or destruction upon collision.
    - **GameEntity Class**: Implements the `Collectible` interface along with other interfaces. Each specific type of `GameEntity` provides its own implementation of the `isCollectible()` method to return true for items that can be collected by the player (e.g., `Coins`, `Fuel`) and false for non-collectable entities like enemies.

**Benefits**:
- **Clear Object Classification**: The `Collectible` interface helps distinguish between collectable and non-collectable objects, ensuring that friendly items like coins or fuel are treated correctly during collisions.
- **Collision Handling Simplification**: In the collision detection system, the `isCollectible()` method allows the game to determine whether to trigger damage or not. Collectable objects don’t cause damage when colliding with enemy units, but non-collectables (such as enemies) do.
- **Extensibility**: New collectable objects can easily be added to the game by implementing the `Collectible` interface, allowing items with different functionalities (e.g., bonus points, health boosts) to be introduced without altering the existing collision system.

---

### **Class:** `EnemyRocket`

#### **Purpose**
The `EnemyRocket` class represents an enemy rocket in the game, functioning as an active threat for the player. It is designed to add variety to the types of enemies in the game by introducing a dynamic, moving projectile that the player must avoid or destroy.

- **Location**: `com.example.demo.actors.Planes.enemyPlanes`

#### **Key Features**
1. **Inherited from `FighterPlane`**:
2. **Directional Movement**:
    - The rocket moves in one of four possible directions (North, East, South, or West). The direction is determined by the `direction` variable, which is passed during instantiation.
    - The movement is implemented using the `rotatePlane` and `moveHorizontally` or `moveVertically` methods, which ensure that the rocket's position is updated based on its launch direction.
3. **Health and Damage**:
    - The rocket starts with an initial health value of 1 and can take damage. If it collides with an object or another actor in the game, the `takeDamage()` method is called, reducing its health.
    - If the health drops to 0, the rocket is considered destroyed and removed from the game.
4. **No Projectile Firing**:
    - Unlike other actors, the `EnemyRocket` does not fire projectiles itself (the `fireProjectile()` method returns `null`). This simplifies its role to being a direct threat that the player needs to avoid or destroy.
5. **Not Friendly or Collectible**:
    - The `isFriendly()` method returns `false`, indicating that the rocket is an enemy.
    - The `isCollectible()` method also returns `false`, so it is not an item that the player can collect, unlike coins or other bonuses.

#### **Why It Was Implemented**
The `EnemyRocket` class was introduced to increase the complexity and variety of enemy behavior in the game. It provides a unique type of enemy that moves in multiple directions, requiring the player to adapt their strategy depending on the rocket's movement. It also adds a new challenge to the player by forcing them to avoid or destroy these rockets while managing other in-game tasks such as collecting items or dealing with other enemies.

---

### **Class: `UserTank`**

#### **Purpose**
The `UserTank` class represents a stationary user-controlled vehicle designed for precision attacks in the game. Unlike other user vehicles, the tank remains fixed in its position and can only rotate to aim and fire projectiles in different directions. This design adds a strategic element to gameplay, where players must rely on rotation and timing rather than movement.

- **Location**: `com.example.demo.actors.Planes.friendlyPlanes`

#### **Key Features**
1. **Stationary Position**:
    - The `UserTank` does not move during gameplay. Its position is fixed at the center of the screen, determined by the constants `INITIAL_X_POSITION` and `INITIAL_Y_POSITION`.

2. **Directional Rotation**:
    - Provides four methods (`faceNorth()`, `faceSouth()`, `faceEast()`, and `faceWest()`) to rotate the tank:
        - Adjusts the rotation angle using the `rotatePlane()` method.
        - Updates the projectile's spawn position to align with the current direction.

3. **Projectile Firing**:
    - Implements the `fireProjectile()` method, which creates a new `UserProjectile` at the specified spawn position (`PROJECTILE_X_POSITION`, `PROJECTILE_Y_POSITION`) and shoots it in the direction of the tank's current rotation.

4. **Initial Configuration**:
    - The tank's position is statically set in the center of the screen for visibility and accessibility.
    - Uses the image `userTank.png` with a predefined height of 100 pixels for its graphical representation.

5. **Updates During Gameplay**:
    - Overrides the `update()` method to manage internal logic and maintain synchronization between rotation and projectile alignment.

#### **Why It Was Implemented**
The `UserTank` was introduced to provide a unique gameplay mechanic focusing on precision and defense. As a stationary vehicle, the tank challenges the player to anticipate enemy movements and strategically rotate to engage targets. Its design promotes a different style of play, emphasizing aiming and timing over mobility, which complements the other user-controlled vehicles in the game.

---

### **Class: `UserParent`**
#### **Purpose**
The `UserParent` class serves as a composite base class for all user-controlled vehicles in the game, including the `UserPlane` and `UserTank`. It encapsulates common properties and functionalities such as health management, bullet and fuel tracking, collision cooldown mechanisms, and power-up effects. By centralizing shared logic, it facilitates the development and extension of diverse user vehicle types while maintaining consistency and reducing code duplication.

- **Location**: `com.example.demo.actors.Planes.friendlyPlanes`

#### **Key Features**
1. **Core Attributes**:
    - **Health Management**: Provides methods for decrementing and incrementing health, with logic to determine if the vehicle is destroyed.
    - **Bullet Count**: Tracks the number of bullets available for firing, with methods to increment and decrement the count.
    - **Fuel Capacity**: Maintains the fuel level, with methods to modify and reset fuel as needed.

2. **Collision Cooldown**:
    - Prevents rapid successive damage through a cooldown mechanism (`collisionCooldownActive`).
    - Includes visual feedback during cooldown using `FadeTransition` animations.
    - Automatically resets after the specified cooldown duration.

3. **Power-Up Effects**:
    - Implements visual and scale-based effects for power-ups like magnets (`initiateMagnetActivated()` and `endMagnetActivated()`).
    - Tracks gameplay metrics such as kill count and coins collected.

4. **Destruction and Revival**:
    - Supports vehicle destruction (`destroyUser()`) and revival for health, fuel, and bullets (`reviveUserLife()`, `reviveUserFuel()`, and `reviveBulletCount()`).

5. **Gameplay Metrics**:
    - Tracks statistics such as the number of in-level kills (`inLevelKillCount`) and coins collected (`coinsCollected`), with methods for incrementing or decrementing these values.

6. **Abstracted Movement and Projectile Firing**:
    - Placeholder methods (`updatePosition()` and `fireProjectile()`) allow subclasses to implement their specific movement and firing logic.
    - Ensures a unified interface for all user vehicles.

#### **Why It Was Implemented**
The `UserParent` class was introduced to serve as a centralized base for all user-controlled vehicle types, providing shared attributes and functionalities while allowing subclasses to define their specific behavior. It simplifies the creation of new vehicle types (e.g., `UserPlane`, `UserTank`) by providing default implementations and a unified interface, reducing code redundancy and improving maintainability.

This design facilitates future expansion, allowing new user vehicle types to be integrated seamlessly.

---

### **Class: `Coins`**

#### **Purpose**
The `Coins` class represents collectible coins in the game that players can gather to increase their in-game resources. It also interacts with the magnet feature, which attracts coins toward the player's vehicle when activated.

- **Location**: `com.example.demo.actors.additionalUnits`

#### **Key Features**

1. **Visual Representation**:
    - The coin uses the image `coin.png` with a height of 40 pixels.
    - Positioned on the screen with initial `X` and `Y` coordinates provided during instantiation.

2. **Horizontal Movement**:
    - Moves leftward across the screen at a constant velocity (`HORIZONTAL_VELOCITY`).
    - Allows players to encounter coins dynamically during gameplay.

3. **Magnet Interaction**:
    - When the magnet is activated, the coin checks if it is within the magnet's radius (`magnetActivated` and `isWithinMagnetRadius()`).
    - If within range, the coin retrieves the player's position and moves incrementally toward the player (`moveTowards()`).

4. **Collision and Removal**:
    - Coins are removed from the game when collected or damaged (`takeDamage()`).
    - Collectibility is signaled through the `isCollectible()` method, returning `true`.

5. **Compatibility with Player Vehicles**:
    - Works with any subclass of `UserParent` (`UserPlane` and `UserTank`).
    - Coins behave differently depending on the vehicle:
        - For `UserTank`, all coins on screen are attracted when the magnet is active.
        - For `UserPlane`, only coins within the magnet radius are attracted.

6. **Dynamic Magnet Radius**:
    - The magnet radius is defined during instantiation and varies based on the gameplay scenario, allowing for flexible difficulty adjustments.

#### **Why It Was Implemented**
The `Coins` class was introduced to incentivize exploration and interaction within the game environment. By collecting coins, players gain tangible rewards that enhance their progression. The integration of the magnet functionality adds a strategic layer, requiring players to decide when to activate the magnet for maximum benefit.

This implementation also supports diverse player vehicles (`UserPlane` and `UserTank`) with tailored behaviors, enhancing gameplay variety and accommodating different strategies. The dynamic movement of coins toward the player enriches the visual and interactive elements of the game.

### **Class: `Magnet`**

#### **Purpose**
The `Magnet` class represents a collectible power-up in the game that activates a magnetic effect, attracting nearby coins towards the player's vehicle. It adds a strategic layer by allowing players to gather resources efficiently when the magnet is collected.

- **Location**: `com.example.demo.actors.additionalUnits`

#### **Key Features**

1. **Visual Representation**:
    - The magnet uses the image `magnet.png` with a height of 40 pixels.
    - Positioned on the screen with initial `X` and `Y` coordinates passed during instantiation.

2. **Dynamic Directional Movement**:
    - Moves based on its launch direction, determined by the `direction` parameter:
        - `0`: Moves upward (North).
        - `1`: Moves leftward (East).
        - `2`: Moves downward (South).
        - `3`: Moves rightward (West).
    - Direction-specific movement is handled by the `updatePosition()` method using `VERTICAL_VELOCITY` and `HORIZONTAL_VELOCITY`.

3. **Interaction and Removal**:
    - When collected or damaged, the magnet is removed from the game through the `takeDamage()` method.
    - The `isCollectible()` method returns `true`, signifying it as a collectible power-up.

4. **Friendly Entity**:
    - The `isFriendly()` method returns `true`, ensuring that the magnet is non-threatening and interacts positively with the player.

5. **Gameplay Integration**:
    - Once collected, it triggers a magnetic effect, enabling the attraction of coins within a certain radius to the player's vehicle.

#### **Why It Was Implemented**
The `Magnet` class was introduced to enhance gameplay by providing players with a utility-focused power-up. Its primary purpose is to simplify resource collection, reducing the need for precise navigation to gather coins. This makes the gameplay more engaging and rewarding, especially in levels with high coin density.
Additionally, its compatibility with different user vehicles (e.g., `UserPlane` and `UserTank`) ensures consistent functionality across diverse gameplay styles.

### **Class: `Obstacle`**

#### **Purpose**
The `Obstacle` class represents a non-destructible hazard that moves horizontally across the game environment, challenging players to navigate around or avoid it. It serves to increase the difficulty of the game by introducing barriers that disrupt movement and strategies.

- **Location**: `com.example.demo.actors.additionalUnits`

#### **Key Features**

1. **Visual Representation**:
    - Uses the `obstacle.png` image with a height of 300 pixels.
    - Positioned at specific `X` and `Y` coordinates passed during instantiation.

2. **Horizontal Movement**:
    - Moves leftward at a constant velocity, defined by the `HORIZONTAL_VELOCITY` constant.
    - Movement is implemented in the `updatePosition()` method, which shifts the obstacle horizontally across the screen.

3. **Non-Interactive with Damage**:
    - The obstacle cannot take damage. The `takeDamage()` method does not alter its behavior, emphasizing its role as a static challenge.
    - Players cannot destroy it, but it can be removed from the game using the `destroy()` method.

4. **Non-Collectible and Hostile**:
    - The `isFriendly()` method returns `false`, indicating that the obstacle poses a threat to the player.
    - The `isCollectible()` method returns `false`, confirming that it is not a collectible object.

5. **Static Dimensions**:
    - Includes a static `getDimensions()` method that returns the obstacle's fixed height (300 pixels), providing a consistent reference for collision detection or layout adjustments.

#### **Why It Was Implemented**
The `Obstacle` class was added to enhance the complexity of gameplay by introducing stationary hazards. Its consistent movement and non-destructible nature make it a reliable challenge for players, requiring them to adapt their navigation skills and strategies. Obstacles add variety and balance to the game by complementing dynamic threats like enemy projectiles or moving enemies.

By ensuring that obstacles cannot be destroyed or collected, the game maintains a balanced risk-reward system, compelling players to focus on avoidance rather than direct confrontation. Their integration adds a layer of spatial awareness and timing to gameplay, making it more engaging and challenging.

### **Class: `FuelToken`**

#### **Purpose**
The `FuelToken` class represents a collectible item that replenishes the player's fuel when collected. It moves across the screen and provides players with an opportunity to sustain their vehicle's energy, ensuring prolonged gameplay.

- **Location**: `com.example.demo.actors.additionalUnits`

#### **Key Features**

1. **Visual Representation**:
    - Uses the `fuel.png` image with a height of 55 pixels, ensuring distinct visibility.
    - Positioned at specific `X` and `Y` coordinates provided during instantiation.

2. **Horizontal Movement**:
    - Moves leftward at a constant velocity of `-8`, defined by the `HORIZONTAL_VELOCITY` constant.
    - The `updatePosition()` method handles the movement logic, ensuring the token traverses the screen smoothly.

3. **Collectible**:
    - The `isCollectible()` method returns `true`, indicating that the player can collect the fuel token to gain a gameplay benefit.

4. **Friendly Nature**:
    - The `isFriendly()` method returns `true`, signifying that the token is a positive and non-hostile element within the game.

5. **Destruction Upon Collection**:
    - The `takeDamage()` method removes the token from the game via `ActorManager.getInstance().removeActor(this)`. This ensures proper management of game objects once the token is collected or no longer needed.

#### **Why It Was Implemented**
The `FuelToken` was introduced to provide an additional resource management mechanic for the player. By collecting these tokens, players can replenish their vehicle's fuel, creating a balanced gameplay dynamic where resource acquisition becomes part of the strategy.

The presence of fuelTokens adds depth to the gameplay, ensuring players remain aware of their fuel levels and seek tokens to avoid running out of energy mid-level.

### **Class: `FinishLine`**

#### **Purpose**
The `FinishLine` class represents the goal or endpoint in a level, signifying success when reached by the player. It marks the completion of the current level and progression to subsequent gameplay stages.

- **Location**: `com.example.demo.actors.additionalUnits`

#### **Key Features**

1. **Visual Representation**:
    - Uses the `finishLine.png` image, visually distinctive and easily identifiable.
    - Height is set to 900 pixels, making it prominent on the screen.
    - Initial position is defined by `initialXPos` and `initialYPos`, allowing flexible placement within levels.

2. **Horizontal Movement**:
    - Moves from right to left across the screen at a constant velocity of `-10`, implemented via the `HORIZONTAL_VELOCITY` constant.
    - The `updatePosition()` method ensures smooth, consistent movement in alignment with other dynamic game entities.

3. **Game Logic Integration**:
    - Marked as a friendly entity (`isFriendly()` returns `true`), indicating its positive role in gameplay.
    - `isCollectible()` returns `true`, signaling that interaction (e.g., crossing it) triggers level completion logic.

4. **Simplified Lifecycle**:
    - The `takeDamage()` method is intentionally left blank, as the `FinishLine` is not destructible or combat-related.

#### **Why It Was Implemented**
The `FinishLine` is integral for structuring gameplay objectives, providing a clear and satisfying endpoint to levels. Its implementation supports the following:
- **Objective Clarity**: Offers players a tangible goal, ensuring that level progression feels rewarding and intuitive.
- **Consistency with Gameplay Dynamics**: Maintains horizontal movement like other entities, blending seamlessly into the scrolling game environment.
- **Streamlined Design**: By focusing solely on its role as a level endpoint, the class avoids unnecessary complexity.

---

new format

### **Class: `ActorManager`**

#### **Purpose**
Manages the lifecycle of all `GameEntity` actors, ensuring their addition, removal, and updates are synchronized and efficiently integrated into the game scene.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Singleton Design**: Provides a single global instance for actor management.
2. **Actor Control**: Tracks active actors and manages pending additions and removals with thread-safe lists.
3. **Scene Updates**: Dynamically updates the game scene by adding or removing actors.
4. **Actor Updates**: Invokes `update()` on all active actors every frame.
5. **Player Support**: Retrieves the player-controlled vehicle (`UserParent`) for gameplay interactions.
6. **Level Management**: Clears all actors to reset the game state.

#### **Benefits**
Centralizes actor control, prevents concurrency issues, supports scalability, and ensures efficient, synchronized updates for smooth gameplay.

---

### **Class: `CollisionHandler`**

#### **Purpose**
Manages collisions between game entities to enforce gameplay logic, including handling interactions between friendly actors, enemies, and collectibles.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Collision Detection**: Identifies intersections between all active game entities.
2. **Collectible Handling**: Detects when the user vehicle collects items like coins, fuel tokens, or magnets and applies their effects.
3. **Damage Application**: Processes damage when friendly and enemy entities collide (e.g., projectiles and planes). When damage is processed, the actor is removed from the activeActor class all by itself instead of setting its isDestroyed value to true. This ensure that in the subsequent collision checks, the array is not iterating over already destroyed instances, and only the ones the one screen. This keeps the array size small and manageable and makes the collision detection process much more efficient.
4. **Kill Count Updates**: Increments the user’s kill count and decrements the enemy count upon successful eliminations.
5. **Cooldown Activation**: Triggers cooldowns for the user vehicle after collisions.

#### **Why It Was Implemented**
To streamline and centralize collision handling for better game logic consistency and scalability. This ensures all interactions, from item pickups to enemy engagements, are processed efficiently.

#### **Benefits**
- Centralizes collision logic, reducing code duplication.
- Enhances gameplay by ensuring collectibles and damage effects are applied correctly.
- Keeps the game state (e.g., kills, coins, fuel) updated dynamically.
- Maintains balanced gameplay with cooldown mechanics for collisions.

---

### **Class: `InputHandler`**

#### **Purpose**
Handles keyboard input for game entities by mapping key presses and releases to specific actions, enabling dynamic and intuitive control of game actors.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Action Mapping**: Maps `KeyCode` inputs to customizable press and release actions using `EnumMap`.
2. **Event Handlers**: Generates `KeyEvent` handlers for both key press and release events, executing the associated actions.
3. **Generalized Setup**: Provides a method to configure common input actions, like movement and firing, for specific game entities.
4. **Seamless Integration**: Designed to work with multiple actors, such as user-controlled planes, through flexible action assignments.

#### **Why It Was Implemented**
To decouple input handling from game logic, enabling reuse and scalability. This simplifies integrating new controls or changing input behavior for various game entities.

#### **Benefits**
- Enhances gameplay by allowing responsive and precise input handling.
- Supports modular and reusable design, reducing coupling between input logic and game logic.
- Simplifies adding or modifying key bindings and actions for different entities.
- Improves code maintainability by centralizing input management logic.

---

### **Class: `LevelManager`**

#### **Purpose**
Manages the progression and transitions between levels, including tracking the current level, displaying level start screens, and notifying observers of level changes.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Singleton Design Pattern**: Ensures only one instance of `LevelManager` exists across the application.
2. **Level Tracking**: Maintains the current level number and supports incrementing to the next level.
3. **Level Start Screen**: Displays a start screen for the current level with relevant information.
4. **Observer Pattern**: Notifies observers (e.g., game controllers) of level transitions using `setChanged()` and `notifyObservers()`.
5. **Boundary Handling**: Safeguards against exceeding the total number of levels.
6. **Dynamic Level Loading**: Constructs the class name of the next level dynamically for integration with the game's level loading system.

#### **Why It Was Implemented**
To centralize level management logic, streamline transitions, and ensure the game's progression is cohesive and maintainable. This allows for adding new levels or changing progression logic without impacting unrelated systems.

#### **Benefits**
- Provides a consistent and reusable mechanism for level transitions.
- Simplifies game progression logic by encapsulating level tracking in a single class.
- Supports extensibility by using dynamic level name generation and observer notifications.
- Reduces errors with clear boundaries for level progression.

---

### **Class: `OverlayHandler`**

#### **Purpose**
Manages the display and hiding of overlays during game state changes (e.g., level completion, failure, pause), interacting with `LevelStateHandler` to update the user interface.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Overlay Management**: Controls overlay creation and visibility using `OverlayFactory`.
2. **Level Completion/Failure**: Displays appropriate overlays with actions like retry or next level.
3. **Redeem Life**: Handles redeem life functionality with a countdown timer.
4. **Pause State**: Pauses the game and shows a pause overlay with resume and other options.
5. **Singleton**: Ensures only one instance of `OverlayHandler`.

#### **Why It Was Implemented**
To centralize overlay management, decouple it from other game logic, and streamline the user interface flow.

#### **Benefits**
- **Modular**: Isolates overlay logic for easier management.
- **Improved UX**: Smooth transitions between game states.
- **Maintainable**: Easy to update overlay behavior without affecting core logic.

---

### **Class: `LevelStateHandler`**

#### **Purpose**
Manages the state transitions between game levels, handling events like level completion, retry, volume control, and level revival. It coordinates with `LevelManager` and `OverlayHandler` to update the game state and UI.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Level Navigation**: Handles transitions to the main menu, next level, and level retry.
2. **Level Revival**: Allows players to revive the level by spending collected coins, restoring health, bullets, or fuel.
3. **Overlay Handling**: Integrates with `OverlayHandler` to display level completion, failure, and other UI updates.
4. **Game Control**: Manages gameplay actions such as pausing, resuming, and volume toggling.

#### **Why It Was Implemented**
To centralize the handling of game state transitions and simplify the logic around level progression, revivals, and UI updates.

#### **Benefits**
- **Centralized Logic**: Combines level transitions and UI management in one class.
- **Simplified Gameplay**: Easier control over game flow (level management, reviving, pausing).
- **Maintainable**: Isolates the game state handling, making it easier to adjust level behaviors or UI changes.

---

### **Class: `SpawnHandler`**

#### **Purpose**
Manages the spawning of actors on the screen, ensuring they follow specific spawn probabilities, limits, and avoid overlap with other actors.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Actor Spawning**: Uses a supplier to generate new actors and spawns them based on probability, respecting spawn limits.
2. **Overlap Checking**: Ensures newly spawned actors don't overlap with existing ones, retrying spawn if necessary.
3. **Flexible Spawning Logic**: Can control maximum spawn count, spawn probability, and total actor limits.

#### **Why It Was Implemented**
To manage dynamic spawning of actors (e.g., enemies, items) in the game, ensuring they appear with controlled frequency and without interfering with other on-screen actors.

#### **Benefits**
- **Controlled Actor Creation**: Ensures balanced game flow by limiting how many actors are on-screen at once.
- **Efficient Overlap Management**: Prevents actors from spawning on top of each other, improving gameplay quality.
- **Flexibility**: Easily adapts to different types of actors and their specific spawn conditions through a customizable spawning mechanism.

---

### **Class: `UserStatsManager`**

#### **Purpose**
Manages and tracks the user’s statistics, including total coins, kills, and level scores. It ensures the statistics are updated and stored correctly across levels.

- **Location**: `com.example.demo.activityManager`

#### **Key Features**
1. **Singleton Pattern**: Ensures only one instance of `UserStatsManager` exists throughout the game.
2. **Level Score Management**: Calculates, stores, and retrieves scores for each level.
3. **User Statistics Tracking**: Tracks and updates total kills and total coins collected by the user.
4. **Level Statistics Storage**: Stores statistics like total kills and coins for each level after completion.

#### **Why It Was Implemented**
To provide a centralized system for managing the user's progress, including their score, kills, and coins across different levels, and to ensure that data persists throughout the game.

#### **Benefits**
- **Centralized User Data**: Simplifies tracking of user progress by consolidating statistics in a single manager.
- **Efficient Score Calculation**: Automatically calculates scores based on user health and ammo, streamlining level completion.
- **Easy Access to Statistics**: Provides methods to get and set various statistics, making it easy to retrieve and update data.

---

### **Class: `DisplayWinningParameter`**

#### **Purpose**
Manages the display of winning parameters, such as score, kills, or coins, by combining image icons and values. It allows dynamic updates to these values and ensures they are centered on the screen in a clean, visually appealing layout.

- **Location**: `com.example.demo.displays`

#### **Key Features**
1. **Three Parameter Display**: Supports displaying three parameters, each with its corresponding image and value.
2. **Dynamic Value Updates**: Provides methods to update values and ensure they reflect in the UI immediately.
3. **Customizable Styling**: Uses bold fonts and bright colors for value text, with a black outline for clarity.
4. **Responsive Layout**: Automatically re-centers the display on the screen whenever values are updated.

#### **Why It Was Implemented**
To present key winning parameters (such as total score or other metrics) clearly and stylishly at the end of a level or game, improving player engagement and satisfaction. The flexible design accommodates multiple types of parameters while maintaining readability.

#### **Benefits**
- **Enhanced Visual Appeal**: By using large, bold text and attractive icons, the UI draws attention to key game statistics in an engaging way.
- **Flexible Design**: Supports two or three parameters, adapting to different game needs.
- **Automatic Layout Adjustment**: Ensures the display is always centered and visually balanced, improving usability across various screen sizes.

---

### **Class: `LevelScoreGenerator`**

#### **Purpose**
Handles the calculation of level scores based on remaining lives and bullets, and provides the appropriate star image path based on the score.

- **Location**: `com.example.demo.displays`

#### **Key Features**
1. **Singleton Pattern**: Ensures only one instance of the class exists.
2. **Score Calculation**: Calculates a score based on the player's remaining lives and bullets.
3. **Star Image Path**: Returns the file path for a star image based on the calculated score.

#### **Why It Was Implemented**
To determine the player’s performance and reward them with a star rating at the end of each level, based on their remaining resources.

#### **Benefits**
- **Efficient Scoring System**: Provides a clear and simple method to evaluate player performance.
- **Easy Visual Feedback**: Dynamically selects the appropriate star image to display based on the score.

### **Class: `Level3`**

#### **Purpose**
Handles the logic for Level 3 of the game, including setting up the environment, managing enemies, handling user input, and updating game status based on specific conditions like survival time and player resources.

- **Location**: `com.example.demo.levels.Level3`

#### **Key Features**
1. **Level Initialization**: Sets up the level with a background, user tank, and initial values for health, bullets, and survival time.
2. **Enemy and Item Spawning**: Spawns enemy rockets, coins, and magnets at random intervals and positions.
3. **Survival Timer**: Tracks the survival time and triggers win conditions when time runs out.
4. **Game Over Conditions**: Determines if the player wins or loses based on remaining health, bullets, and survival time.
5. **User Input Handling**: Uses keyboard input to control user tank movement and firing.
6. **Level View Updates**: Updates the UI with current player stats, such as health, bullets, and collected coins.

#### **Why It Was Implemented**
To create a challenging, timed survival level where the player must avoid enemy rockets, collect coins, and manage limited resources (health and bullets) to succeed.

### **Class: `LevelViewLevelThree`**

#### **Purpose**
Manages and displays the UI elements for Level 3, including player stats like bullets, survival time, and coins.

- **Location**: `com.example.demo.levels.Level3`

#### **Key Features**
- Extends `LevelView` and initializes UI components.
- Uses `DisplayWinningParameter` to show ammo count, fuel timer, and coin count.
- Updates the UI with relevant stats on the screen.

#### **Why It Was Implemented**
To present and update player stats in a visually clear way, reflecting level-specific parameters.

#### **Benefits**
- Centralized management of level UI.
- Easy integration of dynamic gameplay stats (ammo, time, coins).

---

### **Class: `Level4`**

#### **Purpose**
Handles Level 4 gameplay, including spawning enemies, fuel tokens, obstacles, and managing the survival timer.

- **Location**: `com.example.demo.levels.Level4`

#### **Key Features**
- Initializes user plane with fuel and bullet count.
- Spawns enemies, fuel tokens, and obstacles at intervals.
- Displays game status (health, fuel, bullets) via `LevelViewLevelFour`.
- Implements win/loss conditions and updates the survival timer.

#### **Why It Was Implemented**
To create a dynamic level with survival mechanics, requiring the player to manage resources and avoid obstacles.

#### **Benefits**
- Adds variety to gameplay with different units and obstacles.
- Clear win/loss conditions with integrated UI updates.

### **Class: `LevelViewLevelFour`**

#### **Purpose**
Manages the UI for Level 4, displaying health, fuel, ammo, and coins collected.

- **Location**: `com.example.demo.levels.Level4`

#### **Key Features**
- Inherits from `LevelView`.
- Displays fuel, ammo, and coin counts using `DisplayWinningParameter`.

#### **Why It Was Implemented**
To provide specific UI updates for Level 4, including resources like fuel, bullets, and coins.
- Ensures the player receives relevant information specific to Level 4.
- Enhances the game's visual feedback.

### **Class: `StartScreen`**

#### **Purpose**
Handles the start screen of the game, including the background, title, and buttons for starting or quitting the game.

- **Location**: `com.example.demo.displays`

#### **Key Features**
- Displays a background and title image.
- Creates and positions start and quit buttons.
- Handles button actions to start the game or quit.

#### **Why It Was Implemented**
To provide an interactive start screen where users can begin the game or exit.

#### **Benefits**
- Clear and engaging entry point for the game.
- Easy navigation for the player to start or quit the game.

---

### **Class: `LevelStartScreen`**

#### **Purpose**
Displays the start screen for a specific level, showing its aim, instructions, and a prompt to begin.

- **Location**: `com.example.demo.displays`

#### **Key Features**
- Displays background, title, level aim, and instructions.
- Handles user input (Space key) to start the level.
- Dynamically sets content based on the level number.

#### **Why It Was Implemented**
To guide the player with information about the current level and prepare them to start.

#### **Benefits**
- Provides clear and concise information about each level.
- Enhances user experience by giving clear instructions.
- Prevents confusion by displaying tailored level objectives and controls.

---

### **Class: `YouWinScreen`**

#### **Purpose**
Displays the win screen with a celebratory "YOU WON!" title, total score, total kills, level-wise scores, and a confetti effect. It also provides a button to return to the main menu.

- **Location**: `com.example.demo.displays`

#### **Key Features**
- Shows total score, total kills, and level-wise scores with star ratings.
- Includes a confetti animation effect.
- Provides a button to return to the main menu.

#### **Why It Was Implemented**
To celebrate the player's victory by providing detailed feedback and a fun, interactive way to navigate back to the main menu.

#### **Benefits**
- Increases player satisfaction with dynamic feedback and a confetti effect.
- Simplifies navigation to the main menu after completing the game.

---

### **Class: `ButtonFactory`**

#### **Purpose**
Provides a method to create image-based buttons with specified dimensions and transparent backgrounds.

- **Location**: `com.example.demo.displays`

#### **Key Features**
- `createImageButton()` method to generate a button with an image.
- Supports custom button dimensions with maintained image aspect ratio.
- Sets the button's background to be transparent.

#### **Why It Was Implemented**
To simplify and standardize the creation of buttons with images, ensuring consistency in design and functionality across the game.

#### **Benefits**
- Reduces code duplication when creating image-based buttons.
- Ensures buttons have a uniform appearance and behavior.

---

### **Class: `OverlayFactory`**

#### **Purpose**
Provides a method to create customizable overlays with titles, text, buttons, and optional score images for different scenarios.

- **Location**: `com.example.demo.displays`

#### **Key Features**
- Singleton pattern ensures a single instance for overlay creation.
- `createOverlay()` method generates overlays with dynamic buttons based on configurations.
- Buttons are created using `ButtonFactory` with specified image paths and actions.
- Optionally adds a score image if a path is provided.

#### **Why It Was Implemented**
To centralize the creation of consistent overlays with dynamic content, making it easier to manage overlays with various button actions and text in the game.

#### **Benefits**
- Streamlines overlay creation and management.
- Reduces redundancy by reusing button creation logic.
- Simplifies adding new overlays with minimal changes.

---

### **Class: `BaseOverlay`**

#### **Purpose**
Serves as a base class for creating overlays that display a title, content, buttons, and optionally a score image with a semi-transparent background.

- **Location**: `com.example.demo.displays`

#### **Key Features**
- Displays a full-screen overlay with a background and layout for content and buttons.
- Contains a `VBox` for content and an `HBox` for buttons.
- Optionally adds a score image through `addScoreImage()`.
- Provides methods to set the title, add content, and show or hide the overlay.

#### **Why It Was Implemented**
To provide a reusable structure for overlays in the game, ensuring consistent appearance and functionality across various screens (e.g., win screens, pause screens).

#### **Benefits**
- Centralizes overlay management for better code organization.
- Easy to customize with different content types (text, images, buttons).
- Simplifies the addition of score images and dynamic buttons.

### extra
• Unexpected Problems: Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.

## Modified Java Classes

#### Class: `Controller`
In the modified `Controller` class:

1. **Removal of Level Collision Handling**: The original class had logic for handling level transitions and collision detection within the `goToLevel()` method, which has now been streamlined for level management. Collision handling has been moved to a separate manager class, removing this responsibility from the `Controller`.
2. **Launch Screen Addition**: The `launchGame()` method has been changed to instantiate and show a `StartScreen` instead of immediately loading a level. This refactor now allows for a start screen before the game begins.
3. **Error Handling Consolidation**: A new private method, `showError()`, has been added to handle exceptions more cleanly, centralizing the error display logic that was previously embedded within the `update()` method.
4. **Refactored Imports**: The `LevelParent` class is now imported from the `levels` package instead of directly from the root package, aligning with a better organizational structure.
5. **Observer Handling**: The observer functionality is preserved, with the `Controller` still updating levels upon receiving notifications. However, the collision and level management logic is cleaner and more modular, with the `Controller` now focusing more on the flow of the game rather than game mechanics.

These changes simplify the class, making it more maintainable and focused on managing the game flow rather than internal game logic like collisions.

#### Class: `Main`
In the modified `Main` class:
1. **Dynamic Screen Size**: The screen size is set dynamically based on the primary screen’s dimensions, replacing the fixed values for width and height.
2. **Maximized Window**: The window is now maximized by default for better screen usage.
3. **LevelManager Integration**: The `LevelManager` is introduced to centralize game level handling, with the `Controller` registered as an observer for level transitions.
4. **AppStage Access**: The primary stage is stored in `AppStage`, allowing easy access from other classes.
These changes improve flexibility, modularity, and organization by dynamically adjusting the screen size and managing game levels more effectively.

#### Class: `ActiveActor`

The `ActiveActor` class, now in the `com.example.demo.actors` package, remains largely unchanged in terms of functionality. It extends `ImageView`, providing a base for actors in the game to manage their images and movements. The constructor sets up the actor's image, position, and size. The class includes abstract `updatePosition()` and concrete methods for moving horizontally and vertically. The main change is the relocation of the class to a different package for better organization, without altering its functionality.

#### Class: `ActiveActorDestructible` → `GameEntity`

The class `ActiveActorDestructible` (from the `com.example.demo` package) was renamed and refactored into `GameEntity` (moved to `com.example.demo.actors`). While the original `ActiveActorDestructible` only handled destructibility and basic movement, the new `GameEntity` class expands its functionality by implementing the `Collidable`, `Collectible`, and `Updatable` interfaces, offering a more comprehensive structure for game entities. The new class includes methods for managing collisions, updates, collectibility, and determining whether an entity is friendly, alongside retaining destructibility and movement features from the old class.

#### Interface: `Destructible` → `Collidable`

The `Destructible` interface, located in the `com.example.demo` package, was refactored and renamed to `Collidable` (in the `com.example.demo.actors` package). The `Collidable` interface retains the `takeDamage()` method from `Destructible` but adds a new method `isFriendly()` to determine if the entity is friendly or not. The old `destroy()` method from `Destructible` was removed as it was incorporated into other game entity handling logic, specifically in the `GameEntity` class.

#### Class: `LevelParent` Changes

**Old Class:**
- Managed the game flow, actors, and level transitions.
- Handled core gameplay mechanics like projectile firing, collision detection, and enemy spawning.

**New Class:**
- Refactored to use the **actor-manager pattern**, with `ActorManager` centralizing actor handling. Removed 4 different arrays of different types of actors on the screen and made it into one activeActors array in the actorManager class. Now all active actors were added/removed form this one class which made handling easier.
- **Input handling** and **collision handling** were moved to dedicated classes (`InputHandler` and `CollisionHandler`).
- **Magnet effect** introduced, which attracts `Coins` within a specified radius, managed by `activateMagnet()` and `deactivateMagnet()`.
- Enhanced **UI updates** through the `LevelView` class, including dynamic changes to hearts and score displays.
- Refined game state management with the `LevelStateHandler` and `OverlayHandler`, providing more granular control over level pauses, wins, and losses.
- Changed the name of the method in updateLevelView() to update the heart display from removeHearts() to syncHeartsWithUserHealth().

**Key Changes:**
- **Actor Management:** Actors like coins, enemies, and projectiles are now handled by `ActorManager`.
- **Collision & Spawn Management:** Dedicated handlers for collisions (`CollisionHandler`) and spawning (`SpawnHandler`).
- **Magnet Effect:** A magnet system allows for coin attraction to the player when activated.
- **UI & Level Handling:** Extensive use of `LevelView` to manage UI elements such as the score, health, and other gameplay overlays.

#### Class: `FighterPlane` Changes

**Old Class:**
- The `FighterPlane` class was derived from `ActiveActorDestructible`.
- It had a `health` property and abstracted the `fireProjectile()` method for subclasses.
- The `takeDamage()` method decreased health and destroyed the plane if health reached zero.

**New Class:**
- The class is now derived from `GameEntity` instead of `ActiveActorDestructible`.
- **ActorManager Integration:** When health reaches zero, the `ActorManager` removes the destroyed `FighterPlane` instance.
- **Rotation:** Added a `rotatePlane(int angle)` method to handle rotation of the plane.
- **Health Management:** Added `incrementHealth()` and `decrementHealth()` methods to adjust health dynamically, alongside the existing `takeDamage()` method.
- **Destruction Flag:** Uses an `isDestroyed` flag to track destruction, in place of directly calling `destroy()`.

**Key Changes:**
- **Inheritance Change:** The class now inherits from `GameEntity`, centralizing entity behavior management.
- **Actor Removal:** The `ActorManager` now handles removal of destroyed planes.
- **Health Modifications:** The new `incrementHealth()` and `decrementHealth()` methods provide finer control over health changes.
- **Plane Rotation:** Introduced the `rotatePlane()` method to adjust the plane's angle.

#### Class: `UserPlane`

**Old Class:**
- Inherited from `FighterPlane`.
- Managed movement, projectile firing, and health.
- Tracked kill count.

**Modifications:**
- **Inheritance Change:** Now inherits from `UserParent`, which likely handles more generalized behaviors.
- **Projectile Rotation:** The `fireProjectile()` method now includes a rotation angle, allowing for directional shooting.
- **Movement Precision:** `velocityMultiplier` changed from `int` to `double` for more precise vertical movement control.
- **New Fall Behavior:** Introduced a `fallDown()` method for controlled downward movement.
- **Position Reset:** Added a `resetPosition()` method to restore the plane's starting position.

#### Class: `EnemyPlane`

**Old Class:**
- Inherited from `FighterPlane`.
- Managed movement with horizontal velocity and projectile firing.
- Had fixed health and a firing rate.

**Modifications:**
- **Inheritance Change:** Inherits from `FighterPlane`, but now works with `GameEntity`.
- **Projectile Offset Adjustment:** Adjusted the horizontal and vertical offsets for projectiles.
- **Firing Rate:** Maintained random firing with a fixed rate of 0.01.
- **Health Change:** Health is still 1 but now handled with the `takeDamage()` method inherited from `FighterPlane`.
- **New Methods:**
    - `isFriendly()` and `isCollectible()` added, indicating that `EnemyPlane` is neither friendly nor collectible.
- **Size Change:** Image height reduced to 45 pixels to likely represent a smaller enemy plane.

#### Class: `Boss`

**Old Class:**
- Inherited from `FighterPlane`.
- Had a move pattern with vertical velocity and shield functionality.
- Used a fixed shield probability and duration, along with a fixed move pattern.
- Fired projectiles with a random rate.

**Modifications:**
- **Inheritance Change:** Inherits from `FighterPlane`, now working with `GameEntity`.
- **Movement Pattern:** Refined with random cycles and added randomness to shield activation/deactivation duration.
- **Shield Behavior:** Modified shield logic:
    - Shield now has a random duration for both activation and deactivation.
    - `LevelViewLevelTwo` is used to manage shield visibility and synchronization with the boss’s position.
- **Health Management:** Increased health to 5.
- **Projectile:** Boss fires projectiles in a random pattern with adjusted rate.
- **Additional Features:**
    - Shield and position synchronization added with `LevelViewLevelTwo`.
    - `takeDamage` method updated to only damage the boss when not shielded.
    - New method `isFriendly()` added to indicate the boss is not friendly.

#### Class: `Projectile`

**Old Class:**
- Inherited from `ActiveActorDestructible`.
- Implemented `takeDamage()` to destroy the projectile when it takes damage.
- Included an abstract `updatePosition()` method for movement logic.

**Modifications:**
- **Inheritance Change:** Now inherits from `GameEntity` instead of `ActiveActorDestructible`.
- **Rotation:** Introduced `rotateProjectile()` method to handle projectile rotation.
- **Damage Handling:** `takeDamage()` now removes the projectile via `ActorManager` instead of destroying it directly.
- **Position Update:** Keeps `updatePosition()` as abstract, requiring specific movement logic in subclasses.

#### Class: `UserProjectile`

**Old Class:**
- Inherited from `Projectile`.
- Used a constant horizontal velocity for movement.
- `updatePosition()` moved the projectile horizontally.

**Modifications:**
- **Movement Logic:** Now incorporates both horizontal and vertical velocities (`HORIZONTAL_VELOCITY` and `VERTICAL_VELOCITY`), depending on the direction of the shot.
- **Direction Handling:** The `direction` (rotation angle) is passed to the constructor to determine the projectile's movement (e.g., North, East, South, West).
- **Rotation:** Uses `rotateProjectile()` to apply the direction of movement to the projectile.
- **Damage Handling:** Inherited `takeDamage()` from `Projectile`, which removes the projectile upon taking damage.
- **Collectibility:** The `isCollectible()` method is overridden to return `false`, indicating the projectile is not collectible.

#### Class: `EnemyProjectile`

**Old Class:**
- Inherited from `Projectile`.
- Used a constant horizontal velocity for movement.
- `updatePosition()` moved the projectile horizontally.

**Modifications:**
- **Collectibility:** The `isCollectible()` method is overridden to return `false`, ensuring the projectile is not collectible.
- **Friendly Check:** The `isFriendly()` method is overridden to return `false`, indicating the projectile is not friendly.
- **Damage Handling:** Inherited `takeDamage()` from `Projectile`, which removes the projectile upon taking damage.
- **Movement Logic:** Continues using the same horizontal velocity for movement (`HORIZONTAL_VELOCITY`).

#### Class: `BossProjectile`

**Old Class:**
- Inherited from `Projectile`.
- Defined a constant horizontal velocity (`HORIZONTAL_VELOCITY`) for movement.
- `updatePosition()` moved the projectile horizontally.
- The initial X position was set to a constant value (`INITIAL_X_POSITION`).

**Modifications:**
- **Collectibility:** The `isCollectible()` method is overridden to return `false`, ensuring the projectile is not collectible.
- **Friendly Check:** The `isFriendly()` method is overridden to return `false`, indicating the projectile is not friendly.
- **Damage Handling:** Inherited `takeDamage()` from `Projectile`, which removes the projectile upon taking damage.
- **Movement Logic:** Continues using the same horizontal velocity (`HORIZONTAL_VELOCITY`) for movement.
- **Updated Image Height:** The image height has been adjusted to `40` from `75` for the modified projectile size.

#### Class: `LevelOne` -> `Level1`

**Modifications:**
- Renamed the class from `LevelOne` to `Level1` to match new naming conventions- Done so that the Level name for the next level can be dynamically calculated instead of storing string values for each Level class name and putting them in a switch case statement.
- Adjusted the background image.
- Incorporated player bullet count in initialization.
- Implemented custom conditions for losing the level (e.g., if the player is destroyed or runs out of bullets).
- Added conditions for winning the level based on a kill target.
- Added updated methods for spawning enemies and updating the level view.
- Changed the name of the method in updateLevelView() to update the heart display from removeHearts() to syncHeartsWithUserHealth().

**Key Differences:**
- **`hasLevelBeenLost()`**: Custom logic based on whether the player is destroyed or has no bullets.
- **`hasLevelBeenWon()`**: Checks if the player has reached the kill target (based on `userHasReachedKillTarget()`).
- **`spawnEnemyUnits()`**: A refined spawn system using a `spawnHandler` for managing enemy generation, with parameters such as maximum spawn and spawn probability.
- **`update()`**: Includes additional updates like enemy fire generation and updated level view.
---

### **Class: `LevelView`**

#### **Modifications and Key Differences:**
**Modifications:**
- **Package Update:** Moved from `controller` to `levels` for better organization.
- **Dependencies Updated:** Replaced `HeartDisplay`, `WinImage`, and `GameOverImage` with `DisplayHeart` and `DisplayWinningParameter` for modularity.
- **Pause Button Added:** Created via `ButtonFactory` and positioned dynamically for improved user control.
- **Winning Parameters:** Integrated real-time metrics (coins, bullets, kills) with `DisplayWinningParameter`.
- **Heart Logic Refactored:** Simplified health updates using `syncHeartsWithUserHealth()`.
- **UI Initialization:** Consolidated into `showUIComponents()` for cleaner rendering.

**Key Differences:**
- **Pause Button:** New feature enabling gameplay pausing.
- **Winning Parameter Display:** Tracks and updates gameplay stats dynamically.
- **Improved Heart Management:** Automatically syncs hearts with player health. Instead of the removing function, this clears frame every update and updated it according to the current userHealth; this allows for adding heart, mechanism also.
- **Streamlined UI Rendering:** Single method to display all UI components, reducing redundancy.

**Benefits:**
- Enhanced modularity, better UI feedback, and simplified maintenance.
- Scalable and aligned with modern game design principles.

### **Class: `Level2`**

#### **Modifications and Key Differences:**

- **Boss Inclusion**:
    - The level now features a `Boss` actor, which is the primary enemy. It is spawned once the player has cleared other enemies, marking a focused, climactic encounter.
    - The boss has health, tracked and displayed in the UI, influencing the win condition of the level.

- **Win/Loss Conditions**:
    - The level's loss condition is triggered by either the player’s destruction or running out of bullets (`getUser().getBulletCount() == 0`).
    - The win condition depends on whether the boss is destroyed, signaling completion of the level.

- **Level View Updates**:
    - `LevelViewLevelTwo` is introduced to handle the display of both player stats and the boss’s health. This view dynamically updates during gameplay to reflect changes in health, bullet count, and coins collected.
    - Changed the name of the method in updateLevelView() to update the heart display from removeHearts() to syncHeartsWithUserHealth().

- **Enemy Projectile Generation**:
    - The `generateEnemyFire()` method ensures the boss fires projectiles during the level, adding another layer of complexity to the boss fight.

- **Level Initialization**:
    - The `instantiateLevelView()` method is overridden to create a specialized view that includes the boss's health and dynamic updates for player stats like health, bullet count, and coins collected.

### **Class: `LevelViewLevelTwo`**

#### **Modifications and Key Differences:**

- **Shield Integration**:
    - A `ShieldImage` is introduced, positioned at specific coordinates (`1150, 500`). This shield is part of the level's visual representation, and it can be shown or hidden depending on game events (such as player interaction or shield activation).
    - The `showShield()` and `hideShield()` methods control the visibility of the shield.
    - A new method, `updateShieldPosition(double xPosition, double yPosition)`, allows the shield's position to be dynamically updated, offering greater flexibility in gameplay.

- **Winning Parameter Display**:
    - The level view now includes a `DisplayWinningParameter` that shows the boss's health, player bullet count, and coin count. This provides the player with real-time statistics to track progress during the level.

- **Constructor Adjustments**:
    - The constructor has been updated to include parameters for the boss's health, bullet count, and coins collected, allowing the `LevelViewLevelTwo` to handle these dynamic statistics in addition to the player’s health.

### **Class: `GameOverImage`**

#### **Modifications and Key Differences:**
- **Class Removal**:
    - The `GameOverImage` class has been **removed**. It was previously responsible for displaying the "Game Over" image on the screen, but this functionality has been replaced and refactored in a different part of the code-in the levelLostOverlay in OverlayHandler class.

### **Class: `WinImage`**

#### **Modifications and Key Differences:**
- **Class Removal**:
    - The `WinImage` class has been **removed**. It was previously responsible for displaying the "You Win" image on the screen. Like the `GameOverImage`, its functionality is likely replaced and refactored in another part of the code-in the YouWin class.

### **Class: `DisplayHeart`**

#### **Modifications and Key Differences:**
- **Class Renamed:** Changed from `HeartDisplay` to `DisplayHeart` for consistency with other `Display*` classes.
- **Package Relocation:** Moved to `functionalClasses` for better modularity and separation of concerns.
- **Dynamic Heart Updates:** Replaced `initializeHearts()` and `removeHeart()` with `syncHeartsWithUserHealth(int)`, which dynamically updates the display based on user health by clearing and repopulating hearts.
- **Incremental Heart Addition:** Introduced `addHeart()` for flexible, incremental updates to the heart display.
- **Streamlined Logic:** Centralized heart management in `syncHeartsWithUserHealth()`, reducing redundancy and improving readability.

**Benefits:**
- Simplifies heart display updates, making it easier to sync with gameplay mechanics.
- Provides flexibility for dynamically adding or removing hearts based on health changes.
- Improved modularity and reusability by aligning with consistent naming and organizational practices.

### **Class: `Shield`**
#### **Modifications and Key Differences:**
**Modifications and Key Differences:**
- **Package Update:** Moved from `com.example.demo` to `com.example.demo.actors.additionalUnits` to reflect its role as an actor unit.
- **Image Path Updated:** Fixed resource path to `shield.png` to match consistent file naming.
- **Shield Size Adjusted:** Reduced shield size from 200 to 100 for better scaling within gameplay.
- **Position Update Method:** Added `updatePosition(double xPosition, double yPosition)` for dynamic movement of the shield based on in-game events, with an offset for better alignment.
- **Visibility Improvement:** `showShield()` now calls `toFront()` to ensure the shield remains on top of other elements when displayed.

**Benefits:**
- Improved modularity and code organization by relocating the class to a relevant package.
- Enhanced gameplay dynamics with the ability to reposition the shield dynamically.
- Better scaling for diverse resolutions with reduced and manageable shield size.
- Improved player experience by ensuring the shield remains visible when activated, regardless of overlapping UI elements.  