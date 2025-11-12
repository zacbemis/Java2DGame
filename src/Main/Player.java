package Main;

import Data.Vector2D;
import Data.spriteInfo;
import timer.stopWatchX;
import java.util.ArrayList;

public class Player {
    // Position and movement using Vector2D
    private Vector2D position;
    public int speed, dir;

    // Animation state
    public int animFrame;
    private int animDirection; // 1 for forward, -1 for backward

    private stopWatchX animTimer; // Timer for animation updates

    // Sprite information
    private spriteInfo sprite;

    // Collision detection
    public BoundingBox boundingBox;

    // Movement timer
    private stopWatchX moveTimer;
    private boolean canMove;

    // Animation sprite lists for each direction
    private ArrayList<String> rightSprites;
    private ArrayList<String> leftSprites;
    private ArrayList<String> frontSprites;
    private ArrayList<String> backSprites;
    private String standingSprite;
    
    /**
     * @param x
     * @param y
     * @param speed The movement speed
     */
    public Player(int x, int y, int speed) {
        this.position = new Vector2D(x, y);
        this.speed = speed;
        this.dir = 0; // Default direction (down)
        this.animFrame = 0;
        this.animDirection = 1; // Start with forward animation
        
        // Initialize sprite info with default sprite tag
        this.standingSprite = "f1";
        this.sprite = new spriteInfo(position, standingSprite);
        
        // Create a bounding box for the player with size 64x64
        this.boundingBox = new BoundingBox(x, y, 64, 64, "player");
        
        // Initialize movement timer - higher speed means lower delay
        updateMoveTimer();
        
        // Initialize animation timer (75ms between animation updates)
        this.animTimer = new stopWatchX(75);
        
        // Initialize animation sprite lists
        initializeSpriteLists();
    }
    
    /**
     * Initialize the sprite lists for each direction
     */
    private void initializeSpriteLists() {
        // Right-facing sprites
        rightSprites = new ArrayList<>();
        rightSprites.add("1R");
        rightSprites.add("2R");
        rightSprites.add("3R");
        rightSprites.add("4R");
        
        // Left-facing sprites
        leftSprites = new ArrayList<>();
        leftSprites.add("1L");
        leftSprites.add("2L");
        leftSprites.add("3L");
        leftSprites.add("4L");
        
        // Front-facing sprites
        frontSprites = new ArrayList<>();
        frontSprites.add("1F");
        frontSprites.add("2F");
        frontSprites.add("3F");
        frontSprites.add("4F");
        
        // Back-facing sprites
        backSprites = new ArrayList<>();
        backSprites.add("1B");
        backSprites.add("2B");
        backSprites.add("3B");
        backSprites.add("4B");
    }
    
    /**
     * Updates the movement timer based on the current speed
     */
    public void updateMoveTimer() {
        // Convert speed to movement delay (higher speed = lower delay)
        // Base delay of 100ms divided by speed
        int moveDelay = Math.max(1, 100 / speed);
        this.moveTimer = new stopWatchX(moveDelay);
        this.canMove = true;
    }
    
    /**
     * Updates the player's bounding box position to match the player's position
     */
    public void updateBoundingBox() {
        this.boundingBox.updatePosition(position.getX(), position.getY());
    }

    /**
     * Getters and Setters
     */
    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public void setX(int x) {
        position.setX(x);
        sprite.setCoords(position);
    }

    public void setY(int y) {
        position.setY(y);
        sprite.setCoords(position);
    }
    
    /**
     * Sets the player's speed and updates the movement timer
     * @param speed The new speed value
     */
    public void setSpeed(int speed) {
        this.speed = speed;
        updateMoveTimer();
    }
    
    /**
     * Checks if the player can move based on the movement timer
     * @return true if the player can move, false otherwise
     */
    public boolean checkMovement() {
        if (moveTimer.isTimeUp()) {
            moveTimer.resetWatch();
            return true;
        }
        return false;
    }
    
    /**
     * @param dx The amount to move in the x direction
     * @param dy The amount to move in the y direction
     * @return true if the player actually moved, false otherwise
     */
    public boolean move(int dx, int dy) {
        // Check if there's any movement input
        if (dx == 0 && dy == 0) {
            return false;
        }
        
        // Check if we can move based on the timer
        if (checkMovement()) {
            position.adjustX(dx);
            position.adjustY(dy);
            sprite.setCoords(position);
            return true;
        }
        
        // Even if we didn't actually move due to the timer,
        // we still want to return true for animation purposes
        // if there was movement input
        return true;
    }
    
    /**
     * @return The spriteInfo object
     */
    public spriteInfo getSpriteInfo() {
        return sprite;
    }
    
    /**
     * Updates the sprite animation based on character movement
     * @param moved Whether the player moved this frame
     */
    public void updateAnimation(boolean moved) {
        // Default to standing sprite if not moving
        if (!moved) {
            this.sprite.setTag(standingSprite);
            return;
        }
        
        // Only update animation frame if the timer is up
        if (!animTimer.isTimeUp()) {
            return;
        }
        
        // Reset timer
        animTimer.resetWatch();
        
        // Get the appropriate sprite list based on direction
        ArrayList<String> sprites;
        switch (dir) {
            case 0: // Down/Front
                sprites = frontSprites;
                updateBackAndForthAnimation(sprites);
                break;
            case 1: // Left
                sprites = leftSprites;
                updateBackAndForthAnimation(sprites);
                break;
            case 2: // Right
                sprites = rightSprites;
                updateBackAndForthAnimation(sprites);
                break;
            case 3: // Up/Back
                sprites = backSprites;
                updateBackAndForthAnimation(sprites);
                break;
            default:
                sprites = frontSprites;
                animFrame = (animFrame + 1) % sprites.size();
                break;
        }
        
        // Update the sprite tag
        this.sprite.setTag(sprites.get(animFrame));
    }
    
    /**
     * Helper method to make the animation loop correctly
     * @param sprites The list of sprites for the current direction
     */
    private void updateBackAndForthAnimation(ArrayList<String> sprites) {
        // Update frame with direction
        animFrame += animDirection;
        
        // Reverse direction at endpoints
        if (animFrame >= sprites.size() - 1) {
            animFrame = sprites.size() - 1;
            animDirection = -1;
        } else if (animFrame <= 0) {
            animFrame = 0;
            animDirection = 1;
        }
    }
}
