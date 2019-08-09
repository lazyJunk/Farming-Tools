package com.lazynessmind.farmingtools.network;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import com.lazynessmind.farmingtools.network.packet.MessageRedstonePower;
import com.lazynessmind.farmingtools.network.packet.MessageShowArea;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class FTNetworkHandler {

    private static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(FarmingToolsConst.MODID);

    private static int id = 0;

    public static void init(){
        INSTANCE.registerMessage(MessageShowArea.Handler.class, MessageShowArea.class, id++, Side.SERVER);
        INSTANCE.registerMessage(MessageRedstonePower.Handler.class, MessageRedstonePower.class, id++, Side.SERVER);
    }

    public static void sendPacketToServer(IMessage message){
        INSTANCE.sendToServer(message);
    }
}
