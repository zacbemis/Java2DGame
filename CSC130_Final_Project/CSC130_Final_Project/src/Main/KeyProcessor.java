/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ') {
			Main.trigger = "";
			return;
		}
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
			case '%':								// ESC key
				System.exit(0);
				break;

			case '$':
				Main.trigger = "space is pressed";
				break;

			case 'a':
				Main.trigger = "a is pressed";
				break;

			case 'w':
				Main.trigger = "w is pressed";
				break;

			case 's':
				Main.trigger = "s is pressed";
				break;

			case 'd':
				Main.trigger = "d is pressed";
				break;
			
			case 'm':
				// For mouse coordinates
				Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
				break;
		}
	}
}