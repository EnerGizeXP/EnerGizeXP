package mods.favoslav.energizexp.items

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.gui.tabs.CreativeTab
import net.minecraft.item.Item

class IronGear : Item() {
    private val name = "iron_gear"

    init {
        setRegistryName(name)
        unlocalizedName = EnerGizeXP.translationKey(name)
        creativeTab = CreativeTab
    }
}