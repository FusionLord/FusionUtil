package net.fusionlord.fusionutil.client.dynamics.backgrounds;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;

public enum DynGuiPart
{
	SLOT,
	FILLER,
	BAR_TOP,
	BAR_LEFT,
	BAR_RIGHT,
	BAR_BOTTOM,
	CORNER_TOP_LEFT,
	CORNER_TOP_RIGHT,
	CORNER_BOTTOM_LEFT,
	CORNER_BOTTOM_RIGHT,
	CORNER_TOP_LEFT_INVERTED,
	CORNER_TOP_RIGHT_INVERTED,
	CORNER_BOTTOM_LEFT_INVERTED,
	CORNER_BOTTOM_RIGHT_INVERTED,;

	int uMin, uMax, vMin, vMax;

	public int getuMin()
	{
		return uMin;
	}

	public int getuMax()
	{
		return uMax;
	}

	public int getvMin()
	{
		return vMin;
	}

	public int getvMax()
	{
		return vMax;
	}

	@SuppressWarnings("boxing")
	public void loadFromJSON()
	{
		JsonParser parser = new JsonParser();
		JsonElement file = parser.parse(new ResourceLocation("lib:textures/gui/gui.json").getResourcePath());
		JsonObject contents = file.getAsJsonObject();
		for (DynGuiPart part : values())
		{
			//JsonObject current = contents.getAsJsonObject(part.name().toLowerCase());
			for (Field field : part.getDeclaringClass().getFields())
			{
				try
				{
					field.set(this, contents.getAsJsonObject(field.getName().toLowerCase()).getAsInt());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
