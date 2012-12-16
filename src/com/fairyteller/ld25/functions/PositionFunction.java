/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.functions;

/**
 *
 * @author Tom
 */
public interface PositionFunction {

    public double getX(double t, double dt);

    public double getY(double t, double dt);

    public double getZ(double t, double dt);
}
