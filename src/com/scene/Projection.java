// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import java.awt.*;
import java.awt.event.*;

import com.point.Matrix;

public class Projection extends Matrix
{
   private double fov = 60;
   private float aspect;
   private float near = 0.1f;
   private float far = 100f;

   public Projection(int width, int height)
   {
      super(4,4);
      aspect = (((float)width)/((float)height));
      update();
   }

   public void updateSize(int width, int height)
   {
        aspect = (((float)width)/((float)height));
        update();
   }

   public void update()
   {
        double fov_rad = Math.toRadians(fov);
        float f = (1 / (float)Math.tan(fov_rad/2));
        data[0] = (1/((float)Math.tan(fov_rad/2)*aspect));
        data[5] = f;
        data[10] = ((far+near)/(near-far));
        data[11] = ((2*far*near)/(near-far));
        data[14] = -1;
   }
}