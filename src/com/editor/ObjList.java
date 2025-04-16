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
import com.use.Point2D;

public class ObjList extends Box2D
{
    private ObjNode root = null;
    private ObjNode tail = null;
    private int size = 0;

    private ObjEditor edit;

    public ObjList(int width, int height, int x, int y)
    {
        super(300, 300, x, y);

        edit = new ObjEditor(300, height - 300, cnr[3].getX(), cnr[3].getY());
    }

    public void setSize(int x, int y)
    {
        setLoc(x, y);

        edit.setSize(cnr[3].getX(), cnr[3].getY());

        if (root != null)
        {
            root.setLoc(loc[0] + 25, loc[1] + 25);
            ObjNode hold = root;
            int z = 45;
            while (hold.isNext())
            {
                hold = hold.getNext();
                hold.setLoc(loc[0] + 25, loc[1] + z);
                z += 20;
            }
        }
    }

    public void onClick(int x, int y)
    {
        if (edit.inBox(x, y))
            edit.onClick(x,y);
        else
        {
            ObjNode hold = root;
            for (int lcv = 0; lcv < size; lcv++)
            {
                if (hold.inBox(x, y))
                {
                    edit.getObj(hold);
                    edit.setVisible();
                }
                hold = hold.getNext();
            }
        }
    }

    public void paint(Graphics g)
    {
        drawBox(g, Color.darkGray);

        g.setColor(Color.white);
        g.drawString("Object Select", loc[0] + 10, loc[1] + 20);

        ObjNode hold = root;
        for (int x = 0, y = 0; x < size; x++, y += 20)
        {
            hold.paint(g);
            hold = hold.getNext();
            g.drawLine(loc[0] + 15, loc[1] + 30 + y, loc[0] + 15, loc[1] + 40 + y);
            if (x > 0)
                g.drawLine(loc[0] + 15, loc[1] + 20 + y, loc[0] + 15, loc[1] + 30 + y);
            g.drawLine(loc[0] + 15, loc[1] + 35 + y, loc[0] + 20, loc[1] + 35 + y);
        }

        if (edit.isVisible())
            edit.paint(g);
    }

    public void append(Object data)
    {
        if (root == null)
        {
            root = new ObjNode(50, 20, loc[0] + 25, loc[1] + 35, data);
            root.setName("Object " + ++size);
            tail = root;
            return;
        }
        ObjNode last = root;
        int y = 55;
        while (last.isNext())
        {
            last = last.getNext();
            y += 20;
        }
        tail = new ObjNode(50, 20, loc[0] + 25, loc[1] + y, data);
        tail.setName("Object " + ++size);
        last.setNext(tail);
    }

    public ObjNode getRoot()
    {
        return root;
    }

    public ObjNode getTail()
    {
        return tail;
    }
}