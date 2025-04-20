// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.use.Matrix;

public class Face
{
    private Matrix normal = new Matrix(3,1);
    private int[][] data;
    private int vNum;

    public Face(int vNum)
    {
        this.data = new int[vNum][3];
        this.vNum = vNum;
    }

    public void setV(int v, int vt, int vn, int num)
    {
        this.data[num][0] = v;
        this.data[num][1] = vt;
        this.data[num][2] = vn;
    }

    public int getVNum()
    {
        return vNum;
    }

    public int getV(int v)
    {
        return data[v][0];
    }

    public int getV0()
    {
        return data[0][0];
    }

    public int getV1()
    {
        return data[1][0];
    }

    public int getV2()
    {
        return data[2][0];
    }

    public void print()
    {
        System.out.println("Face");
        for (int x = 0; x < vNum; x++)
            System.out.println("v" + x + ": " + data[x][0]);
    }

    public void setNormal(Matrix v0, Matrix v1, Matrix v2)
    {
        normal = Matrix.normalize(Matrix.cross(Matrix.subtraction(v1,v0),Matrix.subtraction(v2,v0)));
    }

    public Matrix getNorm()
    {
        return normal;
    }
}