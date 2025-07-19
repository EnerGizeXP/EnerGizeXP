# EnerGizeXP mod

A Minecraft Forge mod for Minecraft 1.12.2 that provides energy and XP conversion functionality with Mekanism compatibility.

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
├── blocks/                   # Block implementations
├── items/                    # Item implementations
├── registry/                 # Registration handlers
├── network/                  # Network packet handling
├── proxy/                    # Client/Server proxy classes
├── handler/                  # Event handlers
├── gui/                      # GUI implementations
├── client/                   # Client-side code
├── compatibility/            # Mod compatibility (Mekanism)
└── world/                    # World generation
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

#### Adding New Blocks/Items

1. Create your class in the appropriate package (`blocks/` or `items/`)
2. Register in `RegistryHandler.java`
3. Add textures and models in `src/main/resources/assets/energizexp/`

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
5. **Submit a pull request**

### Troubleshooting

#### Common Issues

- **Gradle sync fails**: Ensure you're using JDK 8
- **Mod not loading**: Check `mcmod.info` for correct mod ID
- **Missing dependencies**: Run `./gradlew --refresh-dependencies`

#### Debug Mode

Enable debug logging in `build.gradle.kts`:
```gradle
property("forge.logging.console.level", "debug")
```

### License

[Add your license information here]

### Support

- **Issues**: Report bugs and feature requests on GitHub
- **Discord**: [Add Discord server link if available]
- **Documentation**: Check the code comments for detailed API documentation
