/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control;

import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import mygame.Constants;
import com.fairyteller.ld25.entity.Entity;

/**
 *
 * @author Tom
 */
public class MissileControl extends AbstractControl {

    PositionFunction function;
    double xOffset = 0d;
    double yOffset = 0d;
    double zOffset = 0d;
    double total = 0d;
    double sqRange = 6d;
    Entity owner;

    public MissileControl(Entity owner) {
        this.owner = owner;
    }

    @Override
    protected void controlUpdate(float tpf) {

        total = (total + (double) tpf);
        double speed = Constants.speed();
        double x = function.getX(total * speed, xOffset, yOffset, zOffset);
        double y = function.getY(total * speed, xOffset, yOffset, zOffset);
        double z = function.getZ(total * speed, xOffset, yOffset, zOffset);
        if (x * x + y * y + z * z > sqRange) {
            getSpatial().removeFromParent();
        } else {
            getSpatial().setLocalTranslation((float) xOffset + (float) x, (float) yOffset + (float) y, (float) zOffset + (float) z);
        }

//        if (owner != null) {
//            BoundingVolume pkBoundingVolume = Constants.pkVolume(owner);
//            if (pkBoundingVolume != null) {
//                pkBoundingVolume.mergeLocal(getSpatial().getWorldBound());
//            }
//        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        MissileControl res = new MissileControl(owner);
        res.setFunction(function);
        Vector3f local = spatial.getLocalTranslation();
        res.xOffset = local.x;
        res.yOffset = local.y;
        res.zOffset = local.z;
        System.out.println("spawning from x = " + res.xOffset);
        return res;
    }

    public void setFunction(PositionFunction function) {
        this.function = function;
    }

    public MissileControl() {
    }
}
