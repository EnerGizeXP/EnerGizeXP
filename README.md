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
- Enrichment Chamber recipe for lead ore → 2x lead dust
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

This mod is written in Java with Kotlin support and uses the Forge modding framework. The source code is available for reference and modification.

### Building from Source

1. Clone the repository
2. Run `./gradlew build` to compile the mod
3. The compiled JAR will be in the `build/libs` directory

## License

This mod is developed by Favoslav_. Please check the license file for specific terms.

## Support

For issues, questions, or contributions, please refer to the project's issue tracker or contact the developer, contact info can be found here [www.favoslav.cz](https://www.favoslav.cz/)
.

## Version History

- **0.1.0**: Initial release with XP converters and lead material system

---

*EnerGizeXP - Converting experience into power since 2025*
*Guaranteed support for mekanism.*

## For Developers

### Prerequisites

- **Java Development Kit (JDK)**: Version 8 (required for Minecraft 1.12.2)
- **Gradle**: The project uses Gradle wrapper, so no separate installation needed
- **IDE**: Recommended: IntelliJ IDEA or Eclipse with Forge MDK support
- **Minecraft Forge**: Version 1.12.2-14.23.5.2860

### Development Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd energizexp
   ```

2. **Setup the development environment**:
   ```bash
   # On Linux/macOS
   ./gradlew setupDecompWorkspace
   ./gradlew genIntellijRuns
   
   # On Windows
   gradlew.bat setupDecompWorkspace
   gradlew.bat genIntellijRuns
   ```

3. **Import into your IDE**:
   - **IntelliJ IDEA**: Import as Gradle project
   - **Eclipse**: Run `./gradlew eclipse` then import existing project

4. **Run the mod**:
   ```bash
   ./gradlew runClient  # For client-side testing
   ./gradlew runServer  # For server-side testing
   ```

### Project Structure

```
src/main/java/mods/favoslav/energizexp/
├── EnerGizeXP.java          # Main mod class
├── blocks/                   # Block implementations (XP Converters, Lead Ore)
├── items/                    # Item implementations (Lead Ingot, Machine Casing)
├── registry/                 # Registration handlers
├── network/                  # Network packet handling
├── proxy/                    # Client/Server proxy classes
├── handler/                  # Event handlers
├── gui/                      # GUI implementations
├── client/                   # Client-side code
├── compatibility/            # Mod compatibility (Mekanism)
└── world/                    # World generation (Lead Ore generation)
```

### Using as a Dependency

#### Maven/Gradle Dependency

To use EnerGizeXP as a dependency in your mod:

```gradle
repositories {
    maven {
        url "https://your-maven-repo.com"  // Replace with actual repository
    }
}

dependencies {
    compileOnly "mods.favoslav:energizexp:0.1.0"
}
```

#### Manual JAR Inclusion

1. Build the mod: `./gradlew build`
2. Copy the JAR from `build/libs/` to your mod's `libs/` folder
3. Add to your `build.gradle.kts`:
   ```gradle
   dependencies {
       compileOnly files("libs/energizexp-0.1.0.jar")
   }
   ```

### API Usage

#### Basic Integration

```java
// Check if EnerGizeXP is loaded
if (Loader.isModLoaded("energizexp")) {
    // Your integration code here
}

// Access mod instance
EnerGizeXP modInstance = EnerGizeXP.INSTANCE;
```

#### Translation Keys

Use the mod's translation key helper:
```java
String key = EnerGizeXP.translationKey("item", "example");
// Returns: "energizexp.item.example"
```

#### Resource Locations

Use the mod's resource location helper:
```java
ResourceLocation texture = EnerGizeXP.resource("textures/items/example.png");
// Returns: ResourceLocation("energizexp", "textures/items/example.png")
```

### Modifying the Mod

#### Adding New XP Converter Tiers

1. Create a new block class in the `blocks/` package following the existing converter pattern
2. Add the block to `RegistryHandler.java`
3. Create the crafting recipe in `RegistryHandler.registerRecipes()`
4. Add textures and models in `src/main/resources/assets/energizexp/`

#### Adding New Materials

1. Create item/block classes in the appropriate package
2. Register in `RegistryHandler.java`
3. Add world generation if needed in the `world/` package
4. Create crafting recipes and smelting recipes

#### Adding Compatibility

1. Create a new class in the `compatibility/` package
2. Follow the pattern of `MekanismCompatibility.java`
3. Register in the main mod's `init()` method

#### Network Communication

Use the existing `NetworkHandler` for custom packets:
```java
// Register your packet
NetworkHandler.registerPacket(YourPacket.class, YourPacket::new);
```

### Building

#### Development Build
```bash
./gradlew build
```

#### Release Build
```bash
./gradlew build -Pversion=1.0.0
```

#### Fat JAR (includes dependencies)
```bash
./gradlew jarJar
```

### Contributing

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/your-feature`
3. **Follow the coding style**:
   - Use Java for main mod logic
   - Kotlin is supported for utilities
   - Follow existing package structure
   - Use meaningful class and method names
4. **Test your changes**:
   - Test in both single-player and multiplayer
   - Verify compatibility with Mekanism
   - Test XP conversion rates and energy output
5. **Submit a pull request**

### Troubleshooting

#### Common Issues

- **Gradle sync fails**: Ensure you're using JDK 8
- **Mod not loading**: Check `mcmod.info` for correct mod ID
- **Missing dependencies**: Run `./gradlew --refresh-dependencies`
- **XP conversion not working**: Check if Mekanism is properly loaded

#### Debug Mode

Enable debug logging in `build.gradle.kts`:
```gradle
property("forge.logging.console.level", "debug")
```

### API Reference

#### XP Converter Tiers

The mod provides three tiers of XP Converters with different specifications:

- **Basic**: 400 XP storage, 25,000 RF storage, 300 RF per cycle
- **Advanced**: 800 XP storage, 40,000 RF storage, 600 RF per cycle  
- **Elite**: 1,600 XP storage, 55,000 RF storage, 1,200 RF per cycle

#### Energy Conversion Rates

Each converter tier has different XP-to-energy conversion rates:
- **Basic**: 2 XP → 300 RF
- **Advanced**: 6 XP → 600 RF
- **Elite**: 12 XP → 1,200 RF

#### Mekanism Integration

When Mekanism is present, the mod automatically adds:
- Lead ore enrichment recipes
- Lead dust smelting recipes
- Enhanced processing options
