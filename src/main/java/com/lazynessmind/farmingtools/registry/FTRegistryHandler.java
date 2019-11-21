package com.lazynessmind.farmingtools.registry;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.FarmingToolsConst;
import com.lazynessmind.farmingtools.client.specialrenderer.GrowthPedestalSpecialRenderer;
import com.lazynessmind.farmingtools.client.specialrenderer.HarvesterSpecialRenderer;
import com.lazynessmind.farmingtools.client.specialrenderer.PlanterSpecialRenderer;
import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.item.ItemAdvancedBoneMeal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityGrowthPedestal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityHarvester;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.util.ChatUtil;
import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FTRegistryHandler {

    public static void registry() {
        dispenserBehaviorRegistration();
        registryCustomRecipes();
        bindSpecialRenderer();
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

    private static void bindSpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHarvester.class, new HarvesterSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlanter.class, new PlanterSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrowthPedestal.class, new GrowthPedestalSpecialRenderer());
    }

    static void registryHoeRightClickOnCrops(EntityPlayer player, EnumHand hand, World world, BlockPos pos) {
        if (!world.isRemote) {
            if (player.getHeldItem(hand) != ItemStack.EMPTY && player.getActiveHand() == EnumHand.MAIN_HAND) {
                if (world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                    if (player.getHeldItem(hand).getItem() instanceof ItemHoe) {
                        BlockCrops crop = (BlockCrops) world.getBlockState(pos).getBlock();
                        if (FarmUtils.canFarm(crop, world, pos)) {
                            FarmUtils.farmAndDrop(crop, world, pos, world.getBlockState(pos), true);
                            world.setBlockState(pos, crop.getDefaultState());
                            if (!player.isCreative()) player.getHeldItem(hand).damageItem(1, player);
                        }
                    }
                }
            }
        }
    }

    static void harvestWithEnchant(EntityPlayer player, EnumHand hand, World world, BlockPos pos) {
        if (!world.isRemote) {
            if (player.getHeldItem(hand) != ItemStack.EMPTY && player.getActiveHand() == EnumHand.MAIN_HAND) {
                if (world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                    BlockCrops crop = (BlockCrops) world.getBlockState(pos).getBlock();
                    for (BlockPos poss : FarmUtils.checkInRange(1, pos, 1, true)) {
                        if (FarmUtils.checkBlockInPos(crop, world, poss) && FarmUtils.canFarm(crop, world, poss)) {
                            FarmUtils.farmAndDrop(crop, world, poss, world.getBlockState(poss), true);
                            world.setBlockState(poss, crop.getDefaultState());
                            if (!player.isCreative()) player.getHeldItem(hand).damageItem(1, player);
                        }
                    }
                }
            }
        }
    }

    static void onWorldLoaded(World world, Entity entity){
        if(FarmingToolsConfigs.showUpdateMessage){
            if(!world.isRemote){
                if(entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    if(ForgeVersion.getStatus() == ForgeVersion.Status.OUTDATED){
                        ChatUtil.chatMessage(player, TextFormatting.BOLD + "[Farming Tools]" + TextFormatting.RED + " Current version is outdated! " + TextFormatting.WHITE + "Check the mod page to update. :)");
                    }
                }
            }
        }
    }
}

