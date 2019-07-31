package com.lazynessmind.farmingtools.container;

import com.lazynessmind.farmingtools.container.slots.SlotPlanter;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerPlanter extends Container {

    private final TileEntityPlanter tileEntityPlanter;

    public ContainerPlanter(InventoryPlayer inventoryPlayer, TileEntityPlanter planter) {
        this.tileEntityPlanter = planter;
        IItemHandler handler = planter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        this.addSlotToContainer(new SlotPlanter(handler, 0, 80, 33));

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntityPlanter.isUsableByPlayer(playerIn);
    }
}
