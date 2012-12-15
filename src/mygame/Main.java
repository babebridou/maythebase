package mygame;

import com.fairyteller.ld25.control.EntityControl;
import com.fairyteller.ld25.entity.HeroShip;
import com.fairyteller.ld25.functions.PositionFunction;
import com.fairyteller.ld25.entity.Wave;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;


public class Main extends SimpleApplication {
    
    HeroShip hero;
    Wave wave;

    boolean isRunning = true;
    
    public static void main(String[] args) {
        Main app = new Main();
        Constants.setInstanceMain(app);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        hero = new HeroShip("hero");
        hero.create(assetManager);
        
        PositionFunction heroPosition = new PositionFunction() {
            
            public double getX(double t, double x0, double y0, double z0) {
                return x0+Math.cos(t % (2d * Math.PI));
            }
            
            public double getY(double t, double x0, double y0, double z0) {
                return y0;
            }
            
            public double getZ(double t, double x0, double y0, double z0) {
                return z0;
            }
        };
        EntityControl control = new EntityControl();
        hero.setPositionFunction(heroPosition);
        hero.addControl(control);
        
        PositionFunction function = new PositionFunction() {
            
            public double getX(double t, double x0, double y0, double z0) {
                return Math.cos(t % (2d * Math.PI));
            }
            
            public double getY(double t, double x0, double y0, double z0) {
                return Math.sin(t % (2d * Math.PI));
            }
            
            public double getZ(double t, double x0, double y0, double z0) {
                return 0d;
            }
        };
        wave = new Wave(3, function, 1d);
        wave.create(assetManager);
        rootNode.attachChild(hero);
        flyCam.setEnabled(false);
        
//        EntityControl control = new EntityControl();
//        player.setPositionFunction();
//        player.addControl(control);
        
        // You can map one or several inputs to one named action
//        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
//        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
//        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
//                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
//        // Add the names to the action listener.
//        inputManager.addListener(actionListener, new String[]{"Pause"});
//        inputManager.addListener(analogListener, new String[]{"Left", "Right", "Rotate"});
    }
    
    @Override
    public void simpleUpdate(float tpf) {
//        if(playerKillingVolume==null){
//            playerKillingVolume = new HashMap<Entity, BoundingVolume>();
//        }
//        if(playerKillingVolume.get(player2) !=null){
//            CollisionResults results = new CollisionResults();
//            player.getGeometry().collideWith(playerKillingVolume.get(player2), results);
//            if(results.size()>0){
//              System.out.println("Player hit!");
//            } 
//        }
//        
//        playerKillingVolume.put(player, new BoundingBox(Vector3f.ZERO, Vector3f.ZERO));
//        playerKillingVolume.put(player2, new BoundingBox(Vector3f.ZERO, Vector3f.ZERO));
        if(wave!=null){
            wave.update(tpf, speed, rootNode);
        }
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
//    private ActionListener actionListener = new ActionListener() {
//
//        public void onAction(String name, boolean keyPressed, float tpf) {
//            if (name.equals("Pause") && !keyPressed) {
//                isRunning = !isRunning;
//            }
//        }
//    };
//    private AnalogListener analogListener = new AnalogListener() {
//
//        public void onAnalog(String name, float value, float tpf) {
//            if (isRunning) {
//                if (name.equals("Rotate")) {
//                    player.rotate(0, value * speed, 0);
//                }
//                if (name.equals("Right")) {
//                    Vector3f v = player.getLocalTranslation();
//                    player.setLocalTranslation(v.x + value * speed, v.y, v.z);
//                }
//                if (name.equals("Left")) {
//                    Vector3f v = player.getLocalTranslation();
//                    player.setLocalTranslation(v.x - value * speed, v.y, v.z);
//                }
//            } else {
//                System.out.println("Press P to unpause.");
//            }
//        }
//    };
//    
    public float getSpeed(){
        return this.speed;
    }
//    public BoundingVolume getPlayerKillingBoundingVolume(Entity player){
//        return this.playerKillingVolume.get(player);
//    }
}
