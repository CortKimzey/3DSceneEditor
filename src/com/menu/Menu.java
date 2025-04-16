// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.menu;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;

import com.editor.Editor;
import com.scene.Scene;

import com.use.Box2D;
import com.use.Point2D;

import java.awt.Desktop;
import java.awt.Desktop.Action;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class Menu {
   private Box2D outline;
   private Scene scene;

   public Menu(int width, int height)
   {  
      outline = new Box2D(width, 22);
      scene = new Scene(width, height, outline);
   }

   public void setSize(int width, int height)
   {
      outline.setW(width);
      scene.setSize(width, height, outline);
   }

   public void onClick(int x, int y) throws IOException
   {
      if (y > 23)
         scene.onClick(x, y);
      else
      {
         //File directory = new File("C://Program Files//");
         //Desktop.getDesktop().open(directory);

         JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

         int returnValue = fileChooser.showOpenDialog(null);

         if (returnValue == JFileChooser.APPROVE_OPTION) {
            scene.addObject(fileChooser.getSelectedFile());
            //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
         }
      }
   }

   public void keyPressed(char in)
   {
      if (scene.isActive())
      {
         
      }
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

      scene.paint(g);
   }
   */

  public void paint(Graphics g)
   {
      outline.drawBox(g, Color.gray);

      scene.paint(g);
   }
}