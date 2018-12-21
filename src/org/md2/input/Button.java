package org.md2.input;

import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import org.md2.common.Tools;
import org.md2.main.Game;
import org.md2.main.GraphicRendererV2;
import org.md2.rendering.Texture;

public class Button
{
    protected final Vec2 coordinates;
    protected final Vec2 size;
    private Texture[] texturesNormal;
    private Texture[] texturesClicked;
    private boolean isCurrentlyClicked = false;

    public Button(Vec2 coordinates, Vec2 size, Texture[] texturesNormal)
    {
        this.coordinates = coordinates;
        this.size = size;
        this.texturesNormal = texturesNormal;
        this.texturesClicked = texturesNormal;

    }

    public Button(Vec2 coordinates, Vec2 size, Texture[] texturesNormal, Texture[] texturesClicked)
    {
        this.coordinates = coordinates;
        this.size = size;
        this.texturesNormal = texturesNormal;
        this.texturesClicked = texturesClicked;
    }

    public void refresh(int action)
    {
        if(wasClicked()){
            isCurrentlyClicked = action != GLFW.GLFW_RELEASE;
        }
    }

    public boolean wasClicked()
    {
        System.out.println(GraphicRendererV2.getMousePos());
        return Tools.vec2InsideRect(coordinates, size, GraphicRendererV2.getMousePos());
    }

    public Texture[] getTexture()
    {
        return isCurrentlyClicked ? texturesClicked : texturesNormal;
    }

    public Vec2 getCoordinates() {
        return coordinates;
    }

    public Vec2 getSize() {
        return size;
    }
}
