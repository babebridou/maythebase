/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.gui;

import com.fairyteller.ld25.collision.CollisionZone;
import com.fairyteller.ld25.entity.Constants;
import com.fairyteller.ld25.entity.Ship;
import com.fairyteller.ld25.entity.Wave;
import com.jme3.app.SimpleApplication;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Tom
 */
public class ScreenControllerDelegate {

  SimpleApplication app;
  float speed = 1.0f;
  boolean cleaning = false;
  

  public synchronized boolean isCleaning() {
	return cleaning;
  }
  public synchronized void setCleaning(boolean cleaning) {
	this.cleaning = cleaning;
  }

  public ScreenControllerDelegate(SimpleApplication app) {
	this.app = app;
  }
  boolean performInit = false;

  public void onStartScreen() {
	this.performInit = true;
  }

  public void onEndScreen(ActionListenerManager actionListenerManager) {
	Constants.cleaning = true;
	setCleaning(true);

	app.enqueue(new Callable<Void>() {
	  public Void call() throws Exception {
		
		List<Ship> ships;
		  for (Wave v : Constants.heroWaves) {
			v.emptyQueue(app.getRootNode());
			
			ships = v.getLaunchedShips();
			if(ships!=null){
			for (Ship s : ships) {
			  s.setDestroyed(true);
			}
			}
			
		  }
		for (Wave v : Constants.waves) {
		  v.emptyQueue(app.getRootNode());
		  ships = v.getLaunchedShips();
		  if(ships!=null){
			for (Ship s : ships) {
			  s.setDestroyed(true);
			}
		  }
		}
		setCleaning(false);
//		app.getRootNode().detachAllChildren();
		return null;
	  }

	});


	actionListenerManager.removeListeners();

  }

  public void update(float tpf, ActionListenerManager actionListenerManager) {
//	System.out.println("updating... "+tpf);
	if (performInit) {
	  actionListenerManager.addListeners();
	  
	  // init finished
	  performInit = false;
	}

	/** jME update loop! */
	if (Constants.waves != null && Constants.heroWaves != null) {
//	Constants.red.pickTarget(heroWave);
//	CollisionZone.getInstance().check(heroWave);
	  for (Wave wave : Constants.heroWaves) {
		CollisionZone.getInstance().check(wave);
	  }
	  for (Wave wave : Constants.waves) {
		CollisionZone.getInstance().check(wave);
	  }

	  CollisionZone.getInstance().purgeBoundingBoxes();

//	heroWave.update(tpf, speed, app.getRootNode());

	  if (Constants.waves.size() > 0) {
		Constants.blue.pickTarget(Constants.waves.getFirst());
	  }
	  if (Constants.heroWaves.size() > 0) {
		Constants.red.pickTarget(Constants.heroWaves.getFirst());
	  }
	  for (Wave wave : Constants.heroWaves) {
		wave.update(tpf, speed, app.getRootNode());
	  }
	  for (Wave wave : Constants.waves) {
		wave.update(tpf, speed, app.getRootNode());
	  }
	}
	
	Constants.red.poolScore();
	Constants.blue.poolScore();
	
	
  }
  
}
