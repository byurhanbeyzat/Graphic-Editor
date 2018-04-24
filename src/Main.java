import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.application.Application;
import java.awt.image.RenderedImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javax.imageio.ImageIO;
import java.util.LinkedList;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

import shapes.*;
import utils.AlertBox;

public class Main extends Application {

  GraphicsContext g;
  File file;
  Canvas area;
  MenuBar menuBar;
  RadioButton rb1, rb2, rb3;
  SeparatorMenuItem divider;
  ToggleGroup shapesRadioButtons;
  ColorPicker fillColor, strokeColor;
  Button drawButton, printListButton;
  FileChooser saveImageFileChooser;
  TextField xPosField, yPosField, widthField, heightField, strokeField;
  Label xPosFieldLabel, yPosFieldLabel, widthFieldLabel, heightFieldLabel, strokeFieldLabel, fillColorLabel, strokeColorLabel;
  
  StackPane drawingArea;
  GridPane shapeChooser, shapeProperties;
  VBox shapeButtons, posProperties, sizeProperties, colorProperties;

  final static int CANVAS_WIDTH = 900;
  final static int CANVAS_HEIGHT = 750;

  LinkedList<GraphicShape> shapeList = new LinkedList<GraphicShape>();

  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();

    // =================================================== MENU BAR ====================================================
    menuBar = new MenuBar();
    divider = new SeparatorMenuItem();

    // Create menus
    Menu fileMenu = new Menu("File");
    Menu helpMenu = new Menu("Help");

    // Create FileMenu items
    MenuItem newCanvasItem = new MenuItem("New Canvas");
    MenuItem saveImageItem = new MenuItem("Save Image");
    MenuItem exitItem = new MenuItem("Exit");
    MenuItem licenseItem = new MenuItem("License");

    // Add menu items to Menus
    fileMenu.getItems().addAll(newCanvasItem, saveImageItem, divider, exitItem);
    helpMenu.getItems().addAll(licenseItem);

    menuBar.getMenus().addAll(fileMenu, helpMenu);

    // Functions
    newCanvasItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
    newCanvasItem.setOnAction(e -> {
      g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
      shapeList.clear();
      xPosField.clear();
      yPosField.clear();
      widthField.clear();
      heightField.clear();
      strokeField.clear();
      strokeColor.setValue(Color.BLACK);
      fillColor.setValue(Color.TRANSPARENT);
    });

    saveImageItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
    saveImageItem.setOnAction(e -> {
      saveImageFileChooser = new FileChooser();

      // set extension filter
      FileChooser.ExtensionFilter pngExtFilter = new FileChooser.ExtensionFilter("PNG Files / *.png", "*.png");
      saveImageFileChooser.getExtensionFilters().add(pngExtFilter);

      // show save file dialog
      file = saveImageFileChooser.showSaveDialog(primaryStage);

      if (file != null) {
        try {
          WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
          area.snapshot(null, writableImage);
          RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
          ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
    exitItem.setOnAction(e -> {
      AlertBox.confirm("Exit Graphic Editor", "Exit Graphic Editor","Are you sure you want to quit Graphic Editor?");
      System.exit(0);
    });

    licenseItem.setOnAction(e -> {
      StackPane panel1 = new StackPane();
      TextArea licenseItemArea = new TextArea(
        "MIT License\n" +
        "\n" +
        "Copyright (c) 2018 Byurhan Beyzat\n" +
        "\n" +
        "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
        "of this software and associated documentation files (the \"Software\"), to deal\n" +
        "in the Software without restriction, including without limitation the rights\n" +
        "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
        "copies of the Software, and to permit persons to whom the Software is\n" +
        "furnished to do so, subject to the following conditions:\n" +
        "\n" +
        "The above copyright notice and this permission notice shall be included in all\n" +
        "copies or substantial portions of the Software.\n" +
        "\n" +
        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
        "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
        "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
        "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
        "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
        "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
        "SOFTWARE.\n");
      licenseItemArea.setEditable(false);
      panel1.getChildren().add(licenseItemArea);
      Stage stage = new Stage();
      stage.setTitle("License");
      stage.setScene(new Scene(panel1, 680, 390));
      stage.show();
    });

    // ================================================= END MENU BAR ==================================================
    // ==================================================== PANELS =====================================================
    shapeChooser = new GridPane();
    shapeChooser.setPadding(new Insets(30, 0, 0, 20));
    shapeChooser.setPrefWidth(180);

    shapeButtons = new VBox();
    shapesRadioButtons = new ToggleGroup();
    
    rb1 = new RadioButton("Rectangle");
    rb2 = new RadioButton("Oval");
    rb3 = new RadioButton("Line");
    rb1.setToggleGroup(shapesRadioButtons);
    rb2.setToggleGroup(shapesRadioButtons);
    rb3.setToggleGroup(shapesRadioButtons);

    shapeButtons.setSpacing(15);
    shapeButtons.getChildren().addAll(rb1, rb2, rb3);
    GridPane.setConstraints(shapeButtons, 0, 0);

    shapeChooser.getChildren().addAll(shapeButtons);
    
    // Panel for shape properties ======================================================================================
    shapeProperties = new GridPane();
    shapeProperties.setPadding(new Insets(30, 20, 10, 20));
    shapeProperties.setVgap(20);
    shapeProperties.setPrefWidth(200);

    posProperties = new VBox();
    posProperties.setSpacing(5);

    xPosFieldLabel = new Label("Enter X Position:");
    xPosField = new TextField();

    yPosFieldLabel = new Label("Enter Y Position:");
    yPosField = new TextField();
    
    posProperties.getChildren().addAll(xPosFieldLabel, xPosField, yPosFieldLabel, yPosField);
    GridPane.setConstraints(posProperties, 0, 0);
    
    sizeProperties = new VBox();
    sizeProperties.setSpacing(5);

    widthFieldLabel = new Label("Enter Width:");
    widthField = new TextField();

    heightFieldLabel = new Label("Enter Height:");
    heightField = new TextField();

    strokeFieldLabel = new Label("Enter Stroke width:");
    strokeField = new TextField();

    sizeProperties.getChildren().addAll(widthFieldLabel, widthField, heightFieldLabel, heightField, strokeFieldLabel, strokeField);
    GridPane.setConstraints(sizeProperties, 0, 1);
    
    colorProperties = new VBox();

    fillColorLabel = new Label("Choose fill color:");
    fillColor = new ColorPicker(Color.TRANSPARENT);
    fillColor.setPrefWidth(200);

    strokeColorLabel = new Label("Choose stroke color:");
    strokeColor = new ColorPicker(Color.BLACK);
    strokeColor.setPrefWidth(200);
    
    // Color picker functionality
    fillColor.setOnAction(e -> g.setFill(fillColor.getValue()));
    strokeColor.setOnAction(e -> g.setStroke(strokeColor.getValue()));

    colorProperties.setSpacing(5);
    colorProperties.getChildren().addAll(fillColorLabel, fillColor, strokeColorLabel, strokeColor);
    GridPane.setConstraints(colorProperties, 0, 2);
    
    // Button to draw the shape
    drawButton = new Button("Draw Shape");
    GridPane.setConstraints(drawButton, 0, 3);
    drawButton.setPrefWidth(200);

    // Button to print the shape list to console
    printListButton = new Button("Print List");
    GridPane.setConstraints(printListButton, 0, 4);
    printListButton.setPrefWidth(200);

    drawButton.setOnAction(e -> {
      int x, y, w, h, stroke;
      try {
        x = Integer.parseInt(xPosField.getText());
        y = Integer.parseInt(yPosField.getText());
        w = Integer.parseInt(widthField.getText());
        h = Integer.parseInt(heightField.getText());
        stroke = Integer.parseInt(strokeField.getText());

        if (x >= g.getCanvas().getWidth() || y >= g.getCanvas().getHeight() || w >= g.getCanvas().getWidth() || h >= g.getCanvas().getHeight()) {
          AlertBox.warning("Too Big Properties", "Too Big Properties", "Please enter more smaller properties for the shape!");
        } else {
          if (rb1.isSelected()) {
            shapeList.add(new RectShape(x, y, w, h, stroke, fillColor.getValue(), strokeColor.getValue()));
          } else if (rb2.isSelected()) {
            shapeList.add(new OvalShape(x, y, w, h, stroke, fillColor.getValue(), strokeColor.getValue()));
          } else if (rb3.isSelected()) {
            shapeList.add(new LineShape(x, y, w, stroke, strokeColor.getValue()));
          }
        }
      }
      catch (Exception exc) {
        AlertBox.warning("Empty Fields", "Empty Fields", "To continue please fill Position and Size fields!");
      }
      
      drawShape(g);
    });
    
    printListButton.setOnAction(e -> {
      if (shapeList.size() == 0) {
        System.out.print("Empty list!");
      }
      for (int i = 0; i < shapeList.size(); i++) {
        System.out.println(shapeList.get(i).getClass().toString());
      }
    });
    
    // Add all elements to panel
    shapeProperties.getChildren().addAll(posProperties, sizeProperties, colorProperties, drawButton, printListButton);
    
    // Area for drawing the shapes =====================================================================================
    drawingArea = new StackPane();
    drawingArea.setStyle("-fx-background-color: #eee");
    area = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    g = area.getGraphicsContext2D();
    drawingArea.getChildren().add(area);

    // ================================================== END PANELS ===================================================
    // ================================================ Layout Settings ================================================
    root.setTop(menuBar);
    root.setLeft(shapeChooser);
    root.setRight(shapeProperties);
    root.setCenter(drawingArea);
    
    Scene scene = new Scene(root, 1300, 750);
    scene.getStylesheets().add("assets/Main.css");
    primaryStage.getIcons().add(new Image("http://icons.iconarchive.com/icons/iconshock/real-vista-text/256/shapes-icon.png"));
    primaryStage.setTitle("Graphical Shape Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void drawShape(GraphicsContext g) {
    for (int i = 0; i < shapeList.size(); i++) {
      shapeList.get(i).drawShape(g);
    }
  }

  public static void main(String args[]) {
    launch(args);
  }
}