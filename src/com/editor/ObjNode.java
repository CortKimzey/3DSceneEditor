// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.editor;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import com.object.Object;
import java.awt.*;
import java.awt.event.*;

import com.object.Object;

import com.use.Box2D;
import com.use.Point2D;

public class ObjNode extends Box2D
{
    private ObjNode next = null;

    private String name = "Object";

    private Object object;

    public ObjNode(int width, int height, int x, int y, Object object)
    {
        super(width, height, x, y);
        this.object = object;
    }

    public ObjNode getNext()
    {
        return next;
    }

    public void setNext(ObjNode next)
    {
        this.next = next;
    }

    public boolean isNext()
    {
        if (next == null)
            return false;
        else
            return true;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void paint(Graphics g)
    {
        g.drawString(name, loc[0], loc[1] + 15);
    }

    public void print()
    {
        System.out.println(name);
    }

    public String toString()
    {
        return name;
    }
}