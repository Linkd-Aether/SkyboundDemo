package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skybound.demo.specialRects.Hitbox;
import com.badlogic.gdx.graphics.Texture;

public interface ActorGeneric {
	
	public int getX();
	
	public int getY();
	
	public void changeSprite(String toChange);
	
	public void setHit(int width, int height, int x, int y);
	
	public void setLags(int start, int dur, int end);
	
	public void update();
	
	public Sprite getSprite();
	
	public Hitbox getHitbox();
}
