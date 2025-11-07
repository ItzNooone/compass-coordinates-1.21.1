package name.compass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.nbt.NbtCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompassCoordinates implements ClientModInitializer {
    public static final String MOD_ID = "compass-coordinates";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient && hand == Hand.MAIN_HAND) {
                if (player.getStackInHand(hand).isOf(Items.COMPASS)) {
                    ItemStack stack = player.getStackInHand(hand);

                    if (stack.hasNbt()) {
                        NbtCompound nbt = stack.getNbt();
                        if(nbt != null && nbt.contains("LodestonePos")) {
                            NbtCompound lodestonePos = nbt.getCompound("LodestonePos");
                            int lx = lodestonePos.getInt("X");
                            int lz = lodestonePos.getInt("Z");
                            player.sendMessage(Text.literal("Lodestone at X: " + lx + " Z: " + lz), true);
                            return TypedActionResult.success(stack);
                        }
                    }

                    int x = (int) player.getX();
                    int z = (int) player.getZ();
                    player.sendMessage(Text.literal("X: " + x + " Z: " + z), true);
                    return TypedActionResult.success(player.getStackInHand(hand));
                }
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });


    }
}