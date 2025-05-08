// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Menu header that adds application features and holds rooms

package com.menu;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import com.canvas.DimCanvas;

import com.room.Room;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

import com.use.BoxElement;
import com.use.Button;

public class Menu extends BoxElement
{
   private DimCanvas d;
   private ArrayList<Room> rooms = new ArrayList<Room>(1);
   private Button addFile = new Button(0,0,80,22, "Add Object");

   public Menu(DimCanvas d)
   {  
      super(0, 0, d.getWidth(), 22);
      this.d = d;

      rooms.add(new Room(0, 22, d));
      rooms.get(0).setActive(true);
   }

   public void updateSize()
   {
      setDim(d.getWidth(), 22);

      rooms.forEach(room -> {
         room.updateSize();
      });
   }

   public void onClick(int x, int y) throws IOException
   {
      if (isClicked(x,y))
      {
         if (addFile.isClicked(x,y))
         {
            rooms.get(0).addObject(testOut());
         }
      }
      else
      {
         rooms.forEach(room -> {
            if (room.isActive())
               room.onClick(x,y);
         });
      }
   }

   public void onRightClick(int x, int y) {
      System.out.println("[Menu] Right click forwarded at: " + x + ", " + y);
      rooms.forEach(room -> {
         if (room.isActive()) {
            System.out.println("[Menu] Forwarding to active room");
            room.onRightClick(x, y);
         }
      });
   }

   private java.io.File testOut()
   {
      JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) 
         return fileChooser.getSelectedFile();

      return null;
   }


   public void keyPressed(char in)
   {
      rooms.forEach(room -> {
         if (room.isActive())
            room.keyPressed(in);
      });
   }

   public void onDrag(int x, int y)
   {
      rooms.forEach(room -> {
         if (room.isActive())
            room.onDrag(x,y);
      });
   }

   public void paint(Graphics2D g)
   {
      drawBox(g, Color.gray);

      rooms.forEach(room -> {
         if (room.isActive())
            room.paint(g);
      });

      addFile.drawButton(g, Color.gray);
   }
}
