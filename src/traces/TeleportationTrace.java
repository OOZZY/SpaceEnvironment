package traces;

import processing.core.PApplet;
import processing.core.PVector;

/* Traces that appear during a teleportation. */
public class TeleportationTrace implements Trace {
  private PApplet p; // PApplet to draw this trace onto
  private PVector pos; // position
  private float diameter; // diameter
  private int countDown = 30; // duration of trace in frames

  /* Constructor: Initializes this trace with given position. */
  public TeleportationTrace(PApplet p, PVector pos) {
    this.p = p;
    this.pos = pos;
    diameter = 25;
  }

  /* Moves this trace. */
  @Override public void move() {
    countDown--;
    diameter += 3;
  }

  /* Draws this trace. */
  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.stroke(128, 128, 255); // purplish color
    p.strokeWeight(3);
    p.noFill();
    p.ellipse(0, 0, diameter, diameter);
    p.popMatrix();
  }

  /* Returns whether this trace is deactivated. */
  @Override public boolean isDeactivated() { return countDown <= 0; }
}
