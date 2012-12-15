/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.fairyteller.ld25.entity.Entity;
import com.jme3.bounding.BoundingVolume;

/**
 *
 * @author Tom
 */
public class Constants {
    public static float SCALE_X = 4.0f;
    private static Main instanceMain;
    public static void setInstanceMain(Main main){
        instanceMain = main;
    }
    public static float speed(){
        return instanceMain.getSpeed();
    }
    
//    public static BoundingVolume pkVolume(Entity player){
//        return instanceMain.getPlayerKillingBoundingVolume(player);
//    }
}
