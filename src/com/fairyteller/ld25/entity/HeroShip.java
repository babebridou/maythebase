/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.control.interfaces.Destroyable;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class HeroShip //extends Node implements Mover, Shooter, Destroyable 
{
/*
    Spatial geomship;
    Box box;
    PositionFunction positionFunction;
    EntityClass projectileClass;


    
    public HeroShip(String name) {
        super(name);
        projectileClass = new TorpedoeClass(ColorRGBA.Cyan);
    }

    public void setPositionFunction(PositionFunction positionFunction) {
        this.positionFunction = positionFunction;
    }

    public PositionFunction getPositionFunction() {
        return this.positionFunction;
    }

    public void create(AssetManager assetManager) {
        this.projectileClass.init(assetManager);
        box = new Box(Vector3f.ZERO, 0.40f, 0.40f, 0f);
        geomship = new Geometry("Box", box);
//        geomship = assetManager.loadModel("Models/testobj.j3o");
//        geomship = new Geometry("Box", teapot);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        Material mat = new Material(assetManager, "Materials/testobj.mtl");
        Texture texture = assetManager.loadTexture("Textures/sample32.png");
        mat.setTexture("ColorMap", texture);
        geomship.setMaterial(mat);
//        geomship.setModelBound(box.getBound());
        
        attachChild(geomship);
    }
    double nextshoot = 1d;
    double delay = 0.2d;

    public boolean shouldFire(double lifetime) {
        return lifetime > nextshoot;
    }

    public void shoot(double lifetime) {
        Torpedoe t = null;

        t = new Torpedoe(projectileClass, this, new PositionFunction() {

            public double getX(double t) {
                return 0d;
            }

            public double getY(double t) {
                return t * 4d;
            }

            public double getZ(double t) {
                return 0d;
            }
        });

        if (t != null) {
            nextshoot += delay;
            getParent().attachChild(t);
        }
    }

    public Vector3f getShootOffsets() {
        return getAzimuth().mult(0.5f);
    }

    public double getMoveOffsetX() {
        return 0;
    }

    public double getMoveOffsetY() {
        return 3d;
    }

    public double getMoveOffsetZ() {
        return 0;
    }

    public boolean shouldDespawn(double lifetime) {
        return false;
    }

    public void despawn() {
        detachAllChildren();
        removeFromParent();
    }

    public Vector3f getAzimuth() {
        return Vector3f.UNIT_Y.negate();
    }

    public Vector3f getAim() {
        return getAzimuth();
    }

    public Spatial getGeometry() {
        return geomship;
    }
    private boolean isDestroyed = false;
    public void setDestroyed(boolean destroyed){
        this.isDestroyed = destroyed;
    }
    public boolean isDestroyed(){
        return isDestroyed;
    }
    public void destroy(){
        System.out.println("Hero destroyed!");
    }
  */  
}
