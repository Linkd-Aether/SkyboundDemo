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
import com.skybound.demo.Actors.PlayerChar;
import com.skybound.demo.specialRects.Hitbox;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SkyboundDemoMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerTxtr;
	Sprite playerSpr;
	Stage stage;
	OrthographicCamera camera;
	PlayerChar mc;
	
	Enemy foe;
	Texture enemyTxtr;
	Sprite enemySpr;
	
	boolean debug = true;
	Texture debugBox;
	Hitbox tempHit;
	
	@Override
	public void create () {
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
		playerTxtr = new Texture("MarthNeutralBlue-1.png");
		playerSpr = new Sprite(playerTxtr);
		enemyTxtr = new Texture("GrimaNeutralBlue-1.png");
		enemySpr = new Sprite(enemyTxtr);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();
		debugBox = new Texture("debug.png");
		
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
//		batch.draw(txtr, charRep.x, charRep.y);
		batch.end();
//		stage.act();
//		stage.draw();
		mc.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerTxtr.dispose();
	}
	
	private void SetupActors(){
		mc = new PlayerChar(playerTxtr, playerSpr);
		foe = new Enemy(enemyTxtr, enemySpr);
	}
}
