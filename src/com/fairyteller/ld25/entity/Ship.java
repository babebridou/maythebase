/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.control.interfaces.Despawner;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author Tom
 */
public class Ship extends Node implements Mover, Shooter, Despawner {

    PositionFunction positionFunction;
    EntityClass projectileClass;
    EntityClass shipClass;

    public Ship(String name) {
        super(name);
        this.projectileClass = new TorpedoeClass(ColorRGBA.Red);
        this.shipClass = new ShipClass(ColorRGBA.Red);
    }

    public void setPositionFunction(PositionFunction positionFunction) {
        this.positionFunction = positionFunction;
    }

    public PositionFunction getPositionFunction() {
        return this.positionFunction;
    }

    public void create(AssetManager assetManager) {
        this.shipClass.init(assetManager);
        this.projectileClass.init(assetManager);
        Geometry spawn = shipClass.getGeometry().clone();
        attachChild(spawn);
    }
    double nextshoot = 1d;
    double delay = 0.2d;

    public boolean shouldFire(double lifetime) {
        return lifetime > nextshoot;
    }

    public void shoot(double lifetime) {
        Torpedoe t = null;

        t = new Torpedoe(projectileClass, this, new PositionFunction() {

            public double getX(double t, double x0, double y0, double z0) {
                return x0+0d;
            }

            public double getY(double t, double x0, double y0, double z0) {
                return y0+t * 4d;
            }

            public double getZ(double t, double x0, double y0, double z0) {
                return z0+0d;
            }
        });

        if (t != null) {
            nextshoot += delay;
            getParent().attachChild(t);
        }
    }

    public Vector3f getShootOffsets() {
        return Vector3f.UNIT_Y.mult(0.5f);
    }

    public double getMoveOffsetX() {
        return 0;
    }

    public double getMoveOffsetY() {
        return 0;
    }

    public double getMoveOffsetZ() {
        return 0;
    }

    public boolean shouldDespawn(double lifetime) {
        return lifetime>4d;
    }

    public void despawn() {
        detachAllChildren();
        removeFromParent();
    }
    
    
    
}
