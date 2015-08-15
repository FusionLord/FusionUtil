package net.fusionlord.fusionutil.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractPacketOld
{
	public abstract void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf buffer);

	public abstract void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf buffer);

	public abstract void handleClientSide(EntityPlayer entityPlayer);

	public abstract void handleServerSide(EntityPlayer entityPlayer);
}
