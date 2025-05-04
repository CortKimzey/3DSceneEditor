// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.editor;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;

import com.object.Object;

import com.use.Input;
import com.use.BoxElement;

public class Transform extends BoxElement
{
    private Input xIn, yIn, zIn;
    private String type;

    public Transform(int width, int height, int x, int y, String type)
    {
        super(x, y, width, height);
        xIn = new Input(130, 16, x + 20, y + 22);
        yIn = new Input(130, 16, x + 20, y + 42);
        zIn = new Input(130, 16, x + 20, y + 62);

        this.type = type + ": ";
    }

    public void updateSize(int x, int y)
    {
        setLoc(x, y);
        xIn.setLoc(x + 75, y + 22);
        yIn.setLoc(x + 75, y + 42);
        zIn.setLoc(x + 75, y + 62);
    }

    public void setInactive()
    {
        active = false;
        xIn.setInactive();
        yIn.setInactive();
        zIn.setInactive();
    }

    public void reset()
    {
        active = false;
        xIn.reset();
        yIn.reset();
        zIn.reset();
    }

    public void keyPressed(char in)
    {
        if (xIn.isActive())
            xIn.keyPressed(in);
        else if (yIn.isActive())
            yIn.keyPressed(in);
        else if (zIn.isActive())
            zIn.keyPressed(in);
    }


    public void onClick(int x, int y)
    {
        active = true;
        if (xIn.isClicked(x,y))
        {
            yIn.setInactive();
            zIn.setInactive();
            xIn.onClick();
        }
        else if (yIn.isClicked(x,y))
        {
            xIn.setInactive();
            zIn.setInactive();
            yIn.onClick();
        }
        else if (zIn.isClicked(x,y))
        {
            xIn.setInactive();
            yIn.setInactive();
            zIn.onClick();
        }
    }

    public float[] getData()
    {
        float[] out = new float[3];
        out[0] = xIn.getData();
        out[1] = yIn.getData();
        out[2] = zIn.getData();
        return out;
    }

    public void paint(Graphics2D g)
    {
        g.setColor(Color.white);
        g.drawString(type, loc[0] + 15, loc[1] + 15);
        g.drawLine(loc[0] + 5, tblr(0) + 5, loc[0] + 5, tblr(1));
        g.drawLine(loc[0] + 5, loc[1] + 10, loc[0] + 10, loc[1] + 10);
        
        g.drawLine(loc[0] + 20, loc[1] + 25, loc[0] + 20, tblr(1) - 5);
        xIn.paint(g);
        g.setColor(Color.white);
        g.drawString("X-axis: ", loc[0] + 30, loc[1] + 35);
        g.drawLine(loc[0] + 20, loc[1] + 30, loc[0] + 25, loc[1] + 30);

        yIn.paint(g);
        g.setColor(Color.white);
        g.drawString("Y-axis: ", loc[0] + 30, loc[1] + 55);
        g.drawLine(loc[0] + 20, loc[1] + 50, loc[0] + 25, loc[1] + 50);

        zIn.paint(g);
        g.setColor(Color.white);
        g.drawString("Z-axis: ", loc[0] + 30, loc[1] + 75);
        g.drawLine(loc[0] + 20, loc[1] + 70, loc[0] + 25, loc[1] + 70);
    }
}