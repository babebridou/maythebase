/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.collision;

import com.fairyteller.ld25.control.interfaces.Shooter;
import com.fairyteller.ld25.entity.Ship;
import com.fairyteller.ld25.entity.Torpedoe;
import com.fairyteller.ld25.entity.Wave;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Tom
 */
public class CollisionZone {
    
    private static CollisionZone instance;
    
    public static CollisionZone getInstance() {
        if (instance == null) {
            instance = new CollisionZone();
        }
        return instance;
    }
    Set<Shooter> entities;
    Map<Shooter, BoundingVolume> potentialZone;
    Map<Shooter, List<Torpedoe>> killingZone;
    
    public CollisionZone() {
        this.entities = new HashSet<Shooter>();
        this.killingZone = new HashMap<Shooter, List<Torpedoe>>();
        this.potentialZone = new HashMap<Shooter, BoundingVolume>();
    }
    
    public void addEntity(Shooter entity) {
        this.entities.add(entity);
        
    }
    
    public void mergeKillzones(Shooter entity, BoundingVolume bv, Torpedoe torpedoe) {
        
        if (!entities.contains(entity) || !killingZone.containsKey(entity) || !potentialZone.containsKey(entity)) {
            addEntity(entity);
        }
        BoundingVolume potentialVolume = this.potentialZone.get(entity);
        if (potentialVolume == null) {
            this.potentialZone.put(entity, bv);
        } else {
            potentialVolume.mergeLocal(bv);
            potentialZone.put(entity, potentialVolume);//raaaaaah pause
        }
        
        List<Torpedoe> bvs = this.killingZone.get(entity);
        if (bvs == null) {
            bvs = new ArrayList<Torpedoe>();
            this.killingZone.put(entity, bvs);
        }
        bvs.add(torpedoe);
    }
    
    public void removeEntity(Shooter entity) {
        if (entities.contains(entity)) {
            this.entities.remove(entity);
            this.potentialZone.remove(entity);
            this.killingZone.remove(entity);
        }
    }
    
    public Set<Shooter> getEntities() {
        return entities;
    }
    
    public Map<Shooter, List<Torpedoe>> getKillingZone() {
        return killingZone;
    }
    
    public void check(Wave wave) {
        if (wave.getLaunchedShips() != null && !wave.getLaunchedShips().isEmpty()) {
            for (Ship ship : wave.getLaunchedShips()) {
                check(ship);                
            }
        }
    }
    
    public static String debug(BoundingVolume bv) {
        if (bv != null) {
            BoundingBox bb = (BoundingBox) bv;
            
            return "" + bb.getCenter().x + ", " + bb.getCenter().y + " : " + bb.getVolume() + ", " + bb.toString();
        }
        return "bv is null";
    }
    
    public void check(Ship hero) {
        Set<Shooter> aces = getEntities();
        for (Shooter s : aces) {
            if (s != hero) {
                CollisionResults potentialResults = new CollisionResults();
                BoundingVolume potential = potentialZone.get(s);
                if (potential != null && hero.getGeometry() != null) {
                    hero.getGeometry().collideWith(potential, potentialResults);
                    if (potentialResults.size() > 0) {
                        
                        List<Torpedoe> bvs = killingZone.get(s);
                        if (bvs != null && !bvs.isEmpty()) {
                            for (Torpedoe bv : bvs) {
                                CollisionResults results = new CollisionResults();
                                if (bv != null && hero.getGeometry() != null && bv.getWorldBound() != null) {
                                    hero.getGeometry().collideWith(bv.getWorldBound(), results);
                                    if (results.size() > 0 && results.getClosestCollision().getGeometry() != null) {
//                                        bv.getTorpedoeClass().toggle(true, bv);
                                        bv.hit(2);
//                            System.out.println(hero+" : impact at "+debug(hero.getGeometry().getWorldBound()));
//                            System.out.println("w/ "+bv+" : at "+debug(bv.getGeometry().getWorldBound()));
                                        hero.hit(s.getDamage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void purgeBoundingBoxes() {
        Iterator<Shooter> iter = getEntities().iterator();
        while (iter.hasNext()) {
            Shooter s = iter.next();
            iter.remove();
            potentialZone.remove(s);
            killingZone.remove(s);
        }
    }
}
