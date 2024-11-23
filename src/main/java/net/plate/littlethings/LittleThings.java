package net.plate.littlethings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.plate.littlethings.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;

public class LittleThings implements ModInitializer {
	public static final String MOD_ID = "littlethings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final int TICK_INTERVAL = 3;
	private static int tickCounter = 0;
	public static int RGB(int r, int g, int b) {
		return r*65536+g*256+b;
	}
	private static final int[] COLORS = {
			RGB(75, 0, 0), RGB(85, 0, 3), RGB(95, 0, 7), RGB(105, 0, 10), RGB(115, 0, 14),
			RGB(125, 0, 17), RGB(135, 0, 21), RGB(145, 0, 24), RGB(155, 0, 28), RGB(165, 0, 31),
			RGB(175, 0, 35), RGB(185, 0, 38), RGB(195, 0, 42), RGB(205, 0, 45), RGB(215, 0, 49),
			RGB(229, 0, 52), RGB(229, 0, 52), RGB(215, 0, 49), RGB(205, 0, 45), RGB(195, 0, 42),
			RGB(185, 0, 38), RGB(175, 0, 35), RGB(165, 0, 31), RGB(155, 0, 28), RGB(145, 0, 24),
			RGB(135, 0, 21), RGB(125, 0, 17), RGB(115, 0, 14), RGB(105, 0, 10), RGB(95, 0, 7),
			RGB(85, 0, 3), RGB(75, 0, 0)
	};


	private static int colorIndex = 0;

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			tickCounter++;
			if (tickCounter >= TICK_INTERVAL) {
				tickCounter = 0;
				colorIndex = (colorIndex + 1) % COLORS.length;

				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
					checkAndUpdateArmor(player);
					//System.out.println("checked");
				}
			}
		});

		LOGGER.info("Initializing " + MOD_ID);
	}

	private void checkAndUpdateArmor(ServerPlayerEntity player) {
		for (ItemStack armorPiece : player.getArmorItems()) {
			//System.out.println(armorPiece);
			if (isValidCrimsonLeatherArmor(armorPiece)) {
				updateArmorColor(armorPiece);
			}
		}
	}

	private void updateArmorColor(ItemStack stack) {
		if (stack.isEmpty()) return;
		//System.out.println("the color" + stack.get(DataComponentTypes.DYED_COLOR));
		DyedColorComponent dye = new DyedColorComponent(COLORS[colorIndex], true);
		stack.set(DataComponentTypes.DYED_COLOR, dye);
	}

	private boolean isValidCrimsonLeatherArmor(ItemStack stack) {
		if (stack.isEmpty()) return false;

		if (!isLeatherArmor(stack)) return false;

		String displayName = stack.get(DataComponentTypes.LORE).toString();
		//System.out.println(displayName);
		return displayName.contains("Crimson");
	}

	private boolean isLeatherArmor(ItemStack stack) {
		return stack.isOf(Items.LEATHER_HELMET) ||
				stack.isOf(Items.LEATHER_CHESTPLATE) ||
				stack.isOf(Items.LEATHER_LEGGINGS) ||
				stack.isOf(Items.LEATHER_BOOTS);
	}
}