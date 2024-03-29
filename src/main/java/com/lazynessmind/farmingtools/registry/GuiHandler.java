package com.lazynessmind.farmingtools.registry;

import com.lazynessmind.farmingtools.gui.container.ContainerPedestal;
import com.lazynessmind.farmingtools.gui.container.slots.SlotGrowthPedestal;
import com.lazynessmind.farmingtools.gui.container.slots.SlotHarvester;
import com.lazynessmind.farmingtools.gui.container.slots.SlotPlanter;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.gui.GuiGrowthPedestal;
import com.lazynessmind.farmingtools.gui.GuiHarvester;
import com.lazynessmind.farmingtools.gui.GuiPlanter;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityGrowthPedestal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityHarvester;
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
            return new ContainerPedestal(player.inventory, (TileEntityHarvester)world.getTileEntity(new BlockPos(x, y, z)), new SlotHarvester(((TileEntityHarvester) world.getTileEntity(new BlockPos(x, y, z))), 0, 80, 33));
        } else if(ID == FTGuis.PLANTER){
            return new ContainerPedestal(player.inventory, (TileEntityPlanter)world.getTileEntity(new BlockPos(x, y, z)), new SlotPlanter(((TileEntityPlanter) world.getTileEntity(new BlockPos(x, y, z))), 0, 80, 33));
        } else if(ID == FTGuis.GROWTHER){
            return new ContainerPedestal(player.inventory, (TileEntityGrowthPedestal)world.getTileEntity(new BlockPos(x, y, z)), new SlotGrowthPedestal(((TileEntityGrowthPedestal) world.getTileEntity(new BlockPos(x, y, z))), 0, 80, 33));
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
