# EnerGizeXP Mod - Comprehensive API Documentation

## Overview

EnerGizeXP is a Minecraft Forge mod that converts energy (RF/FE) into experience points using specialized machines. The mod adds three tiers of XP converters along with lead ore generation and processing capabilities. It also provides integration with Mekanism mod for enhanced ore processing.

## Table of Contents

1. [Main Mod Class](#main-mod-class)
2. [Core Components](#core-components)
3. [Block System](#block-system)
4. [Item System](#item-system)
5. [Networking API](#networking-api)
6. [Compatibility System](#compatibility-system)
7. [GUI System](#gui-system)
8. [Proxy System](#proxy-system)
9. [Usage Examples](#usage-examples)

## Main Mod Class

### EnerGizeXP

**Package:** `mods.favoslav.energizexp`

The main mod class that handles initialization and provides utility methods.

#### Constants

```java
public static final String modId = "energizexp"
public static Logger logger
public static EnerGizeXP INSTANCE
public static Proxy proxy
```

#### Public Methods

##### `translationKey(String... path)`

Creates a fully qualified translation key for the mod.

**Parameters:**
- `path` - Variable arguments representing the path elements

**Returns:** String - Fully qualified translation key

**Example:**
```java
String key = EnerGizeXP.translationKey("block", "xpconv_basic");
// Returns: "energizexp.block.xpconv_basic"
```

##### `resource(String path)`

Creates a ResourceLocation for mod assets.

**Parameters:**
- `path` - Path to the resource within the mod's assets

**Returns:** ResourceLocation - Resource location pointing to the given path

**Example:**
```java
ResourceLocation texture = EnerGizeXP.resource("textures/blocks/xpconv_basic.png");
```

## Core Components

### RegistryHandler

**Package:** `mods.favoslav.energizexp.registry`

Central registry for all mod blocks, items, and other game objects.

#### Public Fields

```kotlin
val xpconvBasic: XpConvBlock        // Basic XP Converter
val xpconvAdvanced: XpConvBlock     // Advanced XP Converter  
val xpconvElite: XpConvBlock        // Elite XP Converter
val leadOre: LeadOre                // Lead ore block
val leadIngot: LeadIngotItem        // Lead ingot item
val leadBlock: LeadBlock            // Lead storage block
val machineCasing: MachineCasingBlock // Machine casing block
val ironGear: IronGear              // Iron gear item
```

#### Public Methods

##### `registerRecipes()`

Registers smelting recipes for the mod.

**Usage:**
```kotlin
RegistryHandler.registerRecipes()
```

##### `registerTileEntities()`

Registers tile entities with the game registry.

**Usage:**
```kotlin
RegistryHandler.registerTileEntities()
```

## Block System

### XpConvBlock

**Package:** `mods.favoslav.energizexp.blocks.xpconv`

The main XP converter block that converts energy to experience.

#### Constructor

```kotlin
XpConvBlock(
    name: String,           // Block registry name
    maxXp: Int,            // Maximum XP storage
    maxEnergy: Int,        // Maximum energy storage
    xpPerCycle: Int,       // XP generated per cycle
    energyPerCycle: Int,   // Energy consumed per cycle
    guiId: Int             // GUI identifier
)
```

#### Features

- **Directional Placement:** Faces away from the player when placed
- **GUI Interaction:** Right-click to open interface
- **Tile Entity:** Has associated `XpConvTileEntity` for logic
- **Energy Storage:** Supports Forge Energy (FE/RF) capability
- **Fluid Handling:** Outputs XP as fluid to connected tanks

### XpConvTileEntity

**Package:** `mods.favoslav.energizexp.blocks.xpconv`

The tile entity that handles the core XP conversion logic.

#### Public Properties

```kotlin
val xpFluidTank: XpFluidTank        // XP fluid storage
var maxXp: Int                      // Maximum XP capacity
var maxEnergy: Int                  // Maximum energy capacity
var xpPerCycle: Int                 // XP produced per cycle
var energyPerCycle: Int             // Energy consumed per cycle
var energyLevel: Int                // Current energy level
var cycle: Int                      // Current cycle counter
```

#### Public Methods

##### `getGuiTitle(): String`

Returns the display title for the GUI.

**Returns:** String - GUI title based on converter tier

##### `extractXpToPlayer(player: EntityPlayer)`

Extracts all stored XP and gives it to the specified player.

**Parameters:**
- `player` - The player to receive the XP

**Usage:**
```kotlin
tileEntity.extractXpToPlayer(player)
```

#### Capabilities

- **Energy Storage:** Implements `IEnergyStorage` capability
- **Fluid Handler:** Implements `IFluidHandler` capability for XP fluid

### BlockInfo

**Package:** `mods.favoslav.energizexp.blocks.xpconv`

Enum defining the three tiers of XP converters.

```kotlin
enum class BlockInfo(
    val title: String,      // Registry name
    val maxXp: Int,        // Maximum XP storage
    val maxEnergy: Int,    // Maximum energy storage
    val energyPerCycle: Int, // Energy consumption
    val guild: Int         // Tier level (0-2)
) {
    BASIC("xpconv_basic", 400, 25000, 300, 0),
    ADVANCED("xpconv_advanced", 800, 40000, 600, 1),
    ELITE("xpconv_elite", 1600, 55000, 1200, 2)
}
```

#### Computed Properties

- `xpPerCycle: Int` - Calculated based on tier level

### XpFluidTank

**Package:** `mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity`

Specialized fluid tank for handling XP fluids.

#### Constants

```kotlin
companion object {
    const val XP_MB_PER_POINT = 20  // Millibuckets per XP point
    val acceptedRegistryNames = setOf(
        "liquid_xp", "xpjuice", "essence", 
        "mobessence", "experience", "fluid_experience"
    )
}
```

#### Public Methods

##### `getOrCreateUniversalXpFluid(): Fluid`

Creates or retrieves the universal XP fluid for the mod.

**Returns:** Fluid - The XP fluid instance

##### `canFillFluidType(fluid: FluidStack): Boolean`

Checks if the tank can accept the given fluid type.

**Parameters:**
- `fluid` - The fluid stack to check

**Returns:** Boolean - True if the fluid is accepted

##### `internalFill(resource: FluidStack, doFill: Boolean): Int`

Internal method to fill the tank with XP fluid.

### IHasItemBlock

**Package:** `mods.favoslav.energizexp.blocks`

Interface for blocks that have associated ItemBlocks.

```kotlin
interface IHasItemBlock {
    val itemBlock: ItemBlock
}
```

## Item System

### LeadIngotItem

**Package:** `mods.favoslav.energizexp.items`

Lead ingot item used in crafting and as a product of lead ore smelting.

#### Features

- **Creative Tab:** Appears in mod's creative tab
- **Smelting Product:** Result of smelting lead ore or lead dust

### IronGear

**Package:** `mods.favoslav.energizexp.items`

Iron gear item used in machine crafting recipes.

## Networking API

### NetworkHandler

**Package:** `mods.favoslav.energizexp.network`

Handles client-server communication for the mod.

#### Public Fields

```kotlin
val CHANNEL: SimpleNetworkWrapper  // Network channel for the mod
```

#### Public Methods

##### `registerPackets()`

Registers all network packets with the channel.

**Usage:**
```kotlin
NetworkHandler.registerPackets()
```

### XpConvExtractPacket

**Package:** `mods.favoslav.energizexp.network`

Network packet for extracting XP from converters.

#### Constructor

```kotlin
XpConvExtractPacket(x: Int = 0, y: Int = 0, z: Int = 0)
XpConvExtractPacket(pos: BlockPos)  // Convenience constructor
```

#### Public Methods

##### `getPos(): BlockPos`

Returns the block position from the packet data.

**Returns:** BlockPos - The target block position

## Compatibility System

### MekanismCompatibility

**Package:** `mods.favoslav.energizexp.compatibility`

Provides integration with the Mekanism mod.

#### Public Methods

##### `registerMekanismEnrichmentRecipe()`

Adds enrichment chamber recipe for lead ore processing.

**Usage:**
```kotlin
MekanismCompatibility.registerMekanismEnrichmentRecipe()
```

**Recipe:** Lead Ore → 2x Lead Dust

##### `registerLeadDustSmelting()`

Adds smelting recipe for Mekanism lead dust.

**Usage:**
```kotlin
MekanismCompatibility.registerLeadDustSmelting()
```

**Recipe:** Lead Dust → Lead Ingot

## GUI System

### XpConvGuiHandler

**Package:** `mods.favoslav.energizexp.handler`

Handles GUI creation for XP converters.

#### Constants

```kotlin
companion object {
    const val XPCONV_BASIC = 0      // Basic converter GUI ID
    const val XPCONV_ADVANCED = 1   // Advanced converter GUI ID
    const val XPCONV_ELITE = 2      // Elite converter GUI ID
}
```

#### Public Methods

##### `getServerGuiElement(...): Any?`

Creates server-side GUI element.

##### `getClientGuiElement(...): Any?`

Creates client-side GUI element.

### XpConvGui

**Package:** `mods.favoslav.energizexp.client.gui`

Client-side GUI for XP converters.

#### Features

- **Energy Bar:** Visual representation of stored energy
- **XP Bar:** Visual representation of stored XP
- **Progress Indicator:** Animated circle showing conversion progress
- **Extract Button:** Button to extract XP to player
- **Tooltips:** Hover information for bars and buttons

### XpConvContainer

**Package:** `mods.favoslav.energizexp.blocks.xpconv`

Server-side container for XP converter GUI.

#### Features

- **Player Inventory:** Standard 9x4 player inventory slots
- **No Machine Slots:** Converter operates without input/output slots

### CreativeTab

**Package:** `mods.favoslav.energizexp.gui.tabs`

Custom creative tab for mod items and blocks.

#### Features

- **Custom Icon:** Uses basic XP converter as tab icon
- **Ordered Display:** Shows items in a specific order
- **All Mod Items:** Includes all mod blocks and items

## Proxy System

### Proxy

**Package:** `mods.favoslav.energizexp.proxy`

Abstract proxy class for side-specific implementations.

#### Public Methods

##### `preInit()`

Handles pre-initialization tasks.

**Default Implementation:**
- Registers world generator for lead ore

##### `init()`

Handles initialization tasks (empty by default).

##### `postInit()`

Handles post-initialization tasks (empty by default).

##### `registerItemRenderer(item: Item, metadata: Int, id: String)`

Registers item renderers (client-side only).

## Usage Examples

### Basic XP Converter Setup

```kotlin
// Get the basic XP converter block
val basicConverter = RegistryHandler.xpconvBasic

// Place in world (done by game engine)
// Connect energy source (cables/conduits)
// Machine will automatically convert energy to XP

// Access tile entity
val tileEntity = world.getTileEntity(pos) as? XpConvTileEntity
tileEntity?.let { tile ->
    println("Energy: ${tile.energyLevel}/${tile.maxEnergy}")
    println("XP: ${tile.xpFluidTank.fluidAmount / XpFluidTank.XP_MB_PER_POINT}")
}
```

### Network Packet Usage

```kotlin
// Send XP extraction packet from client
val packet = XpConvExtractPacket(blockPos)
NetworkHandler.CHANNEL.sendToServer(packet)
```

### Custom XP Fluid Integration

```kotlin
// Check if fluid is XP-compatible
val xpFluid = FluidStack(someFluid, 1000)
val canAccept = xpFluidTank.canFillFluidType(xpFluid)

// Get universal XP fluid
val universalXpFluid = XpFluidTank.getOrCreateUniversalXpFluid()
```

### Energy Integration

```kotlin
// Access energy capability
val energyCap = tileEntity.getCapability(CapabilityEnergy.ENERGY, null)
energyCap?.let { energy ->
    val stored = energy.energyStored
    val capacity = energy.maxEnergyStored
    val canReceive = energy.canReceive()
}
```

### Mekanism Integration

```kotlin
// Register compatibility (usually done in mod init)
if (Loader.isModLoaded("mekanism")) {
    MekanismCompatibility.registerLeadDustSmelting()
    MekanismCompatibility.registerMekanismEnrichmentRecipe()
}
```

## Conversion Rates

### XP Converter Tiers

| Tier | Energy Storage | XP Storage | Energy/Cycle | XP/Cycle | Efficiency |
|------|----------------|------------|--------------|----------|------------|
| Basic | 25,000 RF | 400 XP | 300 RF | 2 XP | 150 RF/XP |
| Advanced | 40,000 RF | 800 XP | 600 RF | 4 XP | 150 RF/XP |
| Elite | 55,000 RF | 1,600 XP | 1,200 RF | 8 XP | 150 RF/XP |

### Fluid Conversion

- **1 XP Point = 20 mB XP Fluid**
- **10 Ticks = 1 Conversion Cycle** (0.5 seconds at 20 TPS)

## Block Properties

### XP Converter Blocks

- **Material:** Rock
- **Directional:** Yes (faces away from placer)
- **Tile Entity:** Yes
- **GUI:** Yes
- **Energy:** Input only
- **Fluid:** Output only (XP)

### Lead Blocks

- **Lead Ore:** Generates in world, drops lead ingot when smelted
- **Lead Block:** Storage block for lead ingots
- **Machine Casing:** Crafting component

## World Generation

### Lead Ore Generation

- **Generator:** `WorldGenLeadOre`
- **Registration Priority:** 1
- **Biomes:** All overworld biomes
- **Y-Level:** Standard ore generation range

## Notes

- All XP converters maintain the same energy efficiency (150 RF per XP point)
- Higher tiers process faster but consume more energy per cycle
- The mod automatically detects and integrates with various XP fluid systems
- GUI elements scale properly with different converter tiers
- Network packets are optimized for server performance
- All capability integrations follow Forge standards