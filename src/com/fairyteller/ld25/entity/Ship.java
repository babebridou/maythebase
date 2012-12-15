/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.control.interfaces.Despawner;
import com.fairyteller.ld25.control.interfaces.Destroyable;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Tom
 */
public class Ship extends Node implements Mover, Shooter, Despawner, Destroyable {
    Spatial shipSpatial;
    PositionFunction positionFunction;
    EntityClass projectileClass;
    EntityClass shipClass;

    double nextshoot = 1d;
    double delay = 0.2d;
    double fuel = 30d;
    Vector3f azimuth;
    boolean isDestroyed = false;
    Wave wave;
    
    int health;
    int damage;
    
    double nextHitPossible = 0;
    double hitInvulnerability = 0.2d;
    boolean justGotHit = false;
    boolean isInvulnerable = false;
    
    
    public Ship(String name, Wave wave) {
        this(name, wave, new TorpedoeClass(ColorRGBA.Red), new ShipClass(ColorRGBA.Red), Vector3f.UNIT_Y);
    }
    
    public Ship(String name, Wave wave, EntityClass projectileClass, EntityClass shipClass, Vector3f azimuth) {
        super(name);
        this.projectileClass = projectileClass;
        this.shipClass = shipClass;
        this.azimuth = azimuth;
        this.wave = wave;
    }

    public void setPositionFunction(PositionFunction positionFunction) {
        this.positionFunction = positionFunction;
    }

    public PositionFunction getPositionFunction() {
        return this.positionFunction;
    }

    public void create(AssetManager assetManager) {
        this.shipClass.init(assetManager);
        setDamage(this.shipClass.getBaseDamage());
        setHealth(this.shipClass.getBaseHealth());
        this.projectileClass.init(assetManager);
        shipSpatial = shipClass.getGeometry().clone();
        rotate(0, 0, getAzimuth().angleBetween(Vector3f.UNIT_Y));
        attachChild(shipSpatial);
        
    }
    

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

    public boolean shouldDespawn(double lifetime) {
        return lifetime>fuel;
    }

    public void despawn() {
        detachAllChildren();
        removeFromParent();
        if(wave!=null){
            wave.destroyShip(this);
        }
    }
    
    double moveOffsetX;
    double moveOffsetY;
    double moveOffsetZ;

    public double getMoveOffsetX() {
        return moveOffsetX;
    }

    public void setMoveOffsetX(double moveOffsetX) {
        this.moveOffsetX = moveOffsetX;
    }

    public double getMoveOffsetY() {
        return moveOffsetY;
    }

    public void setMoveOffsetY(double moveOffsetY) {
        this.moveOffsetY = moveOffsetY;
    }

    public double getMoveOffsetZ() {
        return moveOffsetZ;
    }

    public void setMoveOffsetZ(double moveOffsetZ) {
        this.moveOffsetZ = moveOffsetZ;
    }

    public void setAzimuth(Vector3f azimuth){
        this.azimuth = azimuth;
    }
   
    public Vector3f getAzimuth() {
        return azimuth;
    }
    public Vector3f getAim(){
        return getAzimuth();
    }

   public Spatial getGeometry() {
        return shipSpatial;
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
    
    public ShipClass getShipClass(){
        return (ShipClass)shipClass;
    }

    
    
    public void hit(int damage) {
        if(!isInvulnerable && !justGotHit){
          justGotHit = true;
          incomingDamage+=damage;
        }
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
