/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

import com.fairyteller.ld25.entity.Team;
import com.jme3.math.Vector3f;

/**
 *
 * @author Tom
 */
public interface Shooter {
    public boolean shouldFire(double lifetime);
    public void shoot(double lifetime);
    public Vector3f getShootOffsets();
    public Vector3f getAim();
    public int getDamage();
	public Team getTeam();
}
