// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.room;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;

import com.canvas.DimCanvas;

import com.editor.Editor;
import com.scene.Scene;

import com.use.BoxElement;

public class Room extends BoxElement
{
    private DimCanvas d;
    private Editor editor;
    private Scene scene;

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
            //scene.setInactive();
            //edit.onClick(x, y);
        }
        else if (scene.isClicked(x,y))
        {
            //editor.setInactive();
            scene.onClick(x,y);
        }
    }


    public void keyPressed(char in)
    {
        scene.keyPressed(in);
    }

    public void onDrag(int x, int y)
    {
      //if (scene.isActive())
        scene.onDrag(x,y);
    }

    public void paint(Graphics2D g)
    {
        editor.paint(g);
        scene.paint(g);
    }
}