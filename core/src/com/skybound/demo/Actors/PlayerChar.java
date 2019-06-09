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
	boolean invulnerable = false;
	boolean hasAirAttacked = false;
	double airMomentum;
	int framesSinceLastAction = 0;
	int framesInvulnerable = 0;
	int frameAirSpin = 0;
	int idleCount = 1;
	int duration = 0;
	int startLag = 0;
	int endLag = 0;
	int hitX = 0;
	int hitY = 0;
	
	public PlayerChar(Texture txt, Sprite spr) {
		txtr = txt;
		spri = spr;
		spri.setBounds(spri.getX(), spri.getY(), 100, 100);
		hit = new Hitbox();
		spri.setOriginCenter();
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
	public void setLags(int start, int dur, int end) {
		startLag = start;
		duration = dur;
		endLag = end;
	}
	
	public void takeDamage() {
		currentAction = PlayerActions.takeDamage;
		invulnerable = true;
		inAir = true;
		airMomentum = 10;
		framesSinceLastAction = 0;
		framesInvulnerable = 0;
		System.out.println("HIT");
		spri.setAlpha((float) .5);
	}
	
	@Override
	public void update() {
		
		framesSinceLastAction++;
		
		startLag--;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && (inAir || currentAction == PlayerActions.idle) && currentAction != PlayerActions.takeDamage) { spri.translateX(-7);
			if(spri.getX() <= 0) spri.setX(0);
			if(!inAir && facingRight) spri.flip(true, false);
			if(!inAir) facingRight = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (inAir || currentAction == PlayerActions.idle) && currentAction != PlayerActions.takeDamage) { spri.translateX(7);
			if(spri.getX() >= 550) spri.setX(550);
			if(!inAir && !facingRight) spri.flip(true, false);
			if(!inAir) facingRight = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !inAir && currentAction == PlayerActions.idle) { inAir = true; airMomentum = 20; }
		
		if(inAir) { spri.translateY((float) airMomentum); airMomentum -= 1.0; }
		
		if(spri.getY() < 0) {
			inAir = false;
			spri.setY(0);
			idleCount = 1;
			hasAirAttacked = false;
			spri.setRotation(0);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && currentAction == PlayerActions.idle) {
			
			spri.setRotation(0);
			
			framesSinceLastAction = 0;
			
			//Up attacks
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
				setHit(75, 60, 20, 75);
				if(inAir) {
					hasAirAttacked = true;
					currentAction = PlayerActions.upAir;
					setLags(3, 15, 5);
				}
				else {
					changeSprite("AyanaUTilt-1.png");
					currentAction = PlayerActions.upTilt;
					setLags(5, 20, 5);
				}
			}
			
			//Down attacks
			else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				if(inAir) {
					setHit(60, 50, 20, -20);
					hasAirAttacked = true;
					changeSprite("AyanaDAir-1.png");
					currentAction = PlayerActions.downAir;
					setLags(5, 20, 8);
				}
				else {
					if(facingRight) setHit(60, 10, 70, 10);
					else setHit(60, 10, -20, 10);
					changeSprite("AyanaDTilt-1.png");
					currentAction = PlayerActions.downTilt;
					setLags(2, 10, 3);
				}
			}
			
			//Side attacks
			else if(facingRight) {
				if(inAir) {
					if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
						setHit(70, 40, -20, 20);
						hasAirAttacked = true;
						currentAction = PlayerActions.backAir;
						setLags(5, 20, 8);
					}
					else {
						setHit(60, 80, 60, 20);
						hasAirAttacked = true;
						changeSprite("AyanaFAir-1.png");
						currentAction = PlayerActions.forwardAir;
						setLags(3, 15, 6);
					}
				}
				else {
					setHit(60, 70, 60, 20);
					currentAction = PlayerActions.sideTilt;
					setLags(6, 20, 8);
				}
			}
			else {
				if(inAir) {
					if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
						setHit(70, 40, 60, 20);
						hasAirAttacked = true;
						currentAction = PlayerActions.backAir;
						setLags(5, 20, 8);
					}
					else {
						setHit(60, 80, -20, 20);
						hasAirAttacked = true;
						changeSprite("AyanaFAir-1.png");
						currentAction = PlayerActions.forwardAir;
						setLags(3, 15, 6);
					}
				}
				else {
					setHit(60, 70, -20, 20);
					currentAction = PlayerActions.sideTilt;
					setLags(6, 20, 8);
				}
			}
		}
		
		//Aerial hitbox movement
		if(inAir) {
			switch(currentAction) {
				case forwardAir: {
					if(facingRight) {
						hit.setX(spri.getX() + 60);
						hit.setY(spri.getY() + 20);
					}
					else {
						hit.setX(spri.getX() - 20);
						hit.setY(spri.getY() + 20);
					}
					break;
				}
				case backAir: {
					if(facingRight) {
						hit.setX(spri.getX() - 20);
						hit.setY(spri.getY() + 20);
					}
					else {
						hit.setX(spri.getX() + 60);
						hit.setY(spri.getY() + 20);
					}
					break;
				}
				case upAir: {
					hit.setX(spri.getX() + 20);
					hit.setY(spri.getY() + 75);
					break;
				}
				case downAir: {
					hit.setX(spri.getX() + 20);
					hit.setY(spri.getY() - 20);
					break;
				}
			}
			
			if(!hasAirAttacked) {
				changeSprite("AyanaAirSpin-1.png");
				if(frameAirSpin == 4) frameAirSpin = 0;
				spri.setRotation(frameAirSpin++ * 90);
				
			}
			
		}
		
		if(startLag == 0) {
			hit.setActive(true);
		}
		
		if(framesSinceLastAction >= duration) {
			if(inAir && hasAirAttacked) changeSprite("AyanaNeutral-1.png");
			hit.setActive(false);
			if(endLag-- == 0) {
				currentAction = PlayerActions.idle;
			}
		}
		
		if(!invulnerable && ((spri.getBoundingRectangle().overlaps(Enemy.hit) && Enemy.hit.getActive()) || 
				(spri.getBoundingRectangle().overlaps(Fireball.hit) && Fireball.hit.getActive()))) takeDamage();
		
		if(currentAction == PlayerActions.takeDamage && framesSinceLastAction >= 30) currentAction = PlayerActions.idle;
		
		if(invulnerable) framesInvulnerable++;
		
		if(framesInvulnerable >= 60) {
			invulnerable = false;
			spri.setAlpha(1);
		}
		
		if(currentAction == PlayerActions.idle) {
			if(!inAir) changeSprite("AyanaNeutral-" + (idleCount++ / 20 + 1) + ".png");
		}
		
		if(idleCount == 60) idleCount = 0;
		
	}
	
	@Override
	public Sprite getSprite() {
		return spri;
	}
	
	@Override
	public Hitbox getHitbox() {
		return hit;
	}
}
