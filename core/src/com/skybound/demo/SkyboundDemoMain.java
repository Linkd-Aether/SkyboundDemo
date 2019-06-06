package com.skybound.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
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
	
	boolean debug = true;
	Texture debugBox2;
	Texture debugBox;
	Hitbox tempHit;
	
	@Override
	public void create () {
		
//		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("perpetua.tff"));
//		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
//		parameter.size = 12;
//		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
//		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
		playerTxtr = new Texture("AyanaNeutral-1.png");
		playerSpr = new Sprite(playerTxtr);
		enemyTxtr = new Texture("GrimaNeutralBlue-1.png");
		enemySpr = new Sprite(enemyTxtr);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();
		debugBox = new Texture("debug.png");
		debugBox2 = new Texture("debug2.png");
//		Music music = Gdx.audio.newMusic(Gdx.files.internal("Wake.mp3"));
//		music.play();
		SetupActors();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		foe.getSprite().draw(batch);
		mc.getSprite().draw(batch);
		if(mc.getHitbox().getActive() && debug) {
			tempHit = mc.getHitbox();
			batch.draw(debugBox, tempHit.getX(), tempHit.getY(), tempHit.getWidth(), tempHit.getHeight());
		}
		if(foe.getHitbox().getActive() && debug) {
			tempHit = foe.getHitbox();
			batch.draw(debugBox2, tempHit.getX(), tempHit.getY(), tempHit.getWidth(), tempHit.getHeight());
		}
		batch.end();
		mc.update();
		foe.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerTxtr.dispose();
		enemyTxtr.dispose();
//		fbTxtr.dispose();
		debugBox.dispose();
		debugBox2.dispose();
	}
	
	private void SetupActors(){
		mc = new PlayerChar(playerTxtr, playerSpr);
		foe = new Enemy(enemyTxtr, enemySpr);
	}
}
