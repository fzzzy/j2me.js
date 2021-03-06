package com.nokia.mid.ui;

import javax.microedition.lcdui.Font;
import java.util.Hashtable;

class TextEditorThread implements Runnable {
    // We need a thread to be able to wake from js when there is an async js keyboard event.
    native void sleep();
    native int getNextDirtyEditor();

    Hashtable listeners;

    TextEditorThread() {
        listeners = new Hashtable();
    }

    public void run() {
        while (true) {
            sleep();
            int dirty = getNextDirtyEditor();
            TextEditor t = (TextEditor)listeners.get("" + dirty);
            t.myListener.inputAction(t, TextEditorListener.ACTION_CONTENT_CHANGE);
        }
    }

    void register(int id, TextEditor t) {
        listeners.put("" + id, t);
    }
}

public class TextEditor extends CanvasItem {
    // This flag is a hint to the implementation that during text editing, the smiley key should be disabled on the virtual keyboard.
    public static final int DISABLE_SMILEY_MODE = 4194304;

    protected TextEditorListener myListener;

    private int backgroundColor = 0;
    private int foregroundColor = 0;
    private boolean multiline = true;
    private boolean visible = true;
    private Object parent = null;
    private int maxSize = 0;
    private int width = 0;
    private int height = 0;
    private int myId;
    private static TextEditorThread textEditorThread;

    native int TextEditor0();
    native void setParent0(Object theParent);
    native void setSize0(int width, int height);
    native void setPosition0(int x, int y);
    native void setVisible0(boolean visible);
    native String getContent0();
    native void setContent0(String str);
    native void insert0(String str, int pos);
    native int size0();

    protected TextEditor(String label, String text, int aMaxSize, int constraints, int width, int height) {
        myId = TextEditor0();
        maxSize = aMaxSize;
        if (textEditorThread == null) {
            textEditorThread = new TextEditorThread();
            Thread t = new Thread(textEditorThread);
            t.start();
        }
    }

    // Creates a new TextEditor object with the given initial contents, maximum size in characters, constraints and editor size in pixels.
    public static TextEditor createTextEditor(String text, int maxSize, int constraints, int width, int height) {
        return new TextEditor("", text, maxSize, constraints, width, height);
    }

    // Creates a new empty TextEditor with the given maximum size in characters, constraints and editor size as number of visible rows.
    public static TextEditor createTextEditor(int maxSize, int constraints, int width, int rows) {
        return createTextEditor("", maxSize, constraints, width, Font.getDefaultFont().getHeight() * rows);
    }

    // Sets this TextEditor focused or removes keyboard focus.
    native public void setFocus(boolean focused);

    // Returns the focus state of TextEditor.
    native public boolean hasFocus();

    // Set the parent object of this TextEditor.
    public void setParent(Object theParent) {
        parent = theParent;
        setParent0(theParent);
    }

    // Get the parent object of this TextEditor.
    public Object getParent() {
        return parent;
    }

    // Sets the size of this TextEditor in pixels.
    public void setSize(int width, int height) {
        setSize0(width, height);
    }

    // Sets the rendering position of this TextEditor.
    public void setPosition(int x, int y) {
        setPosition0(x, y);
    }

    // Sets the visibility value of TextEditor.
    public void setVisible(boolean vis) {
        setVisible0(vis);
        visible = vis;
    }

    // Gets the visibility value of TextEditor.
    public  boolean isVisible() {
        return visible;
    }

    // Sets the Z-position, or the elevation, of the item.
    public void setZPosition(int z) {
        throw new RuntimeException("TextEditor::setZPosition(int) not implemented");
    }

    // Specifies whether or not the editor will receive touch-events.
    public void setTouchEnabled(boolean enabled) {
        throw new RuntimeException("TextEditor::setTouchEnabled(boolean) not implemented");
    }

    // Gets the current touch-enabled state.
    public boolean isTouchEnabled() {
        throw new RuntimeException("TextEditor::isTouchEnabled() not implemented");
    }

    // Returns the Z-position, or the elevation, of the item. The Z-position decides the stacking order of neighboring items.
    public int getZPosition() {
        throw new RuntimeException("TextEditor::getZPosition() not implemented");
    }

    // Gets the line margin height in this TextEditor in pixels.
    public int getLineMarginHeight() {
        return getFont().getHeight();
    }

    // Gets the whole content height in this TextEditor in pixels.
    public int getContentHeight() {
        System.out.println("warning: TextEditor::getContentHeight needs a more correct implementation");
        return getFont().getHeight();
    }

    // Sets the index of the caret.
    public void setCaret(int index) {
        System.out.println("warning: TextEditor::setCaret(int) not implemented");
    }

    // Gets the current position of the caret in the editor.
    public int getCaretPosition() {
        throw new RuntimeException("TextEditor::getCaretPosition() not implemented");
    }

    // Gets the topmost pixel position of the topmost visible line in the editor.
    public int getVisibleContentPosition() {
        System.out.println("warning: TextEditor::getVisibleContentPosition() not implemented");
        return 0;
    }

    // Gets the font being used in rendering the text content in this TextEditor.
    public Font getFont() {
        return Font.getDefaultFont();
    }

    // Sets the application preferred font for rendering the text content in this TextEditor.
    public void setFont(Font font) {
        System.out.println("TextEditor::setFont(Font) not implemented");
    }

    // Gets the background color and alpha of this TextEditor.
    public int getBackgroundColor() {
        return backgroundColor;
    }

    // Gets the foreground color and alpha of this TextEditor.
    public int getForegroundColor() {
        return foregroundColor;
    }

    // Sets the background color and alpha of this TextEditor to the specified values.
    public void setBackgroundColor(int color) {
        backgroundColor = color;
    }

    // Sets the foreground color and alpha of this TextEditor to the specified values.
    public void setForegroundColor(int color) {
        foregroundColor = color;
    }

    // Sets the highlight background color.
    public void setHighlightBackgroundColor(int color) {
        throw new RuntimeException("TextEditor::setHighlightBackgroundColor(int) not implemented");
    }

    // Sets the highlight foreground color.
    public void setHighlightForegroundColor(int color) {
        throw new RuntimeException("TextEditor::setHighlightForegroundColor(int) not implemented");
    }

    // Sets the content of the TextEditor as a string.
    public void setContent(String content) {
        setContent0(content);
    }

    // Gets the string content in the TextEditor.
    public String getContent() {
        return getContent0();
    }

    // Inserts a string into the content of the TextEditor.
    public void insert(String text, int position) {
        insert0(text, position);
    }

    // Deletes characters from the TextEditor.
    public void delete(int offset, int length) {
        System.out.println("TextEditor::delete(int,int) not implemented");
    }

    // Returns the maximum size (number of characters) that can be stored in this TextEditor.
    public int getMaxSize() {
        System.out.println("warning: TextEditor::getMaxSize() not implemented");
        return maxSize;
    }

    // Sets the maximum size (number of characters) that can be contained in this TextEditor.
    public int setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        System.out.println("warning: TextEditor::setMaxSize(int) not implemented");
        return maxSize;
    }

    // Gets the number of characters that are currently stored in this TextEditor.
    public int size() {
        return size0();
    }

    // Sets the input constraints of this TextEditor.
    public void setConstraints(int constraints) {
        throw new RuntimeException("TextEditor::setConstraints(int) not implemented");
    }

    // Gets the current input constraints of this TextEditor.
    public int getConstraints() {
        throw new RuntimeException("TextEditor::getConstraints() not implemented");
    }

    // Sets a hint to the implementation as to the input mode that should be used when the user initiates editing of this TextEditor.
    public void setInitialInputMode(String characterSubset) {
        throw new RuntimeException("TextEditor::setInitialInputMode(String) not implemented");
    }

    // Returns the initial input mode set to the editor, or null if no initial input mode has been set.
    public String getInitialInputMode() {
        throw new RuntimeException("TextEditor::getInitialInputMode() not implemented");
    }

    // Sets a selection on a range of text in the TextEditor content.
    public void setSelection(int index, int length) {
        throw new RuntimeException("TextEditor::setSelection(int,int) not implemented");
    }

    // Gets the currently selected content in the TextEditor.
    public String getSelection() {
        throw new RuntimeException("TextEditor::getSelection() not implemented");
    }

    // Sets a listener for content changes in this TextEditor, replacing any previous TextEditorListener.
    public void setTextEditorListener(TextEditorListener listener) {
        myListener = listener;
        textEditorThread.register(myId, this);
    }

    // Returns the multiline state of the TextEditor.
    public boolean isMultiline() {
        return multiline;
    }

    // Sets the editor to be either multi-line (true) or single-line (false).
    public void setMultiline(boolean aMultiline) {
        multiline = aMultiline;
    }

    // If the default indicator location is not used then sets the drawing location for input indicators relative to the TextEditor's parent.
    public void setIndicatorLocation(int x, int y) {
        throw new RuntimeException("TextEditor::setIndicatorLocation(int,int) not implemented");
    }

    // Resets the implementation provided input indicators to their default position.
    public void setDefaultIndicators() {
        throw new RuntimeException("TextEditor::setDefaultIndicators() not implemented");
    }

    // By default indicators visibility is set to true and they are made visible when the associated TextEditor is focused.
    public void setIndicatorVisibility(boolean visible) {
        throw new RuntimeException("TextEditor::setIndicatorVisibility(boolean) not implemented");
    }

    // Gets the size of the area needed for drawing the input indicators.
    public int[] getIndicatorSize() {
        throw new RuntimeException("TextEditor::getIndicatorSize() not implemented");
    }

    // Disables one or multiple touch input modes from use.
    public void setDisabledTouchInputModes(int touchInputModes) {
        throw new RuntimeException("TextEditor::setDisabledTouchInputModes(int) not implemented");
    }

    // By default all supported touch input modes are available.
    public int getDisabledTouchInputModes() {
        throw new RuntimeException("TextEditor::getDisabledTouchInputModes() not implemented");
    }

    // Set the preferred touch input mode overriding the device default preferred mode.
    public void setPreferredTouchMode(int touchInputModes) {
        throw new RuntimeException("TextEditor::setPreferredTouchMode(int) not implemented");
    }

    // Gets the preferred touch input mode.
    public int getPreferredTouchMode() {
        throw new RuntimeException("TextEditor::getPreferredTouchMode() not implemented");
    }

    // Sets the caret in the Editor at x, y location.
    public void setCaretXY(int x, int y) {
        throw new RuntimeException("TextEditor::setCaretXY(int,int) not implemented");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

