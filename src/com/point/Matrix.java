// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

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

    //Getting the Data in the Matrix
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

    //Setting the Data in the Matrix
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

    //Arithmatic Operations
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

    //Method for 4x1 Matrix
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

        // Initialize augmented matrix [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i * (2 * n) + j] = data[i * n + j]; // Copy original matrix
                augmented[i * (2 * n) + (j + n)] = (i == j) ? 1f : 0f; // Identity matrix
            }
        }

        // Forward elimination
        for (int i = 0; i < n; i++) {
            // Find the pivot
            float pivot = augmented[i * (2 * n) + i];
            if (Math.abs(pivot) < 1e-6) {
                System.out.println("Matrix is singular and cannot be inverted.");
                return false;
            }

            // Normalize the pivot row
            for (int j = 0; j < 2 * n; j++) {
                augmented[i * (2 * n) + j] /= pivot;
            }

            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                float factor = augmented[k * (2 * n) + i];
                for (int j = 0; j < 2 * n; j++) {
                    augmented[k * (2 * n) + j] -= factor * augmented[i * (2 * n) + j];
                }
            }
        }

        // Copy back the right half (inverse) into the data array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i * n + j] = augmented[i * (2 * n) + (j + n)];
            }
        }

        return true;
    }

    //Static Methods for producing a new Matrix
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


    //Output Data
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