package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OvalShape implements GraphicShape {
  int posX, posY, ovalWidth, ovalHeight, strokeWidth;
  Color fillColor, strokeColor;

  public OvalShape(int posX, int posY, int ovalWidth, int ovalHeight, int strokeWidth, Color fillColor, Color strokeColor) {
    this.posX = posX;
    this.posY = posY;
    this.ovalWidth = ovalWidth;
    this.ovalHeight = ovalHeight;
    this.fillColor = fillColor;
    this.strokeColor = strokeColor;
    this.strokeWidth = strokeWidth;
  }

  public void drawShape(GraphicsContext g) {
    g.setLineWidth(strokeWidth);
    g.setStroke(strokeColor);
    g.setFill(fillColor);
    g.fillOval(posX, posY, ovalWidth, ovalHeight);
    g.strokeOval(posX, posY, ovalWidth, ovalHeight);
  }
}
