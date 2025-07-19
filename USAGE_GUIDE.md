# EnerGizeXP Mod - Usage Guide

## Quick Start

### Installation

1. Download and install Minecraft Forge 1.12.2-14.23.5.2860 or later
2. Place the EnerGizeXP mod jar file in your `mods` folder
3. Launch Minecraft

### Getting Started

#### Step 1: Obtain Lead
- Mine lead ore found naturally in the world
- Smelt lead ore in a furnace to get lead ingots
- Or use Mekanism's enrichment chamber for 2x lead dust, then smelt the dust

#### Step 2: Craft Your First XP Converter
- Basic XP Converter is the entry-level machine
- Requires energy input (RF/FE compatible)
- Stores up to 400 XP and 25,000 RF

#### Step 3: Set Up Power
- Connect any RF/FE energy source (like thermal generators, solar panels, etc.)
- The machine will start converting energy to XP automatically
- Higher tier machines are more efficient per tick but consume more energy per cycle

#### Step 4: Extract XP
- Right-click the converter to open its GUI
- Click the XP extraction button to get all stored XP
- Or connect XP fluid pipes to automatically extract XP as fluid

## Machine Tiers Comparison

### Basic XP Converter
- **Energy Storage:** 25,000 RF
- **XP Storage:** 400 XP
- **Energy per Cycle:** 300 RF
- **XP per Cycle:** 2 XP
- **Best for:** Early game, low power setups

### Advanced XP Converter
- **Energy Storage:** 40,000 RF  
- **XP Storage:** 800 XP
- **Energy per Cycle:** 600 RF
- **XP per Cycle:** 4 XP
- **Best for:** Mid-game automation

### Elite XP Converter
- **Energy Storage:** 55,000 RF
- **XP Storage:** 1,600 XP  
- **Energy per Cycle:** 1,200 RF
- **XP per Cycle:** 8 XP
- **Best for:** End-game mass XP production

## Setup Examples

### Basic Setup
```
[Energy Source] → [Energy Cable] → [Basic XP Converter]
                                         ↓
                                   [Player Extract]
```

### Automated XP Farm
```
[Solar Panel] → [Energy Conduit] → [Elite XP Converter]
                                         ↓
[XP Tank] ← [Fluid Pipe] ← [XP Converter]
    ↓
[Experience Obelisk/XP Shower]
```

### Mekanism Integration
```
[Lead Ore] → [Enrichment Chamber] → [Lead Dust (x2)]
                                          ↓
                                    [Furnace] → [Lead Ingot (x2)]
```

## Fluid Integration

### Compatible XP Fluids
The mod accepts these XP fluid types:
- `liquid_xp` (OpenBlocks)
- `xpjuice` (Cyclic)
- `essence` (Mob Grinding Utils)
- `mobessence` (MineFactory Reloaded)
- `experience` (EnderIO)
- `fluid_experience` (Reliquary)

### Fluid Conversion Rate
- **1 XP Point = 20 mB XP Fluid**
- Connect fluid pipes to automatically extract XP as liquid

### Example Fluid Setup
```
[XP Converter] → [Fluid Pipe] → [XP Tank] → [Fluid Pipe] → [Auto-Spawner]
```

## Energy Integration

### Supported Energy Systems
The mod supports all Forge Energy (FE) compatible systems:
- **Thermal Expansion** (RF)
- **EnderIO** (µI)
- **Mekanism** (J - Joules)
- **Industrial Craft 2** (EU via converters)
- **Applied Energistics 2** (AE)

### Power Requirements
- **Efficiency:** All tiers use exactly 150 RF per XP point
- **Speed:** Higher tiers process more XP per cycle
- **Buffers:** Larger energy storage prevents interruptions

## GUI Guide

### Main Interface Elements

1. **Energy Bar (Left):** Shows stored energy with tooltip
2. **XP Bar (Right):** Shows stored XP with tooltip  
3. **Progress Circle (Center):** Animated conversion indicator
4. **Extract Button:** Click to extract all XP to your player
5. **Inventory:** Your player inventory for easy access

### Status Indicators

- **Green Circle:** Machine actively converting
- **Yellow Circle:** Machine has energy but XP tank is full
- **Red Circle:** Machine has no energy
- **Static Circle:** Conversion complete or paused

## Automation Tips

### Energy Management
- Use energy storage buffers to prevent conversion interruptions
- Higher capacity energy systems work better with elite converters
- Consider renewable energy for continuous operation

### XP Distribution
- Connect multiple converters to the same XP tank for higher throughput
- Use XP fluid distribution networks for base-wide XP access
- Combine with mob farms for complete automation

### Efficiency Optimization
- Elite converters produce 4x more XP per tick than basic
- Place converters near your main base for easy access
- Use energy conduits with high transfer rates for best performance

## Common Issues & Solutions

### Machine Not Working
- **Check Energy:** Ensure adequate power supply
- **Check XP Storage:** Tank might be full
- **Check Connections:** Verify energy cables are connected properly

### Low Efficiency
- **Upgrade Tiers:** Higher tier machines are more efficient per tick
- **Power Issues:** Insufficient energy delivery slows conversion
- **Network Lag:** Too many machines can cause server lag

### XP Not Extracting
- **Manual Extraction:** Right-click and use extract button
- **Fluid Extraction:** Connect fluid pipes to output XP as liquid
- **Tank Full:** XP tank might be at capacity

## Advanced Usage

### Scaling Production
For massive XP production:
1. Build arrays of Elite XP Converters
2. Use high-capacity energy generation (nuclear, solar farms)
3. Connect to centralized XP storage systems
4. Distribute via fluid networks

### Integration with Other Mods

#### With Mekanism
- Use enrichment chambers to double lead ore yield
- Smelt lead dust for extra lead ingots
- Connect to Mekanism's energy network

#### With Thermal Expansion
- Use thermal generators for energy
- Connect with thermal dynamics conduits
- Store XP in portable tanks

#### With EnderIO
- Use EnderIO capacitor banks for energy storage
- Connect with EnderIO conduits
- Use XP vacuum for player collection

### Performance Considerations
- Limit concurrent converters on servers
- Use chunk loaders sparingly
- Monitor energy network capacity
- Consider alternative XP sources for balance

## Recipes & Materials

### Lead Processing
```
Lead Ore → [Furnace] → Lead Ingot (0.7 XP)
Lead Ore → [Enrichment Chamber] → 2x Lead Dust → [Furnace] → 2x Lead Ingot
```

### Machine Crafting
*Note: Actual crafting recipes would be defined in the mod's recipe files*

Basic components needed:
- Lead ingots
- Iron gears  
- Machine casings
- Standard Minecraft materials (iron, redstone, etc.)

## Troubleshooting

### Performance Issues
- Reduce the number of active converters
- Ensure adequate server resources
- Check for energy network bottlenecks

### Compatibility Problems
- Verify mod versions match
- Check for conflicting energy systems
- Update to latest mod versions

### XP Loss
- Extract XP regularly to prevent overflow
- Use XP storage systems with large capacity
- Monitor conversion rates vs. usage

## Tips for Modpack Creators

### Balance Considerations
- Adjust energy costs in config files
- Consider alternative recipes for progression
- Balance against other XP sources

### Integration Recommendations
- Include compatible energy mods
- Add XP storage/distribution mods
- Consider mob farming mods for complete automation

### Server Settings
- Monitor performance with multiple converters
- Set reasonable limits on converter arrays
- Consider energy network capacity limits

## Community Resources

### Getting Help
- Check mod documentation
- Review GitHub issues
- Ask in Minecraft Forge forums
- Consult modding communities

### Contributing
- Report bugs with detailed information
- Suggest features through proper channels
- Help others in community forums
- Share creative automation setups