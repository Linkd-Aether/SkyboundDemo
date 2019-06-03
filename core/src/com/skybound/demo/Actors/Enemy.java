package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.specialRects.Hitbox;

public class Enemy implements ActorGeneric {

	Texture txtr;
	public static Hitbox hit;
	Sprite spri;
	EnemyActions currentAction = EnemyActions.idle;
	boolean inAir = false;
	boolean facingRight = true;
	double airMomentum;
	int actionChoice = 0;
	int startLag = 0;
	int duration = 0;
	int endLag = 0;
	int framesIdle = 0;
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
	public void update() {
		if(currentAction == EnemyActions.idle) {
			framesIdle++;
			if(framesIdle % 10 == 0 && Math.random() * 1200 <= framesIdle) {
				actionChoice = (int) (Math.random() * 100);
				if(actionChoice <= 19) walk((int)Math.random() * 3 + 3);
				else if(actionChoice <= 49) claw();
				else if(actionChoice <= 69) fireball();
				else if(actionChoice <= 89) jump();
				else if(actionChoice <= 99) fly();
			}
		}
		
		
	}

	@Override
	public Sprite getSprite() {
		return spri;
	}
	
	@Override
	public Hitbox getHitbox() {
		return hit;
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

	public void walk(int frames) {
		duration = frames;
		currentAction = EnemyActions.walk;
	}
	
	public void claw() {
		duration = 180;
		currentAction = EnemyActions.claw;
	}
	
	public void fireball() {
		duration = 120;
		currentAction = EnemyActions.fireball;
	}
	
	public void jump() {
		duration = 180;
		currentAction = EnemyActions.jump;
	}
	
	public void fly() {
		duration = 600;
		currentAction = EnemyActions.fly;
		inAir = true;
	}
	
	public void flyFireball() {
		duration = 120;
		currentAction = EnemyActions.flyFireball;
	}
	
}
