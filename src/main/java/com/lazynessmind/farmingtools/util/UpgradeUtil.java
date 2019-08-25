package com.lazynessmind.farmingtools.util;

import com.lazynessmind.farmingtools.init.tileentities.TileEntityPedestal;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import net.minecraft.tileentity.TileEntity;

public class UpgradeUtil {

    public static String getNameFromType(int type){
        switch (type){
            case 0:
                return "Iron";
            case 1:
                return "Diamond";
            case 2:
                return "Gold";
            case 3:
                return "Emerald";
        }
        return "I don´t know... Something is wrong!";
    }

    public static int getRangeFromType(int type){
        switch (type){
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
        }
        return 666;
    }

    public static int getVerticalRangeFromPedestal(int id){
        switch (id){
            case 0:
                return 3;
            case 1:
            case 2:
                return 1;
        }
        return 666;
    }

    public static int getMaxCooldownFromType(int type){
        switch (type){
            case 0:
                return 200;
            case 1:
                return 150;
            case 2:
                return 100;
            case 3:
                return 50;
        }
        return 69;
    }
}
