package mygame;

import com.fairyteller.ld25.collision.CollisionZone;
import com.fairyteller.ld25.entity.ShipClass;
import com.fairyteller.ld25.entity.Team;
import com.fairyteller.ld25.entity.TorpedoeClass;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.entity.Wave;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends SimpleApplication {
	Team red;
	Team blue;
    Wave heroWave;
//    Ship hero;
    LinkedList<Wave> waves;
    PositionFunction function;
    boolean isRunning = true;

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.WARNING);
        Main app = new Main();
        Constants.setInstanceMain(app);
        app.start();
    }

    @Override
    public void simpleInitApp() {
	  red = new Team();
	  blue = new Team();
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
        heroWave = new Wave(blue, "hero", 1, functionHeroWave, 0d, 0d, 3d, 0d, new TorpedoeClass(ColorRGBA.Cyan), new ShipClass(ColorRGBA.Cyan, 3, 100), Vector3f.UNIT_Y.negate());
        heroWave.create(assetManager);

//        hero = new Ship("hero");
//        hero.create(assetManager);
//        hero.setMoveOffsetY(3d);

        function = new PositionFunction() {

            public double getX(double t, double tpf) {
                return 0d;//Math.cos(t % (2d * Math.PI));
            }

            public double getY(double t, double tpf) {
                return 0d;//Math.sin(t % (2d * Math.PI));
            }

            public double getZ(double t, double tpf) {
                return 0d;
            }
        };
        Wave wave = new Wave(red, "me", 4, function, 1d, 3d, -2d, 0, new TorpedoeClass(ColorRGBA.Red), new ShipClass(ColorRGBA.Red), Vector3f.UNIT_Y);
        wave.create(assetManager);
        waves.push(wave);
        flyCam.setEnabled(false);

//        EntityControl control = new EntityControl();
//        player.setPositionFunction();
//        player.addControl(control);

        // You can map one or several inputs to one named action
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Wave0", new KeyTrigger(KeyInput.KEY_0));
        inputManager.addMapping("Wave1", new KeyTrigger(KeyInput.KEY_1));
//        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
//                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
//        // Add the names to the action listener.
        inputManager.addListener(actionListener, new String[]{"Pause", "Wave0", "Wave1"});
//        inputManager.addListener(analogListener, new String[]{"Wave0", "Wave1"});

        //      

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
                        Wave wave = new Wave(red, "wave0", 4, function, 1d, 3d, -2d, 0, new TorpedoeClass(ColorRGBA.Red), new ShipClass(ColorRGBA.Red), Vector3f.UNIT_Y);
                        wave.create(assetManager);
                        waves.push(wave);
                    }
                    if (name.equals("Wave1")&& !keyPressed) {
                        Wave wave = new Wave(red, "wave1", 4, function, 1d, 3d, -2d, 0, new TorpedoeClass(ColorRGBA.Red), new ShipClass(ColorRGBA.Red), Vector3f.UNIT_Y);
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
//    public BoundingVolume getPlayerKillingBoundingVolume(Entity player){
//        return this.playerKillingVolume.get(player);
//    }
}
