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
    LinkedList<Ship> ships;
    List<Ship> launchedShips;
    int count;
    PositionFunction function;
    double offsetX;
    double offsetY;
    double offsetZ;
    EntityClass projectileClass;
    EntityClass shipClass;
    Vector3f azimuth;
    String name;
    
    public Wave(String name, 
            int count, 
            PositionFunction function, 
            double delay, 
            double offsetX, 
            double offsetY, 
            double offsetZ, 
            EntityClass projectileClass, 
            EntityClass shipClass, 
            Vector3f azimuth) {
        this.count = count;
        this.function = function;
        this.delay = delay;
        this.nextpop = delay;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.projectileClass = projectileClass;
        this.shipClass = shipClass;
        this.azimuth = azimuth;
        this.name = name;
    }
    
    public void create(AssetManager assetManager){
        this.ships = new LinkedList<Ship>();
        for(int i = 0; i<count;i++){
            Ship ship = new Ship(name+"_"+i, this, projectileClass, shipClass, azimuth);
            ship.create(assetManager);
            ship.addControl(new EntityControl());
            ship.setMoveOffsetX(offsetX);
            ship.setMoveOffsetY(offsetY);
            ship.setMoveOffsetZ(offsetZ);
             ship.setPositionFunction(function);
            this.ships.push(ship);
        }
        
    }
    double lifetime = 0;
    double delay = 0;
    double nextpop;
    public void update(float tpf, float speed, Node rootNode){
        lifetime = (lifetime + (double)tpf);
        if(!this.ships.isEmpty()){
            if(lifetime>nextpop){
                nextpop+=delay;
                if(!this.ships.isEmpty()){
                    Ship ship = this.ships.pop();
                    if(launchedShips==null){
                        launchedShips = new ArrayList<Ship>();
                    }
                    launchedShips.add(ship);
                    rootNode.attachChild(ship);
                }
            }
        }
    }
    
    public void destroyShip(Ship ship){
        this.launchedShips.remove(ship);
    }

    public List<Ship> getLaunchedShips() {
        return launchedShips;
    }
    
}
