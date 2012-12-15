/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.fairyteller.ld25.entity.Entity;
import com.fairyteller.ld25.control.MissileControl;
import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Tom
 */
public class Missile {
    Material mat;
    Box box;
    MissileControl control;
    long indice;
    PositionFunction function;
    Geometry geom;
    

    public Missile() {
        
    }
    
    public void create(AssetManager assetManager){
        box = new Box(Vector3f.ZERO, 0.10f, 0.10f, 0f);
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/sampleFire.png");
        mat.setTexture("ColorMap", texture);
        function = new PositionFunction() {

            public double getX(double t, double x0, double y0, double z0) {
                return 0;
            }

            public double getY(double t, double x0, double y0, double z0) {
                return t*4d;
            }

            public double getZ(double t, double x0, double y0, double z0) {
                return 0;
            }
        };
        geom = new Geometry("missile", box);
    }

    public void addControl(MissileControl control){
        this.control = control;
        this.control.setFunction(function);
    }
    
    public Geometry spawn(Entity player){
      Geometry spawn = geom.clone();
      spawn.setLocalTranslation(player.getLocalTranslation().x, player.getLocalTranslation().y, player.getLocalTranslation().z);
      spawn.addControl(control.cloneForSpatial(spawn));
      spawn.setMaterial(mat);
      
      return spawn;
    }
}
