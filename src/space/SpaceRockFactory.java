package space;

import processing.core.PApplet;

/* A concrete space rock factory. */
public class SpaceRockFactory extends AbstractSpaceRockFactory {
  /* Constructor: Initialize this space rock factory with given argument. */
  public SpaceRockFactory(PApplet p) {
    super(p);
  }

  /*
   * Creates and returns a new space rock object which will be either an
   * asteroid or a comet.
   */
  @Override public AbstractSpaceObject createRock() {
    // randomly choose whether to return an asteroid or a comet
    if ((int)p.random(100) % 2 == 0) {
      return new Asteroid(p);
    } else {
      return new Comet(p);
    }
  }
}
