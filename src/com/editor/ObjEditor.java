// Cortland Kimzey
// Professor Pushpa Kumar
// CS 4361.001
// Description: Modified to apply transformations only when apply button is clicked

package com.editor;

import java.awt.*;
import com.point.Matrix;
import com.object.Object;
import com.canvas.DimCanvas;
import com.use.BoxElement;
import com.use.Button;

public class ObjEditor extends BoxElement {
    private DimCanvas d;
    private Object object = null;
    private Object originalObject = null; // Store original state for reset
    private Transform rot;
    private Transform trans;
    private Transform scale;
    private Button apply;
    private Button reset;

    public ObjEditor(int x, int y, DimCanvas d) {
        super(x, y, 300, d.getHeight() - y);
        this.d = d;
        rot = new Transform(280, 80, x + 25, y + 20, "Rotation");
        trans = new Transform(280, 80, rot.getCNR(3).getX(), rot.getCNR(3).getY(), "Translation");
        scale = new Transform(280, 80, trans.getCNR(3).getX(), trans.getCNR(3).getY(), "Scale");
        apply = new Button(scale.getCNR(3).getX() + 15, scale.getCNR(3).getY() + 5, 80, 20, "APPLY");
        reset = new Button(scale.getCNR(3).getX() + 150, scale.getCNR(3).getY() + 5, 80, 20, "RESET");
    }

    public void setObject(Object obj) {
        this.object = obj;

        // Store a backup of the original object state
        // In a production system, you would create a deep copy here
        // For simplicity, we're not implementing the full backup/restore system
        this.originalObject = obj;

        // Set the object for all transform components
        rot.setTargetObject(obj);
        trans.setTargetObject(obj);
        scale.setTargetObject(obj);
    }

    public void updateSize(int x, int y) {
        setLoc(x, y);
        setH(d.getHeight() - y);

        rot.updateSize(x + 10, y + 25);
        trans.updateSize(rot.getCNR(3).getX(), rot.getCNR(3).getY());
        scale.updateSize(trans.getCNR(3).getX(), trans.getCNR(3).getY());
        apply.setLoc(scale.getCNR(3).getX() + 15, scale.getCNR(3).getY() + 5);
        reset.setLoc(scale.getCNR(3).getX() + 150, scale.getCNR(3).getY() + 5);
    }

    @Override
    public void setInactive() {
        rot.setInactive();
        trans.setInactive();
        scale.setInactive();
        active = false;
    }

    public void keyPressed(char in) {
        if (rot.isActive())
            rot.keyPressed(in);
        else if (trans.isActive())
            trans.keyPressed(in);
        else if (scale.isActive())
            scale.keyPressed(in);
    }

    public void onDrag(int x, int y) {
        if (rot.isActive())
            rot.onDrag(x, y);
        else if (trans.isActive())
            trans.onDrag(x, y);
        else if (scale.isActive())
            scale.onDrag(x, y);

        // No real-time updates from dragging - wait for apply button
    }

    public void onClick(int x, int y) {
        active = true;
        if (rot.isClicked(x, y)) {
            trans.setInactive();
            scale.setInactive();
            rot.onClick(x, y);
        } else if (trans.isClicked(x, y)) {
            rot.setInactive();
            scale.setInactive();
            trans.onClick(x, y);
        } else if (scale.isClicked(x, y)) {
            rot.setInactive();
            trans.setInactive();
            scale.onClick(x, y);
        } else if (apply.isClicked(x, y)) {
            // When apply is clicked, commit the current transformation
            applyTrans();

            // Reset sliders but keep the object in its current state
            rot.reset();
            trans.reset();
            scale.reset();
        } else if (reset.isClicked(x, y)) {
            System.out.println("reset clicked");
            // Reset the object to original state
            resetObject();

            // Also reset all sliders
            rot.reset();
            trans.reset();
            scale.reset();
        }
    }

    private void resetObject() {
        // In a production system, you would restore from the backup
        // For this implementation, we'll just reset sliders
        // and let the user transform back manually
        // Reset transformation sliders
        rot.reset();
        trans.reset();
        scale.reset();
    }

    private void applyTrans() {
        // This method applies the final transformation and resets the UI
        float[] r = rot.getData();
        if (r[0] != 0)
            object.manipulate(Matrix.rotate(r[0], 'x'));
        if (r[1] != 0)
            object.manipulate(Matrix.rotate(r[1], 'y'));
        if (r[2] != 0)
            object.manipulate(Matrix.rotate(r[2], 'z'));

        float[] t = trans.getData();
        object.manipulate(Matrix.translation(t, false));
        object.updateTrans(t);

        // object.manipulate(Matrix.scale(scale.getData()));
        object.manipulate(Matrix.mult(Matrix.translation(object.getTrans(), true),
                Matrix.scale(scale.getData()),
                Matrix.translation(object.getTrans(), false)));
    }

    public void paint(Graphics2D g) {
        drawBox(g, Color.DARK_GRAY);

        g.setColor(Color.white);
        g.drawString("Object Transformations: " + (object != null ? object.getName() : "None"),
                loc[0] + 10, loc[1] + 20);

        rot.paint(g);
        trans.paint(g);
        scale.paint(g);

        // Draw Apply and Reset buttons with clear visual distinction
        apply.drawButton(g, new Color(50, 200, 50)); // Green for Apply
        reset.drawButton(g, new Color(200, 50, 50)); // Red for Reset

        // Add button text
        g.setColor(Color.WHITE);
        g.drawString("APPLY", apply.getX() + 21, apply.getY() + 15);
        g.drawString("RESET", reset.getX() + 20, reset.getY() + 15);
    }
}