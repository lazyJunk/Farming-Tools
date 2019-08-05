package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityPlanter extends FTTileEntity implements ITickable {

    private ItemStackHandler handler = new ItemStackHandler(1);
    private IBlockState crop;
    private int doWorkStartTime;
    private int doWorkEndTime = 10;

    @Override
    public NBTTagCompound writeNBT(NBTTagCompound compound) {
        compound.setTag("Items", this.handler.serializeNBT());
        return compound;
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        this.handler.deserializeNBT(compound.getCompoundTag("Items"));
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
        if (!handler.getStackInSlot(0).isEmpty()) {
            crop = ((IPlantable) handler.getStackInSlot(0).getItem()).getPlant(world, pos);
        }
        if(doWorkStartTime < doWorkEndTime){
            doWorkStartTime++;
        }

        plantCrop(crop);
    }

    private void plantCrop(IBlockState crop) {
        if (!world.isRemote) {
            for (BlockPos pos : FarmUtils.checkInRange(4, getPos(), 1, false)) {
                if (FarmUtils.canPlantCrop(pos, world)) {
                    if (crop != null && !handler.getStackInSlot(0).isEmpty()) {
                        if(doWork()){
                            world.setBlockState(pos, crop);
                            handler.extractItem(0, 1, false);
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

    private boolean doWork(){
        return doWorkStartTime >= doWorkEndTime;
    }

    public ItemStackHandler getHandler() {
        return handler;
    }
}
