package mods.favoslav.energizexp.blocks.xpconv

enum class BlockInfo(
    val title: String,
    val maxXp: Int,
    val maxEnergy: Int,
    val energyPerCycle: Int,
    val xpPerCycle: Int,
    val guild: Int
) {
    BASIC("xpconv_basic", 400, 25000, 300, 2, 0),
    ADVANCED("xpconv_advanced", 800, 40000, 600, 5, 1),
    ELITE("xpconv_elite", 1600, 55000, 1200, 12, 2);

    /*private val basexp = 2
    val xpPerCycle: Int
        get() = (basexp * (guild * 2) + (guild * guild)).takeIf { it != 0 } ?: basexp*/
}