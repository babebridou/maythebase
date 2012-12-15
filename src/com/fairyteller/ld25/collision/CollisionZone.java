/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.collision;

import com.fairyteller.ld25.control.EntityControl;
import com.fairyteller.ld25.control.interfaces.Shooter;
import com.fairyteller.ld25.entity.Entity;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Tom
 */
public class CollisionZone {

    private static CollisionZone instance;
    public static CollisionZone getInstance(){
        if(instance==null)
            instance = new CollisionZone();
        return instance;
    }
    
    Set<Shooter> entities;
    Map<Shooter, BoundingVolume> killingZone;
    
    public CollisionZone() {
        this.entities = new HashSet<Shooter>();
        this.killingZone = new HashMap<Shooter, BoundingVolume>();
    }
    
    public void addEntity(Shooter entity){
        this.entities.add(entity);
        this.killingZone.put(entity, new BoundingBox(Vector3f.ZERO, Vector3f.ZERO));
    }
    
    public void mergeKillzones(Shooter entity, BoundingVolume bv){
        if(!entities.contains(entity)){
            addEntity(entity);
        }
        this.killingZone.get(entity).mergeLocal(bv);
    }
    
    public void removeEntity(Shooter entity){
        if(entities.contains(entity)){
            this.entities.remove(entity);
            this.killingZone.remove(entity);
        }
    }
    
    
    
}
