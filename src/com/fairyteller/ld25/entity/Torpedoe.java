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
import com.fairyteller.ld25.control.interfaces.Destroyable;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Tom
 */
public class Torpedoe extends Node implements Mover, Damager, Despawner, Destroyable {
    EntityClass torpedoeClass;
    PositionFunction positionFunction;
    Shooter owner;
    double shootOffsetX;
    double shootOffsetY;
    double shootOffsetZ;
    Vector3f azimuth;
    Geometry spawn;

    int health = 0;
    int damage = 1;
    boolean isDestroyed = false;
    double nextHitPossible = 0;
    double hitInvulnerability = 0.2d;
    boolean justGotHit = false;
    boolean isInvulnerable = false;
    
    double fuel = 2d;

    public TorpedoeClass getTorpedoeClass() {
        return (TorpedoeClass)torpedoeClass;
    }
    
    public Torpedoe(EntityClass entityClass, Shooter owner, PositionFunction function) {
        this.torpedoeClass = entityClass;
        spawn = (Geometry)entityClass.getGeometry().clone();
        spawn.setName("torpedoe");
        setOwner(owner);
        setPositionFunction(function);
        Vector3f local = ((Spatial)owner).getLocalTranslation().add(owner.getShootOffsets());
        setLocalTranslation(local.x, local.y, local.z);
        setShootOffsetX(local.x);
        setShootOffsetY(local.y);
        setShootOffsetZ(local.z);
        this.azimuth = owner.getAim();
//		rotate(0, 0, getAzimuth().angleBetween(Vector3f.UNIT_Y));
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
        BoundingVolume bv = spawn.getWorldBound();
//            System.out.println(owner.toString()+" : "+bv.getCenter().x+", "+bv.getCenter().y);
        CollisionZone.getInstance().mergeKillzones(getOwner(), bv, this);
    }
    
    public Spatial getGeometry(){
        return spawn;
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
        return lifetime>fuel;
    }

    public void despawn() {
        detachAllChildren();
        removeFromParent();
    }

    public Vector3f getAzimuth() {
       return azimuth;
    }

    public void hit(int damage) {
        if(!isInvulnerable && !justGotHit){
          justGotHit = true;
          incomingDamage+=damage;
        }
    }

    public void setDestroyed(boolean destroyed){
        this.isDestroyed = destroyed;
    }
    public boolean isDestroyed(){
        return isDestroyed||health<0;
    }
    public void destroy(){
        System.out.println("Hero destroyed!");
        
    }
    
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return this.damage;
    }

    public double getNextHitPossible() {
        return nextHitPossible;
    }
    
    public boolean isInvulnerable(){
        return isInvulnerable;
    }

    public double getHitInvulnerability() {
        return hitInvulnerability;
    }

    public void setHitInvulnerability(double hitInvulnerability) {
        this.hitInvulnerability = hitInvulnerability;
    }

    public void setInvulnerable(boolean isInvulnerable) {
        this.isInvulnerable = isInvulnerable;
    }

    public void setNextHitPossible(double nextHitPossible) {
        this.nextHitPossible = nextHitPossible;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isJustGotHit() {
        return justGotHit;
    }

    int incomingDamage = 0;
    
    public int getIncomingDamage() {
        return incomingDamage;
    }
    public void setIncomingDamage(int incomingDamage){
        this.incomingDamage = incomingDamage;
    }

    public void setJustGotHit(boolean justGotHit) {
        this.justGotHit = justGotHit;
    }

}
