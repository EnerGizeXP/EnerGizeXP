package mods.favoslav.energizexp.items

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.gui.tabs.CreativeTab
import net.minecraft.item.Item

class LeadIngotItem : Item() {
    private val name = "lead_ingot"

    init {
        setRegistryName(name)
        unlocalizedName = EnerGizeXP.translationKey(name)
        creativeTab = CreativeTab
    }
}
