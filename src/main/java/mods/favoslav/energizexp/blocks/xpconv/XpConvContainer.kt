package mods.favoslav.energizexp.blocks.xpconv

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

class XpConvContainer(playerInventory: InventoryPlayer, tile: XpConvTileEntity) : Container() {
    init {
        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
            }
        }
        for (col in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, col, 8 + col * 18, 142))
        }
    }

    override fun canInteractWith(player: EntityPlayer): Boolean = true
}


