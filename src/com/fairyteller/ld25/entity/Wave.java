/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.control.EntityControl;
import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tom
 */
public class Wave {

  Team team;
  LinkedList<Ship> ships;
  List<Ship> launchedShips;
  int count;
  PositionFunction function;
  double offsetX;
  double offsetY;
  double offsetZ;
  List<EntityClass> projectileClasses;
  EntityClass shipClass;
  List<EntityClass> cannonClasses;
  Vector3f azimuth;
  String name;
  boolean active = true;

  public Wave(Team team, String name,
	  int count,
	  PositionFunction function,
	  double delay,
	  double offsetX,
	  double offsetY,
	  double offsetZ,
	  List<EntityClass> projectileClasses,
	  EntityClass shipClass,
	  List<EntityClass> cannonClasses,
	  Vector3f azimuth) {
	this.count = count;
	this.function = function;
	this.delay = delay;
	this.nextpop = delay;
	this.offsetX = offsetX;
	this.offsetY = offsetY;
	this.offsetZ = offsetZ;
	this.projectileClasses = projectileClasses;
	this.cannonClasses = cannonClasses;
	this.shipClass = shipClass;
	this.azimuth = azimuth;
	this.name = name;
	this.team = team;
  }

  public void setActive(boolean active) {
	this.active = active;
  }

  public boolean isActive() {
	return active;
  }

  public void create(AssetManager assetManager) {
	System.out.println("Creating ships for team " + team.getName());
	this.ships = new LinkedList<Ship>();
	for (int i = 0; i < count; i++) {
	  Ship ship = new Ship(team, name + "_" + i, this, null, shipClass, azimuth) {

		@Override
		public boolean shouldFire(double lifetime) {
		  return false;
		}

		@Override
		public Vector3f getShootOffsets() {
		  return getAzimuth().mult(0.5f);
		}
	  };
	  ship.create(assetManager);
	  ship.addControl(new EntityControl());
	  ship.setMoveOffsetX(offsetX);
	  ship.setMoveOffsetY(offsetY);
	  ship.setMoveOffsetZ(offsetZ);
	  ship.setPositionFunction(function);
	  for (int j = 0; j < cannonClasses.size(); j++) {
		EntityClass cc = cannonClasses.get(j);
		EntityClass projectileClass = projectileClasses.get(j);
		Ship cannon = new Ship(team, name + "_cannonR_" + i, this, projectileClass, cc, azimuth);
		cc.initShootOffsets(ship, cannon);
		ship.attachSubShip(cannon);
		cannon.create(assetManager);
		cannon.rotate(0, 0, (float) Math.acos(cannon.getAzimuth().normalize().dot(Vector3f.UNIT_Y)));

//	  cannon.setShootOffsets(Vector3f.UNIT_X.mult(ship.getAzimuth().normalize().dot(Vector3f.UNIT_Y)*0.5f));
		cannon.addControl(new EntityControl());
	  }

	  this.ships.push(ship);
	}

  }
  double lifetime = 0;
  double delay = 0;
  double nextpop;

  public void update(float tpf, float speed, Node rootNode) {
	if (isActive()) {
	  lifetime = (lifetime + (double) tpf);
	  synchronized (ships) {
		if (!this.ships.isEmpty()) {
		  if (lifetime > nextpop) {
			nextpop += delay;
			if (!this.ships.isEmpty()) {
			  Ship ship = this.ships.pop();
			  if (launchedShips == null) {
				launchedShips = new ArrayList<Ship>();
			  }
			  launchedShips.add(ship);
			  rootNode.attachChild(ship);
			}
		  }
		}
	  }
	}
  }

  public void destroyShip(Ship ship) {
	this.launchedShips.remove(ship);
	if(this.team==Constants.red){
	  Constants.red.reportLosses(ship.getScore(), this);
	  Constants.blue.reportScore(ship.getScore(), this);
	} else {
	  Constants.blue.reportLosses(ship.getScore(), this);
	  Constants.red.reportScore(ship.getScore(), this);
	}
  }

  public void shotAmmo(Ship ship, long ammoCost){
	if(this.team==Constants.red){
	  Constants.red.reportAmmunition(ammoCost, this);
	} else {
	  Constants.blue.reportAmmunition(ammoCost, this);
	}
  }
  
  
  public List<Ship> getLaunchedShips() {
	return launchedShips;
  }

  public void emptyQueue(Node rootNode) {
	setActive(false);
	synchronized (ships) {
	  Iterator<Ship> iter = ships.iterator();
	  while (iter.hasNext()) {
		iter.next().destroy(lifetime);
		iter.remove();
	  }

	  if (launchedShips != null) {
		for (Ship s : launchedShips) {
		  s.setDestroyed(true);
		  String missileName = "msl_"+s.getName();
		  int i = 0;
		  List<Spatial> missiles = rootNode.descendantMatches(missileName);
		  if(missiles!=null&&missiles.size()>0){
			for(Spatial m : missiles){
			  ((Torpedoe)m).despawn();
			}
		  }
		}
	  }
	}
  }
}
