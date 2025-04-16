// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.object;

import java.awt.*;
import java.awt.event.*;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import com.use.Matrix;

public class Object
{
    private ArrayList<Matrix> vList = new ArrayList<Matrix>();
    private ArrayList<Matrix> vtList = new ArrayList<Matrix>();
    private ArrayList<Matrix> vnList = new ArrayList<Matrix>();
    private ArrayList<FaceList> fList = new ArrayList<FaceList>();

    private String name = "Object";

    public Object(java.io.File objFile) throws FileNotFoundException
    {
        Scanner file = new Scanner(objFile);

        while (file.hasNextLine())
        {
            String line = file.nextLine();
            if (line.startsWith("v "))
                setV(new StringTokenizer(line));
            else if (line.startsWith("vt "))
                setVT(new StringTokenizer(line));
            else if (line.startsWith("vn "))
                setVN(new StringTokenizer(line));
            else if (line.startsWith("f "))
                setF(new StringTokenizer(line));
        }
    }

    private void setV(StringTokenizer line)
    {
        double[] data = new double[]{0,0,0,1};
        line.nextToken();
        for (int x = 0; x < 4 && line.hasMoreTokens(); x++)
            data[x] = Double.parseDouble(line.nextToken());

        vList.add(new Matrix(data));
    }

    private void setVT(StringTokenizer line)
    {
        double[] data = new double[]{0,0,0};
        line.nextToken();
        for (int x = 0; x < 3 && line.hasMoreTokens(); x++)
            data[x] = Double.parseDouble(line.nextToken());

        vtList.add(new Matrix(data));
    }

    private void setVN(StringTokenizer line)
    {
        double[] data = new double[]{0,0,0};
        line.nextToken();
        for (int x = 0; x < 3 && line.hasMoreTokens(); x++)
            data[x] = Double.parseDouble(line.nextToken());

        vnList.add(new Matrix(data));
    }

    private void setF(StringTokenizer line)
    {
        FaceList newFace = new FaceList();
        String face = line.nextToken();
        int index[] = new int[3];

        while (line.hasMoreTokens())
        {
            face = line.nextToken();
            for (int x = 0, y = 0; x < 3; x++, y++)
            {
                int num = 0;
                while (y < face.length() && face.charAt(y) != '/')
                    num = (num * 10) + (face.charAt(y++) - 48);
                index[x] = num - 1;
            }
            newFace.append(new Face(vList.get(index[0]), vtList.get(index[1]), vnList.get(index[2])));
        }

        fList.add(newFace);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}