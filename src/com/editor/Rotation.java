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
import com.editor.ObjNode;

import com.use.Input;
import com.use.Button;
import com.use.Box2D;

public class Rotation extends Box2D
{
    private Input xIn, yIn, zIn;

    public Rotation(int width, int height, int x, int y)
    {
        super(width, height, x, y);
        xIn = new Input(130, 16, x + 20, y + 22);
        yIn = new Input(130, 16, x + 20, y + 42);
        zIn = new Input(130, 16, x + 20, y + 62);
    }

    public void setSize(int x, int y)
    {
        setLoc(x, y);
        xIn.setLoc(x + 75, y + 22);
        yIn.setLoc(x + 75, y + 42);
        zIn.setLoc(x + 75, y + 62);
    }

    public void isClicked(int x, int y)
    {
        if (xIn.inBox(x,y))
            xIn.isClicked();
        else if (yIn.inBox(x,y))
            yIn.isClicked();
        else if (zIn.inBox(x,y))
            zIn.isClicked();
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.drawString("Rotation: ", loc[0] + 15, loc[1] + 15);
        g.drawLine(loc[0] + 5, getTBLR(0) + 5, loc[0] + 5, getTBLR(1));
        g.drawLine(loc[0] + 5, loc[1] + 10, loc[0] + 10, loc[1] + 10);
        
        g.drawLine(loc[0] + 20, loc[1] + 25, loc[0] + 20, getTBLR(1) - 5);
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