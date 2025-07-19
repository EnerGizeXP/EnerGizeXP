package mods.favoslav.energizexp.gui.tabs

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.registry.RegistryHandler
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object CreativeTab : CreativeTabs(EnerGizeXP.modId) {
    @SideOnly(Side.CLIENT)
    override fun getTabIconItem(): ItemStack {
        return ItemStack(Item.getItemFromBlock(RegistryHandler.xpconvBasic))
    }

    @SideOnly(Side.CLIENT)
    override fun displayAllRelevantItems(items: NonNullList<ItemStack>) {
        val already = mutableSetOf<Item>()

        val customFirst = listOf(
            RegistryHandler.xpconvBasic,
            RegistryHandler.xpconvAdvanced,
            RegistryHandler.xpconvElite,
            RegistryHandler.leadOre,
            RegistryHandler.leadIngot,
            RegistryHandler.leadBlock
        )

        for (entry in customFirst) {
            when (entry) {
                is Block -> {
                    val item = Item.getItemFromBlock(entry)
                    if (item != net.minecraft.init.Items.AIR) {
                        items.add(ItemStack(item))
                        already.add(item)
                    }
                }
                is Item -> {
                    if (entry != net.minecraft.init.Items.AIR) {
                        items.add(ItemStack(entry))
                        already.add(entry)
                    }
                }
                else -> { /* ignore */ }
            }
        }

        for (entry in Item.REGISTRY) {
            val item = entry as Item
            if (item != net.minecraft.init.Items.AIR && item.creativeTab == this && item !in already) {
                item.getSubItems(this, items)
                already.add(item)
            }
        }
    }

}



