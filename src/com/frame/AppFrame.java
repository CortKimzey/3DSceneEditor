// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Sets up the frame so it can be closed and adds our canvas that draws our application

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
      add(new AppCanvas(1450, 920));

      setVisible(true);
   }
}
