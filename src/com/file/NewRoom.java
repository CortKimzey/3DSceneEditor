// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.file;

import java.awt.*;
import java.awt.event.*;

import com.use.Button;

public class NewRoom extends Button 
{
    public NewRoom(int width, int height, int x, int y)
    {
        super(width, height, x, y, "New Room");
    }

    public void paint(Graphics2D g, Color color)
    {
        drawBox(g, color);
        g.drawString(text, loc[0] + 5, loc[1] + strDepth);
    }
}