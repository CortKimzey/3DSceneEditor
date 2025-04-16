// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import com.editor.Editor;

import com.use.Box2D;
import com.use.Point2D;

public class Scene {
   private boolean active = false;
   private Editor editor;
   private Box2D outline;

   public Scene(int width, int height, Box2D menu)
   {
      outline = new Box2D(width - 300, height - menu.getH(), 0, menu.getTBLR(1));
      editor = new Editor(width, height, outline);
      try {
         editor.addObject(new java.io.File("cube.obj"));
      } catch (java.io.FileNotFoundException e) {
         System.out.println("No cube file!");
      }
   }

   public void setSize(int width, int height, Box2D menu)
   {
      outline.setH(height - menu.getH());
      outline.setW(width - 300);

      editor.setSize(width, height, outline);
   }

   public void addObject(File file)
   {
      try {
         editor.addObject(file);
      } catch (java.io.FileNotFoundException e) {
         System.out.println("Could not find file.");
      }
   }

   public boolean isActive()
   {
      return active;
   }

   /** 
   public void paint(Graphics g)
   {
      g.setColor(Color.white);
      g.fillRect(0, 22, width - 300, height);
      editor.paint(g);
   }
   */

  public void paint(Graphics g)
  {
      outline.drawBox(g, Color.red);

      editor.paint(g);
  }

   public void onClick(int x, int y)
   {
      active = true;
      if (editor.isClicked(x,y))
         editor.onClick(x, y);
   }
}