// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Object that takes a .obj file and turns it into useable data for our application

package com.object;

import java.awt.*;
import java.awt.event.*;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import com.point.Matrix;
import com.point.Vertex;
import com.point.Vector;
import com.point.Point;

import com.use.Button;

public class Object extends Button
{
    private ArrayList<Material> mtlList = new ArrayList<Material>(0);
    private ArrayList<Vertex> vList = new ArrayList<Vertex>(0);
    private ArrayList<Matrix> vtList = new ArrayList<Matrix>(0);
    private ArrayList<Vector> vnList = new ArrayList<Vector>(0);
    private ArrayList<Face> fList = new ArrayList<Face>(0);
    private ArrayList<Triangle> tList = new ArrayList<Triangle>(0);

    private float[] trans = new float[]{0,0,0};

    public Object(File objFile) throws FileNotFoundException
    {
        super(0,0,150,20, "Object");

        Scanner file = new Scanner(objFile);
        int material = 0;
        int sGroup = 0;

        while (file.hasNextLine())
        {
            String line = file.nextLine();
            if (line.startsWith("mtllib "))
                mtl(new StringTokenizer(line), objFile.getAbsolutePath());
            else if (line.startsWith("o "))
                name(new StringTokenizer(line));
            else if (line.startsWith("v "))
                setV(new StringTokenizer(line));
            else if (line.startsWith("vt "))
                setVT(new StringTokenizer(line));
            else if (line.startsWith("vn "))
                setVN(new StringTokenizer(line));
            else if (line.startsWith("usemtl "))
                material = setMTL(new StringTokenizer(line));
            else if (line.startsWith("s "))
                sGroup = setSGroup(new StringTokenizer(line));
            else if (line.startsWith("f "))
                setF(new StringTokenizer(line), material, sGroup);
        }

        file.close();

        triangulate();
    }

    private void mtl(StringTokenizer line, String path)
    {
        line.nextToken();
        
        int x = path.length();
        while (--x > 0)
            if (path.charAt(x) == 47 || path.charAt(x) == 92)
                break;
        
        try {
            Scanner mtlFile = new Scanner(new File(path.substring(0,++x) + line.nextToken()));
            String mtlLine;
            int index = 0;
            StringTokenizer test;

            while (mtlFile.hasNextLine())
            {
                mtlLine = mtlFile.nextLine();
                test = new StringTokenizer(mtlLine);
                if (test.hasMoreTokens())
                    test.nextToken();

                if (mtlLine.startsWith("newmtl "))
                {
                    String name = "";
                    while (line.hasMoreTokens())
                    {
                        name += line.nextToken();
                        if (line.hasMoreTokens())
                            name += " ";
                    }
                    mtlList.add(new Material(name));
                    index = mtlList.size() - 1;
                }
                else if (mtlLine.startsWith("Ns "))
                    mtlList.get(index).setNs(Float.parseFloat(test.nextToken()));
                else if (mtlLine.startsWith("Ka "))
                {
                    float[] inputKa = new float[3];
                    for (int lcv = 0; lcv < 3; lcv++)
                        inputKa[lcv] = Float.parseFloat(test.nextToken());
                    
                    mtlList.get(index).setKa(inputKa);
                }
                else if (mtlLine.startsWith("Kd "))
                {
                    float[] inputKd = new float[3];
                    for (int lcv = 0; lcv < 3; lcv++)
                        inputKd[lcv] = Float.parseFloat(test.nextToken());
                    mtlList.get(index).setKd(inputKd);
                }
                else if (mtlLine.startsWith("Ks "))
                {
                    float[] inputKs = new float[3];
                    for (int lcv = 0; lcv < 3; lcv++)
                        inputKs[lcv] = Float.parseFloat(test.nextToken());
                    mtlList.get(index).setKs(inputKs);
                }
                else if (mtlLine.startsWith("d "))
                    mtlList.get(index).setD(Float.parseFloat(test.nextToken()));
                else if (mtlLine.startsWith("illum "))
                    mtlList.get(index).setIllum(Integer.parseInt(test.nextToken()));
                else if (mtlLine.startsWith("map_Kd "))
                    mtlList.get(index).setText(path.substring(0,x) + test.nextToken());
                else if (mtlLine.startsWith("map_Bump "))
                    mtlList.get(index).setText(path.substring(0,x) + test.nextToken());
                else if (mtlLine.startsWith("Ni "))
                    mtlList.get(index).setNi();
            }
            
            mtlFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find .mtl file for inputed .obj file!");
        }
    }

    private int setMTL(StringTokenizer line)
    {
        line.nextToken();
        String matName = "";
        while (line.hasMoreTokens())
        {
            matName += line.nextToken();
            if (line.hasMoreTokens())
                matName += " ";
        }
        
        int output = 0;
        for (int lcv = mtlList.size(); output < lcv; output++)
            if (mtlList.get(output).getName() == matName)
                break;
        
        return output - 1;
    }

    private int setSGroup(StringTokenizer line)
    {
        line.nextToken();
        String hold = line.nextToken();
        if (hold.equals("off"))
            return 0;
        else
            return Integer.parseInt(hold);
    }

    private void name(StringTokenizer line)
    {
        String test = "";
        line.nextToken();
        while (line.hasMoreTokens())
        {
            test += line.nextToken();
            if (line.hasMoreTokens())
                test += " ";
        }
        this.text = test;
    }

    private void setV(StringTokenizer line)
    {
        float[] data = new float[]{0,0,0,1};
        line.nextToken();
        for (int x = 0; x < 4 && line.hasMoreTokens(); x++)
            data[x] = Float.parseFloat(line.nextToken());

        vList.add(new Vertex(data));
    }

    private void setVT(StringTokenizer line)
    {
        float[] data = new float[]{0,0,0};
        line.nextToken();
        for (int x = 0; x < 3 && line.hasMoreTokens(); x++)
            data[x] = Float.parseFloat(line.nextToken());

        vtList.add(new Matrix(data, 3, 1));
    }

    private void setVN(StringTokenizer line)
    {
        float[] data = new float[]{0,0,0};
        line.nextToken();
        for (int x = 0; x < 3 && line.hasMoreTokens(); x++)
            data[x] = Float.parseFloat(line.nextToken());

        vnList.add(new Vector(data[0], data[1], data[2]));
    }

    private void setF(StringTokenizer line, int material, int sGroup)
    {
        String token = line.nextToken();
        int index[] = new int[3];
        Face face = new Face(line.countTokens(), mtlList.get(material), sGroup);

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
            face.addP(new Point(vList.get(index[0]), vtList.get(index[1]), vnList.get(index[2])));
        }

        fList.add(face);
    }

    private void triangulate()
    {

        for (int x = 0, lcv = fList.size(); x < lcv; x++)
        {
            Face f = fList.get(x);
            f.project();

            ArrayList<Integer> indices = new ArrayList<>(0);
            for (int i = 0; i < f.getVNum(); i++)
            {
                indices.add(f.getV(i));
            }

            while (indices.size() > 3)
            {
                boolean earFound = false;
                for (int i = 0; i < indices.size(); i++)
                {
                    int prev = indices.get((i - 1 + indices.size()) % indices.size());
                    int curr = indices.get(i);
                    int next = indices.get((i + 1) % indices.size());

                    Point a = f.getP(prev);
                    Point b = f.getP(curr);
                    Point c = f.getP(next);

                    if (Point.isConvex(a, b, c))
                        continue;

                    boolean isEar = true;
                    for (int j = 0; j < f.getVNum(); j++)
                    {
                        if (f.getV(j) == prev || f.getV(j) == curr || f.getV(j) == next)
                            continue;

                        if (Point.inTriangle(f.getP(indices.get(j)), a, b, c))
                        {
                            isEar = false;
                            break;
                        }
                    }

                    if (isEar)
                    {
                        tList.add(new Triangle(a,b,c, f.getMat(), f.getFace()));
                        indices.remove(i);
                        earFound = true;
                        break;
                    }
                }

                if (!earFound)
                {
                    System.err.println("Warning: Non-simple polygon or bad vertex order");
                    break;
                }
            }

            if (indices.size() == 3)
                tList.add(new Triangle(f.getP(indices.get(0)), f.getP(indices.get(1)), f.getP(indices.get(2)), f.getMat(), f.getFace()));
        }
    }

    public boolean inView()
    {
        return true;
    }

    public ArrayList<Vertex> getVList()
    {
        return vList;
    }

    public void write(java.io.FileWriter file) throws java.io.IOException
    {
        file.write("o " + this.text);
        
        mtlList.forEach(mtl -> {
            try {
                mtl.write(file);
            } catch (java.io.IOException e){
                //throw new java.io.IOException("Error in vt");
            }
        });

        vList.forEach(v -> { 
            try {
                file.write("v " + v.write());
            } catch (java.io.IOException e){
                //throw new java.io.IOException("Error in v");
            }
        });

        vtList.forEach(vt -> {
            try {
                file.write("vt " + vt.write());
            } catch (java.io.IOException e){
                //throw new java.io.IOException("Error in vt");
            }
        });

        vnList.forEach(vn -> {
            try {
                file.write("vn " + vn.write());
            } catch (java.io.IOException e){
                //throw new java.io.IOException("Error in vt");
            }
        });

        fList.forEach(f -> {
            try {
                f.write(file);
            } catch (java.io.IOException e){
                //throw new java.io.IOException("Error in vt");
            }
        });
    }

    public void manipulate(Matrix man)
    {
        vList.forEach(v -> {
            v.manipulate(man);
        });
    }

    public void updateTrans(float[] trans)
    {
        this.trans[0] += trans[0];
        this.trans[1] += trans[1];
        this.trans[2] += trans[2];
    }

    public float[] getTrans()
    {
        System.out.println("Trans: " + trans[0] + " " + trans[1] + " " + trans[2]);
        return this.trans;
    }

    public ArrayList<Triangle> getTList()
    {
        return tList;
    }
}
