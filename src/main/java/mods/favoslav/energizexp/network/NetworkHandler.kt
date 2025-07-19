// NetworkHandler.kt
package mods.favoslav.energizexp.network

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side
import mods.favoslav.energizexp.EnerGizeXP

object NetworkHandler {
    @JvmField
    val CHANNEL = SimpleNetworkWrapper(EnerGizeXP.modId)

    @JvmStatic
    fun registerPackets() {
        CHANNEL.registerMessage(
            XpConvExtractPacketHandler::class.java,
            XpConvExtractPacket::class.java,
            0,
            Side.SERVER
        )
    }
}