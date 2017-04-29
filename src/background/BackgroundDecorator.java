package background;

import processing.core.PApplet;

/* A superclass for background decorators. */
public class BackgroundDecorator implements Background {
  public Background background; // background to decorate

  /* Constructor: Initializes this decorator with given background. */
  public BackgroundDecorator(Background background) {
    this.background = background;
  }

  /* Draws this decorator onto given PApplet. */
  @Override public void draw(PApplet p) {
    background.draw(p);
  }
}
