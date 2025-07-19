# EnerGizeXP Mod - Developer Integration Guide

## Overview

This guide helps developers integrate with or extend the EnerGizeXP mod. It covers API usage, extension points, and best practices for mod compatibility.

## Table of Contents

1. [Integration Basics](#integration-basics)
2. [Energy System Integration](#energy-system-integration)
3. [Fluid System Integration](#fluid-system-integration)
4. [Custom XP Converters](#custom-xp-converters)
5. [Network Extensions](#network-extensions)
6. [GUI Extensions](#gui-extensions)
7. [Compatibility Development](#compatibility-development)
8. [Best Practices](#best-practices)

## Integration Basics

### Mod Dependencies

Add EnerGizeXP as a dependency in your mod:

```kotlin
// build.gradle.kts
dependencies {
    implementation(files("libs/energizexp-0.1.0.jar"))
}
```

### Runtime Detection

```java
// Check if EnerGizeXP is loaded
if (Loader.isModLoaded("energizexp")) {
    // Integration code here
    EnerGizeXPIntegration.initialize();
}
```

### Accessing Core Components

```kotlin
// Import registry handler
import mods.favoslav.energizexp.registry.RegistryHandler

// Access converter blocks
val basicConverter = RegistryHandler.xpconvBasic
val advancedConverter = RegistryHandler.xpconvAdvanced
val eliteConverter = RegistryHandler.xpconvElite

// Access items
val leadIngot = RegistryHandler.leadIngot
val ironGear = RegistryHandler.ironGear
```

## Energy System Integration

### Reading Energy from Converters

```kotlin
import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity
import net.minecraftforge.energy.CapabilityEnergy

fun readEnergyFromConverter(world: World, pos: BlockPos): Int {
    val tileEntity = world.getTileEntity(pos) as? XpConvTileEntity ?: return 0
    val energyCap = tileEntity.getCapability(CapabilityEnergy.ENERGY, null)
    return energyCap?.energyStored ?: 0
}
```

### Providing Energy to Converters

```kotlin
import net.minecraftforge.energy.IEnergyStorage

fun provideEnergyToConverter(world: World, pos: BlockPos, amount: Int): Int {
    val tileEntity = world.getTileEntity(pos) as? XpConvTileEntity ?: return 0
    val energyCap = tileEntity.getCapability(CapabilityEnergy.ENERGY, null) as? IEnergyStorage
    return energyCap?.receiveEnergy(amount, false) ?: 0
}
```

### Custom Energy Providers

```kotlin
class CustomEnergyProvider : TileEntity(), IEnergyStorage {
    private var energyStored = 0
    private val maxEnergy = 100000
    
    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int = 0
    
    override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
        val energyExtracted = energyStored.coerceAtMost(maxExtract)
        if (!simulate) {
            energyStored -= energyExtracted
            markDirty()
        }
        return energyExtracted
    }
    
    override fun getEnergyStored(): Int = energyStored
    override fun getMaxEnergyStored(): Int = maxEnergy
    override fun canExtract(): Boolean = true
    override fun canReceive(): Boolean = false
    
    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing)
    }
    
    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == CapabilityEnergy.ENERGY) {
            CapabilityEnergy.ENERGY.cast(this)
        } else {
            super.getCapability(capability, facing)
        }
    }
}
```

## Fluid System Integration

### Reading XP Fluid from Converters

```kotlin
import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity
import net.minecraftforge.fluids.capability.CapabilityFluidHandler

fun readXpFluidFromConverter(world: World, pos: BlockPos): Int {
    val tileEntity = world.getTileEntity(pos) as? XpConvTileEntity ?: return 0
    val fluidCap = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)
    return fluidCap?.getTankProperties()?.get(0)?.contents?.amount ?: 0
}
```

### Extracting XP Fluid

```kotlin
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

fun extractXpFluid(world: World, pos: BlockPos, maxAmount: Int): FluidStack? {
    val tileEntity = world.getTileEntity(pos) as? XpConvTileEntity ?: return null
    val fluidCap = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)
    return fluidCap?.drain(maxAmount, true)
}
```

### Custom XP Fluid Support

```kotlin
import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity.XpFluidTank

// Register your custom XP fluid
fun registerCustomXpFluid() {
    val customXpFluid = object : Fluid("custom_xp",
        ResourceLocation("mymod", "blocks/custom_xp_still"),
        ResourceLocation("mymod", "blocks/custom_xp_flow")
    ) {
        override fun getColor(fluidStack: FluidStack?): Int = 0xFF00FF00.toInt() // Green
    }
    
    customXpFluid.density = 1000
    customXpFluid.viscosity = 1500
    customXpFluid.luminosity = 5
    FluidRegistry.registerFluid(customXpFluid)
    
    // The XpFluidTank will automatically accept fluids in acceptedRegistryNames
    // Add your fluid name to that set if you want automatic compatibility
}
```

### Custom Fluid Handlers

```kotlin
class CustomXpTank(capacity: Int) : FluidTank(capacity) {
    override fun canFillFluidType(fluid: FluidStack): Boolean {
        // Check if this is an XP-compatible fluid
        return XpFluidTank.acceptedRegistryNames.contains(fluid.fluid.name) ||
               fluid.fluid.name == "custom_xp"
    }
    
    fun convertToXpPoints(): Int {
        return fluidAmount / XpFluidTank.XP_MB_PER_POINT
    }
}
```

## Custom XP Converters

### Creating a Custom Converter Block

```kotlin
class CustomXpConverterBlock : Block(Material.ROCK), ITileEntityProvider {
    
    init {
        setRegistryName("custom_xp_converter")
        unlocalizedName = "mymod.custom_xp_converter"
        setCreativeTab(CreativeTabs.REDSTONE)
    }
    
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
        return CustomXpConverterTileEntity()
    }
    
    override fun onBlockActivated(
        worldIn: World, pos: BlockPos, state: IBlockState,
        playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing,
        hitX: Float, hitY: Float, hitZ: Float
    ): Boolean {
        if (!worldIn.isRemote) {
            // Open custom GUI
            playerIn.openGui(MyMod.INSTANCE, GUI_ID, worldIn, pos.x, pos.y, pos.z)
        }
        return true
    }
}
```

### Custom Converter Tile Entity

```kotlin
class CustomXpConverterTileEntity : TileEntity(), ITickable {
    private var energyStored = 0
    private var xpStored = 0
    private val maxEnergy = 50000
    private val maxXp = 1000
    private val energyPerCycle = 400
    private val xpPerCycle = 3
    
    private val energyStorage = object : IEnergyStorage {
        override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
            val energyReceived = (maxEnergy - energyStored).coerceAtMost(maxReceive)
            if (!simulate) {
                energyStored += energyReceived
                markDirty()
            }
            return energyReceived
        }
        
        override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int = 0
        override fun getEnergyStored(): Int = energyStored
        override fun getMaxEnergyStored(): Int = maxEnergy
        override fun canExtract(): Boolean = false
        override fun canReceive(): Boolean = true
    }
    
    override fun update() {
        if (world != null && !world.isRemote) {
            // Convert energy to XP
            if (energyStored >= energyPerCycle && xpStored < maxXp) {
                energyStored -= energyPerCycle
                xpStored += xpPerCycle
                markDirty()
            }
        }
    }
    
    fun extractXpToPlayer(player: EntityPlayer) {
        if (xpStored > 0) {
            player.addExperience(xpStored)
            xpStored = 0
            markDirty()
        }
    }
    
    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing)
    }
    
    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == CapabilityEnergy.ENERGY) {
            CapabilityEnergy.ENERGY.cast(energyStorage)
        } else {
            super.getCapability(capability, facing)
        }
    }
}
```

## Network Extensions

### Custom Network Packets

```kotlin
import mods.favoslav.energizexp.network.NetworkHandler

class CustomXpPacket(var customData: String = "") : IMessage {
    
    override fun toBytes(buf: ByteBuf) {
        val bytes = customData.toByteArray(Charsets.UTF_8)
        buf.writeInt(bytes.size)
        buf.writeBytes(bytes)
    }
    
    override fun fromBytes(buf: ByteBuf) {
        val length = buf.readInt()
        val bytes = ByteArray(length)
        buf.readBytes(bytes)
        customData = String(bytes, Charsets.UTF_8)
    }
}

class CustomXpPacketHandler : IMessageHandler<CustomXpPacket, IMessage> {
    override fun onMessage(message: CustomXpPacket, ctx: MessageContext): IMessage? {
        val player = ctx.serverHandler.player
        // Handle packet on server side
        return null
    }
}

// Register with EnerGizeXP network channel
fun registerCustomPackets() {
    NetworkHandler.CHANNEL.registerMessage(
        CustomXpPacketHandler::class.java,
        CustomXpPacket::class.java,
        100, // Use ID > 50 to avoid conflicts
        Side.SERVER
    )
}
```

### Extending Existing Packets

```kotlin
import mods.favoslav.energizexp.network.XpConvExtractPacket

// Custom packet extending existing functionality
class EnhancedXpExtractPacket(
    pos: BlockPos,
    val extractMode: ExtractMode = ExtractMode.ALL
) : XpConvExtractPacket(pos) {
    
    enum class ExtractMode { ALL, HALF, QUARTER }
    
    override fun toBytes(buf: ByteBuf) {
        super.toBytes(buf)
        buf.writeInt(extractMode.ordinal)
    }
    
    override fun fromBytes(buf: ByteBuf) {
        super.fromBytes(buf)
        extractMode = ExtractMode.values()[buf.readInt()]
    }
}
```

## GUI Extensions

### Custom GUI for XP Systems

```kotlin
class CustomXpGui(playerInv: InventoryPlayer, tile: CustomXpConverterTileEntity) 
    : GuiContainer(CustomXpContainer(playerInv, tile)) {
    
    private val background = ResourceLocation("mymod", "textures/gui/custom_xp_gui.png")
    
    init {
        xSize = 176
        ySize = 166
    }
    
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1f, 1f, 1f, 1f)
        mc.textureManager.bindTexture(background)
        
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
        
        // Draw energy bar
        val energyPercent = tile.energyStored.toFloat() / tile.maxEnergy
        val energyBarHeight = (52 * energyPercent).toInt()
        drawTexturedModalRect(
            x + 8, y + 69 - energyBarHeight,
            176, 52 - energyBarHeight,
            14, energyBarHeight
        )
        
        // Draw XP bar
        val xpPercent = tile.xpStored.toFloat() / tile.maxXp
        val xpBarHeight = (52 * xpPercent).toInt()
        drawTexturedModalRect(
            x + 154, y + 69 - xpBarHeight,
            190, 52 - xpBarHeight,
            14, xpBarHeight
        )
    }
}
```

### JEI Integration

```kotlin
import mezz.jei.api.IModPlugin
import mezz.jei.api.JEIPlugin

@JEIPlugin
class EnerGizeXPJEIPlugin : IModPlugin {
    
    override fun register(registry: IModRegistry) {
        // Add XP conversion recipes to JEI
        registry.addRecipes(createXpConversionRecipes(), "energizexp.xp_conversion")
        
        // Add converter blocks as catalysts
        registry.addRecipeCatalyst(ItemStack(RegistryHandler.xpconvBasic), "energizexp.xp_conversion")
        registry.addRecipeCatalyst(ItemStack(RegistryHandler.xpconvAdvanced), "energizexp.xp_conversion")
        registry.addRecipeCatalyst(ItemStack(RegistryHandler.xpconvElite), "energizexp.xp_conversion")
    }
    
    private fun createXpConversionRecipes(): List<XpConversionRecipe> {
        return listOf(
            XpConversionRecipe(300, 2, "Basic Converter"),
            XpConversionRecipe(600, 4, "Advanced Converter"),
            XpConversionRecipe(1200, 8, "Elite Converter")
        )
    }
}
```

## Compatibility Development

### Creating Compatibility Modules

```kotlin
object MyModCompatibility {
    
    @JvmStatic
    fun initialize() {
        if (Loader.isModLoaded("energizexp")) {
            registerCustomRecipes()
            registerFluidCompatibility()
        }
    }
    
    @JvmStatic
    private fun registerCustomRecipes() {
        // Add custom smelting recipes
        GameRegistry.addSmelting(
            MyMod.customOre,
            ItemStack(RegistryHandler.leadIngot, 2),
            1.0f
        )
    }
    
    @JvmStatic
    private fun registerFluidCompatibility() {
        // Register custom XP fluid compatibility
        val customXpFluid = FluidRegistry.getFluid("my_custom_xp")
        if (customXpFluid != null) {
            // Add to accepted registry names (requires reflection or PR)
            // Alternative: create custom fluid handler
        }
    }
}
```

### Cross-Mod Energy Bridges

```kotlin
class EnergyBridge : TileEntity() {
    private val energyBuffers = mutableMapOf<EnumFacing, IEnergyStorage>()
    
    override fun update() {
        if (world != null && !world.isRemote) {
            // Distribute energy to connected XP converters
            for (facing in EnumFacing.values()) {
                val pos = getPos().offset(facing)
                val tile = world.getTileEntity(pos)
                
                if (tile is XpConvTileEntity) {
                    val energyCap = tile.getCapability(CapabilityEnergy.ENERGY, facing.opposite)
                    energyCap?.let { storage ->
                        val transferred = transferEnergy(storage, 1000)
                        if (transferred > 0) {
                            markDirty()
                        }
                    }
                }
            }
        }
    }
    
    private fun transferEnergy(target: IEnergyStorage, maxAmount: Int): Int {
        // Implementation depends on your energy source
        return 0
    }
}
```

## Best Practices

### Performance Optimization

```kotlin
// Cache tile entity references
class OptimizedXpSystem {
    private val converterCache = mutableMapOf<BlockPos, XpConvTileEntity>()
    private var lastCacheUpdate = 0L
    
    fun getConverter(world: World, pos: BlockPos): XpConvTileEntity? {
        val currentTime = world.totalWorldTime
        
        // Update cache every 20 ticks
        if (currentTime - lastCacheUpdate > 20) {
            updateCache(world)
            lastCacheUpdate = currentTime
        }
        
        return converterCache[pos]
    }
    
    private fun updateCache(world: World) {
        converterCache.clear()
        // Repopulate cache with valid converters
    }
}
```

### Error Handling

```kotlin
fun safelyAccessConverter(world: World, pos: BlockPos): Boolean {
    return try {
        val tile = world.getTileEntity(pos) as? XpConvTileEntity
        tile?.let { converter ->
            // Perform operations
            true
        } ?: false
    } catch (e: Exception) {
        MyMod.logger.error("Error accessing XP converter at $pos", e)
        false
    }
}
```

### Version Compatibility

```kotlin
object VersionCompatibility {
    
    fun isCompatibleVersion(): Boolean {
        val energizeXpMod = Loader.instance().indexedModList["energizexp"]
        return energizeXpMod?.version?.let { version ->
            // Check if version is compatible
            version.startsWith("0.1.")
        } ?: false
    }
    
    fun getApiLevel(): Int {
        // Return API level for feature detection
        return if (isCompatibleVersion()) 1 else 0
    }
}
```

### Configuration Integration

```kotlin
// config/energizexp_integration.cfg
class IntegrationConfig {
    
    @Config.Comment("Enable custom XP fluid compatibility")
    var enableCustomFluidCompat = true
    
    @Config.Comment("Energy conversion multiplier for integration")
    var energyMultiplier = 1.0
    
    @Config.Comment("Maximum converters per chunk")
    var maxConvertersPerChunk = 16
}
```

### Testing Integration

```kotlin
// Test class for integration testing
class EnerGizeXPIntegrationTest {
    
    fun testEnergyTransfer() {
        // Create test world and converter
        val testWorld = createTestWorld()
        val converterPos = BlockPos(0, 64, 0)
        
        // Place converter
        testWorld.setBlockState(converterPos, RegistryHandler.xpconvBasic.defaultState)
        
        val converter = testWorld.getTileEntity(converterPos) as XpConvTileEntity
        
        // Test energy input
        val energyCap = converter.getCapability(CapabilityEnergy.ENERGY, null)
        val transferred = energyCap?.receiveEnergy(1000, false) ?: 0
        
        assert(transferred > 0) { "Energy transfer failed" }
        assert(converter.energyLevel == transferred) { "Energy not stored correctly" }
    }
    
    fun testXpConversion() {
        // Test XP conversion logic
        // Add energy, wait for cycles, check XP output
    }
}
```

## Common Integration Patterns

### Event-Based Integration

```kotlin
@SubscribeEvent
fun onXpConverterUpdate(event: TickEvent.WorldTickEvent) {
    if (event.phase == TickEvent.Phase.END && !event.world.isRemote) {
        // Process custom integration logic every world tick
        processCustomXpLogic(event.world)
    }
}

@SubscribeEvent  
fun onBlockPlaced(event: BlockEvent.PlaceEvent) {
    val block = event.placedBlock.block
    if (block is XpConvBlock) {
        // Handle XP converter placement
        onXpConverterPlaced(event.world, event.pos)
    }
}
```

### Data-Driven Configuration

```kotlin
// JSON configuration for custom converter recipes
data class CustomConverterRecipe(
    val energyCost: Int,
    val xpOutput: Int,
    val processingTime: Int,
    val tier: String
)

object RecipeLoader {
    fun loadCustomRecipes(): List<CustomConverterRecipe> {
        val configFile = File("config/energizexp_recipes.json")
        return if (configFile.exists()) {
            Gson().fromJson(configFile.readText(), Array<CustomConverterRecipe>::class.java).toList()
        } else {
            emptyList()
        }
    }
}
```

This developer guide provides comprehensive information for integrating with and extending the EnerGizeXP mod. It covers all major integration points and provides practical examples for common use cases.