// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.menu;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;

import com.room.Room;

import com.use.Button;
import com.use.BoxElement;

import java.awt.Desktop;
import java.awt.Desktop.Action;

import com.file.FileControl;
import com.file.Import;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class Menu extends BoxElement
{
   private FileControl file;
   private Room room;

   public Menu(int width, int height)
   {  
      super(width, 22);
      file = new FileControl(22, loc[0], loc[1]);
      room = new Room(width, height, this.height, tblr(1));
   }

   public void updateSize(int width, int height)
   {
      setW(width);
      room.updateSize(width, height, this.height);
   }

   public void onClick(int x, int y) throws IOException
   {
      if (isClicked(x, y))
      {
         room.setInactive();
         if (file.isClicked(x,y))
            file.dropDown();
      }
      else if (file.isClicked(x,y))
      {
         switch (file.getCase(x,y))
         {
            case 4:
               room.addObject(Import.testOut());
               file.setInactive();
               break;
         }
      }
      else
         room.onClick(x, y);
   }

   public void keyPressed(char in)
   {
      if (room.isActive())
         room.keyPressed(in);
   }

   public void onDrag(int x, int y)
   {
      room.onDrag(x,y);
   }

   /** 
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

      room.paint(g);
   }
   */

  public void paint(Graphics2D g)
   {
      drawBox(g, Color.gray);
      room.paint(g);
      file.paint(g, Color.gray);
   }
}