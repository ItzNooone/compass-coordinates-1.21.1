package name.compass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
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