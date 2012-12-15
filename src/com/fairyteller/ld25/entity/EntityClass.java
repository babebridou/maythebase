/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.entity;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;

/**
 *
 * @author Tom
 */
public interface EntityClass {
    public Material getMaterial();
    public Geometry getGeometry();
    public void init(AssetManager assetManager);
}
