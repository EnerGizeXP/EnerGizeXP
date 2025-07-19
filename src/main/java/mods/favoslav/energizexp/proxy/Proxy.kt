package mods.favoslav.energizexp.proxy

import net.minecraft.item.Item
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Proxy for exposing a common set of behaviours with side-specific implementations.
 */
abstract class Proxy {

    /**
     * Run on the mod's PreInit event.
     */
    @Suppress("DEPRECATION")
    open fun preInit() {
        GameRegistry.registerWorldGenerator(mods.favoslav.energizexp.world.WorldGenLeadOre(), 1)
    }

    /**
     * Run on the mod's Init event.
     */
    open fun init() {

    }

    /**
     * Run after mod initialization.
     */
    open fun postInit() {

    }

    /**
     * Register a renderer for the given [Item].
     */
    open fun registerItemRenderer(item: Item, metadata: Int, id: String) {}

}
