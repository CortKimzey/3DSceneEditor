// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import com.editor.Editor;

import com.use.Matrix;

import com.use.BoxElement;

public class Projection extends Matrix
{
   private double fov = 60;
   private double aspect;
   private double near = 0.1;
   private double far = 100;

   public Projection(int width, int height)
   {
      super(4,4);
      aspect = (((double)width)/((double)height));
      update();
   }

   public void updateSize(int width, int height)
   {
        //System.out.println("W: " + width + " H: " + height);
        aspect = (((double)width)/((double)height));
        update();
   }

   public void update()
   {
        double fov_rad = Math.toRadians(fov);
        double f = (1 / Math.tan(fov_rad/2));
        //System.out.println(aspect);
        matrix[0][0] = (1/(Math.tan(fov_rad/2)*aspect));
        matrix[1][1] = f;
        matrix[2][2] = ((far+near)/(near-far));
        matrix[2][3] = ((2*far*near)/(near-far));
        matrix[3][2] = -1;
   }
}