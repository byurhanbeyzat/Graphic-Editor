package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.scene.paint.Color;

public class LineShape {
  int x, y, w, h;
  Color c;

  public LineShape(int posX, int posY, int lineWidth, int lineHeight, Color lineColor) {
    x = posX;
    y = posY;
    w = lineWidth;
    h = lineHeight;
    c = lineColor;
  }

//  public void DrawShape(GraphicsContext g) {
//    g.setFill(c);
//    g.strokeLine(x, y, w, h);
//  }
}