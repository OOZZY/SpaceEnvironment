package traces;

/* An interface for traces. */
public interface Trace {
  /* Moves this trace. */
  void move();

  /* Draws this trace. */
  void draw();

  /* Returns whether this trace is deactivated. */
  boolean isDeactivated();
}
