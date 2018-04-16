package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OvalShape {
  int x, y, w, h;
  Color fc, sc;

  public OvalShape(int posX, int posY, int ovalWidth, int ovalHeight, Color fillColor, Color strokeColor) {
    x = posX;
    y = posY;
    w = ovalWidth;
    h = ovalHeight;
    fc = fillColor;
    sc = strokeColor;
  }

  public void DrawShape(GraphicsContext g) {
    g.fillOval(x, y, w, h);
  }
}
