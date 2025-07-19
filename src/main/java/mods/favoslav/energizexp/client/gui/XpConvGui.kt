package mods.favoslav.energizexp.client.gui

import mods.favoslav.energizexp.EnerGizeXP
import mods.favoslav.energizexp.blocks.xpconv.XpConvTileEntity
import mods.favoslav.energizexp.blocks.xpconv.XpConvContainer
import mods.favoslav.energizexp.network.NetworkHandler
import mods.favoslav.energizexp.network.XpConvExtractPacket
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.ResourceLocation

class XpConvGui(playerInv: InventoryPlayer, tile: XpConvTileEntity) : GuiContainer(XpConvContainer(playerInv, tile)) {

    private val pos = tile.pos

    private val background = ResourceLocation(EnerGizeXP.modId, "textures/gui/new_gui.png")
    private val circleSegments = ResourceLocation(EnerGizeXP.modId, "textures/gui/circle_segments.png")

    init {
        xSize = 176
        ySize = 166
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1f, 1f, 1f, 1f)
        mc.textureManager.bindTexture(background)

        val x = (width - xSize) / 2
        val y = (height - ySize) / 2

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)

        mc.textureManager.bindTexture(circleSegments)

        val tileEntity = mc.world.getTileEntity(pos) as? XpConvTileEntity ?: return

        val segmentSize = 32
        val currentSegment = tileEntity.cycle.coerceIn(0, 9)
        val u = currentSegment * segmentSize

        if (tileEntity.energyLevel == 0) {
            drawModalRectWithCustomSizedTexture(
                x + 72, y + 27,
                0f, 0f,
                segmentSize, segmentSize,
                320f, 64f
            )
        } else if (tileEntity.xpFluidTank.fluidAmount / XpConvTileEntity.XpFluidTank.XP_MB_PER_POINT < tileEntity.xpFluidTank.capacity / XpConvTileEntity.XpFluidTank.XP_MB_PER_POINT) {
            drawModalRectWithCustomSizedTexture(
                x + 72, y + 27,
                u.toFloat(), 0f,
                segmentSize, segmentSize,
                320f, 64f
            )
        } else {
            drawModalRectWithCustomSizedTexture(
                x + 72, y + 27,
                288f, 0f,
                segmentSize, segmentSize,
                320f, 64f
            )
        }

        mc.textureManager.bindTexture(background)

        // === XP bar ===
        val maxXpBarHeight = 52
        val xp = tileEntity.xpFluidTank.fluidAmount / XpConvTileEntity.XpFluidTank.XP_MB_PER_POINT
        val maxXp = tileEntity.xpFluidTank.capacity / XpConvTileEntity.XpFluidTank.XP_MB_PER_POINT
        val xpBarHeight = ((xp / maxXp.toFloat()) * maxXpBarHeight).toInt()
        val xpBarY = y + 17 + (maxXpBarHeight - xpBarHeight)

        drawTexturedModalRect(
            x + 165,
            xpBarY,
            176,
            0 + (maxXpBarHeight - xpBarHeight),
            4,
            xpBarHeight
        )

        // === Energy bar ===
        val maxEnergyBarHeight = 52
        val energy = tileEntity.energyLevel
        val maxEnergy = tileEntity.maxEnergy
        val energyBarHeight = ((energy / maxEnergy.toFloat()) * maxEnergyBarHeight).toInt()
        val energyBarY = y + 17 + (maxEnergyBarHeight - energyBarHeight)

        drawTexturedModalRect(
            x + 7,
            energyBarY,
            180,
            0 + (maxEnergyBarHeight - energyBarHeight),
            4,
            energyBarHeight
        )

        // XP Button
        drawTexturedModalRect(
            x + 141,
            y + 35,
            184,
            16,
            16,
            16
        )
    }

    private fun formatValue(value: Int, unit: String): String {
        return if (value < 1000) {
            "$value $unit"
        } else {
            val valueInK = value / 1000.0
            val formatted = if (valueInK % 1.0 == 0.0) {
                String.format("%.1f", valueInK)
            } else if ((valueInK * 10) % 1.0 == 0.0) {
                String.format("%.1f", valueInK)
            } else {
                String.format("%.2f", valueInK)
            }
            "$formatted k$unit"
        }
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val tile = mc.world.getTileEntity(pos) as? XpConvTileEntity ?: return
        val displayName = when (tile.getGuiTitle()) {
            "xpconv_basic" -> "Basic XP Converter"
            "xpconv_advanced" -> "Advanced XP Converter"
            "xpconv_elite" -> "Elite XP Converter"
            else -> "XP Converter"
        }
        fontRenderer.drawString(displayName, 8, 6, 0x404040)
        fontRenderer.drawString("Inventory", 8, ySize - 96 + 3, 0x404040)

        val tileEntity = mc.world.getTileEntity(pos) as? XpConvTileEntity ?: return

        // Real mouse x and y
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        val relMouseX = mouseX - x
        val relMouseY = mouseY - y

        // Energy bar
        val energyBarX = 7
        val energyBarY = 19
        val energyBarWidth = 4
        val energyBarHeight = 52

        // XP bar
        val xpBarX = 165
        val xpBarY = 17
        val xpBarWidth = 4
        val xpBarHeight = 52

        // XP Button
        val xpButtonX = 141
        val xpButtonY = 35
        val xpButtonWidth = 16
        val xpButtonHeight = 16

        if (relMouseX in energyBarX until (energyBarX + energyBarWidth) &&
            relMouseY in energyBarY until (energyBarY + energyBarHeight)) {
            val energyText = formatValue(tileEntity.energyLevel, "RF") + " / " + formatValue(tileEntity.maxEnergy, "RF")
            this.drawHoveringText(listOf(energyText), relMouseX, relMouseY, fontRenderer)
        }

        if (relMouseX in xpBarX until (xpBarX + xpBarWidth) &&
            relMouseY in xpBarY until (xpBarY + xpBarHeight)) {
            val xpText = formatValue(tileEntity.xpFluidTank.fluidAmount / XpConvTileEntity.XpFluidTank.XP_MB_PER_POINT, "XP") + " / " + formatValue(tileEntity.xpFluidTank.capacity / XpConvTileEntity.XpFluidTank.XP_MB_PER_POINT, "XP")
            this.drawHoveringText(listOf(xpText), relMouseX, relMouseY, fontRenderer)
        }

        if (relMouseX in xpButtonX until (xpButtonX + xpButtonWidth) &&
            relMouseY in xpButtonY until (xpButtonY + xpButtonHeight)) {
            GlStateManager.color(1f, 1f, 1f, 1f)
            mc.textureManager.bindTexture(background)
            drawTexturedModalRect(
                xpButtonX,
                xpButtonY,
                184,
                0,
                16,
                16
            )
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

        val x = (width - xSize) / 2
        val y = (height - ySize) / 2

        val tileEntity = mc.world.getTileEntity(pos) as? XpConvTileEntity ?: return

        val xpButtonX = 141
        val xpButtonY = 35
        val xpButtonWidth = 16
        val xpButtonHeight = 16

        if (
            mouseX in (x + xpButtonX) until (x + xpButtonX + xpButtonWidth) &&
            mouseY in (y + xpButtonY) until (y + xpButtonY + xpButtonHeight)
        ) {
            NetworkHandler.CHANNEL.sendToServer(XpConvExtractPacket(tileEntity.pos))
        }
    }

}
