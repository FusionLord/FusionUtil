package net.fusionlord.fusionutil.client.dynamics.skins;

import net.fusionlord.fusionutil.FusionUtil;
import net.minecraft.util.ResourceLocation;

public class BackgroundSkin
{

	public static final BackgroundSkin defualt =
			new BackgroundSkin(new ResourceLocation(FusionUtil.MODNAME.toLowerCase() + ":textures/gui/gui.png"));
	public final ResourceLocation texture;

	public BackgroundSkin(ResourceLocation compoundTexture)
	{
		this.texture = compoundTexture;
	}

	public BackgroundSkin(String compoundTexture)
	{
		this.texture = new ResourceLocation(compoundTexture);
	}

	public static BackgroundSkin getDefualt()
	{
		return defualt;
	}
}
