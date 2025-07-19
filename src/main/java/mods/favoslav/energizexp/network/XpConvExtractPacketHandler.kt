package mods.favoslav.energizexp.network

import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class XpConvExtractPacketHandler
    : IMessageHandler<XpConvExtractPacket, IMessage> {
    override fun onMessage(msg: XpConvExtractPacket, ctx: MessageContext): IMessage? {
        val player = ctx.serverHandler.player
        player.serverWorld.addScheduledTask {
            val tileEntity = player.entityWorld.getTileEntity(msg.getPos()) as? XpConvTileEntity ?: return@addScheduledTask
            tileEntity.extractXpToPlayer(player)
        }
        return null
    }
}
