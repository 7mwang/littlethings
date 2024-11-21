package net.plate.littlethings.item;

import net.plate.littlethings.item.ModDyeColor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.nbt.NbtCompound;

import javax.swing.*;

public class CrimsonDyeItem extends Item {
    public CrimsonDyeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient && entity.getType() == EntityType.SHEEP) {
            SheepEntity sheep = (SheepEntity) entity;

                int[] colors = {0xFF0000, 0xFFA500}; // Red, Orange
                int currentColor = sheep.getColor().getFireworkColor();
                int newColor = getNextColor(currentColor, colors);
                sheep.setColor(DyeColor.RED);
            }


            return super.useOnEntity(stack, user, entity, hand);
        }



    private int getNextColor(int currentColor, int[] colors) {
        // Cycle through the colors array
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == currentColor) {
                return colors[(i + 1) % colors.length]; // Get next color, wrapping around
            }
        }
        return colors[0]; // Default to the first color if none match
    }
}
