package net.fusionlord.fusionutil.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public final class PacketHandler
{
	private SimpleNetworkWrapper HANDLER;

	public PacketHandler(String channelName)
	{
		this.HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
	}

	public static void register()
	{
	}

	public Packet getMCPacket(IMessage message)
	{
		return HANDLER.getPacketFrom(message);
	}

	/**
	 * Sends a packet to the server.<br>
	 * Must be called Client side.
	 */
	public void sendToServer(IMessage msg)
	{
		HANDLER.sendToServer(msg);
	}
	
	/**
	 * Sends a packet to all the clients.<br>
	 * Must be called Server side.
	 */
	public void sendToAll(IMessage msg)
	{
		HANDLER.sendToAll(msg);
	}
	
	/**
	 * Send a packet to all players around a specific point.<br>
	 * Must be called Server side.
	 */
	public void sendToAllAround(IMessage msg, NetworkRegistry.TargetPoint point)
	{
		HANDLER.sendToAllAround(msg, point);
	}
	
	/**
	 * Send a packet to a specific player.<br>
	 * Must be called Server side.
	 */
	public void sendTo(IMessage msg, EntityPlayerMP player)
	{
		if (!(player instanceof FakePlayer))
		{
			HANDLER.sendTo(msg, player);
		}
	}
	
	/**
	 * Send a packet to all the players in the specified dimension.<br>
	 * Must be called Server side.
	 */
	public void sendToDimension(IMessage msg, int dimension)
	{
		HANDLER.sendToDimension(msg, dimension);
	}

	public SimpleNetworkWrapper getHandler()
	{
		return HANDLER;
	}
}