package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.container.InputStackHandler;
import com.lazynessmind.farmingtools.container.OutputStackHandler;
import com.lazynessmind.farmingtools.init.FarmingToolsCapabilities;
import com.lazynessmind.farmingtools.init.capabilities.Worker;
import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked cast")
public class TileEntityPedestal extends FTTileEntity implements IRange, IRedPower {

    //Properties
    private boolean showRange, redPower;
    //Range
    private int range, yRange;
    //Current Tier
    private int type;
    //Work mode (Burnable, Energy or Magic)
    private int workMode, currentFuel;

    //Capabilities
    private ItemStackHandler mainSlot;
    private ItemStackHandler fuelSlot;
    private InputStackHandler mainInput;
    private InputStackHandler fuelInput;
    private Worker worker;

    public TileEntityPedestal(boolean showRange, boolean redPower, int maxCooldown, int range, int yRange, int numSlots, Item valid) {
        this.showRange = showRange;
        this.redPower = redPower;
        this.range = range;
        this.yRange = yRange;
        this.type = 0;
        this.workMode = 0;
        this.currentFuel = 0;

        this.mainSlot = new ItemStackHandler();
        this.fuelSlot = new ItemStackHandler();
        this.mainInput = new InputStackHandler(mainSlot);
        this.fuelInput = new InputStackHandler(fuelSlot);

        this.worker = new Worker(maxCooldown);
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setTag("Items", this.mainSlot.serializeNBT());
        compound.setTag("Worker", this.worker.serializeNBT());
        compound.setBoolean("ShowRange", this.showRange);
        compound.setBoolean("NeedRedstone", this.redPower);
        compound.setInteger("Range", this.range);
        compound.setInteger("yRange", this.yRange);
        compound.setInteger("Type", this.type);
        compound.setInteger("FuelMode", this.workMode);
        compound.setInteger("CurrentFuel", this.currentFuel);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        this.mainSlot.deserializeNBT(compound.getCompoundTag("Items"));
        this.worker.deserializeNBT(compound.getCompoundTag("Worker"));
        this.showRange = compound.getBoolean("ShowRange");
        this.redPower = compound.getBoolean("NeedRedstone");
        this.range = compound.getInteger("Range");
        this.yRange = compound.getInteger("yRange");
        this.type = compound.getInteger("Type");
        this.workMode = compound.getInteger("FuelMode");
        this.currentFuel = compound.getInteger("CurrentFuel");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == FarmingToolsCapabilities.CAPABILITY_WORKER;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if (facing == EnumFacing.UP){
                return (T) this.mainInput;
            } else {
                return (T) this.fuelSlot;
            }
        }
        if (capability == FarmingToolsCapabilities.CAPABILITY_WORKER) return (T) this.worker;
        return super.getCapability(capability, facing);
    }

    public void updateTile() {
        if (getFuelHandler().getStackInSlot(0).getCount() > 0) {
            ItemStack temp = getFuelHandler().getStackInSlot(0);
            int amountInSlot = temp.getCount();
            if (temp.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
                //each coal gives 8, one block contains 9 coals, final product = 8x9 or 72
                int valueFromEachBlock = 8 * 9; //72
                addToFuel(amountInSlot * valueFromEachBlock);
                System.out.println("Added " + (amountInSlot*valueFromEachBlock) + " Now have: " + getFuel());
            } else {
                addToFuel(amountInSlot);
                System.out.println("Added " + (amountInSlot) + " Now have: " + getFuel());
            }
            temp.shrink(amountInSlot);
        }
        markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }

    private void addToFuel(int amt) {
        this.currentFuel += amt;
    }

    public void removeFromFuel(int amt) {
        this.currentFuel -= amt;
    }

    public int getFuel() {
        return currentFuel;
    }

    @Override
    public boolean canShowRangeArea() {
        return this.showRange;
    }

    @Override
    public void showRangeArea(boolean state) {
        this.showRange = state;
        markDirty();
    }

    @Override
    public boolean needRedstonePower() {
        return this.redPower;
    }

    @Override
    public void setNeedRedstonePower(boolean state) {
        this.redPower = state;
        markDirty();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
        markDirty();
    }

    public int getVerticalRange() {
        return yRange;
    }

    public void setVerticalRange(int yRange) {
        this.yRange = yRange;
        markDirty();
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public ItemStackHandler getMainHandler() {
        return mainInput;
    }
    public ItemStackHandler getFuelHandler() {
        return fuelInput;
    }

    public Worker getWorker() {
        return worker;
    }

    public int getFuelMode() {
        return this.workMode;
    }

    public void setFuelMode(int mode) {
        this.workMode = mode;
    }

    public List<String> getProperties() {
        List<String> temp = new ArrayList<>();
        String type = TextFormatting.YELLOW + UpgradeUtil.getNameFromType(getType());
        String redRes = needRedstonePower() ? TextFormatting.GREEN + "On" : TextFormatting.RED + "Off";
        String rangeRes = canShowRangeArea() ? TextFormatting.GREEN + "On" : TextFormatting.RED + "Off";
        String range = TextFormatting.YELLOW + String.valueOf(UpgradeUtil.getRangeFromType(getType()));
        String vRange = TextFormatting.YELLOW + String.valueOf(UpgradeUtil.getVerticalRangeFromPedestal(getType()));
        String currentSpeed = TextFormatting.YELLOW + String.valueOf(getWorker().getMaxWork());
        String fuelMode = TextFormatting.YELLOW + String.valueOf(getFuelMode());
        String currentFuel = TextFormatting.YELLOW + String.valueOf(getFuel());
        temp.add("Type: " + type);
        temp.add("Redstone: " + redRes);
        temp.add("Show Range: " + rangeRes);
        temp.add("Range: " + range);
        temp.add("Vertical Range: " + vRange);
        temp.add("Cooldown: " + currentSpeed);
        temp.add("Fuel Mode: " + fuelMode);
        temp.add("Current Fuel: " + currentFuel);
        return temp;
    }

    public enum WORKMODE {
        FLAMMABLE(0, "Flammable"),
        ENERGY(1, "Energy"),
        MAGIC(2, "Magic");

        public int id;
        public String name;

        WORKMODE(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
