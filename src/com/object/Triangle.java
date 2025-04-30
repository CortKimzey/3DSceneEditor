// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.point.Matrix;
import com.point.Point;
import com.point.Vector;
import com.point.Vertex2D;

public class Triangle
{
    //private static int num = 1;

    private Point[] abc = new Point[3];
    private Material mat;

    private float area;
    private Matrix invM = new Matrix(3,3);

    private Edge[] edges = new Edge[3];
    private int t,b,l,r;

    private Vector normal;
    private Vector center;

    public Triangle(Point A, Point B, Point C, Material mat)
    {
        abc[0] = A;
        abc[1] = B;
        abc[2] = C;
        this.mat = mat;

        //System.out.println("Triangle: " + num++);
        //System.out.println("VN 1");
        //abc[0].vn().print();
        //System.out.println("VN 2");
        //abc[1].vn().print();
        //System.out.println("VN 3");
        //abc[2].vn().print();

        setArea();
        //if (!setInvM())
            //System.out.println("Problem");

        edges[0] = new Edge(abc[0].v().get2D(), abc[1].v().get2D());
        edges[1] = new Edge(abc[1].v().get2D(), abc[2].v().get2D());
        edges[2] = new Edge(abc[2].v().get2D(), abc[0].v().get2D());

        normal = Vector.normal(A.v(), B.v(), C.v());
        center = Vector.center(A.v(), B.v(), C.v());

        setTBLR(800, 800);
    }

    public void setTBLR(int width, int height)
    {
        t = abc[0].v().y2D() + 1;
        b = abc[0].v().y2D();
        l = abc[0].v().x2D();
        r = abc[0].v().x2D() + 1;

        for (int x = 1; x < 3; x++)
        {
            if (t < abc[x].v().y2D())
                t = abc[x].v().y2D() + 1;
            if (b > abc[x].v().y2D())
                b = abc[x].v().y2D();
            if (l > abc[x].v().x2D())
                l = abc[x].v().x2D();
            if (r < abc[x].v().x2D())
                r = abc[x].v().x2D() + 1;
        }

        if (b < 0)
            b = 0;
        if (l < 0)
            l=0;
        if (t > height)
            t = height;
        if (r > width)
            r = width;
    }

    private void setArea()
    {
        area = Vertex2D.area(abc[0].v().get2D(), abc[1].v().get2D(), abc[2].v().get2D());
    }

    public boolean setInvM()
    {
        if (area == 0f)
            return false;
        else
        {
            for (int x = 0; x < 3; x++)
            {
                invM.set(x, abc[x].v().get2D().x());
                invM.set(3 + x, abc[x].v().get2D().y());
                invM.set(6 + x, 1f);
            }
            invM.invert();
            return true;
        }
    }

    private float depth(float a, float b, float c)
    {
         return ((a*abc[0].v().z())+(b*abc[1].v().z())+(c*abc[2].v().z()));
    }

    private boolean inTriangle(int x, int y)
    {
        if (edges[0].pixelTest(x,y) > 0 && edges[1].pixelTest(x,y) > 0 && edges[2].pixelTest(x,y) > 0)
            return true;
        else
            return false;
    }

    public float edge(int x, int y, Vertex2D B, Vertex2D C)
    {
        return ((x-B.x2D())*(C.y2D()-B.y2D()))-((y-B.y2D())*(C.x2D()-B.x2D()));
    }

    private boolean inside(int x, int y)
    {
        if ((edge(x,y,abc[0].v().get2D(),abc[1].v().get2D())>=0)&&(edge(x,y,abc[1].v().get2D(),abc[2].v().get2D())>=0)&&(edge(x,y,abc[2].v().get2D(),abc[0].v().get2D())>=0))
            return true;
        else
            return false;
    }

    public Vector getN()
    {
        return normal;
    }

    public Vector getC()
    {
        return center;
    }

    public void paint(float[][] zBuff)
    {
        setInvM();
        for (int x = l; x < r; x++)
        {
            for (int y = b; y < t; y++)
            {
                //System.out.println("X: " + x + ", Y: " + y);
                if (inside(x,y))//Triangle(x,y))
                {
                    //System.out.println("in");
                    Matrix bar = Matrix.mult(invM, new Matrix(new float[]{x,y,1}, 3, 1));
                    //System.out.println((bar.get(0) + bar.get(1) + bar.get(2)));
                    float z = depth(bar.get(0), bar.get(1), bar.get(2));
                    if (z < zBuff[x][y])
                    {
                        zBuff[x][y] = z;
                    }
                }
            }
        }
    }

    public void paint(float[][] zBuff, Color[][] cBuff, Vector eye)
    {
        setInvM();
        for (int x = l; x < r; x++)
        {
            for (int y = b; y < t; y++)
            {
                if (inside(x,y))
                {
                    Vector bar = Vector.barMult(invM, x, y);
                    float z = depth(bar.x(), bar.y(), bar.z());
                    if (z < zBuff[x][y])
                    {
                        zBuff[x][y] = z;

                        Vector ambient = Vector.mult(mat.getKa(), 0.3f);

                        Vector L = Vector.sub(new Vector(1,1,1), surfacePoint(bar));
                        L.normalize();
                        Vector N = Vector.sum(Vector.mult(abc[0].vn(), bar.x()),Vector.mult(abc[1].vn(), bar.y()),Vector.mult(abc[2].vn(), bar.z()));
                        N.normalize();
                        float dot = N.dot(L);
                        if (dot < 0)
                            dot = 0;
                        //System.out.println(dot);
                        Vector diffuse = Vector.mult(mat.getKd(), dot);

                        Vector outColor = Vector.sum(ambient,diffuse);
                        outColor.clamp();

                        cBuff[x][y] = new Color(outColor.x(), outColor.y(), outColor.z());
                    }
                }
            }
        }
    }

    public void paintTest(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.drawLine(abc[0].v().x2D(), abc[0].v().y2D(), abc[0].v().x2D(), abc[0].v().y2D());
        g.drawLine(abc[1].v().x2D(), abc[1].v().y2D(), abc[1].v().x2D(), abc[1].v().y2D());
        g.drawLine(abc[2].v().x2D(), abc[2].v().y2D(), abc[2].v().x2D(), abc[2].v().y2D());
    }

    public void paint(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.drawLine(abc[0].v().x2D(), abc[0].v().y2D(), abc[1].v().x2D(), abc[1].v().y2D());
        g.drawLine(abc[1].v().x2D(), abc[1].v().y2D(), abc[2].v().x2D(), abc[2].v().y2D());
        g.drawLine(abc[2].v().x2D(), abc[2].v().y2D(), abc[0].v().x2D(), abc[0].v().y2D());
    }

    public void paintAmbient(float[][] zBuff, Color[][] cBuff)
    {
        setInvM();
        for (int x = l; x < r; x++)
        {
            for (int y = b; y < t; y++)
            {
                if (inside(x,y))
                {
                    Vector bar = Vector.barMult(invM, x, y);
                    float z = depth(bar.x(), bar.y(), bar.z());
                    if (z < zBuff[x][y])
                    {
                        zBuff[x][y] = z;
                        Vector ambient = Vector.mult(mat.getKa(), 0.3f);
                        cBuff[x][y] = new Color(ambient.x(), ambient.y(), ambient.z());
                    }
                }
            }
        }
    }

    public void paintDiffuse(float[][] zBuff, Color[][] cBuff)
    {
        setInvM();
        for (int x = l; x < r; x++)
        {
            for (int y = b; y < t; y++)
            {
                if (inside(x,y))
                {
                    Vector bar = Vector.barMult(invM, x, y);
                    float z = depth(bar.x(), bar.y(), bar.z());
                    if (z < zBuff[x][y])
                    {
                        zBuff[x][y] = z;
                        Vector L = Vector.sub(new Vector(1,1,1), surfacePoint(bar));
                        L.normalize();
                        Vector N = Vector.sum(Vector.mult(abc[0].vn(), bar.x()),Vector.mult(abc[1].vn(), bar.y()),Vector.mult(abc[2].vn(), bar.z()));
                        N.normalize();
                        float dot = N.dot(L);
                        if (dot < 0)
                            dot = 0;
                        //System.out.println(dot);
                        Vector diffuse = Vector.mult(mat.getKd(), dot);
                        //diffuse.clamp();
                        cBuff[x][y] = new Color(diffuse.x(), diffuse.y(), diffuse.z());
                    }
                }
            }
        }
    }

    public void paintSpecular(float[][] zBuff, Color[][] cBuff, Vector eye)
    {
        setInvM();
        for (int x = l; x < r; x++)
        {
            for (int y = b; y < t; y++)
            {
                if (inside(x,y))
                {
                    Vector bar = Vector.barMult(invM, x, y);
                    float z = depth(bar.x(), bar.y(), bar.z());
                    if (z < zBuff[x][y])
                    {
                        zBuff[x][y] = z;
                        Vector surface = surfacePoint(bar);
                        Vector V = Vector.sub(eye, surface);
                        V.normalize();
                        
                        Vector L = Vector.sub(new Vector(-1,-1,-1), surface);
                        L.normalize();
                        
                        Vector N = Vector.sum(Vector.mult(abc[0].vn(), bar.x()),Vector.mult(abc[1].vn(), bar.y()),Vector.mult(abc[2].vn(), bar.z()));
                        N.normalize();
                        
                        Vector R = Vector.reflect(Vector.neg(L), N);
                        R.normalize();

                        float dot = R.dot(V);
                        if (dot < 0)
                            dot = 0;
                        //System.out.println(dot);
                        //Vector diffuse = Vector.mult(mat.getKd(), dot);
                        Vector specular = Vector.mult(mat.getKs(), (float)Math.pow(dot, mat.getNs()));
                        specular.clamp();
                        cBuff[x][y] = new Color(specular.x(), specular.y(), specular.z());
                    }
                }
            }
        }
    }

    private Vector surfacePoint(Vector bar)
    {
        return Vector.sum(Vector.mult(abc[0].v(), bar.x()),
                        Vector.mult(abc[1].v(), bar.y()),
                        Vector.mult(abc[2].v(), bar.z()));
    }

    public void paintOutline(float[][] zBuff, Color[][] cBuff)
    {
        Line.paint(abc[0].v(), abc[1].v(), zBuff, cBuff);
        Line.paint(abc[1].v(), abc[2].v(), zBuff, cBuff);
        Line.paint(abc[2].v(), abc[0].v(), zBuff, cBuff);
    }
}