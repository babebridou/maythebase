/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity.gear;

import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class RocketClass extends TorpedoeClass{

  PositionFunction function = new PositionFunction() {

		public double getX(double t, double dt) {
		  return Math.sin(t*4.d*Math.PI)/(10d+10d*t*t);
		}

		public double getY(double t, double dt) {
		  return t+1/2d*t*t;
		}

		public double getZ(double t, double dt) {
		  return 0d;
		}
	  };

  @Override
  public PositionFunction getPositionFunction() {
	return function;
  }
  
  public RocketClass(ColorRGBA color) {
	super(color);
  }

  @Override
  public double getBaseFuel() {
	return 6d;
  }
  
  
  
  
  
  public void init(AssetManager assetManager) {
        if(isInit){
            return;
        }
        Box box = new Box(Vector3f.ZERO, 0.10f, 0.40f, 0f);
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/biglaser32.png");
        materialHit = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", texture);
        if(color!=null){
           material.setColor("Color", color);
        }
        materialHit.setColor("Color", ColorRGBA.White);
        geometry = new Geometry("missile", box);
        geometry.setModelBound(box.getBound());
		
		explosion = new Geometry("Box", box);
		explosion.setModelBound(box.getBound());
		
		material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		materialHit.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		geometry.setQueueBucket(Bucket.Transparent);
        explosion.setQueueBucket(Bucket.Transparent);
		
        this.isInit = true;
    }
}
