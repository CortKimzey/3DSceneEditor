// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Hold the dimension of the canvas so other elements can base there size off this

package com.canvas;

import java.io.IOException;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.event.*;
import com.menu.Menu;

public class DimCanvas
{
    private int width;
    private int height;

    public DimCanvas(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setDim(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
}
