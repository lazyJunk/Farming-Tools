package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityHarvester extends FTTileEntity implements ITickable, IRange, IRedPower {

    //Tile Data
    public int range = 2;
    public int yRange = 2;
    private boolean showRangeArea = false;
    private boolean needsRedstonePower = false;
    private ItemStackHandler handler = new ItemStackHandler(1);

    private int doWorkStartTime;
    private int doWorkEndTime = 150;

    @Override
    public void writeNBT(NBTTagCompound compound) {
        compound.setTag("Inventory", this.handler.serializeNBT());
        compound.setInteger("Range", this.range);
        compound.setBoolean("ShowRangeArea", this.showRangeArea);
        compound.setBoolean("NeedRedstonePower", this.needsRedstonePower);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.range = compound.getInteger("Range");
        this.showRangeArea = compound.getBoolean("ShowRangeArea");
        this.needsRedstonePower = compound.getBoolean("NeedRedstonePower");
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
        if (doWork()) {
            harvestBlock(pos);
        }
    }

    private void harvestBlock(BlockPos pos) {
        if (!world.isRemote) {
            for (BlockPos poss : FarmUtils.checkInRange(range, pos, yRange, false)) {
                if (world.getBlockState(poss).getBlock() instanceof BlockCrops) {
                    BlockCrops crops = (BlockCrops) world.getBlockState(poss).getBlock();
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            if (FarmUtils.canFarm(crops, world, poss) &&hasHoeOnSlot()) {
                                FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                                if(handler.getStackInSlot(0).isItemStackDamageable()){
                                    handler.getStackInSlot(0).damageItem(1, Minecraft.getMinecraft().player);
                                }
                                doWorkStartTime = 0;
                            }
                        }
                    } else {
                        if (FarmUtils.canFarm(crops, world, poss) && hasHoeOnSlot()) {
                            FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                            if(handler.getStackInSlot(0).isItemStackDamageable()){
                                handler.getStackInSlot(0).damageItem(1, Minecraft.getMinecraft().player);
                            }
                            doWorkStartTime = 0;
                        }
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

    private boolean isPowered() {
        return world.isBlockPowered(pos);
    }

    @Override
    public boolean canShowRangeArea() {
        return this.showRangeArea;
    }

    @Override
    public void showRangeArea(boolean state) {
        this.showRangeArea = state;
    }

    @Override
    public boolean needRedstonePower() {
        return this.needsRedstonePower;
    }

    @Override
    public void setNeedRedstonePower(boolean state) {
        this.needsRedstonePower = state;
    }
}
