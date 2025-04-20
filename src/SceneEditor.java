// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

import java.awt.*;
import java.awt.event.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.object.Object;
import com.frame.AppFrame;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;

public class SceneEditor {
   public static void main(String[] args) throws FileNotFoundException, IOException
   {
      AppFrame applicationWindow = new AppFrame();

      double[] name = new double[3];
      //System.out.println(name[0]);
      change(name);
      System.out.println(name[0]);
   }

   public static void change(double[] name)
   {
      name[0] = 1.5;
      name[1] = 1.5;
   }
}