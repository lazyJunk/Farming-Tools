package com.lazynessmind.farmingtools.handler;

import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityFertilizedSoil;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityGrowthPedestal;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(FarmingToolsItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(FarmingToolsBlocks.BLOCKS.toArray(new Block[0]));
        GameRegistry.registerTileEntity(TileEntityFertilizedSoil.class, "farmingtools:poweredDirt");
        GameRegistry.registerTileEntity(TileEntityGrowthPedestal.class, "farmingtools:growthPedestal");
        GameRegistry.registerTileEntity(TileEntityHarvester.class, "farmingtools:harvester");
        GameRegistry.registerTileEntity(TileEntityPlanter.class, "farmingtools:planter");
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : FarmingToolsItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
                ((IHasModel) item).registerModels(item.getMetadata(item.getDefaultInstance()));
            }
        }

        for (Block block : FarmingToolsBlocks.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event){
        FTRegistryHandler.registryHoeRightClickOnCrops(event.getEntityPlayer(), event.getHand(), event.getWorld(), event.getPos());
    }
}
