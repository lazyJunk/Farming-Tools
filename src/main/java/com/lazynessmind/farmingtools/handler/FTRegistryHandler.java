package com.lazynessmind.farmingtools.handler;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.init.item.ItemAdvancedBoneMeal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FTRegistryHandler {

    public static void registry() {
        dispenserBehaviorRegistration();
        registCustomRecipes();
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
    private static void registCustomRecipes(){
        GameRegistry.addShapedRecipe(new ResourceLocation(FarmingToolsConst.MODID, "farmingtools.recipe"), null, new ItemStack(Item.getItemFromBlock(FarmingToolsBlocks.FERTILIZED_SOIL)),
                new String[] {
                        "AAA",
                        "ABA",
                        "AAA"
                },
                'A', Blocks.DIRT,
                'B', Items.POTIONITEM.getDefaultInstance().getItem()
        );
    }
}
