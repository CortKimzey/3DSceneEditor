// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: 3D line that are clipped and projected to 2D then drawn using Bresenham Line Algorithm

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.scene.Projection;
import com.scene.View;

import com.point.Vertex;
import com.point.Matrix;

public class Line
{
    private boolean visable = true;
    Vertex[] ends = new Vertex[2];
    Color color = Color.BLACK;

    public Line()
    {
        ends[0] = new Vertex(0,0,0,1);
        ends[1] = new Vertex(1,1,1,1);
    }


    public void setV1(float x, float y, float z)
    {
        ends[0].set(x,y,z,1f);
    }

    public void setV2(float x, float y, float z)
    {
        ends[1].set(x,y,z,1f);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void transform2D(int width, int height, Projection projMat, View viewMat)
    {
        byte p0 = ends[0].clipTransform(width, height, projMat, viewMat);
        byte p1 = ends[1].clipTransform(width, height, projMat, viewMat);

        if (p0 == 0 && p1 == 0)
            visable = true;
        else if ((p0 & p1) > 0)
            visable = false;
        else
        {
            if (p0 > 0)
                lineClipping(p0, 0, 0);
            
            if (p1 > 0)
                lineClipping(p1, 1, 0);
            visable = true;
        }

        ends[0].set2D();
        ends[1].set2D();
    }

    private void lineClipping(byte rc, int v, int depth)
    {
        if ((rc & 0b00000001) > 0)
            newPoint(v,0, true);
        else if ((rc & 0b00000010) > 0)
            newPoint(v,0, false);
        else if ((rc & 0b00000100) > 0)
        {
            newPoint(v,1, true);
        }
        else if ((rc & 0b00001000) > 0)
            newPoint(v,1, false);
        else if ((rc & 0b00010000) > 0)
            newPoint(v,2, true);
        else if ((rc & 0b00100000) > 0)
            newPoint(v,2, false);

        if (ends[v].getRC() > 0 && depth < 6)
            lineClipping(ends[v].getRC(), v, ++depth);
    }

    private void newPoint(int v, int p, boolean neg)
    {
        Matrix p0 = ends[v].getCP();
        Matrix p1 = ends[(v == 0)? 1:0].getCP();

        float t = 0;
        if (neg)
        {
            t = (-1)*(p0.get(3) + p0.get(p));
            t /= (p1.get(p)-p0.get(p))+(p1.get(3)-p0.get(3));
        }
        else
        {
            t = (p0.get(3) - p0.get(p));
            t /= (p1.get(p)-p0.get(p))-(p1.get(3)-p0.get(3));
        }

        Matrix hold = Matrix.sub(p1,p0);
        hold.mult(t);
        hold = Matrix.add(p0,hold);
        ends[v].setCP(hold);
    }

   public void paint(float[][] zBuff, Color[][] cBuff)
   {
        if (visable)
        {
            float[] z = new float[2];
            int[] xy1;
            int[] xy2;
            boolean inv;
            if (ends[0].x2D() == ends[1].x2D()) {
                if (ends[0].y2D() < ends[1].y2D()) {
                    xy1 = new int[]{ends[0].x2D(), ends[0].y2D()};
                    z[0] = ends[0].z();
                    xy2 = new int[]{ends[1].x2D(), ends[1].y2D()};
                    z[1] = ends[1].z();
                    inv = false;
                } else {
                    xy1 = new int[]{ends[1].x2D(), ends[1].y2D()};
                    z[0] = ends[1].z();
                    xy2 = new int[]{ends[0].x2D(), ends[0].y2D()};
                    z[1] = ends[0].z();
                    inv = false;
                }
            } else if (ends[0].x2D() < ends[1].x2D()) {
                xy1 = new int[]{ends[0].x2D(), ends[0].y2D()};
                z[0] = ends[0].z();
                xy2 = new int[]{ends[1].x2D(), ends[1].y2D()};
                z[1] = ends[1].z();
                inv = (ends[0].y2D() < ends[1].y2D())? false : true;
            } else {
                xy1 = new int[]{ends[1].x2D(), ends[1].y2D()};
                z[0] = ends[1].z();
                xy2 = new int[]{ends[0].x2D(), ends[0].y2D()};
                z[1] = ends[0].z();
                inv = (ends[0].y2D() < ends[1].y2D())? true : false;
            }

            if (z[0] < zBuff[xy1[0]][xy1[1]])
            {
                zBuff[xy1[0]][xy1[1]] = z[0];
                cBuff[xy1[0]][xy1[1]] = color;
            }

            int cx = xy2[0] - xy1[0];
            int cy = xy2[1] - xy1[1];
            float cz = z[1] - z[0];
            

            if (Math.abs((float) cy / (float) cx) <= 1)
                hamsL1M(Math.abs(cx), Math.abs(cy), cz / Math.abs(cx), xy1, z[0], inv, zBuff, cBuff);
            else
                hamsG1M(Math.abs(cx), Math.abs(cy), cz / Math.abs(cy), xy1, z[0], inv, zBuff, cBuff);
        }
   }

   private void hamsL1M(int cx, int cy, float zStep, int[] xy, float z, boolean inv, float[][] zBuff, Color[][] cBuff)
    {
        int lcM1 = cx + 1;
        int x0 = xy[0];
        int y0 = xy[1];
        int y = (inv)? -1 : 1;
        int cy2 = 2 * cy;
        int c2y2x = cy2 - (2 * cx);
        for (int p = cy2 - cx, lcv = 0; ++lcv < lcM1; ) {
            if (p < 0)
                p = p + cy2;
            else {
                xy[1] = xy[1] + y;
                p = p + c2y2x;
            }
            ++xy[0];
            z += zStep;
            if (z < zBuff[xy[0]][xy[1]])
            {
                zBuff[xy[0]][xy[1]] = z;
                cBuff[xy[0]][xy[1]] = color;
            }
        }
    }

    private void hamsG1M(int cx, int cy, float zStep, int[] xy, float z, boolean inv, float[][] zBuff, Color[][] cBuff)
    {
        int lcM1 = cy + 1;
        int x0 = xy[0];
        int y0 = xy[1];
        int y = (inv)? -1 : 1;
        int cx2 = 2 * cx;
        int c2x2y = cx2 - (2 * cy);
        for (int p = cx2 - cy, lcv = 0; ++lcv < lcM1; ) {
            if (p < 0)
                p = p + cx2;
            else {
                xy[0]++;
                p = p + c2x2y;
            }
            xy[1] += y;
            z += zStep;

            if (z < zBuff[xy[0]][xy[1]])
            {
                zBuff[xy[0]][xy[1]] = z;
                cBuff[xy[0]][xy[1]] = color;
            }
        }
    }

    public static void paint(Vertex A, Vertex B, float[][] zBuff, Color[][] cBuff)
    {
            float[] z = new float[2];
            int[] xy1;
            int[] xy2;
            boolean inv;
            if (A.x2D() == B.x2D()) {
                if (A.y2D() < B.y2D()) {
                    xy1 = new int[]{A.x2D(), A.y2D()};
                    z[0] = A.z();
                    xy2 = new int[]{B.x2D(), B.y2D()};
                    z[1] = B.z();
                    inv = false;
                } else {
                    xy1 = new int[]{B.x2D(), B.y2D()};
                    z[0] = B.z();
                    xy2 = new int[]{A.x2D(), A.y2D()};
                    z[1] = A.z();
                    inv = false;
                }
            } else if (A.x2D() < B.x2D()) {
                xy1 = new int[]{A.x2D(), A.y2D()};
                z[0] = A.z();
                xy2 = new int[]{B.x2D(), B.y2D()};
                z[1] = B.z();
                inv = (A.y2D() < B.y2D())? false : true;
            } else {
                xy1 = new int[]{B.x2D(), B.y2D()};
                z[0] = B.z();
                xy2 = new int[]{A.x2D(), A.y2D()};
                z[1] = A.z();
                inv = (A.y2D() < B.y2D())? true : false;
            }

            if (z[0] < zBuff[xy1[0]][xy1[1]])
            {
                zBuff[xy1[0]][xy1[1]] = z[0];
                cBuff[xy1[0]][xy1[1]] = Color.BLACK;
            }

            int cx = xy2[0] - xy1[0];
            int cy = xy2[1] - xy1[1];
            float cz = z[1] - z[0];

            if (Math.abs((float) cy / (float) cx) <= 1)
                staticHamsL1M(Math.abs(cx), Math.abs(cy), cz / Math.abs(cx), xy1, z[0], inv, zBuff, cBuff);
            else
                staticHamsG1M(Math.abs(cx), Math.abs(cy), cz / Math.abs(cy), xy1, z[0], inv, zBuff, cBuff);
    }

    private static void staticHamsL1M(int cx, int cy, float zStep, int[] xy, float z, boolean inv, float[][] zBuff, Color[][] cBuff)
    {
        int lcM1 = cx + 1;
        int x0 = xy[0];
        int y0 = xy[1];
        int y = (inv)? -1 : 1;
        int cy2 = 2 * cy;
        int c2y2x = cy2 - (2 * cx);
        for (int p = cy2 - cx, lcv = 0; ++lcv < lcM1; ) {
            if (p < 0)
                p = p + cy2;
            else {
                xy[1] = xy[1] + y;
                p = p + c2y2x;
            }
            ++xy[0];
            z += zStep;
            if (z < zBuff[xy[0]][xy[1]])
            {
                zBuff[xy[0]][xy[1]] = z;
                cBuff[xy[0]][xy[1]] = Color.BLACK;
            }
        }
    }

    private static void staticHamsG1M(int cx, int cy, float zStep, int[] xy, float z, boolean inv, float[][] zBuff, Color[][] cBuff)
    {
        int lcM1 = cy + 1;
        int x0 = xy[0];
        int y0 = xy[1];
        int y = (inv)? -1 : 1;
        int cx2 = 2 * cx;
        int c2x2y = cx2 - (2 * cy);
        for (int p = cx2 - cy, lcv = 0; ++lcv < lcM1; ) {
            if (p < 0)
                p = p + cx2;
            else {
                xy[0]++;
                p = p + c2x2y;
            }
            xy[1] += y;
            z += zStep;

            if (z < zBuff[xy[0]][xy[1]])
            {
                zBuff[xy[0]][xy[1]] = z;
                cBuff[xy[0]][xy[1]] = Color.BLACK;
            }
        }
    }

    public void paint(Graphics2D g)
    {
        if (visable)
        {
            g.setColor(color);
            int[] xy1;
            int[] xy2;
            boolean inv;
            if (ends[0].x2D() == ends[1].x2D()) {
                if (ends[0].y2D() < ends[1].y2D()) {
                    xy1 = new int[]{ends[0].x2D(), ends[0].y2D()};
                    xy2 = new int[]{ends[1].x2D(), ends[1].y2D()};
                    inv = false;
                } else {
                    xy1 = new int[]{ends[1].x2D(), ends[1].y2D()};
                    xy2 = new int[]{ends[0].x2D(), ends[0].y2D()};
                    inv = false;
                }
            } else if (ends[0].x2D() < ends[1].x2D()) {
                xy1 = new int[]{ends[0].x2D(), ends[0].y2D()};
                xy2 = new int[]{ends[1].x2D(), ends[1].y2D()};
                inv = (ends[0].y2D() < ends[1].y2D())? false : true;
            } else {
                xy1 = new int[]{ends[1].x2D(), ends[1].y2D()};
                xy2 = new int[]{ends[0].x2D(), ends[0].y2D()};
                inv = (ends[0].y2D() < ends[1].y2D())? true : false;
            }
            drawPixel(g, xy1[0], xy1[1]);

            int cx = xy2[0] - xy1[0];
            int cy = xy2[1] - xy1[1];

            if (Math.abs((float) cy / (float) cx) <= 1)
                hamsL1M(g, Math.abs(cx), Math.abs(cy), xy1, inv);
            else
                hamsG1M(g, Math.abs(cx), Math.abs(cy), xy1, inv);
        }
    }

    private static void hamsL1M(Graphics2D g, int cx, int cy, int[] xy, boolean inv)
    {
        int lcM1 = cx + 1;
        int y = (inv)? -1 : 1;
        int cy2 = 2 * cy;
        int c2y2x = cy2 - (2 * cx);
        for (int p = cy2 - cx, lcv = 0; ++lcv < lcM1; drawPixel(g, ++xy[0], xy[1])) {
            if (p < 0)
                p = p + cy2;
            else {
                xy[1] = xy[1] + y;
                p = p + c2y2x;
            }
        }
    }

    private static void hamsG1M(Graphics2D g, int cx, int cy, int[] xy, boolean inv)
    {
        int lcM1 = cy + 1;
        int y = (inv)? -1 : 1;
        int cx2 = 2 * cx;
        int c2x2y = cx2 - (2 * cy);
        for (int p = cx2 - cy, lcv = 0; ++lcv < lcM1; drawPixel(g, xy[0], xy[1])) {
            if (p < 0)
                p = p + cx2;
            else {
                xy[0]++;
                p = p + c2x2y;
            }
            xy[1] += y;
        }
    }

    private static void drawPixel(Graphics2D g, int x, int y)
    {
        g.drawLine(x, y, x, y);
    }
}
