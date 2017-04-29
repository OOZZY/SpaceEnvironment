package space;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PConstants;
import assets.Images;

/* This class implement a comet space object. */
public class Comet extends AbstractSpaceObject {
  private float rotate; // angle of rotation

  /* Constructor: Initialize this comet with given argument. */
  public Comet(PApplet p) {
    super(p, p.random(25, 50), 0, 4);
    height = width;
    // randomly determine angle of rotation
    rotate = p.random(PConstants.TWO_PI);
  }

  /* Draws this comet. */
  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);

    // draw comet tail
    p.pushMatrix();
    p.rotate(vel.heading()); // rotate to direction of velocity
    PImage flame = Images.getFlame();
    // scale image to this comet's size
    p.scale(width*4 / flame.width, height / flame.height);
    p.imageMode(PConstants.CENTER);
    p.image(flame, -55, 0);
    p.popMatrix();

    // draw comet rock
    p.rotate(rotate);
    PImage rock = Images.getRock();
    // scale image to this comet's size
    p.scale(width / rock.width, height / rock.height);
    p.imageMode(PConstants.CENTER);
    p.image(rock, 0, 0);
    p.popMatrix();
  }
}
