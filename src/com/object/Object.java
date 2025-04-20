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

import java.io.File;
import java.io.FileNotFoundException;

import com.scene.Vert2D;
import com.scene.Face2D;
import com.scene.View;

import com.use.BoxElement;
import com.use.Matrix;

public class Object extends BoxElement
{
    private String name = "Object";
    private Object next = null;
    private int strDepth;

    private ArrayList<Matrix> vList = new ArrayList<Matrix>();
    private ArrayList<Matrix> vtList = new ArrayList<Matrix>();
    private ArrayList<Matrix> vnList = new ArrayList<Matrix>();
    private ArrayList<Face> fList = new ArrayList<Face>();

    public Object(java.io.File objFile) throws FileNotFoundException
    {
        super(100,20,0,0);
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

    public Object(int width, int height, int x, int y, File objFile) throws FileNotFoundException
    {
        super(width, height, x, y);
        strDepth = (height - 10) / 2;
        strDepth += 10;
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

        vList.add(new Matrix(data, true));
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
        String token = line.nextToken();
        int index[] = new int[3];
        Face face = new Face(line.countTokens());

        for (int v = 0; line.hasMoreTokens(); v++)
        {
            token = line.nextToken();
            for (int x = 0, y = 0; x < 3; x++, y++)
            {
                int num = 0;
                while (y < token.length() && token.charAt(y) != '/')
                    num = (num * 10) + (token.charAt(y++) - 48);
                index[x] = num - 1;
            }
            face.setV(index[0], index[1], index[2], v);
        }

        face.setNormal(vList.get(face.getV(0)), vList.get(face.getV(1)), vList.get(face.getV(2)));

        fList.add(face);
    }

    public ArrayList<Face2D> triangulate(ArrayList<Vert2D> v2DList)
    {
        ArrayList<Face2D> output = new ArrayList<Face2D>();

        for (int x = 0, lcv = fList.size(); x < lcv; x++)
        {
            Face f = fList.get(x);
            ArrayList<Integer> indices = new ArrayList<>(0);

            for (int i = 0; i < f.getVNum(); i++)
                indices.add(f.getV(i));

            while (indices.size() > 3)
            {
                boolean earFound = false;
                for (int i = 0; i < indices.size(); i++)
                {
                    int prev = indices.get((i - 1 + indices.size()) % indices.size());
                    int curr = indices.get(i);
                    int next = indices.get((i + 1) % indices.size());

                    Vert2D a = v2DList.get(prev);
                    Vert2D b = v2DList.get(curr);
                    Vert2D c = v2DList.get(next);

                    if (Vert2D.cross(a, b, c) >= 0)
                        continue;

                    boolean isEar = true;
                    for (int j = 0; j < f.getVNum(); j++)
                    {
                        if (f.getV(j) == prev || f.getV(j) == curr || f.getV(j) == next)
                            continue;
                        if (Vert2D.isPointInTriangle(v2DList.get(f.getV(j)), a, b, c))
                        {
                            isEar = false;
                            break;
                        }
                    }

                    if (isEar)
                    {
                        output.add(new Face2D(a,b,c));
                        indices.remove(i);
                        earFound = true;
                        break;
                    }
                }

                if (!earFound)
                {
                    //System.err.println("Warning: Non-simple polygon or bad vertex order");
                    break;
                }
            }

            if (indices.size() == 3)
            {
                output.add(new Face2D(v2DList.get(indices.get(0)), v2DList.get(indices.get(1)), v2DList.get(indices.get(2))));
            }
        }

        return output;
    }

    public ArrayList<Matrix> getVList()
    {
        return vList;
    }

    public ArrayList<Face> getFList()
    {
        return fList;
    }

    public void setName(String name)
    {
        this.name = name;

    }

    public String getName()
    {
        return name;
    }


    public Object getNext()
    {
        return next;
    }

    public void setNext(Object next)
    {
        this.next = next;
    }

    public boolean isNext()
    {
        if (next == null)
            return false;
        else
            return true;
    }

    public void paint(Graphics2D g)
    {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        width = metrics.stringWidth(name) + 8;
        g.setColor(Color.white);
        g.drawString(name, tblr(2) + 4, loc[1] + strDepth);
    }

    public String toString()
    {
        return name;
    }
}