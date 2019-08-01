package com.lazynessmind.farmingtools.init;

import com.lazynessmind.farmingtools.init.blocks.BlockFertilizedSoil;
import com.lazynessmind.farmingtools.init.blocks.BlockGrowthPedestal;
import com.lazynessmind.farmingtools.init.blocks.BlockHarvester;
import com.lazynessmind.farmingtools.init.blocks.BlockPlanter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class FarmingToolsBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block FERTILIZED_SOIL = new BlockFertilizedSoil(Material.GROUND, "fertilized_soil");
    public static final Block GROWTH_PEDESTAL = new BlockGrowthPedestal(Material.GROUND, "growth_pedestal");
    public static final Block HARVESTER = new BlockHarvester(Material.GROUND, "harvester");
    public static final Block PLANTER = new BlockPlanter(Material.GROUND, "planter");
}
