package com.lazynessmind.farmingtools.init;

import com.lazynessmind.farmingtools.block.BlockFertilizedSoil;
import com.lazynessmind.farmingtools.block.BlockGrowthPedestal;
import com.lazynessmind.farmingtools.block.BlockHarvester;
import com.lazynessmind.farmingtools.block.BlockPlanter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class FarmingToolsBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block FERTILIZED_SOIL = new BlockFertilizedSoil("fertilized_soil");
    public static final Block GROWTH_PEDESTAL = new BlockGrowthPedestal("growth_pedestal");
    public static final Block HARVESTER = new BlockHarvester("harvester");
    public static final Block PLANTER = new BlockPlanter("planter");
}
