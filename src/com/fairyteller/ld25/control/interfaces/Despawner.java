/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.control.interfaces;

/**
 *
 * @author Tom
 */
public interface Despawner {
    public boolean shouldDespawn(double lifetime);
    public void despawn();
}
