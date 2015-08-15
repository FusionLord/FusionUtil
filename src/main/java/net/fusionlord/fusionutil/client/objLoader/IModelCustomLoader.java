package net.fusionlord.fusionutil.client.objLoader;

import net.minecraft.util.ResourceLocation;

/**
 * Instances of this class act as factories for their model type
 *
 * @author cpw
 */
public interface IModelCustomLoader
{
	/**
	 * Get the main type name for this loader
	 *
	 * @return the type name
	 */
	String getType();

	/**
	 * Get resource suffixes this model loader recognizes
	 *
	 * @return a list of suffixes
	 */
	String[] getSuffixes();

	/**
	 * Load a model INSTANCE from the supplied path
	 *
	 * @param resource The ResourceLocation of the model
	 * @return A model INSTANCE
	 * @throws net.fusionlord.fusionutil.client.objLoader.ModelFormatException if the model format is not correct
	 */
	net.fusionlord.fusionutil.client.objLoader.IModelCustom loadInstance(ResourceLocation resource) throws net.fusionlord.fusionutil.client.objLoader.ModelFormatException;
}