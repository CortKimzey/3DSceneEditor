// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Matrix object that creates a matrix and allows for different matrix calculations

package com.point;

import java.awt.*;
import java.awt.event.*;

public class Matrix
{
    protected float[] data;
    protected int row, col;

    public Matrix(float[] data, int row, int col)
    {
        this.data = data;
        this.row = row;
        this.col = col;
    }

    public Matrix(int row, int col)
    {
        this.data = new float[row * col];
        this.row = row;
        this.col = col;
        for (int x = 0, lcv = row * col; x < lcv; x++)
            data[x] = 0f;
    }

    public Matrix(float[][] input)
    {
        row = input.length;
        col = input[0].length;

        data = new float[row * col];

        for (int x = 0, loc = 0; x < this.row; x++)
            for (int y = 0; y < this.col; y++, loc++)
                data[loc] = input[x][y];
    }

    public static Matrix identity(int size)
    {
        float[] outData = new float[size*size];
        for (int x = 0, loc = 0; x < size; x++)
            for (int y = 0; y < size; y++, loc++)
                outData[loc] = (x == y)? 1 : 0;

        return new Matrix(outData, size, size);
    }

    public static Matrix translation(float[] trans, boolean inv)
    {
        Matrix output = Matrix.identity(4);

        for (int x = 0, y = 3; x < 3; x++)
            output.set(x,y, (inv)? ((-1)*trans[x]) : trans[x]);

        return output;
    }

    public static Matrix rotate(float theta, char axis)
    {
        double rad = (double)theta * (Math.PI / 180);
        Matrix output = Matrix.identity(4);

        if (axis == 120)
        {
            output.set(1,1, (float)Math.cos(rad));
            output.set(2,2, (float)Math.cos(rad));
            output.set(1,2, (float)Math.sin(rad));
            output.set(2,1, (float)-Math.sin(rad));
        }
        else if (axis == 121)
        {
            output.set(0,0, (float)Math.cos(rad));
            output.set(2,2, (float)Math.cos(rad));
            output.set(0,2, (float)-Math.sin(rad));
            output.set(2,0, (float)Math.sin(rad));
        }
        else if (axis == 122)
        {
            output.set(0,0, (float)Math.cos(rad));
            output.set(1,1, (float)Math.cos(rad));
            output.set(0,1, (float)-Math.sin(rad));
            output.set(1,0, (float)Math.sin(rad));
        }

        return output;
    }

    public static Matrix scale(float[] scal)
    {
        Matrix output = Matrix.identity(4);

        for (int x = 0, y = 0; x < 3; x++, y++)
            output.set(x,y, (scal[x] == 0)? 1:scal[x]);

        return output;
    }

    public float get(int loc)
    {
        return data[loc];
    }

    public float get(int xLoc, int yLoc)
    {
        return data[(xLoc * col) + yLoc];
    }

    public int row()
    {
        return this.row;
    }

    public int col()
    {
        return this.col;
    }

    public void set(int loc, float dataIn)
    {
        this.data[loc] = dataIn;
    }

    public void set(int xLoc, int yLoc, float dataIn)
    {
        data[(xLoc * col) + yLoc] = dataIn;
    }

    public void set(float[] dataIn)
    {
        for (int x = 0, lcv = (row * col); x < lcv; x++)
            data[x] = dataIn[x];
    }

    public void div(float divisor)
    {
        for (int x = 0, loc = 0; x < this.row; x++)
            for (int y = 0; y < this.col; y++, loc++)
                data[loc] /= divisor;
    }

    public void abs()
    {
        for (int x = 0, lcv = (row * col); x < lcv; x++)
                data[x] = Math.abs(data[x]);
    }

    public void neg()
    {
        for (int x = 0, loc = 0; x < row; x++)
            for (int y = 0; y < col; y++, loc++)
                data[loc] = ((-1) * data[loc]);
    }

    public void interpolate()
    {
        if (row == 4 && col == 1)
        {
            data[0] /= data[3];
            data[1] /= data[3];
            data[2] /= data[3];
        }
    }

    public void mult(float multiplicand)
    {
        for (int x = 0, loc = 0; x < this.row; x++)
            for (int y = 0; y < this.col; y++, loc++)
                data[loc] *= multiplicand;
    }

    public boolean invert()
    {
        if (row != col) {
            System.out.println("Matrix must be square to invert.");
            return false;
        }
        
        int n = row;
        float[] augmented = new float[n * n * 2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i * (2 * n) + j] = data[i * n + j];
                augmented[i * (2 * n) + (j + n)] = (i == j) ? 1f : 0f;
            }
        }

        for (int i = 0; i < n; i++) {
            float pivot = augmented[i * (2 * n) + i];
            if (Math.abs(pivot) < 1e-6) {
                System.out.println("Matrix is singular and cannot be inverted.");
                return false;
            }

            for (int j = 0; j < 2 * n; j++) {
                augmented[i * (2 * n) + j] /= pivot;
            }

            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                float factor = augmented[k * (2 * n) + i];
                for (int j = 0; j < 2 * n; j++) {
                    augmented[k * (2 * n) + j] -= factor * augmented[i * (2 * n) + j];
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i * n + j] = augmented[i * (2 * n) + (j + n)];
            }
        }

        return true;
    }

    public static Matrix sub(Matrix A, Matrix B)
    {
        int row = A.row();
        int col = A.col();
        if (row != B.row() || col != B.col())
            return null;
        else
        {
            Matrix output = new Matrix(row, col);
            for (int x = 0, loc = 0; x < row; x++)
                for (int y = 0; y < col; y++, loc++)
                    output.set(loc, (A.get(loc) - B.get(loc)));
            return output;
        }  
    }

    public static Matrix add(Matrix A, Matrix B)
    {
        int row = A.row();
        int col = A.col();
        if (row != B.row() || col != B.col())
            return null;
        else
        {
            Matrix output = new Matrix(row, col);
            for (int x = 0, loc = 0; x < row; x++)
                for (int y = 0; y < col; y++, loc++)
                    output.set(loc, (A.get(loc) + B.get(loc)));
            return output;
        }  
    }

    public static Matrix mult(Matrix A, Matrix B)
    {
        int n = B.row();
        if (n != A.col())
            return null;
        else
        {
            int row = A.row();
            int col = B.col();
            Matrix output = new Matrix(row, col);
            float hold = 0;
            
            for (int i = 0, loc = 0; i < row; i++)
            {
                for (int j = 0; j < col; j++, loc++, hold = 0)
                {
                    for (int k = 0; k < n; k++)
                        hold += A.get(i,k) * B.get(k,j);
                    output.set(loc, hold);
                }
            }

            return output;
        }
    }

    public static Matrix mult(Matrix A, Matrix B, Matrix C)
    {
        return mult(mult(A,B),C);
    }

    public String write()
    {
        String output = "";
        for (int x = 0, loc = 0; x < row; x++)
            for (int y = 0; y < col; y++, loc++)
                output += data[loc] + " ";
        return output;
    }

    public void print()
    {
        for (int x = 0, loc = 0; x < this.row; x++)
        {
            System.out.print("[");
            for (int y = 0; y < this.col; y++, loc++)
                System.out.print(String.format("%.2f", data[loc]) + (((y + 1) < this.col)? " " : ""));
            System.out.print("]\n");
        }
    }
}
