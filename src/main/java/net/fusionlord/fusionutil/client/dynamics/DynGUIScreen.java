package net.fusionlord.fusionutil.client.dynamics;

import net.fusionlord.fusionutil.client.dynamics.backgrounds.GUIBackgroundProvider;
import net.fusionlord.fusionutil.client.dynamics.backgrounds.SimpleRectangleBackground;
import net.fusionlord.fusionutil.client.dynamics.elements.ButtonGuiElement;
import net.fusionlord.fusionutil.client.dynamics.elements.IGuiElement;
import net.fusionlord.fusionutil.client.dynamics.elements.IMinecraftElement;
import net.fusionlord.fusionutil.client.dynamics.skins.BackgroundSkin;
import net.fusionlord.fusionutil.client.dynamics.widgets.IWidget;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

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

	public DynGUIScreen(EntityPlayer player)
	{
		this(player, BackgroundSkin.defualt);
	}

	public DynGUIScreen(EntityPlayer player, BackgroundSkin skin)
	{
		super();
		this.player = player;
		this.skin = skin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		elements = new ArrayList<>();

		addInitialElements(elements);

		int newWidth = 0;
		int newHeight = 0;

		//dynamically resize the background to fit all elements
		for (IGuiElement element : elements)
		{
			if (element.getX() + element.getWidth() + 7 > newWidth)
			{
				newWidth = element.getX() + element.getWidth() + 7;
			}
			if (element.getY() + element.getHeight() + 7 > newHeight)
			{
				newHeight = element.getY() + element.getHeight() + 7;
			}
		}
		this.xSize = (guiWidth = newWidth);
		this.ySize = (guiHeight = newHeight);

		guiLeft = width / 2 - xSize / 2;
		guiTop = height / 2 - ySize / 2;

		elements.stream().filter(element -> element instanceof ButtonGuiElement).forEach(element -> ((IMinecraftElement) element).setLocation(guiLeft, guiTop));

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
		elements.forEach(IGuiElement::drawForeground);
	}

	protected void drawGuiBackgroundLayer(int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft, guiTop, 0);
		background.drawBackground();
		background.drawForeground();
		elements.forEach(IGuiElement::drawBackground);
		GL11.glPopMatrix();
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		elements.stream().filter(element -> element instanceof IWidget).forEach(element -> ((IWidget) element).update());
	}
}
