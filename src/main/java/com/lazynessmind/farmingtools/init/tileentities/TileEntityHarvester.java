package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityHarvester extends FTTileEntity implements ITickable {

    public int range = 1;
    private ItemStackHandler handler = new ItemStackHandler(1);
    private int doWorkStartTime;
    private int doWorkEndTime = 150;
    private boolean showEffectArea = false;

    @Override
    public NBTTagCompound writeNBT(NBTTagCompound compound) {
        compound.setTag("Inventory", this.handler.serializeNBT());
        compound.setInteger("Range", this.range);
        return compound;
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.range = compound.getInteger("Range");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (doWorkStartTime < doWorkEndTime) {
            doWorkStartTime++;
        }
        if (!world.isBlockPowered(pos)) {
            if (!handler.getStackInSlot(0).isEmpty()) {
                if (doWork())
                    harvestBlock(getPos());
            }
        }
    }

    private void harvestBlock(BlockPos pos) {
        if (!world.isRemote) {
            for (BlockPos poss : FarmUtils.checkInRange(range, pos, 1, false)) {
                if (world.getBlockState(poss).getBlock() instanceof BlockCrops) {
                    BlockCrops crops = (BlockCrops) world.getBlockState(poss).getBlock();
                    if (FarmUtils.canFarm(crops, world, poss)) {
                        FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                        doWorkStartTime = 0;
                    }
                }
            }
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public boolean hasHoeOnSlot() {
        return !this.handler.getStackInSlot(0).isEmpty();
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    private boolean doWork() {
        return doWorkStartTime >= doWorkEndTime;
    }

    public void setShowEffectArea(boolean showEffectArea) {
        this.showEffectArea = showEffectArea;
    }

    public boolean showEffectArea() {
        return showEffectArea;
    }

    public void setRange(int range) {
        this.range = range;
        markDirty();
    }

    public int getRange() {
        return range;
    }
}
