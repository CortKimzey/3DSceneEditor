# 3DSceneEditor
An Interactive 3D Scene Editor made in Java

To run this program run the following command in the directory that contains the bin directory

java -cp bin SceneEditor


To change the Viewing mode of the scene click on the scene to activate it and type any number from 0-7, to manipulate the camera position use w, a, s, d, shift, and space.

0 - Only Axis of Scene

1 - Every triangle of the objects draw

2 - Only the triangle of the object that are visable

3 - The depth of each pixel of the objects

4 - The ambient light of the object

5 - The Diffuse reflection of the objects

6 - The Specular reflection of the object

7 - Fully colored object.

w - Move camera forwards

s - Move camera back

a - Move camera left

d - Move camera right

shift - Move camera down

space - Move camera up


A Java 3D Object Manipulator built from scratch using concepts and algorithms learned in CS 4361.001. User is able to upload objects that are processed through the graphics rendering pipeline and are able to be viewed & manipulated in the scene.


All the source code for our project is contained inside the src directory

The 3DSceneEditor.c file is a program used to compile all code inside the src directory and place the compiled code inside the bin directory
3DSceneEditor and 3DSceneEditor.exe is the compiled 3DSceneEditor.c file that allows you to compile and run our program on Mac and Windows respectively
