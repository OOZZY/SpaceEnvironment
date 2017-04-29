package space;

import processing.core.PApplet;
import processing.core.PConstants;

/* This class implement a wormhole space object. */
public class Wormhole extends AbstractSpaceObject {
  // variables used to retrieve Perlin noise for appearance of this blackhole
  private float xoff1 = 0, xoff2 = 10000;
  private float angle = 0; // angle of rotation

  /* Constructor: Initialize this blackhole with given argument. */
  public Wormhole(PApplet p) {
    super(p, p.random(100, 125), 0, 2);
    height = width;
  }

  /* Draws this wormhole. */
  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.rotate(angle);

    // draw ellipses which make up the wormhole
    float angleLimit = 2 * PConstants.TWO_PI;
    for (float a = 0, d = 0; a < angleLimit; a += 0.5, d += 2.5) {
      // calculating position from angle and distance
      float x = d * PApplet.cos(a);
      float y = d * PApplet.sin(a);

      // modify appearance with Perlin noise values
      float pnoise1 = p.noise(xoff1);
      float pnoise2 = p.noise(xoff2);
      float alpha = pnoise1*200+50;
      float w = pnoise1*25+25;
      float h = pnoise2*25+25;
      xoff1 += 0.001;
      xoff2 += 0.001;

      // draw ellipses
      p.noStroke();
      p.fill(a/angleLimit*150, 75, 150, alpha);
      p.ellipse(x, y, w, h);
      p.ellipse(-x, -y, w, h);
    }

    // draw dark ellipse in the center
    p.noStroke();
    p.fill(0, 0, 75);
    p.ellipse(0, 0, width/10, height/10);
    angle += 0.01;

    p.popMatrix();
  }
}
