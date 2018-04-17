import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.LinkedList;

import shapes.*;
import utils.AlertBox;

public class Main extends Application {

  GraphicsContext g;
  Canvas area;
  MenuBar menuBar;
  RadioButton rb1, rb2, rb3;
  SeparatorMenuItem divider;
  ToggleGroup shapesRadioButtons;
  ColorPicker fillColor, strokeColor;
  Button drawButton, printListButton;
  TextField xPosField, yPosField, widthField, heightField, strokeField;
  Label xPosFieldLabel, yPosFieldLabel, widthFieldLabel, heightFieldLabel, strokeFieldLabel, fillColorLabel, strokeColorLabel;
  
  StackPane drawingArea;
  GridPane shapeChooser, shapeProperties;
  VBox shapeButtons, posProperties, sizeProperties, colorProperties;
  
  R Draw = new R();

  LinkedList<GraphicShape> shapeList = new LinkedList<GraphicShape>();

  class R extends Canvas {
    public void drawShape(GraphicsContext g) {
      for (int i = 0; i < shapeList.size(); i++) {
        shapeList.get(i).drawShape(g);
      }
    }
  }

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
    MenuItem exitItem = new MenuItem("Exit");
    MenuItem aboutItem = new MenuItem("About");
    MenuItem hiwItem = new MenuItem("How it works?");

    // Add menu items to Menus
    fileMenu.getItems().addAll(newCanvasItem, divider, exitItem);
    helpMenu.getItems().addAll(aboutItem, hiwItem);
    
    menuBar.getMenus().addAll(fileMenu, helpMenu);

    // Functions
    newCanvasItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
    newCanvasItem.setOnAction(e -> {
      g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
    });

    exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
    exitItem.setOnAction(e -> {
      AlertBox.confirm("Exit Graphic Editor", "Exit Graphic Editor","Are you sure you want to quit Graphic Editor?");
      System.exit(0);
    });

    // ================================================= END MENU BAR ==================================================
    // ==================================================== PANELS =====================================================
    // Panel for choosing the shape ====================================================================================
    shapeChooser = new GridPane();
    shapeChooser.setPadding(new Insets(30, 0, 0, 20));
    shapeChooser.setPrefWidth(180);

    shapeButtons = new VBox();
    shapesRadioButtons = new ToggleGroup();
    
    rb1 = new RadioButton("Rectangle");
    rb1.setToggleGroup(shapesRadioButtons);
    
    rb2 = new RadioButton("Oval");
    rb2.setToggleGroup(shapesRadioButtons);
    
    rb3 = new RadioButton("Line");
    rb3.setToggleGroup(shapesRadioButtons);

    shapeButtons.setSpacing(15);
    shapeButtons.getChildren().addAll(rb1, rb2, rb3);
    GridPane.setConstraints(shapeButtons, 0, 0);

    // Add all the elements to panel
    shapeChooser.getChildren().addAll(shapeButtons);
    
    // Panel for shape properties ======================================================================================
    shapeProperties = new GridPane();
    shapeProperties.setPadding(new Insets(30, 20, 10, 20));
    shapeProperties.setVgap(20);
    shapeProperties.setPrefWidth(200);

    posProperties = new VBox();
    posProperties.setSpacing(5);
    
    // Text field for X position
    xPosFieldLabel = new Label("Enter X Position:");
    xPosField = new TextField();

    // Text field for Y position
    yPosFieldLabel = new Label("Enter Y Position:");
    yPosField = new TextField();
    
    posProperties.getChildren().addAll(xPosFieldLabel, xPosField, yPosFieldLabel, yPosField);
    GridPane.setConstraints(posProperties, 0, 0);
    
    sizeProperties = new VBox();

    // Text field for width
    widthFieldLabel = new Label("Enter Width:");
    widthField = new TextField();

    // Text field for height
    heightFieldLabel = new Label("Enter Height:");
    heightField = new TextField();

    // Text field for stroke
    strokeFieldLabel = new Label("Enter Stroke width:");
    strokeField = new TextField();

    sizeProperties.setSpacing(5);
    sizeProperties.getChildren().addAll(widthFieldLabel, widthField, heightFieldLabel, heightField, strokeFieldLabel, strokeField);
    GridPane.setConstraints(sizeProperties, 0, 1);
    
    colorProperties = new VBox();
    
    // Color picker for shape color
    fillColorLabel = new Label("Choose fill color:");
    fillColor = new ColorPicker(Color.TRANSPARENT);
    fillColor.setPrefWidth(200);

    // Color picker for shape stroke color
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

        if (x > g.getCanvas().getWidth() || y > g.getCanvas().getHeight() || w > g.getCanvas().getWidth() || h > g.getCanvas().getHeight()) {
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
      
      Draw.drawShape(g);
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
    area = new Canvas(800, 600);
    g = area.getGraphicsContext2D();
    Draw.drawShape(g);
    drawingArea.setStyle("-fx-background-color: #eee");

    // Add all the elements to panel
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
    primaryStage.setOnCloseRequest(e -> {
      AlertBox.confirm("Exit Graphic Editor", "Exit Graphic Editor", "Are you sure you want to quit Graphic Editor?");
    });
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String args[]) {
    launch(args);
  }
}