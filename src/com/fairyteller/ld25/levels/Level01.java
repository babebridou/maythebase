/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.levels;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tom
 */
public class Level01 extends AbstractLevel{

  public Level01() {
	super();
	initEvents();
  }
  int wavecount = 0;
  public void updateWaves(){
	double leveltime = lifetime-starttime;
	AbstractLevelEvent r = pickEvent(leveltime);
	if(r!=null){
	  r.runEvent(lifetime);
	}
  }
  
  public void initEvents(){
	this.events.put(3, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(15, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(20, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(35, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(39, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(45, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(50, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(51, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(52, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(53, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(54, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(55, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(56, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(57, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(58, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(59, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendCosinusHeroWave(lifetime);
	  }
	});
	this.events.put(60, new AbstractLevelEvent() {

	  @Override
	  protected void runEvent(double lifetime) {
		appendSuperCosinusHeroWave(lifetime);
	  }
	});
  }
  
  Map<Integer, AbstractLevelEvent> events = new HashMap<Integer, AbstractLevelEvent>();
  
  
  
  private AbstractLevelEvent pickEvent(double leveltime){
	int i = (int)leveltime;
	AbstractLevelEvent r = events.get(i);
	if(r!=null){
	  events.remove(i);
	  return r;
	}
	return null;
  }
  
  
  
  
  
}
