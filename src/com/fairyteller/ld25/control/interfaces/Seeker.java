/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

import com.jme3.math.Vector3f;

/**
 *
 * @author Tom
 */
public interface Seeker {
    public Vector3f seek();
	public void aim(Vector3f direction);
}
