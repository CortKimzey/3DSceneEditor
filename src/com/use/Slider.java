// Slider.java
// A custom slider component for the editor with text input and real-time updates

package com.use;

import java.awt.*;
import java.awt.event.*;

public class Slider {
    private int x, y;
    private int width, height;
    private int min, max;
    private int value;
    private boolean active = false;
    private boolean textActive = false;
    private Rectangle slider;
    private Rectangle track;
    private Rectangle textBox;
    private String textValue = "";
    private static final int SLIDER_WIDTH = 10;
    private static final int TEXT_BOX_WIDTH = 40;
    private static final int TEXT_BOX_HEIGHT = 16;
    private static final Color TEXT_BOX_COLOR = new Color(50, 50, 50);
    private static final Color TEXT_ACTIVE_COLOR = new Color(70, 70, 70);
    
    // Callback interface for value changes
    public interface ValueChangeListener {
        void onValueChanged(int newValue);
    }
    
    private ValueChangeListener valueChangeListener = null;
    
    public Slider(int width, int height, int x, int y, int min, int max) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;
        this.value = min;
        this.textValue = String.valueOf(min);
        
        // Create track and slider components
        track = new Rectangle(x, y + height/2 - 2, width, 4);
        textBox = new Rectangle(x + width + 10, y, TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        updateSliderPosition();
    }
    
    private void updateSliderPosition() {
        float percentage = (float)(value - min) / (max - min);
        int sliderX = x + (int)(percentage * (width - SLIDER_WIDTH));
        slider = new Rectangle(sliderX, y, SLIDER_WIDTH, height);
    }
    
    public boolean isClicked(int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width &&
            mouseY >= y && mouseY <= y + height) {
            return true;
        }
        
        // Check if text box was clicked
        if (mouseX >= textBox.x && mouseX <= textBox.x + textBox.width &&
            mouseY >= textBox.y && mouseY <= textBox.y + textBox.height) {
            textActive = true;
            active = false;
            textValue = String.valueOf(value);
            return true;
        }
        
        return false;
    }
    
    public void onClick() {
        active = true;
        textActive = false;
    }
    
    public void onTextBoxClick() {
        textActive = true;
        active = false;
        textValue = String.valueOf(value);
    }
    
    public void setInactive() {
        active = false;
        if (textActive) {
            commitTextValue();
        }
        textActive = false;
    }
    
    public boolean isActive() {
        return active || textActive;
    }
    
    public boolean isTextActive() {
        return textActive;
    }
    
    public void setValueChangeListener(ValueChangeListener listener) {
        this.valueChangeListener = listener;
    }
    
    public void setLoc(int x, int y) {
        this.x = x;
        this.y = y;
        track = new Rectangle(x, y + height/2 - 2, width, 4);
        textBox = new Rectangle(x + width + 10, y, TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        updateSliderPosition();
    }
    
    public void onDrag(int mouseX) {
        if (!active) return;
        
        // Constrain mouse position to slider boundaries
        int constrainedX = Math.max(x, Math.min(mouseX, x + width - SLIDER_WIDTH));
        
        // Calculate new value based on position
        float percentage = (float)(constrainedX - x) / (width - SLIDER_WIDTH);
        int newValue = min + (int)(percentage * (max - min));
        
        // Only update if value changed
        if (newValue != value) {
            value = newValue;
            textValue = String.valueOf(value);
            
            // Update slider position
            slider = new Rectangle(constrainedX, y, SLIDER_WIDTH, height);
            
            // Notify listener of value change
            if (valueChangeListener != null) {
                valueChangeListener.onValueChanged(value);
            }
        }
    }
    
    public void keyPressed(char key) {
        if (!textActive) return;
        
        if (key == '\n' || key == '\r') {
            // Enter key - commit the value
            commitTextValue();
            textActive = false;
        } else if (key == '\b') {
            // Backspace key - remove last character
            if (textValue.length() > 0) {
                textValue = textValue.substring(0, textValue.length() - 1);
            }
        } else if (Character.isDigit(key) || (key == '-' && textValue.isEmpty())) {
            // Allow digits and minus sign at beginning
            textValue += key;
        }
    }
    
    private void commitTextValue() {
        try {
            // Parse the text value
            int newValue = Integer.parseInt(textValue);
            
            // Clamp to min/max range
            newValue = Math.max(min, Math.min(max, newValue));
            
            // Update if changed
            if (newValue != value) {
                value = newValue;
                updateSliderPosition();
                
                // Notify listener of value change
                if (valueChangeListener != null) {
                    valueChangeListener.onValueChanged(value);
                }
            }
            
            // Update text to show clamped value
            textValue = String.valueOf(value);
        } catch (NumberFormatException e) {
            // Invalid number, revert to current value
            textValue = String.valueOf(value);
        }
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = Math.max(min, Math.min(max, value));
        this.textValue = String.valueOf(this.value);
        updateSliderPosition();
        
        // Notify listener of value change
        if (valueChangeListener != null) {
            valueChangeListener.onValueChanged(this.value);
        }
    }
    
    public void reset() {
        setValue(min);
        active = false;
        textActive = false;
    }
    
    public void paint(Graphics2D g) {
        // Draw track
        g.setColor(Color.GRAY);
        g.fill(track);
        
        // Draw slider
        if (active) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fill(slider);
        g.setColor(Color.BLACK);
        g.draw(slider);
        
        // Draw text box
        if (textActive) {
            g.setColor(TEXT_ACTIVE_COLOR);
        } else {
            g.setColor(TEXT_BOX_COLOR);
        }
        g.fill(textBox);
        g.setColor(Color.WHITE);
        g.draw(textBox);
        
        // Draw text value
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(textValue);
        int textHeight = fm.getHeight();
        g.drawString(textValue, 
                    textBox.x + (textBox.width - textWidth) / 2, 
                    textBox.y + (textBox.height + textHeight / 2) / 2);
    }
}