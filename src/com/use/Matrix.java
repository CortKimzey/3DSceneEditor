// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

public class Matrix {
    private double[][] matrix;
    private int row;
    private int col;

    public Matrix(int row, int col)
    {
        matrix = new double[row][col];
        this.row = row;
        this.col = col;
    }

    public Matrix(double[][] input)
    {
        row = input.length;
        col = input[0].length;
        this.matrix = input;
    }

    public Matrix(double[] input)
    {
        row = 1;
        col = input.length;
        this.matrix = new double[1][col];
        this.matrix[0] = input;
    }

    public int[] getDim()
    {
        return new int[]{row, col};
    }

    public boolean setLoc(int row, int col, double data)
    {
        if (row < this.row && col < this.col)
        {
            this.matrix[row][col] = data;
            return true;
        }
        else
            return false;
    }

    public double getLoc(int row, int col)
    {
        if (row < this.row && col < this.col)
        {
            return this.matrix[row][col];
        }
        else
            return 0;
    }

    public static Matrix identity(int size)
    {
        double[][] outData = new double[size][size];
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                outData[x][y] = (x == y)? 1 : 0;

        return new Matrix(outData);
    }

    public static Matrix translation(int[] trans, boolean inv)
    {
        int size = trans.length;
        Matrix output = Matrix.identity(size + 1);

        for (int x = size, y = 0; y < size; y++)
            output.setLoc(x,y, (inv)? -trans[y] : trans[y]);

        return output;
    }

    public static Matrix multiplication(Matrix A, Matrix B)
    {
        int row = A.getDim()[0];
        int col = B.getDim()[1];
        int sum = B.getDim()[0];
        Matrix output = new Matrix(row, col);
        double hold = 0;

        for (int x = 0; x < row; x++)
            for (int y = 0; y < col; y++, hold = 0)
            {
                for (int k = 0; k < sum; k++)
                    hold += A.getLoc(x,k) * B.getLoc(k,y);
                output.setLoc(x,y,hold);
            }

        return output;
    }

    public static Matrix multiplication(Matrix A, Matrix B, Matrix C)
    {
        Matrix AB = Matrix.multiplication(A, B);
        return Matrix.multiplication(AB, C);
    }

    public static Matrix rotate(double rad, char axis)
    {
        //double rad = theta * (Math.PI / 180);
        Matrix output = Matrix.identity(4);

        if (axis == 120)
        {
            output.setLoc(1,1, Math.cos(rad));
            output.setLoc(2,2, Math.cos(rad));
            output.setLoc(1,2, Math.sin(rad));
            output.setLoc(2,1, -Math.sin(rad));
        }
        else if (axis == 121)
        {
            output.setLoc(0,0, Math.cos(rad));
            output.setLoc(2,2, Math.cos(rad));
            output.setLoc(0,2, -Math.sin(rad));
            output.setLoc(2,0, Math.sin(rad));
        }
        else if (axis == 122)
        {
            output.setLoc(0,0, Math.cos(rad));
            output.setLoc(1,1, Math.cos(rad));
            output.setLoc(0,1, -Math.sin(rad));
            output.setLoc(1,0, Math.sin(rad));
        }

        return output;
    }
}