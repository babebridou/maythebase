/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

/**
 *
 * @author Tom
 */
public interface Damager {
    public Shooter getOwner();
    public void setOwner(Shooter owner);
    public void updateDamageBox();
    public double getShootOffsetX();
    public void setShootOffsetX(double offsetX);
    public double getShootOffsetY();
    public void setShootOffsetY(double offsetY);
    public double getShootOffsetZ();
    public void setShootOffsetZ(double offsetZ);
}
