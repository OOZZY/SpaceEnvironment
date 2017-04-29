package space;

import processing.core.PApplet;
import processing.core.PConstants;

public class Comet extends AbstractSpaceObject {
  public Comet(PApplet p) {
    super(p);
  }

  @Override public void draw() {
    p.pushMatrix();
    p.translate(pos.x, pos.y);
    p.fill(150);
    p.rectMode(PConstants.CENTER);
    p.rect(0, 0, width, height);
    p.popMatrix();
  }
}
