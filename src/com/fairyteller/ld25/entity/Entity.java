/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.control.MissileControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import mygame.Missile;

/**
 *
 * @author Tom
 */
public class Entity extends Node{
    Geometry geomship;
    Box box;
    Missile missile;

    public Entity(String name) {
        super(name);
    }
    
    public void create(AssetManager assetManager){
        box = new Box(Vector3f.ZERO, 0.40f, 0.40f, 0f);
        geomship = new Geometry("Box", box);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/sample32.png");
        
        mat.setTexture("ColorMap", texture);
        geomship.setMaterial(mat);
        attachChild(geomship);
        this.missile = new Missile();
        this.missile.create(assetManager);
        this.missile.addControl(new MissileControl(this));
    }

    public void fire(){
        Geometry geometry = this.missile.spawn(this);
        getParent().attachChild(geometry);
    }
    
    public Geometry getGeometry(){
        return geomship;
    }

    
}
