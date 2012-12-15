/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class HeroShip extends Node implements Mover, Shooter {

    Geometry geomship;
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
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/sample32.png");
        mat.setTexture("ColorMap", texture);
        geomship.setMaterial(mat);
        rotate(0, 0, (float) Math.PI);
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

            public double getX(double t, double x0, double y0, double z0) {
                return x0 + 0d;
            }

            public double getY(double t, double x0, double y0, double z0) {
                return y0 - t * 4d;
            }

            public double getZ(double t, double x0, double y0, double z0) {
                return z0 + 0d;
            }
        });

        if (t != null) {
            nextshoot += delay;
            getParent().attachChild(t);
        }
    }

    public Vector3f getShootOffsets() {
        return Vector3f.UNIT_Y.negate().mult(0.5f);
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
}
