package Main;

import java.awt.*;
import java.util.ArrayList;
import logic.Control;

/**
 * Bounding boxes class is called BoundingBoxes
 * Bounding boxes are held in ArrayList in Main
 * Collisions are handled by CollisionManager
 * Interactables (I know I spelled it wrong in the program, but I was too deep into it already) have their own class
 * Interactables are held in an ArrayList in Main
 * Player class handles synchronisation between movement and animation (this is where you can find the ArrayList for the animation sprites)
 * I enjoyed your class thanks for the great semester! :)
 */
public class Main{
	// Fields (Static) below...
	public static String trigger = "";
	// Game-specific fields
	public static Player player;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int PLAYER_SPEED = 5;
	// Collision system
	public static CollisionManager collisionManager;
	// Interactible objects
	public static ArrayList<InteractibleObject> interactibleObjects;
	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control();			// Do NOT remove!
		start();							// Initialize game objects
		ctrl.gameLoop();					// Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// Initialize the player in the middle of the screen
		player = new Player(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, PLAYER_SPEED);
		
		// Initialize the collision manager
		collisionManager = new CollisionManager();

		// Initialize interactible objects list
		interactibleObjects = new ArrayList<>();

		// Add boxes into collision manager
		createBoxes();
		
		// Create interactible objects
		createInteractibleObjects();
	}

	/* Methods used for start */

	private static void createBoxes() {
		// Screen boundary bounding boxes
		// Top boundary (slightly above the screen)
		BoundingBox topBoundary = new BoundingBox(-100, -20, SCREEN_WIDTH + 200, 20, "boundary_top");
		// Bottom boundary
		BoundingBox bottomBoundary = new BoundingBox(-100, SCREEN_HEIGHT - 125, SCREEN_WIDTH + 200, 20, "boundary_bottom");
		// Left boundary
		BoundingBox leftBoundary = new BoundingBox(20, -110, 20, SCREEN_HEIGHT + 200, "boundary_left");
		// Right boundary
		BoundingBox rightBoundary = new BoundingBox(SCREEN_WIDTH - 140, -100, 20, SCREEN_HEIGHT + 200, "boundary_right");

		// Add boxes to the collision manager
		collisionManager.addBoundingBox(topBoundary);
		collisionManager.addBoundingBox(bottomBoundary);
		collisionManager.addBoundingBox(leftBoundary);
		collisionManager.addBoundingBox(rightBoundary);

		// Add the player's bounding box to the collision manager
		collisionManager.addBoundingBox(player.boundingBox);
	}
	
	private static void createInteractibleObjects() {
		// Create some example interactible objects
		// Parameters: x, y, width, height, id, text, tag, disappearOnInteract, offsetX, offsetY
		InteractibleObject key = new InteractibleObject(380, 280, 20, 50, "key", "You found a key!", "IN1", true, 0, 0);
		
		// Create door with offset bounding box (10 pixels to the right, 5 pixels down)
		InteractibleObject door = new InteractibleObject(600, 500, 70, 70, "door", "The key doesn't work", "IN2", false, 15, -5);

		// Add their bounding boxes to the collision manager
		collisionManager.addBoundingBox(key.boundingBox);
		collisionManager.addBoundingBox(door.boundingBox);
		
		// Add them to our list
		interactibleObjects.add(key);
		interactibleObjects.add(door);
	}

	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// Draw the background first
		ctrl.addSpriteToFrontBuffer(0, 0, "BG");

		// Print Message
		ctrl.drawString(30, 40, "Oh No! You have found yourself trapped in a cell!", Color.white);
		ctrl.drawString(30, 60, "How will you escape?", Color.white);

		// Handle player movement with WASD keys
		boolean moved = false;
		int dx = 0, dy = 0;

		// Store the current position to revert to for collision detection
		int oldX = player.getX();
		int oldY = player.getY();

		// W key - move up
		if(KeyProcessor.isWKeyDown()) {
			dy = -player.speed;
			player.dir = 3; // Up direction
			moved = true;
		}
		// S key - move down
		if(KeyProcessor.isSKeyDown()) {
			dy = player.speed;
			player.dir = 0; // Down direction
			moved = true;
		}
		// A key - move left
		if(KeyProcessor.isAKeyDown()) {
			dx = -player.speed;
			player.dir = 1; // Left direction
			moved = true;
		}
		// D key - move right
		if(KeyProcessor.isDKeyDown()) {
			dx = player.speed;
			player.dir = 2; // Right direction
			moved = true;
		}

		// Update player position using the move method
		moved = player.move(dx, dy);

		// Update the player's bounding box position
		player.updateBoundingBox();

		// Check for collisions with obstacles
		ArrayList<BoundingBox> collisions = collisionManager.getAllCollisions(player.boundingBox);

		// If there's a collision, revert to the old position
		if (!collisions.isEmpty()) {
			// Revert position
			player.setX(oldX);
			player.setY(oldY);
			player.updateBoundingBox();
		}
		
		// Check for spacebar interaction
		boolean spacePressed = trigger.equals("space is pressed");
		if (spacePressed) {
			trigger = ""; // Reset trigger after processing
		}
		
		// Update player animation based on movement
		player.updateAnimation(moved);

		// Draw the player with the current sprite
		try {
			ctrl.addSpriteToFrontBuffer(player.getX(), player.getY(), player.getSpriteInfo().getTag());
		} catch (Exception e) {
			// If there's an error with the sprite, use the default one
			ctrl.addSpriteToFrontBuffer(player.getX(), player.getY(), "f1");
		}
		
		// Update and handle interactions with all interactible objects
		for (InteractibleObject obj : interactibleObjects) {
			// Handle interaction if space was pressed
			obj.handleInteraction(player, spacePressed);
			// Update and draw the object
			obj.update(ctrl);
		}
	}
}