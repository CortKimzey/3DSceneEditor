// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.editor;

import java.awt.*;
import java.awt.event.*;

public class Editor {
    private int locX;
    private int locY = 22;
    private int height;
    private int width = 300;

   public Editor() {}

   public void setDimension(int width, int height)
   {
      this.height = height - 21;
      locX = width - 299;
   }

   public void paint(Graphics g)
   {
        g.setColor(Color.darkGray);
        g.fillRect(locX, locY, width, height);
        g.setColor(Color.black);
   }
}