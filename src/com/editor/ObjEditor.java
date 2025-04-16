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

import com.use.Box2D;

public class ObjEditor extends Box2D
{
    private boolean visibility = false;

    private ObjNode object = null;
    private Rotation rot;
    private Transformation trans;
    private Scale scale;

    public ObjEditor(int width, int height, int x, int y)
    {
        super(width, height, x, y);
        rot = new Rotation(280, 80, x + 25, y + 20);
        trans = new Transformation(280, 80, rot.getCNR(3).getX(), rot.getCNR(3).getY());
        scale = new Scale(280, 80, trans.getCNR(3).getX(), trans.getCNR(3).getY());
    }

    public void setSize(int x, int y)
    {
        setLoc(x, y);
        rot.setSize(x + 10, y + 25);
        trans.setSize(rot.getCNR(3).getX(), rot.getCNR(3).getY());
        scale.setSize(trans.getCNR(3).getX(), trans.getCNR(3).getY());
    }

    public void paint(Graphics g)
    {
        drawBox(g, Color.darkGray);

        g.setColor(Color.white);
        g.drawString("Object Transformations: " + object, loc[0] + 10, loc[1] + 20);

        rot.paint(g);
        trans.paint(g);
        scale.paint(g);
    }

    public void onClick(int x, int y)
    {
        if (rot.inBox(x,y))
            rot.isClicked(x,y);
    }

    public void getObj(ObjNode object)
    {
        this.object = object;
    }

    public void setVisible()
    {
        visibility = true;
    }

    public boolean isVisible()
    {
        return visibility;
    }
}