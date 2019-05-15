package com.skybound.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skybound.demo.Actors.PlayerChar;

public class SkyboundDemoMain extends ApplicationAdapter {
//	SpriteBatch batch;
	Texture txtr;
	Image imag;
	Stage stage;
	
	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		txtr = new Texture("SunsetBG-1.png");
		imag = new Image(txtr);
		imag.rotateBy(15);
		stage.addActor(imag);
//		batch = new SpriteBatch();
		SetupActors();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
	}
	
	private void SetupActors(){
		PlayerChar mc = new PlayerChar();
	}
}
