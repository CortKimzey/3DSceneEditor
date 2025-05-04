// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.point;

import java.awt.*;
import java.awt.event.*;

public class Vector extends Matrix
{
    public Vector(float x, float y, float z)
    {
        super(new float[]{x,y,z}, 3, 1);
    }

    public Vector(float[] in)
    {
        super(new float[]{in[0],in[1],in[2]}, 3, 1);
    }

    public Vector()
    {
        super(3,1);
    }

    public float x()
    {
        return data[0];
    }

    public float y()
    {
        return data[1];
    }

    public float z()
    {
        return data[2];
    }

    public void setXYZ(float x, float y, float z)
    {
        data[0] = x;
        data[1] = y;
        data[2] = z;
    }

    //Vector Operations
    public float mag()
    {
        return (float)(Math.sqrt(pow(data[0]) + pow(data[1]) + pow(data[2])));
    }

    private float pow(float dataIn)
    {
        return (dataIn * dataIn);
    }

    public float dot(Vector A)
    {
        return ((data[0] * A.get(0)) + (data[1] * A.get(1)) + (data[2] * A.get(2)));
    }

    public static Vector cross(Vector A, Vector B)
    {
        return new Vector(
            ((A.get(1) * B.get(2)) - (A.get(2) * B.get(1))),
            ((A.get(2) * B.get(0)) - (A.get(0) * B.get(2))),
            ((A.get(0) * B.get(1)) - (A.get(1) * B.get(0))));
    }

    public static Vector sub(Vector A, Vector B)
    {
        int row = A.row();
        int col = A.col();
        if (row != B.row() || col != B.col())
        {
            return null;
        }
        else
        {
            Vector output = new Vector();
            for (int lcv = 0; lcv < 3; lcv++)
                output.set(lcv, A.get(lcv) - B.get(lcv));
            return output;
        }  
    }

    public static Vector neg(Vector A)
    {
        return new Vector((-1*A.x()),(-1*A.y()),(-1*A.z()));
    }

    /**
    public static Vector reflect(Vector A, Vector B)
    {
        float dot = B.dot(A);
        return new Vector(((2*dot*A.x())-B.x()),((2*dot*A.y())-B.y()),((2*dot*A.z())-B.z()));
    }
    */

    public static Vector reflect(Vector A, Vector B)
    {
        return sub(A, (mult(A.mult(B).mult(B),2f)));
    }

    public static Vector sum(Vector A, Vector B)
    {
        int row = A.row();
        int col = A.col();
        if (row != B.row() || col != B.col())
        {
            return null;
        }
        else
        {
            Vector output = new Vector();
            for (int lcv = 0; lcv < 3; lcv++)
                output.set(lcv, A.get(lcv) + B.get(lcv));
            return output;
        }  
    }

    public static Vector sum(Vector A, Vector B, Vector C)
    {
        return sum(sum(A,B),C);
    }

    public static Vector normal(Vector A, Vector B, Vector C)
    {
        Vector output = cross(sub(B,A), sub(C,A));
        output.normalize();
        return output;
    }

    public static Vector center(Vector A, Vector B, Vector C)
    {
        return new Vector(
            ((A.x() + B.x() + C.x()) / 3),
            ((A.y() + B.y() + C.y()) / 3),
            ((A.z() + B.z() + C.z()) / 3));
    }

    public static Vector barMult(Matrix A, int x, int y)
    {
        if (A.row() == 3 && A.col() == 3)
        {
            return new Vector(
                ((A.get(0) * x) + (A.get(1) * y) + A.get(2)),
                ((A.get(3) * x) + (A.get(4) * y) + A.get(5)),
                ((A.get(6) * x) + (A.get(7) * y) + A.get(8))
            );
        }
        else
            return null;
    }

    public static Vector mult(Vector A, float m)
    {
        return new Vector(A.x() * m, A.y() * m, A.z() * m);
    }

    public Vector mult(Vector A)
    {
        return new Vector((data[0] * A.get(0)), (data[1] * A.get(1)), (data[2] * A.get(2)));
    }

    public void normalize()
    {
        float mag = mag();
        for (int x = 0; x < 3; x++)
            data[x] /= mag;
    }

    public String write()
    {
        return data[0] + " " + data[1] + " " + data[2];
    }

    public void clamp()
    {
        for (int x = 0; x < 3; x++)
            if (data[x] > 1)
                data[x] = 1;
    }
}