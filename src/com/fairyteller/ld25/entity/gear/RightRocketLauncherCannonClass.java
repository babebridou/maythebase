/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity.gear;

import com.fairyteller.ld25.entity.Ship;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author Tom
 */
public class RightRocketLauncherCannonClass extends LeftCannonClass {

  public RightRocketLauncherCannonClass() {
	this(null);
  }

  public RightRocketLauncherCannonClass(ColorRGBA color) {
	this(null, null, color, 7, 10, 2f);
  }

  public RightRocketLauncherCannonClass(ColorRGBA color, int baseDamage, int baseHealth, double baseShootDelay) {
	this(null, null, color, baseDamage, baseHealth, baseShootDelay);
  }

  public RightRocketLauncherCannonClass(Material material, Geometry geometry, ColorRGBA color, int baseDamage, int baseHealth, double baseShootDelay) {
	super(material, geometry, color, baseDamage, baseHealth, baseShootDelay);
  }


  @Override
  public void initShootOffsets(Ship ship, Ship cannon) {
	cannon.setShootOffsets(Vector3f.UNIT_X.mult(-ship.getAzimuth().normalize().dot(Vector3f.UNIT_Y) * 0.5f));
  }
  
  @Override
  public boolean isHoming() {
	return true;
  }
}
