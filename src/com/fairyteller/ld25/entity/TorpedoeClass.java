/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class TorpedoeClass implements EntityClass{
    boolean isInit = false;
    Material material;
    Geometry geometry;

    ColorRGBA color;

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
        material.setTexture("ColorMap", texture);
        if(color!=null){
           material.setColor("Color", color);
        }
        geometry = new Geometry("missile", box);
        this.isInit = true;
    }
    
    
    
    
}
