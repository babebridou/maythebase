/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

/**
 *
 * @author Tom
 */
public interface EntityClass {
    public Material getMaterial();
    public Spatial getGeometry();
    public void init(AssetManager assetManager);
    public int getBaseDamage();
    public int getBaseHealth();
}
