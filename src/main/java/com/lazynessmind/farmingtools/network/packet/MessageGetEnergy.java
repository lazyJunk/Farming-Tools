package com.lazynessmind.farmingtools.network.packet;

import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.network.FTNetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGetEnergy implements IMessage {

    private int x, y, z;
    private String className;
    private String energyDifferenceFieldName;


    public MessageGetEnergy() {
    }


    public MessageGetEnergy(int x, int y, int z, String className, String energyDifferenceFieldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.className = className;
        this.energyDifferenceFieldName = energyDifferenceFieldName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.className = ByteBufUtils.readUTF8String(buf);
        this.energyDifferenceFieldName = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, this.className);
        ByteBufUtils.writeUTF8String(buf, this.energyDifferenceFieldName);
    }

    public static class Handler implements IMessageHandler<MessageGetEnergy, IMessage> {

        @Override
        public IMessage onMessage(MessageGetEnergy message, MessageContext ctx) {
            if (ctx.side != Side.SERVER)
                return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
                    .addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(MessageGetEnergy message, MessageContext ctx) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntity te = ctx.getServerHandler().player.getServerWorld().getTileEntity(pos);
            if (te == null)
                return;
            if (!(te instanceof TileEntityPedestal))
                return;
            FTNetworkHandler.INSTANCE.sendTo(new MessageReturnEnergy(((TileEntityPedestal) te).currentEnergy,
                    message.className, message.energyDifferenceFieldName), ctx.getServerHandler().player);
        }

    }

}