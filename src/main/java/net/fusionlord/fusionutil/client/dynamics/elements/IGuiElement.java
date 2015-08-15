package net.fusionlord.fusionutil.client.dynamics.elements;

public interface IGuiElement
{
	void drawBackground();

	void drawForeground();

	int getHeight();

	int getWidth();

	int getX();

	int getY();
}