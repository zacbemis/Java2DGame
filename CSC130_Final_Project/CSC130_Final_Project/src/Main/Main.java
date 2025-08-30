package Main;

import java.awt.Color;
import logic.Control;
import timer.stopWatchX;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import FileIO.EZFileRead;

public class Main{
	// Fields (Static) below...
	public static stopWatchX timer = new stopWatchX(4000);
	public static EZFileRead ezr = new EZFileRead("Diamond-Man_VoiceLines.txt");
	public static Map<String, String> voicelinesHM = new HashMap<>();
	public static int counter = 1;
	public static String trigger = "";
	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control();			// Do NOT remove!
		ctrl.gameLoop();						// Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)


	}

	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)


		ctrl.drawString(100, 250, trigger, Color.white);

	}

	// Additional Static methods below...(if needed)

}