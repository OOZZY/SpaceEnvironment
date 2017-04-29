package space;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PConstants;
import assets.Images;

/* This class implement an asteroid space object. */
public class Asteroid extends AbstractSpaceObject {
  private float rotate = 0; // angle of rotation
  private float rotationSpeed = 0.01f; // speed of rotation
  private boolean rotateRight = true; // direction of rotation

  /* Constructor: Initialize this asteroid with given argument. */
  public Asteroid(PApplet p) {
    super(p, p.random(50, 75), 0, 2);
    height = width;
    // randomly determine direction of rotation
    if ((int)p.random(100) % 2 == 0) {
      rotateRight = false;
    }
  }

  /* Draws this asteroid. */
  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.rotate(rotate);
    if (rotateRight) { // update angle of rotation
      rotate += rotationSpeed;
    } else {
      rotate -= rotationSpeed;
    }
    PImage rock = Images.getRock();
    // scale image to this asteroid's size
    p.scale(width / rock.width, height / rock.height);
    p.imageMode(PConstants.CENTER);
    p.image(rock, 0, 0);
    p.popMatrix();
  }
}
