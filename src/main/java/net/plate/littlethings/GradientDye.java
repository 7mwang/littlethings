package net.plate.littlethings;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Arrays;

import static net.plate.littlethings.LittleThings.colorIndex;

public class GradientDye {
    public static int RGB(int r, int g, int b) {
        return r*65536+g*256+b;
    }
    static class Dye {
        String dyeName;
        int sR, sG, sB, eR, eG, eB;
        int[] COLORS = new int[32];
        public Dye(String dyeName, int sR, int sG, int sB, int eR, int eG, int eB) {
            this.COLORS = COLORS; // Fix array size to match 16 steps
            this.dyeName = dyeName;
            this.sR = sR;
            this.sG = sG;
            this.sB = sB;
            this.eR = eR;
            this.eG = eG;
            this.eB = eB;

            // Calculate the differences for the gradient
            int diffR = (eR - sR) / 15; // 15 steps to fill 16 colors (inclusive of both ends)
            int diffG = (eG - sG) / 15;
            int diffB = (eB - sB) / 15;

            System.out.println("Diff R: " + diffR);
            System.out.println("Diff G: " + diffG);
            System.out.println("Diff B: " + diffB);

            // Fill the COLORS array with the gradient
            for (int i = 0; i < 16; i++) {
                //System.out.println("Step " + i + ": " + sR + "," + sG + "," + sB);
                COLORS[i] = RGB(sR, sG, sB);
                sR += diffR;
                sG += diffG;
                sB += diffB;
            }
            for (int i = 0; i < 16; i++) {
                COLORS[COLORS.length-1-i] = COLORS[i];
            }
            System.out.println(Arrays.toString(COLORS));
    }
        public static void checkAndUpdateArmor(ServerPlayerEntity player, Dye dye) {
            for (ItemStack armorPiece : player.getArmorItems()) {
                if (isDyedArmor(armorPiece, dye)) {
                    updateArmorColor(armorPiece, dye);
                }
            }
        }
    }
    public static boolean isDyedArmor(ItemStack stack, Dye dye) {
        if (stack.isEmpty()) return false;
        String displayName = stack.get(DataComponentTypes.LORE).toString();
        return displayName.contains(dye.dyeName);
    }
    public static void updateArmorColor(ItemStack stack, Dye customDye) {
        if (stack.isEmpty()) return;
        DyedColorComponent dye = new DyedColorComponent(customDye.COLORS[colorIndex], true);
        stack.set(DataComponentTypes.DYED_COLOR, dye);
    }
}
