package mods.favoslav.energizexp.blocks.lead

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.blocks.IHasItemBlock
import mods.favoslav.energizexp.gui.tabs.CreativeTab
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemBlock

class LeadBlock : Block(Material.IRON), IHasItemBlock {
    private val name = "lead_block"

    override val itemBlock = ItemBlock(this).apply { setRegistryName(name) }

    init {
        setRegistryName(name)
        unlocalizedName = EnerGizeXP.translationKey(name)
        setHardness(5f)
        setResistance(10f)
        setCreativeTab(CreativeTab)
    }
}
