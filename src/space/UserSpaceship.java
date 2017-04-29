package space;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.AbstractRectangularMover;
import utility.KeysPressed;

public class UserSpaceship extends AbstractRectangularMover {
  private PVector acc; // acceleration

  private static final float DAMP = 0.8f;

  // vectors saved to use for user spaceship's movement
  private static final PVector up = new PVector(0, -1);
  private static final PVector down = new PVector(0, 1);
  private static final PVector left = new PVector(-1, 0);
  private static final PVector right = new PVector(1, 0);

  public UserSpaceship(PApplet p) {
    super(p, 100, 50,
          new PVector(p.width / 2, p.height / 2),
          new PVector(0, 0));
    acc = new PVector(0, 0);
  }

  @Override public void move() {
    // modify acceleration based on keyboard input
    if (KeysPressed.up) { acc.add(up); }
    if (KeysPressed.down) { acc.add(down); }
    if (KeysPressed.left) { acc.add(left); }
    if (KeysPressed.right) { acc.add(right); }
    vel.add(acc); // update velocity
    vel.mult(DAMP); // dampen velocity
    super.move(); // update position
    acc.mult(0); // reset acceleration
  }

  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.fill(255);
    p.rectMode(PConstants.CENTER);
    p.rect(0, 0, width, height);
    p.popMatrix();
  }

  public void resetPosition() {
    pos.x = p.width / 2;
    pos.y = p.height / 2;
  }
}
