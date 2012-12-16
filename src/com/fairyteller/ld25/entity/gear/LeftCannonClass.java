/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity.gear;

import com.fairyteller.ld25.entity.Ship;
import com.fairyteller.ld25.entity.ShipClass;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class LeftCannonClass extends ShipClass {

  boolean isInit = false;
  Material material;
  Material materialHit;
  Geometry geometry;
  ColorRGBA color;
  int baseDamage;
  int baseHealth;

  public LeftCannonClass() {
	this(null);
  }

  public LeftCannonClass(ColorRGBA color) {
	this(null, null, color, 1, 10, 0.5d);
	this.color = color;
  }

  public LeftCannonClass(ColorRGBA color, int baseDamage, int baseHealth, double baseShootDelay) {
	this(null, null, color, baseDamage, baseHealth, baseShootDelay);
	this.color = color;
  }

  public LeftCannonClass(Material material, Geometry geometry, ColorRGBA color, int baseDamage, int baseHealth, double baseShootDelay) {
	this.material = material;
	this.geometry = geometry;
	this.color = color;
	this.baseDamage = baseDamage;
	this.baseHealth = baseHealth;
	setBaseShootDelay(baseShootDelay);
  }

  @Override
  public double getBaseFuel() {
	return 999d;
  }
  
  
  

  public Spatial getGeometry() {
	return geometry;
  }

  public Material getMaterial() {
	return material;
  }

  public void init(AssetManager assetManager) {
	if (isInit) {
	  return;
	}
	Box box = new Box(Vector3f.ZERO, 0.10f, 0.20f, 0f);
	if (geometry == null) {
	  geometry = new Geometry("Box", box);
	}
	if (material == null) {
	  material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	}
	materialHit = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	Texture texture = assetManager.loadTexture("Textures/cannon32.png");
	if (color != null) {
	  material.setColor("Color", color);
	}
	materialHit.setColor("Color", ColorRGBA.White);
	material.setTexture("ColorMap", texture);
	geometry.setMaterial(material);
	geometry.setModelBound(box.getBound());
	
	material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		materialHit.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		geometry.setQueueBucket(Bucket.Transparent);
        
	
	isInit = true;
  }

  public void toggle(boolean hit, Spatial spatial) {
	if (hit) {
	  spatial.setMaterial(materialHit);
	} else {
	  spatial.setMaterial(material);
	}
  }

  public int getBaseDamage() {
	return this.baseDamage;
  }

  public int getBaseHealth() {
	return this.baseHealth;
  }

  public void initShootOffsets(Ship ship, Ship cannon) {
	cannon.setShootOffsets(Vector3f.UNIT_X.mult(ship.getAzimuth().normalize().dot(Vector3f.UNIT_Y) * 0.5f));
  }
}
