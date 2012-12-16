/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

/**
 *
 * @author Tom
 */
public class Team {
  Ship currentTarget;
  public void pickTarget(Wave wave){
	if(wave!=null&&wave.getLaunchedShips()!=null&&!wave.getLaunchedShips().isEmpty()){
	  this.currentTarget =  wave.getLaunchedShips().get(0);
	} else {
	  this.currentTarget = null;
	}
  }
  
  
  
}
