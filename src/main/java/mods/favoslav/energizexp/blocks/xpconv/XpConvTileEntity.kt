package mods.favoslav.energizexp.blocks.xpconv

import net.minecraft.entity.item.EntityXPOrb
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler

class XpConvTileEntity() : TileEntity(), ITickable {
    private lateinit var title: String
    lateinit var xpFluidTank: XpFluidTank
        private set

    var maxXp: Int = 0
    var maxEnergy: Int = 0
    var xpPerCycle: Int = 0
    var energyPerCycle: Int = 0

    init {
        if (!::xpFluidTank.isInitialized) xpFluidTank = XpFluidTank(0)
    }

    constructor(
        title: String,
        maxXp: Int,
        maxEnergy: Int,
        xpPerCycle: Int,
        energyPerCycle: Int
    ) : this() {
        this.title = title
        this.maxXp = maxXp
        this.maxEnergy = maxEnergy
        this.xpPerCycle = xpPerCycle
        this.energyPerCycle = energyPerCycle
        this.xpFluidTank = XpFluidTank(maxXp * XpFluidTank.XP_MB_PER_POINT)
    }

    fun getGuiTitle(): String = title

    private val energyStorage = object : IEnergyStorage {
        override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
            val energyReceived = (maxEnergy - energyLevel).coerceAtMost(maxReceive)
            if (!simulate) {
                energyLevel += energyReceived
                markDirty()
            }
            return energyReceived
        }

        override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
            val energyExtracted = energyLevel.coerceAtMost(maxExtract)
            if (!simulate) {
                energyLevel -= energyExtracted
                markDirty()
            }
            return energyExtracted
        }

        override fun getEnergyStored(): Int = energyLevel
        override fun getMaxEnergyStored(): Int = maxEnergy
        override fun canExtract(): Boolean = true
        override fun canReceive(): Boolean = true
    }

    class XpFluidTank(capacity: Int) : FluidTank(capacity) {
        companion object {
            val acceptedRegistryNames = setOf(
                "liquid_xp", "xpjuice", "essence", "mobessence", "experience", "fluid_experience"
            )

            fun getOrCreateUniversalXpFluid(): Fluid {
                acceptedRegistryNames.firstNotNullOfOrNull { FluidRegistry.getFluid(it) }?.let { return it }

                val fluidId = "fluid_xp"
                FluidRegistry.getFluid(fluidId)?.let { return it }

                val universalXp = object : Fluid(fluidId,
                    ResourceLocation("minecraft", "blocks/water_still"),
                    ResourceLocation("minecraft", "blocks/water_flow")
                ) {
                    override fun getColor(fluidStack: FluidStack?): Int = 0xFF6AA84F.toInt() // zelená XP
                }

                universalXp.density = 1000
                universalXp.viscosity = 1500
                universalXp.luminosity = 2
                universalXp.unlocalizedName = fluidId
                universalXp.isGaseous = false

                FluidRegistry.registerFluid(universalXp)
                FluidRegistry.addBucketForFluid(universalXp)
                return universalXp
            }
            const val XP_MB_PER_POINT = 20
        }
        override fun canFillFluidType(fluid: FluidStack): Boolean {
            return fluid.fluid.name in acceptedRegistryNames || fluid.fluid.name == "universal_xp"
        }
        fun internalFill(resource: FluidStack, doFill: Boolean): Int = super.fill(resource, doFill)
        override fun fill(resource: FluidStack?, doFill: Boolean): Int = 0
    }

    fun extractXpToPlayer(player: EntityPlayer) {
        val mbAmount = xpFluidTank.fluidAmount
        val xpPoints = mbAmount / XpFluidTank.XP_MB_PER_POINT

        if (xpPoints > 0) {
            xpFluidTank.drain(xpPoints * XpFluidTank.XP_MB_PER_POINT, true)
            //player.addExperience(xpPoints)
            world.spawnEntity(EntityXPOrb(world, player.posX, player.posY + 0.5, player.posZ, xpPoints))
            markDirty()
        }
    }

    var energyLevel = 0
    var cycle = 0

    override fun update() {
        if (world != null && !world.isRemote) {
            cycle++
            if (cycle >= 10) {
                if (energyLevel >= energyPerCycle && xpFluidTank.fluidAmount < xpFluidTank.capacity) {
                    energyLevel -= energyPerCycle
                    val toFill = xpPerCycle
                    val fluid = XpFluidTank.getOrCreateUniversalXpFluid()
                    val stack = FluidStack(fluid, toFill)
                    val filled = xpFluidTank.internalFill(stack, true)
                    if (filled > 0) markDirty()
                }
                cycle = 0
            }

            for (side in EnumFacing.values()) {
                val adj = world.getTileEntity(pos.offset(side)) ?: continue
                val handler: IFluidHandler = adj.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.opposite) ?: continue

                val extractStack = xpFluidTank.drain(1000, false)
                if (extractStack != null && extractStack.amount > 0) {
                    val accepted = handler.fill(extractStack, true)
                    if (accepted > 0) {
                        xpFluidTank.drain(accepted, true)
                        markDirty()
                    }
                }
            }
            sync()
        }
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when (capability) {
            CapabilityEnergy.ENERGY -> CapabilityEnergy.ENERGY.cast(energyStorage)
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(xpFluidTank)
            else -> super.getCapability(capability, facing)
        }
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityEnergy.ENERGY ||
                capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ||
                super.hasCapability(capability, facing)
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity {
        val nbt = NBTTagCompound()
        writeToNBT(nbt)
        return SPacketUpdateTileEntity(pos, 0, nbt)
    }

    override fun onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity) {
        readFromNBT(pkt.nbtCompound)
    }

    override fun getUpdateTag(): NBTTagCompound {
        return writeToNBT(NBTTagCompound())
    }

    override fun handleUpdateTag(tag: NBTTagCompound) {
        readFromNBT(tag)
    }

    private fun sync() {
        if (world != null && !world.isRemote) {
            val blockState = world.getBlockState(pos)
            world.notifyBlockUpdate(pos, blockState, blockState, 3)
        }
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        title = compound.getString("GuiTitle")
        /*maxXp = compound.getInteger("MaxXpTank")
        maxEnergy = compound.getInteger("MaxEnergy")
        xpPerCycle = compound.getInteger("XpPerCycle")
        energyPerCycle = compound.getInteger("EnergyPerCycle")*/
        val info = BlockInfo.values().find { it.title == title } ?: BlockInfo.BASIC
        maxXp = info.maxXp
        maxEnergy = info.maxEnergy
        xpPerCycle = info.xpPerCycle
        energyPerCycle = info.energyPerCycle
        energyLevel = compound.getInteger("EnergyLevel")
        cycle = compound.getInteger("Cycle")
        xpFluidTank = XpFluidTank(maxXp * XpFluidTank.XP_MB_PER_POINT)
        xpFluidTank.readFromNBT(compound.getCompoundTag("XpFluidTank"))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        compound.setString("GuiTitle", title)
        /*compound.setInteger("MaxXpTank", maxXp)
        compound.setInteger("MaxEnergy", maxEnergy)
        compound.setInteger("XpPerCycle", xpPerCycle)
        compound.setInteger("EnergyPerCycle", energyPerCycle)*/
        compound.setInteger("EnergyLevel", energyLevel)
        compound.setInteger("Cycle", cycle)
        compound.setTag("XpFluidTank", xpFluidTank.writeToNBT(NBTTagCompound()))
        return compound
    }

}
