// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: A Room that holds a scene to display the objects and an editor that hold a list of objects and can maniplulate them

package com.room;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;

import com.canvas.DimCanvas;

import java.io.File;
import java.io.FileNotFoundException;

import com.editor.Editor;
import com.scene.Scene;

import com.use.BoxElement;
import com.use.Button;

public class Room extends BoxElement
{
    private DimCanvas d;
    private Editor editor;
    private Scene scene;
    private String roomName = "3D Room";

    public Room(int x, int y, DimCanvas d)
    {
        super(x, y, d.getWidth(), d.getHeight() - y);
        this.d = d;

        editor = new Editor(d.getWidth() - 300, y, d);

        scene = new Scene(x,y, d, editor);
    }

    public void updateSize()
    {
        setDim(d.getWidth(), d.getHeight() - loc[1]);
        editor.updateSize();
        scene.updateSize();
    }

    public void onClick(int x, int y)
    {
        active = true;
        if (editor.isClicked(x,y))
        {
            scene.setInactive();
            editor.onClick(x, y);
        }
        else if (scene.isClicked(x,y))
        {
            editor.setInactive();
            scene.onClick(x,y);
        }
    }

    public void onRightClick(int x, int y) {
        System.out.println("[Room] Right click received at: " + x + ", " + y);

        if (editor.isClicked(x, y)) {
            System.out.println("[Room] Editor was clicked");
            editor.removeObjectAt(x, y);
        } else if (scene.isClicked(x, y)) {
            System.out.println("[Room] Scene was clicked");
        } else {
            System.out.println("[Room] Click was not in scene or editor bounds");
        }
    }

    public void addObject(File file)
    {
        try {
            editor.addObject(file);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Could not find file.");
        }
    }

    public void keyPressed(char in)
    {
        if (scene.isActive())
            scene.keyPressed(in);
        else if (editor.isActive())
            editor.keyPressed(in);
    }

    public void onDrag(int x, int y)
    {
      if (scene.isClicked(x,y))
        scene.onDrag(x,y);
    }

    public void paint(Graphics2D g)
    {
        editor.paint(g);
        scene.paint(g);
    }
}
