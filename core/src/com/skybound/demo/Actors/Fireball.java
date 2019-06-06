package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.specialRects.Hitbox;

public class Fireball {

	
	Texture txtr;
	public static Hitbox hit;
	int ang;
	int spd;
	
	public Fireball(Texture txtr, Sprite spr) {
		hit = new Hitbox();
	}
	
	public void set(int x, int y, int angle, int speed) {
		ang = angle;
		spd = speed;
		
	}

}
