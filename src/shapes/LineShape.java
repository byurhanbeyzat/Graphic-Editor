package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineShape  implements GraphicShape {
  int posX, posY, lineLength, strokeWidth;
  Color color;

  public LineShape(int posX, int posY, int lineLength, int strokeWidth, Color color) {
    posX = posX;
    posY = posY;
    lineLength = lineLength;
    strokeWidth = strokeWidth;
    color = color;
  }

  public void drawShape(GraphicsContext g) {
    g.setLineWidth(strokeWidth);
    g.setStroke(color);
    g.strokeLine(posX, posY, lineLength, strokeWidth);
  }
}
