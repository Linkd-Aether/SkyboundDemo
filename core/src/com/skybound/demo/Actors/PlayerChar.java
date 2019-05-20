package com.skybound.demo.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.mappings.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.skybound.demo.specialRects.Hitbox;

public class PlayerChar implements ActorGeneric {

	Rectangle charRep;
	Texture txtr;
	Hitbox hit;
	Sprite spri;
	boolean inAir = false;
	boolean facingRight;
	double airMomentum;
	
	public PlayerChar(Rectangle rep, Texture txt, Sprite spr, Hitbox hb) {
		charRep = rep;
		txtr = txt;
		spri = spr;
		hit = hb;
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
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) { spri.translateX(-5);
			if(charRep.getX() <= 0) charRep.x = 0;
//			if(facingRight) 
			if(!inAir) facingRight = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { spri.translateX(5);
			if(charRep.getX() >= 500) spri.setX(500);
			if(!inAir) facingRight = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && !inAir) { inAir = true; airMomentum = 20; }
		
		if(inAir) { spri.translateY((float) airMomentum); airMomentum -= 1.0; }
		
		if(charRep.getY() <= 0) { inAir = false; spri.setX(0); }
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
//			if()
		}
	}

	@Override
	public Sprite getSprite() {
		return spri;
	}
}
