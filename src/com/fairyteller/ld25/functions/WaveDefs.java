/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fairyteller.ld25.functions;

import com.fairyteller.ld25.entity.EntityClass;
import com.fairyteller.ld25.entity.Team;
import com.jme3.math.Vector3f;
import java.util.List;

/**
 *
 * @author Tom
 */
public class WaveDefs {
  Team team;
  List<EntityClass> projectileClasses;
  EntityClass shipClasses;
  List<EntityClass> cannonClasses;
  int shipCount;
  double delayBetweenShips;
  Vector3f azimuth;
  
}
