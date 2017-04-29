package space;

import processing.core.PApplet;

public abstract class AbstractSpaceRockFactory {
  protected PApplet p;
  public AbstractSpaceRockFactory(PApplet p) { this.p = p; }
  public abstract AbstractSpaceObject createRock();
}
