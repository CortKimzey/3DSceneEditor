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

import com.use.BoxElement;

public class ObjList extends BoxElement
{
    private Object root = null;
    private Object tail = null;
    private int size = 0;

    public ObjList(int width, int height, int x, int y)
    {
        super(300, 300, x, y);
    }

    public void updateSize(int x, int y)
    {
        setLoc(x, y);

        if (root != null)
        {
            root.setLoc(loc[0] + 25, loc[1] + 25);
            Object hold = root;
            int z = 45;
            while (hold.isNext())
            {
                hold = hold.getNext();
                hold.setLoc(loc[0] + 25, loc[1] + z);
                z += 20;
            }
        }
    }

    public Object giveRoot()
    {
        return root;
    }

    public void paint(Graphics2D g)
    {
        drawBox(g, Color.darkGray);

        g.setColor(Color.white);
        g.drawString("Object Select", loc[0] + 10, loc[1] + 20);

        Object hold = root;
        for (int x = 0, y = 0; x < size; x++, y += 20)
        {
            hold.paint(g);
            hold = hold.getNext();
            g.drawLine(loc[0] + 15, loc[1] + 30 + y, loc[0] + 15, loc[1] + 40 + y);
            if (x > 0)
                g.drawLine(loc[0] + 15, loc[1] + 20 + y, loc[0] + 15, loc[1] + 30 + y);
            g.drawLine(loc[0] + 15, loc[1] + 35 + y, loc[0] + 20, loc[1] + 35 + y);
        }
    }

    public void append(File data) throws FileNotFoundException
    {
        if (root == null)
        {
            root = new Object(50, 20, loc[0] + 25, loc[1] + 35, data);
            root.setName("Object " + ++size);
            tail = root;
            return;
        }
        Object last = root;
        int y = 55;
        while (last.isNext())
        {
            last = last.getNext();
            y += 20;
        }
        tail = new Object(50, 20, loc[0] + 25, loc[1] + y, data);
        tail.setName("Object " + ++size);
        last.setNext(tail);
    }

    public Object getRoot()
    {
        return root;
    }

    public Object getTail()
    {
        return tail;
    }

    public void keyPressed(char in)
    {
        System.out.println("List active");
    }

    public void setInactive()
    {
        active = false;
    }

    public void onClick(int x, int y)
    {
        active = true;
    }
}