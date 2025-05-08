// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Point class that hold all the data for each end point of a face

package com.point;

import java.awt.*;
import java.awt.event.*;

import com.point.Vertex;
import com.point.Matrix;
import com.point.Vertex2D;

public class Point
{
    private Vertex v;
    private Matrix vt;
    private Vector vn;

    public Point(Vertex v, Matrix vt, Vector vn)
    {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
    }

    public Vertex v()
    {
        return this.v;
    }

    public Matrix vt()
    {
        return this.vt;
    }

    public Vector vn()
    {
        return this.vn;
    }

    public static boolean isConvex(Point A, Point B, Point C)
    {
        return Vertex2D.isConvex(A.v().get2D(), B.v().get2D(), C.v().get2D());
    }

    public static boolean inTriangle(Point P, Point A, Point B, Point C)
    {
        return Vertex2D.inTriangle(P.v().get2D(), A.v().get2D(), B.v().get2D(), C.v().get2D());
    }

    public void quickProjection(int x, int y)
    {
        v.quickProjection(x,y);
    }

    public void vOut()
    {
        System.out.print(v.getVNum());
    }

    public void print()
    {
        v.print();
        v.get2D().print();
    }
}
