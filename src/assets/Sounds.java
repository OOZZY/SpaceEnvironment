package assets;

import ddf.minim.Minim;
import ddf.minim.AudioPlayer;
import processing.core.PApplet;

/*
 * This class stores all sound assets and provides methods to load and retrieve
 * them.
 */
public class Sounds {
  private static Minim minim; // minim object needed to load sound files
  private static AudioPlayer explosion; // sound of an explosion
  private static AudioPlayer teleportation; // sound of a teleportation

  /* Loads all sound assets. */
  public static void loadSounds(PApplet p) {
    try {
      // load sounds
      minim = new Minim(p);
      explosion = minim.loadFile("../assets/explosion.mp3");
      teleportation = minim.loadFile("../assets/teleportation.mp3");
    } catch (NullPointerException e) {
      PApplet.println("Error: Probably minim.loadFile failed.");
      e.printStackTrace();
    } catch (Exception e) {
      PApplet.println("Error: Something went wrong.");
      e.printStackTrace();
    }
  }

  /* Get sound of an explosion. */
  public static AudioPlayer getExplosion() { return explosion; }

  /* Get sound of a teleportation. */
  public static AudioPlayer getTeleportation() { return teleportation; }
}
