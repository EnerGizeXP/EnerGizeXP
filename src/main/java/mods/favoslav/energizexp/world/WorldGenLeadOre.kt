package mods.favoslav.energizexp.world

import mods.favoslav.energizexp.registry.RegistryHandler
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

class WorldGenLeadOre : IWorldGenerator {
    override fun generate(rand: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        if (world.provider.dimension == 0) {
            generateOre(
                RegistryHandler.leadOre.defaultState,
                world, rand, chunkX, chunkZ,
                minY = 10, maxY = 40,
                veinSize = 6,
                chances = 16
            )
        }
    }

    private fun generateOre(
        state: IBlockState,
        world: World,
        rand: Random,
        chunkX: Int,
        chunkZ: Int,
        minY: Int,
        maxY: Int,
        veinSize: Int,
        chances: Int
    ) {
        val deltaY = maxY - minY
        for (i in 0 until chances) {
            val pos = BlockPos(
                chunkX * 16 + rand.nextInt(16),
                minY + rand.nextInt(deltaY),
                chunkZ * 16 + rand.nextInt(16)
            )
            WorldGenMinable(state, veinSize) { blockState -> blockState?.block == Blocks.STONE }.generate(world, rand, pos)
            val after = world.getBlockState(pos)
            if (after.block == RegistryHandler.leadOre) {
                println("SUCCESS: Lead ore generated at $pos")
            }
        }
    }
}
