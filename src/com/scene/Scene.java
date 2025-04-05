// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import java.awt.*;
import java.awt.event.*;
import com.editor.Editor;
import com.use.Point2D;

public class Scene {
   private Editor editor = new Editor();
   private Point2D[] corners = new Point2D[4];
   private int height;

   private int width;

   public Scene() {}

   public void setDimension(int width, int height)
   {
      editor.setDimension(width, height);
      this.width = width;
      this.height = height - 21;
   }

   public void paint(Graphics g)
   {
      g.setColor(Color.white);
      g.fillRect(0, 22, width - 300, height);
      editor.paint(g);
   }
}