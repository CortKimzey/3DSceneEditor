// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.menu;

import java.awt.*;
import java.awt.event.*;
import com.editor.Editor;
import com.scene.Scene;
import com.use.Point2D;

public class Menu {
   private Point2D[] cnr = new Point2D[4];
   private Scene scene = new Scene();
   private int height;
   private int width;

   public Menu() {}

   public Menu(int width, int height)
   {
      this.width = width;
      this.height = height;
      cnr[0] = new Point2D(0, 0);
      cnr[1] = new Point2D(width, 0);
      cnr[2] = new Point2D(0, 21);
      cnr[3] = new Point2D(width, 21);
   }

   public void setDimension(int width, int height)
   {
      this.width = width;
      this.height = height;
      cnr[1] = new Point2D(width, 0);
      cnr[3] = new Point2D(width, 21);
      scene.setDimension(width, height);
      //editor.setDimension(width, height);
   }

   public void paint(Graphics g)
   {
      g.setColor(Color.black);
      g.drawLine(0, 0, width, 0);

      g.setColor(Color.gray);
      g.fillRect(0, 1, width + 1, 20);

      g.setColor(Color.black);
      g.drawString("File", 10, 15);

      g.setColor(Color.black);
      g.drawLine(39, 3, 39, 18);

      g.setColor(Color.black);
      g.drawString("Edit", 49, 15);

      g.setColor(Color.black);
      g.drawLine(80, 3, 80, 18);

      g.setColor(Color.black);
      g.drawLine(0, 21, width, 21);

      scene.paint(g);
   }
}