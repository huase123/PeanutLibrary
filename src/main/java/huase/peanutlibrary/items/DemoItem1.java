package huase.peanutlibrary.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import huase.peanutlibrary.ModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DemoItem1 extends Item {
    public DemoItem1() {
        super(new Properties());
    }

    public static UUID uuid = UUID.nameUUIDFromBytes(("sattributename").getBytes(StandardCharsets.UTF_8));


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) { // Only call code once as the tick event is called twice every tick
            Player player = Minecraft.getInstance().player;
            if (event.phase == TickEvent.Phase.END && player != null  && player.level().isClientSide) {
                if (Minecraft.getInstance().options.keyJump.isDown()) {
                    for (AttributeModifier modifier : player.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).getModifiers()) {
                        String name = modifier.getName();
                        player.sendSystemMessage(Component.translatable(name));
                    }

                }
            }
        }

    }
}
