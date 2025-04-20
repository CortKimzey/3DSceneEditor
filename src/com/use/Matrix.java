// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.use;

import java.awt.*;
import java.awt.event.*;

public class Matrix {
    protected double[][] matrix;
    protected int row;
    protected int col;

    public Matrix(int row, int col)
    {
        matrix = new double[row][col];
        for (int x = 0; x < row; x++)
            for (int y = 0; y < col; y++)
                matrix[x][y] = 0;
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

    public Matrix(double[] input, boolean test)
    {
        row = input.length;
        col = 1;
        this.matrix = new double[row][col];
        for (int x = 0; x < row; x++)
            matrix[x][0] = input[x];
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

    public double getLoc(int row)
    {
        if (row < this.row)
            return this.matrix[row][0];
        else
            return 0;
    }

    public static Matrix subtraction(Matrix A, Matrix B)
    {
        int[] dimA = A.getDim();
        int[] dimB = B.getDim();
        if (dimA[0] != dimB[0] || dimA[1] != dimB[1])
        {
            return null;
        }
        else
        {
            Matrix output = new Matrix(dimA[0], dimA[1]);
            for (int row = 0; row < dimA[0]; row++)
                for (int col = 0; col < dimA[1]; col++)
                    output.setLoc(row,col,A.getLoc(row,col)-B.getLoc(row,col));
            return output;
        }  
    }

    public static Matrix identity(int size)
    {
        double[][] outData = new double[size][size];
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                outData[x][y] = (x == y)? 1 : 0;

        return new Matrix(outData);
    }

    public static Matrix translation(double[] trans, boolean inv)
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

    public static Matrix rotate(double theta, char axis)
    {
        double rad = theta * (Math.PI / 180);
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

    public static double magnitude(Matrix A)
    {
        double sum = Math.pow(A.getLoc(0), 2);
        sum += Math.pow(A.getLoc(1), 2);
        sum += Math.pow(A.getLoc(2), 2);
        return Math.sqrt(sum);
    }

    public static Matrix sum(Matrix A, Matrix B)
    {
        int[] dim = A.getDim();
        Matrix out = new Matrix(dim[0], dim[1]);
        for (int x = 0; x < dim[0]; x++)
            for (int y = 0; y < dim[1]; y++)
                out.setLoc(x,y, A.getLoc(x,y) + B.getLoc(x,y));
        return out;
    }

    public static Matrix normalize(Matrix A)
    {
        Matrix output = new Matrix(3,1);
        double mag = magnitude(A);
        for (int x = 0; x < 3; x++)
            output.setLoc(x,0, A.getLoc(x)/mag);
        return output;
    }

    public static Matrix multVale(Matrix A, double val)
    {
        int[] dim = A.getDim();
        Matrix out = new Matrix(dim[0],dim[1]);
        for (int x = 0; x < dim[0]; x++)
            for (int y = 0; y < dim[1]; y++)
                out.setLoc(x,y, A.getLoc(x,y) * val);
        return out;
    }

    public static double dot(Matrix A, Matrix B)
    {
        double sum = 0;
        for (int x = 0; x < 3; x++)
            sum += (A.getLoc(x) * B.getLoc(x));
        return sum;
    }

    public static Matrix cross(Matrix A, Matrix B)
    {
        Matrix output = new Matrix(3,1);
        output.setLoc(0,0,((A.getLoc(1)*B.getLoc(2))-(A.getLoc(2)*B.getLoc(1))));
        output.setLoc(1,0,(A.getLoc(2)*B.getLoc(0))-(A.getLoc(0)*B.getLoc(2)));
        output.setLoc(2,0,(A.getLoc(0)*B.getLoc(1))-(A.getLoc(1)*B.getLoc(0)));
        return output;
    }

    public static Matrix negative(Matrix A)
    {
        int[] dim = A.getDim();
        Matrix output = new Matrix(dim[0],dim[1]);
        for (int row = 0; row < dim[0]; row++)
            for (int col = 0; col < dim[1]; col++)
                output.setLoc(row,col,((-1) * A.getLoc(row,col)));
        return output;
    }

    public static Matrix perpDiv(Matrix A)
    {
        Matrix output = new Matrix(3,1);
        double w = A.getLoc(3);
        for (int x = 0; x < 3; x++)
            output.setLoc(x,0, (A.getLoc(x)/w));
        return output;
    }

    public static Matrix invertMatrix(Matrix A)
    {
        int[] dims = A.getDim();
        int n = dims[0];
        int m = dims[1];

        Matrix augmented = new Matrix(n, 2 * n);

        // Create augmented matrix [A | I]
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                augmented.setLoc(row, col, A.getLoc(row, col));
            }
            for (int col = 0; col < n; col++) {
                augmented.setLoc(row, col + n, (row == col) ? 1.0 : 0.0);
            }
        }

        // Perform Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Find pivot
            double pivot = augmented.getLoc(i, i);
            if (Math.abs(pivot) < 1e-10) {
                throw new IllegalArgumentException("Matrix is singular and cannot be inverted.");
            }

            // Normalize pivot row
            for (int j = 0; j < 2 * n; j++) {
                augmented.setLoc(i, j, augmented.getLoc(i, j) / pivot);
            }

            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k == i) continue;
                double factor = augmented.getLoc(k, i);
                for (int j = 0; j < 2 * n; j++) {
                    double value = augmented.getLoc(k, j) - factor * augmented.getLoc(i, j);
                    augmented.setLoc(k, j, value);
                }
            }
        }

        // Extract inverse from augmented matrix
        Matrix inverse = new Matrix(n, n);
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                inverse.setLoc(row, col, augmented.getLoc(row, col + n));
            }
        }

        return inverse;
    }

    public void printMatrix()
    {
        for(int x = 0; x < row; x++)
        {
            System.out.print("[");
            for (int y = 0; y < col; y++)
                System.out.print(matrix[x][y] + ", ");
            System.out.print("]\n");
        }
    }
}