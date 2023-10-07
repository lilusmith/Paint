package com.example.paint1;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.scene.text.Text;

/**
 * This class file is used to manage the menu bar and all the relevant menu bar events.
 **/

public class MenuFeatures {
    public ImageView imageView;
    public File currentFile;
    public AnchorPane anchorPane;
    private ColorPicker colorPicker;
    private Color currentColor = Color.RED;
    private static File saveFile;
    private HelloController controller;
    private App app;
    public File selectedFile;
    public MenuFeatures(App application){
        app = application;
    }

    // calls ShapeMath clear function to clear or not clear the entire canvas with an alert
    public void clear() {
        ButtonType noClear = new ButtonType("Don't clear canvas");
        ButtonType yesClear = new ButtonType("Clear canvas");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear the canvas?", noClear, yesClear);
        alert.setTitle("Clear Canvas");
        Optional<ButtonType> x = alert.showAndWait();
        if(x.isPresent()) {
            if (x.get().equals(noClear)) {
                return;
            }
            if (x.get().equals(yesClear)) {
                app.getShapeMath().clear();
            }
        }
    }

    // calling the ShapeMath file methods to the sub tabs in the menu bar
    public void cut(){app.getShapeMath().cut();}
    public void copy(){app.getShapeMath().copy();}
    public void undo(){app.getShapeMath().undo();}
    public void redo(){app.getShapeMath().redo();}

    public void about(){
        // Create a VBox to hold the about content
        VBox root = new VBox();

        // Add text to display about information
        Text aboutText = new Text("Welcome to the About Page!\n\n" +
                "Author: Lilu Smith\n" + "Version 1.0.1\n" + "Date: 9/15/2023");
        root.getChildren().add(aboutText);

        // Create a new scene for the about page and set it as the primary stage's scene
        Scene aboutScene = new Scene(root, 600, 400);
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.setScene(aboutScene);
        aboutStage.show();
    }

    public void exit(){ Platform.exit();}       // exit the entire program

    public void showHelpPage() {
        // Create a VBox to hold the help content
        VBox root = new VBox();

        // Add text to display help information
        Text helpText = new Text("Welcome to the Help Page!\n\n" +
                "Copy and paste below to continue.\n" +
                "https://support.microsoft.com/en-us/windows/help-in-paint-d62e155a-1775-6da4-0862-62a3e9e5a511");
        root.getChildren().add(helpText);

        // Create a new scene for the help page and set it as the primary stage's scene
        Scene helpScene = new Scene(root, 600, 400);
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    // open
    public void open(Stage primaryStage, FileChooser fileChooser) {
        Canvas canvas = app.getCanvas();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        selectedFile = fileChooser.showOpenDialog(primaryStage);
        primaryStage.setTitle(selectedFile.getName());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());
            canvas.getGraphicsContext2D().drawImage(image,0, 0);
        }
        String lastOpenLocation = selectedFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenLocation));
        System.out.println(lastOpenLocation);
    }

    // save
    public void save() {
        Canvas canvas = app.getCanvas();
        if(selectedFile == null){
            saveAs(App.fileChooser, App.getStage2());
        } else{
            String format = selectedFile.getName().toLowerCase().endsWith(".png") ? "png" : "jpg";
            try {
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null,writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, format, selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // save as
    public void saveAs(FileChooser fileChooser, Stage stage) {
        Canvas canvas = app.getCanvas();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp")
        );
        File selectedFile = fileChooser.showSaveDialog(stage);
        String files = selectedFile.toString().substring(selectedFile.toString().lastIndexOf(".") + 1);
        System.out.println(files);
        if (files.equals("jpg")) {
            ButtonType yes = new ButtonType("Yes, save");
            ButtonType no = new ButtonType("No, don't save");

            Alert alert = new Alert(Alert.AlertType.WARNING, "WPotential data loss may occur.\n Are you sure you want to continue?", yes, no);
            alert.setTitle("Potential Data Loss");

            ButtonType x = alert.showAndWait().get();
            if (x == no) {
                return;
            }
        }
        if (files.equals("bmp")) {
            ButtonType yes2 = new ButtonType("Yes, save");
            ButtonType no2 = new ButtonType("No, don't save");
            Alert alert2 = new Alert(Alert.AlertType.WARNING, "Potential data loss may occur.\n Are you sure you want to continue?", yes2, no2);
            alert2.setTitle("Potential Data Loss");

            ButtonType x = alert2.showAndWait().get();
            if (x == no2) {
                return;
            }
        }
        stage.setTitle(selectedFile.getName());
        if (selectedFile != null) {
            String format = selectedFile.getName().toLowerCase().endsWith(".png") ? "png" : "jpg";
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, format, selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveFile = selectedFile;
        }
        String lastOpenLocation = selectedFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenLocation));
        System.out.println(lastOpenLocation);
    }

    // uses new project in menu bar to open a new tab
    public void newProject(TabPane tabPane, List<ShapeMath> listCanvas) {
        TabFeatures newTab = new TabFeatures ("New Tab");
        listCanvas.add(newTab.getCanvas());
        tabPane.getTabs().add(newTab.getTab());
    }
}