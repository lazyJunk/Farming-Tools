package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.BasicItemHandler;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityHarvester extends TileEntityPedestal implements ITickable {

    private int timer;

    public TileEntityHarvester() {
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
                    this.harvestBlock(this.pos);
                }
            }
        }
        this.markDirty();
    }

    private void harvestBlock(BlockPos pos) {
        ItemStack mainSlot = this.getStackInSlot(0);

        if (!mainSlot.isEmpty()) {
            if (mainSlot.getItem() instanceof ItemHoe) {
                for (BlockPos poss : FarmUtils.checkInRange(1, pos, 3, false)) {
                    if (this.world.getBlockState(poss).getBlock() instanceof BlockCrops) {
                        BlockCrops crops = (BlockCrops) this.world.getBlockState(poss).getBlock();
                        if (this.needRedstonePower()) {
                            if (this.world.isBlockPowered(pos)) {
                                if (FarmUtils.canFarm(crops, this.world, poss) && this.hasHoeOnSlot()) {
                                    FarmUtils.farmAndDrop(crops, this.world, poss, this.world.getBlockState(poss), true);
                                    EntityPlayer player = Minecraft.getMinecraft().player;
                                    if (player != null && !player.isCreative()) {
                                        this.getEnergy().removeFromBuffer(this.getCostPerWork());
                                        if (mainSlot.isItemStackDamageable()) {
                                            mainSlot.damageItem(1, player);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (FarmUtils.canFarm(crops, this.world, poss) && this.hasHoeOnSlot()) {
                                FarmUtils.farmAndDrop(crops, this.world, poss, this.world.getBlockState(poss), true);
                                EntityPlayer player = Minecraft.getMinecraft().player;
                                if (player != null && !player.isCreative()) {
                                    this.getEnergy().removeFromBuffer(this.getCostPerWork());
                                    if (mainSlot.isItemStackDamageable()) {
                                        mainSlot.damageItem(1, player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean hasHoeOnSlot() {
        return !this.getStackInSlot(0).isEmpty();
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
        return index == 0 && stack.getItem() instanceof ItemHoe;
    }
}
