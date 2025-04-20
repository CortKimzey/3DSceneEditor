// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import java.awt.*;
import java.awt.event.*;

import com.scene.Vert2D;

import com.use.BoxElement;

public class Face2D
{
    private Vert2D[] v = new Vert2D[3];
    private int area;
    private int t,b,l,r;

    public Face2D(Vert2D[] vIn)
    {
        for (int x = 0; x < 3; x++)
            v[x] = vIn[x];
        setArea();
        setTBLR();
    }

    public Face2D(Vert2D a, Vert2D b, Vert2D c)
    {
        v[0] = a;
        v[1] = b;
        v[2] = c;
        setArea();
        setTBLR();
    }

    private void setTBLR()
    {
        t = v[0].y() + 1;
        b = v[0].y();
        l = v[0].x();
        r = v[0].x() + 1;

        for (int x = 1; x < 3; x++)
        {
            if (t < v[x].y())
                t = v[x].y() + 1;
            if (b > v[x].y())
                b = v[x].y();
            if (l > v[x].x())
                l = v[x].x();
            if (r < v[x].x())
                r = v[x].x() + 1;
        }
    }

    private void setArea()
    {
        area = Vert2D.getArea(v[0], v[1], v[2]);
    }

    /**
    public void addFace(double[][] zBuff)
    {
        for (int x = 0; x < 3; x++)
            if (v[x].getZ() < zBuff[v[x].x()][v[x].y()])
                zBuff[v[x].x()][v[x].y()] = v[x].getZ();
    }*/

    public void addFace(double[][] zBuff)
    {
        for (int x = l; x < r; x++)
        {
            for (int y = b; y < t; y++)
            {
                if (inside(x,y))
                {
                    zBuff[x][y] = 0;
                }
            }
        }
    }

    private double depth(double a, double b, double c)
    {
        return ((a*v[0].getZ())+(b*v[1].getZ())+(c*v[2].getZ()));
    }

    public void paint2(Graphics2D g)
    {
        g.drawLine(v[0].x(), v[0].y(), v[1].x(), v[1].y());
        g.drawLine(v[1].x(), v[1].y(), v[2].x(), v[2].y());
        g.drawLine(v[2].x(), v[2].y(), v[0].x(), v[0].y());
    }

    public void paint(Graphics2D g)
    {
        for (int x = 0; x < 3; x++)
            v[x].paint(g);
    }

    public static int getArea(int x, int y, Vert2D B, Vert2D C)
    {
        return (((B.x()-x)*(C.y()-y))-((C.x()-x)*(B.y()-y)));
    }

    public int edge(int x, int y, Vert2D B, Vert2D C)
    {
        return ((x-B.x())*(C.y()-B.y()))-((y-B.y())*(C.x()-B.x()));
    }

    private boolean inside(int x, int y)
    {
        if ((edge(x,y,v[0],v[1])>=0)&&(edge(x,y,v[1],v[2])>=0)&&(edge(x,y,v[2],v[0])>=0))
            return true;
        else
            return false;
    }
}