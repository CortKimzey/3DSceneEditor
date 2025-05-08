// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Hold the data for a point in 2D

package com.point;

public class Vertex2D
{
    protected float x;
    protected float y;
    protected int x2D, y2D;

    public Vertex2D(float x, float y)
    {
        this.x = x;
        x2D = Math.round(x);
        this.y = y;
        y2D = Math.round(y);
    }

    public void setX(float x)
    {
        this.x = x;
        x2D = Math.round(x);
    }

    public void setY(float y)
    {
        this.y = y;
        y2D = Math.round(y);
    }

    public float x()
    {
        return x;
    }

    public int x2D()
    {
        return x2D;
    }

    public int y2D()
    {
        return y2D;
    }

    public float y()
    {
        return y;
    }

    public static float cross(Vertex2D A, Vertex2D B, Vertex2D C)
    {
        return (((B.x() - A.x()) * (C.y() - A.y())) - ((B.y() - A.y()) * (C.x() - A.x())));
    }

    public static boolean isConvex(Vertex2D A, Vertex2D B, Vertex2D C)
    {
        //Only sends true if its Collinear not CCW or CW
        if (cross(A,B,C) == 0)
            return true;
        else
            return false;
    }

    public static boolean inTriangle(Vertex2D P, Vertex2D A, Vertex2D B, Vertex2D C)
    {
        return (area(A,B,P) >= 0) && (area(B,C,P) >= 0) && (area(C,A,P) >= 0);
    }

    public static float area(Vertex2D A, Vertex2D B, Vertex2D C)
    {
        return (((B.x()-A.x())*(C.y()-A.y()))-((C.x()-A.x())*(B.y()-A.y())));
    }

    public void print()
    {
        System.out.println("X: " + x2D + ", Y: " + y2D);
    }

    public String toString()
    {
        return ("X: " + x + ", Y: " + y);
    }
}
