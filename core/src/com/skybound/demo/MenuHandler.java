package com.skybound.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MenuHandler {

	private boolean listening = false;
	
	public MenuHandler() {
		listening = true;
	}
	
	public int poll() {
		int result = 0;

		if(Joystick.joystickCheck("up")) {
			result = 1;
		}
		if(Joystick.joystickCheck("down")) {
			result = 2;
		}
		if(Joystick.buttonCheck(1)) {
			result = 3;
		}
		
		
		return result;
	}
	
	public boolean getBoolean() {
		return listening;
	}

}
