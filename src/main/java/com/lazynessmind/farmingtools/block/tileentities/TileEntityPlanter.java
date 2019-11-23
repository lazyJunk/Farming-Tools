package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.BasicItemHandler;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class TileEntityPlanter extends TileEntityPedestal implements ITickable {

    private IBlockState crop;
    private int timer;

    public TileEntityPlanter() {
        super(false, false);
    }

    @Override
    public BasicItemHandler getInv() {
        return super.getInv();
    }

    @Override
    public void update() {
        this.updateValues();

        if (this.world != null && !this.world.isRemote) {

            ItemStack mainSlot = this.getStackInSlot(0);
            Item itemSlot = this.getStackInSlot(0).getItem();

            if (!mainSlot.isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    this.crop = ((IPlantable) mainSlot.getItem()).getPlant(this.world, this.pos);
                }
            }
            if (this.canWork()) {
                if (this.timer < this.getWorkTime()) {
                    this.timer++;
                } else if (this.timer >= this.getWorkTime()) {
                    this.timer = 0;
                    this.plantCrop(this.crop);
                }
            }
            this.markDirty();
        }
    }

    private void plantCrop(IBlockState crop) {
        for (BlockPos poss : FarmUtils.checkInXZRange(1, this.getPos(), false)) {
            if (FarmUtils.canPlantCrop(poss, this.world)) {
                ItemStack mainSlot = this.getStackInSlot(0);

                if (this.crop != null) {
                    if (!mainSlot.isEmpty() && mainSlot.getItem() instanceof ItemSeedFood || mainSlot.getItem() instanceof ItemSeeds) {
                        if (this.needRedstonePower()) {
                            if (this.world.isBlockPowered(this.pos)) {
                                this.world.setBlockState(poss, this.crop);
                                EntityPlayer player = Minecraft.getMinecraft().player;
                                if (player != null && !player.isCreative()){
                                    this.decrStackSize(0, 1);
                                    this.getEnergy().removeFromBuffer(this.getCostPerWork());
                                }
                            }
                        } else {
                            this.world.setBlockState(poss, crop);
                            EntityPlayer player = Minecraft.getMinecraft().player;
                            if (player != null && !player.isCreative()) {
                                this.decrStackSize(0, 1);
                                this.getEnergy().removeFromBuffer(this.getCostPerWork());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean canInsertOnSlot(int slotIndex, ItemStack itemStackIn, EnumFacing direction) {
        if (slotIndex == 0) {
            return this.isItemValid(slotIndex, itemStackIn);
        } else return false;
    }

    @Override
    protected boolean canExtractOnSlot(int slotIndex, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    protected boolean isItemValid(int index, ItemStack stack) {
        return index == 0 && stack.getItem() instanceof ItemSeeds || stack.getItem() instanceof ItemSeedFood;
    }
}
