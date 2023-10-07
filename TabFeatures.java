package com.example.paint1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;

/**
 * This class file is used to create a new tab and implement the drawing class onto each tab.
 **/

public class TabFeatures {
        private Tab tab;
        private static ShapeMath canvas;
        public GraphicsContext gc;
        // public File openFile;

        // get a new tab
        public TabFeatures(String tabName) {
            tab = new Tab(tabName);
            canvas = new ShapeMath(tab);
            gc = canvas.getGc();
        //    openFile = null;
        }

        public Tab getTab() {
            return tab;
        }

        public static ShapeMath getCanvas() {
            return canvas;
        }

        public GraphicsContext getGc() {
            return gc;
        }
    }