package net.fusionlord.fusionutil.client.dynamics;

import net.fusionlord.fusionutil.client.dynamics.backgrounds.GUIBackgroundProvider;
import net.fusionlord.fusionutil.client.dynamics.backgrounds.SimpleRectangleBackground;
import net.fusionlord.fusionutil.client.dynamics.elements.IGuiElement;
import net.fusionlord.fusionutil.client.dynamics.elements.SimpleDrawingElement;
import net.fusionlord.fusionutil.client.dynamics.skins.BackgroundSkin;
import net.fusionlord.fusionutil.client.dynamics.widgets.IWidget;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample implementation of a dynamic gui around a container with no special dynamic GUI support
 *
 * @author FireStorm, FusionLord
 */
public abstract class DynGUIScreen extends GuiScreen
{
	final BackgroundSkin skin;
	protected EntityPlayer player;
	protected int guiWidth, guiHeight;
	protected int xSize, ySize, guiLeft, guiTop;

	protected List<IGuiElement> elements;

	private GUIBackgroundProvider background;
	private boolean center;

	public DynGUIScreen(EntityPlayer player, int x, int y)
	{
		this(player);
		center = false;
		guiLeft = x;
		guiTop = y;
	}

	public DynGUIScreen(EntityPlayer player, BackgroundSkin skin, int x, int y)
	{
		this(player, skin);
		center = false;
		guiLeft = x;
		guiTop = y;
	}

	public DynGUIScreen(EntityPlayer player)
	{
		this(player, BackgroundSkin.defualt);
	}

	public DynGUIScreen(EntityPlayer player, BackgroundSkin skin)
	{
		super();
		center = true;
		this.player = player;
		this.skin = skin;
	}

	@Override
	public void initGui()
	{
		elements = new ArrayList<>();

		addInitialElements(elements);

		int newWidth = 0;
		int newHeight = 0;

		//dynamically resize the background to fit all elements
		for (IGuiElement element : elements)
		{
			if (element.getElementX() + element.getElementWidth() + 7 > newWidth)
			{
				newWidth = element.getElementX() + element.getElementWidth() + 7;
			}
			if (element.getElementY() + element.getElementHeight() + 7 > newHeight)
			{
				newHeight = element.getElementY() + element.getElementHeight() + 7;
			}
		}
		xSize = (guiWidth = newWidth);
		ySize = (guiHeight = newHeight);
		if (center)
		{
			guiLeft = width / 2 - xSize / 2;
			guiTop = height / 2 - ySize / 2;
		}

		elements.stream().filter(element -> element instanceof SimpleDrawingElement).forEach(element -> ((SimpleDrawingElement) element).setXY(guiLeft + element.getElementX(), guiTop + element.getElementY()));

		background = getBackgroundProvider();
	}

	protected abstract void addInitialElements(List<IGuiElement> elements);

	protected GUIBackgroundProvider getBackgroundProvider()
	{
		return new SimpleRectangleBackground(skin, guiWidth, guiHeight);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawGuiBackgroundLayer(mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawGuiForegroundLayer(mouseX, mouseY);
	}

	/***
	 * @param mouseX MousePosition X
	 * @param mouseY MousePosition Y
	 */
	protected void drawGuiForegroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		elements.forEach(o -> o.drawForeground(mc, mouseX, mouseY));
	}

	protected void drawGuiBackgroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft, guiTop, 0);
		background.drawBackground(mc, mouseX, mouseY);
		background.drawForeground(mc, mouseX, mouseY);
		GlStateManager.translate(-guiLeft, -guiTop, 0);
		GlStateManager.popMatrix();
		elements.forEach(o -> o.drawBackground(mc, mouseX, mouseY));
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		elements.stream().filter(element -> element instanceof IWidget).forEach(element -> ((IWidget) element).update());
	}
}
