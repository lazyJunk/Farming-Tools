package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class TileEntityPlanter extends TileEntityPedestal implements ITickable {

    private IBlockState crop;
    private int timer;

    public TileEntityPlanter() {
        super(false, false, 1);
    }

    @Override
    public void update() {
        setType(getBlockMetadata());
        setRange(TypeUtil.getHorizontalRangeFromType(getType()));
        setTimeBetween(TypeUtil.getTimeBetweenFromType(getType()));
        if (!world.isRemote) {
            Item itemSlot = mainSlot().getItem();
            if (!mainSlot().isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    crop = ((IPlantable) mainSlot().getItem()).getPlant(world, pos);
                }
            }
            if(timer < getTimeBetween()){
                timer++;
            } else if(timer >= getTimeBetween()){
                timer = 0;
                plantCrop(crop);
            }
            this.markDirty();
        }
    }

    private void plantCrop(IBlockState crop) {
        for (BlockPos pos : FarmUtils.checkInXZRange(getRange(), getPos(), false)) {
            if (FarmUtils.canPlantCrop(pos, world)) {
                if (crop != null && !mainSlot().isEmpty() && mainSlot().getItem() instanceof ItemSeedFood || mainSlot().getItem() instanceof ItemSeeds) {
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            world.setBlockState(pos, crop);
                            if(!Minecraft.getMinecraft().player.isCreative()) getMainHandler().extractItem(0, 1, false);
                        }
                    } else {
                        world.setBlockState(pos, crop);
                        if(!Minecraft.getMinecraft().player.isCreative()) getMainHandler().extractItem(0, 1, false);
                    }
                }
            }
        }
    }
}
