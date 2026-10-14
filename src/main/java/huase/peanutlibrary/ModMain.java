package huase.peanutlibrary;

import com.mojang.logging.LogUtils;
import huase.peanutlibrary.network.NetworkRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import java.lang.reflect.Field;
import java.util.Locale;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModMain.MODID)
public class ModMain {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "peanutlibrary";
    public static final String MOD_ID = "peanutlibrary";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext    .get().getModEventBus();


        NetworkRegistries.register();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);



        changeAttributesIO();
    }

    public static ResourceLocation prefix(String name) {
//        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
    public static ResourceLocation parse(String name) {
//        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
        return new ResourceLocation(name);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting"+MODID);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }


    public static void changeAttributesIO()  {

        try {
            Field privateField = RangedAttribute.class.getDeclaredFields()[1];
            privateField.setAccessible(true);
            //    修改血量上限
            privateField.set(Attributes.MAX_HEALTH, Float.MAX_VALUE);
            //    攻击伤害、物攻上限
            privateField.set(Attributes.ATTACK_DAMAGE, Float.MAX_VALUE);
            //    盔甲、物防上限
            privateField.set(Attributes.ARMOR, Float.MAX_VALUE);
            //    盔甲韧性、体魄上限
            privateField.set(Attributes.ARMOR_TOUGHNESS,  Float.MAX_VALUE);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
//        添加物攻同步
        Attributes.ATTACK_DAMAGE.setSyncable(true);
    }
}
