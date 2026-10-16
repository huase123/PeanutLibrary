package huase.peanutlibrary.event.server;



import huase.peanutlibrary.ModMain;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RePlayerLoggedInEvent {
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        // 只在服务器端执行
        if (player.level() instanceof ServerLevel level) {

            ForgeRegistry<Attribute> registry = RegistryManager.ACTIVE.getRegistry(ForgeRegistries.Keys.ATTRIBUTES);

            if (registry == null) {
                System.out.println("属性注册表不可用");
                return;
            }

            // 遍历所有已注册的属性
            for (Attribute attribute : registry) {
                System.out.println("Attribute: " + attribute.getDescriptionId());
            }



            System.out.println(ForgeMod.SWIM_SPEED.get().getDescriptionId()+"--------------");



            for (Attribute attribute : ForgeRegistries.ATTRIBUTES) {
                // 处理每一个属性实例
                // 例如：获取其描述ID或注册名
                String name = attribute.getDescriptionId();
                System.out.println(attribute.getDescriptionId());
            }

        }
    }


}
