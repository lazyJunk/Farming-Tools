package com.lazynessmind.farmingtools.util;

import net.minecraft.util.IStringSerializable;

public class Enum {

    public enum Type implements IStringSerializable {

        IRON(0, "iron"),
        GOLD(1, "gold"),
        DIAMOND(2, "diamond"),
        EMERALD(3, "emerald");

        public int meta;
        public String name;

        Type(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
