// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.scene;

import com.use.Matrix;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import com.object.Object;
import com.editor.Editor;

import com.object.Face;
import com.use.BoxElement;

public class Scene extends BoxElement
{
   private double[][] zBuffer;
   private double min = 1;
   private double max = 0;
   private int buffW, buffH;
   private Object root = null;
   private View viewMat;
   private Projection projMat;

   public Scene(int width, int height, int menuHeight, int menuBottom)
   {
      super(width - 300, height - menuHeight, 0, menuBottom);
      viewMat = new View();
      projMat = new Projection(this.width, this.height);
      zBuffer = new double[this.width][this.height];
      buffW = this.width;
      buffH = this.height;
   }

   public void updateSize(int width, int height, int menuHeight)
   {
      setH(height - menuHeight);
      setW(width - 300);
      projMat.updateSize(this.width, this.height);

      if (zBuffer == null || buffW < this.width || buffH < this.height)
      {
         zBuffer = new double[this.width][this.height];
         buffW = this.width;
         buffH = this.height;
      }
   }

   public void getRoot(Object root)
   {
      this.root = root;
      System.out.println("Got Root");
   }

  public void paint(Graphics2D g)
  {
      resetBuffer();

      //triangleTest(g);

      paintBuffer(g);
  }

  private void paintTriangles(Graphics2D g, Object obj)
  {
      ArrayList<Face2D> f2DList = obj.triangulate(v2DList(obj.getVList()));
      for (int x = 0, lcv = f2DList.size(); x < lcv; x++)
      {
         f2DList.get(x).paint2(g);
      }
  }

  private void triangleTest(Graphics2D g)
  {
      Object hold = root;
      paintTriangles(g, hold);
      while (hold.isNext())
      {
         hold = hold.getNext();
         paintTriangles(g, hold);
      }
  }

  private void paintBuffer(Graphics2D g)
  {
      Object hold = root;
      paintObj(g, hold);
      while (hold.isNext())
      {
         hold = hold.getNext();
         paintObj(g, hold);
      }

      setMinMax();
      Color color;
      for (int x = 0; x < this.width; x++)
      {
         for (int y = 0; y < this.height; y++)
         {
            if (zBuffer[x][y] < Double.POSITIVE_INFINITY)
            {
               //System.out.println(zBuffer[x][y]);
               int gray = (int)(255 * (zBuffer[x][y] - min) / (max - min));
               gray = Math.max(0, Math.min(255, gray)); // clamp
               color = new Color(gray, gray, gray);
            }
            else
               color = Color.WHITE;

            g.setColor(color);
            g.drawLine(loc[0] + x, loc[1] + y, loc[0] + x, loc[1] + y);
         }
      }
  }

  private void resetBuffer()
  {
      for (int x = 0; x < this.width; x++)
         for (int y = 0; y < this.height; y++)
            zBuffer[x][y] = Double.POSITIVE_INFINITY;
  }

  private void setMinMax()
  {
      min = 1;
      max = 0;

      for (int x = 0; x < this.width; x++)
      {
         for (int y = 0; y < this.height; y++)
         {
            if (zBuffer[x][y] < Double.POSITIVE_INFINITY)
            {
               if (zBuffer[x][y] < min)
                  min = zBuffer[x][y];
               if (zBuffer[x][y] > max)
                  max = zBuffer[x][y];
            }
         }
      }
  }

  private void paintObj(Graphics2D g, Object obj)
  {
      ArrayList<Face2D> f2DList = obj.triangulate(v2DList(obj.getVList()), this.width, this.height);
      for (int x = 0, lcv = f2DList.size(); x < lcv; x++)
      {
         f2DList.get(x).addFace(zBuffer);
         //f2DList.get(x).paint2(g);
      }
  }

  private ArrayList<Vert2D> v2DList(ArrayList<Matrix> vList)
  {
      ArrayList<Vert2D> output = new ArrayList<>(0);
      for (int x = 0, lcv = vList.size(); x < lcv; x++)
         output.add(loc2D(vList.get(x)));
      return output;
  }

  private Vert2D loc2D(Matrix vert)
   {
      Matrix ndc = Matrix.perpDiv(Matrix.multiplication(projMat, viewMat, vert));
      int[] out = new int[2];
      out[0] = Math.round((float)((ndc.getLoc(0) + 1) * 0.5 * width));   // X coordinate
      out[1] = Math.round((float)((1 - ndc.getLoc(1)) * 0.5 * height));  // Y coordinate
      return new Vert2D(out[0],out[1], ndc.getLoc(2));
   }

   public void keyPressed(char in)
   {
      switch (in)
      {
         case 87:
            viewMat.moveForward();
            break;
         case 65:
            viewMat.moveLeft();
            break;
         case 83:
            viewMat.moveBack();
            break;
         case 68:
            viewMat.moveRight();
            break;
         case 32:
            viewMat.moveUp();
            break;
         case 14:
            viewMat.moveDown();
            break;
         case 82:
            viewMat.restView();
      }
   }

   public void setInactive()
   {
      active = false;
   }

   public void onClick(int x, int y)
   {
      active = true;
      viewMat.setXY(x,y);
   }

   public void onDrag(int x, int y)
   {
      viewMat.onDrag(x,y);
   }
}