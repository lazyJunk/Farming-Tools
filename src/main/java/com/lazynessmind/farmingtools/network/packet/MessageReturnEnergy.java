package com.lazynessmind.farmingtools.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;

public class MessageReturnEnergy implements IMessage {

    private int currentEnergy;
    private String className;
    private String energyDifferenceFieldName;


    public MessageReturnEnergy() {

    }

    public MessageReturnEnergy(int energyDifference2, String className, String energyDifferenceFieldName) {
        this.currentEnergy = energyDifference2;
        this.className = className;
        this.energyDifferenceFieldName = energyDifferenceFieldName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.currentEnergy = buf.readInt();
        this.className = ByteBufUtils.readUTF8String(buf);
        this.energyDifferenceFieldName = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.currentEnergy);
        ByteBufUtils.writeUTF8String(buf, this.className);
        ByteBufUtils.writeUTF8String(buf, this.energyDifferenceFieldName);
    }

    public static class Handler implements IMessageHandler<MessageReturnEnergy, IMessage> {

        @Override
        public IMessage onMessage(MessageReturnEnergy message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(MessageReturnEnergy message) {
            try {
                Class clazz = Class.forName(message.className);
                Field energyDifferenceField = clazz.getDeclaredField(message.energyDifferenceFieldName);
                energyDifferenceField.setInt(clazz, message.currentEnergy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}