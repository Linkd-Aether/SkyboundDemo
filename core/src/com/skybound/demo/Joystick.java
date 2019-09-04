package com.skybound.demo;

import com.badlogic.gdx.controllers.Controllers;

public class Joystick {
	
	public static boolean joystickCheck(String dir) {
		
		if(!controllersActive()) return false;
		
		double y = Controllers.getControllers().get(0).getAxis(0);
		if(y < .2 && y > -.2) y = 0;
		
		double x = Controllers.getControllers().get(0).getAxis(1);
		if(x < .2 && x > -.2) x = 0;
		
		if(dir == "right" && x > y && x > 0) return true;
		if(dir == "left" && x < y && x < 0) return true;
		if(dir == "down" && y > x && y > 0) return true;
		if(dir == "up" && y < x && y < 0) return true;
		return false;
	}

	public static boolean controllersActive() {
		return !Controllers.getControllers().isEmpty();
	}
	
	/**
	 * 
	 * @param id ID of the button that you want to poll
	 * 
	 * IDs:
	 * 1 - a
	 * 2 - 
	 * 3 - x
	 * 4 - 
	 */
	public static boolean buttonCheck(int id) {
		return Controllers.getControllers().get(0).getButton(id);
	}
}
