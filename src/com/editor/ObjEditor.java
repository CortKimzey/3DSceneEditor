// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Implements the Object Editor Menu visually and sets up the "Apply" buttton to activate the inputted transformations.

package com.editor;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;

import com.point.Matrix;
import com.object.Object;
import com.canvas.DimCanvas;
import com.use.BoxElement;
import com.use.Button;

public class ObjEditor extends BoxElement
{
    private DimCanvas d;
    private Object object = null;
    private Transform rot;
    private Transform trans;
    private Transform scale;
    private Button apply;

    public ObjEditor(int x, int y, DimCanvas d)
    {
        super(x, y, 300, d.getHeight() - y);
        this.d = d;
        rot = new Transform(280, 80, x + 25, y + 20, "Rotation");
        trans = new Transform(280, 80, rot.getCNR(3).getX(), rot.getCNR(3).getY(), "Translation");
        scale = new Transform(280, 80, trans.getCNR(3).getX(), trans.getCNR(3).getY(), "Scale");
        apply = new Button(scale.getCNR(3).getX() + 15, scale.getCNR(3).getY() + 5, 47,20, "APPLY");
    }

    public void setObject(Object obj)
    {
        this.object = obj;
    }

    public void updateSize(int x, int y)
    {
        setLoc(x, y);
        setH(d.getHeight() - y);

        rot.updateSize(x + 10, y + 25);
        trans.updateSize(rot.getCNR(3).getX(), rot.getCNR(3).getY());
        scale.updateSize(trans.getCNR(3).getX(), trans.getCNR(3).getY());
        apply.setLoc(scale.getCNR(3).getX() + 15, scale.getCNR(3).getY() + 5);
    }

    @Override
    public void setInactive()
    {
        rot.setInactive();
        trans.setInactive();
        scale.setInactive();
        active = false;
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
        else if (apply.isClicked(x,y))
        {
            applyTrans();
            rot.reset();
            trans.reset();
            scale.reset();
        }
    }

    private void applyTrans()
    {
        float[] r = rot.getData();
        if (r[0] != 0)
            object.manipulate(Matrix.rotate(r[0],'x'));
        if (r[1] != 0)
            object.manipulate(Matrix.rotate(r[1],'y'));
        if (r[2] != 0)
            object.manipulate(Matrix.rotate(r[2],'z'));


        float[] t = trans.getData();
        object.manipulate(Matrix.translation(t,false));
        object.updateTrans(t);

        //object.manipulate(Matrix.scale(scale.getData()));
        object.manipulate(Matrix.mult(Matrix.translation(object.getTrans(),true), Matrix.scale(scale.getData()), Matrix.translation(object.getTrans(),false)));
    }

    public void paint(Graphics2D g)
    {
        drawBox(g,Color.DARK_GRAY);

        g.setColor(Color.white);
        g.drawString("Object Transformations: " + object.getName(), loc[0] + 10, loc[1] + 20);

        rot.paint(g);
        trans.paint(g);
        scale.paint(g);

        apply.drawButton(g, Color.WHITE);
    }
}
