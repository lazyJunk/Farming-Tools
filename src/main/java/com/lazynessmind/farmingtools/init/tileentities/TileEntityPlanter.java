package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityPlanter extends FTTileEntity implements ITickable, IRange, IRedPower {

    //Tile Data
    public int range = 1;
    private boolean showRangeArea = false;
    private boolean needRedstonePower = false;
    private ItemStackHandler handler = new ItemStackHandler(1);
    private IBlockState crop;
    private int doWorkStartTime;
    private int doWorkEndTime = 10;

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setTag("Items", this.handler.serializeNBT());
        compound.setBoolean("ShowRangeArea", this.showRangeArea);
        compound.setBoolean("NeedRedstonePower", this.needRedstonePower);
        compound.setInteger("Range", this.range);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        this.handler.deserializeNBT(compound.getCompoundTag("Items"));
        this.showRangeArea = compound.getBoolean("ShowRangeArea");
        this.needRedstonePower = compound.getBoolean("NeedRedstonePower");
        this.range = compound.getInteger("Range");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) this.handler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        Item itemSlot = handler.getStackInSlot(0).getItem();
        if (!world.isRemote) {
            if (!handler.getStackInSlot(0).isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    crop = ((IPlantable) handler.getStackInSlot(0).getItem()).getPlant(world, pos);
                }
            }
            if (doWorkStartTime < doWorkEndTime) {
                doWorkStartTime++;
            }
        }
        plantCrop(crop);
    }

    private void plantCrop(IBlockState crop) {
        if (!world.isRemote) {
            for (BlockPos pos : FarmUtils.checkInXZRange(range, getPos(), false)) {
                if (FarmUtils.canPlantCrop(pos, world)) {
                    if (crop != null && !handler.getStackInSlot(0).isEmpty()) {
                        if (needRedstonePower()) {
                            if (world.isBlockPowered(pos)) {
                                if (doWork()) {
                                    world.setBlockState(pos, crop);
                                    handler.extractItem(0, 1, false);
                                    doWorkStartTime = 0;
                                }
                            }
                        } else {
                            if (doWork()) {
                                world.setBlockState(pos, crop);
                                handler.extractItem(0, 1, false);
                                doWorkStartTime = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean doWork() {
        return doWorkStartTime >= doWorkEndTime;
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    //IRange implementations
    @Override
    public void showRangeArea(boolean show) {
        this.showRangeArea = show;
        markDirty();
    }

    @Override
    public boolean canShowRangeArea() {
        return this.showRangeArea;
    }

    //IRedPower implementations
    @Override
    public void setNeedRedstonePower(boolean need) {
        this.needRedstonePower = need;
        markDirty();
    }

    @Override
    public boolean needRedstonePower() {
        return needRedstonePower;
    }
}
