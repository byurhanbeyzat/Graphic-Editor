import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import utils.AlertBox;

public class Main extends Application {

  GraphicsContext g;

  private void drawShape(GraphicsContext g) {
    g.fillOval(10, 60, 100, 100);
  }

  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();

    // =================================================== MENU BAR ====================================================
    // Create menu bar =================================================================================================
    MenuBar menuBar = new MenuBar();

    SeparatorMenuItem divider = new SeparatorMenuItem();

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

    // Add Menu to the Menu Bar
    menuBar.getMenus().addAll(fileMenu, helpMenu);

    // Functions
    newCanvasItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
    newCanvasItem.setOnAction(e -> {
      g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
    });

    exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
    exitItem.setOnAction(e -> System.exit(0));

    // ================================================= END MENU BAR ==================================================

    // ==================================================== PANELS =====================================================
    // Panel for choosing the shape ====================================================================================
    GridPane shapeChooser = new GridPane();
    shapeChooser.setPadding(new Insets(30, 40, 10, 20));
    shapeChooser.setVgap(15);

    final ToggleGroup shapes = new ToggleGroup();

    RadioButton rb1 = new RadioButton("Rectangle");
    rb1.setSelected(true);
    rb1.setToggleGroup(shapes);
    GridPane.setConstraints(rb1, 0, 0);

    RadioButton rb2 = new RadioButton("Square");
    rb2.setToggleGroup(shapes);
    GridPane.setConstraints(rb2, 0, 1);

    RadioButton rb3 = new RadioButton("Oval");
    rb3.setToggleGroup(shapes);
    GridPane.setConstraints(rb3, 0, 2);

    RadioButton rb4 = new RadioButton("Line");
    rb4.setToggleGroup(shapes);
    GridPane.setConstraints(rb4, 0, 3);

    // Add all the elements to panel
    shapeChooser.getChildren().addAll(rb1, rb2, rb3, rb4);

    // =================================================================================================================
    // Panel for shape properties ======================================================================================
    GridPane shapeProperties = new GridPane();
    shapeProperties.setPadding(new Insets(30, 20, 10, 20));
    shapeProperties.setVgap(10);
    shapeProperties.setPrefWidth(180);

    // Text field for X position
    Label xPosFieldLabel = new Label("Enter X Position:");
    GridPane.setConstraints(xPosFieldLabel, 0, 0);
    TextField xPosField = new TextField();
    xPosField.setOnMouseClicked(e -> xPosField.clear());
    GridPane.setConstraints(xPosField, 0, 1);

    // Text field for Y position
    Label yPosFieldLabel = new Label("Enter Y Position:");
    GridPane.setConstraints(yPosFieldLabel, 0, 2);
    TextField yPosField = new TextField();
    yPosField.setOnMouseClicked(e -> yPosField.clear());
    GridPane.setConstraints(yPosField, 0, 3);

    // Text field for width
    Label widthFieldLabel = new Label("Enter Width:");
    GridPane.setConstraints(widthFieldLabel, 0, 4);
    TextField widthField = new TextField();
    widthField.setOnMouseClicked(e -> widthField.clear());
    GridPane.setConstraints(widthField, 0, 5);

    // Text field for height
    Label heightFieldLabel = new Label("Enter Height:");
    GridPane.setConstraints(heightFieldLabel, 0, 6);
    TextField heightField = new TextField();
    heightField.setOnMouseClicked(e -> heightField.clear());
    GridPane.setConstraints(heightField, 0, 7);

    // Text field for stroke
    Label strokeFieldLabel = new Label("Enter Stroke width:");
    GridPane.setConstraints(strokeFieldLabel, 0, 8);
    TextField strokeField = new TextField();
    strokeField.setOnMouseClicked(e -> strokeField.clear());
    GridPane.setConstraints(strokeField, 0, 9);

    // Color picker for shape color
    Label fillColorLabel = new Label("Choose fill color:");
    GridPane.setConstraints(fillColorLabel, 0, 14);
    ColorPicker fillColor = new ColorPicker(Color.TRANSPARENT);
    fillColor.setPrefWidth(180);
    GridPane.setConstraints(fillColor, 0, 15);

    // Color picker for shape stroke color
    Label strokeColorLabel = new Label("Choose stroke color:");
    GridPane.setConstraints(strokeColorLabel, 0, 16);
    ColorPicker strokeColor = new ColorPicker(Color.BLACK);
    strokeColor.setPrefWidth(180);
    GridPane.setConstraints(strokeColor, 0, 17);

    fillColor.setOnAction(e -> g.setFill(fillColor.getValue()));
    strokeColor.setOnAction(e -> g.setStroke(strokeColor.getValue()));

    // Button to draw the shape
    Button generateButton = new Button("Generate Shape");
    GridPane.setConstraints(generateButton, 0, 23);
    generateButton.setPrefWidth(180);

    generateButton.setOnAction(e -> {
      if (xPosField.getText().trim().equals("") || yPosField.getText().trim().equals("") || widthField.getText().trim().equals("") || heightField.getText().trim().equals("")) {
        AlertBox.warning("Warning!", "Please fill all of the required fields!");
        return;
      }

      if (strokeField.getText().trim().equals("")) {
        strokeField.setText("1");
      }

      drawShape(g);
    });

    // Add all elements to panel
    shapeProperties.getChildren().addAll(xPosFieldLabel, xPosField, yPosFieldLabel, yPosField, widthFieldLabel, widthField, heightFieldLabel, heightField, strokeFieldLabel, strokeField, fillColorLabel, fillColor, strokeColorLabel, strokeColor, generateButton);

    // =================================================================================================================
    // Area for drawing the shapes =====================================================================================
    StackPane drawingArea = new StackPane();
    Canvas area = new Canvas(600, 500);
    g = area.getGraphicsContext2D();
    drawShape(g);
    drawingArea.setStyle("-fx-background-color: #eee");

    // Add all the elements to panel
    drawingArea.getChildren().add(area);

    // ================================================== END PANELS ===================================================
    // ================================================ Layout Settings ================================================

    root.setTop(menuBar);
    root.setLeft(shapeChooser);
    root.setRight(shapeProperties);
    root.setCenter(drawingArea);

    Scene scene = new Scene(root, 1200, 650);
    primaryStage.getIcons().add(new Image("http://icons.iconarchive.com/icons/iconshock/real-vista-text/256/shapes-icon.png"));
    primaryStage.setTitle("Graphical Shape Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String args[]) {
    launch(args);
  }
}