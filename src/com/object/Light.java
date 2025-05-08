// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.scene.Projection;
import com.scene.View;

import com.point.Vertex;
import com.point.Vector;
import com.point.Matrix;

public class Light
{
    private byte type = 0b00000000;

    private Vector position = new Vector(5,5,5);
    private Vector direction = new Vector(-1f,-1f,-1f);
    private Vector intensity = new Vector(0.1f, 0.1f, 0.1f);
    private Color color = Color.WHITE;

    public Light()
    {

    }
}
