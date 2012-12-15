/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.fairyteller.ld25.control.EntityControl;
import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class Wave {
    LinkedList<Ship> ships;
    int count;
    PositionFunction function;
    
    public Wave(int count, PositionFunction function, double delay) {
        this.count = count;
        this.function = function;
        this.delay = delay;
        this.nextpop = delay;
    }
    
    public void create(AssetManager assetManager){
        this.ships = new LinkedList<Ship>();
        for(int i = 0; i<count;i++){
            Ship ship = new Ship("ship_"+i);
            ship.create(assetManager);
            ship.addControl(new EntityControl());
            ship.setPositionFunction(function);
            this.ships.push(ship);
        }
        
    }
    double lifetime = 0;
    double delay = 0;
    double nextpop;
    public void update(float tpf, float speed, Node rootNode){
        lifetime = (lifetime + (double)tpf);
        if(lifetime>nextpop){
            nextpop+=delay;
            if(!this.ships.isEmpty()){
                Ship ship = this.ships.pop();
                rootNode.attachChild(ship);
            }
        }
    }
    
    
}
