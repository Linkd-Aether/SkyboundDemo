package com.skybound.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skybound.demo.Actors.Enemy;
import com.skybound.demo.Actors.Fireball;
import com.skybound.demo.Actors.PlayerChar;
import com.skybound.demo.specialRects.Hitbox;
import com.skybound.demo.menu.MenuHandler;

import com.badlogic.gdx.math.Rectangle;


public class SkyboundDemoMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerTxtr;
	Sprite playerSpr;
	Stage stage;
	OrthographicCamera camera;
	public static PlayerChar mc;
	
	public static Enemy foe;
	Texture enemyTxtr;
	Sprite enemySpr;
	
	public static Fireball fb;
	Texture fbTxtr;
	Sprite fbSpr;
	
	static boolean debug = false;
	Texture debugBox3;
	Texture debugBox2;
	Texture debugBox;
	Hitbox tempHit;
	
	Texture mCHPContainer;
	Texture foeHPContainer;
	
	Texture background;
	
	static boolean gameUpdate = true;
	static Texture endText;
	
	public static String gameMode = "menu";
	Sprite bossButton;
	Sprite rushButton;
	Sprite duelButton;
	Sprite controlsExpl;
	
	MenuHandler menu = new MenuHandler();
	
	@Override
	public void create () {
		
		background = new Texture("Background-1.png");
		playerTxtr = new Texture("AyanaNeutral-1.png");
		playerSpr = new Sprite(playerTxtr);
		batch = new SpriteBatch();
		debugBox = new Texture("debug.png");
		debugBox2 = new Texture("debug2.png");
		debugBox3 = new Texture("debug3.png");
		mCHPContainer = new Texture("AyanaHealthBar-1.png");
		endText = new Texture("debug.png");
		
//		startBossFight();
		SetupActors(gameMode);
		
		int i = 0;
		for (Controller controller : Controllers.getControllers()) {
			System.out.println("#" + i++ + ": " + controller.getName());
		}
		if (Controllers.getControllers().size == 0) System.out.println("No controllers attached");
	}

	public void setupMainMenu() {
		bossButton = new Sprite(debugBox);
		bossButton.setBounds(100, 200, 100, 150);
		rushButton = new Sprite(debugBox);
		rushButton.setBounds(100, 400, 100, 150);
		duelButton = new Sprite(debugBox);
		duelButton.setBounds(100, 600, 100, 150);
		
	}
	
	public void startBossFight() {
		enemyTxtr = new Texture("GrimaNeutralBlue-1.png");
		enemySpr = new Sprite(enemyTxtr);
		fbTxtr = new Texture("Fireball-1.png");
		fbSpr = new Sprite(fbTxtr);
		foeHPContainer = new Texture("DragonHealthBar-1.png");
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, 640, 480);
				
		mc.getSprite().draw(batch);
		
		if(mc.getHitbox().getActive() && debug) {
			tempHit = mc.getHitbox();
			batch.draw(debugBox, tempHit.getX(), tempHit.getY(), tempHit.getWidth(), tempHit.getHeight());
		}
		if(debug) {
			tempHit = mc.getHitbox();
			batch.draw(debugBox, mc.getX(), mc.getY(), 1, 800);
		}
		if(mc.hp > 0)batch.draw(debugBox2, 40, 255, 20, 140 - (int)((100 - mc.hp) * 1.4));
		batch.draw(mCHPContainer, -50, 250);
		
		if(gameMode == "boss") {
			if(fb.getActive()) fb.getSprite().draw(batch);
			foe.getSprite().draw(batch);
			if(foe.getHitbox().getActive() && debug) {
				tempHit = foe.getHitbox();
				batch.draw(debugBox2, tempHit.getX(), tempHit.getY(), tempHit.getWidth(), tempHit.getHeight());
			}
			if(debug) {
				tempHit = foe.getHitbox();
				batch.draw(debugBox2, foe.getX(), foe.getY(), 1, 800);
			}
			if(foe.hp > 0) batch.draw(debugBox3, 190 + (200 - foe.hp) * 2, 410, 400 - (200 - foe.hp) * 2, 30);
			batch.draw(foeHPContainer, 100, 200);
		}
		
				
		if(gameUpdate) {
			mc.update();
			if(gameMode == "boss") {
			foe.update();
			fb.update();
			}
		}
		else batch.draw(endText, 225, 250, 200, 43);
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerTxtr.dispose();
		debugBox.dispose();
		debugBox2.dispose();
		debugBox3.dispose();
		mCHPContainer.dispose();
		if(gameMode == "boss") {
			enemyTxtr.dispose();
			fbTxtr.dispose();
			foeHPContainer.dispose();
		}
		endText.dispose();
		background.dispose();
	}
	
	private void SetupActors(String mode){
		mc = new PlayerChar(playerTxtr, playerSpr);
		if(mode.equals("boss")) {
			foe = new Enemy(enemyTxtr, enemySpr);
			fb = new Fireball(fbTxtr, fbSpr);
		}
	}

	public static void endGame(boolean win) {
		if(win) endText = new Texture("GameEnd-1.png");
		else endText = new Texture("GameEnd-2.png");
		gameUpdate = false;
	}
	
	public static void setDebug(boolean toSet) {
		debug = toSet;
	}
	
	public static boolean getDebug() {
		return debug;
	}
	
}
