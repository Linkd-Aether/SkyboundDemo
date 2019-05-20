package com.skybound.demo.specialRects;

import com.badlogic.gdx.math.Rectangle;

public class Hitbox extends Rectangle {
	
	private boolean active = false;
	
	public Hitbox() {
		// TODO Auto-generated constructor stub
	}

	public Hitbox(Rectangle rect) {
		super(rect);
		// TODO Auto-generated constructor stub
	}

	public Hitbox(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void set(boolean active) {
		this.active = active;
	}
	
	public boolean get() {
		return active;
	}
}
