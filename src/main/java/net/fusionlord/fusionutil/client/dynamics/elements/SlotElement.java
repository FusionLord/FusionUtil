package net.fusionlord.fusionutil.client.dynamics.elements;

import net.fusionlord.fusionutil.client.dynamics.helper.TexturedQuadDrawer;
import net.fusionlord.fusionutil.client.dynamics.skins.BackgroundSkin;
import net.minecraft.inventory.Slot;

public class SlotElement extends SimpleDrawingElement
{
	private static final int slotSize = 18;
	protected final Slot slot;

	public SlotElement(Slot slot, BackgroundSkin skin)
	{
		super(new TexturedQuadDrawer(skin.texture, 0, slotSize, 11, 11 + slotSize));
		this.x = slot.xDisplayPosition - 1;
		this.y = slot.yDisplayPosition - 1;
		drawer.setWH(slotSize, slotSize);
		this.slot = slot;
	}

	public SlotElement(Slot slot)
	{
		this(slot, BackgroundSkin.defualt);
	}
}
