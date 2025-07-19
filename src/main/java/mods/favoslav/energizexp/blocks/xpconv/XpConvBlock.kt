package mods.favoslav.energizexp.blocks.xpconv

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.blocks.IHasItemBlock
import mods.favoslav.energizexp.gui.tabs.CreativeTab
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.tileentity.TileEntity

class XpConvBlock(
    private val name: String,
    private val maxXp: Int,
    private val maxEnergy: Int,
    private val xpPerCycle: Int,
    private val energyPerCycle: Int,
    private val guiId: Int,
) : Block(Material.ROCK), ITileEntityProvider, IHasItemBlock {

    override val itemBlock = ItemBlock(this).apply { setRegistryName(name) }

    init {
        defaultState = blockState.baseState.withProperty(FACING, EnumFacing.NORTH)
        setRegistryName(name)
        // * needs to be in a "property access syntax" so kotlin syntax checker shuts up and leaves me alone
        //translationKey = RfXp.translationKey(NAME)
        //creativeTab = CreativeTab
        unlocalizedName = EnerGizeXP.translationKey(name)
        setCreativeTab(CreativeTab)
    }

    companion object {
        val FACING: PropertyDirection = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL)
    }

    @Deprecated("",
        ReplaceWith(
            "defaultState.withProperty(FACING, placer.horizontalFacing.opposite)",
            "mods.favoslav.energizexp.blocks.xpconv.XpConvBlock.Companion.FACING"
        )
    )
    override fun getStateForPlacement(
        worldIn: World,
        pos: BlockPos,
        facing: EnumFacing,
        hitX: Float,
        hitY: Float,
        hitZ: Float,
        meta: Int,
        placer: EntityLivingBase
    ): IBlockState {
        return defaultState.withProperty(FACING, placer.horizontalFacing.opposite)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, FACING)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(FACING).horizontalIndex
    }

    @Deprecated("", ReplaceWith(
            "defaultState.withProperty(FACING, EnumFacing.values()[meta + 2])",
            "mods.favoslav.energizexp.blocks.xpconv.XpConvBlock.Companion.FACING",
            "net.minecraft.util.EnumFacing"
        )
    )
    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState.withProperty(FACING, EnumFacing.values()[meta + 2])
    }

    override fun onBlockActivated(
        worldIn: World, pos: BlockPos, state: IBlockState,
        playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing,
        hitX: Float, hitY: Float, hitZ: Float
    ): Boolean {
        if (!worldIn.isRemote) {
            playerIn.openGui(EnerGizeXP.INSTANCE, guiId, worldIn, pos.x, pos.y, pos.z)
        }
        return true
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
        val title = when (guiId) {
            0 -> "xpconv_basic"
            1 -> "xpconv_advanced"
            2 -> "xpconv_elite"
            else -> "xpconv_basic"
        }
        return XpConvTileEntity(title, maxXp, maxEnergy, xpPerCycle, energyPerCycle)
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }
}
