package mods.favoslav.energizexp;

import mods.favoslav.energizexp.compatibility.MekanismCompatibility;
import mods.favoslav.energizexp.network.NetworkHandler;
import mods.favoslav.energizexp.proxy.Proxy;
import mods.favoslav.energizexp.registry.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import mods.favoslav.energizexp.handler.XpConvGuiHandler;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@Mod(
    modid = EnerGizeXP.modId,
    name = "EnerGizeXP",
    version = "0.1.0"
)
public class EnerGizeXP {

  public static final String modId = "energizexp";
  public static Logger logger;

  @Mod.Instance(modId)
  public static EnerGizeXP INSTANCE;

  @SidedProxy(
      clientSide = "mods.favoslav.rfxp.proxy.ClientProxy",
      serverSide = "mods.favoslav.rfxp.proxy.DedicatedProxy"
  )
  public static Proxy proxy;

  public EnerGizeXP() {
    INSTANCE = this;
    MinecraftForge.EVENT_BUS.register(RegistryHandler.class);
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    logger.info("Mod {} is now loading!", modId);

    RegistryHandler.registerTileEntities();
    NetworkHandler.registerPackets();

    proxy.preInit();
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new XpConvGuiHandler());
    RegistryHandler.registerRecipes();

    if (Loader.isModLoaded("mekanism")) {
      MekanismCompatibility.registerLeadDustSmelting();
      MekanismCompatibility.registerMekanismEnrichmentRecipe();
      //MekanismCompatibility.debugMekanismMethods();
    }

    proxy.init();
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit();
  }

  /**
   * Get the translation key for the given path.
   *
   * @param path "Path" of the translation key elements.
   * @return Fully qualified translation key string.
   */
  public static String translationKey(@Nonnull final String... path) {
    return modId + "." + String.join(".", path);
  }

  /**
   * Get a resource location for this mod.
   *
   * @param path Path to the resource within this mod's assets.
   * @return ResourceLocation pointing to the given path.
   */
  public static ResourceLocation resource(@Nonnull final String path) {
    return new ResourceLocation(modId, path);
  }

}
