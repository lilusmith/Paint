package com.example.paint1;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 * This class file is used to manage the features used to draw on the canvas or image
 * and the features in the edit and movement tab of the menu bar.
 * Includes the mouse interaction movements, calling each draw function button, menu bar functions, and undo/redo stacks
 **/

public class ShapeMath extends Canvas {
    private static final int CANVAS_WIDTH = 1000;
    private static final int CANVAS_HEIGHT = 800;
    private Canvas canvas;
    private GraphicsContext gc;
    double startX, startY;
    double endX, endY;
    private ScrollPane sp;
    public Image imageSelection;
    private Point2D mouseLocation = new Point2D(0,0);
    static Stack<Image> stackUndo = new Stack<>();
    static Stack<Image> stackRedo = new Stack<>();
    BufferedImage bimage;
    BufferedImage bimage2;
    Image image;

    /**
     * This method constructs a ShapeMath object for a specified Tab, initializing a Canvas with default dimensions,
     * setting up the graphics context, cursor, and scroll pane, and configuring various mouse event handlers
     * for different drawing tools and features.
     * @param tab to which the Canvas and associated drawing tools will be added.
     */
    public ShapeMath(Tab tab){
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setCursor(Cursor.CROSSHAIR);
        sp = new ScrollPane(canvas);
        tab.setContent(sp);

        // setting up mouse moved event handler to track mouse location
        canvas.setOnMouseMoved(e-> {
            mouseLocation.add(e.getX(), e.getY());
        });

        // Setting up mouse pressed event handler for various drawing tools
        canvas.setOnMousePressed(e -> {         // once mouse is pressed, start drawing the corresponding shape
            // color pickers and sliders being applied to the drawing features
            initDraw(Toolbar.getColorPicker(), Toolbar.getColorPicker2(), Toolbar.getSlider(), Toolbar.getSlider2(), gc);
            startX = e.getX();
            startY = e.getY();
            System.out.println("Tab ID: " + tab.getId());

            // free hand draw starts once mouse is pressed down
            if(Toolbar.getDrawButton().isSelected()){
                gc.beginPath();
                gc.moveTo(startX, startY);
                gc.stroke();
                addUndo(stackUndo, canvas);
            }
            // rectangle drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton2().isSelected()){addUndo(stackUndo, canvas);}

            // square drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton3().isSelected()){addUndo(stackUndo, canvas);}

            // ellipse drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton4().isSelected()){addUndo(stackUndo, canvas);}

            // circle drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton5().isSelected()){addUndo(stackUndo, canvas);}

            // line drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton6().isSelected()){addUndo(stackUndo, canvas);}

            // triangle drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton7().isSelected()){addUndo(stackUndo, canvas);}

            // my shape drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton8().isSelected()){addUndo(stackUndo, canvas);}

            // free hand eraser tool starts once mouse is pressed down
            if(Toolbar.getDrawButton9().isSelected()){
                gc.beginPath();
                gc.moveTo(startX, startY);
                gc.setStroke(Color.WHITE);
                addUndo(stackUndo, canvas);
            }

            // text appears on canvas once mouse is pressed down
            if(Toolbar.getDrawButton10().isSelected()){addUndo(stackUndo, canvas);}

            // polygon drawing starts once mouse is pressed down
            if(Toolbar.getDrawButton11().isSelected()){addUndo(stackUndo, canvas);}

            // selection starts once mouse is pressed down
            if(Toolbar.getDrawButton12().isSelected()){addUndo(stackUndo, canvas);}
        });

        // Setting up mouse dragged event handler for various drawing tools
        canvas.setOnMouseDragged(e -> {         // live draw, making sure to be able to draw in all directions, is implemented
            double endX = e.getX();             // Once mouse is dragged, shapes can be drawn in any direction
            double endY = e.getY();

            if(App.move.isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                gc.drawImage(imageSelection, endX, endY);
            }
            if(Toolbar.getDrawButton().isSelected()){       // free hand draw
                gc.lineTo(endX, endY);
                gc.stroke();
            }
            if(Toolbar.getDrawButton2().isSelected()){      // rectangle live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawRectangle(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton3().isSelected()){      // square live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawSquare(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton4().isSelected()){      // ellipse live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawEllipse(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton5().isSelected()){      // circle live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawCircle(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton6().isSelected()){      // line live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawPencil(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton7().isSelected()){      // triangle live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawTriangle(startX, startY, endX,endY, 3);
            }
            if(Toolbar.getDrawButton8().isSelected()){      // my shape live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawMyShape(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton9().isSelected()){      // free hand eraser
                gc.lineTo(endX, endY);
                gc.stroke();
            }
            if(Toolbar.getDrawButton10().isSelected()){     // text
                gc.lineTo(endX, endY);
            }
            if(Toolbar.getDrawButton11().isSelected()){     // polygon live draw
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawPolygon(startX, startY, endX,endY, 5);
            }
        });

        // Setting up mouse released event handler for various drawing tools
        canvas.setOnMouseReleased(e-> {             // once mouse is released, corresponding shapes are drawn on the canvas
            endX = e.getX();
            endY = e.getY();

            if(Toolbar.getDrawButton2().isSelected()){           // calls rectangle
                this.drawRectangle(startX, startY, endX, endY);  // gets all start and end points
                stackRedo.clear();                               // clears all elements currently in the stack redo
            }
            if(Toolbar.getDrawButton3().isSelected()){           // calls square
                this.drawSquare(startX, startY, endX, endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton4().isSelected()){           // calls ellipse
               this.drawEllipse(startX, startY, endX, endY);
               stackRedo.clear();
            }
            if(Toolbar.getDrawButton5().isSelected()){           // calls circle
                this.drawCircle(startX, startY, endX, endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton6().isSelected()){          // calls line
                this.drawPencil(startX, startY, endX, endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton7().isSelected()){          // calls triangle
                this.drawTriangle(startX, startY, endX, endY, 3);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton8().isSelected()){          // calls my shape
                this.drawMyShape(startX, startY, endX, endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton11().isSelected()){          // calls polygon
                this.drawPolygon(startX, startY, endX, endY, 5);
                stackRedo.clear();
            }

            /**
             * @param x Getting start and end points with concise conditional statements for the selection button
             * If the condition startX < endX = true, value of x will be set to startX
             * If the condition startX < endX = false, value of x will be set to endX
             * @param y Getting start and end points with concise conditional statements for the selection button
             * If the condition startY < endY = true, value of y will be set to startY
             * If the condition startY < endY = false, value of y will be set to endY
             */
            if(Toolbar.getDrawButton12().isSelected()){          // selection button for copy and cut
                double x = (startX < endX ? startX : endX);
                double y = (startY < endY ? startY : endY);
                double w = Math.abs(endX-startX);
                double h = Math.abs(endY-startY);
                System.out.println("Image Getter: " + w + " " + h);      // prints out the size of the selection to the terminal
                image = canvas.snapshot(null, null);
                bimage = SwingFXUtils.fromFXImage(image,null);
                bimage2 = new BufferedImage((int)w, (int)h, BufferedImage.OPAQUE);
                bimage2.createGraphics().drawImage(bimage.getSubimage((int) x, (int) y, (int) w, (int) h), 0, 0, null);
                imageSelection = SwingFXUtils.toFXImage(bimage2,null);
                stackRedo.clear();
                Toolbar.selection.setSelected(false);
            }
        });

        // Setting up mouse clicked event handler for various drawing tools
        canvas.setOnMouseClicked(e-> {
            if(Toolbar.getDrawButton10().isSelected()){         // text is being put onto the canvas when mouse is clicked
                String s = Toolbar.getTextField().getText();
                gc.fillText(s, startX, startY);
                stackRedo.clear();
            }
            else if(App.paste.isSelected()){                    // pasting an image onto the canvas when mouse is clicked
                if (imageSelection == null) {
                    System.out.println("This image is null");   // check to make sure the function is being called
                } else {
                    System.out.println("This image is not null");
                }
                gc.drawImage(imageSelection, e.getX(), e.getY());
            }
            if(Toolbar.getEyeDropper().isSelected()){          // getting the color when the mouse is clicked for the eyedropper
                this.eyeDropper(startX, startY);
                stackRedo.clear();
            }
        });
    }

    /** rotate an entire image 90 degrees each time */
    public void rotateImage(){
        System.out.println("Rotated Image 90 deg");
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        ImageView iv = new ImageView(writableImage);
        iv.setRotate(90);
        WritableImage writableImage2 = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, 0, 0);
    }

    /** rotate a selection by 90 degrees */
    public void rotateSelection(){
        ImageView iv = new ImageView(imageSelection);
        iv.setRotate(90);
        WritableImage writableImage2 = new WritableImage((int)imageSelection.getWidth(), (int)imageSelection.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, startX, startY);
    }

    /** flip entire image on the y-axis */
    public void flipY() {
        System.out.println("Flip image on Y axis");
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        ImageView iv = new ImageView(writableImage);
        iv.setScaleX(-1);
        WritableImage writableImage2 = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, 0, 0);
    }

    /** flip selections on the y-axis */
    public void flipYSelection(){
        ImageView iv = new ImageView(imageSelection);
        iv.setScaleX(-1);
        WritableImage writableImage2 = new WritableImage((int)imageSelection.getWidth(), (int)imageSelection.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, startX, startY);
    }

    /** flip entire image on the x-axis */
    public void flipX(){
        System.out.println("Flip image on x axis");
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        ImageView iv = new ImageView(writableImage);
        iv.setScaleY(-1);
        WritableImage writableImage2 = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, 0, 0);
    }

    /** flip selections on the x-axis */
    public void flipXSelection(){
        ImageView iv = new ImageView(imageSelection);
        iv.setScaleY(-1);
        WritableImage writableImage2 = new WritableImage((int)imageSelection.getWidth(), (int)imageSelection.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, startX, startY);
    }

    /** cut function is used to cut out parts of the drawing on the canvas or image that is selected */
    public void cut(){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        double x = (startX < endX ? startX : endX);
        double y = (startY < endY ? startY : endY);
        double w = Math.abs(endX-startX);
        double h = Math.abs(endY-startY);
        System.out.println("Test Cut: " + w + " " + h);
        image = canvas.snapshot(null, null);
        bimage = SwingFXUtils.fromFXImage(image,null);
        bimage2 = new BufferedImage((int)w, (int)h, BufferedImage.OPAQUE);
        bimage2.createGraphics().drawImage(bimage.getSubimage((int) x, (int) y, (int) w, (int) h), 0, 0, null);
        imageSelection = SwingFXUtils.toFXImage(bimage2,null);
        gc.strokeRect(startX, startY, endX, endY);
        gc.fillRect(startX, startY, endX, endY);
    }

    /** copies part of the drawing or image selected */
    public void copy(){
        double x = (startX < endX ? startX : endX);
        double y = (startY < endY ? startY : endY);
        double w = Math.abs(endX-startX);
        double h = Math.abs(endY-startY);
        System.out.println("Test Copy: " + w + " " + h);
        image = canvas.snapshot(null, null);
        bimage = SwingFXUtils.fromFXImage(image,null);
        bimage2 = new BufferedImage((int)w, (int)h, BufferedImage.OPAQUE);
        bimage2.createGraphics().drawImage(bimage.getSubimage((int) x, (int) y, (int) w, (int) h), 0, 0, null);
        imageSelection = SwingFXUtils.toFXImage(bimage2,null);
    }

    /** uses a stack to undo any actions performed */
    public void undo(){
        if(! stackUndo.empty()){
            WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(null, writableImage);
            Image x = stackUndo.pop();
            gc.drawImage(x, 0, 0);
            stackRedo.push(writableImage);
        }
    }

    /** uses a stack to redo any action the user undoes */
    public void redo(){
        if(! stackRedo.empty()){
            Image x = stackRedo.pop();
            gc.drawImage(x, 0, 0);
        }
    }

    /** Clear function used in menu features file to get rid of all actions performed on the canvas */
    public void clear(){gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Captures the current state of a Canvas by taking a snapshot and adds it to an Undo stack.
     * This method is designed to work with JavaFX Canvas and a stack of Image objects to support undo functionality.
     * @param stack the Undo stack where the snapshots of the Canvas state will be stored.
     * @param canvas the Canvas whose current state needs to be captured and added to the Undo stack.
     */
    public void addUndo(Stack<Image> stack, Canvas canvas){
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);      // captures the current state of the canvas by taking a snapshot of it
        stack.push(writableImage);      // puts the writable image on a stack to save the current canvas state so that it can be restored later if needed
    }

    /**
     * Draws my shape to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawMyShape(double x1, double y1, double x2, double y2){        // drawing my shape
        double x = (x1 < x2 ? x1 : x2);
        double y = (y1 < y2 ? y1 : y2);
        double w = Math.abs(x1-x2);
        double h = Math.abs(y1-y2);
        gc.strokeRoundRect(x, y, w, h, 20, 20);
        gc.fillRoundRect(x, y, w, h, 20, 20);
    }

    /**
     * Draws a rectangle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawRectangle(double x1, double y1, double x2, double y2){      // drawing a rectangle
        double x = (x1 < x2 ? x1 : x2);
        double y = (y1 < y2 ? y1 : y2);
        double w = Math.abs(x1-x2);
        double h = Math.abs(y1-y2);
        gc.strokeRect(x, y, w, h);
        gc.fillRect(x, y, w, h);
    }

    /**
     * Draws a square to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawSquare(double x1, double y1, double x2, double y2){         // drawing a square
        double x = (x2 >= x1 ? x1 : x2);
        double y = (y2 >= y1 ? y1 : y2);
        double w = Math.abs(x2 >= x1 ? x2-x1 :x1-x2);
        double h = Math.abs(x2 >= x1 ? x2-x1 :x1-x2);
        gc.strokeRect(x, y, w, h);
        gc.fillRect(x, y, w, h);
    }

    /**
     * Draws an ellipse to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawEllipse(double x1, double y1, double x2, double y2) {       // drawing an ellipse
        double x = (x1 < x2 ? x1 : x2);
        double y = (y1 < y2 ? y1 : y2);
        double w = Math.abs(x1-x2);
        double h = Math.abs(y1-y2);
        gc.strokeOval(x, y, w, h);
        gc.fillOval(x, y, w, h);
    }

    /**
     * Draws a circle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawCircle(double x1, double y1, double x2, double y2) {       // drawing a circle
        double x = (x2 >= x1 ? x1 : x2);
        double y = (y2 >= y1 ? y1 : y2);
        double w = Math.abs(x2 >= x1 ? x2-x1 :x1-x2);
        double h = Math.abs(x2 >= x1 ? x2-x1 :x1-x2);
        gc.strokeOval(x, y, w, h);
        gc.fillOval(x, y, w, h);
    }

    /**
     * Draws a triangle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawTriangle(double x1, double y1, double x2, double y2, int s){        // drawing a triangle
        double[] x = new double[s];
        double[] y = new double[s];
        double radius = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double startAngle = Math.atan2(y2 - y1, x2 - x1);
        for (int i = 0; i < s; i++) {
            x[i] = x1 + (radius * Math.cos(((2 * Math.PI * i) / s) + startAngle));
            y[i] = y1 + (radius * Math.sin(((2 * Math.PI * i) / s) + startAngle));
        }
        gc.strokePolygon(x, y, s);
        gc.fillPolygon(x, y, s);
    }

    /**
     * Draws a polygon of s sides to the canvas with a center at x1, y1 and
     * calculates radius with x2, y2.
     * @param x1 x coordinate for center of polygon
     * @param y1 y coordinate for center of polygon
     * @param x2 x2 coordinate for
     * @param y2 y2 coordinate for
     * @param s  s for desired sides on the polygon
     */
    public void drawPolygon(double x1, double y1, double x2, double y2, int s) {        // drawing a polygon
        double[] x = new double[s];
        double[] y = new double[s];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double startAngle = Math.atan2(y2 - y1, x2 - x1);
        for (int i = 0; i < s; i++) {
            x[i] = x1 + (radius * Math.cos(((2 * Math.PI * i) / s) + startAngle));
            y[i] = y1 + (radius * Math.sin(((2 * Math.PI * i) / s) + startAngle));
        }
        gc.strokePolygon(x, y, s);
        gc.fillPolygon(x, y, s);
    }

    /**
     * Draws a pencil line to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 initial x
     * @param y1 initial y
     * @param x2 final x
     * @param y2 final y
     */
    public void drawPencil(double x1, double y1, double x2, double y2) {gc.strokeLine(x1, y1, x2, y2);}   // drawing straight lines

    /**
     * Grabs color on the canvas from the coordinates x1 and y1
     * @param x1 initial x
     * @param y1 initial y
     */
    public void eyeDropper(double x1, double y1){
        WritableImage writer = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null,writer);
        PixelReader pixelReader = writer.getPixelReader();
        Toolbar.colorPicker.setValue(pixelReader.getColor((int) x1, (int) y1));     // changes color of the outline to grabbed color
        Toolbar.colorPicker2.setValue(pixelReader.getColor((int) x1, (int) y1));    // changes color of the fill to grabbed color
    }

    /**
     * This method is used for the setting up the color pickers and line width and type into the toolbar
     * @param colorOutline colorpicker for the outline of shapes and line color
     * @param colorFill colorpickers for the fill of shapes and text
     * @param slider adjust the width of the outline line on shapes and regular lines
     * @param slider2 adjust the line from solid to dashed
     * @param gc graphics context
     */
    private void initDraw(ColorPicker colorOutline, ColorPicker colorFill, Slider slider, Slider slider2, GraphicsContext gc){
        double d = slider2.getValue();              // dashed line spacing
        gc.setStroke(colorOutline.getValue());      // color changer for outline
        gc.setFill(colorFill.getValue());           // color changer for fill
        gc.setLineWidth(slider.getValue());         // change width of lines
        gc.setLineDashes(new double[]{d, d*1.3, d, d*1.3});    // dashed line
        gc.setFont(Font.font(12));               // Font size for text box
    }

    /**
     * Retrieves the Canvas associated with this ShapeMath instance
     * @return canvas object used for drawing within the ShapeMath context
     */
    public Canvas getCanvas() {return canvas;}

    /**
     * Retrieves the graphics context associated with this ShapeMath instance
     * @return gc used for rendering and drawing on the associated Canvas
     */
    public GraphicsContext getGc(){return gc;}
}