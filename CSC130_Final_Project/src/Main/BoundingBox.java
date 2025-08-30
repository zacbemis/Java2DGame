package Main;



public class BoundingBox {
    // Position and dimensions
    private int x, y, width, height;
    // Identifier for this bounding box
    private String id;
    // Whether this bounding box is active (can be collided with)
    private boolean active;
    
    /**
     * @param x top left
     * @param y top left
     * @param width width of box
     * @param height height of box
     * @param id id for each box
     */
    public BoundingBox(int x, int y, int width, int height, String id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.active = true;
    }
    
    /**
     * @param other The other bounding box to check collision with
     * @return true if the boxes collide
     */
    public boolean collidesWith(BoundingBox other) {
        // If either box is inactive, no collision
        if (!this.active || !other.active) {
            return false;
        }
        
        // Check for intersection using the separating axis theorem
        return !(this.x + this.width <= other.x ||
                 other.x + other.width <= this.x ||
                 this.y + this.height <= other.y ||
                 other.y + other.height <= this.y);
    }

    public boolean containsPoint(int pointX, int pointY) {
        return this.active &&
               pointX >= this.x && pointX <= this.x + this.width &&
               pointY >= this.y && pointY <= this.y + this.height;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isActive() {
        return this.active;
    }
}
