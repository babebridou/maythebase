/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.collision.CollisionZone;
import com.fairyteller.ld25.control.EntityControl;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Damager;
import com.fairyteller.ld25.control.interfaces.Despawner;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Tom
 */
public class Torpedoe extends Node implements Mover, Damager, Despawner {
    TorpedoeClass torpedoeClass;

    PositionFunction positionFunction;
    Shooter owner;
    double shootOffsetX;
    double shootOffsetY;
    double shootOffsetZ;

    public Torpedoe(EntityClass entityClass, Shooter owner, PositionFunction function) {
        Geometry spawn = entityClass.getGeometry().clone();
        setOwner(owner);
        setPositionFunction(function);
        Vector3f local = ((Spatial)owner).getLocalTranslation().add(owner.getShootOffsets());
        setLocalTranslation(local.x, local.y, local.z);
        setShootOffsetX(local.x);
        setShootOffsetY(local.y);
        setShootOffsetZ(local.z);
        addControl(new EntityControl());
        spawn.setMaterial(entityClass.getMaterial());
        attachChild(spawn);
    }

    public Shooter getOwner() {
        return owner;
    }

    public void setOwner(Shooter owner) {
        this.owner = owner;
    }

    public PositionFunction getPositionFunction() {
        return positionFunction;
    }

    public void setPositionFunction(PositionFunction positionFunction) {
        this.positionFunction = positionFunction;
    }

    public double getShootOffsetX() {
        return shootOffsetX;
    }

    public void setShootOffsetX(double shootOffsetX) {
        this.shootOffsetX = shootOffsetX;
    }

    public double getShootOffsetY() {
        return shootOffsetY;
    }

    public void setShootOffsetY(double shootOffsetY) {
        this.shootOffsetY = shootOffsetY;
    }

    public double getShootOffsetZ() {
        return shootOffsetZ;
    }

    public void setShootOffsetZ(double shootOffsetZ) {
        this.shootOffsetZ = shootOffsetZ;
    }

    public void updateDamageBox() {
        CollisionZone.getInstance().mergeKillzones(getOwner(), getWorldBound());
    }

    public double getMoveOffsetX() {
        return getShootOffsetX();
    }

    public double getMoveOffsetY() {
        return getShootOffsetY();
    }

    public double getMoveOffsetZ() {
        return getShootOffsetZ();
    }

    public boolean shouldDespawn(double lifetime) {
        return lifetime>0.5d;
    }

    public void despawn() {
        detachAllChildren();
        removeFromParent();
    }
}
