package com.example.paint1;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Tab;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

/**
 * This class file is used to manage the auto save feature in the toolbar
 *
 **/

public class AutoSave extends TimerTask {
    int saveInterval;
    int currentTime;
    final MenuFeatures menu;
    final Toolbar tools;
    private File selectedFile;

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
                    WritableImage writableImage = new WritableImage((int)ShapeMath.getCanvas().getWidth(), (int)ShapeMath.getCanvas().getHeight());
                    ShapeMath.getCanvas().snapshot(null,writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", selectedFile);
                } catch (IOException e) {
                    System.out.println("Auto saved");
                }
            });
            // calls save method from menu features when it runs
            Platform.runLater(() -> currentTime = saveInterval);
        }
    }

    public AutoSave(MenuFeatures menu2, Toolbar tools2, int i){
        saveInterval = i;
        currentTime = saveInterval;
        menu = menu2;
        tools = tools2;
    }
}