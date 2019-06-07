package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.specialRects.Hitbox;

public class Fireball {

	Sprite spri;
	Texture txtr;
	public static Hitbox hit;
	boolean active = false;
	int ang;
	int spd;
	
	public Fireball(Texture txtr, Sprite spr) {
		hit = new Hitbox();
		spri = spr;
		spri.setOriginCenter();
	}
	
	public void set(int x, int y, int angle, int speed) {
		ang = angle;
		spd = speed;
	}

	public void update() {
		if(active) {
			spri.setRotation(ang);
			spri.translateX((float) (Math.cos(ang) * spd));
			spri.translateY((float) (Math.sin(ang) * spd));
			hit.setActive(active);
			hit.set(spri.getX(), spri.getY(), spri.getWidth(), spri.getHeight());
		}
	}
	
	public Sprite getSprite() {
		return spri;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean act) {
		active = act;
	}
}
