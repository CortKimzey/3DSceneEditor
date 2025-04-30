// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.editor;

import java.io.IOException;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;

import com.canvas.DimCanvas;

import com.object.Object;

import com.use.BoxElement;

public class Editor extends BoxElement
{
    private DimCanvas d;
    private ArrayList<Object> oList = new ArrayList<Object>(0);

    public Editor(int x, int y, DimCanvas d)
    {
        super(x, y, 300, d.getHeight() - x);
        this.d = d;

        try {
            oList.add(new Object(new java.io.File("monkey.obj")));
        } catch (FileNotFoundException e) {
            System.out.println("No cube file!");
        }
    }

    public ArrayList<Object> getOList()
    {
        return oList;
    }

    public void updateSize()
    {
        setX(d.getWidth() - 300);
        setDim(300, d.getHeight() - loc[1]);
    }

    public void onClick(int x, int y) throws IOException
    {

    }


    public void keyPressed(char in)
    {

    }

    public void onDrag(int x, int y)
    {

    }

    public void paint(Graphics2D g)
    {
        drawBox(g, Color.DARK_GRAY);
    }
}