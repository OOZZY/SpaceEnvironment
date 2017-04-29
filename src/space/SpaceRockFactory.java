package space;

import processing.core.PApplet;

public class SpaceRockFactory extends AbstractSpaceRockFactory {
  public SpaceRockFactory(PApplet p) {
    super(p);
  }

  @Override public AbstractSpaceObject createRock() {
    if ((int)p.random(100) % 2 == 0) {
      return new Asteroid(p);
    } else {
      return new Comet(p);
    }
  }
}
