package net.fusionlord.fusionutil.client.dynamics.backgrounds;

import net.fusionlord.fusionutil.client.dynamics.elements.IGuiElement;
import net.fusionlord.fusionutil.client.dynamics.skins.BackgroundSkin;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIBackgroundProvider implements IGuiElement
{
	protected final List<IGuiElement> components = new ArrayList<IGuiElement>();
	protected final int width, height;
	protected final BackgroundSkin skin;
	
	public GUIBackgroundProvider(BackgroundSkin skin, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.skin = skin;
		addComponents();
	}
	
	public GUIBackgroundProvider(int width, int height)
	{
		this(BackgroundSkin.defualt, width, height);
	}
	
	protected abstract void addComponents();

	@Override
	public final int getHeight()
	{
		return height;
	}

	@Override
	public final int getWidth()
	{
		return width;
	}

	@Override
	public final int getY()
	{
		return 0;
	}

	@Override
	public final int getX()
	{
		return 0;
	}

	@Override
	public void drawBackground()
	{
		for (IGuiElement el : components)
		{
			el.drawBackground();
		}
	}

	@Override
	public void drawForeground()
	{
		for (IGuiElement el : components)
		{
			el.drawForeground();
		}
	}
	
}
