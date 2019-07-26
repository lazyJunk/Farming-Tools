package com.lazynessmind.farmingtools.handler;

import com.lazynessmind.farmingtools.container.ContainerHarvester;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.gui.GuiHarvester;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == FTGuis.HARVESTER)
            return new ContainerHarvester(player.inventory, (TileEntityHarvester) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == FTGuis.HARVESTER)
            return new GuiHarvester(player.inventory, (TileEntityHarvester) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
