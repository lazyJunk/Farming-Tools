package com.lazynessmind.farmingtools.init.item;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import com.lazynessmind.farmingtools.util.RarityUtil;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class ItemAdvancedBoneMeal extends FTItem {

    private final int maxAgeStem = 7;
    private final int maxAgeNetherWart = 3;

    public ItemAdvancedBoneMeal(String name) {
        super(name, RarityUtil.LEGENDARY);
        setMaxStackSize(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GREEN + "Better than normal bone meal.");
        tooltip.add(TextFormatting.GREEN + "But do the same thing as bone meal.");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //Bone meal effect
        if (!worldIn.isRemote) {
            if (worldIn.getBlockState(pos).getBlock() == Blocks.GRASS) {
                doBoneMealEffectOnGround(player.getHeldItem(hand), worldIn, pos);
                return EnumActionResult.SUCCESS;
            }
        }

        if (worldIn.getBlockState(pos).getBlock() instanceof BlockCrops) {
            BlockCrops crops = (BlockCrops) worldIn.getBlockState(pos).getBlock();
            grow(worldIn, pos, crops, crops.getMaxAge(), player, hand);
            return EnumActionResult.SUCCESS;
        } else if (worldIn.getBlockState(pos).getBlock() instanceof BlockStem) {
            BlockStem stem = (BlockStem) worldIn.getBlockState(pos).getBlock();
            PropertyInteger AGE = BlockStem.AGE;
            int maxAge = stem.getDefaultState().getValue(BlockStem.AGE) + Math.max(stem.getMetaFromState(stem.getDefaultState()), maxAgeStem);
            if (checkIfIsMaxAge(maxAge, AGE.getAllowedValues())) {
                grow(worldIn, pos, stem, maxAge, player, hand);
                return EnumActionResult.SUCCESS;
            }
        } else if (worldIn.getBlockState(pos).getBlock() instanceof BlockNetherWart){
            BlockNetherWart netherWart = (BlockNetherWart) worldIn.getBlockState(pos).getBlock();
            PropertyInteger AGE = BlockNetherWart.AGE;
            int maxAge = netherWart.getDefaultState().getValue(AGE) + Math.max(netherWart.getMetaFromState(netherWart.getDefaultState()), maxAgeNetherWart);
            if (checkIfIsMaxAge(maxAge, AGE.getAllowedValues())) {
                grow(worldIn, pos, netherWart, maxAge, player, hand);
                return EnumActionResult.SUCCESS;
            }
        } else if (worldIn.getBlockState(pos).getBlock() instanceof BlockReed) {
            BlockReed sugarCane = (BlockReed)worldIn.getBlockState(pos).getBlock();
            growSugarCane(sugarCane, worldIn, pos, player, hand);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    private void grow(World worldIn, BlockPos pos, Block block, int maxAge, EntityPlayer player, EnumHand hand) {
        if (worldIn.getBlockState(pos) != block.getStateFromMeta(maxAge)) {
            worldIn.setBlockState(pos, block.getStateFromMeta(maxAge));
            if (!player.isCreative()) {
                player.getHeldItem(hand).shrink(1);
            }
            ParticleCreator.spawnParticle(EnumParticleTypes.SPELL, worldIn, pos, 15, worldIn.rand);
        }
    }

    private boolean checkIfIsMaxAge(int givenAge, Collection<Integer> allAges) {
        for (int i : allAges) {
            if (givenAge == i) {
                return true;
            }
        }
        return false;
    }

    private void growSugarCane(Block crop, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand) {
        BlockReed reed = (BlockReed) crop;
        if (worldIn.getBlockState(pos.down()).getBlock() != Blocks.REEDS || worldIn.getBlockState(pos.up()) == Blocks.AIR) {
            if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
                showGrowParticles(worldIn, pos);
                if (!player.isCreative()) {
                    player.getHeldItem(hand).shrink(1);
                }
            }
            for (int i = 0; i < 3; i++) {
                BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
                worldIn.setBlockState(newPos, reed.getDefaultState(), 2);
            }
        }
    }

    private void showGrowParticles(World world, BlockPos pos) {
        ParticleCreator.spawnParticle(EnumParticleTypes.SPELL, world, pos, 15, world.rand);
    }

    public static void doBoneMealEffectOnGround(ItemStack itemStack, World worldIn, BlockPos pos) {
        int range = worldIn.rand.nextInt(FarmingToolsConfigs.advancedBoneMealRange);
        for (int x = -range; x < range; x++) {
            for (int z = -range; z < range; z++) {
                ItemDye.applyBonemeal(itemStack, worldIn, pos.add(x, 0, z));
            }
        }
    }
}
