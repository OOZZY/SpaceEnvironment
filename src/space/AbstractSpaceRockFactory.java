package space;

import processing.core.PApplet;

/* An abstract superclass for space rock factories. */
public abstract class AbstractSpaceRockFactory {
  protected PApplet p; // PApplet associated with this space rock factory

  /* Constructor: Initialize this space rock factory with given argument. */
  public AbstractSpaceRockFactory(PApplet p) {
    this.p = p;
  }

  /* Creates and returns a new space rock object. */
  public abstract AbstractSpaceObject createRock();
}
