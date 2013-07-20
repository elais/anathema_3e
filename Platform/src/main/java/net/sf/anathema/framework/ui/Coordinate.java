package net.sf.anathema.framework.ui;

public class Coordinate {

  public final int x;
  public final int y;

  public Coordinate() {
    this(0, 0);
  }

  public Coordinate(double x, double y) {
    this.x = (int) x;
    this.y = (int) y;
  }

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinate translate(int dx, int dy) {
    return new Coordinate(x + dx, y + dy);
  }
}