package org.md2.input;

import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.md2.main.Game;
import org.md2.rendering.Texture;

import java.util.ArrayList;

public class KeyboardInput extends GLFWKeyCallback
{
	public static int ACTION_UP = GLFW.GLFW_KEY_W;
    public static int ACTION_DOWN = GLFW.GLFW_KEY_S;
    public static int ACTION_LEFT = GLFW.GLFW_KEY_A;
    public static int ACTION_RIGHT = GLFW.GLFW_KEY_D;
    public static int ACTION_INVENTORY = GLFW.GLFW_KEY_E;
    public static int ACTION_ESC = GLFW.GLFW_KEY_ESCAPE;
    public static int ACTION_VOID = GLFW.GLFW_KEY_SPACE;
    public static int ACTION_ENTER = GLFW.GLFW_KEY_ENTER;
    public static int ACTION_BACK = GLFW.GLFW_KEY_BACKSPACE;
    public static int ACTION_DELETE = GLFW.GLFW_KEY_DELETE;
    public static int ACTION_QUICKUSE1 = GLFW.GLFW_MOUSE_BUTTON_1;
    public static int ACTION_QUICKUSE2 = GLFW.GLFW_MOUSE_BUTTON_2;
    public static int ACTION_QUICKUSE3 = GLFW.GLFW_KEY_Q;
    public static int ACTION_SELECT = GLFW.GLFW_MOUSE_BUTTON_1;
    


    
    private static boolean keyboardUnlocked = false;
	private static boolean[] keyPushed = new boolean[348];
	private static boolean[] keyDisabled = new boolean[348];

	public KeyboardInput()
	{
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) 
	{
			keyPushed[key] = GLFW.GLFW_RELEASE != action;
			if(keyDisabled[key] && GLFW.GLFW_RELEASE == action)
				enableKey(key);
	}
	
	public static void invokeMouse(int button, int action, int mods) 
	{
		keyPushed[button] = GLFW.GLFW_RELEASE != action;
		if(keyDisabled[button] && GLFW.GLFW_RELEASE == action)
			enableKey(button);
		if(keyboardUnlocked && button == ACTION_SELECT)
			Game.getGame().getButtonManager().invokeButtons(action);
	}
	
	public void unlockKeyboard(boolean b)
    {
        keyboardUnlocked = b;
    }
	
	public static boolean isPushed(int key)
	{
		return keyPushed[key]&&keyboardUnlocked;
	}
	
	public static boolean isPressed(int key)
	{
		boolean ret = keyPushed[key]&&keyboardUnlocked&&!keyDisabled[key];
		if(ret)
			disableKey(key);
		return ret;
	}
	
	public static void disableKey(int key)
	{
		keyDisabled[key] = true;
	}
	
	public static void enableKey(int key)
	{
		keyDisabled[key] = false;
	}
	
	
}
