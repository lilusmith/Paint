package com.example.paint1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.undo.UndoManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Lilu Smith's Pain(t) Application
 * The Pain(t) application is a program that currently allows users to upload,
 * save and save as images. Auto-save is going to be implemented. These images
 * can be drawn upon and saved as new files. Users can use the following tools:
 * Line, Dashed Line, Pencil, Square, Rectangle, and more. More features will
 * come in the future.
 * The application also hosts a total of 3 unit tests to ensure that methods
 * work properly.
 *
 * @author Lilu Smith
 * @version 1.0.3
 * @since 2022-10-3
 **/

public class App extends Application {
    public ImageView imageView;
    private MenuFeatures menuFeatures;
    public Scene scene;
    public AutoSave autoSave;
    public Timer time;
    public static FileChooser fileChooser;
    private static Stage stage2;
    public List<com.example.paint1.ShapeMath> listCanvas = new ArrayList<>();
    public TabPane tabPane = new TabPane();
    private UndoManager ShapeMath;
    public static CheckMenuItem paste, move;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) {
        TabFeatures newTab = new TabFeatures("New Tab");      // adding new tab to canvas
        listCanvas.add(newTab.getCanvas());
        tabPane.getTabs().add(newTab.getTab());

        stage2 = primaryStage;
        primaryStage.setTitle("Pain(t)");
        MenuFeatures menu = new MenuFeatures(this);
        fileChooser = new FileChooser();

        BorderPane layout = new BorderPane();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu drawMenu = new Menu("Draw");
        Menu helpMenu = new Menu("Help");

        // sub tabs for file
        MenuItem newProject = new MenuItem("New Project");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem exit = new MenuItem("Exit");

        // sub tabs for edit
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        MenuItem clear = new MenuItem("Clear");
        MenuItem copy = new MenuItem("Copy");
        paste = new CheckMenuItem("Paste");
        MenuItem cut = new MenuItem("Cut");
        move = new CheckMenuItem("Move Image");

        // sub tabs for Help
        MenuItem help = new MenuItem("Help");
        MenuItem about = new MenuItem("About");

        // adding sub tabs to main tab
        fileMenu.getItems().addAll(newProject, open, save, saveAs, exit);
        editMenu.getItems().addAll(undo, redo, clear, copy, paste, cut, move);
        helpMenu.getItems().addAll(help, about);

        // getting the menu bar set up with the main tabs
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        Toolbar tools = new Toolbar();
        autoSave = new AutoSave(menuFeatures, tools, 10);
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox(menuBar, tools.getToolBar());
        layout.setTop(vBox);
        layout.setCenter(scrollPane);
        Scene scene = new Scene(layout, 960, 600);
        layout.setCenter(tabPane);

        stage2.setOnCloseRequest(e ->{
            e.consume();
            exitPaint(stage2);
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        // implements tabs from menuFeatures class
        open.setOnAction(event -> menu.open(stage2, fileChooser));
        save.setOnAction(event -> menu.save());
        newProject.setOnAction(e-> menu.newProject(tabPane, listCanvas));
        saveAs.setOnAction((e -> menu.saveAs(fileChooser, primaryStage)));
        help.setOnAction(e -> menu.showHelpPage());
        about.setOnAction(e -> menu.about());
        exit.setOnAction(e -> menu.exit());
        undo.setOnAction(e-> menu.undo());
        redo.setOnAction(e-> menu.redo());
        clear.setOnAction(e-> menu.clear());
        copy.setOnAction(e-> menu.copy());
        cut.setOnAction(e-> menu.cut());
        startAutoSave();

        // shotcuts
        KeyCombination newtabShortCut = new KeyCodeCombination(KeyCode.N, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination openShortCut = new KeyCodeCombination(KeyCode.O, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination saveShortCut = new KeyCodeCombination(KeyCode.S, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination saveAsShortCut = new KeyCodeCombination(KeyCode.S, javafx.scene.input.KeyCombination.CONTROL_DOWN, javafx.scene.input.KeyCombination.SHIFT_DOWN);
        KeyCombination exitShortCut = new KeyCodeCombination(KeyCode.E, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination undoShortCut = new KeyCodeCombination(KeyCode.Z, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination redoShortCut = new KeyCodeCombination(KeyCode.Y, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination copyShortCut = new KeyCodeCombination(KeyCode.C, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination pasteShortCut = new KeyCodeCombination(KeyCode.V, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination cutShortCut = new KeyCodeCombination(KeyCode.X, javafx.scene.input.KeyCombination.CONTROL_DOWN);

        // implements shortcuts
        scene.setOnKeyPressed(event-> {
            if(newtabShortCut.match(event)){newProject.fire();}

            if(openShortCut.match(event)){open.fire();}

            if(saveShortCut.match(event)){save.fire();}

            if(saveAsShortCut.match(event)){saveAs.fire();}

            if(exitShortCut.match(event)){exit.fire();}

            if(undoShortCut.match(event)){undo.fire();}

            if(redoShortCut.match(event)){redo.fire();}

            if(copyShortCut.match(event)){copy.fire();}

            if(pasteShortCut.match(event)){paste.fire();}

            if(cutShortCut.match(event)){cut.fire();}
        });
    }

    public void startAutoSave(){
        try {
           if(time == null){
               time = new Timer();
           }
           time.scheduleAtFixedRate(autoSave,0,1000);
        } catch (Exception e) {
            time.cancel();
            time.purge();
            time = new Timer();
           // autoSave = new AutoSave();
            time.scheduleAtFixedRate(autoSave,0,1000);
        }
    }

    public ShapeMath getShapeMath(){
        int x = tabPane.getSelectionModel().getSelectedIndex();
        return listCanvas.get(x);
    }
    public Canvas getCanvas(){
        int x = tabPane.getSelectionModel().getSelectedIndex();
        System.out.println(x + " " + listCanvas.size());
        return listCanvas.get(x).getCanvas();
    }

    public GraphicsContext getGc(){
        int x = tabPane.getSelectionModel().getSelectedIndex();
        System.out.println(x + " " + listCanvas.size());
        return listCanvas.get(x).getGc();
    }
    public static Stage getStage2() {
        return stage2;
    }

    // exit menu with 3 buttons for confirmation
    public void exitPaint(Stage priamaryStage){
        ButtonType saveAndExit = new ButtonType("Save and exit");
        ButtonType exitNoSave = new ButtonType("Don't save and exit");
        ButtonType cancel = new ButtonType("Cancel");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", saveAndExit, exitNoSave, cancel);
        alert.setTitle("Exit Menu");
        priamaryStage.setOnCloseRequest(e -> {
            e.consume();
            ButtonType x = alert.showAndWait().get();
            if(x == saveAndExit){
               menuFeatures.save();
               priamaryStage.close();
               System.exit(0);
            }
            if(x == cancel){e.consume();}

            if(x == exitNoSave){
                priamaryStage.close();
                System.exit(0);
            }
        });
    }

    public Scene getScene(){
        return scene;
    }
}