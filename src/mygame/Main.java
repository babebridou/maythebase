package mygame;

import com.fairyteller.ld25.entity.Constants;
import com.fairyteller.ld25.gui.Level01Controller;
import com.fairyteller.ld25.gui.StartMenuController;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends SimpleApplication {
	
//    PositionFunction function;
    

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.WARNING);
        Main app = new Main();
        Constants.setInstanceMain(app);
        app.start();
    }

    @Override
    public void simpleInitApp() {
	  
	  
	  
	  NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
    assetManager, inputManager, audioRenderer, guiViewPort);
	  
	/** Create a new NiftyGUI object */
	Nifty nifty = niftyDisplay.getNifty();
	
	StartMenuController myController = new StartMenuController();
	  stateManager.attach(myController);
	nifty.registerScreenController(myController);
	
	Level01Controller level01Controller = new Level01Controller();
	  stateManager.attach(level01Controller);
	nifty.registerScreenController(level01Controller);
	
	/** Read your XML and initialize your custom ScreenController */
	nifty.fromXml("Interface/newNiftyGui.xml", "start");
	// nifty.fromXml("Interface/helloworld.xml", "start", new MySettingsScreen(data));
	// attach the Nifty display to the gui view port as a processor
	guiViewPort.addProcessor(niftyDisplay);
	//nifty.setDebugOptionPanelColors(true);



	  flyCam.setEnabled(false);
	  setPauseOnLostFocus(false);
	  


        

        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Wave0", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Wave1", new KeyTrigger(KeyInput.KEY_2));
		inputManager.addMapping("Wave2", new KeyTrigger(KeyInput.KEY_3));
		inputManager.addMapping("Wave3", new KeyTrigger(KeyInput.KEY_4));
		inputManager.addMapping("Wave4", new KeyTrigger(KeyInput.KEY_5));
//        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
//                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
//        // Add the names to the action listener.
        
		
    }

    @Override
    public void simpleUpdate(float tpf) {
	  super.simpleUpdate(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    

    public float getSpeed() {
        return this.speed;
    }

	
 
	
}
