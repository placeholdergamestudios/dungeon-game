package org.md2.main;

import org.jbox2d.common.Vec2;
import org.md2.input.Button;
import org.md2.rendering.Texture;

import java.util.ArrayList;

public class ButtonManager
{
    public static int M_ESC_BUTTON_BACK = 0;
    public static int M_ESC_BUTTON_CLOSE_GAME = 1;
    public static int[] M__BUTTONS = {M_ESC_BUTTON_BACK};

    public static Button[] buttons = new Button[1];

    public ButtonManager()
    {
        buttons[M_ESC_BUTTON_BACK] = new Button(new Vec2(0.3F, 0.3F), new Vec2(0.3F, 0.3F), new Texture[]{Texture.ESCAPE_MENUE}, new Texture[]{Texture.PLAYERFRONT});
    }

    public void invokeButtons(int action)
    {
            for(Button b : buttons){
                b.refresh(action);
            }
    }

    public Button[] getButtons()
    {
        return buttons;
    }
}
