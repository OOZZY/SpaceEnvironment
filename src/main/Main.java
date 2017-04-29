package main;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import controlP5.ControlP5;
import controlP5.Button;
import controlP5.ControlEvent;
import utility.KeysPressed;
import assets.Images;
import assets.Sounds;
import space.UserSpaceship;
import space.AbstractSpaceObject;
import space.AbstractSpaceRockFactory;
import space.SpaceRockFactory;
import space.Blackhole;
import space.Wormhole;
import background.Background;
import background.SimpleBackground;
import background.StarsDecorator;
import background.PlanetDecorator;
import traces.Trace;
import traces.ExplosionTrace;
import traces.TeleportationTrace;

/*
 * A virtual space environment. The environment contains a spaceship which the
 * user can move with the arrow keys of a keyboard. The environment also
 * contains asteroids, comets, blackholes, and wormholes that the user's
 * spaceship can interact with. These space objects can also interact with each
 * other.
 */
public class Main extends PApplet {
  // rock factory used to generate space rocks
  final AbstractSpaceRockFactory rockFactory = new SpaceRockFactory(this);

  ControlP5 controlP5; // used to implement a gui
  Button playButton; // play button which appears at the introduction screen

  // stores whether the screen being displayed should be the intro screen
  boolean introScreen;

  // variables
  UserSpaceship user; // spaceship controlled by the user
  ArrayList<AbstractSpaceObject> rocks; // space rocks. asteroids or comets
  ArrayList<AbstractSpaceObject> blackholes; // blackholes
  ArrayList<AbstractSpaceObject> wormholes; // wormholes
  ArrayList<Trace> traces; // traces indicating explosions or teleportations
  Background background; // background

  /* Initializes variables. */
  public void setup() {
    size(1280, 720);
    smooth();

    // setup gui elements
    controlP5 = new ControlP5(this);
    playButton = controlP5.addButton("Play");
    playButton.setValue(0);
    playButton.setSize(60, 25);
    playButton.setPosition(width / 2 - playButton.getWidth() / 2,
                           height / 4 - playButton.getHeight() / 2);
    playButton.show();
    introScreen = true;

    // load assets and fonts
    Images.loadImages(this);
    Sounds.loadSounds(this);
    MessageArea.loadFonts(this);

    // initialize variables
    user = new UserSpaceship(this);
    initializeOrResetVariables();
  }

  /* Draws either the introduction screen or the space environment. */
  public void draw() {
    if (introScreen) {
      introScreen();
    } else {
      appScreen();
    }
  }

  /* Initializes or resets variables. */
  public void initializeOrResetVariables() {
    // initialize ArrayLists
    rocks = new ArrayList<AbstractSpaceObject>();
    blackholes = new ArrayList<AbstractSpaceObject>();
    wormholes = new ArrayList<AbstractSpaceObject>();
    traces = new ArrayList<Trace>();

    // choose background randomly
    switch ((int)random(100) % 2) {
      case 0: // simple background + stars
        background = new StarsDecorator(new SimpleBackground());
        break;
      case 1: // simple background + stars + planet
        background
          = new PlanetDecorator(new StarsDecorator(new SimpleBackground()));
        break;
      default:
    }
  }

  /* Draws the introduction screen. */
  public void introScreen() {
    playButton.setPosition(width / 2 - playButton.getWidth() / 2,
                           height / 4 - playButton.getHeight() / 2);
    background(0); // black background
    fill(255); // white text
    textFont(MessageArea.getFont()); // use font of MessageArea
    textAlign(CENTER, CENTER);
    int y = height / 2; // vertical position of text
    // draw text
    text("This is a virtual outer space environment.", width / 2, y);
    text("Use your keyboard's arrow keys to move your spaceship.", width / 2,
         y += 20);
    text("Click the PLAY button above to start the experience.", width / 2,
         y += 20);
    text("You can press your keyboard's Escape button anytime to end the " +
         "experience.", width / 2, y += 20);
  }

  /* Draws the space environment. */
  public void appScreen() {
    background.draw(this);

    // spawn space objects
    spawnRock();
    spawnBlackhole();
    spawnWormhole();

    // update and draw user and space objects
    updateAndDrawUser();
    updateAndDrawRocks();
    updateAndDrawBlackholes();
    updateAndDrawWormholes();
    updateAndDrawTraces();

    // detect and resolve collisions involving user and space objects
    detectAndResolveUserRockCollisions();
    detectAndResolveUserBlackholeCollisions();
    detectAndResolveUserWormholeCollisions();
    detectAndResolveRockRockCollisions();
    detectAndResolveRockBlackholeCollisions();
    detectAndResolveRockWormholeCollisions();
    detectAndResolveBlackholeBlackholeCollisions();
    detectAndResolveBlackholeWormholeCollisions();
    detectAndResolveWormholeWormholeCollisions();

    // remove deactivated space objects
    removeDeactivatedRocks();
    removeDeactivatedBlackholes();
    removeDeactivatedWormholes();
    removeDeactivatedTraces();

    // draw text
    MessageArea.draw(this);
    // drawInfoForDebugging(); // useful for debugging purposes
  }

  /* Spawns a space rock (asteroid or comet) every half second */
  public void spawnRock() {
    if (frameCount % 30 == 0) {
      rocks.add(rockFactory.createRock());
    }
  }

  /*
   * Spawns a blackhole every two seconds but limits the number of blackholes
   * to two.
   */
  public void spawnBlackhole() {
    if (blackholes.size() < 2) {
      if (frameCount % 120 == 0) {
        blackholes.add(new Blackhole(this));
      }
    }
  }

  /*
   * Spawns a wormhole every two seconds but limits the number of wormhole
   * to two.
   */
  public void spawnWormhole() {
    if (wormholes.size() < 2) {
      if (frameCount % 120 == 0) {
        wormholes.add(new Wormhole(this));
      }
    }
  }

  /* Updates and draws user's spaceship. */
  public void updateAndDrawUser() {
    user.move();
    user.draw();
  }

  /* Updates and draws space rocks. */
  public void updateAndDrawRocks() {
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

  /* Updates and draws blackholes. */
  public void updateAndDrawBlackholes() {
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

  /* Updates and draws wormholes. */
  public void updateAndDrawWormholes() {
    for (int i = 0; i < wormholes.size(); ++i) {
      AbstractSpaceObject wormhole = wormholes.get(i);
      if (wormhole.isOutOfScreen()) {
        wormhole.deactivate();
      } else {
        wormhole.move();
        wormhole.draw();
      }
    }
  }

  /* Updates and draws explosion or teleportation traces. */
  public void updateAndDrawTraces() {
    for (int i = 0; i < traces.size(); ++i) {
      Trace trace = traces.get(i);
      trace.move();
      trace.draw();
    }
  }

  /* Detects and resolves collisions between the user's spaceship and rocks. */
  public void detectAndResolveUserRockCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      if (user.isCollidingWith(rock)) {
        traces.add(new ExplosionTrace(this, user.getPosition().get()));
        traces.add(new ExplosionTrace(this, rock.getPosition().get()));
        user.resetPosition();
        rock.deactivate();
        traces.add(new TeleportationTrace(this, user.getPosition().get()));
        Sounds.getExplosion().play();
        Sounds.getExplosion().rewind();
        MessageArea.setUserMessage("You got hit by a rock and respawned!");
      }
    }
  }

  /*
   * Detects and resolves collisions between the user's spaceship and
   * blackholes.
   */
  public void detectAndResolveUserBlackholeCollisions() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole = blackholes.get(i);
      if (user.isCollidingWith(blackhole)) {
        traces.add(new TeleportationTrace(this, user.getPosition().get()));
        user.toRandomPosition();
        traces.add(new TeleportationTrace(this, user.getPosition().get()));
        Sounds.getTeleportation().play();
        Sounds.getTeleportation().rewind();
        MessageArea.setUserMessage
          ("You entered a blackhole and got teleported to a random position!");
      }
    }
  }

  /*
   * Detects and resolves collisions between the user's spaceship and
   * wormholes.
   */
  public void detectAndResolveUserWormholeCollisions() {
    for (int i = 0; i < wormholes.size(); ++i) {
      AbstractSpaceObject wormhole = wormholes.get(i);
      if (user.isCollidingWith(wormhole)) {
        user.resetPosition();
        initializeOrResetVariables();
        traces.add(new TeleportationTrace(this, user.getPosition().get()));
        Sounds.getTeleportation().play();
        Sounds.getTeleportation().rewind();
        MessageArea.setUserMessage
          ("You entered a wormhole and got teleported to a new location!");
      }
    }
  }

  /* Detects and resolves collisions between rocks. */
  public void detectAndResolveRockRockCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock1 = rocks.get(i);
      for (int j = i + 1; j < rocks.size(); ++j) {
        AbstractSpaceObject rock2 = rocks.get(j);
        if (rock1.isCollidingWith(rock2)) {
          traces.add(new ExplosionTrace(this, rock1.getPosition().get()));
          traces.add(new ExplosionTrace(this, rock2.getPosition().get()));
          rock1.deactivate();
          rock2.deactivate();
          Sounds.getExplosion().play();
          Sounds.getExplosion().rewind();
          MessageArea.setEventMessage("Two rocks collided and exploded!");
        }
      }
    }
  }

  /* Detects and resolves collisions between rocks and blackholes. */
  public void detectAndResolveRockBlackholeCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      for (int j = 0; j < blackholes.size(); ++j) {
        AbstractSpaceObject blackhole = blackholes.get(j);
        if (rock.isCollidingWith(blackhole)) {
          traces.add(new TeleportationTrace(this, rock.getPosition().get()));
          rock.toRandomPosition();
          traces.add(new TeleportationTrace(this, rock.getPosition().get()));
          Sounds.getTeleportation().play();
          Sounds.getTeleportation().rewind();
          MessageArea.setEventMessage
            ("A rock entered a blackhole and got teleported to a random " +
             "position!");
        }
      }
    }
  }

  /* Detects and resolves collisions between rocks and wormholes. */
  public void detectAndResolveRockWormholeCollisions() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      for (int j = 0; j < wormholes.size(); ++j) {
        AbstractSpaceObject wormhole = wormholes.get(j);
        if (rock.isCollidingWith(wormhole)) {
          traces.add(new TeleportationTrace(this, rock.getPosition().get()));
          rock.deactivate();
          Sounds.getTeleportation().play();
          Sounds.getTeleportation().rewind();
          MessageArea.setEventMessage
            ("A rock entered a wormhole and disappeared!");
        }
      }
    }
  }

  /* Detects and resolves collisions between blackholes. */
  public void detectAndResolveBlackholeBlackholeCollisions() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole1 = blackholes.get(i);
      for (int j = i + 1; j < blackholes.size(); ++j) {
        AbstractSpaceObject blackhole2 = blackholes.get(j);
        if (blackhole1.isCollidingWith(blackhole2)) {
          traces.add
            (new TeleportationTrace(this, blackhole1.getPosition().get()));
          traces.add
            (new TeleportationTrace(this, blackhole2.getPosition().get()));
          blackhole1.deactivate();
          blackhole2.deactivate();
          Sounds.getTeleportation().play();
          Sounds.getTeleportation().rewind();
          MessageArea.setEventMessage
            ("Two blackholes collided and disappeared!");
        }
      }
    }
  }

  /* Detects and resolves collisions between blackholes and wormholes. */
  public void detectAndResolveBlackholeWormholeCollisions() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole = blackholes.get(i);
      for (int j = 0; j < wormholes.size(); ++j) {
        AbstractSpaceObject wormhole = wormholes.get(j);
        if (blackhole.isCollidingWith(wormhole)) {
          traces.add
            (new TeleportationTrace(this, blackhole.getPosition().get()));
          traces.add
            (new TeleportationTrace(this, wormhole.getPosition().get()));
          blackhole.deactivate();
          wormhole.deactivate();
          Sounds.getTeleportation().play();
          Sounds.getTeleportation().rewind();
          MessageArea.setEventMessage
            ("A blackhole and a wormhole collided and they both disappeared!");
        }
      }
    }
  }

  /* Detects and resolves collisions between wormholes. */
  public void detectAndResolveWormholeWormholeCollisions() {
    for (int i = 0; i < wormholes.size(); ++i) {
      AbstractSpaceObject wormhole1 = wormholes.get(i);
      for (int j = i + 1; j < wormholes.size(); ++j) {
        AbstractSpaceObject wormhole2 = wormholes.get(j);
        if (wormhole1.isCollidingWith(wormhole2)) {
          traces.add
            (new TeleportationTrace(this, wormhole1.getPosition().get()));
          traces.add
            (new TeleportationTrace(this, wormhole2.getPosition().get()));
          wormhole1.deactivate();
          wormhole2.deactivate();
          Sounds.getTeleportation().play();
          Sounds.getTeleportation().rewind();
          MessageArea.setEventMessage
            ("Two wormholes collided and disappeared!");
        }
      }
    }
  }

  /* Removes deactivated space rocks. */
  public void removeDeactivatedRocks() {
    for (int i = 0; i < rocks.size(); ++i) {
      AbstractSpaceObject rock = rocks.get(i);
      if (rock.isDeactivated()) {
        rocks.remove(i);
      }
    }
  }

  /* Removes deactivated blackholes. */
  public void removeDeactivatedBlackholes() {
    for (int i = 0; i < blackholes.size(); ++i) {
      AbstractSpaceObject blackhole = blackholes.get(i);
      if (blackhole.isDeactivated()) {
        blackholes.remove(i);
      }
    }
  }

  /* Removes deactivated wormholes. */
  public void removeDeactivatedWormholes() {
    for (int i = 0; i < wormholes.size(); ++i) {
      AbstractSpaceObject wormhole = wormholes.get(i);
      if (wormhole.isDeactivated()) {
        wormholes.remove(i);
      }
    }
  }

  /* Removes deactivated explosion or teleportation traces. */
  public void removeDeactivatedTraces() {
    for (int i = 0; i < traces.size(); ++i) {
      Trace trace = traces.get(i);
      if (trace.isDeactivated()) {
        traces.remove(i);
      }
    }
  }

  /*
   * Draws the state of important variables to the top left of the screen.
   * This can be useful when debugging.
   */
  public void drawInfoForDebugging() {
    fill(255);
    textAlign(PConstants.LEFT, PConstants.BASELINE);
    text("rocks.size(): " + rocks.size(), 10, 20);
    text("blackholes.size(): " + blackholes.size(), 10, 40);
    text("wormholes.size(): " + wormholes.size(), 10, 60);
    text("traces.size(): " + traces.size(), 10, 80);
    text("frameRate: " + frameRate, 10, 100);
  }

  /* Modifies boolean variables when arrow keys are pressed. */
  public void keyPressed() {
    if (key == CODED && keyCode == UP) { KeysPressed.up = true; }
    if (key == CODED && keyCode == DOWN) { KeysPressed.down = true; }
    if (key == CODED && keyCode == LEFT) { KeysPressed.left = true; }
    if (key == CODED && keyCode == RIGHT) { KeysPressed.right = true; }
  }

  /* Modifies boolean variables when arrow keys are released. */
  public void keyReleased() {
    if (key == CODED && keyCode == UP) { KeysPressed.up = false; }
    if (key == CODED && keyCode == DOWN) { KeysPressed.down = false; }
    if (key == CODED && keyCode == LEFT) { KeysPressed.left = false; }
    if (key == CODED && keyCode == RIGHT) { KeysPressed.right = false; }
  }

  /* Starts the virtual environment when the play button is pressed. */
  public void controlEvent(ControlEvent event) {
    // do something when play button is pressed
    if (event.getController().getName() == "Play") {
      if (introScreen == true) {
        playButton.hide();
        introScreen = false;
      }
    }
  }
}
