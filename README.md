# Java 2D Game - Diamond Man Escape

A 2D top-down adventure game developed in Java as a CSC130 Final Project. This project demonstrates fundamental game development principles through the implementation of a custom game engine with sprite-based rendering, collision detection, and interactive gameplay mechanics.

## Project Overview

Diamond Man Escape is an escape room-style game where the player must navigate a confined space, interact with objects, and solve puzzles to escape. The project showcases object-oriented programming principles, real-time game loop architecture, and modular system design.
<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/f8afbe06-6de5-47c7-8018-dcb2129024ad" />


## Technologies Used

### Core Technologies
- **Java SE 8+**: Primary programming language
- **Java AWT (Abstract Window Toolkit)**: Graphics rendering and display management
- **Java Swing**: Window management and UI components
- **Custom Game Engine**: Built from scratch without external game frameworks

### Key Java APIs & Libraries
- `java.awt.Graphics2D`: 2D graphics rendering
- `java.awt.event.KeyListener`: Keyboard input handling
- `java.util.ArrayList`: Dynamic data structure for game objects
- `java.awt.Color`: Color management for UI elements
- `javax.imageio.ImageIO`: Sprite and asset loading

### Development Tools
- **IntelliJ IDEA**: IDE configuration (`.iml` project file)
- **Git**: Version control
- **PNG Format**: Sprite assets and graphics

## Project Structure

```
Java2DGame/
├── src/
│   ├── Main/
│   │   ├── Main.java              # Entry point and game loop
│   │   ├── Player.java            # Player character with animations
│   │   ├── BoundingBox.java       # Collision box implementation
│   │   ├── CollisionManager.java  # Handles collision detection
│   │   ├── InteractibleObject.java # Interactive game objects
│   │   └── KeyProcessor.java      # Keyboard input handling
│   ├── logic/
│   │   └── Control.java           # Game loop controller
│   ├── Graphics/
│   │   ├── Graphic.java           # Graphics rendering
│   │   └── Sprites.java           # Sprite management
│   ├── Data/
│   │   ├── Vector2D.java          # 2D vector math
│   │   ├── spriteInfo.java        # Sprite metadata
│   │   └── Sprite.java            # Sprite data structure
│   ├── Input/
│   │   └── Keyb.java              # Keyboard input system
│   ├── timer/
│   │   └── stopWatchX.java        # Game timing utilities
│   └── FileIO/
│       ├── EZFileRead.java        # File reading utilities
│       └── EZFileWrite.java       # File writing utilities
├── Art/                           # Game sprites and graphics
├── Font/                          # Game fonts
├── Art.txt                        # Sprite mapping configuration
└── Diamond-Man_VoiceLines.txt     # Character dialogue
```

## Core Features & Implementation

### 1. Custom Game Loop Architecture
- **Fixed Timestep Loop**: Ensures consistent gameplay across different hardware
- **Separation of Concerns**: Update logic separated from rendering
- **Callback Pattern**: Main game logic implemented through callback methods in `Control` class
- **Frame-rate Independent**: Timer-based updates for smooth animation

### 2. Collision Detection System
- **AABB (Axis-Aligned Bounding Box)**: Efficient rectangle-based collision detection
- **Centralized Management**: `CollisionManager` class handles all collision queries
- **Dynamic Collision Response**: Position reversion prevents clipping through walls
- **Boundary System**: Screen edges implemented as collision boxes
- **Offset Support**: Customizable collision box positioning for precise hitboxes

### 3. Sprite Animation Engine
- **Multi-directional Animation**: 16 total sprites (4 frames × 4 directions)
- **State Machine**: Direction-based sprite selection (0=down, 1=left, 2=right, 3=up)
- **Ping-pong Animation**: Back-and-forth frame cycling for natural walking motion
- **Timer-based Updates**: 75ms frame intervals for smooth animation
- **Idle State Handling**: Automatic return to standing sprite when stationary

### 4. Input System
- **Event-driven Architecture**: Java KeyListener for real-time input
- **WASD Movement**: Standard PC gaming controls
- **Interaction System**: Spacebar trigger for object interaction
- **Input Buffering**: Trigger system prevents duplicate interactions

### 5. Object-Oriented Design Patterns
- **Component-based Entities**: Player and objects use composition (position, bounding box, sprite)
- **Vector Mathematics**: Custom `Vector2D` class for position management
- **Data Encapsulation**: Sprite information separated into dedicated classes
- **Manager Pattern**: Centralized collision and sprite management

## Technical Specifications

- **Screen Resolution**: 1280×720 pixels
- **Player Speed**: 5 pixels per frame
- **Animation Rate**: 75ms per frame (~13 FPS animation)
- **Collision Box Size**: 64×64 pixels
- **Coordinate System**: Top-left origin (standard screen coordinates)

## Gameplay

### Controls
- **W** - Move up
- **A** - Move left  
- **S** - Move down
- **D** - Move right
- **SPACE** - Interact with objects

### Objective
Navigate Diamond Man through the cell, collect items, and interact with objects to find a way to escape.

## How to Run

### Prerequisites
- **Java Development Kit (JDK) 8 or higher**
- Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or command line tools

### Running from IDE
1. Open the project in your IDE
2. Ensure the `src` folder is marked as the source root
3. Run `Main.java` from the `Main` package
4. The game window will open at 1280×720 resolution

### Running from Command Line
```bash
# Navigate to the project directory
cd Java2DGame

# Compile all source files
javac -d bin src/**/*.java

# Run the game
java -cp bin Main.Main
```

### System Requirements
- **OS**: Windows, macOS, or Linux
- **Java**: JDK/JRE 8 or higher
- **Display**: Minimum 1280×720 resolution
- **Input**: Keyboard required

## Architecture & Key Components

### Class Hierarchy

#### Core Game Classes
- **`Main.java`**: Entry point, game initialization, and main update loop callback
- **`Control.java`**: Game loop controller managing timing and rendering pipeline
- **`Player.java`**: Player entity with state management, movement logic, and animation controller

#### System Classes
- **`CollisionManager.java`**: Centralized collision detection using spatial queries
- **`BoundingBox.java`**: Rectangle collision primitive with position and dimension data
- **`InteractibleObject.java`**: Base class for interactive game elements with trigger system
- **`KeyProcessor.java`**: Input handler translating keyboard events to game actions

#### Utility Classes
- **`Vector2D.java`**: 2D vector mathematics for position and movement calculations
- **`spriteInfo.java`**: Sprite metadata container linking graphics to game objects
- **`stopWatchX.java`**: High-precision timer for animation and movement timing
- **`Sprites.java`**: Sprite loading and management system

### Asset Management
- **Sprite Loading**: Text-based configuration (`Art.txt`) maps file paths to sprite tags
- **Format**: `filepath*tag` (e.g., `Art/DiamondMan.png*f1`)
- **16 Character Sprites**: 4-frame animations for each cardinal direction
- **Environment Assets**: Background, key, door sprites

## Learning Outcomes

This project demonstrates proficiency in:
- **Game Loop Implementation**: Understanding of real-time update/render cycles
- **Collision Detection Algorithms**: AABB implementation and spatial management
- **State Management**: Handling game state, player state, and animation states
- **Event-driven Programming**: Keyboard input handling and interaction triggers
- **Object-Oriented Design**: Encapsulation, composition, and separation of concerns
- **Custom Engine Development**: Building game systems without external frameworks
- **Resource Management**: Loading and managing sprites and game assets

## Credits

Created as a CSC130 Final Project. Special thanks to the course instructor for a great semester!

## License

This project was created for educational purposes.
