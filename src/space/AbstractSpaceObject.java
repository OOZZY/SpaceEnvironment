package space;

import processing.core.PApplet;
import processing.core.PVector;
import utility.AbstractRectangularMover;

/*
 * A superclass for space objects that provides methods and properties common
 * to all space objects.
 */
public abstract class AbstractSpaceObject extends AbstractRectangularMover {
  private boolean active = true; // stores whether this space object is active

  /*
   * Constructor: Initializes this space object with given arguments. The
   * initial position of the space object is a random position along the
   * screen's edges. The velocity of the space object is also random. The
   * parameter maxSpeed specifies the maximum speed of the space object.
   */
  public AbstractSpaceObject(PApplet p, float width, float height,
                             float maxSpeed) {
    super(p, width, height, null, null);

    int direction = (int)p.random(100) % 4; // pick a direction
    switch (direction) {
      case 0: // enter from left edge
        pos = new PVector(-width / 2, p.random(p.height));
        vel = new PVector(p.random(maxSpeed), p.random(-maxSpeed, maxSpeed));
        break;
      case 1: // enter from right edge
        pos = new PVector(p.width + width / 2, p.random(p.height));
        vel = new PVector(-p.random(maxSpeed), p.random(-maxSpeed, maxSpeed));
        break;
      case 2: // enter from top edge
        pos = new PVector(p.random(p.width), -height / 2);
        vel = new PVector(p.random(-maxSpeed, maxSpeed), p.random(maxSpeed));
        break;
      case 3: // enter from bottom edge
        pos = new PVector(p.random(p.width), p.height + height / 2);
        vel = new PVector(p.random(-maxSpeed, maxSpeed), -p.random(maxSpeed));
        break;
      default:
    }
  }

  /* Draws this space object. */
  public abstract void draw();

  /* Deactivates this space object. */
  public void deactivate() {
    active = false;
  }

  /* Returns whether this space object is deactivated. */
  public boolean isDeactivated() {
    return active == false;
  }
}
