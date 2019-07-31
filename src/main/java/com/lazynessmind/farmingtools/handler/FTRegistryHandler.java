package com.lazynessmind.farmingtools.handler;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.FarmingToolsConst;
import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import com.lazynessmind.farmingtools.init.FarmingToolsEnchants;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.init.item.ItemAdvancedBoneMeal;
import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FTRegistryHandler {

    public static void registry() {
        dispenserBehaviorRegistration();
        registryCustomRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(FarmingTools.instance, new GuiHandler());
    }

    private static void dispenserBehaviorRegistration() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FarmingToolsItems.ADVANCED_BONE_MEAL, new Bootstrap.BehaviorDispenseOptional() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.successful = true;
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset((EnumFacing) source.getBlockState().getValue(BlockDispenser.FACING));
                ItemAdvancedBoneMeal.doBoneMealEffectOnGround(FarmingToolsItems.ADVANCED_BONE_MEAL.getDefaultInstance(), world, blockpos);
                return stack;
            }
        });
    }

    /* Just used to get recipes with items that cant be used in JSON METHOD */
    private static void registryCustomRecipes() {
        GameRegistry.addShapedRecipe(new ResourceLocation(FarmingToolsConst.MODID, "farmingtools.recipe"), null, new ItemStack(Item.getItemFromBlock(FarmingToolsBlocks.FERTILIZED_SOIL)),
                new String[]{
                        "AAA",
                        "ABA",
                        "AAA"
                },
                'A', Blocks.DIRT,
                'B', Items.POTIONITEM.getDefaultInstance().getItem()
        );
    }

    public static void registryHoeRightClickOnCrops(EntityPlayer player, EnumHand hand, World world, BlockPos pos) {
        if (!world.isRemote) {
            if (player.getHeldItem(hand) != ItemStack.EMPTY && player.getActiveHand() == EnumHand.MAIN_HAND) {
                if (world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                    if (EnchantmentHelper.getMaxEnchantmentLevel(FarmingToolsEnchants.HOEHARVEST, player) > 0) {
                        BlockCrops crop = (BlockCrops) world.getBlockState(pos).getBlock();
                        for (BlockPos poss : FarmUtils.checkInRange(1, pos, 1, true)) {
                            FarmUtils.farmAndDrop(crop, world, poss, world.getBlockState(poss), true);
                            world.setBlockState(pos, crop.getDefaultState());
                            if (!player.isCreative()) player.getHeldItem(hand).damageItem(1, player);
                        }
                    } else if (player.getHeldItem(hand).getItem() instanceof ItemHoe) {
                        BlockCrops crop = (BlockCrops) world.getBlockState(pos).getBlock();
                        FarmUtils.farmAndDrop(crop, world, pos, world.getBlockState(pos), true);
                        world.setBlockState(pos, crop.getDefaultState());
                        if (!player.isCreative()) player.getHeldItem(hand).damageItem(1, player);
                    }
                }
            }
        }
    }

    public static void harvestWithEnchant(EntityPlayer player, EnumHand hand, World world, BlockPos pos) {
        if (!world.isRemote) {
            if (player.getHeldItem(hand) != ItemStack.EMPTY && player.getActiveHand() == EnumHand.MAIN_HAND) {
                if (world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                    BlockCrops crop = (BlockCrops) world.getBlockState(pos).getBlock();
                    for (BlockPos poss : FarmUtils.checkInRange(1, pos, 1, true)) {
                        if(FarmUtils.checkBlockInPos(crop, world, poss)){
                            FarmUtils.farmAndDrop(crop, world, poss, world.getBlockState(poss), true);
                            world.setBlockState(poss, crop.getDefaultState());
                            if (!player.isCreative()) player.getHeldItem(hand).damageItem(1, player);
                        }
                    }
                }
            }
        }
    }
}

