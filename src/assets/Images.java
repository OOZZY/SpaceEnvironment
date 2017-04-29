package assets;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;

/*
 * This class stores all image assets and provides methods to load and retrieve
 * them.
 */
public class Images {
  private static PImage stars; // image of blue stars
  private static PImage planet; // image of a red planet
  private static PImage rock; // image of a space rock
  private static PImage flame; // image of a flame
  private static PImage ship; // image of a spaceship
  private static PImage starsRed; // image of red stars
  private static PImage planetBlue; // image of a blue planet

  /* Loads all image assets. */
  public static void loadImages(PApplet p) {
    try {
      // load/create images
      stars = p.loadImage("../assets/stars.jpg");
      planet = p.loadImage("../assets/planet.png");
      rock = p.loadImage("../assets/rock.png");
      flame = p.loadImage("../assets/flame.png");
      ship = p.loadImage("../assets/ship.png");
      starsRed = p.createImage(stars.width, stars.height, PConstants.RGB);
      planetBlue = p.createImage(planet.width, planet.height, PConstants.ARGB);

      // create a red version of the stars image
      for (int i = 0; i < starsRed.pixels.length; ++i) {
        float r = p.red(stars.pixels[i]);
        float g = p.green(stars.pixels[i]);
        float b = p.blue(stars.pixels[i]);
        starsRed.pixels[i] = p.color(b, g, r); // swapped red and blue
      }

      // create a blue version of the planet image
      for (int i = 0; i < planetBlue.pixels.length; ++i) {
        float r = p.red(planet.pixels[i]);
        float g = p.green(planet.pixels[i]);
        float b = p.blue(planet.pixels[i]);
        float a = p.alpha(planet.pixels[i]);
        planetBlue.pixels[i] = p.color(b, g, r, a); // swapped red and blue
      }
    } catch (NullPointerException e) {
      PApplet.println("Error: Probably loadImage/createImage failed.");
      e.printStackTrace();
    } catch (ArrayIndexOutOfBoundsException e) {
      PApplet.println("Error: Accessed invalid index of pixels array.");
      e.printStackTrace();
    } catch (Exception e) {
      PApplet.println("Error: Something went wrong.");
      e.printStackTrace();
    }
  }

  /* Get image of blue stars. */
  public static PImage getStars() { return stars; }

  /* Get image of a red planet. */
  public static PImage getPlanet() { return planet; }

  /* Get image of a space rock. */
  public static PImage getRock() { return rock; }

  /* Get image of a flame. */
  public static PImage getFlame() { return flame; }

  /* Get image of a spaceship. */
  public static PImage getShip() { return ship; }

  /* Get image of red stars. */
  public static PImage getStarsRed() { return starsRed; }

  /* Get image of a blue planet. */
  public static PImage getPlanetBlue() { return planetBlue; }
}
