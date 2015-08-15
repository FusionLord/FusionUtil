package net.fusionlord.fusionutil.client.objLoader.obj;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class GroupObject
{
	public String name;
	public ArrayList<Face> faces = new ArrayList<>();
	public int glDrawingMode;

	public GroupObject()
	{
		this("");
	}

	public GroupObject(String name)
	{
		this(name, -1);
	}

	public GroupObject(String name, int glDrawingMode)
	{
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	@SideOnly(Side.CLIENT)
	public void render()
	{
		if (faces.size() > 0)
		{
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			worldRenderer.startDrawing(glDrawingMode);
			render(worldRenderer);
			Tessellator.getInstance().draw();
		}
	}

	@SideOnly(Side.CLIENT)
	public void render(WorldRenderer worldRenderer)
	{
		if (faces.size() > 0)
		{
			for (Face face : faces)
			{
				face.addFaceForRender(worldRenderer);
			}
		}
	}
}