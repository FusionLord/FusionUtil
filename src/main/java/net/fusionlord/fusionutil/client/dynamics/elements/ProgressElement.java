package net.fusionlord.fusionutil.client.dynamics.elements;

import net.fusionlord.fusionutil.client.dynamics.widgets.IWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class ProgressElement implements IWidget
{
	protected static final int emptyColor = 0x777777;
	protected final IProgressElementDataProvider dataProvider;
	protected final ResourceLocation tex;
	protected final TextureManager texMan = Minecraft.getMinecraft().getTextureManager();
	protected final Tessellator tessellator = Tessellator.getInstance();
	protected int x, y, width, height;
	float progress;

	public ProgressElement(IProgressElementDataProvider dataProvider, ResourceLocation tex, int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dataProvider = dataProvider;
		this.tex = tex;
	}

	@Override
	public void update()
	{
		progress = dataProvider.getProgress(this);
	}

	@Override
	public void drawBackground(Minecraft mc, int mouseX, int mouseY)
	{
//        drawStrechedTexture(x, y, u, v, width, height);
//        drawStrechedTexture(x + 1, y + 1, u, v + 1, width - 2, height - 2);
		texMan.bindTexture(tex);
		tessellator.getWorldRenderer().startDrawingQuads();
		tessellator.getWorldRenderer().setColorOpaque_I(emptyColor);
		tessellator.getWorldRenderer().addVertexWithUV(x, y + height, 0, 0, 0);
		tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, 0, 1, 0);
		tessellator.getWorldRenderer().addVertexWithUV(x + width, y, 0, 1, 1);
		tessellator.getWorldRenderer().addVertexWithUV(x, y, 0, 0, 1);
		tessellator.draw();
		
		texMan.bindTexture(tex);
		tessellator.getWorldRenderer().startDrawingQuads();
		tessellator.getWorldRenderer().setColorOpaque_I(0xFFFFFF);
		tessellator.getWorldRenderer().addVertexWithUV(x, y + height, 0, 0, 0);
		tessellator.getWorldRenderer().addVertexWithUV(x + width * progress, y + height, 0, progress, 0);
		tessellator.getWorldRenderer().addVertexWithUV(x + width * progress, y, 0, progress, 1);
		tessellator.getWorldRenderer().addVertexWithUV(x, y, 0, 0, 1);
		tessellator.draw();
	}

	@Override
	public void drawForeground(Minecraft mc, int mouseX, int mouseY)
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
	public int getElementY()
	{
		return y;
	}

	@Override
	public int getElementX()
	{
		return x;
	}
}
