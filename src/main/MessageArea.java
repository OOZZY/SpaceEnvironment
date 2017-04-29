package main;

import processing.core.PFont;
import processing.core.PApplet;
import processing.core.PConstants;

/*
 * This class implements the message area at the bottom center of the screen
 * which informs the user of events that occur in the virtual space
 * environment.
 */
public class MessageArea {
  private static PFont font; // stores font
  private static String userMessage = ""; // stores messages for the user
  private static String eventMessage = ""; // stores messages about events

  /* Loads the fonts required by the message area. */
  public static void loadFonts(PApplet p) {
    font = p.createFont("Lucida Sans", 12, true);
  }

  /* Draws the message area. */
  public static void draw(PApplet p) {
    p.noStroke();
    p.fill(0, 128);
    p.rectMode(PConstants.CENTER);
    p.rect(p.width / 2, 7 * p.height / 8 + 10, 500, 70);
    p.fill(255);
    p.textFont(font);
    p.textAlign(PConstants.CENTER, PConstants.CENTER);
    p.text(userMessage, p.width / 2, 7 * p.height / 8);
    p.text(eventMessage, p.width / 2, 7 * p.height / 8 + 20);
  }

  /* Returns the font used by the message area. */
  public static PFont getFont() { return font; }

  /* Sets the current message for the user to the given string. */
  public static void setUserMessage(String string) { userMessage = string; }

  /* Sets the current message about an event to the given string. */
  public static void setEventMessage(String string) { eventMessage = string; }
}
