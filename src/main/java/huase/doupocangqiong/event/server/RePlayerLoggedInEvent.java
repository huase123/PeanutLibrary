package huase.doupocangqiong.event.server;


import huase.doupocangqiong.ModMain;
import huase.doupocangqiong.registries.FirstJoinData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RePlayerLoggedInEvent {
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        // 只在服务器端执行
        if (player.level() instanceof ServerLevel level) {
            FirstJoinData data = FirstJoinData.get(level);
            UUID playerUuid = player.getUUID();
//            AdvenceInit.join_wrold.trigger((ServerPlayer) player);
            if (data.isFirstTime(playerUuid)) {
                // ✅ 首次进入！执行你的逻辑
                player.sendSystemMessage(
                        Component.translatable("🎉 欢迎"+player.getName().getString()+"第一次来到斗破！")
                                .withStyle(ChatFormatting.GOLD)
                );
                CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
//                    inventory.addTransientSlotModifier("ring", UUID.nameUUIDFromBytes(("ring").getBytes(StandardCharsets.UTF_8)), "name", 2, AttributeModifier.Operation.ADDITION);
                    inventory.addTransientSlotModifier("test_slot", UUID.nameUUIDFromBytes(("test_slot").getBytes(StandardCharsets.UTF_8)), "name", 2, AttributeModifier.Operation.ADDITION);
//                    inventory.addTransientSlotModifier("test_demo", UUID.nameUUIDFromBytes(("test_demo").getBytes(StandardCharsets.UTF_8)), "name", 2, AttributeModifier.Operation.ADDITION);
                });
//
//                player.addItem(ItemsRegistries.awakening.get().getDefaultInstance());
//                player.addItem(ItemsRegistries.wanfajieshaoitem.get().getDefaultInstance());
                // 标记为已访问，下次不再触发
                data.markAsVisited(playerUuid);
            }else {
                player.sendSystemMessage(
                        Component.translatable("🎉 欢迎"+player.getName().getString()+"回来")
                                .withStyle(ChatFormatting.GOLD)
                );
            }
        }
    }


}
