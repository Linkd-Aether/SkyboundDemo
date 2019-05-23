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
	PlayerActions currentAction = PlayerActions.idle;
	boolean inAir = false;
	boolean facingRight = true;
	double airMomentum;
	int framesSinceLastAction = 0;
	int currentFrameTarget = 0;
	
	public PlayerChar(Rectangle rep, Texture txt, Sprite spr) {
		charRep = rep;
		txtr = txt;
		spri = spr;
		spri.setBounds(spri.getX(), spri.getY(), 100, 100);
		hit = new Hitbox();
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
			if(Gdx.input.isKeyPressed(Input.Keys.UP) && currentAction == PlayerActions.idle) {}
			else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && currentAction == PlayerActions.idle) {}
			else if(facingRight) {
				hit.width = 20;
				hit.height = 20;
				hit.x = spri.getX() + 20;
				hit.y = spri.getY() + 20;
				hit.setActive(true);
				framesSinceLastAction = 0;
				currentFrameTarget = 20;
				if(inAir) {
					if(Gdx.input.isKeyPressed(Input.Keys.LEFT))currentAction = PlayerActions.backAir;
					else currentAction = PlayerActions.forwardAir;
				}
				else currentAction = PlayerActions.sideTilt;
				changeSprite("MarthNeutralBlueAttack.png");
			}
			else {
				hit.width = 20;
				hit.height = 20;
				hit.x = spri.getX() - 20;
				hit.y = spri.getY() + 20;
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
			
			if(inAir) {
				switch(currentAction) {
//					case(PlayerActions.backAir){
//						break;
//					}
				}
			}
		}
		
//		System.out.println(currentAction);
		
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
