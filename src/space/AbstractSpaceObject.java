package space;

import processing.core.PApplet;
import processing.core.PVector;
import utility.AbstractRectangularMover;

public abstract class AbstractSpaceObject extends AbstractRectangularMover {
  private boolean active = true;

  public AbstractSpaceObject(PApplet p) {
    super(p, p.random(50, 75), 0, null, null);
    int direction = (int)p.random(100) % 4;
    final int MAXSPEED = 2; // max speed

    height = width;
    switch (direction) {
      case 0:
        // enter from left edge
        pos = new PVector(-width/2, p.random(p.height));
        vel = new PVector(p.random(MAXSPEED), p.random(-MAXSPEED, MAXSPEED));
        break;
      case 1:
        // enter from right edge
        pos = new PVector(p.width + width/2, p.random(p.height));
        vel = new PVector(-p.random(MAXSPEED), p.random(-MAXSPEED, MAXSPEED));
        break;
      case 2:
        // enter from top edge
        pos = new PVector(p.random(p.width), -height/2);
        vel = new PVector(p.random(-MAXSPEED, MAXSPEED), p.random(MAXSPEED));
        break;
      case 3:
        // enter from bottom edge
        pos = new PVector(p.random(p.width), p.height + height/2);
        vel = new PVector(p.random(-MAXSPEED, MAXSPEED), -p.random(MAXSPEED));
        break;
      default:
    }
  }

  public abstract void draw();

  public void deactivate() {
    active = false;
  }

  public boolean isDeactivated() {
    return active == false;
  }
}
