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
    private ObjList objList;
    private ObjEditor objEditor;

    public Editor(int x, int y, DimCanvas d)
    {
        super(x, y, 300, d.getHeight() - x);
        this.d = d;

        try {
            oList.add(new Object(new java.io.File("cube.obj")));
        } catch (FileNotFoundException e) {
            System.out.println("No cube file!");
        }

        objList = new ObjList(x,y,oList,d);
        objEditor = new ObjEditor(objList.getBLX(),objList.getBLY(),d);
        objEditor.setObject(oList.get(0));
        objList.setObjEditor(objEditor);
    }

    public ArrayList<Object> getOList()
    {
        return oList;
    }

    public void updateSize()
    {
        setX(d.getWidth() - 300);
        setDim(300, d.getHeight() - loc[1]);
        objList.updateSize(loc[0],loc[1]);
        objEditor.updateSize(objList.getBLX(),objList.getBLY());
    }

    public void onClick(int x, int y)
    {
        active = true;
        if (objList.isClicked(x,y))
            objList.onClick(x,y);
        else if (objEditor.isClicked(x,y))
            objEditor.onClick(x,y);
    }

    public void removeObjectAt(int x, int y) {
        System.out.println("[Editor] Checking for clicked object at: " + x + ", " + y);
        for (int i = 0; i < oList.size(); i++) {
            Object obj = oList.get(i);
            if (obj.isClicked(x, y)) {
                System.out.println("[Editor] Object clicked: " + obj.getName() + ", removing.");
                oList.remove(i);
                if (!oList.isEmpty()) {
                    objEditor.setObject(oList.get(0));
                }
                return;
            }
        }
        System.out.println("[Editor] No object found at click location.");
    }

    @Override
    public void setInactive()
    {
        objList.setInactive();
        objEditor.setInactive();
        active = false;
    }

    public void keyPressed(char in)
    {
        if (objEditor.isActive())
            objEditor.keyPressed(in);
    }

    public void onDrag(int x, int y)
    {

    }

    public void addObject(File file) throws FileNotFoundException
    {
        oList.add(new Object(file));
    }

    public void write(java.io.FileWriter file) throws java.io.IOException
    {
        oList.forEach(o -> {
            try {
                file.write("ObjectStart");
                o.write(file);
                file.write("ObjectEnd");
            } catch (java.io.IOException e){
                //throw new java.io.IOException("Error in vt");
            }
        });
    }

    public void paint(Graphics2D g)
    {
        drawBox(g, Color.DARK_GRAY);
        objList.paint(g);
        objEditor.paint(g);
    }
}