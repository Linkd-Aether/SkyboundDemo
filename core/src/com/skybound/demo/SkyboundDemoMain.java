package com.skybound.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skybound.demo.Actors.PlayerChar;
import com.badlogic.gdx.math.Rectangle;

public class SkyboundDemoMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture txtr;
	Image imag;
	Stage stage;
	OrthographicCamera camera;
	Rectangle charRep;
	double airMomentum;
	boolean inAir;
	
	@Override
	public void create () {
//		stage = new Stage(new ScreenViewport());
		txtr = new Texture("AzuraNeutralBlue-1.png");
//		imag = new Image(txtr);
//		imag.rotateBy(15);
//		stage.addActor(imag);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();
		charRep = new Rectangle();
//		Music music = Gdx.audio.newMusic(Gdx.files.internal("Wake.mp3"));
//		music.play();
//		SetupActors();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(txtr, charRep.x, charRep.y);
		batch.end();
//		stage.act();
//		stage.draw();
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) charRep.x -= 5;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) charRep.x += 5;
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) { inAir = true; airMomentum = 5; }
		
		if(inAir) { charRep.y += airMomentum; airMomentum -= .5; }
		
		if(charRep.y == 0) inAir = false;
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		txtr.dispose();
	}
	
	private void SetupActors(){
		PlayerChar mc = new PlayerChar();
	}
}
