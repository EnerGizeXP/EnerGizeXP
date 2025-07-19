# EnerGizeXP Mod

A Minecraft Forge mod that converts energy (RF/FE) into experience points using specialized machines.

## Overview

EnerGizeXP adds three tiers of XP converters that transform electrical energy into experience points. The mod includes lead ore generation, Mekanism compatibility, and fluid-based XP distribution systems.

## Features

### XP Converters
- **Basic XP Converter:** Entry-level machine (25,000 RF, 400 XP storage)
- **Advanced XP Converter:** Mid-tier efficiency (40,000 RF, 800 XP storage)  
- **Elite XP Converter:** High-performance conversion (55,000 RF, 1,600 XP storage)

### Resource System
- **Lead Ore:** Naturally generates in the world
- **Lead Ingots:** Crafting material and Mekanism integration
- **Iron Gears:** Machine components

### Energy Integration
- Full Forge Energy (FE/RF) compatibility
- Works with Thermal Expansion, EnderIO, Mekanism, and more
- Consistent 150 RF per XP point efficiency across all tiers

### Fluid System
- XP output as fluid for automation
- Compatible with multiple XP fluid types
- 20 mB per XP point conversion rate
- Auto-distribution to connected tanks

### Mekanism Compatibility
- Lead ore enrichment (2x dust yield)
- Lead dust smelting recipes
- Seamless integration with Mekanism processing

## Quick Start

1. **Mine Lead Ore** - Found naturally in the world
2. **Craft XP Converter** - Start with the Basic tier
3. **Connect Power** - Any RF/FE energy source
4. **Extract XP** - Right-click GUI button or fluid pipes

## Documentation

### For Users
- **[Usage Guide](USAGE_GUIDE.md)** - Complete setup and automation guide
- **[API Documentation](API_DOCUMENTATION.md)** - Comprehensive API reference

### For Developers
- **[Developer Guide](DEVELOPER_GUIDE.md)** - Integration and extension guide
- **[API Documentation](API_DOCUMENTATION.md)** - Technical specifications

## Technical Specifications

### Conversion Rates

| Tier | Energy Storage | XP Storage | Energy/Cycle | XP/Cycle | Efficiency |
|------|----------------|------------|--------------|----------|------------|
| Basic | 25,000 RF | 400 XP | 300 RF | 2 XP | 150 RF/XP |
| Advanced | 40,000 RF | 800 XP | 600 RF | 4 XP | 150 RF/XP |
| Elite | 55,000 RF | 1,600 XP | 1,200 RF | 8 XP | 150 RF/XP |

### System Requirements
- **Minecraft:** 1.12.2
- **Forge:** 14.23.5.2860 or later
- **Kotlin:** 1.3.72 (included)

### Compatible Mods
- **Energy:** Thermal Expansion, EnderIO, Mekanism, Applied Energistics 2
- **Fluids:** OpenBlocks, Cyclic, Mob Grinding Utils, Reliquary
- **Processing:** Mekanism (enrichment chamber integration)

## Installation

1. Download and install Minecraft Forge 1.12.2
2. Download the EnerGizeXP mod jar
3. Place in your `mods` folder
4. Launch Minecraft

## Development

### Building
```bash
./gradlew build
```

### Dependencies
- Minecraft Forge 1.12.2-14.23.5.2860
- Kotlin 1.3.72
- Mekanism 9.8.3.390 (optional compatibility)

### Project Structure
```
src/main/java/mods/favoslav/energizexp/
├── blocks/          # XP converter blocks and tile entities
├── items/           # Lead ingots and components
├── gui/             # Creative tabs and interfaces
├── client/gui/      # Client-side GUI implementation
├── network/         # Networking packets
├── compatibility/   # Mod integration (Mekanism)
├── proxy/           # Client/server proxies
├── registry/        # Registration handlers
└── world/           # World generation (lead ore)
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

### Guidelines
- Follow Kotlin coding conventions
- Add documentation for public APIs
- Include usage examples
- Test with multiple mod combinations
- Maintain backwards compatibility

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Community

- **Issues:** Report bugs and request features on GitHub
- **Discussions:** Join the Minecraft Forge community
- **Discord:** Find us in modding Discord servers

## Changelog

### Version 0.1.0
- Initial release
- Three-tier XP converter system
- Lead ore generation and processing
- Mekanism compatibility
- Fluid-based XP distribution
- Forge Energy integration

## Acknowledgments

- **Minecraft Forge Team** - For the modding framework
- **Kotlin Team** - For the programming language
- **Mekanism Developers** - For compatibility APIs
- **Community** - For testing and feedback
