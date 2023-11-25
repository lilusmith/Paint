package com.example.paint1;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.control.ScrollPane;

/**
 * This class file is used to manage the menu bar and all the relevant menu bar events.
 **/

public class MenuFeatures {
    private App app;
    public File selectedFile, saveFile;

    /** Constructs a MenuFeatures instance associated with the specified application
     * @param application App instance representing the main application
     */
    public MenuFeatures(App application){
        app = application;
    }
    public HashMap<Integer, File> hashMap = new HashMap<>();

    /** calls ShapeMath clear function to clear or not clear the entire canvas with an alert */
    public void clear() {
        ButtonType yesClear = new ButtonType("Clear Canvas");
        ButtonType noClear = new ButtonType("Don't Clear Canvas");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear the canvas?", yesClear, noClear);
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

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for cut */
    public void cut(){app.getShapeMath().cut();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for copy */
    public void copy(){app.getShapeMath().copy();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for undo */
    public void undo(){app.getShapeMath().undo();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for redo*/
    public void redo(){app.getShapeMath().redo();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for rotate */
    public void rotate(){app.getShapeMath().rotateImage();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for rotate selection */
    public void rotateSelec(){app.getShapeMath().rotateSelection();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for flip on x-axis */
    public void flipX(){app.getShapeMath().flipX();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for flip selection on x-axis */
    public void flipXSelec(){app.getShapeMath().flipXSelection();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for flip on y-axis */
    public void flipY(){app.getShapeMath().flipY();}

    /** calling the ShapeMath file methods to the sub tabs in the menu bar for flip selection on y-axis */
    public void flipYSelec(){app.getShapeMath().flipYSelection();}

    /** method used to open up a text file for the about page and description of tool features */
    public void about(){
        VBox root = new VBox();

        // Add text to display about information
        Text aboutText = new Text("Welcome to the About Page!\n\n" +
                "Author: Lilu Smith\n" + "Version 1.0.5\n" + "Date: 10/20/2023");
        root.getChildren().add(aboutText);

        String filePath = "C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\About.txt";
        File about = new File(filePath);

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox();

        try {
            // Read the contents of the file
            Scanner scanner = new Scanner(about);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Text fileText = new Text(line);
                content.getChildren().add(fileText);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create a new scene for the about page and set it as the primary stage's scene
        scrollPane.setContent(content);
        root.getChildren().add(scrollPane);
        Scene aboutScene = new Scene(root, 600, 400);
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.setScene(aboutScene);
        aboutStage.show();
    }

    /** method used to exit the entire program */
    public void exit(){ Platform.exit();}

    /** method used to open up a text file about release notes */
    public void showReleaseNotes() {
        VBox root = new VBox();
        Text helpText = new Text("Welcome to the Release Notes Page!\n");
        root.getChildren().add(helpText);

        // hardcoded file path
        String filePath = "C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\Lilu's Pain(t) ReleaseNotes.txt";
        File release = new File(filePath);

        // Create a ScrollPane to allow scrolling through content
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox();

        try {
            // Read the contents of the file
            Scanner scanner = new Scanner(release);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Text fileText = new Text(line);
                content.getChildren().add(fileText);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scrollPane.setContent(content);
        root.getChildren().add(scrollPane);
        Scene helpScene = new Scene(root, 600, 400);
        Stage helpStage = new Stage();
        helpStage.setTitle("Release Notes");
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    /**
     * Opens and loads an image file into the Canvas of the application using a FileChooser.
     * Updates the application title with the name of the opened file, sets the Canvas dimensions
     * based on the image size, and draws the image onto the Canvas.
     * Updates the initial directory of the FileChooser to the last opened location
     * and keeps track of opened files in a HashMap associating tab indices with opened files.
     * @param primaryStage primaryStage of the application
     * @param fileChooser used the select image files for opening
     */
    public void open(Stage primaryStage, FileChooser fileChooser) {
        Canvas canvas = app.getCanvas();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            primaryStage.setTitle(selectedFile.getName());
            Image image = new Image(selectedFile.toURI().toString());
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());
            canvas.getGraphicsContext2D().drawImage(image,0, 0);

            if(! hashMap.containsValue(selectedFile)){      // taking tab index, assign file index, looking to see if it's there or not
                                                            // if not, putting it into the hashmap
                hashMap.put(app.getSelectedindex(), selectedFile);      // adds to the dictionary of files
            }
        }
        String lastOpenLocation = selectedFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenLocation));
        System.out.println(lastOpenLocation);
    }

    /** save any modifications made on the canvas */
    public void save() {
        Canvas canvas = app.getCanvas();
        if(hashMap.containsKey(app.getSelectedindex())){
            selectedFile = hashMap.get(app.getSelectedindex());
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
        else {
            saveAs();
        }
    }

    /** save as, specifically pick why type of file to save the canvas as or change file types with a warning about potential data loss */
    public void saveAs() {
        Canvas canvas = app.getCanvas();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp")
        );
        File selectedFile = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        String files = selectedFile.toString().substring(selectedFile.toString().lastIndexOf(".") + 1);
        System.out.println(files);
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        if ((files.equals("jpg")) || (files.equals("bmp") || (files.equals("png")))) {   // Lossy compression, warn user
            ButtonType yes = new ButtonType("Yes, Save");
            ButtonType no = new ButtonType("No, Don't Save");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Potential data loss may occur.\nAre you sure you want to continue?", yes, no);
            alert.setTitle("Potential Data Loss");
            ButtonType x = alert.showAndWait().get();
            if (x == no) {
                return;
            }
            if(x == yes){
                bufferedImage = pngTojpg(bufferedImage);
            } else {
                bufferedImage = pngTobmp(bufferedImage);
            }
        }
        if (selectedFile != null) {
            // Koeppen was here
            String format = selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.') + 1);
            try {
                ImageIO.write(bufferedImage, format, selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveFile = selectedFile;           // changing files types from one to another

            if(! hashMap.containsValue(selectedFile)){
                hashMap.put(app.getSelectedindex(), selectedFile);      // adds to the dictionary of files
            }
        }
        String lastOpenLocation = selectedFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenLocation));
        System.out.println(lastOpenLocation);
    }

    /** Will convert a png image down to an image format compatible with the jpg Image Writer,
     * this will result in a loss of transparency
     * @param image The image to convert
     * @return A new image with fewer data that can be saved as a jpg using the ImageIO Image Writer
     */
    private static BufferedImage pngTojpg(BufferedImage image) {
        if (image.getType() == 3 || image.getType() == 2) {
            BufferedImage newBufferedImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // draw a white background and puts the originalImage on it.
            newBufferedImage.createGraphics()
                    .drawImage(image, 0, 0, java.awt.Color.WHITE, null);
            return newBufferedImage;
        } else return null;
    }

    /**
     * Will convert a png image down to an image format compatible with the bmp Image Writer,
     * this will result in a loss of transparency and color accuracy
     * @param image the image to convert
     * @return a new image with fewer data that can be saved as a bmp using the ImageIO Image Writer
     */
    private static BufferedImage pngTobmp(BufferedImage image) {
        if (image.getType() == 3 || image.getType() == 2) {
            BufferedImage newBufferedImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_3BYTE_BGR);
            // draw a white background and puts the originalImage on it.
            newBufferedImage.createGraphics()
                    .drawImage(image, 0, 0, java.awt.Color.WHITE, null);
            return newBufferedImage;
        } else return null;
    }

    /**
     * Creates and adds a new project tab to the specified TabPane and associates a new ShapeMath instance
     * with the tab, adding it to the provided list of ShapeMath objects
     * @param tabPane where the new project tab will be added
     * @param listCanvas list of ShapeMath object where the new ShapeMath instance will be added
     */
    public void newProject(TabPane tabPane, List<ShapeMath> listCanvas) {
        TabFeatures newTab = new TabFeatures ("New Tab");
        listCanvas.add(newTab.getCanvas());
        tabPane.getTabs().add(newTab.getTab());
    }
}