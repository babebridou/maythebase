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
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class CannonClass implements EntityClass {

    boolean isInit = false;
    Material material;
    Material materialHit;
    Geometry geometry;
    ColorRGBA color;

    int baseDamage;
    int baseHealth;
    
    public CannonClass() {
        this(null);
    }

    public CannonClass(ColorRGBA color) {
        this(null, null, color, 1, 10);
        this.color = color;
    }
    public CannonClass(ColorRGBA color, int baseDamage, int baseHealth) {
        this(null, null, color, baseDamage, baseHealth);
        this.color = color;
    }

    public CannonClass(Material material, Geometry geometry, ColorRGBA color, int baseDamage, int baseHealth) {
        this.material = material;
        this.geometry = geometry;
        this.color = color;
        this.baseDamage = baseDamage;
        this.baseHealth = baseHealth;
    }
    

    public Spatial getGeometry() {
        return geometry;
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
        if(material==null)
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        materialHit = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/sample32.png");
        if (color != null) {
            material.setColor("Color", color);
        }
        materialHit.setColor("Color", ColorRGBA.White);
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);
        geometry.setModelBound(box.getBound());
        isInit = true;
    }
    
    public void toggle(boolean hit, Spatial spatial){
        if(hit){
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
    
}
