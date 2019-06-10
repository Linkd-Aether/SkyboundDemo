package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.specialRects.Hitbox;

public class Fireball {

	Sprite spri;
	Texture txtr;
	public static Hitbox hit;
	static boolean active = false;
	int ang;
	int spd;
	
	public Fireball(Texture txtr, Sprite spr) {
		hit = new Hitbox();
		spri = spr;
		spri.setBounds(spri.getX(), spri.getY(), 100, 70);
		spri.setOriginCenter();
	}
	
	public void set(int x, int y, int angle, int speed) {
		ang = angle;
		spd = speed;
		spri.setX(x);
		spri.setY(y);
	}

	public void update() {
		if(active) {
			spri.setRotation(ang);
			spri.translateX((float) (Math.cos(ang * (Math.PI / 180)) * spd));
			spri.translateY((float) (Math.sin(ang * (Math.PI / 180)) * spd));
			hit.set(spri.getX(), spri.getY(), spri.getWidth(), spri.getHeight());
		}
		if(spri.getX() >= 600 || spri.getX() <= -100 || spri.getY() >= 500 || spri.getY() <= -100) active = false;
		hit.setActive(active);
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
