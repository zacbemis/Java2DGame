package Main;

import Data.Vector2D;
import Data.spriteInfo;
import timer.stopWatchX;
import java.awt.Color;
import logic.Control;

public class InteractibleObject {
    // Position using Vector2D
    private Vector2D position;
    
    // Sprite information
    private spriteInfo sprite;
    
    // Collision detection
    public BoundingBox boundingBox;
    
    // Text to display when interacted with
    private String interactionText;
    
    // Whether the object is still active
    private boolean active;
    
    // Timer for displaying the text
    private stopWatchX textDisplayTimer;
    private boolean showingText;
    
    // How long to show the text
    private static final int TEXT_DISPLAY_TIME = 3000;

    // Whether this object should disappear after interaction
    private boolean disappearOnInteract;
    
    // Offset for the bounding box relative to the sprite position
    private int offsetX;
    private int offsetY;
    
    /**
     * Creates a new interactible object
     * 
     * @param x The x position
     * @param y The y position
     * @param width The width of the hitbox
     * @param height The height of the hitbox
     * @param id The unique identifier for this object
     * @param text The text to display when interacted with
     * @param tag The sprite tag to use for this object
     * @param disappearOnInteract Whether this object should disappear after interaction
     */
    public InteractibleObject(int x, int y, int width, int height, String id, String text, String tag, boolean disappearOnInteract) {
        this(x, y, width, height, id, text, tag, disappearOnInteract, 0, 0);
    }
    
    /**
     * Creates a new interactible object with bounding box offsets
     * 
     * @param x The x position
     * @param y The y position
     * @param width The width of the hitbox
     * @param height The height of the hitbox
     * @param id The unique identifier for this object
     * @param text The text to display when interacted with
     * @param tag The sprite tag to use for this object
     * @param disappearOnInteract Whether this object should disappear after interaction
     * @param offsetX The x offset for the bounding box relative to the sprite position
     * @param offsetY The y offset for the bounding box relative to the sprite position
     */
    public InteractibleObject(int x, int y, int width, int height, String id, String text, String tag, boolean disappearOnInteract, int offsetX, int offsetY) {
        this.position = new Vector2D(x, y);
        this.sprite = new spriteInfo(position, tag);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.boundingBox = new BoundingBox(x + offsetX, y + offsetY, width, height, "interactible_" + id);
        this.interactionText = text;
        this.active = true;
        this.showingText = false;
        this.textDisplayTimer = new stopWatchX(TEXT_DISPLAY_TIME);
        this.disappearOnInteract = disappearOnInteract;
    }
    
    /**
     * Checks if the player can interact with this object based on their position and direction
     * 
     * @param player The player object
     * @return true if the player can interact with this object
     */
    public boolean canInteract(Player player) {
        if (!active) {
            return false; // Can't interact if the object is not active
        }
        
        // Check if player is close enough to the object
        if (!isPlayerNearby(player)) {
            return false;
        }
        
        // Check if player is facing the object
        return isPlayerFacing(player);
    }
    
    /**
     * Checks if the player is nearby (within interaction range)
     * 
     * @param player The player object
     * @return true if the player is nearby
     */
    private boolean isPlayerNearby(Player player) {
        // Use a larger interaction range
        int interactionRange = 80; // Pixels
        
        int playerX = player.getX();
        int playerY = player.getY();
        int objectX = this.position.getX();
        int objectY = this.position.getY();
        
        // Calculate distance between player and object
        int distanceX = Math.abs(playerX - objectX);
        int distanceY = Math.abs(playerY - objectY);
        
        return distanceX < interactionRange && distanceY < interactionRange;
    }
    
    /**
     * Checks if the player is facing this object based on their direction
     * 
     * @param player The player object
     * @return true if the player is facing this object
     */
    private boolean isPlayerFacing(Player player) {
        int playerX = player.getX();
        int playerY = player.getY();
        int objectX = this.position.getX();
        int objectY = this.position.getY();
        
        // Check player direction (0=down, 1=left, 2=right, 3=up)
        switch (player.dir) {
            case 0: // Down
                // Object is below player (y increases downward)
                return playerY < objectY;
            case 1: // Left
                // Object is to the left of player (x decreases leftward)
                return playerX > objectX;
            case 2: // Right
                // Object is to the right of player (x increases rightward)
                return playerX < objectX;
            case 3: // Up
                // Object is above player (y decreases upward)
                return playerY > objectY;
            default:
                return false;
        }
    }
    
    /**
     * Interact with this object
     * 
     * @return The text to display
     */
    public String interact() {
        if (!active) {
            return ""; // Can't interact if the object is not active
        }
        
        // Deactivate the object (make it disappear) only if it should disappear on interact
        if (disappearOnInteract) {
            this.active = false;
            this.boundingBox.setActive(false);
        }
        
        // Start showing the text
        this.showingText = true;
        this.textDisplayTimer.resetWatch();
        
        return this.interactionText;
    }
    
    /**
     * Updates the object's state and draws it if needed
     * 
     * @param ctrl The Control object for rendering
     * @return true if the text is still being displayed
     */
    public boolean update(Control ctrl) {
        // Check if text display timer is up
        if (showingText && textDisplayTimer.isTimeUp()) {
            showingText = false;
        }
        
        // Draw the object if it's active
        if (active) {
            // Calculate the center position of the bounding box
            int centerX = boundingBox.getX() + boundingBox.getWidth() / 2 - offsetX;
            int centerY = boundingBox.getY() + boundingBox.getHeight() / 2 - offsetY;
            
            // Draw the sprite centered on the bounding box
            ctrl.addSpriteToFrontBuffer(centerX, centerY, sprite.getTag());
        }
        
        // Draw the text if we're showing it
        if (showingText) {
            ctrl.drawString(position.getX(), position.getY() - 20, interactionText, Color.yellow);
        }
        
        return showingText;
    }
    
    /**
     * Handle interaction with this object if the spacebar is pressed
     * 
     * @param player The player object
     * @param spacePressed Whether the space key is pressed
     * @return true if an interaction occurred
     */
    public boolean handleInteraction(Player player, boolean spacePressed) {
        if (spacePressed && canInteract(player)) {
            interact();
            return true;
        }
        return false;
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
    
    public spriteInfo getSpriteInfo() {
        return sprite;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public boolean isShowingText() {
        return showingText;
    }
    
    public String getInteractionText() {
        return interactionText;
    }
    
    public boolean shouldDisappearOnInteract() {
        return disappearOnInteract;
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }
}
