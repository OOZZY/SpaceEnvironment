package traces;

import processing.core.PVector;
import processing.core.PApplet;

/* Traces that appear during an explosion. */
public class ExplosionTrace implements Trace {
  /* Explosion particle. */
  private class Particle {
    private PVector pos, vel; // position, velocity
    private float width, height; // width, height

    /* Constructor: Initializes this particle with given position. */
    public Particle(PVector pos) {
      this.pos = pos;
      // random velocity, width, height
      vel = new PVector(p.random(-3, 3), p.random(-3, 3));
      width = p.random(5, 8);
      height = p.random(5, 8);
    }

    /* Updates this particle. */
    public void move() {
      pos.add(vel); // update position
    }

    /* Draws this particle onto given PApplet. */
    public void draw(PApplet p) {
      p.pushMatrix();
      p.translate(pos.x, pos.y);
      p.stroke(0);
      p.strokeWeight(1);
      p.fill(255, 128, 0); // orange
      p.ellipse(0, 0, width, height);
      p.popMatrix();
    }
  }

  private PApplet p; // PApplet to draw this trace onto
  private Particle[] particles = new Particle[25]; // explosion particles
  private int countDown = 30; // duration of trace in frames

  /* Constructor: Initializes this trace with given position. */
  public ExplosionTrace(PApplet p, PVector pos) {
    this.p = p;

    // initialize particles
    for (int i = 0; i < particles.length; ++i) {
      particles[i] = new Particle(pos.get());
    }
  }

  /* Moves this trace. */
  @Override public void move() {
    countDown--;
    for (int i = 0; i < particles.length; ++i) {
      particles[i].move();
    }
  }

  /* Draws this trace. */
  @Override public void draw() {
    for (int i = 0; i < particles.length; ++i) {
      particles[i].draw(p);
    }
  }

  /* Returns whether this trace is deactivated. */
  @Override public boolean isDeactivated() { return countDown <= 0; }
}
