/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.fairyteller.ld25.entity.EntityClass;
import com.fairyteller.ld25.entity.ShipClass;
import com.fairyteller.ld25.entity.Team;
import com.fairyteller.ld25.entity.Wave;
import com.fairyteller.ld25.entity.gear.LaserCannonClass;
import com.fairyteller.ld25.entity.gear.LaserClass;
import com.fairyteller.ld25.entity.gear.TorpedoeClass;
import com.fairyteller.ld25.entity.gear.LeftCannonClass;
import com.fairyteller.ld25.entity.gear.RightCannonClass;
import com.jme3.math.ColorRGBA;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class Constants {
	
  public static boolean isRunning = true;
  public static Team red = new Team("Red");
  public static Team blue = new Team("Blue");
  public static boolean cleaning = false;
  public static LinkedList<Wave> waves = new LinkedList<Wave>();
  public static LinkedList<Wave> heroWaves = new LinkedList<Wave>();
  
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
	
	public static EntityClass cyanTorpedoe = new TorpedoeClass(ColorRGBA.Cyan);
	public static EntityClass redTorpedoe = new TorpedoeClass(ColorRGBA.Red);
	public static EntityClass greenTorpedoe = new TorpedoeClass(ColorRGBA.Green);
	public static EntityClass leftCannon = new LeftCannonClass(null, 15, 50, 1d);
	public static EntityClass rightCannon = new RightCannonClass(null, 2, 50, 0.3d);
	public static EntityClass cyanShip = new ShipClass(ColorRGBA.Cyan, 3, 100, 999d);
	public static EntityClass redShip = new ShipClass(ColorRGBA.Red, 15, 60, 30d);
	public static EntityClass grayShip = new ShipClass(null);
	public static EntityClass greenShip = new ShipClass(ColorRGBA.Green, 5, 30, 5d);
	
	public static EntityClass yellowLaser = new LaserClass(ColorRGBA.Yellow);
	public static EntityClass laserCannon = new LaserCannonClass(null);
	static{
	  cyanTorpedoe.setScore(10);
	  redTorpedoe.setScore(10);
	  greenTorpedoe.setScore(10);
	  leftCannon.setScore(350);
	  rightCannon.setScore(1000);
	  cyanShip.setScore(100000);
	  redShip.setScore(4000);
	  grayShip.setScore(200);
	  greenShip.setScore(550);
	  yellowLaser.setScore(1);
	  laserCannon.setScore(1700);
	}
}
