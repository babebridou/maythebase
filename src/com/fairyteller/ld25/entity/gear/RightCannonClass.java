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
public class RightCannonClass extends LeftCannonClass {

//  boolean isInit = false;
//  Material material;
//  Material materialHit;
//  Geometry geometry;
//  ColorRGBA color;
//  int baseDamage;
//  int baseHealth;

  public RightCannonClass() {
	this(null);
  }

  public RightCannonClass(ColorRGBA color) {
	this(null, null, color, 1, 10, 0.5f);
	this.color = color;
  }

  public RightCannonClass(ColorRGBA color, int baseDamage, int baseHealth, double baseShootDelay) {
	this(null, null, color, baseDamage, baseHealth, baseShootDelay);
//	this.color = color;
  }

  public RightCannonClass(Material material, Geometry geometry, ColorRGBA color, int baseDamage, int baseHealth, double baseShootDelay) {
	super(material, geometry, color, baseDamage, baseHealth, baseShootDelay);
//	this.material = material;
//	this.geometry = geometry;
//	this.color = color;
//	this.baseDamage = baseDamage;
//	this.baseHealth = baseHealth;
  }
//
//  @Override
//  public double getBaseFuel() {
//	return 999d;
//  }
//  
//  public Spatial getGeometry() {
//	return geometry;
//  }
//
//  public Material getMaterial() {
//	return material;
//  }
//
//  public void init(AssetManager assetManager) {
//	if (isInit) {
//	  return;
//	}
//	Box box = new Box(Vector3f.ZERO, 0.20f, 0.30f, 0f);
//	if (geometry == null) {
//	  geometry = new Geometry("Box", box);
//	}
//	if (material == null) {
//	  material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//	}
//	materialHit = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//	Texture texture = assetManager.loadTexture("Textures/cannon32.png");
//	if (color != null) {
//	  material.setColor("Color", color);
//	}
//	materialHit.setColor("Color", ColorRGBA.White);
//	material.setTexture("ColorMap", texture);
//	geometry.setMaterial(material);
//	geometry.setModelBound(box.getBound());
//	isInit = true;
//  }
//
//  public void toggle(boolean hit, Spatial spatial) {
//	if (hit) {
//	  spatial.setMaterial(materialHit);
//	} else {
//	  spatial.setMaterial(material);
//	}
//  }
//
//  public int getBaseDamage() {
//	return this.baseDamage;
//  }
//
//  public int getBaseHealth() {
//	return this.baseHealth;
//  }

  @Override
  public void initShootOffsets(Ship ship, Ship cannon) {
	cannon.setShootOffsets(Vector3f.UNIT_X.mult(-ship.getAzimuth().normalize().dot(Vector3f.UNIT_Y) * 0.5f));
  }
}
