/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.gui;

import com.fairyteller.ld25.levels.AbstractLevel;
import com.fairyteller.ld25.levels.Level01;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import mygame.Constants;

/**
 *
 * @author Tom
 */
public class Level01Controller extends AbstractAppState implements ScreenController, ActionListenerManager {

  private Nifty nifty;
  private Screen screen;
  private SimpleApplication app;
  private AbstractLevel level;
  private ScreenControllerDelegate delegate;
  boolean isRunning = true;

  public Level01Controller() {
  }

  public void bind(Nifty nifty, Screen screen) {
	this.nifty = nifty;
	this.screen = screen;
	System.out.println("bind( " + screen.getScreenId() + ")");
  }

  public void onStartScreen() {
	if(level==null)
	  level = new Level01();
	level.setActiveLevel(true);
	if(delegate!=null)
	  delegate.onStartScreen();
	playtime = 0;
	
  }

  public void onEndScreen() {
	level.setActiveLevel(false);
	delegate.onEndScreen(this);
  }

  public void addListeners(){
	app.getInputManager().addListener(actionListener, new String[]{"Pause", "Wave0", "Wave1", "Wave2"});
  }
  
  public void removeListeners(){
	app.getInputManager().removeListener(actionListener);
  }
  
  /** jME3 AppState methods */
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
	super.initialize(stateManager, app);
	stateManager.attach(this);
	AssetManager assetManager = app.getAssetManager();
	this.app = (SimpleApplication) app;
	this.delegate = new ScreenControllerDelegate(this.app);
	this.delegate.onStartScreen();
	if(this.level==null)
	  this.level = new Level01();
	this.level.setApplication(this.app);
	
	separator.setGroupingSeparator('.');
	format.setDecimalFormatSymbols(separator);
  }

  /** custom methods */ 
  public void startGame(String nextScreen) {
    nifty.gotoScreen(nextScreen);  // switch to another screen
    // start the game and do some more stuff...
  }
  
  DecimalFormat format = new DecimalFormat("000,000,000" ); // c'est pas necessaire de mettre 3 blocs mais je me rappelle plus la syntaxe exacte
  DecimalFormatSymbols separator = format.getDecimalFormatSymbols();
  
  double playtime = 0;
  @Override
  public void update(float tpf) {
	if(level.isActiveLevel()){
	  playtime +=tpf;

	  if(playtime>3d){
		level.start();
	  }
	  delegate.update(tpf, this);
	  if(nifty!=null){
		// find old text
		Element niftyRedScore = nifty.getCurrentScreen().findElementByName("red-score");
		// swap old with new text
		niftyRedScore.getRenderer(TextRenderer.class).setText("RED : "+format.format(Constants.red.getCurrentScore()));

		// find old text
		Element niftyBlueScore = nifty.getCurrentScreen().findElementByName("blue-score");
		// swap old with new text
		niftyBlueScore.getRenderer(TextRenderer.class).setText("BLUE:"+format.format(Constants.blue.getCurrentScore()));
	  }
	}
  }
  
  private ActionListener actionListener = new ActionListener() {

	public void onAction(String name, boolean keyPressed, float tpf) {
	  if (name.equals("Pause") && !keyPressed) {
		isRunning = !isRunning;
	  } else {
		if (isRunning) {
		  if (name.equals("Wave0") && !keyPressed) {
			level.summonWave(0);
		  }
		  if (name.equals("Wave1") && !keyPressed) {
			level.summonWave(1);
		  }
		  if (name.equals("Wave2") && !keyPressed) {
			level.summonWave(2);
		  }

		} else {
		  System.out.println("Press P to unpause.");
		}
	  }
	}
  };
  private AnalogListener analogListener = new AnalogListener() {

	public void onAnalog(String name, float value, float tpf) {
	}
  };
  
}
