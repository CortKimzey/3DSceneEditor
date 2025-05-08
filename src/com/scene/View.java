// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: View Matrix that defines the position and orientation of the camera in our scene

package com.scene;

import java.awt.*;
import java.awt.event.*;

import com.point.Matrix;
import com.point.Vector;

public class View extends Matrix
{
   private Vector eye = new Vector(3f, 3f, 3f);
   private Vector center = new Vector(0f, 0f, 0f);
   private Vector up = new Vector(0f, 1f, 0f);
   private Vector newF = new Vector();
   private Vector newR = new Vector();
   private Vector newU = new Vector();

   private int x, y;

   public View()
   {
      super(4,4);
      update();
   }

   private void setF()
   {
      newF = Vector.sub(center, eye);
      newF.normalize();
   }

   private void setR()
   {
      newR = Vector.cross(newF, up);
      newR.normalize();
   }

   private void setU()
   {
      newU = Vector.cross(newR, newF);
   }

   public void moveLeft()
   {
      Vector movement = newR;
      movement.neg();
      movement.mult(0.1f);
      eye = Vector.sum(eye,movement);
      center = Vector.sum(center,movement);
      update();
   }

   public void moveRight()
   {
      Vector movement = newR;
      movement.mult(0.1f);
      eye = Vector.sum(eye,movement);
      center = Vector.sum(center,movement);
      update();
   }

   public void moveForward()
   {
      Vector movement = newF;
      movement.mult(0.1f);
      eye = Vector.sum(eye,movement);
      center = Vector.sum(center,movement);
      update();
   }

   public void moveBack()
   {
      Vector movement = newF;
      movement.neg();
      movement.mult(0.1f);
      eye = Vector.sum(eye,movement);
      center = Vector.sum(center,movement);
      update();
   }

   public void moveUp()
   {
      Vector movement = newU;
      movement.mult(0.1f);
      eye = Vector.sum(eye, movement);
      center = Vector.sum(center, movement);
      update();
   }

   public void moveDown()
   {
      Vector movement = newU;
      movement.neg();
      movement.mult(0.1f);
      eye = Vector.sum(eye, movement);
      center = Vector.sum(center, movement);
      update();
   }

   public void update()
   {
      setF();
      setR();
      setU();

      newF.neg();
      for (int x = 0; x < 3; x++)
      {
         data[x] = newR.get(x);
         data[4 + x] = newU.get(x);
         data[8 + x] = newF.get(x);
         data[12 + x] = 0;
      }

      data[3] = ((-1) * newR.dot(eye));
      data[7] = ((-1) * newU.dot(eye));
      data[11] = ((-1) * newF.dot(eye));
      data[15] = 1;

      newF.neg();
   }

   public void setXY(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public void restView()
   {
      eye.setXYZ(3f, 3f, 3f);
      center.setXYZ(0f, 0f, 0f);
      up.setXYZ(0f, 1f, 0f);
      update();
   }

   public void onDrag(int x, int y)
   {
      Vector offset = Vector.sum(Vector.mult(newR, (float)(x - this.x)/1000), Vector.mult(newU, (float)(y - this.y)/1000));
      Vector newD = Vector.sum(newF, offset);
      newD.normalize();
      center = Vector.sum(eye, Vector.mult(newD, 5.2f));
      setXY(x,y);
      update();
   }

   public Vector getEye()
   {
      return this.eye;
   }

   public boolean isFacing(Vector N, Vector C)
   {
      if (N.dot(Vector.sub(eye,C)) > 0)
         return true;
      else
         return false;
   }

}
