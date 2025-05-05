// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: Modified to use sliders with text input but apply changes only on button press

package com.editor;

import java.util.ArrayList;
import java.awt.*;

import com.object.Object;
import com.use.BoxElement;
import com.use.Slider;

public class Transform extends BoxElement
{
    private Slider xSlider, ySlider, zSlider;
    private String type;
    private boolean isRotation;
    private int min, max;
    private Object targetObject = null; // Object to transform when apply is pressed

    public Transform(int width, int height, int x, int y, String type)
    {
        super(x, y, width, height);
        this.type = type + ": ";
        
        // Set slider range based on transformation type
        isRotation = type.equals("Rotation");
        min = 0;
        max = isRotation ? 360 : 100; // 0-360 for rotation, 0-100 for others
        
        xSlider = new Slider(130, 16, x + 75, y + 22, min, max);
        ySlider = new Slider(130, 16, x + 75, y + 42, min, max);
        zSlider = new Slider(130, 16, x + 75, y + 62, min, max);
    }
    
    public void setTargetObject(Object obj) {
        this.targetObject = obj;
    }

    public void updateSize(int x, int y)
    {
        setLoc(x, y);
        xSlider.setLoc(x + 75, y + 22);
        ySlider.setLoc(x + 75, y + 42);
        zSlider.setLoc(x + 75, y + 62);
    }

    public void setInactive()
    {
        active = false;
        xSlider.setInactive();
        ySlider.setInactive();
        zSlider.setInactive();
    }

    public void reset()
    {
        active = false;
        xSlider.reset();
        ySlider.reset();
        zSlider.reset();
    }

    public void keyPressed(char in)
    {
        if (xSlider.isTextActive()) {
            xSlider.keyPressed(in);
        } else if (ySlider.isTextActive()) {
            ySlider.keyPressed(in);
        } else if (zSlider.isTextActive()) {
            zSlider.keyPressed(in);
        }
    }

    public void onClick(int x, int y)
    {
        active = true;
        if (xSlider.isClicked(x, y))
        {
            if (!xSlider.isTextActive()) {
                ySlider.setInactive();
                zSlider.setInactive();
                xSlider.onClick();
            } else {
                xSlider.onTextBoxClick();
            }
        }
        else if (ySlider.isClicked(x, y))
        {
            if (!ySlider.isTextActive()) {
                xSlider.setInactive();
                zSlider.setInactive();
                ySlider.onClick();
            } else {
                ySlider.onTextBoxClick();
            }
        }
        else if (zSlider.isClicked(x, y))
        {
            if (!zSlider.isTextActive()) {
                xSlider.setInactive();
                ySlider.setInactive();
                zSlider.onClick();
            } else {
                zSlider.onTextBoxClick();
            }
        }
    }
    
    public void onDrag(int x, int y)
    {
        if (xSlider.isActive() && !xSlider.isTextActive())
            xSlider.onDrag(x);
        else if (ySlider.isActive() && !ySlider.isTextActive())
            ySlider.onDrag(x);
        else if (zSlider.isActive() && !zSlider.isTextActive())
            zSlider.onDrag(x);
        
        // No real-time updates - changes will be applied only when the apply button is pressed
    }

    public float[] getData()
    {
        float[] out = new float[3];
        
        // Convert slider values to appropriate format
        if (isRotation) {
            // For rotation, convert to radians if needed
            out[0] = xSlider.getValue();
            out[1] = ySlider.getValue();
            out[2] = zSlider.getValue();
        } else {
            // For scale and translation, normalize to reasonable values
            float scaleFactor = 0.1f; // Adjust this for scale/translation sensitivity
            out[0] = xSlider.getValue() * scaleFactor;
            out[1] = ySlider.getValue() * scaleFactor;
            out[2] = zSlider.getValue() * scaleFactor;
        }
        
        return out;
    }

    public void paint(Graphics2D g)
    {
        g.setColor(Color.white);
        g.drawString(type, loc[0] + 15, loc[1] + 15);
        g.drawLine(loc[0] + 5, tblr(0) + 5, loc[0] + 5, tblr(1));
        g.drawLine(loc[0] + 5, loc[1] + 10, loc[0] + 10, loc[1] + 10);
        
        g.drawLine(loc[0] + 20, loc[1] + 25, loc[0] + 20, tblr(1) - 5);
        
        // Draw axis labels and sliders
        g.setColor(Color.white);
        g.drawString("X-axis: ", loc[0] + 30, loc[1] + 35);
        g.drawLine(loc[0] + 20, loc[1] + 30, loc[0] + 25, loc[1] + 30);
        xSlider.paint(g);

        g.setColor(Color.white);
        g.drawString("Y-axis: ", loc[0] + 30, loc[1] + 55);
        g.drawLine(loc[0] + 20, loc[1] + 50, loc[0] + 25, loc[1] + 50);
        ySlider.paint(g);

        g.setColor(Color.white);
        g.drawString("Z-axis: ", loc[0] + 30, loc[1] + 75);
        g.drawLine(loc[0] + 20, loc[1] + 70, loc[0] + 25, loc[1] + 70);
        zSlider.paint(g);
    }
}