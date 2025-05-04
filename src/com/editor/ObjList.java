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

public class ObjList extends BoxElement
{
    private DimCanvas d;
    private ArrayList<Object> oList;
    private ObjEditor objEditor = null;

    public ObjList(int x, int y, ArrayList<Object> oList, DimCanvas d)
    {
        super(x,y,300,300);
        this.d = d;
        this.oList = oList;
    }

    public void setObjEditor(ObjEditor objEditor)
    {
        this.objEditor = objEditor;
    }

    public void onClick(int x, int y)
    {
        active = true;
        oList.forEach(o -> {
            if (o.isClicked(x,y))
            {
                objEditor.setObject(o);
            }
        });
    }

    public void updateSize(int x, int y)
    {
        setLoc(x, y);

        for (int g = 0, h = 25; g < oList.size(); g++, h += 20)
        {
            oList.get(g).setLoc(loc[0] + 30, loc[1] + h);
        }
    }

    public void paint(Graphics2D g)
    {
        drawBox(g, Color.DARK_GRAY);

        g.setColor(Color.white);
        g.drawString("Object Select", loc[0] + 10, loc[1] + 20);


        for (int x = 0, y = 0; x < oList.size(); x++, y+=20)
        {
            oList.get(x).drawText(g, Color.WHITE);
            g.drawLine(loc[0] + 15, loc[1] + 30 + y, loc[0] + 15, loc[1] + 40 + y);
            if (x > 0)
                g.drawLine(loc[0] + 15, loc[1] + 20 + y, loc[0] + 15, loc[1] + 30 + y);
            g.drawLine(loc[0] + 15, loc[1] + 35 + y, loc[0] + 20, loc[1] + 35 + y);
        }
    }
}