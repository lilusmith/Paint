package com.example.paint1;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.File;
import java.util.*;
import org.controlsfx.control.ToggleSwitch;
import java.util.logging.Logger;

/**
 * This class file is used to manage the toolbar features used to draw on the canvas or image
 * Includes the draw toggle buttons, color pickers for lines, fill, and outlines, sliders to adjust line width
 * and dashed line option etc.
 * AutoSave toggle switch and logging the data is here.
 * Configuring the layout of the toolbar is included, along with the image icons and tooltips for each button.
 **/

public class Toolbar {
    public static Logger logger;
    public static String activeTool;
    private static ToolBar toolBar;
    public ToggleSwitch toggleSwitch;
    public Label autosave, fillColor, outlineColor, lineWidth, dashLine;
    public HBox fillBox, outBox, lineBox, dashBox;
    public static ToggleButton freeDraw, lineDraw, rectangleDraw, squareDraw, ellipseDraw, circleDraw, triangleDraw,
            myshapeDraw, erase, text, polyDraw, selection, eyeDropper;
    private static Tooltip freeLineTip, lineTip, squareTip, rectTip, myShapeTip, ellTip, triTip,
            cirTip, eraseTip, polyTip, textTip, selecTip, eyeDropperTip;
    public static ColorPicker colorPicker, colorPicker2;
    private static Slider slider, slider2;
    private static TextField textField;
    static Stack<Image> stackUndo = new Stack<>();
    static Stack<Image> stackRedo = new Stack<>();
    static List<ToggleButton> toolOff;

    // hard encoded image icons for buttons
    final private static File freehandIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\pencil.png");
    final private static File lineIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\line.png");
    final private static File sqrIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\square.png");
    final private static File rectIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\rectangle.png");
    final private static File myshapeIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\myshape.png");
    final private static File ellIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\ellipse.png");
    final private static File cirIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\circle.png");
    final private static File triIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\triangle.png");
    final private static File polyIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\poly.png");
    final private static File selecIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\selec.png");
    final private static File eyeIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\eye.png");
    final private static File eraserIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\erase.jpg");
    final private static File textIcon = new File("C:\\Users\\lilus\\OneDrive\\Documents\\CS 250\\images\\text.jpg");

    public Toolbar(){
        logger = Logger.getLogger(this.getClass().getName());

        // labeling the toggle buttons and toggle switch
        toggleSwitch = new ToggleSwitch("AutoSave");
        autosave = new Label();
        freeDraw = new ToggleButton("Pencil");
        lineDraw = new ToggleButton("Line");
        rectangleDraw = new ToggleButton("Rectangle");
        squareDraw = new ToggleButton("Square");
        ellipseDraw = new ToggleButton("Ellipse");
        circleDraw = new ToggleButton("Circle");
        triangleDraw = new ToggleButton("Triangle");
        polyDraw = new ToggleButton("Polygon");
        myshapeDraw = new ToggleButton("My Shape");
        eyeDropper = new ToggleButton("Eyedropper");
        erase = new ToggleButton("Erase");
        selection = new ToggleButton("Selection");
        text = new ToggleButton("Text");

        // tool tips that describes the toggle buttons when hovering over the button
        freeLineTip = new Tooltip("Tool that draws free hand lines");
        freeDraw.setTooltip(freeLineTip);
        lineTip = new Tooltip("Tool that draws straight lines");
        lineDraw.setTooltip(lineTip);
        rectTip = new Tooltip("Tool that draws rectangles");
        rectangleDraw.setTooltip(rectTip);
        squareTip = new Tooltip("Tool that draws squares");
        squareDraw.setTooltip(squareTip);
        ellTip = new Tooltip("Tool that draws ellipses");
        ellipseDraw.setTooltip(ellTip);
        cirTip = new Tooltip("Tool that draws circles");
        circleDraw.setTooltip(cirTip);
        triTip = new Tooltip("Tool that draws triangles");
        triangleDraw.setTooltip(triTip);
        polyTip = new Tooltip("Tool that draws polygons");
        polyDraw.setTooltip(polyTip);
        myShapeTip = new Tooltip("Tool that draws rounded rectangles");
        myshapeDraw.setTooltip(myShapeTip);
        eyeDropperTip = new Tooltip("Grabs color to replace fill and outline");
        eyeDropper.setTooltip(eyeDropperTip);
        eraseTip = new Tooltip("Tool that erases");
        erase.setTooltip(eraseTip);
        selecTip = new Tooltip("Tool to select sections on workspace");
        selection.setTooltip(selecTip);
        textTip = new Tooltip("Enables text to be put on workspace");
        text.setTooltip(textTip);

        // adds image icon next to name of toggle button
        freeDraw.setGraphic(openImage(freehandIcon));
        lineDraw.setGraphic(openImage(lineIcon));
        rectangleDraw.setGraphic(openImage(rectIcon));
        squareDraw.setGraphic(openImage(sqrIcon));
        ellipseDraw.setGraphic(openImage(ellIcon));
        circleDraw.setGraphic(openImage(cirIcon));
        triangleDraw.setGraphic(openImage(triIcon));
        polyDraw.setGraphic(openImage(polyIcon));
        myshapeDraw.setGraphic(openImage(myshapeIcon));
        eyeDropper.setGraphic(openImage(eyeIcon));
        erase.setGraphic(openImage(eraserIcon));
        selection.setGraphic(openImage(selecIcon));
        text.setGraphic(openImage(textIcon));

        toolOff = new ArrayList<ToggleButton>(Arrays.asList(freeDraw, lineDraw, rectangleDraw, squareDraw, ellipseDraw,
                circleDraw, triangleDraw, polyDraw, myshapeDraw, eyeDropper, erase, selection, text));

        toggleSwitch.setSelected(true);     // toggle switch is used for autosave

        stackUndo = new Stack<>();
        stackRedo = new Stack<>();
        textField = new TextField();        // creating a textbox

        colorPicker = new ColorPicker();    // change color of outline
        colorPicker2 = new ColorPicker();   // change color of fill in shapes

        slider = new Slider();          // slider for changing line width
        slider2 = new Slider();         // slider for changing dashed line spacing

        // stacking the shapes and editing tools into a 3x4 toolbar layout
        VBox vbox = new VBox(freeDraw, lineDraw, rectangleDraw);
        VBox vbox2 = new VBox(squareDraw, ellipseDraw, circleDraw);
        VBox vbox3 = new VBox(triangleDraw, polyDraw, myshapeDraw);
        VBox vbox5 = new VBox(eyeDropper, erase, selection);
        HBox hbox = new HBox(vbox, vbox2, vbox3, vbox5);
        hbox.setSpacing(10);        // adds spacing between the buttons

        // stacking the color pickers on top of each other with a vbox for the layout of the toolbar
        outlineColor = new Label(" Outline Color");
        fillColor = new Label(" Fill Color");
        outBox = new HBox(colorPicker, outlineColor);
        fillBox = new HBox(colorPicker2, fillColor);
        VBox vbox4 = new VBox(outBox, fillBox);
        vbox4.setSpacing(10);       // adds spacing between the color pickers

        // stacking the sliders on top of each other with a vbox for the layout of the toolbar
        lineWidth = new Label(" Line Width");
        dashLine = new Label(" Dashed Line");
        lineBox = new HBox(slider, lineWidth);
        dashBox = new HBox(slider2, dashLine);
        VBox vbox6 = new VBox(lineBox, dashBox);
        vbox6.setSpacing(10);       // adds spacing between the sliders

        // creating separator lines for the toolbar
        Separator[] lines = new Separator[]{ new Separator(Orientation.VERTICAL), new Separator(Orientation.VERTICAL), new Separator(Orientation.VERTICAL)};

        toolBar = new ToolBar();        // adding buttons and toggle switch to toolbar
        // putting all the features into the toolbar with separators
        toolBar.getItems().addAll(toggleSwitch, autosave, lines[0], hbox, lines[1], vbox4, vbox6, lines[2], text, textField);
        toolBar.setBackground(Background.fill(Color.POWDERBLUE));      // setting the background color of the toolbar

        // methods for when one button is selected or not
        freeDraw.setOnAction(e-> {
            buttonOff(toolOff);               // button starts as off
            freeDraw.setSelected(true);       // once selected, the corresponding drawing function is being called
            App.logData();                    // logs the data into a file
            activeTool = "Free hand drawn";   // output put into the file and terminal once the toggle button is not active anymore
        });
        lineDraw.setOnAction(e-> {
            buttonOff(toolOff);
            lineDraw.setSelected(true);
            App.logData();
            activeTool = "Line drawn";
        });
        rectangleDraw.setOnAction(e-> {
            buttonOff(toolOff);
            rectangleDraw.setSelected(true);
            App.logData();
            activeTool = "Rectangle drawn";
        });
        squareDraw.setOnAction(e-> {
            buttonOff(toolOff);
            squareDraw.setSelected(true);
            App.logData();
            activeTool = "Square drawn";
        });
        ellipseDraw.setOnAction(e-> {
            buttonOff(toolOff);
            ellipseDraw.setSelected(true);
            App.logData();
            activeTool = "Ellipse drawn";
        });
        circleDraw.setOnAction(e-> {
            buttonOff(toolOff);
            circleDraw.setSelected(true);
            App.logData();
            activeTool = "Circle drawn";
        });
        triangleDraw.setOnAction(e-> {
            buttonOff(toolOff);
            triangleDraw.setSelected(true);
            App.logData();
            activeTool = "Triangle drawn";
        });
        polyDraw.setOnAction(e-> {
            buttonOff(toolOff);
            polyDraw.setSelected(true);
            App.logData();
            activeTool = "Polygon drawn";
        });
        myshapeDraw.setOnAction(e-> {
            buttonOff(toolOff);
            myshapeDraw.setSelected(true);
            App.logData();
            activeTool = "Rounded rectangle drawn";
        });
        eyeDropper.setOnAction(e-> {
            buttonOff(toolOff);
            eyeDropper.setSelected(true);
            App.logData();
            activeTool = "Eyedropper used";
        });
        erase.setOnAction(e-> {
            buttonOff(toolOff);
            erase.setSelected(true);
            App.logData();
            activeTool = "Eraser used";
        });
        selection.setOnAction(e-> {
            buttonOff(toolOff);
            selection.setSelected(true);
            App.logData();
            activeTool = "Selection used";
        });
        text.setOnAction(e-> {
            buttonOff(toolOff);
            text.setSelected(true);
            App.logData();
            activeTool = "Text used";
        });
        App.paste.setOnAction(e-> {
            buttonOff(toolOff);
            App.paste.setSelected(true);
            App.logData();
            activeTool = "Paste used";
        });
        App.move.setOnAction(e-> {
            buttonOff(toolOff);
            App.move.setSelected(true);
            App.logData();
            activeTool = "User moved an image";
        });
        App.rotate.setOnAction(e-> {
            App.logData();
            activeTool = "User rotated an image";
        });
        App.rotateSelec.setOnAction(e-> {
            App.logData();
            activeTool = "User rotated a selection of an image";
        });
        App.flipx.setOnAction(e-> {
            App.logData();
            activeTool = "User flipped an image on the x-axis";
        });
        App.flipxSelec.setOnAction(e-> {
            App.logData();
            activeTool = "User flipped an image selection on the x-axis";
        });
        App.flipy.setOnAction(e-> {
            App.logData();
            activeTool = "User flipped an image on the y-axis";
        });
        App.flipySelec.setOnAction(e-> {
            App.logData();
            activeTool = "User flipped an image selection on the y-axis";
        });
    }

    /**
     * This method makes sure the toggle buttons are set to off initially to running the program
     * @param list of toggle buttons to loop through
     */
    private static void buttonOff(List<ToggleButton> list){
        App.paste.setSelected(false);
        App.move.setSelected(false);
        for(int i = 0; i < list.size(); i++){       // for loop to pass go through all toggle buttons in the list
            list.get(i).setSelected(false);
        }
    }

     /**This method takes a file to open an image to
     * the size of 25, 25. It can be used as an icon for the toggle buttons in the toolbar
     * @param p, file object
     * @return an image view
     */
    private static ImageView openImage(File p){
        Image i = new Image(p.toURI().toString(), 25, 25, false, false);
        ImageView iv = new ImageView(i);
        return iv;
    }

    // getter functions for the toolbar
    /** gets active tool as a string
     * @return a string representing the active tool */
    public static String getTool(){return activeTool;}

    /** gets ToggleButton representing the eye dropper tool.
     * @return ToggleButton instance associated with the eye dropper */
    public static ToggleButton getEyeDropper(){return eyeDropper;}

    /** gets ToggleButton representing selection tool.
     * @return ToggleButton instance associated with selection */
    public static ToggleButton getDrawButton12(){return selection;}

    /** gets ToggleButton representing polygon tool.
     * @return ToggleButton instance associated with polygon shape */
    public static ToggleButton getDrawButton11(){return polyDraw;}

    /** gets ToggleButton representing text tool.
     * @return ToggleButton instance associated with text */
    public static ToggleButton getDrawButton10(){return text;}

    /** gets ToggleButton representing eraser tool.
     * @return ToggleButton instance associated with eraser */
    public static ToggleButton getDrawButton9(){return erase;}

    /** gets ToggleButton representing myshape tool.
     * @return ToggleButton instance associated with special shape */
    public static ToggleButton getDrawButton8(){return myshapeDraw;}

    /** gets ToggleButton representing triangle tool.
     * @return ToggleButton instance associated with triangle shape */
    public static ToggleButton getDrawButton7(){return triangleDraw;}

    /** gets ToggleButton representing line tool.
     * @return ToggleButton instance associated with straight lines */
    public static ToggleButton getDrawButton6(){return lineDraw;}

    /** gets ToggleButton representing circle tool.
     * @return ToggleButton instance associated with circle shapes */
    public static ToggleButton getDrawButton5(){return circleDraw;}

    /** gets ToggleButton representing ellipse tool.
     * @return ToggleButton instance associated with ellipse shapes */
    public static ToggleButton getDrawButton4(){return ellipseDraw;}

    /** gets ToggleButton representing square tool.
     * @return ToggleButton instance associated with square shapes */
    public static ToggleButton getDrawButton3(){return squareDraw;}

    /** gets ToggleButton representing rectangle tool.
     * @return ToggleButton instance associated with rectangle shapes */
    public static ToggleButton getDrawButton2(){return rectangleDraw;}

    /** gets ToggleButton representing pencil tool.
     * @return ToggleButton instance associated with free hand lines */
    public static ToggleButton getDrawButton() {return freeDraw;}

    /** gets undo stack of image objects.
     * @return Undo stack used for storing snapshots of the canvas state */
    public static Stack<Image> getStackUndo(){return stackUndo;}

    /** gets redo stack containing image objects.
     * @return redo stack used for storing snapshots of the canvas state for redo function */
    public static Stack<Image> getStackRedo(){return stackRedo;}

    /** gets textfield instances from the application
     * @return textField instance used for input or display within the application */
    public static TextField getTextField(){return textField;}

    /** gets slider instance
     * @return slider for adjusting line width */
    public static Slider getSlider(){return slider;}

    /** gets a second slider instance
     * @return slider for adjusting line type */
    public static Slider getSlider2(){return slider2;}

    /** gets a color picker instance
     * @return color picker for outline of shapes and lines */
    public static ColorPicker getColorPicker(){return colorPicker;}

    /** gets a second color picker instance
     * @return color picker for fill of shapes and text */
    public static ColorPicker getColorPicker2(){return colorPicker2;}

    /** gets a toolbar instance
     * @return toolbar to provide access to the tools in the application */
    public static ToolBar getToolBar(){return toolBar;}
}