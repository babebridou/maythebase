/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

import com.fairyteller.ld25.functions.PositionFunction;

/**
 *
 * @author Tom
 */
public interface Mover {
    public double getMoveOffsetX();
    public double getMoveOffsetY();
    public double getMoveOffsetZ();
    public PositionFunction getPositionFunction();
}
