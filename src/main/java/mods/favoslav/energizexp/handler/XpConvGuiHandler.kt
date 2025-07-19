package mods.favoslav.energizexp.handler

import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity
import mods.favoslav.energizexp.blocks.xpconv.XpConvContainer
import mods.favoslav.energizexp.client.gui.XpConvGui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class XpConvGuiHandler : IGuiHandler {

    companion object {
        const val XPCONV_BASIC = 0
        const val XPCONV_ADVANCED = 1
        const val XPCONV_ELITE = 2
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile = world.getTileEntity(BlockPos(x, y, z))
        return when (ID) {
            XPCONV_BASIC, XPCONV_ADVANCED, XPCONV_ELITE ->
                if (tile is XpConvTileEntity) XpConvContainer(player.inventory, tile) else null
            else -> null
        }
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile = world.getTileEntity(BlockPos(x, y, z))
        return when (ID) {
            XPCONV_BASIC, XPCONV_ADVANCED, XPCONV_ELITE ->
                if (tile is XpConvTileEntity) XpConvGui(player.inventory, tile) else null
            else -> null
        }
    }
}
