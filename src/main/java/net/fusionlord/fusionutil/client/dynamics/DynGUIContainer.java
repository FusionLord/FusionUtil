package net.fusionlord.fusionutil.client.dynamics;

import net.fusionlord.fusionutil.client.dynamics.backgrounds.GUIBackgroundProvider;
import net.fusionlord.fusionutil.client.dynamics.backgrounds.SimpleRectangleBackground;
import net.fusionlord.fusionutil.client.dynamics.elements.IGuiElement;
import net.fusionlord.fusionutil.client.dynamics.elements.SimpleDrawingElement;
import net.fusionlord.fusionutil.client.dynamics.elements.SlotElement;
import net.fusionlord.fusionutil.client.dynamics.skins.BackgroundSkin;
import net.fusionlord.fusionutil.client.dynamics.widgets.IWidget;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sample implementation of a dynamic gui around a container with no special dynamic GUI support
 *
 * @param <T> Either Container, or a type that extends it; use something specific if
 *            you want tighter integration between your GUI elements and the container
 * @author FireStorm, FusionLord
 */
public abstract class DynGUIContainer<T extends Container> extends GuiContainer
{
	protected final T container;
	final BackgroundSkin skin;
	protected int guiWidth, guiHeight;
	protected List<IGuiElement> elements;

	private GUIBackgroundProvider background;

	public DynGUIContainer(T container)
	{
		this(container, BackgroundSkin.defualt);
	}

	public DynGUIContainer(T container, BackgroundSkin skin)
	{
		super(container);
		this.container = container;
		this.skin = skin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		elements = new ArrayList<>();
		elements.addAll(((ArrayList<Slot>) container.inventorySlots).stream().map(slot -> new SlotElement(slot, skin)).collect(Collectors.toList()));

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
		this.xSize = (guiWidth = newWidth);
		this.ySize = (guiHeight = newHeight);

		guiLeft = width / 2 - xSize / 2;
		guiTop = height / 2 - ySize / 2;

		elements.stream().filter(element -> element instanceof SimpleDrawingElement).forEach(element -> ((SimpleDrawingElement) element).setXY(guiLeft + element.getElementX(), guiTop + element.getElementY()));

		background = getBackgroundProvider();
	}

	public abstract void addInitialElements(List<IGuiElement> elements);

	protected GUIBackgroundProvider getBackgroundProvider()
	{
		return new SimpleRectangleBackground(skin, guiWidth, guiHeight);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{

		elements.forEach(o -> o.drawForeground(mc, mouseX, mouseY));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY)
	{
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
