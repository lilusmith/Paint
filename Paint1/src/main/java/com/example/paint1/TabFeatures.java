package com.example.paint1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;

/**
 * This class file is used to create a new tab and implement the drawing class onto each tab.
 **/

public class TabFeatures {
    private Tab tab;
        public ShapeMath canvas;
        public GraphicsContext gc;

        public TabFeatures(String tabName) {
            tab = new Tab(tabName);         // creates a tab
            canvas = new ShapeMath(tab);    // passing the tab through shape math class
            gc = canvas.getGc();
        }

    /**
     * gets the tab associated with this TabFeatures instance
     * @return tab object associated with TabFeatures instance
     */
    public Tab getTab() {return tab;}

    /**
     * gets ShapeMath instance with this object
     * @return canvas associated with the instance
     */
    public ShapeMath getCanvas() {return canvas;}
}