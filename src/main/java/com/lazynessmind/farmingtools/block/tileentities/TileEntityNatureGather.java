package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.FTTileEntity;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.NaturePower;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityNatureGather extends FTTileEntity implements ITickable {

    public long currentAmount;

    public int range = 3;

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);
        compound.setLong("Amount", currentAmount);
        compound.setInteger("Range", range);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);
        this.currentAmount = compound.getLong("Amount");
        this.range = compound.getInteger("Range");
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            currentAmount = NaturePower.getNaturePower(world, pos, range);
            for(BlockPos poss : FarmUtils.checkInXZRange(range, pos, false)){
                if(world.rand.nextInt(10) % 5 == 0){
                    ParticleCreator.spawnParticle(EnumParticleTypes.SPELL, world, poss, 10, world.rand);
                }
            }
        }
    }
}
