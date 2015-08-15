package net.fusionlord.fusionutil.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.fusionlord.fusionutil.network.packets.AbstractPacketOld;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

@ChannelHandler.Sharable
public class PacketHandlerOld extends MessageToMessageCodec<FMLProxyPacket, AbstractPacketOld>
{
	private final String CHANNEL;
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private LinkedList<Class<? extends AbstractPacketOld>> packets = new LinkedList<>();
	private boolean isPostInitialised = false;

	public PacketHandlerOld(String channel)
	{
		CHANNEL = channel;
	}

	@SideOnly(Side.CLIENT)
	private static EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	public boolean registerPacket(Class<? extends AbstractPacketOld> clazz)
	{
		if (this.packets.size() > 256)
		{
			return false;
		}
		if (this.packets.contains(clazz))
		{
			return false;
		}
		if (this.isPostInitialised)
		{
			return false;
		}
		this.packets.add(clazz);
		return true;
	}

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacketOld abstractPacketOld, List<Object> out) throws Exception
	{
		ByteBuf buffer = Unpooled.buffer();
		Class<? extends AbstractPacketOld> clazz = abstractPacketOld.getClass();
		if (!this.packets.contains(abstractPacketOld.getClass()))
		{
			throw new NullPointerException("No Packet Registered for: " + abstractPacketOld.getClass().getCanonicalName());
		}
		byte discriminator = (byte) this.packets.indexOf(clazz);
		buffer.writeByte(discriminator);
		abstractPacketOld.encodeInto(channelHandlerContext, buffer);
		FMLProxyPacket proxyPacket = new FMLProxyPacket(new PacketBuffer(buffer.copy()), channelHandlerContext.channel().attr(NetworkRegistry.FML_CHANNEL).get());
		out.add(proxyPacket);
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, FMLProxyPacket fmlProxyPacket, List<Object> out) throws Exception
	{
		ByteBuf payload = fmlProxyPacket.payload();
		byte discriminator = payload.readByte();
		Class<? extends AbstractPacketOld> clazz = this.packets.get(discriminator);
		if (clazz == null)
		{
			throw new NullPointerException("No packet registered for discriminator: " + discriminator);
		}

		AbstractPacketOld packet = clazz.newInstance();
		packet.decodeInto(channelHandlerContext, payload.slice());

		EntityPlayer player;
		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
				player = PacketHandlerOld.getClientPlayer();
				packet.handleClientSide(player);
				break;

			case SERVER:
				INetHandler netHandler = channelHandlerContext.channel().attr(NetworkRegistry.NET_HANDLER).get();
				player = ((NetHandlerPlayServer) netHandler).playerEntity;
				packet.handleServerSide(player);
				break;

			default:
		}
		out.add(packet);
	}

	public void initialise()
	{
		this.channels = NetworkRegistry.INSTANCE.newChannel(CHANNEL, this);
	}

	public void postInitialise()
	{
		if (this.isPostInitialised)
		{
			return;
		}

		this.isPostInitialised = true;

		Collections.sort(this.packets, (clazz1, clazz2) -> {
			int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
			if (com == 0)
			{
				com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
			}

			return com;
		});
	}

	public void sendToAll(AbstractPacketOld message)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendTo(AbstractPacketOld message, EntityPlayerMP playerMP)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(playerMP);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToAllAround(AbstractPacketOld message, NetworkRegistry.TargetPoint point)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToDimension(AbstractPacketOld message, Integer dimensionId)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToServer(AbstractPacketOld message)
	{
		this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		this.channels.get(Side.CLIENT).writeAndFlush(message);
	}

	public void registerPackets(Class<? extends AbstractPacketOld>... classes)
	{
		for (Class<? extends AbstractPacketOld> packet : classes)
		{
			registerPacket(packet);
		}
	}
}
