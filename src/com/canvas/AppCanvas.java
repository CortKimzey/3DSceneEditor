// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description:

package com.canvas;

import java.awt.*;
import java.awt.event.*;
import com.menu.Menu;

public class AppCanvas extends Canvas {
    private Menu menu;
    private int width;
    private int height;
    Dimension d;

    public AppCanvas(int width, int height)
    {
        setSize(width, height);
        d = getSize();
        width = d.width - 1;
        height = d.height - 1;
        menu = new Menu(width, height);
    }

    private void updateSize()
    {
        d = getSize();
        width = d.width - 1;
        height = d.height - 1;
        menu.setDimension(width, height);
    }

    public void paint(Graphics g)
    {
        updateSize();
        menu.paint(g);
        //g.drawLine(0, 0, width, 0);
    }
}