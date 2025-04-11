// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.use.Matrix;

public class FaceList
{
    private Face root = null;

    public FaceList() {}

    public FaceList(Face root)
    {
        this.root = root;
    }

    public void append(Face data)
    {
        if (root == null)
        {
            root = data;
            return;
        }
        Face last = root;
        while (last.isNext())
            last = last.getNext();
        
        last.setNext(data);
    }

    public Face getRoot()
    {
        return root;
    }
}