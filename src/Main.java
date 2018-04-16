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
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import utils.AlertBox;

public class Main extends Application {

  GraphicsContext gc;

  private void drawShapes(GraphicsContext gc) {
    gc.setLineWidth(2);
    gc.fillOval(10, 60, 200, 200);
    gc.strokeOval(240, 60, 200, 200);
  }

  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();

    // =================================================== MENU BAR ====================================================
    // Create menu bar =================================================================================================
    MenuBar menuBar = new MenuBar();

    SeparatorMenuItem divider = new SeparatorMenuItem();

    // Create menus
    Menu fileMenu = new Menu("File");

    // Create FileMenu items
    MenuItem exitItem = new MenuItem("Exit");
    MenuItem aboutItem = new MenuItem("About");
    MenuItem hiwItem = new MenuItem("How it works?");

    // Add menu items to Menus
    fileMenu.getItems().addAll(aboutItem, hiwItem, divider, exitItem);

    // Add Menu to the Menu Bar
    menuBar.getMenus().addAll(fileMenu);

    // Functions
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
    shapeProperties.setPrefWidth(200);

    // Text field for width
    TextField widthField = new TextField("Width:");
    widthField.setOnMouseClicked(e -> widthField.clear());
    GridPane.setConstraints(widthField, 0, 0);

    // Text field for height
    TextField heightField = new TextField("Height:");
    heightField.setOnMouseClicked(e -> heightField.clear());
    GridPane.setConstraints(heightField, 0, 1);

    // Text field for stroke
    TextField strokeField = new TextField("Stroke:");
    strokeField.setOnMouseClicked(e -> strokeField.clear());
    GridPane.setConstraints(strokeField, 0, 2);

    // Color picker for shape color
    Label fillColorLabel = new Label("Choose fill color:");
    GridPane.setConstraints(fillColorLabel, 0, 6);
    ColorPicker fillColor = new ColorPicker(Color.TRANSPARENT);
    fillColor.setPrefWidth(200);
    GridPane.setConstraints(fillColor, 0, 7);

    // Color picker for shape stroke color
    Label strokeColorLabel = new Label("Choose stroke color:");
    GridPane.setConstraints(strokeColorLabel, 0, 8);
    ColorPicker strokeColor = new ColorPicker(Color.BLACK);
    strokeColor.setPrefWidth(200);
    GridPane.setConstraints(strokeColor, 0, 9);

    fillColor.setOnAction(e -> gc.setFill(fillColor.getValue()));
    strokeColor.setOnAction(e -> gc.setStroke(strokeColor.getValue()));

    // Button to draw the shape
    Button generateButton = new Button("Generate Shape");
    GridPane.setConstraints(generateButton, 0, 15);
    generateButton.setPrefWidth(200);

    generateButton.setOnAction(e -> drawShapes(gc));

    // Add all elements to panel
    shapeProperties.getChildren().addAll(widthField, heightField, strokeField, fillColorLabel, fillColor, strokeColorLabel, strokeColor, generateButton);

    // =================================================================================================================
    // Area for drawing the shapes =====================================================================================
    StackPane drawingArea = new StackPane();
    Canvas area = new Canvas(600, 500);
    gc = area.getGraphicsContext2D();
    drawShapes(gc);
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
