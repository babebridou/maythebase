/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

import com.jme3.scene.Spatial;

/**
 *
 * @author Tom
 */
public interface Destroyable extends Despawner {
    public void hit(int damage);
    public boolean isDestroyed();
    public void destroy();
    public Spatial getGeometry();
    
    public int getHealth();

    public double getNextHitPossible();
    public boolean isInvulnerable();

    public double getHitInvulnerability();

    public void setHitInvulnerability(double hitInvulnerability);

    public void setInvulnerable(boolean isInvulnerable);

    public void setNextHitPossible(double nextHitPossible);

    public int getIncomingDamage();
    public void setIncomingDamage(int incomingDamage);

    public void setHealth(int health);

    public boolean isJustGotHit();
    public void setJustGotHit(boolean justGotHit);
    
}
