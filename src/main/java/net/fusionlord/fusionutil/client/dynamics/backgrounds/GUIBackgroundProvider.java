package net.fusionlord.fusionutil.client.dynamics.backgrounds;

import net.fusionlord.fusionutil.client.dynamics.elements.IGuiElement;
import net.fusionlord.fusionutil.client.dynamics.skins.BackgroundSkin;
import net.minecraft.client.Minecraft;

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
	public final int getElementHeight()
	{
		return height;
	}

	@Override
	public final int getElementWidth()
	{
		return width;
	}

	@Override
	public final int getElementY()
	{
		return 0;
	}

	@Override
	public final int getElementX()
	{
		return 0;
	}

	@Override
	public void drawBackground(Minecraft mc, int mouseX, int mouseY)
	{
		for (IGuiElement el : components)
		{
			el.drawBackground(mc, mouseX, mouseY);
		}
	}

	@Override
	public void drawForeground(Minecraft mc, int mouseX, int mouseY)
	{
		for (IGuiElement el : components)
		{
			el.drawForeground(mc, mouseX, mouseY);
		}
	}
	
}
