package net.fusionlord.fusionutil.client.objLoader.obj;

import net.fusionlord.fusionutil.client.objLoader.IModelCustom;
import net.fusionlord.fusionutil.client.objLoader.IModelCustomLoader;
import net.fusionlord.fusionutil.client.objLoader.ModelFormatException;
import net.minecraft.util.ResourceLocation;

public class ObjModelLoader implements IModelCustomLoader
{

	private static final String[] types = {"obj"};

	@Override
	public String getType()
	{
		return "OBJ model";
	}

	@Override
	public String[] getSuffixes()
	{
		return types;
	}

	@Override
	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
	{
		return new WavefrontObject(resource);
	}
}