package background;

import processing.core.PApplet;

/* A simple background. */
public class SimpleBackground implements Background {
  /* Draws a black background onto given PApplet. */
  @Override public void draw(PApplet p) {
    p.background(0);
  }
}
