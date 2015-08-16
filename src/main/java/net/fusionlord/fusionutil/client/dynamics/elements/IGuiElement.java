package net.fusionlord.fusionutil.client.dynamics.elements;

public interface IGuiElement
{
	void drawBackground();

	void drawForeground();

	int getElementHeight();

	int getElementWidth();

	int getElementX();

	int getElementY();
}