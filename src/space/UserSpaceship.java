package space;

import processing.core.PVector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PConstants;
import utility.AbstractRectangularMover;
import utility.KeysPressed;
import assets.Images;

/*
 * This class implements the user's spaceship which can be controlled by the
 * arrow keys of a keyboard.
 */
public class UserSpaceship extends AbstractRectangularMover {
  private PVector acc; // acceleration

  private static final float DAMP = 0.8f; // dampening factor

  // vectors saved to use for user spaceship's movement
  private static final PVector up = new PVector(0, -1);
  private static final PVector down = new PVector(0, 1);
  private static final PVector left = new PVector(-1, 0);
  private static final PVector right = new PVector(1, 0);

  /* Construct: Initializes this spaceship with given argument. */
  public UserSpaceship(PApplet p) {
    super(p, 100, 45,
          new PVector(p.width / 2, p.height / 2), new PVector(0, 0));
    acc = new PVector(0, 0);
  }

  /*
   * Moves this spaceship and also taking keyboard input into account. Whenever
   * this spaceship goes out of the screen, it wraps around to the opposite
   * side of the screen.
   */
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

    // wrap around screen edges
    if (pos.x < 0) { pos.x = p.width; }
    if (pos.x > p.width) { pos.x = 0; }
    if (pos.y < 0) { pos.y = p.height; }
    if (pos.y > p.height) { pos.y = 0; }
  }

  /* Draws this spaceship. */
  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    PImage ship = Images.getShip();
    if (vel.x < 0) { p.scale(-1); } // face left when moving left
    p.imageMode(PConstants.CENTER);
    p.image(ship, 0, 0);
    p.popMatrix();
  }

  /* Resets the position of this spaceship back to the center of the screen. */
  public void resetPosition() {
    pos.x = p.width / 2;
    pos.y = p.height / 2;
  }
}
