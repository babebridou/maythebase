package com.fairyteller.ld25.gui;

import com.fairyteller.ld25.entity.Constants;
import mygame.*;
import com.fairyteller.ld25.collision.CollisionZone;
import com.fairyteller.ld25.entity.EntityClass;
import com.fairyteller.ld25.entity.Team;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.entity.Wave;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import de.lessvoid.nifty.Nifty;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main1 extends SimpleApplication {
	Team red;
	Team blue;
    Wave heroWave;
//    Ship hero;
    LinkedList<Wave> waves;
//    PositionFunction function;
    boolean isRunning = true;

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.WARNING);
        Main1 app = new Main1();
//        Constants.setInstanceMain(app);
        app.start();
    }

    @Override
    public void simpleInitApp() {
	  NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
    assetManager, inputManager, audioRenderer, guiViewPort);
/** Create a new NiftyGUI object */
Nifty nifty = niftyDisplay.getNifty();
/** Read your XML and initialize your custom ScreenController */
nifty.fromXml("Interface/newNiftyGui.xml", "start");
// nifty.fromXml("Interface/helloworld.xml", "start", new MySettingsScreen(data));
// attach the Nifty display to the gui view port as a processor
guiViewPort.addProcessor(niftyDisplay);
//nifty.setDebugOptionPanelColors(true);



flyCam.setEnabled(false);
	  
	  
	  List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
	  List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
	  cannonClasses.add(Constants.leftCannon);
	  projectileClasses.add(Constants.cyanTorpedoe);
	  cannonClasses.add(Constants.rightCannon);
	  projectileClasses.add(Constants.cyanTorpedoe);
	  cannonClasses.add(Constants.laserCannon);
	  projectileClasses.add(Constants.yellowLaser);
	  
	  
	  
	  red = new Team("Red");
	  blue = new Team("Blue");
        waves = new LinkedList<Wave>();
        setPauseOnLostFocus(false);

        PositionFunction functionHeroWave = new PositionFunction() {

            public double getX(double t, double tpf) {
                return Math.cos(t % (2d * Math.PI)) * 3.0f;
            }

            public double getY(double t, double tpf) {
                return 0d;
            }

            public double getZ(double t, double tpf) {
                return 0d;
            }
        };
        heroWave = new Wave(blue, "hero", 1, functionHeroWave, 0d, 0d, 3d, 0d, 
			projectileClasses, 
			Constants.cyanShip,
			cannonClasses,
			Vector3f.UNIT_Y.negate());
        heroWave.create(assetManager);

        

        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Wave0", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Wave1", new KeyTrigger(KeyInput.KEY_2));
		inputManager.addMapping("Wave2", new KeyTrigger(KeyInput.KEY_3));
//        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
//                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
//        // Add the names to the action listener.
        inputManager.addListener(actionListener, new String[]{"Pause", "Wave0", "Wave1", "Wave2"});

    }

    @Override
    public void simpleUpdate(float tpf) {
		red.pickTarget(heroWave);
		
        CollisionZone.getInstance().check(heroWave);
        for (Wave wave : waves) {
            CollisionZone.getInstance().check(wave);
        }

        CollisionZone.getInstance().purgeBoundingBoxes();

        if (heroWave != null) {
            heroWave.update(tpf, speed, rootNode);
        }
		if(waves.size()>0){
		  blue.pickTarget(waves.getFirst());
		}
        for (Wave wave : waves) {
            wave.update(tpf, speed, rootNode);
        }


    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;
            } else {
                if (isRunning) {
                    if (name.equals("Wave0")&& !keyPressed) {
					  
					  PositionFunction function = new PositionFunction() {

						  public double getX(double t, double tpf) {
							  return Math.cos(t % (2d * Math.PI));
						  }

						  public double getY(double t, double tpf) {
							  return t/2.0d;//Math.sin(t % (2d * Math.PI));
						  }

						  public double getZ(double t, double tpf) {
							  return 0d;
						  }
					  };
					  
					  List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
					  List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
					  cannonClasses.add(Constants.leftCannon);
					  projectileClasses.add(Constants.cyanTorpedoe);
                        Wave wave = new Wave(red, "wave0", 4, function, 1d, 3d, -2d, 0, 
							projectileClasses, 
							Constants.grayShip,
							cannonClasses,
							Vector3f.UNIT_Y);
                        wave.create(assetManager);
                        waves.push(wave);
                    }
                    if (name.equals("Wave1")&& !keyPressed) {
					  
					  PositionFunction function = new PositionFunction() {

						  public double getX(double t, double tpf) {
							  return t*t;
						  }

						  public double getY(double t, double tpf) {
							  return 0d;//Math.sin(t % (2d * Math.PI));
						  }

						  public double getZ(double t, double tpf) {
							  return 0d;
						  }
					  };
					  
					  List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
						List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
						cannonClasses.add(Constants.rightCannon);
						projectileClasses.add(Constants.greenTorpedoe);
                        Wave wave = new Wave(red, "wave1", 3, function, 1d, -5d, -4d, 0, 
							projectileClasses, 
							Constants.greenShip, 
							cannonClasses,
							Vector3f.UNIT_Y);
                        wave.create(assetManager);
                        waves.push(wave);
                    }
					if (name.equals("Wave2")&& !keyPressed) {
					  
					  PositionFunction function = new PositionFunction() {

						  public double getX(double t, double tpf) {
							  return 0.1d*Math.sin(t);
						  }

						  public double getY(double t, double tpf) {
							  return Math.log(t);//Math.sin(t % (2d * Math.PI));
						  }

						  public double getZ(double t, double tpf) {
							  return 0d;
						  }
					  };
					  
					  List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
						List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
						cannonClasses.add(Constants.leftCannon);
						projectileClasses.add(Constants.redTorpedoe);
						cannonClasses.add(Constants.rightCannon);
						projectileClasses.add(Constants.redTorpedoe);
                        Wave wave = new Wave(red, "wave2", 1, function, 1d, 0d, -4d, 0, 
							projectileClasses, 
							Constants.redShip, 
							cannonClasses,
							Vector3f.UNIT_Y);
                        wave.create(assetManager);
                        waves.push(wave);
                    }

                } else {
                    System.out.println("Press P to unpause.");
                }
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
        }
    };

    public float getSpeed() {
        return this.speed;
    }

}
