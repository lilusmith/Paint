package com.example.paint1;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 * This class file is used to manage the features used to draw on the canvas or image
 * and the features in the edit tab of the menu bar.
 * Includes the mouse interaction movements and calling each draw function button.
 **/

public class ShapeMath extends Canvas {
    private static final int CANVAS_WIDTH = 1000;
    private static final int CANVAS_HEIGHT = 800;
    private static Canvas canvas;
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
    Image image1;

    public ShapeMath(Tab tab){

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setCursor(Cursor.CROSSHAIR);
        sp = new ScrollPane(canvas);
        tab.setContent(sp);

        canvas.setOnMouseMoved(e-> {
            mouseLocation.add(e.getX(), e.getY());
        });

        canvas.setOnMousePressed(e -> {
            initDraw(Toolbar.getColorPicker(), Toolbar.getColorPicker2(), Toolbar.getSlider(), gc,
                    Toolbar.getSlider2());
            startX = e.getX();
            startY = e.getY();
            System.out.println(tab.getId());

            if(Toolbar.getDrawButton().isSelected()){       // free hand draw start
                gc.beginPath();
                gc.moveTo(startX, startY);
                gc.stroke();
                addUndo(stackUndo, canvas);
            }

            if(Toolbar.getDrawButton9().isSelected()){      // free hand eraser tool start
                gc.beginPath();
                gc.moveTo(startX, startY);
                gc.setStroke(Color.WHITE);
                addUndo(stackUndo, canvas);
            }

            // rectangle
            if(Toolbar.getDrawButton2().isSelected()){addUndo(stackUndo, canvas);}

            // square
            if(Toolbar.getDrawButton3().isSelected()){addUndo(stackUndo, canvas);}

            // ellipse
            if(Toolbar.getDrawButton4().isSelected()){addUndo(stackUndo, canvas);}

            // circle
            if(Toolbar.getDrawButton5().isSelected()){addUndo(stackUndo, canvas);}

            // pencil
            if(Toolbar.getDrawButton6().isSelected()){addUndo(stackUndo, canvas);}

            // triangle
            if(Toolbar.getDrawButton7().isSelected()){addUndo(stackUndo, canvas);}

            // my shape
            if(Toolbar.getDrawButton8().isSelected()){addUndo(stackUndo, canvas);}

            // text
            if(Toolbar.getDrawButton10().isSelected()){addUndo(stackUndo, canvas);}

            // polygon
            if(Toolbar.getDrawButton11().isSelected()){addUndo(stackUndo, canvas);}

            // selection
            if(Toolbar.getDrawButton12().isSelected()){addUndo(stackUndo, canvas);}
        });


        canvas.setOnMouseDragged(e -> {
            double endX = e.getX();
            double endY = e.getY();
            if(App.move.isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                gc.drawImage(imageSelection, endX, endY);
            }

            if(Toolbar.getDrawButton().isSelected()){       // free hand draw end
                gc.lineTo(endX, endY);
                gc.stroke();
            }
            if(Toolbar.getDrawButton2().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawRectangle(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton3().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawSquare(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton4().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawEllipse(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton5().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawCircle(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton6().isSelected()){         // pencil
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawPencil(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton7().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawTriangle(startX, startY, endX,endY, 3);
            }
            if(Toolbar.getDrawButton8().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawMyShape(startX, startY, endX,endY);
            }
            if(Toolbar.getDrawButton9().isSelected()){      // free hand eraser end
                gc.lineTo(endX, endY);
                gc.stroke();
            }
            if(Toolbar.getDrawButton10().isSelected()){     // text
                gc.lineTo(endX, endY);
                gc.stroke();
            }
            if(Toolbar.getDrawButton11().isSelected()){
                Image x = (Image)stackUndo.peek();
                gc.drawImage(x,0,0);
                this.drawPolygon(startX, startY, endX,endY, 5);
            }
        });

        canvas.setOnMouseReleased(e-> {
            endX = e.getX();
            endY = e.getY();

            if(Toolbar.getDrawButton2().isSelected()){           // calls rectangle
                this.drawRectangle(startX, startY, endX,endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton3().isSelected()){           // calls square
                this.drawSquare(startX, startY, endX,endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton4().isSelected()){           // calls ellipse
               this.drawEllipse(startX, startY, endX,endY);
               stackRedo.clear();
            }
            if(Toolbar.getDrawButton5().isSelected()){           // calls circle
                this.drawCircle(startX, startY, endX,endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton6().isSelected()){          // calls pencil
                this.drawPencil(startX, startY, endX,endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton7().isSelected()){          // calls triangle
                this.drawTriangle(startX, startY, endX,endY, 3);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton8().isSelected()){          // calls my shape
                this.drawMyShape(startX, startY, endX,endY);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton11().isSelected()){          // calls polygon
                this.drawPolygon(startX, startY, endX,endY, 5);
                stackRedo.clear();
            }
            if(Toolbar.getDrawButton12().isSelected()){          // selection button for copy, paste, and cut
                double x = (startX < endX ? startX : endX);
                double y = (startY < endY ? startY : endY);
                double w = Math.abs(endX-startX);
                double h = Math.abs(endY-startY);
                System.out.println(w + " " + h);
                image = canvas.snapshot(null, null);
                bimage = SwingFXUtils.fromFXImage(image,null);
                bimage2 = new BufferedImage((int)w, (int)h, BufferedImage.OPAQUE);
                bimage2.createGraphics().drawImage(bimage.getSubimage((int) x, (int) y, (int) w, (int) h), 0, 0, null);
                imageSelection = SwingFXUtils.toFXImage(bimage2,null);
                stackRedo.clear();
            }
        });

        canvas.setOnMouseClicked(e-> {
            if(Toolbar.getDrawButton10().isSelected()){
                String s = Toolbar.getTextField().getText();
                gc.fillText(s, startX, startY);
                stackRedo.clear();
            }
            else if(App.paste.isSelected()){
                gc.drawImage(imageSelection, e.getX(), e.getY());
            }
            if(Toolbar.getEyeDropper().isSelected()){
                this.eyeDropper(startX, startY);
                stackRedo.clear();
            }
        });
    }


    // cut function is used to cut out parts of the drawing on the canvas or image that is selected
    public void cut(){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        double x = (startX < endX ? startX : endX);
        double y = (startY < endY ? startY : endY);
        double w = Math.abs(endX-startX);
        double h = Math.abs(endY-startY);
        System.out.println(w + " " + h);
        image = canvas.snapshot(null, null);
        bimage = SwingFXUtils.fromFXImage(image,null);
        bimage2 = new BufferedImage((int)w, (int)h, BufferedImage.OPAQUE);
        bimage2.createGraphics().drawImage(bimage.getSubimage((int) x, (int) y, (int) w, (int) h), 0, 0, null);
        imageSelection = SwingFXUtils.toFXImage(bimage2,null);
        gc.strokeRect(startX, startY, endX, endY);
        gc.fillRect(startX, startY, endX, endY);
    }

    // copies part of the drawing or image selected
    public void copy(){
        double x = (startX < endX ? startX : endX);
        double y = (startY < endY ? startY : endY);
        double w = Math.abs(endX-startX);
        double h = Math.abs(endY-startY);
        System.out.println(w + " " + h);
        image = canvas.snapshot(null, null);
        bimage = SwingFXUtils.fromFXImage(image,null);
        bimage2 = new BufferedImage((int)w, (int)h, BufferedImage.OPAQUE);
        bimage2.createGraphics().drawImage(bimage.getSubimage((int) x, (int) y, (int) w, (int) h), 0, 0, null);
        imageSelection = SwingFXUtils.toFXImage(bimage2,null);
    }

    // uses a stack to undo any actions performed
    public void undo(){
        if(! stackUndo.empty()){
            WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(null, writableImage);
            Image x = stackUndo.pop();
            gc.drawImage(x, 0, 0);
            stackRedo.push(writableImage);
        }
    }

    // uses a stack to redo any action the user undoes
    public void redo(){
        if(! stackRedo.empty()){
            Image x = stackRedo.pop();
            gc.drawImage(x, 0, 0);
        }
    }

    // clear function used in menu features file to get rid of all actions performed on the canvas
    public void clear(){
        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
    }

    public void addUndo(Stack<Image> stack, Canvas canvas){
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        stack.push(writableImage);
    }

    /**
     * Draws my shape to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
     */
    public void drawMyShape(double x1, double y1, double x2, double y2){        // my shape
        double x = (x1 < x2 ? x1 : x2);
        double y = (y1 < y2 ? y1 : y2);
        double w = Math.abs(x1-x2);
        double h = Math.abs(y1-y2);
        gc.strokeRoundRect(x, y, w, h, 20, 20);
        gc.fillRoundRect(x, y, w, h, 20, 20);
    }

    /**
     * Draws a rectangle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
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
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
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
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
     */
    public void drawEllipse(double x1, double y1, double x2, double y2) {       // drawing a ellipse
        double x = (x1 < x2 ? x1 : x2);
        double y = (y1 < y2 ? y1 : y2);
        double w = Math.abs(x1-x2);
        double h = Math.abs(y1-y2);
        gc.strokeOval(x, y, w, h);
        gc.fillOval(x, y, w, h);
    }

    /**
     * Draws a circle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
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
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
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
     * @param x1 - x coordinate for center of polygon
     * @param y1 - y coordinate for center of polygon
     * @param x2 - x2 coordinate for
     * @param y2 - y2 coordinate for
     * @param s  - s for desired sides on the polygon
     */
    public void drawPolygon(double x1, double y1, double x2, double y2, int s) {        // draws a polygon
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
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
     */
    public void drawPencil(double x1, double y1, double x2, double y2) {gc.strokeLine(x1, y1, x2, y2);}   // drawing pencil

    public void eyeDropper(double x1, double y1){
        WritableImage writer = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null,writer);
        PixelReader pixelReader = writer.getPixelReader();
        Toolbar.colorPicker.setValue(pixelReader.getColor((int) x1, (int) y1));
        Toolbar.colorPicker2.setValue(pixelReader.getColor((int) x1, (int) y1));
    }

    private void initDraw(ColorPicker colorOutline, ColorPicker colorFill, Slider slider, GraphicsContext gc, Slider slider2){
        double d = slider2.getValue();
        gc.setStroke(colorOutline.getValue());      // color changer for outline
        gc.setFill(colorFill.getValue());           // color changer for fill
        gc.setLineWidth(slider.getValue());         // change width of lines
        gc.setLineDashes(new double[]{d, d*1.3, d, d*1.3});    // dashed line
        gc.setFont(Font.font(12));               // Font size for text box
    }

    public static Canvas getCanvas() {return canvas;}

    public GraphicsContext getGc(){
        return gc;
    }
}