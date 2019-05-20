package com.skybound.demo.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ActorGeneric {

	public int getX();
	
	public int getY();
	
	public void changeSprite(String toChange);
	
	public void update();
	
	public Sprite getSprite();
}
