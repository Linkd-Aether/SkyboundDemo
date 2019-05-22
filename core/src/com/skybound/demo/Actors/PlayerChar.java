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
	boolean facingRight = true;
	boolean inAttack = false;
	double airMomentum;
	int framesSinceLastAction = 0;
	int currentFrameTarget = 0;
	
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
		
		framesSinceLastAction++;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) { spri.translateX(-5);
			if(spri.getX() <= 0) spri.setX(0);
			if(!inAir && facingRight) spri.flip(true, false);
			if(!inAir) facingRight = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { spri.translateX(5);
			if(spri.getX() >= 500) spri.setX(500);
			if(!inAir && !facingRight) spri.flip(true, false);
			if(!inAir) facingRight = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && !inAir) { inAir = true; airMomentum = 20; }
		
		if(inAir) { spri.translateY((float) airMomentum); airMomentum -= 1.0; }
		
		if(spri.getY() < 0) { inAir = false; spri.setY(0); }
		
		System.out.println(inAir);
		
		if(Gdx.input.isKeyPressed(Input.Keys.Z) && !inAttack) {
			if(facingRight) {
				hit.x = spri.getX() + 20;
				hit.y = spri.getY() + 20;
				hit.setActive(true);
				framesSinceLastAction = 0;
				currentFrameTarget = 20;
				inAttack = true;
				changeSprite("MarthNeutralBlueAttack.png");
			}
			else {
				hit.x = spri.getX() - 20;
				hit.y = spri.getY() + 20;
				hit.setActive(true);
				framesSinceLastAction = 0;
				currentFrameTarget = 20;
				inAttack = true;
				changeSprite("MarthNeutralBlueAttack.png");
			}
		}
		
		if(framesSinceLastAction >= currentFrameTarget) {
			inAttack = false;
			changeSprite("MarthNeutralBlue-1.png");
		}
	}
	
	@Override
	public Sprite getSprite() {
		return spri;
	}
}
