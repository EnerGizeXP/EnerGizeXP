package mods.favoslav.energizexp.compatibility

import mekanism.common.recipe.RecipeHandler
import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.registry.RegistryHandler
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry

object MekanismCompatibility {
    private val mekanismDust = Item.REGISTRY.getObject(ResourceLocation("mekanism", "dust"))

    @JvmStatic
    fun registerMekanismEnrichmentRecipe() {
        if (mekanismDust == null) {
            EnerGizeXP.logger.warn("EnerGizeXP: Lead dust item not found in registry!")
            return
        }
        val inputStack = ItemStack(RegistryHandler.leadOre)
        val outputStack = ItemStack(mekanismDust, 2, 6)

        RecipeHandler.addEnrichmentChamberRecipe(inputStack, outputStack)
        EnerGizeXP.logger.info("EnerGizeXP: Added mekanism enrichment chamber recipe for lead ingot.")
    }

    @JvmStatic
    fun registerLeadDustSmelting() {
        if (mekanismDust == null) {
            EnerGizeXP.logger.warn("EnerGizeXP: Lead dust from Mekanism wasn't found in registry!")
            return
        }
        val outputIngot = ItemStack(RegistryHandler.leadIngot)

        GameRegistry.addSmelting(
            ItemStack(mekanismDust, 1, 6),
            outputIngot,
            0.7f
        )
        EnerGizeXP.logger.info("EnerGizeXP: Smelting lead dust recipe added.")
    }

    @JvmStatic
    @Suppress("unused")
    fun debugMekanismMethods() {
        try {
            val recipeHandlerClass = Class.forName("mekanism.common.recipe.RecipeHandler")
            val methods = recipeHandlerClass.declaredMethods

            EnerGizeXP.logger.debug("=== Mekanism RecipeHandler methods ===")
            for (method in methods) {
                EnerGizeXP.logger.debug("${method.name} - ${method.parameterTypes.contentToString()}")
            }
        } catch (ex: Exception) {
            EnerGizeXP.logger.warn("Debug failed: $ex")
        }
    }

}

