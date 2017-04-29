package background;

import processing.core.PApplet;
import processing.core.PConstants;
import assets.Images;

/* A background decorator that decorates backgrounds with stars. */
public class StarsDecorator extends BackgroundDecorator {
  boolean alternate; // stores whether to use alternate version of stars image
  boolean set = false; // stores whether alternate variable is set

  /* Constructor: Initializes this decorator with given background. */
  public StarsDecorator(Background background) {
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
    // draw stars
    p.imageMode(PConstants.CORNER);
    if (alternate) {
      p.image(Images.getStarsRed(), 0, 0);
    } else {
      p.image(Images.getStars(), 0, 0);
    }
  }
}
