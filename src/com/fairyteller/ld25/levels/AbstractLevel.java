/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.levels;

import com.fairyteller.ld25.entity.EntityClass;
import com.fairyteller.ld25.entity.Wave;
import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import com.fairyteller.ld25.entity.Constants;

/**
 *
 * @author Tom
 */
public abstract class AbstractLevel {
  protected SimpleApplication app;
  static int waveIndex = 0;
  boolean activeLevel = false;
  boolean started = false;
  boolean ended = false;
  double lifetime;
  double starttime = 0;
  
  public void setActiveLevel(boolean activeLevel){
	this.activeLevel = activeLevel;
  }

  public boolean isActiveLevel() {
	return activeLevel;
  }
  
  public AbstractLevel() {
	
  }
  public void setApplication(SimpleApplication app){
	this.app = app;
  }
  
  public void start(double lifetime){
	if(!started){
	ended = false;
	started = true;
	Constants.red.resetScores();
	Constants.blue.resetScores();
	Constants.red.setIsPlaying(true);
	Constants.blue.setIsPlaying(true);
	updateLifetime(lifetime);
	starttime = lifetime;
	}
	if(started && !ended){
	  updateLifetime(lifetime);
	  updateWaves();
	}
  }
  public void updateLifetime(double lifetime){
	this.lifetime = lifetime;
  }
  public abstract void updateWaves();
  
  public void end(){
	ended = true;
	Constants.red.setIsPlaying(false);
	Constants.blue.setIsPlaying(false);
  }
  
  public void summonWave(int wave){
	switch(wave){
	  case 0:summonWaveCircleLeft();break;
	  case 1:summonWaveCircleRight();break;
	  case 2:summonWaveGreenLateral();break;
	  case 3:summonWaveGreenReverseLateral();break;
	  case 4:summonWaveRedBoss();break;
	}
  }
  
  
    public void summonWaveCircleLeft() {
	PositionFunction function = new PositionFunction() {

	  public double getX(double t, double tpf) {
		return Math.cos(t % (2d * Math.PI));
	  }

	  public double getY(double t, double tpf) {
		return t * 2.0d;//Math.sin(t % (2d * Math.PI));
	  }

	  public double getZ(double t, double tpf) {
		return 0d;
	  }
	};

	List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
	List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
	cannonClasses.add(Constants.leftCannon);
	projectileClasses.add(Constants.redTorpedoe);
	int count = 4;
	double delay = 1d;
	double x0 = 3d;
	double y0 = -2d;
	double z0 = 0;
	Wave wave = new Wave(Constants.red, "w0_"+(waveIndex++), count, function, delay, x0, y0, z0,
		projectileClasses,
		Constants.grayShip,
		cannonClasses,
		Vector3f.UNIT_Y);
	wave.create(app.getAssetManager());
	Constants.waves.push(wave);
  }
  
  
  public void summonWaveCircleRight() {
	PositionFunction function = new PositionFunction() {

	  public double getX(double t, double tpf) {
		return Math.cos(t % (2d * Math.PI));
	  }

	  public double getY(double t, double tpf) {
		return t * 2.0d;//Math.sin(t % (2d * Math.PI));
	  }

	  public double getZ(double t, double tpf) {
		return 0d;
	  }
	};
	int count = 4;
	double delay = 1d;
	double x0 = -3d;
	double y0 = -2d;
	double z0 = 0;
	List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
	List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
	cannonClasses.add(Constants.leftCannon);
	projectileClasses.add(Constants.redTorpedoe);
	Wave wave = new Wave(Constants.red, "w1_"+(waveIndex++),count, function, delay, x0, y0, z0,
		projectileClasses,
		Constants.grayShip,
		cannonClasses,
		Vector3f.UNIT_Y);
	wave.create(app.getAssetManager());
	Constants.waves.push(wave);
  }

  public void summonWaveGreenLateral() {
	PositionFunction function = new PositionFunction() {

	  public double getX(double t, double tpf) {
		return t * t;
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
	cannonClasses.add(Constants.leftCannon);
	projectileClasses.add(Constants.redTorpedoe);
	Wave wave = new Wave(Constants.red, "w2_"+(waveIndex++), 3, function, 1d, -5d, -4d, 0,
		projectileClasses,
		Constants.greenShip,
		cannonClasses,
		Vector3f.UNIT_Y);
	wave.create(app.getAssetManager());
	Constants.waves.push(wave);
  }
  
    public void summonWaveGreenReverseLateral() {
	PositionFunction function = new PositionFunction() {

	  public double getX(double t, double tpf) {
		return -t * t;
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
	cannonClasses.add(Constants.leftCannon);
	projectileClasses.add(Constants.redTorpedoe);
	Wave wave = new Wave(Constants.red, "w3_"+(waveIndex++), 3, function, 1d, 5d, -3d, 0,
		projectileClasses,
		Constants.greenShip,
		cannonClasses,
		Vector3f.UNIT_Y);
	wave.create(app.getAssetManager());
	Constants.waves.push(wave);
  }

  public void summonWaveRedBoss() {
	PositionFunction function = new PositionFunction() {

	  public double getX(double t, double tpf) {
		return 0.1d * Math.sin(t);
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
	projectileClasses.add(Constants.greenTorpedoe);
	cannonClasses.add(Constants.rightCannon);
	projectileClasses.add(Constants.redTorpedoe);
	cannonClasses.add(Constants.laserCannon);
	projectileClasses.add(Constants.magentaLaser);
	Wave wave = new Wave(Constants.red, "w4_"+(waveIndex++), 1, function, 1d, 0d, -4d, 0,
		projectileClasses,
		Constants.redShip,
		cannonClasses,
		Vector3f.UNIT_Y);
	wave.create(app.getAssetManager());
	Constants.waves.push(wave);
  }
  
  public void appendCosinusHeroWave(final double when){
	  List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
	  List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
	  cannonClasses.add(Constants.leftCannon);
	  projectileClasses.add(Constants.cyanTorpedoe);
	  cannonClasses.add(Constants.rightCannon);
	  projectileClasses.add(Constants.cyanTorpedoe);
	  cannonClasses.add(Constants.laserCannon);
	  projectileClasses.add(Constants.yellowLaser);

//	waves = new LinkedList<Wave>();
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
        };;
	  Wave heroWave = new Wave(Constants.blue, "hero_"+(waveIndex++), 1, functionHeroWave, 0d, 0d, 3d, 0d,
		  projectileClasses,
		  Constants.cyanShip,
		  cannonClasses,
		  Vector3f.UNIT_Y.negate());
	  heroWave.create(app.getAssetManager());
	  Constants.heroWaves.push(heroWave);
  }
  
  public void appendSuperCosinusHeroWave(final double when){
	  List<EntityClass> projectileClasses = new ArrayList<EntityClass>();
	  List<EntityClass> cannonClasses = new ArrayList<EntityClass>();
	  cannonClasses.add(Constants.leftCannon);
	  projectileClasses.add(Constants.cyanTorpedoe);
	  cannonClasses.add(Constants.rightCannon);
	  projectileClasses.add(Constants.cyanTorpedoe);
	  cannonClasses.add(Constants.laserCannon);
	  projectileClasses.add(Constants.yellowLaser);
	  cannonClasses.add(Constants.leftRPG);
	  projectileClasses.add(Constants.pinkLaser);
	  cannonClasses.add(Constants.rightRPG);
	  projectileClasses.add(Constants.pinkLaser);

//	waves = new LinkedList<Wave>();
	  PositionFunction functionHeroWave = new PositionFunction() {

            public double getX(double t, double tpf) {
                return Math.cos((t/3d) % (2d * Math.PI)) * 3.0f;
            }

            public double getY(double t, double tpf) {
                return Math.min(t, 2d);
            }

            public double getZ(double t, double tpf) {
                return 0d;
            }
        };;
	  Wave heroWave = new Wave(Constants.blue, "superhero_"+(waveIndex++), 1, functionHeroWave, 0d, 0d, 3d, 0d,
		  projectileClasses,
		  Constants.blackShip,
		  cannonClasses,
		  Vector3f.UNIT_Y.negate());
	  heroWave.create(app.getAssetManager());
	  Constants.heroWaves.push(heroWave);
  }
  
}
