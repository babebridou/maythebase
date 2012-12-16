/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control;

import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Damager;
import com.fairyteller.ld25.control.interfaces.Despawner;
import com.fairyteller.ld25.control.interfaces.Destroyable;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Seeker;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.fairyteller.ld25.entity.Ship;
import com.fairyteller.ld25.entity.Torpedoe;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import mygame.Constants;
import com.jme3.math.Vector3f;
import com.sun.xml.internal.ws.message.saaj.SAAJHeader;

/**
 *
 * @author Tom
 */
public class EntityControl extends AbstractControl {
  
  double lifetime = 0d;
  static boolean debug = true;
  
  static final double computeX(double x, double y, double a){
	double A = a;
//	double X = (float) Math.cos(A)*(x * Math.cos(A) - y * Math.sin(A));
	double X = (float) x * Math.cos(A) + y * Math.sin(A);
	return X;
  }
  static final double computeY(double x, double y, double a){
	double A = a;
	double Y = (float) (Math.sin(A) * x + Math.cos(A) * y);
	return Y;
  }
  
  private final void debugMover(Spatial spatial, float tpf) {
	if (spatial instanceof Torpedoe) {
	  Mover mover = (Mover) spatial;
	  PositionFunction function = mover.getPositionFunction();
	  if (function != null) {
		double speed = Constants.speed();
		double xOffset = mover.getMoveOffsetX();
		double yOffset = mover.getMoveOffsetY();
		double x = function.getX(lifetime * speed, tpf);
		double y = function.getY(lifetime * speed, tpf);
		//Vector3f v = new Vector3f((float)x, (float)y, (float)z);
		double a = Vector3f.UNIT_Y.dot(mover.getAzimuth().normalize());
		double X0 = spatial.getLocalTranslation().x;
		double Y0 = spatial.getLocalTranslation().y;
		double X = computeX(x, y, a);
		double Y = computeY(x,y,a);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("owner: ");
		sb.append(((Damager) spatial).getOwner());
		sb.append("lifetime : ");
		sb.append(lifetime);
		sb.append(", xoffset: ");
		sb.append(mover.getMoveOffsetX());
		sb.append(", yoffset: ");
		sb.append(mover.getMoveOffsetY());
		sb.append(", f(x): ");
		sb.append(x);
		sb.append(", f(y): ");
		sb.append(y);
		sb.append(", azimuthX: ");
		sb.append(mover.getAzimuth().x);
		sb.append(", azimuthY: ");
		sb.append(mover.getAzimuth().y);
		sb.append(", angle: ");
		sb.append(a);

		sb.append(", X: ");
		sb.append(X-X0);
		sb.append(", Y: ");
		sb.append(Y-Y0);
		sb.append("}");
		System.out.println(sb.toString());
	  }
	}
  }
  
  @Override
  protected void controlUpdate(float tpf) {
	lifetime = (lifetime + (double) tpf);
	Spatial spatial = getSpatial();
	boolean despawn = false;
	if (spatial instanceof Destroyable) {
	  Destroyable destroyable = (Destroyable) spatial;
	  if (destroyable.isJustGotHit() && !destroyable.isInvulnerable()) {
		if (destroyable instanceof Ship) {
		  ((Ship) destroyable).getShipClass().toggle(true, spatial);
		}
		destroyable.setInvulnerable(true);
		destroyable.setNextHitPossible(lifetime + destroyable.getHitInvulnerability());
		
		destroyable.setHealth(destroyable.getHealth() - destroyable.getIncomingDamage());
		destroyable.setIncomingDamage(0);
		System.out.println(spatial.toString() + " : " + destroyable.getHealth() + " health left");
	  }
	  if (lifetime > destroyable.getNextHitPossible()) {
		if (destroyable instanceof Ship) {
		  ((Ship) destroyable).getShipClass().toggle(false, spatial);
		}
		destroyable.setInvulnerable(false);
		destroyable.setJustGotHit(false);
	  }
	  if (destroyable.isDestroyed()) {
		despawn = true;
		destroyable.destroy();
	  }
	}
	if (!despawn && spatial instanceof Despawner) {
	  Despawner despawner = (Despawner) spatial;
	  despawn = despawn || despawner.shouldDespawn(lifetime);
	  
	}
	if (despawn) {
	  Despawner despawner = (Despawner) spatial;
	  despawner.despawn();
	}
	
	if (!despawn) {
	  if (spatial instanceof Mover) {
		Mover mover = (Mover) spatial;
//		debugMover(spatial, tpf);
		PositionFunction function = mover.getPositionFunction();
		if (function != null) {
		  double speed = Constants.speed();
		  double xOffset = mover.getMoveOffsetX();
		  double yOffset = mover.getMoveOffsetY();
		  double zOffset = mover.getMoveOffsetZ();
		  double x = function.getX(lifetime * speed, tpf);
		  double y = function.getY(lifetime * speed, tpf);
		  double z = function.getZ(lifetime * speed, tpf);
		  //Vector3f v = new Vector3f((float)x, (float)y, (float)z);
		  double a = Vector3f.UNIT_Y.angleBetween(mover.getAzimuth().normalize());//Math.acos(Vector3f.UNIT_Y.dot(mover.getAzimuth().normalize()));
		  if(mover.getAzimuth().x<0){
			a = 2d*Math.PI-a;
		  }
		  
		  double X = computeX(x, y, a);
		  double Y = computeY(x,y,a);
//		  Quaternion q = Quaternion.IDENTITY;
//		  q.fromAngleAxis((float)a, Vector3f.UNIT_Z);
//		  getSpatial().setLocalRotation(q);
//		  getSpatial().move((float)X,(float)Y,0f);
//		  getSpatial().move((float) xOffset + (float) X, (float) yOffset + (float) Y, (float) zOffset + (float) z);
		  getSpatial().setLocalTranslation((float) xOffset + (float)X, (float) yOffset+(float)Y, (float) zOffset);
		  
		}
	  }
	  if (spatial instanceof Seeker) {
		Seeker seeker = (Seeker) spatial;
		Vector3f v = seeker.seek();
		seeker.aim(v);
	  }
	  if (spatial instanceof Shooter) {
		Shooter shooter = (Shooter) spatial;
		if (shooter.shouldFire(lifetime)) {
		  shooter.shoot(lifetime);
		}
	  }
	  if (spatial instanceof Damager) {
		Damager damager = (Damager) spatial;
		damager.updateDamageBox();
	  }

	}
	//recompute world bound
	spatial.getWorldBound();

//        if(spatial.getName()==null||spatial.getName().startsWith("hero")){
//            System.out.println(spatial.getName()+CollisionZone.debug(spatial.getWorldBound()));
//        }
  }
  
  @Override
  protected void controlRender(RenderManager rm, ViewPort vp) {
  }
  
  @Override
  public Control cloneForSpatial(Spatial spatial) {
	EntityControl res = new EntityControl();
	return res;
  }
  
  public EntityControl() {
  }
}
