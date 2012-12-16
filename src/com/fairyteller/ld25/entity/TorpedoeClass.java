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

/**
 *
 * @author Tom
 */
public class TorpedoeClass implements EntityClass{
    boolean isInit = false;
    Material material;
    Material materialHit;
    Geometry geometry;
    ColorRGBA color;
	PositionFunction function = new PositionFunction() {

		public double getX(double t, double dt) {
		  return 0d;
		}

		public double getY(double t, double dt) {
		  return 4d*t;
		}

		public double getZ(double t, double dt) {
		  return 0d;
		}
	  };
	
    public TorpedoeClass() {
        super();
    }

    public TorpedoeClass(ColorRGBA color) {
        this();
        this.color = color;
    }
    
    public Geometry getGeometry() {
        return geometry;
    }

    public Material getMaterial() {
        return material;
    }

    public void init(AssetManager assetManager) {
        if(isInit){
            return;
        }
        Box box = new Box(Vector3f.ZERO, 0.10f, 0.10f, 0f);
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/bullet32.png");
        materialHit = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", texture);
        if(color!=null){
           material.setColor("Color", color);
        }
        materialHit.setColor("Color", ColorRGBA.White);
        geometry = new Geometry("missile", box);
        geometry.setModelBound(box.getBound());
		
		material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		materialHit.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		geometry.setQueueBucket(Bucket.Transparent);
        
		
        this.isInit = true;
    }
    public void toggle(boolean hit, Spatial spatial){
        if(hit){
            spatial.setMaterial(materialHit);
        } else {
            spatial.setMaterial(material);
        }
    }
    public int getBaseDamage() {
        return 1;
    }

    public int getBaseHealth() {
        return 0;
    }

	public double getBaseFuel() {
	  return 2d;
	}
	

  public void initShootOffsets(Ship ship, Ship cannon) {
	throw new UnsupportedOperationException("Not supported yet.");
  }

  public double getBaseShootDelay() {
	throw new UnsupportedOperationException("Not supported yet.");
  }
    
  
  
  public PositionFunction getPositionFunction() {
	return function;
  }
    
    
}
