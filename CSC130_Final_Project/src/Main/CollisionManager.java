package Main;

import java.util.ArrayList;

public class CollisionManager {
    // Collection of bounding boxes
    private ArrayList<BoundingBox> boundingBoxes;
    // Whether to draw debug visuals for bounding boxes

    public CollisionManager() {
        this.boundingBoxes = new ArrayList<>();
    }
    
    /**
     * @param box The bounding box to add
     * @return The index of the added bounding box
     */
    public int addBoundingBox(BoundingBox box) {
        this.boundingBoxes.add(box);
        return this.boundingBoxes.size() - 1;
    }
    
    /**
     * @param index The index of the bounding box to remove
     */
    public void removeBoundingBox(int index) {
        if (index >= 0 && index < this.boundingBoxes.size()) {
            this.boundingBoxes.remove(index);
        }
    }
    
    /**
     * @param id The ID of the bounding box to remove
     */
    public void removeBoundingBoxById(String id) {
        for (int i = 0; i < this.boundingBoxes.size(); i++) {
            if (this.boundingBoxes.get(i).getId().equals(id)) {
                this.boundingBoxes.remove(i);
                return;
            }
        }
    }

    /**
     * @param index of box
     * @return bounding box at index
     */
    public BoundingBox getBoundingBox(int index) {
        if (index >= 0 && index < this.boundingBoxes.size()) {
            return this.boundingBoxes.get(index);
        }
        return null;
    }

    /**
     * @param id id of box
     * @return bounding box with id
     */
    public BoundingBox getBoundingBoxById(String id) {
        for (BoundingBox box : this.boundingBoxes) {
            if (box.getId().equals(id)) {
                return box;
            }
        }
        return null;
    }
    
    /**
     * @param box The bounding box to check
     * @return The first bounding box that collides with the given box, or null if none
     */
    public BoundingBox checkCollision(BoundingBox box) {
        for (BoundingBox other : this.boundingBoxes) {
            if (box != other && box.collidesWith(other)) {
                return other;
            }
        }
        return null;
    }
    
    /**
     * @param index The index of the bounding box to check
     * @return The first bounding box that collides with the given box
     */
    public BoundingBox checkCollision(int index) {
        if (index >= 0 && index < this.boundingBoxes.size()) {
            return checkCollision(this.boundingBoxes.get(index));
        }
        return null;
    }
    
    /**
     * @param box The bounding box to check
     * @return An ArrayList of all colliding
     */
    public ArrayList<BoundingBox> getAllCollisions(BoundingBox box) {
        ArrayList<BoundingBox> collisions = new ArrayList<>();
        for (BoundingBox other : this.boundingBoxes) {
            if (box != other && box.collidesWith(other)) {
                collisions.add(other);
            }
        }
        return collisions;
    }
    
    /**
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @return The bounding box that contains the point
     */
    public BoundingBox checkPointCollision(int x, int y) {
        for (BoundingBox box : this.boundingBoxes) {
            if (box.containsPoint(x, y)) {
                return box;
            }
        }
        return null;
    }

    public int getCount() {
        return this.boundingBoxes.size();
    }

    public void clear() {
        this.boundingBoxes.clear();
    }
}
