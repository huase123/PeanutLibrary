package huase.doupocangqiong.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import huase.doupocangqiong.ModMain;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DemoItem1 extends Item implements ICurioItem {
    public DemoItem1() {
        super(new Properties());
    }
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return true;
//        return slotContext.identifier().equals("test_slot");
    }

    public static UUID uuid = UUID.nameUUIDFromBytes(("sattributename").getBytes(StandardCharsets.UTF_8));

    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(slotContext.entity() instanceof ServerPlayer serverPlayer){
            if(serverPlayer.level().getGameTime()%40 == 0){
                if(!stack.isEmpty() && stack.getItem() instanceof DemoItem1){
                    Multimap<Attribute, AttributeModifier> modifierHashMultimap= HashMultimap.create();

                    modifierHashMultimap.put(Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(UUID.nameUUIDFromBytes(("sss").getBytes(StandardCharsets.UTF_8)), "name:"+serverPlayer.level().getGameTime(), 10, AttributeModifier.Operation.ADDITION));


                    serverPlayer.getAttributes().addTransientAttributeModifiers(modifierHashMultimap);
/*
                    Attribute attribute = Attributes.ATTACK_DAMAGE;
                    CompoundTag tag = stack.getOrCreateTag();

                    if (!tag.contains("CurioAttributeModifiers", 9)) {
                        tag.put("CurioAttributeModifiers", new ListTag());
                    }
                    ListTag listtag = tag.getList("CurioAttributeModifiers", 10);
                    CompoundTag compoundtag = null;
                    for (int i = 0; i < listtag.size(); i++) {
                        CompoundTag tag1 = (CompoundTag)listtag.get(i);
                        if(tag1.hasUUID("UUID")){
                            if(tag1.getUUID("UUID").equals(uuid)){
                                compoundtag =  tag1;
                            }
                        }
                    }
                    if(compoundtag == null){
                        compoundtag = new CompoundTag();
                        compoundtag.putString("Name", "attributename");
                        compoundtag.putDouble("Amount", 0);
                        compoundtag.putInt("Operation", AttributeModifier.Operation.ADDITION.toValue());
                        compoundtag.putUUID("UUID", uuid);
                        compoundtag.putString("Slot", CuriosTestProvider.jingjie_slot);



                        String id = "";

                        if (attribute instanceof SlotAttribute wrapper) {
                            id = "curios:" + wrapper.getIdentifier();
                        } else {
                            ResourceLocation rl = ForgeRegistries.ATTRIBUTES.getKey(attribute);

                            if (rl != null) {
                                id = rl.toString();
                            }
                        }

                        if (!id.isEmpty()) {
                            compoundtag.putString("AttributeName", id);
                        }

                        listtag.add(compoundtag);
                    }
                    double amount = compoundtag.getDouble("Amount");
                    compoundtag.putDouble("Amount", amount+1);

                    */
                }
            }
        }

    }
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                         UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifierHashMultimap= HashMultimap.create();

        modifierHashMultimap.put(Attributes.ATTACK_DAMAGE,  new AttributeModifier(UUID.nameUUIDFromBytes(("s").getBytes(StandardCharsets.UTF_8)), this::getDescriptionId, 10, AttributeModifier.Operation.ADDITION));

        return modifierHashMultimap;
    }

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
