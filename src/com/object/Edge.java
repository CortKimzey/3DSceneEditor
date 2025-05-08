// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Defines the edge of a line

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.point.Vertex2D;

public class Edge
{
    private float dx, dy;
    private float dxy;

    public Edge(Vertex2D A, Vertex2D B)
    {
        dx = B.x() - A.x();
        dy = A.y() - B.y();
        dxy = (A.x()*B.y()) - (B.x()*A.y());
    }

    public float pixelTest(int x, int y)
    {
        return ((dy*x) + (dx*y) + dxy);
    }
}
