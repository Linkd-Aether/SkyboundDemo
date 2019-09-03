package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.SkyboundDemoMain;
import com.skybound.demo.specialRects.Hitbox;

public class Enemy implements ActorGeneric {

	Texture txtr;
	public static Hitbox hit;
	Sprite spri;
	String addFireball = "";
	static EnemyActions currentAction = EnemyActions.idle;
	boolean inAir = false;
	boolean facingRight = true;
	boolean invulnerable = false;
	double airMomentum;
	int framesInvulnerable = 0;
	int actionChoice = 0;
	int startLag = 0;
	int duration = 0;
	int endLag = 0;
	int framesIdle = 0;
	int hitX = 0;
	int hitY = 0;
	public int hp = 200;
	
	public Enemy(Texture txt, Sprite spr) {
		txtr = txt;
		spri = spr;
		hit = new Hitbox();
		spri.setBounds(spri.getX(), spri.getY(), 200, 200);
		spri.setX(400);
		rightCheck(false);
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
		
		if(hp <= 0) SkyboundDemoMain.endGame(true);
		
		if(currentAction == EnemyActions.idle) {
			changeSprite("DragonIdle-1.png");
			framesIdle++;
			if(!inAir && framesIdle % 10 == 0 && Math.random() * 1200 <= framesIdle) {
				framesIdle = 0;
				actionChoice = (int) (Math.random() * 100);
				if(actionChoice <= 19) walk((int)Math.random() * 100 + 60);
				else if(actionChoice <= 49) claw();
				else if(actionChoice <= 69) fireball();
				else if(actionChoice <= 89) jump();
				else if(actionChoice <= 99) fly();
			}
		}
		
		if(currentAction == EnemyActions.walk) {
			if(duration-- >= 0) {
				if(Math.abs(SkyboundDemoMain.mc.getSprite().getX() - (getX() + 150)) < 20) {
					claw();
				}
				else if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) {
					rightCheck(true);
					spri.translateX(2);
				}
				else {
					rightCheck(false);
					spri.translateX(-2);
				}
			}
			if(duration <= 0) {
				currentAction = EnemyActions.idle;
			}
		}
		
		if(currentAction == EnemyActions.claw) {
			if(duration-- == 110 || duration == 60 || duration == 10) {
				changeSprite("DragonClaw-2.png");
				hit.setActive(true);
				if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) {
					rightCheck(true);
					setHit(50, 50, 150, 50);
				}
				else {
					rightCheck(false);
					setHit(50, 50, -20, 50);
				}
			}
			if(duration == 100 || duration == 50 || duration == 0) {
				changeSprite("DragonClaw-1.png");
				hit.setActive(false);
			}
			if(duration <= 0) currentAction = EnemyActions.idle;
		}
		
		if(currentAction == EnemyActions.fireball) {
			changeSprite("DragonFireball-1.png");
			if(duration-- == 60) {
				SkyboundDemoMain.fb.setActive(true);
				if(facingRight) SkyboundDemoMain.fb.set((int) spri.getX() + 100, (int) spri.getY() + 65, 0, 10);
				else SkyboundDemoMain.fb.set((int) spri.getX(), (int) spri.getY() + 65, 180, 10);
			}
			if(duration < 60) changeSprite("DragonIdle-1.png");
			if(duration <= 0) currentAction = EnemyActions.idle;
		}
		
		if(currentAction == EnemyActions.jump) {
			hit.setActive(true);
			if(airMomentum > 0) {
				
				setHit(200, 100, 0, 100);
			}
			else {
				setHit(200, 100, 0, 0);
			}
			
			if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) spri.translateX(2);
			else spri.translateX(-2);
		}
		
		if(currentAction == EnemyActions.fly) {
			hit.setActive(true);
			if(airMomentum > 0) setHit(200, 100, 0, 100);
			else if(airMomentum < -1)setHit(200, 100, 0, 0);
			else hit.setActive(false);
			
			if(duration % 20 == 10) changeSprite("DragonAir" + addFireball + "-1.png");
			if(duration % 20 == 0) changeSprite("DragonAir" + addFireball + "-2.png");
			
			if(duration-- > 0 && airMomentum <= 0) if(spri.getY() <= 200) {
				airMomentum = 10;
			}
			
			if(duration == 240 || duration == 360 || duration == 480) {
				addFireball = "Fireball";
			}
			
			if(duration == 180 || duration == 300 || duration == 420) {
				addFireball = "";
				if(Math.random() > .5)flyFireball();
			}
		}
		
		if(inAir) { spri.translateY((float) airMomentum); airMomentum -= 1.0; }
		
		
		if(spri.getY() < 0) {
			currentAction = EnemyActions.idle;
			spri.setY(0);
			inAir = false;
			hit.setActive(false);
		}
		
		if(!invulnerable && ((spri.getBoundingRectangle().overlaps(PlayerChar.hit) && PlayerChar.hit.getActive()))) takeDamage();
		
		if(invulnerable) framesInvulnerable++;
		
		if(framesInvulnerable >= 30) {
			invulnerable = false;
			spri.setAlpha(1);
		}
		
//		System.out.println(Math.atan((SkyboundDemoMain.mc.getX() - spri.getX()) / (spri.getY() - SkyboundDemoMain.mc.getY())) + (Math.PI/2));
	}

	private void takeDamage() {
		invulnerable = true;
		framesInvulnerable = 0;
		spri.setAlpha((float) .5);
		if(PlayerChar.currentAction == PlayerActions.sideTilt || PlayerChar.currentAction == PlayerActions.upTilt) hp -= 6;
		if(PlayerChar.currentAction == PlayerActions.downTilt) hp -= 3;
		if(PlayerChar.currentAction == PlayerActions.forwardAir || PlayerChar.currentAction == PlayerActions.backAir) hp -= 4;
		if(PlayerChar.currentAction == PlayerActions.downAir || PlayerChar.currentAction == PlayerActions.upAir) hp -= 5;
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
	
	void rightCheck(boolean right) {
		if(facingRight != right) {
			spri.flip(true, false);
		}
		facingRight = right;
	}

	public void walk(int frames) {
		duration = frames;
		currentAction = EnemyActions.walk;
	}
	
	public void claw() {
		changeSprite("DragonClaw-1.png");
		duration = 150;
		currentAction = EnemyActions.claw;
	}
	
	public void fireball() {
		if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) rightCheck(true);
		else rightCheck(false);
		duration = 120;
		currentAction = EnemyActions.fireball;
	}
	
	public void jump() {
		if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) rightCheck(true);
		else rightCheck(false);
		currentAction = EnemyActions.jump;
		inAir = true;
		airMomentum = 25;
	}
	
	public void fly() {
		if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) rightCheck(true);
		else rightCheck(false);
		duration = 600;
		currentAction = EnemyActions.fly;
		inAir = true;
		airMomentum = 25;
	}
	
	public void flyFireball() {
		SkyboundDemoMain.fb.setActive(true);
		if(SkyboundDemoMain.mc.getSprite().getX() > getX() + 100) rightCheck(true);
		else rightCheck(false);
		if(facingRight) SkyboundDemoMain.fb.set((int) spri.getX() + 100, (int) spri.getY(), 0, 10);
		else SkyboundDemoMain.fb.set((int) spri.getX(), (int) spri.getY(), 180, 10);
	}
	
}
