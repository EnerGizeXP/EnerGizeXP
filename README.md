# EnerGizeXP

A Minecraft Forge mod for Minecraft 1.12.2 that adds XP to Energy conversion machines and lead-based materials.

## Overview

EnerGizeXP is a mod that introduces a new way to convert experience points into energy, along with a complete lead material system. The mod features three tiers of XP Converters, each with increasing efficiency and capacity, plus integration with Mekanism for enhanced processing options.

## Features

### XP Converters
The mod adds three tiers of XP Converters that convert experience points into energy:

- **Basic XP Converter**: Entry-level converter with moderate capacity
- **Advanced XP Converter**: Mid-tier converter with improved efficiency  
- **Elite XP Converter**: High-end converter with maximum performance

### Lead Material System
- **Lead Ore**: New ore that generates in the world
- **Lead Ingot**: Crafting material obtained from smelting lead ore
- **Lead Block**: Storage block for lead ingots
- **Machine Casing**: Component used in crafting XP Converters
- **Iron Gear**: Mechanical component for crafting

### Mekanism Integration
When Mekanism is installed, the mod adds:
- Enrichment Chamber recipe for lead ore → lead dust
- Smelting recipe for lead dust → lead ingot

## XP Converter Specifications

| Tier | Max XP Storage | Max Energy Storage | Energy per Cycle | XP per Cycle |
|------|----------------|-------------------|------------------|--------------|
| Basic | 400 XP | 25,000 RF | 300 RF | 2 XP |
| Advanced | 800 XP | 40,000 RF | 600 RF | 6 XP |
| Elite | 1,600 XP | 55,000 RF | 1,200 RF | 12 XP |

## Crafting Recipes

### Basic XP Converter
```
O I O
I C I
O I O
```
- **C**: Machine Casing
- **I**: Iron Ingot  
- **O**: Obsidian

### Advanced XP Converter
```
E S E
G B G
E S E
```
- **B**: Basic XP Converter
- **G**: Gold Block
- **E**: Emerald
- **S**: Blaze Rod

### Elite XP Converter
```
D B D
N C N
D B D
```
- **C**: Advanced XP Converter
- **B**: Beacon
- **D**: Diamond Block
- **N**: Nether Star

### Other Items
- **Machine Casing**: Crafted with iron ingots and redstone
- **Iron Gear**: Crafted with iron ingots in a gear pattern
- **Lead Block**: 3x3 crafting grid of lead ingots
- **Lead Ingot**: Smelted from lead ore

## World Generation

Lead ore generates naturally in the world and can be smelted into lead ingots. The ore follows standard Minecraft generation patterns.

## Compatibility

- **Minecraft**: 1.12.2
- **Forge**: 14.23.5.2860
- **Mekanism**: 1.12.2-9.8.3.390 (optional integration)

## Installation

1. Ensure you have Minecraft 1.12.2 and Forge 14.23.5.2860 installed
2. Download the EnerGizeXP mod JAR file
3. Place the JAR file in your `mods` folder
4. Start Minecraft

## Dependencies

- **Required**: Minecraft Forge 1.12.2-14.23.5.2860
- **Optional**: Mekanism 1.12.2-9.8.3.390 (for enhanced processing)

## Development

This mod is written in Kotlin and uses the Forge modding framework. The source code is available for reference and modification.

### Building from Source

1. Clone the repository
2. Run `./gradlew build` to compile the mod
3. The compiled JAR will be in the `build/libs` directory

## License

This mod is developed by favoslav. Please check the license file for specific terms.

## Support

For issues, questions, or contributions, please refer to the project's issue tracker or contact the developer.

## Version History

- **0.1.0**: Initial release with XP converters and lead material system

---

*EnerGizeXP - Converting experience into power since 2024*
