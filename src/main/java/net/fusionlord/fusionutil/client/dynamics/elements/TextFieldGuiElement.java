package net.fusionlord.fusionutil.client.dynamics.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

/**
 * Created by FusionLord on 8/15/2015.
 */
public class TextFieldGuiElement extends GuiTextField implements IGuiElement, IMinecraftElement
{
	int staticX, staticY;

	public TextFieldGuiElement(int id, FontRenderer fontRendererObj, int x, int y, int width, int height)
	{
		super(id, fontRendererObj, x, y, width, height);
		staticX = x;
		staticY = y;
	}

	@Override
	public void drawBackground()
	{
		drawTextBox();
	}

	@Override
	public void drawForeground() {}

	@Override
	public int getElementWidth()
	{
		return width;
	}

	@Override
	public int getElementHeight()
	{
		return height;
	}

	@Override
	public int getElementX()
	{
		return staticX;
	}

	@Override
	public int getElementY()
	{
		return staticY;
	}

	@Override
	public void setLocation(int guiLeft, int guiTop)
	{
		xPosition = staticX + guiLeft;
		yPosition = staticY + guiTop;
	}
}
