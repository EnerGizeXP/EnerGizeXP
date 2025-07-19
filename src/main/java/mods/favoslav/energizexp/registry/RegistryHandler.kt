package mods.favoslav.energizexp.registry

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.blocks.xpconv.XpConvBlock
import mods.favoslav.energizexp.blocks.IHasItemBlock
import mods.favoslav.energizexp.blocks.casing.MachineCasingBlock
import mods.favoslav.energizexp.blocks.lead.LeadBlock
import mods.favoslav.energizexp.blocks.lead.LeadOre
import mods.favoslav.energizexp.blocks.xpconv.BlockInfo
import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity
import mods.favoslav.energizexp.items.IronGear
import mods.favoslav.energizexp.items.LeadIngotItem
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Handler to register our set of blocks and items and so forth with Forge.
 */
@Suppress("unused")
object RegistryHandler {

    /**
     * Set of blocks we know of to register.
     */
    val xpconvBasic = BlockInfo.BASIC.let {
        XpConvBlock(it.title, it.maxXp, it.maxEnergy, it.xpPerCycle, it.energyPerCycle, it.guild)
    }
    val xpconvAdvanced = BlockInfo.ADVANCED.let {
        XpConvBlock(it.title, it.maxXp, it.maxEnergy, it.xpPerCycle, it.energyPerCycle, it.guild)
    }
    val xpconvElite = BlockInfo.ELITE.let {
        XpConvBlock(it.title, it.maxXp, it.maxEnergy, it.xpPerCycle, it.energyPerCycle, it.guild)
    }
    val leadOre = LeadOre()
    val leadIngot = LeadIngotItem()
    val leadBlock = LeadBlock()
    val machineCasing = MachineCasingBlock()
    val ironGear = IronGear()

    private val blocks: List<Block> = listOf(
        xpconvBasic,
        xpconvAdvanced,
        xpconvElite,
        leadOre,
        leadBlock,
        machineCasing
    )

    /**
     * Set of items we know of to register.
     */
    private val items: Set<Item> = setOf(
        leadIngot,
        ironGear
    )

    /**
     * Set of ItemBlocks to register.
     */
    private val itemBlocks: List<ItemBlock> = blocks
        .mapNotNull { it as? IHasItemBlock }
        .map { it.itemBlock }

    /**
     * Register our list of items.
     */
    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {
        items.forEach(event.registry::register)
        itemBlocks.forEach(event.registry::register)
    }

    /**
     * Register smelt recipe.
     */

    @JvmStatic
    fun registerRecipes() {
        GameRegistry.addSmelting(
            Item.getItemFromBlock(leadOre),
            ItemStack(leadIngot),
            0.7f
        )
    }

    /**
     * Register our list of blocks.
     */
    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {
        blocks.forEach(event.registry::register)
    }

    @JvmStatic
    fun registerTileEntities() {
        GameRegistry.registerTileEntity(
            XpConvTileEntity::class.java,
            ResourceLocation(EnerGizeXP.modId, "xpconv_tileentity")
        )
    }
    /**
     * Register model renderers for our items and blocks.
     */
    @JvmStatic
    @SubscribeEvent
    fun onModelRegister(event: ModelRegistryEvent) {

        items.forEach {
            EnerGizeXP.proxy.registerItemRenderer(it, 0, "inventory")
        }

        itemBlocks.forEach {
            EnerGizeXP.proxy.registerItemRenderer(it, 0, "inventory")
        }

    }

}