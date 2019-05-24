package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.specialRects.Hitbox;

public class Enemy implements ActorGeneric {

	Texture txtr;
	Hitbox hit;
	Sprite spri;
	boolean inAir = false;
	boolean facingRight = true;
	double airMomentum;
	int framesSinceLastAction = 0;
	int currentFrameTarget = 0;
	int hitX = 0;
	int hitY = 0;
	
	public Enemy(Texture txt, Sprite spr) {
		txtr = txt;
		spri = spr;
		hit = new Hitbox();
		spri.setBounds(spri.getX(), spri.getY(), 200, 200);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void changeSprite(String toChange) {
		txtr = new Texture(toChange);
		spri.setTexture(txtr);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public Sprite getSprite() {
		return spri;
	}

}
