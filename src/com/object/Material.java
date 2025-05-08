// Team 5
// Professor Pushpa Kumar
// CS 4361.001
// Description: Material data that determine the color of a face

package com.object;

import java.awt.*;
import java.awt.event.*;

import java.io.File;

import com.point.Vector;

public class Material
{
    private String name;
    private Vector ka = new Vector(0.8f, 0.8f, 0.8f);
    private Vector kd = new Vector(0.8f, 0.8f, 0.8f);
    private Vector ks = new Vector(0.8f, 0.8f, 0.8f);
    private float ns = 500;
    private double d = 1;
    private int illum = 2;

    public Material(String name)
    {
        this.name = name;
    }

    public void setKa(float[] ka)
    {
        this.ka.set(ka);
    }

    public Vector getKa()
    {
        return this.ka;
    }

    public void setKd(float[] kd)
    {
        this.kd.set(kd);
    }

    public Vector getKd()
    {
        return this.kd;
    }

    public void setKs(float[] ks)
    {
        this.ks.set(ks);
    }

    public Vector getKs()
    {
        return this.ks;
    }

    public void setNs(float ns)
    {
        this.ns = ns;
    }

    public float getNs()
    {
        return this.ns;
    }

    public void setD(float d)
    {
        this.d = d;
    }

    public void setIllum(int illum)
    {
        this.illum = illum;
    }

    public void setText(String filePath)
    {
    }

    public void setBump(String filePath)
    {
    }

    public void setNi()
    {

    }

    public void write(java.io.FileWriter file) throws java.io.IOException
    {
        file.write("newmtl " + name);
        file.write("\nNs " + ns);
        file.write("\nKa " + ka.write());
        file.write("\nKd " + kd.write());
        file.write("\nKs " + ks.write());
        file.write("\nd " + d);
        file.write("\nillum " + illum + "\n");
    }

    public String getName()
    {
        return this.name;
    }
}
