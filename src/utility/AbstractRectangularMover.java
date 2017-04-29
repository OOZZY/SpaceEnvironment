package utility;

import processing.core.PApplet;
import processing.core.PVector;

/*
 * A superclass for rectangular movers that provides basic methods for
 * movement, collision detection, and detecting whether the mover has moved out
 * of the screen.
 */
public abstract class AbstractRectangularMover {
  protected PApplet p; // PApplet to draw this mover onto
  protected float width, height; // width, height
  protected PVector pos, vel; // position, velocity

  /* Constructor: Initialize this mover with given arguments. */
  public AbstractRectangularMover(PApplet p, float width, float height,
                                  PVector pos, PVector vel) {
    this.p = p;
    this.width = width;
    this.height = height;
    this.pos = pos;
    this.vel = vel;
  }

  /* Moves this mover. */
  public void move() {
    pos.add(vel);
  }

  /* Draws this mover. */
  public abstract void draw();

  /* Returns whether this mover is colliding with the given mover. */
  public boolean isCollidingWith(AbstractRectangularMover other) {
    boolean horizontal = PApplet.abs(pos.x - other.pos.x)
                         < width / 2 + other.width / 2;
    boolean vertical = PApplet.abs(pos.y - other.pos.y)
                       < height / 2 + other.height / 2;
    boolean colliding = false;
    if (horizontal && vertical) {
      colliding = true;
    }
    return colliding;
  }

  /* Returns whether this mover is out of the screen. */
  public boolean isOutOfScreen() {
    boolean out = false;
    if (pos.x < -width / 2 || pos.x > p.width + width / 2 ||
        pos.y < -height / 2 || pos.y > p.height + height / 2) {
      out = true;
    }
    return out;
  }

  /* Moves this mover to a random position on the screen. */
  public void toRandomPosition() {
    pos.x = p.random(p.width);
    pos.y = p.random(p.height);
  }

  /* Returns the current position of this mover. */
  public PVector getPosition() { return pos; }
}
