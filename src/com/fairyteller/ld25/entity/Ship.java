/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.entity.gear.TorpedoeClass;
import com.fairyteller.ld25.control.interfaces.Despawner;
import com.fairyteller.ld25.control.interfaces.Destroyable;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Seeker;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;

/**
 *
 * @author Tom
 */
public class Ship extends Node implements Mover, Shooter, Despawner, Destroyable, Seeker {

  Team team;
  Spatial shipSpatial;
  Spatial shipHitSpatial;
  PositionFunction positionFunction;
  EntityClass projectileClass;
  EntityClass shipClass;
  double nextshoot = 1d;
  double shootDelay = 0.2d;
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
//  Ship subShip;
  
  long score=0;

  public long getScore(){
	return this.score;
  }
  
  public Ship(Team team, String name, Wave wave) {
	this(team, name, wave, new TorpedoeClass(ColorRGBA.Red), new ShipClass(ColorRGBA.Red), Vector3f.UNIT_Y);
  }

  public Ship(Team team, String name, Wave wave, EntityClass projectileClass, EntityClass shipClass, Vector3f azimuth) {
	super(name);
	this.projectileClass = projectileClass;
	this.shipClass = shipClass;
	this.azimuth = azimuth;
	this.wave = wave;
	this.team = team;
	this.score+=shipClass.getScore();
	setShootDelay(shipClass.getBaseShootDelay());
	
  }

  public void attachSubShip(final Ship subShip) {
	if (subShip.getParent() != null) {
	  subShip.getParent().detachChild(subShip);
	}
	subShip.team = team;
	subShip.setPositionFunction(new PositionFunction() {

	  public double getX(double t, double dt) {
		return -subShip.getShootOffsets().x;
	  }

	  public double getY(double t, double dt) {
		return 0d;
	  }

	  public double getZ(double t, double dt) {
		return 0d;
	  }
	});
	subShip.setAzimuth(getAzimuth().negate());
	attachChild(subShip);
	this.score+=subShip.getScore();
  }

  public double getShootDelay() {
	return shootDelay;
  }

  public void setShootDelay(double delay) {
	this.shootDelay = delay;
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
	setFuel(this.shipClass.getBaseFuel());
	if (this.projectileClass != null) {
	  this.projectileClass.init(assetManager);
	}
	shipSpatial = shipClass.getGeometry().clone();

	attachChild(shipSpatial);
	rotate(0, 0, getAzimuth().angleBetween(Vector3f.UNIT_Y));
  }

  public boolean shouldFire(double lifetime) {
	return lifetime > nextshoot;
  }

  protected void shoot(Ship s, Torpedoe t, boolean isHoming) {
	if (t == null) {
	  PositionFunction f = s.projectileClass.getPositionFunction() != null ? s.projectileClass.getPositionFunction() : new PositionFunction() {

		public double getX(double t, double dt) {
		  return 0d;
		}

		public double getY(double t, double dt) {
		  return 4d * t;
		}

		public double getZ(double t, double dt) {
		  return 0d;
		}
	  };
	  t = new Torpedoe(s.projectileClass, s, f);
	}
	if (!(getParent() instanceof Ship)) {
	  if (t != null) {
		s.nextshoot += s.shootDelay;
		t.initPositionAt(this, isHoming);
		getParent().attachChild(t);
		this.score+=t.getScore();
		wave.shotAmmo(this, t.getScore());
		return;
	  }
	}
	((Ship) getParent()).shoot(s, t, isHoming);
  }

  public void shoot(double lifetime) {
	shoot(this, null, this.getShipClass().isHoming());
  }
  Vector3f shootOffsets;

  public Vector3f getShootOffsets() {
	if (shootOffsets == null) {
	  shootOffsets = new Vector3f(0.0f, 0.5f, 0.0f);//getAzimuth().mult(0.5f);
	}
	return shootOffsets;
  }

  public void setShootOffsets(Vector3f shootOffsets) {
	this.shootOffsets = shootOffsets;
  }
  private boolean exploding = false;
  private double deathAnimationStart = 0d;
  private double deathAnimationEnd = 0d;

  public boolean isExploding() {
	return this.exploding;
  }

  public void animateExplode(double lifetime) {
	double slerp = (deathAnimationEnd - lifetime) / (deathAnimationEnd - deathAnimationStart);
	((ShipClass) shipClass).animateMyExplosion(this, slerp);
	if(slerp>0.4d){
	  if(hasChild(shipSpatial)){
		int detachChild = detachChild(shipSpatial);
	  }
	}
  }

  public boolean shouldDespawn(double lifetime) {
	return (exploding && (lifetime > deathAnimationEnd)) || lifetime > fuel;
  }

  public void despawn() {
	detachAllChildren();
	removeFromParent();
	
	if (wave != null) {
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

  public void setAzimuth(Vector3f azimuth) {
	this.azimuth = azimuth;
  }

  public Vector3f getAzimuth() {
	return azimuth;
  }
  Vector3f aim = null;

  private Vector3f getAimRecursive(Spatial s, boolean isHomingSource) {
	if (!(s.getParent() instanceof Ship)) {
	  if (!isHomingSource||aim == null) {
		return getAzimuth();
	  }
	  return aim.clone();
	} else {
	  return getAimRecursive(s.getParent(), isHomingSource);
	}
  }

  public Vector3f getAim() {
	return getAimRecursive(this, shipClass.isHoming());
//	if(aim==null)
//	  return getAzimuth();
//	
//	return aim;
  }

  public Spatial getGeometry() {
	return shipSpatial;
  }

  public void setDestroyed(boolean destroyed) {
	this.isDestroyed = destroyed;
  }

  public boolean isDestroyed() {
	return isDestroyed || health < 0;
  }

  public void destroy(double lifetime) {
	System.out.println("Hero destroyed!");
	this.exploding = true;
	this.deathAnimationStart = lifetime;
	this.deathAnimationEnd = lifetime + 6 * 0.1d;
  }

  public ShipClass getShipClass() {
	return (ShipClass) shipClass;
  }

  public void hit(int damage) {
	if (!isInvulnerable && !justGotHit) {
	  justGotHit = true;
	  incomingDamage += damage;
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

  public boolean isInvulnerable() {
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

  public void setFuel(double fuel) {
	this.fuel = fuel;
  }

  public double getFuel() {
	return fuel;
  }

  public boolean isJustGotHit() {
	return justGotHit;
  }
  int incomingDamage = 0;

  public int getIncomingDamage() {
	return incomingDamage;
  }

  public void setIncomingDamage(int incomingDamage) {
	this.incomingDamage = incomingDamage;
  }

  public void setJustGotHit(boolean justGotHit) {
	this.justGotHit = justGotHit;
  }

  public Vector3f seek() {

	if (team != null && team.currentTarget != null) {
	  Vector3f v = team.currentTarget.getWorldTranslation();
	  return v;
	}
	return null;
  }

  public void aim(Vector3f target) {
	  aim = target == null ? null : target.subtract(getWorldTranslation());
	  if (aim != null) {
//	  aim.normalizeLocal();
		//lookAt(aim, Vector3f.UNIT_Z);
	  }
  }

  public Team getTeam() {
	return team;
  }

  public Spatial getShipHitSpatial() {
	return shipHitSpatial;
  }

  public void setShipHitSpatial(Spatial shipExplosionSpatial) {
	this.shipHitSpatial = shipExplosionSpatial;
  }
}
