// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description:

package com.canvas;

import java.awt.*;
import java.awt.event.*;

public class SceneCanvas extends Canvas {

    Dimension d;

    public SceneCanvas() {
        setSize(500, 500);
    }

    public void paint(Graphics g) {
        d = getSize();
        int maxX = d.width - 1, maxY = d.height - 1;
        g.drawLine(0,0,maxX, 0);
   }
}