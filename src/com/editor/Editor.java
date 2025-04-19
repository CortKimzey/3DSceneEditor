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

import com.editor.ObjList;
import com.use.BoxElement;

public class Editor extends BoxElement {
   private ObjEditor edit;
   private ObjList list;

   public Editor(int width, int height, int sceneHeight, int trX, int trY)
   {
      super(300, sceneHeight, trX, trY);
      list = new ObjList(width, height, cnr[0].getX(), cnr[1].getY());

      edit = new ObjEditor(300, height - 300, list.getCNR(3).getX(), list.getCNR(3).getY());
   }

   public void updateSize(int width, int height, int sceneHeight, int trX, int trY)
   {
      setH(sceneHeight);
      setLoc(trX, trY);
      list.updateSize(cnr[0].getX(), cnr[0].getY());
      edit.updateSize(list.getCNR(3).getX(), list.getCNR(3).getY());
   }

   public Object giveRoot()
   {
      return list.giveRoot();
   }

   public void addObject(File inputFile) throws FileNotFoundException
   {
      list.append(inputFile);
   }

  public void paint(Graphics2D g)
  {
      drawBox(g, Color.darkGray);
      list.paint(g);
      edit.paint(g);
  }

  public void keyPressed(char in)
   {
      if (edit.isActive())
         edit.keyPressed(in);
      else if (list.isActive())
         list.keyPressed(in);
   }

  public void setInactive()
  {
      active = false;
      list.setInactive();
      edit.setInactive();
  }

  public void onClick(int x, int y)
   {
      active = true;
      if (list.isClicked(x,y))
      {
         edit.setInactive();
         list.onClick(x,y);
      }
      else if (edit.isClicked(x,y))
      {
         list.setInactive();
         edit.onClick(x,y);
      }
   }
}