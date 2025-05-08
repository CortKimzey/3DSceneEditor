// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Base face element that holds the data of all n-gon faces defines in the object class

package com.object;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import com.point.Point;
import com.point.Vector;

public class Face
{
    private ArrayList<Point> points = new ArrayList<Point>(0);
    private int vNum;
    private int[][] data;
    private Material mat;
    private int s = 0;

    private int face = 0;

    public Face(int vNum, Material mat, int s)
    {
        this.data = new int[vNum][3];
        this.vNum = vNum;
        this.mat = mat;
        this.s = s;
    }

    public void setFace(int face)
    {
        this.face = face;
    }

    public int getFace()
    {
        return face;
    }

    public void addP(Point newPoint)
    {
        this.points.add(newPoint);
    }

    public int getVNum()
    {
        return this.vNum;
    }

    public int getV(int v)
    {
        return data[v][0];
    }

    public void setV(int v, int vt, int vn, int num)
    {
        this.data[num][0] = v;
        this.data[num][1] = vt;
        this.data[num][2] = vn;
    }

    public Point getP(int index)
    {
        int point = 0;

        for (int lcv = 0; lcv < vNum; lcv++, point++)
            if (data[lcv][0] == index)
                break;
        
        return points.get(point);
    }

    public Material getMat()
    {
        return this.mat;
    }

    public void project()
    {
        Vector n = Vector.normal(points.get(0).v(), points.get(1).v(), points.get(2).v());
        n.abs();

        if (n.x() > n.y() && n.x() > n.z())
            points.forEach( p -> { p.quickProjection(1,2);});
        else if (n.y() > n.z())
            points.forEach( p -> { p.quickProjection(0,2);});
        else
            points.forEach( p -> { p.quickProjection(0,1);});
    }

    public void print()
    {
        for (int x = 0; x < vNum; x++)
            System.out.print("V" + x + ": " + data[x][0] + " ");
        System.out.print("\n");
    }

    public void write(java.io.FileWriter file) throws java.io.IOException
    {
        file.write("usemtl " + mat.getName());
        file.write("\ns " + ((s == 0)? "off" : s));
        file.write("\nf ");
        for (int x = 0; x < vNum; x++)
            file.write(data[x][0] + "/" + data[x][1] + "/" + data[x][2] + " ");
    }
}
