package com.lazynessmind.farmingtools.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnUtils {

    public static void spawnItemAt(World worldIn, BlockPos pos, ItemStack item) {
        if (!worldIn.isRemote && !item.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) {
            EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY()+0.5, pos.getZ(), item);
            entityitem.setDefaultPickupDelay();
            worldIn.spawnEntity(entityitem);
        }
    }
}
