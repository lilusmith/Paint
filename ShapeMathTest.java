package com.example.paint1;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Tab;
import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShapeMathTest {
    private String tabName;

    @Test
    public void ShapeMathforWidth(){
        JFXPanel name = new JFXPanel();
        Tab t2 = new Tab();
        ShapeMath test = new ShapeMath(t2);
        double width = 1000;
        test.setWidth(1000);
        assertEquals(width, test.getWidth());
    }

    @Test
    public void ShapeMathforHeight(){
        JFXPanel name = new JFXPanel();
        Tab t3 = new Tab();
        ShapeMath test = new ShapeMath(t3);
        double height = 800;
        test.setHeight(800);
        assertEquals(height, test.getHeight());
    }

    @Test
    public void TabFeaturesTest(){
        JFXPanel name = new JFXPanel();
        TabFeatures test = new TabFeatures(tabName);
        assertEquals(false, test.getTab().isSelected());
    }

}