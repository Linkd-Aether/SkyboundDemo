package com.skybound.demo.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.mappings.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.skybound.demo.specialRects.Hitbox;

public class PlayerChar implements ActorGeneric {

	Texture txtr;
	Hitbox hit;
	Sprite spri;
	PlayerActions currentAction = PlayerActions.idle;
	boolean inAir = false;
	boolean facingRight = true;
	double airMomentum;
	int framesSinceLastAction = 0;
	int currentFrameTarget = 0;
	int hitX = 0;
	int hitY = 0;
	
	public PlayerChar(Texture txt, Sprite spr) {
		txtr = txt;
		spri = spr;
		spri.setBounds(spri.getX(), spri.getY(), 100, 100);
		hit = new Hitbox();
	}
	
	@Override
	public int getX() {
		return (int)spri.getX();
	}

	@Override
	public int getY() {
		return (int)spri.getY();
	}

	@Override
	public void changeSprite(String toChange) {
		txtr = new Texture(toChange);
		spri.setTexture(txtr);
	}

	@Override
	public void setHit(int width, int height, int x, int y) {
		hit.setWidth(width);
		hit.setHeight(height);
		hit.setX(spri.getX() + x);
		hit.setY(spri.getY() + y);
	}
	
	@Override
	public void update() {
		
		framesSinceLastAction++;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && (inAir || currentAction == PlayerActions.idle)) { spri.translateX(-5);
			if(spri.getX() <= 0) spri.setX(0);
			if(!inAir && facingRight) spri.flip(true, false);
			if(!inAir) facingRight = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (inAir || currentAction == PlayerActions.idle)) { spri.translateX(5);
			if(spri.getX() >= 500) spri.setX(500);
			if(!inAir && !facingRight) spri.flip(true, false);
			if(!inAir) facingRight = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !inAir) { inAir = true; airMomentum = 20; }
		
		if(inAir) { spri.translateY((float) airMomentum); airMomentum -= 1.0; }
		
		if(spri.getY() < 0) { inAir = false; spri.setY(0); }
				
		if(Gdx.input.isKeyPressed(Input.Keys.Z) && currentAction == PlayerActions.idle) {
			
			//Up attacks
			if(Gdx.input.isKeyPressed(Input.Keys.UP) && currentAction == PlayerActions.idle) {
				setHit(75, 60, 20, 75);
				hit.setActive(true);
				framesSinceLastAction = 0;
				
				if(inAir) {
					currentAction = PlayerActions.upAir;
					currentFrameTarget = 15;
				}
				else {
					currentAction = PlayerActions.upTilt;
					currentFrameTarget = 20;
				}
			}
			
			//Down attacks
			else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && currentAction == PlayerActions.idle) {
				hit.setActive(true);
				framesSinceLastAction = 0;
				currentFrameTarget = 20;
				if(inAir) {
					setHit(60, 50, 20, -40);
					currentAction = PlayerActions.downAir;
				}
				else {
					
				}
			}
			
			//Side attacks
			else if(facingRight) {
				hit.setActive(true);
				framesSinceLastAction = 0;
				currentFrameTarget = 20;
				if(inAir) {
					if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
						setHit(60, 70, 60, 20);
						currentAction = PlayerActions.backAir;
					}
					else {
						setHit(60, 70, 60, 20);
						currentAction = PlayerActions.forwardAir;
					}
				}
				else {
					setHit(60, 70, 60, 20);
					currentAction = PlayerActions.sideTilt;
				}
				changeSprite("MarthNeutralBlueAttack.png");
			}
			else {
				setHit(60, 70, -20, 20);
				hit.setActive(true);
				framesSinceLastAction = 0;
				currentFrameTarget = 20;
				if(inAir) {
					if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))currentAction = PlayerActions.backAir;
					else currentAction = PlayerActions.forwardAir;
				}
				else currentAction = PlayerActions.sideTilt;
				changeSprite("MarthNeutralBlueAttack.png");
			}
		}
		
		//Aerial hitbox movement
		if(inAir) {
			switch(currentAction) {
				case forwardAir: {
					break;
				}
				case backAir: {
//					hit.setX();
					break;
				}
				case upAir: {
					hit.setX(spri.getX() + 20);
					hit.setY(spri.getY() + 75);
				}
				case downAir: {
					hit.setX(spri.getX() + 20);
					hit.setY(spri.getY() - 40);
				}
			}
		}
		
		if(framesSinceLastAction >= currentFrameTarget) {
			currentAction = PlayerActions.idle;
			changeSprite("MarthNeutralBlue-1.png");
			hit.setActive(false);
		}
	}
	
	@Override
	public Sprite getSprite() {
		return spri;
	}
	
	public Hitbox getHitbox() {
		return hit;
	}
}
