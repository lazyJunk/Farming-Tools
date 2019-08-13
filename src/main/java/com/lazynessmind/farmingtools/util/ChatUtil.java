package com.lazynessmind.farmingtools.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil {

    public static void  chatMessage(EntityPlayer player, String text){
        player.sendMessage(new TextComponentString(text));
    }
}
