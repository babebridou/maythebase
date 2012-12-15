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
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import mygame.Constants;
import com.fairyteller.ld25.entity.Entity;
import com.fairyteller.ld25.entity.Ship;
import com.jme3.math.Vector3f;

/**
 *
 * @author Tom
 */
public class EntityControl extends AbstractControl {
    double lifetime = 0d;
    
    @Override
    protected void controlUpdate(float tpf) {
        lifetime = (lifetime + (double)tpf);
        Spatial spatial = getSpatial();
        boolean despawn = false;
        if(spatial instanceof Destroyable){
            Destroyable destroyable = (Destroyable) spatial;
            if(destroyable.isDestroyed()){
                despawn = true;
                destroyable.destroy();
            }
        }
        if(!despawn&&spatial instanceof Despawner){
            Despawner despawner = (Despawner)spatial;
            despawn = despawn||despawner.shouldDespawn(lifetime);
            if(despawn){
                despawner.despawn();
            }
        }
        if(!despawn){
            if(spatial instanceof Mover){
                Mover mover = (Mover)spatial;
                PositionFunction function = mover.getPositionFunction();
                if(function!=null){
                    double speed = Constants.speed();
                    double xOffset = mover.getMoveOffsetX();
                    double yOffset = mover.getMoveOffsetY();
                    double zOffset = mover.getMoveOffsetZ();
                    double x = function.getX(lifetime * speed, xOffset, yOffset, zOffset);
                    double y = function.getY(lifetime * speed, xOffset, yOffset, zOffset);
                    double z = function.getZ(lifetime * speed, xOffset, yOffset, zOffset);
                    getSpatial().setLocalTranslation((float)x, (float)y, (float)z);
                }
            }
            if(spatial instanceof Shooter){
                Shooter shooter = (Shooter) spatial;
                if(shooter.shouldFire(lifetime)){
                    shooter.shoot(lifetime);
                }
            }
            if(spatial instanceof Damager){
                Damager damager = (Damager)spatial;
                damager.updateDamageBox();
            }
        }
            
            
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
    
    void fire(){
        ((Entity)getSpatial()).fire();
    }
}
