package com.lazynessmind.farmingtools.network.packet;

import com.lazynessmind.farmingtools.block.base.FTBlock;
import com.lazynessmind.farmingtools.block.tileentities.base.FTBlockTileEntity;
import com.lazynessmind.farmingtools.interfaces.IRange;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageShowArea implements IMessage {

    public boolean state;
    private String tileEntityId;
    private int x;
    private int y;
    private int z;

    public MessageShowArea() {

    }



    public MessageShowArea(String tileEntity, boolean state, int x, int y, int z) {
        this.state = state;
        this.tileEntityId = tileEntity;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.state = buf.readBoolean();
        this.tileEntityId = ByteBufUtils.readUTF8String(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.state);
        ByteBufUtils.writeUTF8String(buf, tileEntityId);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    //I dont know how this is working ... looooooool (09-08-2019)
    public static class Handler implements IMessageHandler<MessageShowArea, IMessage> {
        @Override
        public IMessage onMessage(MessageShowArea message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServer().addScheduledTask(() -> {
                BlockPos pos = new BlockPos(message.x, message.y, message.z);
                World world = player.world;
                if (world.getBlockState(pos).getBlock() == FTBlock.getBlockFromName(message.tileEntityId)) {
                    Block temp = FTBlock.getBlockFromName(message.tileEntityId);
                    if (temp instanceof FTBlockTileEntity) {
                        TileEntity blockTileEntity = ((FTBlockTileEntity) temp).getTileEntity(world, pos);
                        if (blockTileEntity instanceof IRange) {
                            ((IRange) blockTileEntity).showRangeArea(message.state);
                            ((FTBlockTileEntity) temp).scheduleUpdate(world, pos);
                        }

                    }
                }
            });
            return null;
        }
    }
}
