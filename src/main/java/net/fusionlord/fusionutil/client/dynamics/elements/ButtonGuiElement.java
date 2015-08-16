package net.fusionlord.fusionutil.client.dynamics.elements;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by FusionLord on 8/15/2015.
 */
public class ButtonGuiElement extends GuiButton implements IGuiElement, IMinecraftElement
{
	int staticX, staticY;

	public ButtonGuiElement(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean enabled, boolean visible)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		staticX = x;
		staticY = y;
		this.enabled = enabled;
		this.visible = visible;
	}

	@Override
	public void drawBackground()
	{}

	@Override
	public void drawForeground()
	{}

	@Override
	public int getElementHeight()
	{
		return height;
	}

	@Override
	public int getElementWidth()
	{
		return width;
	}

	@Override
	public int getElementX()
	{
		return xPosition;
	}

	@Override
	public int getElementY()
	{
		return yPosition;
	}

	@Override
	public void setLocation(int guiLeft, int guiTop)
	{
		xPosition = staticX + guiLeft;
		yPosition = staticY + guiTop;
	}
}
