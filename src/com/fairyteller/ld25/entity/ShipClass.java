/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.functions.PositionFunction;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tom
 */
public class ShipClass implements EntityClass {

    boolean isInit = false;
    Material material;
    Material materialHit;
	List<Material> materialExplosions;
    Geometry geometry;
	Geometry explosion;
    ColorRGBA color;

    int baseDamage;
    int baseHealth;
	double baseFuel;
	double baseShootDelay;
    
    public ShipClass() {
        this(null);
    }

    public ShipClass(ColorRGBA color) {
        this(null, null, color, 1, 10, 5d);
        this.color = color;
    }
    public ShipClass(ColorRGBA color, int baseDamage, int baseHealth, double fuel) {
        this(null, null, color, baseDamage, baseHealth, fuel);
        this.color = color;
    }

    public ShipClass(Material material, Geometry geometry, ColorRGBA color, int baseDamage, int baseHealth, double fuel) {
        this.material = material;
        this.geometry = geometry;
        this.color = color;
        this.baseDamage = baseDamage;
        this.baseHealth = baseHealth;
		this.baseFuel = fuel;
//		this.baseShootDelay = baseShootDelay;
		this.materialExplosions = new ArrayList<Material>();
    }
    

    public Spatial getGeometry() {
        return geometry;
    }
	public Spatial getGeometryExplosion() {
        return explosion;
    }

    public Material getMaterial() {
        return material;
    }

    public void init(AssetManager assetManager) {
        if(isInit)
            return;
        Box box = new Box(Vector3f.ZERO, 0.40f, 0.40f, 0f);
        if(geometry==null)
        geometry = new Geometry("Box", box);
		explosion = new Geometry("Box", box);
        if(material==null)
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		
        materialHit = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture textureHit = assetManager.loadTexture("Textures/expl32-0.png");
        Texture texture = assetManager.loadTexture("Textures/sample32.png");
		for(int i = 0; i<6;i++){
		  Texture tex = assetManager.loadTexture("Textures/expl32-"+i+".png");
		  Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		  mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		  mat.setTexture("ColorMap", tex);
		  materialExplosions.add(mat);
		}
        if (color != null) {
            material.setColor("Color", color);
//			materialHit.setColor("Color", color);
        }
//        materialHit.setColor("Color", ColorRGBA.White);
		
		materialHit.setTexture("ColorMap", textureHit);
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);
        geometry.setModelBound(box.getBound());
		explosion.setMaterial(materialHit);
		explosion.setModelBound(box.getBound());
		
		material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		materialHit.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		geometry.setQueueBucket(Bucket.Transparent);
        explosion.setQueueBucket(Bucket.Transparent);
		
		isInit = true;
    }
    
	public void animateMyExplosion(Ship ship, double slerp){
	  if(ship.getShipHitSpatial()==null){
		Spatial newExplosion = explosion.clone();
		ship.setShipHitSpatial(newExplosion);
		ship.attachChild(newExplosion);
	  }
	  int index = (int)Math.round(slerp*5.0d);
	  if(index > materialExplosions.size()-4){
		if(ship.hasChild(ship.getGeometry())){
		  ship.detachChild(ship.getGeometry());
		}
	  }
	  ship.getShipHitSpatial().setMaterial(materialExplosions.get(index%6));
	}
	
    public void toggle(boolean hit, Spatial spatial){
        if(hit){
		  if((spatial instanceof Ship) && ((Ship)spatial).getShipHitSpatial()==null){
			  Spatial newExplosion = explosion.clone();
			  ((Ship)spatial).attachChild(newExplosion);
			  ((Ship)spatial).setShipHitSpatial(newExplosion);
			}
//		    spatial.setMaterial(materialHit);
        } else {
			if((spatial instanceof Ship) && ((Ship)spatial).getShipHitSpatial()!=null){
			  ((Ship)spatial).detachChild(((Ship)spatial).getShipHitSpatial());
			  ((Ship)spatial).setShipHitSpatial(null);
			}
//            spatial.setMaterial(material);
        }
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }

    public int getBaseHealth() {
        return this.baseHealth;
    }

  public void initShootOffsets(Ship ship, Ship cannon) {
	cannon.setShootOffsets(Vector3f.UNIT_X.mult(ship.getAzimuth().normalize().dot(Vector3f.UNIT_Y)*0.5f));
  }

  public double getBaseFuel() {
	return baseFuel;
  }

  public void setBaseShootDelay(double baseShootDelay){
	this.baseShootDelay = baseShootDelay;
  }
  
  public double getBaseShootDelay() {
	return baseShootDelay;
  }
  

  public PositionFunction getPositionFunction() {
	return null;
  }

  public boolean isHoming() {
	return true;
  }
  
  
  long score;

  public long getScore() {
	return this.score;
  }

  public void setScore(long score) {
	this.score = score;
  }
  
	
}
