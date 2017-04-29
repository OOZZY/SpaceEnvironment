package space;

import processing.core.PApplet;

public class Blackhole extends AbstractSpaceObject {
  private float xoff = 0, yoff = 10000;

  public Blackhole(PApplet p) {
    super(p);
    width = p.random(100, 125);
    height = width;
  }

  @Override public void move() {
    super.move();
    pos.x += PApplet.map(p.noise(xoff), 0, 1, -3, 3);
    pos.y += PApplet.map(p.noise(yoff), 0, 1, -3, 3);
    xoff += 0.01;
    yoff += 0.01;
  }

  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.stroke(255);
    p.fill(0);
    p.ellipse(0, 0, width, height);
    p.popMatrix();
  }
}
