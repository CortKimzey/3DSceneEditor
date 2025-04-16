// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.frame;

import java.io.IOException;

import java.awt.*;
import java.awt.event.*;
import com.canvas.AppCanvas;

public class AppFrame extends Frame {
    public AppFrame() throws IOException
    {
      super("3D Scene Editor");
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      });
      setSize(1450, 920);

      AppCanvas scene = new AppCanvas(1450, 920);
      //scene.setSize(500,500);

      add(scene);
      setVisible(true);
   }
}