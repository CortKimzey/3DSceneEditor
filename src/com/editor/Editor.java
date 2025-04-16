// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.editor;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import com.object.Object;
import java.awt.*;
import java.awt.event.*;

import com.use.Box2D;
import com.use.Point2D;

public class Editor extends Box2D {
   private boolean active = false;
   private ObjList list;

   public Editor(int width, int height, Box2D scene)
   {
      super(300, scene.getH(), scene.getCNR(1).getX(), scene.getCNR(1).getY());
      list = new ObjList(width, height, cnr[0].getX(), cnr[1].getY());
      //edit = new ObjEditor(300, this.height - list.getH(), list.getCNR(3).getX(), list.getCNR(3).getY());
   }

   public void onClick(int x, int y)
   {
      
      list.onClick(x,y);
   }

   public void setSize(int width, int height, Box2D scene)
   {
      setH(scene.getH());
      setLoc(scene.getCNR(1).getX(), scene.getCNR(1).getY());
      list.setSize(cnr[0].getX(), cnr[0].getY());
   }

   public void addObject(File inputFile) throws FileNotFoundException
   {
      list.append(new Object(inputFile));
   }

  public void paint(Graphics g)
  {
      drawBox(g, Color.darkGray);
      list.paint(g);
  }

  public boolean isClicked(int x, int y)
  {
      return inBox(x,y);
  }
}