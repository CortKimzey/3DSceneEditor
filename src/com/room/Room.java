// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.room;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import com.editor.Editor;
import com.scene.Scene;

import com.use.BoxElement;

public class Room extends BoxElement
{
   private Editor edit;
   private Scene scene;

   public Room(int width, int height, int menuHeight, int menuBottom)
   {
      super(width, height - menuHeight, 0, menuBottom);
      scene = new Scene(width, height, menuHeight, menuBottom);
      edit = new Editor(width, height, this.height, scene.getCNR(1).getX(), scene.getCNR(1).getY());
      try {
         edit.addObject(new java.io.File("monkey.obj"));
      } catch (java.io.FileNotFoundException e) {
         System.out.println("No cube file!");
      }
      scene.getRoot(edit.giveRoot());
   }

   public void updateSize(int width, int height, int menuHeight)
   {
      setH(height - menuHeight);
      setW(width);
      scene.updateSize(width, height, menuHeight);
      edit.updateSize(width, height, this.height, scene.getCNR(1).getX(), scene.getCNR(1).getY());
   }

   public void addObject(File file)
   {
      try {
         edit.addObject(file);
      } catch (java.io.FileNotFoundException e) {
         System.out.println("Could not find file.");
      }
      scene.getRoot(edit.giveRoot());
   }

  public void paint(Graphics2D g)
  {
      scene.paint(g);
      edit.paint(g);
  }

   public void keyPressed(char in)
   {
      if (edit.isActive())
         edit.keyPressed(in);
      else if (scene.isActive())
         scene.keyPressed(in);
   }

   public void setInactive()
   {
      active = false;
      edit.setInactive();
      scene.setInactive();
   }

   public void onClick(int x, int y)
   {
      active = true;
      if (edit.isClicked(x,y))
      {
         scene.setInactive();
         edit.onClick(x, y);
      }
      else if (scene.isClicked(x,y))
      {
         edit.setInactive();
         scene.onClick(x,y);
      }
   }

   public void onDrag(int x, int y)
   {
      if (scene.isActive())
         scene.onDrag(x,y);
   }
}