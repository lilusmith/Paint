package com.example.paint1;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

/**
 * This class file is used to manage the auto save feature in the toolbar
 **/

public class AutoSave extends TimerTask {
    int saveInterval;
    int currentTime;
    final MenuFeatures menu;
    final Toolbar tools;
    private File selectedFile;
    public App app;

    @Override
    public void run() {
        Platform.runLater(() -> {
            String count;
            int sec;
            int min;
            sec = currentTime % 60;
            min = (currentTime / 60) % 60;
            count = min + ":" + sec;
            tools.autosave.setText(count);
            tools.autosave.setVisible(tools.toggleSwitch.isSelected());
        });

        if(tools.toggleSwitch.isSelected()){
            currentTime --;
        }
        if(currentTime == 0){
            Platform.runLater(() ->{
                try {
                    selectedFile = new File("C:\\Users\\lilus\\Downloads\\AutoSaved.png");
                    WritableImage writableImage = new WritableImage((int)app.getCanvas().getWidth(), (int)app.getCanvas().getHeight());
                    app.getCanvas().snapshot(null,writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", selectedFile);
                    // notifies the user their work has been automatically saved
                    Notifications.create().text("Saved image").hideAfter(new Duration(4000)).owner(app.getCanvas().getScene().getWindow()).show();
                } catch (IOException e) {
                    System.out.println("Auto saved");
                }
            });
            // calls save method from menu features when it runs
            Platform.runLater(() -> currentTime = saveInterval);
        }
    }

    /**
     * Constructs an AutoSave object with the specified parameters to enable automatic saving of the application state.
     * @param menu2 menuFeatures instance associated with the application for handling menu-related features
     * @param tools2 toolbar instance associated with the application for handling toolbar-related features
     * @param i time interval (in seconds) for automatic saving
     * @param app2 App instance representing the main application
     */
    public AutoSave(MenuFeatures menu2, Toolbar tools2, int i, App app2){
        app = app2;
        saveInterval = i;
        currentTime = saveInterval;
        menu = menu2;
        tools = tools2;
    }
}