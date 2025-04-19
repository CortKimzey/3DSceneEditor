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

//import com.use.BoxElement;

public class View extends Matrix
{
   private Matrix eye = new Matrix(new double[]{3,3,3}, true);
   private Matrix center = new Matrix(new double[]{0,0,0}, true);
   private Matrix up = new Matrix(new double[]{0,1,0}, true);
   private Matrix newF = new Matrix(3,1);
   private Matrix newR = new Matrix(3,1);
   private Matrix newU = new Matrix(3,1);

   private int x, y;

   public View()
   {
      super(4,4);
      update();
   }

   private void setF()
   {
      newF = normalize(subtraction(center, eye));
   }

   private void setR()
   {
      newR = normalize(cross(newF, up));
   }

   private void setU()
   {
      newU = cross(newR, newF);
   }

   public void moveLeft()
   {
      Matrix movement = multVale(negative(newR), 0.1);
      eye = sum(eye,movement);
      center = sum(center,movement);
      update();
   }

   public void moveRight()
   {
      Matrix movement = multVale(newR, 0.1);
      eye = sum(eye,movement);
      center = sum(center,movement);
      update();
   }

   public void moveForward()
   {
      Matrix movement = multVale(newF, 0.1);
      eye = sum(eye,movement);
      center = sum(center,movement);
      update();
   }

   public void moveBack()
   {
      Matrix movement = multVale(negative(newF), 0.1);
      eye = sum(eye,movement);
      center = sum(center,movement);
      update();
   }

   public void moveUp()
   {
      Matrix movement = multVale(newU, 0.1);
      eye = sum(eye, movement);
      center = sum(center, movement);
      update();
   }

   public void moveDown()
   {
      Matrix movement = multVale(negative(newU), 0.1);
      eye = sum(eye, movement);
      center = sum(center, movement);
      update();
   }

   public void update()
   {
      setF();
      setR();
      setU();

      for (int x = 0; x < 3; x++)
      {
         matrix[0][x] = newR.getLoc(x);
         matrix[1][x] = newU.getLoc(x);
         matrix[2][x] = ((-1)*newF.getLoc(x));
         matrix[3][x] = 0;
      }

      matrix[0][3] = ((-1) * dot(newR, eye));
      matrix[1][3] = ((-1) * dot(newU, eye));
      matrix[2][3] = ((-1) * dot(negative(newF), eye));
      matrix[3][3] = 1;
   }

   public void setXY(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public void restView()
   {
      eye = new Matrix(new double[]{3,3,3}, true);
      center = new Matrix(new double[]{0,0,0}, true);
      update();
   }

   public void onDrag(int x, int y)
   {
      Matrix offset = sum(multVale(newR, (double)(x - this.x)/1000), multVale(newU, (double)(y - this.y)/1000));
      Matrix newD = normalize(sum(newF,offset));
      center = sum(eye, multVale(newD, 5.2));
      setXY(x,y);
      update();
   }

   public boolean isFacing(Matrix A)
   {
      if (dot(A,this) > 0)
         return false;
      else
         return true;
   }
}