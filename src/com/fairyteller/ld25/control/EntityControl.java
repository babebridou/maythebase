/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control;

import com.fairyteller.ld25.collision.CollisionZone;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.control.interfaces.Damager;
import com.fairyteller.ld25.control.interfaces.Despawner;
import com.fairyteller.ld25.control.interfaces.Destroyable;
import com.fairyteller.ld25.control.interfaces.Mover;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.fairyteller.ld25.entity.Ship;
import com.fairyteller.ld25.entity.Torpedoe;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import mygame.Constants;
import com.jme3.math.Vector3f;

/**
 *
 * @author Tom
 */
public class EntityControl extends AbstractControl {

    double lifetime = 0d;
    
    @Override
    protected void controlUpdate(float tpf) {
        lifetime = (lifetime + (double) tpf);
        Spatial spatial = getSpatial();
        boolean despawn = false;
        if(spatial instanceof Torpedoe){
            ((Torpedoe)spatial).getTorpedoeClass().toggle(false, spatial);
        }
        if (spatial instanceof Destroyable) {
            Destroyable destroyable = (Destroyable) spatial;
            if(destroyable.isJustGotHit() && !destroyable.isInvulnerable()){
                if(destroyable instanceof Ship)
                    ((Ship)destroyable).getShipClass().toggle(true, spatial);
                destroyable.setInvulnerable(true);
                destroyable.setNextHitPossible(lifetime + destroyable.getHitInvulnerability());
                
                destroyable.setHealth(destroyable.getHealth() - destroyable.getIncomingDamage());
                destroyable.setIncomingDamage(0);
                System.out.println(spatial.toString()+" : "+destroyable.getHealth()+" health left");
            }
            if(lifetime>destroyable.getNextHitPossible()){
                if(destroyable instanceof Ship)
                    ((Ship)destroyable).getShipClass().toggle(false, spatial);
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
                PositionFunction function = mover.getPositionFunction();
                if (function != null) {
                    double speed = Constants.speed();
                    double xOffset = mover.getMoveOffsetX();
                    double yOffset = mover.getMoveOffsetY();
                    double zOffset = mover.getMoveOffsetZ();
                    double x = function.getX(lifetime * speed);
                    double y = function.getY(lifetime * speed);
                    double z = function.getZ(lifetime * speed);
                    //Vector3f v = new Vector3f((float)x, (float)y, (float)z);
                    double a = Vector3f.UNIT_Y.angleBetween(mover.getAzimuth());
                    getSpatial().setLocalTranslation((float) xOffset + (float) (x * Math.cos(a) + y * Math.sin(a)), (float) yOffset + (float) (Math.sin(a) * x + Math.cos(a) * y), (float) zOffset + (float) z);
                }
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
