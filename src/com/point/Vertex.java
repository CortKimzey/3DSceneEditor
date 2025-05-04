// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.point;

import java.awt.*;
import java.awt.event.*;

import com.scene.Projection;
import com.scene.View;

import com.point.Matrix;

public class Vertex extends Vector
{
    private Matrix point = new Matrix(4,1);
    private Vertex2D xy = new Vertex2D(0f,0f);
    private float z;

    private Matrix ClipPosition = new Matrix(4,1);
    private int width, height;

    private int vNum = 0;

    public Vertex(float x, float y, float z, float w)
    {
        super(x/w, y/w, z/w);
        point.set(new float[]{x,y,z,w});
    }

    public Vertex(float[] in)
    {
        super(in[0]/in[3], in[1]/in[3], in[2]/in[3]);
        point.set(in);
    }

    public void setVNum(int vNum)
    {
        this.vNum = vNum;
    }

    public int getVNum()
    {
        return vNum;
    }

    public void set(float x, float y, float z, float w)
    {
        data[0] = x/w;
        point.set(0, x);
        data[1] = y/w;
        point.set(1, y);
        data[2] = z/w;
        point.set(2, z);
        point.set(3, w);
    }

    private void interp()
    {
        for (int x = 0; x < 3; x++)
            data[x] = point.get(x) / point.get(3);
    }

    public void transform2D(int width, int height, Projection projMat, View viewMat)
    {
        this.width = width;
        this.height = height;
        //System.out.println("Vert");
        ClipPosition = Matrix.mult(projMat, viewMat, point);
        //ClipPosition.print();
        xy.setX(((ClipPosition.get(0) / ClipPosition.get(3)) + 1) * 0.5f * width);   // X coordinate
        xy.setY((1 - (ClipPosition.get(1) / ClipPosition.get(3))) * 0.5f * height);  // Y coordinate
        z = (ClipPosition.get(2) / ClipPosition.get(3));
        z = ((z+1)/2);
    }

    public byte clipTransform(int width, int height, Projection projMat, View viewMat)
    {
        this.width = width;
        this.height = height;
        ClipPosition = Matrix.mult(projMat, viewMat, point);

        return getRC();
    }

    public void setLineZ()
    {
        z = ((ClipPosition.get(2) / ClipPosition.get(3)) / (1 / ClipPosition.get(3)));
    }

    public Matrix getCP()
    {
        return ClipPosition;
    }

    public void setCP(Matrix newCP)
    {
        ClipPosition = newCP;
    }

    public byte getRC()
    {
        byte rc = 0b00000000;

        if (ClipPosition.get(2) < ((-1)*ClipPosition.get(3)))
            rc = (byte)(rc | 0b00010000);
        else if (ClipPosition.get(2) > ClipPosition.get(3))
            rc = (byte)(rc | 0b00100000);

        if (ClipPosition.get(1) < ((-1)*ClipPosition.get(3)))
            rc = (byte)(rc | 0b00000100);
        else if (ClipPosition.get(1) > ClipPosition.get(3))
            rc = (byte)(rc | 0b00001000);

        if (ClipPosition.get(0) < ((-1)*ClipPosition.get(3)))
            rc = (byte)(rc | 0b00000001);
        else if (ClipPosition.get(0) > ClipPosition.get(3))
            rc = (byte)(rc | 0b00000010);

        return rc;
    }

    public void set2D()
    {
        xy.setX(((ClipPosition.get(0) / ClipPosition.get(3)) + 1) * 0.5f * width);   // X coordinate
        xy.setY((1 - (ClipPosition.get(1) / ClipPosition.get(3))) * 0.5f * height);  // Y coordinate
        z = ((ClipPosition.get(2) / ClipPosition.get(3)) + 1) / 2;
    }

    public void quickProjection(int x, int y)
    {
        xy.setX(data[x]);
        xy.setY(data[y]);
    }

    public void manipulate(Matrix manip)
    {
        point = Matrix.mult(manip, point);
        interp();
    }

    //Getting 2D Data
    public Vertex2D get2D()
    {
        return xy;
    }

    public int x2D()
    {
        return Math.round(xy.x());
    }

    public int y2D()
    {
        return Math.round(xy.y());
    }

    public float z()
    {
        return z;
    }

    public void set2D(float x, float y)
    {
        xy.setX(x);
        xy.setY(y);
    }

}