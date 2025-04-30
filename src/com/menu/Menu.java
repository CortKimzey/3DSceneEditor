// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.menu;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

import com.canvas.DimCanvas;

import com.room.Room;

import com.use.BoxElement;

public class Menu extends BoxElement
{
   private DimCanvas d;
   private ArrayList<Room> rooms = new ArrayList<Room>(1);

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
      rooms.forEach(room -> {
         if (room.isActive())
            room.onClick(x,y);
      });
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
   }
}