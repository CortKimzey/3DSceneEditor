// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import java.awt.*;
import java.awt.event.*;

import com.use.Point2D;

public class Vert2D extends Point2D {
    protected double z;

    public Vert2D(int x, int y, double z_ndc)
    {
        super(x,y);
        this.z = ((z_ndc + 1) / 2);
    }

    public double getZ()
    {
        return z;
    }

    public void paint(Graphics2D g)
    {
        g.fillRect(x, y, 5,5);
    }

    public static int cross(Vert2D A, Vert2D B, Vert2D C)
    {
        return (((B.x()-A.x())*(C.y()-A.y()))-((C.x()-A.x())*(B.y()-A.y())));
    }

    public static boolean isPointInTriangle(Vert2D test, Vert2D a, Vert2D b, Vert2D c)
    {
        return (getArea(a,b,test) >= 0) && (getArea(b,c,test) >= 0) && (getArea(c,a,test) >= 0);
    }

    public static int getArea(Vert2D A, Vert2D B, Vert2D C)
    {
        return (((B.x()-A.x())*(C.y()-A.y()))-((C.x()-A.x())*(B.y()-A.y())));
    }
}