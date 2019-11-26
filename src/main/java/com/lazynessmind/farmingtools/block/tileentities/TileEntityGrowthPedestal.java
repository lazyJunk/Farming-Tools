package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.BasicItemHandler;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.FarmingToolsItems;
import com.lazynessmind.farmingtools.item.ItemAdvancedBoneMeal;
import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityGrowthPedestal extends TileEntityPedestal implements ITickable {

    private int timer = 0;

    public TileEntityGrowthPedestal() {
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
            if (this.canWork()) {
                if (this.timer < this.getWorkTime()) {
                    this.timer++;
                } else if (this.timer >= this.getWorkTime()) {
                    this.timer = 0;
                    this.tickCrop();
                }
            }
        }
        this.markDirty();
    }

    private void tickCrop() {
        ItemStack mainSlot = this.getStackInSlot(0);

        if (mainSlot.getCount() != 0) {
            if (mainSlot.getItem() == FarmingToolsItems.ADVANCED_BONE_MEAL) {
                for (BlockPos blockPos : FarmUtils.checkInXZRange(1, this.pos, false)) {
                    if (this.world.getBlockState(blockPos).getBlock() instanceof BlockCrops) {
                        BlockCrops crops = (BlockCrops) this.world.getBlockState(blockPos).getBlock();
                        if (this.needRedstonePower()) {
                            if (this.world.isBlockPowered(this.pos)) {
                                if (!FarmUtils.canFarm(crops, this.world, blockPos)) {
                                    this.world.setBlockState(blockPos, crops.getStateFromMeta(crops.getMaxAge()), 3);
                                    EntityPlayer player = Minecraft.getMinecraft().player;
                                    if (player != null && !player.isCreative()){
                                        this.decrStackSize(0, 1);
                                        this.getEnergy().removeFromBuffer(this.getCostPerWork());
                                    }
                                }
                            }
                        } else {
                            if (!FarmUtils.canFarm(crops, this.world, blockPos)) {
                                this.world.setBlockState(blockPos, crops.getStateFromMeta(crops.getMaxAge()), 3);
                                EntityPlayer player = Minecraft.getMinecraft().player;
                                if (player != null && !player.isCreative()){
                                    this.decrStackSize(0, 1);
                                    this.getEnergy().removeFromBuffer(this.getCostPerWork());
                                }
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
        return index == 0 && stack.getItem() instanceof ItemAdvancedBoneMeal;
    }
}
