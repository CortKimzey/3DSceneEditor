// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: 

package com.object;

import java.awt.*;
import java.awt.event.*;

import com.use.Matrix;

public class Face
{
    private Matrix v;
    private Matrix vt;
    private Matrix vn;

    private Face next = null;

    public Face(Matrix v, Matrix vt, Matrix vn)
    {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
        this.next = null;
    }

    public Face getNext()
    {
        return next;
    }

    public void setNext(Face next)
    {
        this.next = next;
    }

    public boolean isNext()
    {
        if (next == null)
            return false;
        else
            return true;
    }
}