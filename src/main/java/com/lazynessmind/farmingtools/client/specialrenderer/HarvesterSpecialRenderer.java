package com.lazynessmind.farmingtools.client.specialrenderer;

import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class HarvesterSpecialRenderer extends TileEntitySpecialRenderer<TileEntityHarvester> {

    private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, ItemStack.EMPTY);

    private float yaw = 0f;


    @Override
    public void render(TileEntityHarvester te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(yaw < 360f){
            yaw += 1f;
        } else if(yaw >= 360f){
            yaw = 0f;
        }

        entityItem.setItem(te.getHandler().getStackInSlot(0));

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(1f, 1.1f, 1f);
            GlStateManager.rotate(yaw, 0f, 0.1f, 0f);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0, 0, 0, yaw, 0f, false);
        }
        GlStateManager.popMatrix();
    }
}