package com.example.paint1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Lilu Smith's Pain(t) Application
 * The Pain(t) application is a program that currently allows users to upload,
 * save and save as images. Auto-save is implemented. These images
 * can be drawn upon and saved as new files. Users can use the following tools:
 * Line, Dashed Line, Pencil, Square, Rectangle, and more.
 * The application also hosts a total of 3 unit tests to ensure that methods work properly.
 *
 * @author Lilu Smith
 * @version 1.0.3
 * @since 2023-11-10
 **/

public class App extends Application {
    private MenuFeatures menuFeatures;
    public AutoSave autoSave;
    public Timer time;
    public FileChooser fileChooser;
    private static Stage stage2;
    public List<com.example.paint1.ShapeMath> listCanvas = new ArrayList<>();
    public TabPane tabPane = new TabPane();
    public static MenuItem rotate, rotateSelec, flipx, flipxSelec, flipy, flipySelec;
    public static CheckMenuItem paste, move;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) {
        TabFeatures newTab = new TabFeatures("New Tab");      // labeling new tab to canvas
        listCanvas.add(newTab.getCanvas());
        tabPane.getTabs().add(newTab.getTab());

        stage2 = primaryStage;
        primaryStage.setTitle("Pain(t)");
        MenuFeatures menu = new MenuFeatures(this);
        fileChooser = new FileChooser();

        BorderPane layout = new BorderPane();

        // main tabs in the menu bar
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu moveMenu = new Menu("Movement");
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

        // sub tabs for movement
        move = new CheckMenuItem("Move Image");
        rotate = new MenuItem("Rotate");
        rotateSelec = new MenuItem("Rotate Selection");
        flipx = new MenuItem("Flip Horizontal");
        flipxSelec = new MenuItem("Flip Horizontal Selection");
        flipy = new MenuItem("Flip Vertical");
        flipySelec = new MenuItem("Flip Vertical Selection");

        // sub tabs for Help
        MenuItem about = new MenuItem("About");
        MenuItem releaseNotes = new MenuItem("Release Notes");

        // adding sub tabs to main tab
        fileMenu.getItems().addAll(newProject, open, save, saveAs, exit);
        editMenu.getItems().addAll(undo, redo, clear, copy, paste, cut);
        moveMenu.getItems().addAll(move, rotate, rotateSelec, flipx, flipxSelec, flipy, flipySelec);
        helpMenu.getItems().addAll(about, releaseNotes);

        // getting the menu bar set up with the main tabs
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, moveMenu, helpMenu);

        Toolbar tools = new Toolbar();
        autoSave = new AutoSave(menuFeatures, tools, 60, this);
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
        saveAs.setOnAction(e -> menu.saveAs());
        releaseNotes.setOnAction(e -> menu.showReleaseNotes());
        about.setOnAction(e -> menu.about());
        exit.setOnAction(e -> menu.exit());
        undo.setOnAction(e-> menu.undo());
        redo.setOnAction(e-> menu.redo());
        clear.setOnAction(e-> menu.clear());
        copy.setOnAction(e-> menu.copy());
        cut.setOnAction(e-> menu.cut());
        rotate.setOnAction(e-> menu.rotate());
        rotateSelec.setOnAction(e-> menu.rotateSelec());
        flipx.setOnAction(e-> menu.flipX());
        flipxSelec.setOnAction(e->menu.flipXSelec());
        flipy.setOnAction(e-> menu.flipY());
        flipySelec.setOnAction(e->menu.flipYSelec());
        startAutoSave();

        // shotcuts
        KeyCombination newtabShortCut = new KeyCodeCombination(KeyCode.N, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination openShortCut = new KeyCodeCombination(KeyCode.O, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination saveShortCut = new KeyCodeCombination(KeyCode.S, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination saveAsShortCut = new KeyCodeCombination(KeyCode.S, javafx.scene.input.KeyCombination.CONTROL_DOWN, javafx.scene.input.KeyCombination.SHIFT_DOWN);
        KeyCombination exitShortCut = new KeyCodeCombination(KeyCode.E, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination undoShortCut = new KeyCodeCombination(KeyCode.Z, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination redoShortCut = new KeyCodeCombination(KeyCode.Y, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination clearShortCut = new KeyCodeCombination(KeyCode.D, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination copyShortCut = new KeyCodeCombination(KeyCode.C, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination pasteShortCut = new KeyCodeCombination(KeyCode.V, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination cutShortCut = new KeyCodeCombination(KeyCode.X, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination rotateShortCut = new KeyCodeCombination(KeyCode.R, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination rotateSelecShortCut = new KeyCodeCombination(KeyCode.R, javafx.scene.input.KeyCombination.CONTROL_DOWN, javafx.scene.input.KeyCombination.SHIFT_DOWN);
        KeyCombination flipxShortCut = new KeyCodeCombination(KeyCode.H, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination flipxSelecShortCut = new KeyCodeCombination(KeyCode.H, javafx.scene.input.KeyCombination.CONTROL_DOWN, javafx.scene.input.KeyCombination.SHIFT_DOWN);
        KeyCombination flipyShortCut = new KeyCodeCombination(KeyCode.U, javafx.scene.input.KeyCombination.CONTROL_DOWN);
        KeyCombination flipySelecShortCut = new KeyCodeCombination(KeyCode.U, javafx.scene.input.KeyCombination.CONTROL_DOWN, javafx.scene.input.KeyCombination.SHIFT_DOWN);

        // shows shortcut keyboard functions in sub tabs
        newProject.setAccelerator(newtabShortCut);
        open.setAccelerator(openShortCut);
        save.setAccelerator(saveShortCut);
        saveAs.setAccelerator(saveAsShortCut);
        exit.setAccelerator(exitShortCut);
        undo.setAccelerator(undoShortCut);
        redo.setAccelerator(redoShortCut);
        clear.setAccelerator(clearShortCut);
        copy.setAccelerator(copyShortCut);
        paste.setAccelerator(pasteShortCut);
        cut.setAccelerator(cutShortCut);
        rotate.setAccelerator(rotateShortCut);
        rotateSelec.setAccelerator(rotateSelecShortCut);
        flipx.setAccelerator(flipxShortCut);
        flipxSelec.setAccelerator(flipxSelecShortCut);
        flipy.setAccelerator(flipyShortCut);
        flipySelec.setAccelerator(flipySelecShortCut);

        // implements shortcuts
        scene.setOnKeyPressed(event-> {
            if(newtabShortCut.match(event)){newProject.fire();}
            if(openShortCut.match(event)){open.fire();}
            if(saveShortCut.match(event)){save.fire();}
            if(saveAsShortCut.match(event)){saveAs.fire();}
            if(exitShortCut.match(event)){exit.fire();}
            if(undoShortCut.match(event)){undo.fire();}
            if(redoShortCut.match(event)){redo.fire();}
            if(clearShortCut.match(event)){clear.fire();}
            if(copyShortCut.match(event)){copy.fire();}
            if(pasteShortCut.match(event)){paste.fire();}
            if(cutShortCut.match(event)){cut.fire();}
            if(rotateShortCut.match(event)){rotate.fire();}
            if(rotateSelecShortCut.match(event)){rotateSelec.fire();}
            if(flipxShortCut.match(event)){flipx.fire();}
            if(flipxSelecShortCut.match(event)){flipxSelec.fire();}
            if(flipyShortCut.match(event)){flipy.fire();}
            if(flipySelecShortCut.match(event)){flipySelec.fire();}
        });
    }

    /**
     * auto save function set to 1-min countdown
     */
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
            time.scheduleAtFixedRate(autoSave,0,1000);
        }
    }

    /**
     * The method obtains the index of the currently selected tab and uses it to access the corresponding
     * ShapeMath instance from the list of ShapeMath objects associated with each tab
     * @return instance associated with the currently selected tab
     */
    public ShapeMath getShapeMath(){
        int x = tabPane.getSelectionModel().getSelectedIndex();
        return listCanvas.get(x);
    }

    /**
     * Retrieves the Canvas associated with the currently selected tab in the TabPane.
     * The method informs the user about the index of the selected tab and prints the
     * selected tab index along with the total number of canvases in the list for debugging purposes
     * @return canvas object associated with the current tab
     */
    public Canvas getCanvas(){
        int x = tabPane.getSelectionModel().getSelectedIndex();      // tells user the tab index
        System.out.println(x + " " + listCanvas.size());            // prints the tab index and canvas size
        return listCanvas.get(x).getCanvas();
    }

    /**
     * This method informs the user about the index of the selected tab for reference
     * @return the current tab index
     */
    public int getSelectedindex(){
        return tabPane.getSelectionModel().getSelectedIndex();      // tells the user the tab index
    }

    /**
     * Displays a confirmation dialog when attempting to exit the paint application.
     * The dialog provides options to save and exit, exit without saving, or cancel the exit operation.
     * If the user chooses to save and exit, the application will attempt to save the current state before closing.
     * If the user chooses to cancel, the exit operation will be canceled.
     * If the user chooses to exit without saving, the application will close without saving the current state.
     * @param primaryStage primaryStage of the application
     */
    public void exitPaint(Stage primaryStage){
        ButtonType saveAndExit = new ButtonType("Save and exit");           // creating buttons to click
        ButtonType exitNoSave = new ButtonType("Don't save and exit");
        ButtonType cancel = new ButtonType("Cancel");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", saveAndExit, exitNoSave, cancel);
        alert.setTitle("Exit Menu");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            ButtonType x = alert.showAndWait().get();
            if(x == saveAndExit){
               menuFeatures.save();
               primaryStage.close();
               System.exit(0);
            }
            if(x == cancel){e.consume();}

            if(x == exitNoSave){
                primaryStage.close();
                System.exit(0);
            }
        });
    }


    /**
     * Logs the users data related to the active tool in the application to a file.
     * The method creates a timestamp, sets up a logger with a file handler and formatter,
     * and records information about the active tool along with the timestamp in a log file
     */
    public static void logData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        sdf.format(date);
        FileHandler fileHandler;
        File modifyFile = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\LoggingOutput.log");
        try {
            // This block configures the logger with handler and formatter
            fileHandler = new FileHandler("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\LoggingOutput.log");
            Toolbar.logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            Toolbar.logger.info("(" + date + ") - Active Tool: " + Toolbar.getTool());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}