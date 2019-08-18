package com.lazynessmind.farmingtools.util;

import net.minecraft.block.BlockPlanks;
import net.minecraft.util.IStringSerializable;

public class Enum {

    public enum Planter implements IStringSerializable {

        IRON(0, "iron"),
        DIAMOND(1, "diamond"),
        GOLD(2, "gold"),
        EMERALD(3, "emerald");

        private static final Enum.Planter[] META_LOOKUP = new Enum.Planter[values().length];
        public int meta;
        public String name;

        Planter(int meta, String name){
            this.meta = meta;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getMetadata() {
            return meta;
        }

        public static Enum.Planter byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }
    }
}
