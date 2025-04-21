package com.object;
public class Material {
    public String name;
    public double[] Ka = new double[3];  // Ambient color
    public double[] Kd = new double[3];  // Diffuse color
    public double[] Ks = new double[3];  // Specular color
    public double[] Ke = new double[3];
    public double Ns = 0;  // Shininess
    public double Ni;
    public double d;
    public int illum;


    public Material(String name) {
        this.name = name;
    }
}