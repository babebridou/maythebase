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
import java.util.ArrayList;
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

  public void create(AssetManager assetManager) {
	this.ships = new LinkedList<Ship>();
	for (int i = 0; i < count; i++) {
	  Ship ship = new Ship(team, name + "_" + i, this, null, shipClass, azimuth){
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
	  for(int j = 0; j<cannonClasses.size();j++){
		EntityClass cc = cannonClasses.get(j);
		EntityClass projectileClass = projectileClasses.get(j);
		Ship cannon = new Ship(team, name + "_cannonR_" + i, this, projectileClass, cc, azimuth);
	  cc.initShootOffsets(ship, cannon);
	  ship.attachSubShip(cannon);
	  cannon.create(assetManager);
	  cannon.rotate(0, 0, (float)Math.acos(cannon.getAzimuth().normalize().dot(Vector3f.UNIT_Y)));
	  
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
	lifetime = (lifetime + (double) tpf);
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

  public void destroyShip(Ship ship) {
	this.launchedShips.remove(ship);
  }

  public List<Ship> getLaunchedShips() {
	return launchedShips;
  }
}
