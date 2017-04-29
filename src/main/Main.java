package main;

import java.util.ArrayList;

import processing.core.PApplet;
import space.Blackhole;
import space.SpaceRockFactory;
import space.AbstractSpaceObject;
import space.AbstractSpaceRockFactory;
import space.UserSpaceship;
import utility.KeysPressed;

public class Main extends PApplet {
  AbstractSpaceRockFactory rockFactory = new SpaceRockFactory(this);
  UserSpaceship user;
  ArrayList<AbstractSpaceObject> rocks;
  ArrayList<AbstractSpaceObject> blackholes;

  public void setup() {
    size(1200, 700);

    user = new UserSpaceship(this);
    rocks = new ArrayList<AbstractSpaceObject>();
    blackholes = new ArrayList<AbstractSpaceObject>();
  }

  public void draw() {
    background(0);

    updateAndDrawUser();
    spawnRock();
    spawnBlackhole();
    updateAndDrawRocks();
    updateAndDrawBlackholes();
    detectAndResolveUserRockCollisions();
    detectAndResolveUserBlackholeCollisions();
    detectAndResolveRockRockCollisions();
    detectAndResolveRockBlackholeCollisions();
    detectAndResolveBlackholeBlackholeCollisions();
    removeDeactivatedRocks();
    removeDeactivatedBlackholes();

    drawInfoForDebugging();
  }

  void updateAndDrawUser() {
    user.move();
    user.draw();
  }

  void spawnRock() {
    if (frameCount % 30 == 0) { rocks.add(rockFactory.createRock()); }
  }

  void spawnBlackhole() {
    if (blackholes.size() < 2) {
      if (frameCount % 120 == 0) { blackholes.add(new Blackhole(this)); }
    }
  }

  void updateAndDrawRocks() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      if (rock.isOutOfScreen()) {
        rock.deactivate();
      } else {
        rock.move();
        rock.draw();
      }
    }
  }

  void updateAndDrawBlackholes() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole = blackholes.get(i);
      if (blackhole.isOutOfScreen()) {
        blackhole.deactivate();
      } else {
        blackhole.move();
        blackhole.draw();
      }
    }
  }

  void detectAndResolveUserRockCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      if (user.isCollidingWith(rock)) {
        user.resetPosition();
        rock.deactivate();
      }
    }
  }

  void detectAndResolveUserBlackholeCollisions() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole = blackholes.get(i);
      if (user.isCollidingWith(blackhole)) {
        user.toRandomPosition();
      }
    }
  }

  void detectAndResolveRockRockCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock1 = rocks.get(i);
      for (int j = i + 1; j < rocks.size(); ++j) {
        AbstractSpaceObject rock2 = rocks.get(j);
        if (rock1.isCollidingWith(rock2)) {
          rock1.deactivate();
          rock2.deactivate();
        }
      }
    }
  }

  void detectAndResolveRockBlackholeCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      for (int j = 0; j < blackholes.size(); ++j) {
        AbstractSpaceObject blackhole = blackholes.get(j);
        if (rock.isCollidingWith(blackhole)) {
          rock.toRandomPosition();
        }
      }
    }
  }

  void detectAndResolveBlackholeBlackholeCollisions() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole1 = blackholes.get(i);
      for (int j = i + 1; j < blackholes.size(); ++j) {
        AbstractSpaceObject blackhole2 = blackholes.get(j);
        if (blackhole1.isCollidingWith(blackhole2)) {
          blackhole1.deactivate();
          blackhole2.deactivate();
        }
      }
    }
  }

  void removeDeactivatedRocks() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      if (rock.isDeactivated()) {
        rocks.remove(i);
      }
    }
  }

  void removeDeactivatedBlackholes() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole = blackholes.get(i);
      if (blackhole.isDeactivated()) {
        blackholes.remove(i);
      }
    }
  }

  public void drawInfoForDebugging() {
    fill(255);
    text("rocks.size(): " + rocks.size(), 10, 20);
    text("blackholes.size(): " + blackholes.size(), 10, 40);
  }

  /* Modifies boolean variables when certain keys are pressed. */
  public void keyPressed() {
    if (key == CODED && keyCode == UP) { KeysPressed.up = true; }
    if (key == CODED && keyCode == DOWN) { KeysPressed.down = true; }
    if (key == CODED && keyCode == LEFT) { KeysPressed.left = true; }
    if (key == CODED && keyCode == RIGHT) { KeysPressed.right = true; }
  }

  /* Modifies boolean variables when certain keys are released. */
  public void keyReleased() {
    if (key == CODED && keyCode == UP) { KeysPressed.up = false; }
    if (key == CODED && keyCode == DOWN) { KeysPressed.down = false; }
    if (key == CODED && keyCode == LEFT) { KeysPressed.left = false; }
    if (key == CODED && keyCode == RIGHT) { KeysPressed.right = false; }
  }
}
