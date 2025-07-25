package mods.favoslav.energizexp.proxy

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader

@Suppress("unused")
class ClientProxy : Proxy() {

    override fun registerItemRenderer(item: Item, metadata: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(
            item,
            metadata,
            ModelResourceLocation(item.registryName!!, id)
        )
    }

}
