package com.gstrzal.insects;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Logger;
import com.gstrzal.insects.config.GameConfig;
import com.gstrzal.insects.screens.LoadingScreen;


public class Insects extends Game {
	public static final int V_WIDTH = (int)GameConfig.SCREEN_WIDTH_PX;
	public static final int V_HEIGHT = (int)GameConfig.SCREEN_HEIGHT_PX;
	public static final float PPM = 400;


	public static final short DEFAULT_BIT = 0x0001;
	public static final short INSECT_BIT = 0x0002;
	public static final short BASE_BIT = 0x0004;
	public static final short BRICK_BIT = 0x0008;
	public static final short FLOWER_BIT = 0x0016;
	public static final short LEVEL_END_BIT = 0x0032;
	public static final short DAMAGE_BIT = 0x0064;
	public static final short PASS_BLOCK_BIT = 0x0128;
	public static final short CHANGE_INSECT_BIT = 0x0256;
	public static final short SLOPE_BIT = 0x0512;


	private boolean audioOn = true;
	private boolean displayControllers = true;

	public SpriteBatch batch;
	private final AssetManager assetManager = new AssetManager();

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		assetManager.getLogger().setLevel(Logger.DEBUG);

		batch = new SpriteBatch();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		setScreen(new LoadingScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    public AssetManager getAssetManager() {
        return assetManager;
    }

	public boolean isAudioOn() {
		return audioOn;
	}

	public void setAudioOn(boolean audioOn) {
		this.audioOn = audioOn;
	}

	public boolean isDisplayControllers() {
		return displayControllers;
	}

	public void setDisplayControllers(boolean displayControllers) {
		this.displayControllers = displayControllers;
	}
}
