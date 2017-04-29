package space;

import processing.core.PApplet;

/* This class implement a blackhole space object. */
public class Blackhole extends AbstractSpaceObject {
  // variables used to retrieve Perlin noise for movement of this blackhole
  private float xoff = 0, yoff = 10000;
  // variables used to retrieve Perlin noise for appearance of this blackhole
  private float xoff1 = 0, xoff2 = 10000;

  /* Constructor: Initialize this blackhole with given argument. */
  public Blackhole(PApplet p) {
    super(p, p.random(100, 125), 0, 2);
    height = width;
  }

  /* Moves this blackhole. */
  @Override public void move() {
    super.move();
    // modify movement with Perlin noise values
    pos.x += PApplet.map(p.noise(xoff), 0, 1, -3, 3);
    pos.y += PApplet.map(p.noise(yoff), 0, 1, -3, 3);
    xoff += 0.01;
    yoff += 0.01;
  }

  /* Draws this blackhole. */
  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.noStroke();
    drawCircle(0, 0, width, 50);
    xoff1 += 0.05;
    xoff2 += 0.05;
    p.popMatrix();
  }

  /*
   * Draws a circle and smaller and darker circles recursively. Helper function
   * for draw() method. Parameters x and y determine position. Parameter d
   * determines the diameter of the biggest circle. Parameter determines the
   * fill of the biggest circle.
   */
  public void drawCircle(float x, float y, float d, float fill) {
    p.fill(fill, 200);
    // modify appearance with Perlin noise values
    float wnoise = p.noise(xoff1);
    float hnoise = p.noise(xoff2);
    p.ellipse(x, y, d*0.9f + wnoise*d/2,
                    d*0.9f + hnoise*d/2);
    if (d > 2) {
      d *= 0.75;
      fill *= 0.75;
      drawCircle(x, y, d, fill);
    }
  }
}
