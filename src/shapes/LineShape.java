package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineShape  implements GraphicShape {
  int posX, posY, lineLength, strokeWidth;
  Color color;

  public LineShape(int posX, int posY, int lineLength, int strokeWidth, Color color) {
    this.posX = posX;
    this.posY = posY;
    this.lineLength = lineLength;
    this.strokeWidth = strokeWidth;
    this.color = color;
  }

  public void drawShape(GraphicsContext g) {
    g.setLineWidth(strokeWidth);
    g.setStroke(color);
    g.strokeLine(posX, posY, lineLength, strokeWidth);
  }
}
