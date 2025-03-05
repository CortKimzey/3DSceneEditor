// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.frame;

import java.awt.*;
import java.awt.event.*;
import com.canvas.SceneCanvas;

public class AppFrame extends Frame {
    public AppFrame() 
    {
      super("3D Scene Editor");
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      });
      setSize(1080, 920);

      SceneCanvas scene = new SceneCanvas();

      add(scene);
      setVisible(true);

      scene.setSize(500,500);
   }
}