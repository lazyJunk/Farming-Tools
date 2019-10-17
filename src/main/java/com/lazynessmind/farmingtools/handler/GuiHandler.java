package com.lazynessmind.farmingtools.handler;

import com.lazynessmind.farmingtools.container.ContainerGrowthPedestal;
import com.lazynessmind.farmingtools.container.ContainerHarvester;
import com.lazynessmind.farmingtools.container.ContainerPedestal;
import com.lazynessmind.farmingtools.container.slots.SlotPlanter;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.gui.GuiGrowthPedestal;
import com.lazynessmind.farmingtools.gui.GuiHarvester;
import com.lazynessmind.farmingtools.gui.GuiPlanter;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityGrowthPedestal;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
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
        if(ID == FTGuis.HARVESTER){
            return new ContainerHarvester(player.inventory, (TileEntityHarvester) world.getTileEntity(new BlockPos(x, y, z)));
        } else if(ID == FTGuis.PLANTER){
            return new ContainerPedestal(player.inventory, (TileEntityPlanter)world.getTileEntity(new BlockPos(x, y, z)), new SlotPlanter(((TileEntityPlanter) world.getTileEntity(new BlockPos(x, y, z))).getMainHandler(), 0, 80, 33));
        } else if(ID == FTGuis.GROWTHER){
            return new ContainerGrowthPedestal(player.inventory, (TileEntityGrowthPedestal)world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == FTGuis.HARVESTER){
            return new GuiHarvester(player.inventory, (TileEntityHarvester) world.getTileEntity(new BlockPos(x, y, z)));
        } else if(ID == FTGuis.PLANTER){
            return new GuiPlanter(player.inventory, (TileEntityPlanter)world.getTileEntity(new BlockPos(x, y, z)));
        } else if(ID == FTGuis.GROWTHER){
            return new GuiGrowthPedestal(player.inventory, (TileEntityGrowthPedestal) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
