package space;

import processing.core.PApplet;
import processing.core.PConstants;

public class Asteroid extends AbstractSpaceObject {
  private float rotate = 0;
  private float rotationSpeed = 0.01f;
  private boolean rotateRight = true;

  public Asteroid(PApplet p) {
    super(p);
    if ((int)p.random(100) % 2 == 0) {
      rotateRight = false;
    }
  }

  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.rotate(rotate);
    if (rotateRight) {
      rotate += rotationSpeed;
    } else {
      rotate -= rotationSpeed;
    }
    p.fill(200);
    p.rectMode(PConstants.CENTER);
    p.rect(0, 0, width, height);
    p.popMatrix();
  }
}
