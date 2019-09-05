package com.skybound.demo.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.skybound.demo.Joystick;

public class MenuHandler {

	private boolean listening = false;
	
	public MenuHandler() {
		listening = true;
	}
	
	public int poll() {
		int result = 0;

		if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Joystick.joystickCheck("up")) {
			result = 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Joystick.joystickCheck("down")) {
			result = 2;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.L) || Joystick.buttonCheck(1)) {
			result = 3;
		}
		
		return result;
	}
	
	public boolean getBoolean() {
		return listening;
	}

}
