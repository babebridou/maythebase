/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tom
 */
public class Team {
  String name;

  public String getName() {
	return name;
  }

  public Team(String name) {
	this.name = name;

  }
  
  Ship currentTarget;
  public void pickTarget(Wave wave){
	if(wave!=null&&wave.getLaunchedShips()!=null&&!wave.getLaunchedShips().isEmpty()){
	  this.currentTarget =  wave.getLaunchedShips().get(0);
	} else {
	  this.currentTarget = null;
	}
  }
  
  long deltaScore = 0;
  long deltaAmmo = 0;
  long deltaLosses = 0;
  long currentScore = 0;
  long ammunitionSpent = 0;
  long shipsLost = 0;
  List<Long> highScores = new ArrayList<Long>();
  
  boolean isPlaying = false;
  
  public void reportScore(long score, Wave wave){
	if(isPlaying){
	  deltaScore+=score;
	}
  }
  public void reportAmmunition(long score, Wave wave){
	if(isPlaying){
	  deltaAmmo+=score;
	}
  }
  public void reportLosses(long score, Wave wave){
	if(isPlaying){
	  deltaLosses+=score;
	}
  }

  public boolean isIsPlaying() {
	return isPlaying;
  }

  public void setIsPlaying(boolean isPlaying) {
	this.isPlaying = isPlaying;
  }
  
  public void resetScores(){
	isPlaying = false;
	 deltaScore = 0;
	deltaAmmo = 0;
	 deltaLosses = 0;
	 currentScore = 0;
	 ammunitionSpent = 0;
	 shipsLost = 0;
  }

  public long getCurrentScore() {
	return currentScore;
  }

  public long getAmmunitionSpent() {
	return ammunitionSpent;
  }

  public long getShipsLost() {
	return shipsLost;
  }
  
  
  
  public void poolScore(){
	
	if(deltaScore>0){
	  long diff = Math.max(1, (long)deltaScore/60);
	  deltaScore-=diff;
	  currentScore+=diff;
	}
	if(deltaAmmo>0){
	  long diff = Math.max(1, (long)deltaAmmo/60);
	  deltaAmmo--;
	  ammunitionSpent++;
	}
	if(deltaLosses>0){
	  long diff = Math.max(1, (long)deltaLosses/60);
	  deltaLosses--;
	  shipsLost++;
	}
  }
}
