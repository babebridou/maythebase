/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

import com.fairyteller.ld25.functions.PositionFunction;
import com.jme3.math.Vector3f;

/**
 *
 * @author Tom
 */
public interface Mover {
    public Vector3f getAzimuth();
    public double getMoveOffsetX();
    public double getMoveOffsetY();
    public double getMoveOffsetZ();
    public PositionFunction getPositionFunction();
}
