/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Key state tracking for movement
	private static boolean wKeyDown = false;
	private static boolean aKeyDown = false;
	private static boolean sKeyDown = false;
	private static boolean dKeyDown = false;
	private static boolean spaceKeyDown = false;
	
	// Static Method(s)
	public static void processKey(char key){
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false) return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
			case '%':								// ESC key
				System.exit(0);
				break;

			case '$':								// Space key
				spaceKeyDown = true;
				Main.trigger = "space is pressed";
				break;

			case 'a':
				aKeyDown = true;
				Main.trigger = "a is pressed";
				break;
				
			case 'A': // Key released
				aKeyDown = false;
				break;

			case 'w':
				wKeyDown = true;
				Main.trigger = "w is pressed";
				break;
				
			case 'W': // Key released
				wKeyDown = false;
				break;

			case 's':
				sKeyDown = true;
				Main.trigger = "s is pressed";
				break;
				
			case 'S': // Key released
				sKeyDown = false;
				break;

			case 'd':
				dKeyDown = true;
				Main.trigger = "d is pressed";
				break;
				
			case 'D': // Key released
				dKeyDown = false;
				break;
				
			case ' ': // No key pressed - reset all key states
				wKeyDown = false;
				aKeyDown = false;
				sKeyDown = false;
				dKeyDown = false;
				spaceKeyDown = false;
				Main.trigger = "";
				break;
		}
	}
	
	// Getter methods for key states
	public static boolean isWKeyDown() {
		return wKeyDown;
	}
	
	public static boolean isAKeyDown() {
		return aKeyDown;
	}
	
	public static boolean isSKeyDown() {
		return sKeyDown;
	}
	
	public static boolean isDKeyDown() {
		return dKeyDown;
	}
	
	public static boolean isSpaceKeyDown() {
		return spaceKeyDown;
	}
}