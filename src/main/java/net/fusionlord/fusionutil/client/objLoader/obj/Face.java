package net.fusionlord.fusionutil.client.objLoader.obj;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Face
{
	public net.fusionlord.fusionutil.client.objLoader.obj.Vertex[] vertices;
	public net.fusionlord.fusionutil.client.objLoader.obj.Vertex[] vertexNormals;
	public net.fusionlord.fusionutil.client.objLoader.obj.Vertex faceNormal;
	public net.fusionlord.fusionutil.client.objLoader.obj.TextureCoordinate[] textureCoordinates;

	@SideOnly(Side.CLIENT)
	public void addFaceForRender(WorldRenderer worldRenderer)
	{
		addFaceForRender(worldRenderer, 0.0005F);
	}

	@SideOnly(Side.CLIENT)
	public void addFaceForRender(WorldRenderer worldRenderer, float textureOffset)
	{
		if (faceNormal == null)
		{
			faceNormal = this.calculateFaceNormal();
		}

		worldRenderer.setNormal(faceNormal.x, faceNormal.y, faceNormal.z);

		float averageU = 0F;
		float averageV = 0F;

		if ((textureCoordinates != null) && (textureCoordinates.length > 0))
		{
			for (net.fusionlord.fusionutil.client.objLoader.obj.TextureCoordinate textureCoordinate : textureCoordinates)
			{
				averageU += textureCoordinate.u;
				averageV += textureCoordinate.v;
			}

			averageU = averageU / textureCoordinates.length;
			averageV = averageV / textureCoordinates.length;
		}

		float offsetU, offsetV;

		for (int i = 0; i < vertices.length; ++i)
		{

			if ((textureCoordinates != null) && (textureCoordinates.length > 0))
			{
				offsetU = textureOffset;
				offsetV = textureOffset;

				if (textureCoordinates[i].u > averageU)
				{
					offsetU = -offsetU;
				}
				if (textureCoordinates[i].v > averageV)
				{
					offsetV = -offsetV;
				}

				worldRenderer.addVertexWithUV(
						                             vertices[i].x,
						                             vertices[i].y,
						                             vertices[i].z,
						                             textureCoordinates[i].u + offsetU,
						                             textureCoordinates[i].v + offsetV
				);
			}
			else
			{
				worldRenderer.addVertex(vertices[i].x, vertices[i].y, vertices[i].z);
			}
		}
	}

	public net.fusionlord.fusionutil.client.objLoader.obj.Vertex calculateFaceNormal()
	{
		Vec3 v1 = new Vec3(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
		Vec3 v2 = new Vec3(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);
		Vec3 normalVector;

		normalVector = v1.crossProduct(v2).normalize();

		return new net.fusionlord.fusionutil.client.objLoader.obj.Vertex((float) normalVector.xCoord, (float) normalVector.yCoord, (float) normalVector.zCoord);
	}
}