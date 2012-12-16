/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.jme3.math.Vector3f;

/**
 *
 * @author Tom
 */
public class HeroShip extends Ship{

  public HeroShip(Team team, String name, Wave wave, EntityClass projectileClass, EntityClass shipClass, Vector3f azimuth) {
	super(team, name, wave, projectileClass, shipClass, azimuth);
  }

  @Override
  public Vector3f seek() {
	return null;
  }
  
}
