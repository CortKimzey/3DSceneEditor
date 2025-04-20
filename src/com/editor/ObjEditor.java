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

import com.use.Matrix;
import com.use.BoxElement;
import com.use.Button;

public class ObjEditor extends BoxElement
{
    private Object object = null;
    private Transform rot;
    private Transform trans;
    private Transform scale;
    private Button apply;

    public ObjEditor(int width, int height, int x, int y)
    {
        super(width, height, x, y);
        rot = new Transform(280, 80, x + 25, y + 20, "Rotation");
        trans = new Transform(280, 80, rot.getCNR(3).getX(), rot.getCNR(3).getY(), "Translation");
        scale = new Transform(280, 80, trans.getCNR(3).getX(), trans.getCNR(3).getY(), "Scale");
        apply = new Button(47,20, scale.getCNR(3).getX() + 15, scale.getCNR(3).getY() + 5, "APPLY");
    }

    public void updateSize(int x, int y)
    {
        setLoc(x, y);
        rot.updateSize(x + 10, y + 25);
        trans.updateSize(rot.getCNR(3).getX(), rot.getCNR(3).getY());
        scale.updateSize(trans.getCNR(3).getX(), trans.getCNR(3).getY());

        apply.setLoc(scale.getCNR(3).getX() + 15, scale.getCNR(3).getY() + 5);
    }

    private void applyEdit()
    {
        //Temp
        double[] hold = rot.getData();
        Matrix rotation = Matrix.multiplication(Matrix.rotate(hold[0], 'x'), Matrix.rotate(hold[1], 'y'), Matrix.rotate(hold[2], 'z'));
        Matrix translation = Matrix.translation(trans.getData(), false);
        //Matrix scaling = Matrix
        //Matrix model = Matrix.multiplication()
    }

    public void paint(Graphics2D g)
    {
        drawBox(g, Color.darkGray);

        g.setColor(Color.white);
        g.drawString("Object Transformations: " + object, loc[0] + 10, loc[1] + 20);

        //rot.drawBox(g, Color.RED);
        rot.paint(g);
        //trans.drawBox(g, Color.RED);
        trans.paint(g);
        //scale.drawBox(g, Color.RED);
        scale.paint(g);

        apply.paint(g, Color.WHITE);
    }

    public void getObj(Object object)
    {
        this.object = object;
    }

    public void keyPressed(char in)
    {
        if (rot.isActive())
            rot.keyPressed(in);
        else if (trans.isActive())
            trans.keyPressed(in);
        else if (scale.isActive())
            scale.keyPressed(in);
    }

    public void setInactive()
    {
        active = false;
        rot.setInactive();
        trans.setInactive();
        scale.setInactive();
    }

    public void onClick(int x, int y)
    {
        active = true;
        if (rot.isClicked(x,y))
        {
            trans.setInactive();
            scale.setInactive();
            rot.onClick(x,y);
        }
        else if (trans.isClicked(x,y))
        {
            rot.setInactive();
            scale.setInactive();
            trans.onClick(x,y);
        }
        else if (scale.isClicked(x,y))
        {
            rot.setInactive();
            trans.setInactive();
            scale.onClick(x,y);
        }
    }
}