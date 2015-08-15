package net.fusionlord.fusionutil.client;

import net.fusionlord.fusionutil.client.objLoader.obj.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.lwjgl.opengl.GL11;

public class RenderingUtil
{

	public static void renderWithIcon(WavefrontObject model, TextureAtlasSprite texture, WorldRenderer worldRenderer)
	{
		for (GroupObject go : model.groupObjects)
		{
			for (Face f : go.faces)
			{
				Vertex n = f.faceNormal;
				worldRenderer.setNormal(n.x, n.y, n.z);
				for (int i = 0; i < f.vertices.length; i++)
				{
					Vertex v = f.vertices[i];
					TextureCoordinate t = f.textureCoordinates[i];
					worldRenderer.addVertexWithUV(v.x, v.y, v.z,
							                             texture.getInterpolatedU(t.u * 16),
							                             texture.getInterpolatedV(t.v * 16));
				}
			}
		}
		Tessellator.getInstance().draw();
	}

	public static void renderPartWithIcon(WavefrontObject model, String name, TextureAtlasSprite texture, WorldRenderer worldRenderer, int color)
	{
		for (GroupObject go : model.groupObjects)
		{
			if (go.name.equals(name))
			{
				worldRenderer.startDrawing(GL11.GL_TRIANGLES);
				if (color != -1)
				{
					worldRenderer.setColorOpaque_I(color);
				}
				for (Face f : go.faces)
				{
					Vertex n = f.faceNormal;
					worldRenderer.setNormal(n.x, n.y, n.z);
					for (int i = 0; i < f.vertices.length; i++)
					{
						Vertex v = f.vertices[i];
						TextureCoordinate t = f.textureCoordinates[i];
						worldRenderer.addVertexWithUV(v.x, v.y, v.z,
								                             texture.getInterpolatedU(t.u * 16),
								                             texture.getInterpolatedV(t.v * 16));
					}
				}
				Tessellator.getInstance().draw();
			}
		}
	}
}
