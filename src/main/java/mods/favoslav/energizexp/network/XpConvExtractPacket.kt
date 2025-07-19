package mods.favoslav.energizexp.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraft.util.math.BlockPos

class XpConvExtractPacket(
    var x: Int = 0,
    var y: Int = 0,
    var z: Int = 0
) : IMessage {
    constructor(pos: BlockPos) : this(pos.x, pos.y, pos.z)

    override fun toBytes(buf: ByteBuf) {
        buf.writeInt(x)
        buf.writeInt(y)
        buf.writeInt(z)
    }

    override fun fromBytes(buf: ByteBuf) {
        x = buf.readInt()
        y = buf.readInt()
        z = buf.readInt()
    }

    fun getPos() = BlockPos(x, y, z)
}

