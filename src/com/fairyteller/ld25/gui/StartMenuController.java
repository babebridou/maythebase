/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.gui;

import com.fairyteller.ld25.levels.AbstractLevel;
import com.fairyteller.ld25.levels.StartMenu;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Random;
import mygame.Constants;

/**
 *
 * @author Tom
 */
public class StartMenuController extends AbstractAppState implements ScreenController, ActionListenerManager {

  private Nifty nifty;
  private Screen screen;
  private SimpleApplication app;
  private AbstractLevel level;
  private ScreenControllerDelegate delegate;
  boolean isRunning = true;

  public StartMenuController() {
  }

  public void bind(Nifty nifty, Screen screen) {
	this.nifty = nifty;
	this.screen = screen;
	System.out.println("bind( " + screen.getScreenId() + ")");
  }

  public void onStartScreen() {
	if(level==null)
	  level = new StartMenu();
	level.setActiveLevel(true);
	if(delegate!=null){
	  delegate.onStartScreen();
	  
	}
	isActive = true;
	playtime = 0;
  }
  private boolean isActive = false;
  public void onEndScreen() {
	level.setActiveLevel(false);
	if(isActive){
	  isActive = false;
	}
	delegate.onEndScreen(this);
	playtime = 0;
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
	if(this.level==null){
	  this.level = new StartMenu();
	}
	this.level.setApplication(this.app);

  }

  /** custom methods */ 
  public void startGame(String nextScreen) {
    nifty.gotoScreen(nextScreen);  // switch to another screen
    // start the game and do some more stuff...
  }
  
  double playtime = 0;
  Random rand = new Random();
  @Override
  public void update(float tpf) {
	if(level.isActiveLevel()){
	  playtime +=tpf;

	  if(playtime>4d){
		level.start();
	  }
	  if(isActive&&!delegate.isCleaning() && !Constants.cleaning){
		int i = rand.nextInt(100);
		if(i%100<3){
		  level.summonWave(i);
		}
	  }
	  delegate.update(tpf, this);
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
