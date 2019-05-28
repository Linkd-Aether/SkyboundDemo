package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

public interface ActorGeneric {

	public int getX();
	
	public int getY();
	
	public void changeSprite(String toChange);
	
	public void setHit(int width, int height, int x, int y);
	
	public void update();
	
	public Sprite getSprite();
}
