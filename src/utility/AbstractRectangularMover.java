package utility;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class AbstractRectangularMover {
  protected PApplet p; // associated PApplet
  protected float width, height; // width, height
  protected PVector pos, vel; // position, velocity

  public AbstractRectangularMover(PApplet p, float width, float height,
                          PVector pos, PVector vel) {
    this.p = p;
    this.width = width;
    this.height = height;
    this.pos = pos;
    this.vel = vel;
  }

  public void move() {
    pos.add(vel);
  }

  public abstract void draw();

  public boolean isCollidingWith(AbstractRectangularMover other) {
    boolean horizontal
      = PApplet.abs(pos.x - other.pos.x) < width/2 + other.width/2;
    boolean vertical
      = PApplet.abs(pos.y - other.pos.y) < height/2 + other.height/2;
    boolean colliding = false;
    if (horizontal && vertical) { colliding = true; }
    return colliding;
  }

  public boolean isOutOfScreen() {
    boolean out = false;
    if (pos.x < -width/2 || pos.x > p.width + width/2 ||
        pos.y < -height/2 || pos.y > p.height + height/2) {
      out = true;
    }
    return out;
  }

  public void toRandomPosition() {
    pos.x = p.random(p.width);
    pos.y = p.random(p.height);
  }
}
