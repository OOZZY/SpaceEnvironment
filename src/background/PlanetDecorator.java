package background;

import processing.core.PApplet;
import processing.core.PConstants;
import assets.Images;

/* A background decorator that decorates backgrounds with a planet. */
public class PlanetDecorator extends BackgroundDecorator {
  boolean alternate; // stores whether to use alternate version of planet image
  boolean set = false; // stores whether alternate variable is set

  /* Constructor: Initializes this decorator with given background. */
  public PlanetDecorator(Background background) {
    super(background);
  }

  /* Draws this decorator onto given PApplet. */
  @Override public void draw(PApplet p) {
    background.draw(p); // draw underlying background
    if (!set) {
      // set alternate variable to a random boolean value
      alternate = (int)p.random(100) % 2 == 0;
      set = true;
    }
    // draw planet
    p.imageMode(PConstants.CORNER);
    if (alternate) {
      p.image(Images.getPlanetBlue(), 0, 0);
    } else {
      p.image(Images.getPlanet(), 0, 0);
    }
  }
}
