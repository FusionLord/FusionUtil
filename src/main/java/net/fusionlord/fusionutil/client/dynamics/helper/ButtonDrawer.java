package net.fusionlord.fusionutil.client.dynamics.helper;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by FusionLord on 8/12/2015.
 */
public class ButtonDrawer implements IQuadDrawer
{
	GuiButton button;
	float zLayer = 0;

	public ButtonDrawer(GuiButton button)
	{
		this.button = button;
	}

	@Override
	public void draw(int x, int y)
	{
		draw(x, y, button.width, button.height);
	}

	@Override
	public void draw(int x, int y, int width, int height)
	{
		draw(x, y, width, height, 0);
	}

	@Override
	public void draw(int x, int y, int width, int height, float rotation)
	{
//		button.drawButton(Minecraft.getMinecraft(), x, y);
	}

	@Override
	public float getZLayer()
	{
		return zLayer;
	}

	@Override
	public int getWidth()
	{
		return button.width;
	}

	@Override
	public int getHeight()
	{
		return button.height;
	}

	@Override
	public IQuadDrawer setWH(int width, int height)
	{
		button.width = width;
		button.height = height;
		return this;
	}

	@Override
	public IQuadDrawer setZLayer(float z)
	{
		zLayer = z;
		return this;
	}
}
