package com.lazynessmind.farmingtools.block.tileentities.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public abstract class TileSidedInventoryBase extends FTTileEntity implements ISidedInventory {

    private BasicItemHandler inv;

    public TileSidedInventoryBase(int size) {
        this.inv = new BasicItemHandler(size);
    }

    public BasicItemHandler getInv() {
        return inv;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return this.getSlotForFace(side);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.canInsertOnSlot(index, itemStackIn, direction);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return this.canExtractOnSlot(index, stack, direction);
    }

    @Override
    public int getSizeInventory() {
        return this.getInv().getStacks().size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.getInv().getStacks()) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.getInv().getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.getInv().getStacks(), index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.getInv().getStacks(), index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.getInv().getStacks().set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.isItemValid(index, stack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.getInv().getStacks().clear();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("");
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    protected abstract int[] getSlotForFace(EnumFacing facing);

    protected abstract boolean canInsertOnSlot(int slotIndex, ItemStack itemStackIn, EnumFacing direction);

    protected abstract boolean canExtractOnSlot(int slotIndex, ItemStack itemStackIn, EnumFacing direction);

    protected abstract boolean isItemValid(int index, ItemStack stack);
}
