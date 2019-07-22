package com.lazynessmind.farmingtools.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class ParticleCreator {

    @SideOnly(Side.CLIENT)
    public static void spawnParticle(EnumParticleTypes particle, World worldIn, BlockPos pos, int amount, Random rnd) {
        if (amount == 0) {
            amount = 1;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getMaterial() != Material.AIR) {
            for (int i = 0; i < amount; ++i) {
                double d0 = rnd.nextGaussian() * 0.02D;
                double d1 = rnd.nextGaussian() * 0.02D;
                double d2 = rnd.nextGaussian() * 0.02D;
                worldIn.spawnParticle(particle, (double) ((float) pos.getX() + rnd.nextFloat()), (double) pos.getY() + (double) rnd.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, (double) ((float) pos.getZ() + rnd.nextFloat()), d0, d1, d2);
            }
        } else {
            for (int i1 = 0; i1 < amount; ++i1) {
                double d0 = rnd.nextGaussian() * 0.02D;
                double d1 = rnd.nextGaussian() * 0.02D;
                double d2 = rnd.nextGaussian() * 0.02D;
                worldIn.spawnParticle(particle, (double) ((float) pos.getX() + rnd.nextFloat()), (double) pos.getY() + (double) rnd.nextFloat() * 1.0f, (double) ((float) pos.getZ() + rnd.nextFloat()), d0, d1, d2, new int[0]);
            }
        }
    }
}
