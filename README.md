# Java Game Framework – SameGame & Sokoban

## About the Project

This project is an object-oriented Java game framework developed as part of an Advanced Object-Oriented Programming course.

The main goal was to design a reusable architecture for grid-based games using object-oriented programming principles and software design patterns. The framework is demonstrated through two playable games: **SameGame** and **Sokoban**.

The application includes graphical and console-based views, different input controllers, game-state observers, persistent high scores and unit testing.

## Games

### SameGame
A tile-based puzzle game where groups of connected tiles with the same color are removed from the board.

The implementation includes:

- Multiple difficulty levels with different board sizes and number of colors
- Automatic gravity and column shifting after tiles are removed
- Score calculation based on removed tile groups
- Detection of available moves and game-over conditions
- Hint system that suggests the largest available tile group
- Persistent high-score storage

### Sokoban
A second grid-based puzzle game implemented using the same reusable game framework.

The implementation includes:

- Keyboard-controlled player movement
- Crate pushing mechanics
- Goals and obstacles
- Win-condition detection
- Detection of crates stuck in corners
- Move tracking

## Design & Architecture

The project uses a reusable architecture based on object-oriented design principles.

### Model-View-Controller (MVC)
Game logic, graphical presentation and user input are separated into different components.

### Observer Pattern
Game views and other components can register as observers of the game model and are automatically notified when the game state changes.

Examples include:

- Swing graphical view
- Console view
- Sound observer

### Reusable Game Framework
Both SameGame and Sokoban share common framework components such as:

- `GameModel`
- `AbstractGridGameModel`
- `GameObserver`
- `Position`

This allows common functionality such as board management, scoring and observer handling to be reused between different games.

## Additional Features

- Java Swing graphical user interface
- Mouse controls for SameGame
- Keyboard controls for Sokoban
- Multiple difficulty levels
- Hint system
- Sound notifications
- High-score persistence using Java serialization
- Console-based game representation
- JUnit unit testing

## Testing

JUnit tests are included for important parts of the SameGame model, including:

- Board dimensions
- Game reset behavior
- Difficulty configuration

## Technologies

- Java
- Object-Oriented Programming (OOP)
- Java Swing
- MVC architecture
- Observer Pattern
- Generic types
- Java Serialization
- JUnit
- Event-driven programming

## Project Structure

```text
src/
├── controller/    # User input handling
├── framework/     # Reusable game framework
├── highscore/     # High-score persistence
├── model/         # SameGame model and game logic
├── sokoban/       # Sokoban implementation
├── view/          # Graphical, console and sound views
└── Main.java      # Application entry point

test/
└── SameGameModelTest.java
