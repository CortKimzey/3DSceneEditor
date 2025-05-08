// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Scene that takes a list of 3D objects and paints them on a 2D plane use a zBuffer

package com.scene;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import com.canvas.DimCanvas;

import com.editor.Editor;

import com.use.BoxElement;

public class Scene extends BoxElement
{
    private DimCanvas d;
    private Editor editor;
    private Projection projMat;
    private View viewMat = new View();

    private float[][] zBuffer;
    private Color[][] cBuffer;
    private Color background = new Color(200,200,225);
    private int buffW, buffH;

    private int paintType = 2;

    private Axis axis = new Axis();

    public Scene(int x, int y, DimCanvas d, Editor editor)
    {
        super(x, y, editor.getX(), d.getHeight() - y);
        this.d = d;
        projMat = new Projection(width, height);
        this.editor = editor;

        zBuffer = new float[this.width][this.height];
        cBuffer = new Color[this.width][this.height];
        buffW = this.width;
        buffH = this.height;
    }

    public void updateSize()
    {
        setDim(editor.getX(), d.getHeight() - loc[1]);
        projMat.updateSize(width, height);

        if (zBuffer == null || buffW < this.width || buffH < this.height)
        {
            zBuffer = new float[this.width][this.height];
            cBuffer = new Color[this.width][this.height];
            buffW = this.width;
            buffH = this.height;
        }

        editor.getOList().forEach( obj -> {
            obj.getTList().forEach( t -> {
                t.setTBLR(this.width, this.height);
            });
        });
    }

    public void keyPressed(char in)
    {
        switch (in)
        {
            case 87:
                viewMat.moveForward();
                break;
            case 65:
                viewMat.moveLeft();
                break;
            case 83:
                viewMat.moveBack();
                break;
            case 68:
                viewMat.moveRight();
                break;
            case 32:
                viewMat.moveUp();
                break;
            case 14:
                viewMat.moveDown();
                break;
            case 82:
                viewMat.restView();
                break;
            case 48:
                paintType = 0;
                break;
            case 49:
                paintType = 1;
                break;
            case 50:
                paintType = 2;
                break;
            case 51:
                paintType = 3;
                break;
            case 52:
                paintType = 4;
                break;
            case 53:
                paintType = 5;
                break;
            case 54:
                paintType = 6;
                break;
            case 55:
                paintType = 7;
                break;
        }
    }

    public void resetBuffer()
    {
        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.height; y++)
            {
                zBuffer[x][y] = Float.POSITIVE_INFINITY;
                cBuffer[x][y] = background;
            }
        }
    }

    public void paintBuffer(Graphics2D g)
    {
        for (int x = 0, locX = loc[0]; x < this.width; x++, locX++)
        {
            for (int y = 0, locY = loc[1] + 1; y < this.height; y++, locY++)
            {
                g.setColor(cBuffer[x][y]);
                g.drawLine(locX, locY, locX, locY);
            }
        }
    }

    public void onClick(int x, int y)
    {
        active = true;
        viewMat.setXY(x,y);
    }

    public void onDrag(int x, int y)
    {
      viewMat.onDrag(x,y);
    }

    private void grayScale()
    {
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;

        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.height; y++)
            {
                if (zBuffer[x][y] < Float.POSITIVE_INFINITY)
                {
                    if (zBuffer[x][y] < min)
                        min = zBuffer[x][y];
                    if (zBuffer[x][y] > max)
                        max = zBuffer[x][y];
                }
            }
        }

        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.height; y++)
            {
                if (zBuffer[x][y] < Float.POSITIVE_INFINITY)
                {
                    int gray = (int)(255 * (zBuffer[x][y] - min) / (max - min));
                    gray = Math.max(0, Math.min(255, gray)); // clamp
                    cBuffer[x][y] = new Color(gray, gray, gray);
                }
            }
        }
    }

    public void paint(Graphics2D g)
    {
        resetBuffer();

        axis.transform2D(width, height, projMat, viewMat);

        editor.getOList().forEach( obj -> {
            if (obj.inView())
            {
                obj.getVList().forEach( v -> { v.transform2D(width, height, projMat, viewMat);});
                switch (paintType)
                {
                    case 0:
                        break;
                    case 1:
                        obj.getTList().forEach( t -> { t.paintOutline(zBuffer, cBuffer);});
                        break;
                    case 2:
                        obj.getTList().forEach( t -> { if (viewMat.isFacing(t.getN(), t.getC())){t.paintOutline(zBuffer, cBuffer);}});
                        break;
                    case 3:
                        obj.getTList().forEach( t -> { t.setTBLR(this.width, this.height);if (viewMat.isFacing(t.getN(), t.getC())){
                                t.paint(zBuffer);
                            }});
                        grayScale();
                        break;
                    case 4:
                        obj.getTList().forEach( t -> { t.setTBLR(this.width, this.height);if (viewMat.isFacing(t.getN(), t.getC())){
                                t.paintAmbient(zBuffer, cBuffer);
                            }});
                        break;
                    case 5:
                        obj.getTList().forEach( t -> { t.setTBLR(this.width, this.height);if (viewMat.isFacing(t.getN(), t.getC())){
                                t.paintDiffuse(zBuffer, cBuffer);
                            }});
                        break;
                    case 6:
                        obj.getTList().forEach( t -> { t.setTBLR(this.width, this.height);if (viewMat.isFacing(t.getN(), t.getC())){
                                t.paintSpecular(zBuffer, cBuffer, viewMat.getEye());
                            }});
                        break;
                    case 7:
                        obj.getTList().forEach( t -> { t.setTBLR(this.width, this.height);
                                t.paint(zBuffer, cBuffer, viewMat.getEye());
                            });
                        break;
                }
            }
        });

        axis.paint(zBuffer, cBuffer);
        paintBuffer(g);
        drawOutline(g);
    }
}
