package com.example.paint1;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.ToggleSwitch;

/**
 * This class file is used to manage the toolbar features used to draw on the canvas or image
 * Includes the draw toggle buttons, color pickers for lines, fill, and outlines, sliders to adjust line width
 * and dashed line option etc.
 * AutoSave toggle switch is here.
 **/

public class Toolbar {
    private ToolBar toolBar;
    public ToggleSwitch toggleSwitch;
    public Label autosave;
    private static ToggleButton drawButton, drawButton2, drawButton3, drawButton4, drawButton5, drawButton7,
            drawButton6, drawButton8, drawButton9, drawButton10, drawButton11, drawButton12, eyeDropper;
    private static Tooltip lineTip, pencilTip, squareTip, rectTip, myShapeTip, ellTip,
            cirTip, polyTip, textTip, widthTip, eyeDropperTip, outlineTip, fillTip, dashTip;
    public static ColorPicker colorPicker, colorPicker2;
    private static Slider slider, slider2;
    private static TextField textField;
    public static CheckMenuItem move;
    static Stack<Image> stackUndo = new Stack<>();
    static Stack<Image> stackRedo = new Stack<>();
    static List<ToggleButton> toolOff;

    public Toolbar(){

        // toggle buttons for drawing, switch button for auto save
        toggleSwitch = new ToggleSwitch("AutoSave");
        autosave = new Label();
        drawButton = new ToggleButton("Line");
        drawButton2 = new ToggleButton("Rectangle");
        drawButton3 = new ToggleButton("Square");
        drawButton4 = new ToggleButton("Ellipse");
        drawButton5 = new ToggleButton("Circle");
        drawButton7 = new ToggleButton("Triangle");
        drawButton6 = new ToggleButton("Pencil");
        drawButton8 = new ToggleButton("My Shape");
        drawButton9 = new ToggleButton("Erase");
        drawButton10 = new ToggleButton("Text");
        drawButton11 = new ToggleButton("Polygon");
        drawButton12 = new ToggleButton("Selection");
        eyeDropper = new ToggleButton("Eyedropper");

        lineTip = new Tooltip("Tool that draws free hand lines.");
        lineTip.setTextAlignment(TextAlignment.RIGHT);
        drawButton6.setTooltip(lineTip);

        toggleSwitch.setSelected(true);

        toolOff = new ArrayList<ToggleButton>(Arrays.asList(drawButton, drawButton6, drawButton2, drawButton3, drawButton4,
                drawButton5, drawButton7, drawButton11, drawButton8, drawButton9, drawButton10, drawButton12, eyeDropper));

        stackUndo = new Stack<>();
        stackRedo = new Stack<>();
        textField = new TextField();

        colorPicker = new ColorPicker();    // change color of outline

        colorPicker2 = new ColorPicker();   // change color of fill

        slider = new Slider();          // change line width

        slider2 = new Slider();         // change dashed line spacing

        toolBar = new ToolBar();        // adding buttons and switch to toolbar
        toolBar.getItems().addAll(toggleSwitch, autosave, drawButton, drawButton6, drawButton2, drawButton3, drawButton4, drawButton5, drawButton7, drawButton11,
                drawButton8, drawButton9, drawButton12, eyeDropper, colorPicker, colorPicker2, slider, slider2, drawButton10, textField);

        // when one button is selected, the previous button is off
        drawButton.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton.setSelected(true);
        });
        drawButton2.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton2.setSelected(true);
        });
        drawButton3.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton3.setSelected(true);
        });
        drawButton4.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton4.setSelected(true);
        });
        drawButton5.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton5.setSelected(true);
        });
        drawButton6.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton6.setSelected(true);
        });
        drawButton7.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton7.setSelected(true);
        });
        drawButton8.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton8.setSelected(true);
        });
        drawButton9.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton9.setSelected(true);
        });
        drawButton10.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton10.setSelected(true);
        });
        drawButton11.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton11.setSelected(true);
        });
        drawButton12.setOnAction(e-> {
            buttonOff(toolOff);
            drawButton12.setSelected(true);
        });
        eyeDropper.setOnAction(e-> {
            buttonOff(toolOff);
            eyeDropper.setSelected(true);
        });

    }

    private static void buttonOff(List<ToggleButton> list){
        App.paste.setSelected(false);
        App.move.setSelected(false);
        for(int i = 0; i < list.size(); i++){
            list.get(i).setSelected(false);
        }
    }

    // getter functions
    public static ToggleButton getEyeDropper(){return eyeDropper;}
    public static ToggleButton getDrawButton12(){return drawButton12;}
    public static ToggleButton getDrawButton11(){return drawButton11;}
    public static ToggleButton getDrawButton10(){return drawButton10;}
    public static ToggleButton getDrawButton9(){return drawButton9;}
    public static ToggleButton getDrawButton8(){return drawButton8;}
    public static ToggleButton getDrawButton7(){return drawButton7;}
    public static ToggleButton getDrawButton6(){return drawButton6;}
    public static ToggleButton getDrawButton5(){return drawButton5;}
    public static ToggleButton getDrawButton4(){return drawButton4;}
    public static ToggleButton getDrawButton3(){return drawButton3;}
    public static ToggleButton getDrawButton2(){return drawButton2;}
    public static Stack<Image> getStackUndo(){return stackUndo;}
    public static Stack<Image> getStackRedo(){return stackRedo;}
    public static TextField getTextField(){return textField;}
    public static Slider getSlider(){return slider;}
    public static Slider getSlider2(){return slider2;}
    public static ColorPicker getColorPicker(){return colorPicker;}
    public static ColorPicker getColorPicker2(){return colorPicker2;}
    public ToolBar getToolBar() {return toolBar;}
    public static ToggleButton getDrawButton() {return drawButton;}
}
