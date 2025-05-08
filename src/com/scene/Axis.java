// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Three lines that are painted on the x, y, and z axis of our scene

package com.scene;

import java.awt.*;
import java.awt.event.*;

import com.object.Line;

public class Axis
{
    Line[] axis = new Line[]{new Line(), new Line(), new Line()};

    public Axis()
    {
        axis[0].setColor(Color.RED);
        axis[0].setV1(-100f,0f,0f);
        axis[0].setV2(100f,0f,0f);

        axis[1].setColor(Color.GREEN);
        axis[1].setV1(0f,-100f,0f);
        axis[1].setV2(0f,100f,0f);

        axis[2].setColor(Color.BLUE);
        axis[2].setV1(0f,0f,-100f);
        axis[2].setV2(0f,0f,100f);
    }

    public void transform2D(int width, int height, Projection projMat, View viewMat)
    {
        axis[0].transform2D(width, height, projMat, viewMat);
        axis[1].transform2D(width, height, projMat, viewMat);
        axis[2].transform2D(width, height, projMat, viewMat);
    }

    public void paint(float[][] zBuff, Color[][] cBuff)
    {
        axis[0].paint(zBuff, cBuff);
        axis[1].paint(zBuff, cBuff);
        axis[2].paint(zBuff, cBuff);
    }

    public void paint(Graphics2D g)
    {
        axis[0].paint(g);
        axis[1].paint(g);
        axis[2].paint(g);
    }
}
