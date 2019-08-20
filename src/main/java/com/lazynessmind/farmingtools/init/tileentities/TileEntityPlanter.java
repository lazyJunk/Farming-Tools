package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.state.IBlockState;
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

public class TileEntityPlanter extends TileEntityPedestal implements ITickable {

    private ItemStackHandler handler = new ItemStackHandler(1);
    private IBlockState crop;

    public TileEntityPlanter() {
        super(false, false, 100, 1, 0);
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);
        compound.setTag("Items", this.handler.serializeNBT());
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);
        this.handler.deserializeNBT(compound.getCompoundTag("Items"));
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
        if (!world.isRemote) {
            setType(getBlockMetadata());
            setMaxCooldown(UpgradeUtil.getMaxCooldownFromType(getBlockMetadata()));
            setRange(UpgradeUtil.getRangeFromType(getBlockMetadata()));
            Item itemSlot = handler.getStackInSlot(0).getItem();
            if (!handler.getStackInSlot(0).isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    crop = ((IPlantable) handler.getStackInSlot(0).getItem()).getPlant(world, pos);
                }
            }
            plantCrop(crop);
        }
    }

    private void plantCrop(IBlockState crop) {
        for (BlockPos pos : FarmUtils.checkInXZRange(getRange(), getPos(), false)) {
            if (FarmUtils.canPlantCrop(pos, world)) {
                if (crop != null && !handler.getStackInSlot(0).isEmpty()) {
                    if (needRedstonePower()) {
                        work(() -> {
                            if (world.isBlockPowered(pos)) {
                                world.setBlockState(pos, crop);
                                handler.extractItem(0, 1, false);
                            }
                        });
                    } else {
                        work(() -> {
                            world.setBlockState(pos, crop);
                            handler.extractItem(0, 1, false);
                        });
                    }
                }
            }
        }
    }

    public ItemStackHandler getHandler() {
        return handler;
    }
}
