package net.fusionlord.fusionutil.client.dynamics.helper;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class ColoredQuadDrawer implements IQuadDrawer
{

	final Tessellator tessellator = Tessellator.getInstance();
	public int color0, color1, color2, color3;
	public int width, height;
	float zLevel = 0;

	public ColoredQuadDrawer(int color)
	{
		this(color, 0, 0);
	}

	public ColoredQuadDrawer(int color, int width, int height)
	{
		this(color, color, color, color, width, height);
	}

	public ColoredQuadDrawer(int color0, int color1, int color2, int color3, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.color0 = color0;
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}

	@Override
	public void draw(int x, int y)
	{
		draw(x, y, width, height);
	}

	@Override
	public void draw(int x, int y, int drawWidth, int drawHeight)
	{
		tessellator.getWorldRenderer().startDrawingQuads();

		tessellator.getWorldRenderer().setColorOpaque_I(color0);
		tessellator.getWorldRenderer().addVertex(x, y + drawHeight, zLevel);

		tessellator.getWorldRenderer().setColorOpaque_I(color1);
		tessellator.getWorldRenderer().addVertex(x + drawWidth, y + drawHeight, zLevel);

		tessellator.getWorldRenderer().setColorOpaque_I(color2);
		tessellator.getWorldRenderer().addVertex(x + drawWidth, y, zLevel);

		tessellator.getWorldRenderer().setColorOpaque_I(color3);
		tessellator.getWorldRenderer().addVertex(x, y, zLevel);

		tessellator.draw();
	}

	@Override
	public void draw(int x, int y, int drawWidth, int drawHeight, float rotation)
	{
		GL11.glPushMatrix();
		GL11.glRotatef(rotation, 0, 0, 1);
		draw(x, y, drawWidth, drawHeight);
		GL11.glPopMatrix();
	}

	@Override
	public float getZLayer()
	{
		return zLevel;
	}

	@Override
	public ColoredQuadDrawer setWH(int width, int height)
	{
		this.width = width;
		this.height = height;
		return this;
	}

	@Override
	public IQuadDrawer setZLayer(float z)
	{
		zLevel = z;
		return this;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}
}
