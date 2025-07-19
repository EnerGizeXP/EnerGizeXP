#
# This is our ProGuard configuration, if you're not familiar with ProGuard feel free to just ignore this, just don't
# use the `:reobfReleaseJarJar` task in that case.
#

# We don't care about helper annotations.
-dontwarn org.jetbrains.annotations.**
-dontwarn javax.annotation.**
-dontwarn net.minecraft.**
-ignorewarnings

# Keep stuff that's found by Forge magic(TM).
-keep class mods.favoslav.energizexp.EnerGizeXP { *; }
-keep class mods.favoslav.energizexp.registry.RegistryHandler { *; }
-keep class mods.favoslav.energizexp.proxy.ClientProxy { *; }
-keep class mods.favoslav.energizexp.sproxy.DedicatedProxy { *; }

# TODO: Supressing notes about duplicate classes, doesn't seem to cause issues but feels wrong.
-dontnote kotlin.**

# We don't care about obfuscation! We just want to minify.
-dontobfuscate
