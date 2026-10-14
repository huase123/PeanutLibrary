package huase.doupocangqiong.network.server;

import com.google.common.collect.Lists;
import huase.doupocangqiong.ModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
public class ReClientboundUpdateAttributesPacket {

  private final int entityId;
  private final List<ClientboundUpdateAttributesPacket.AttributeSnapshot> attributes;

  /**
   * 服务端构造：从 AttributeInstance 集合收集完整属性数据（含 modifier 的真实 name）。
   */
  public ReClientboundUpdateAttributesPacket(int entityId, Collection<AttributeInstance> attributeInstances) {
    this.entityId = entityId;
    this.attributes = Lists.newArrayList();

    for (AttributeInstance instance : attributeInstances) {
      this.attributes.add(new ClientboundUpdateAttributesPacket.AttributeSnapshot(
              instance.getAttribute(),
              instance.getBaseValue(),
              instance.getModifiers()
      ));
    }
  }

  public ReClientboundUpdateAttributesPacket(int entityId,
                                             List<ClientboundUpdateAttributesPacket.AttributeSnapshot> attributes) {
    this.entityId = entityId;
    this.attributes = attributes;
  }

  public int getEntityId() {
    return entityId;
  }

  public List<ClientboundUpdateAttributesPacket.AttributeSnapshot> getAttributes() {
    return attributes;
  }

  public static void encode(ReClientboundUpdateAttributesPacket msg, FriendlyByteBuf buffer) {
    buffer.writeVarInt(msg.entityId);
    buffer.writeCollection(msg.attributes, (buf, snapshot) -> {
      buf.writeResourceLocation(BuiltInRegistries.ATTRIBUTE.getKey(snapshot.getAttribute()));
      buf.writeDouble(snapshot.getBase());
      buf.writeCollection(snapshot.getModifiers(), (modifierBuf, modifier) -> {
        modifierBuf.writeUUID(modifier.getId());
        modifierBuf.writeUtf(modifier.getName());   // ★ 追加 name
        modifierBuf.writeDouble(modifier.getAmount());
        modifierBuf.writeByte(modifier.getOperation().toValue());
      });
    });
  }

  public static ReClientboundUpdateAttributesPacket decode(FriendlyByteBuf buffer) {
    int entityId = buffer.readVarInt();
    List<ClientboundUpdateAttributesPacket.AttributeSnapshot> attributes = buffer.readList(attributeBuf -> {
      ResourceLocation attributeId = attributeBuf.readResourceLocation();
      Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(attributeId);
      double base = attributeBuf.readDouble();
      List<AttributeModifier> modifiers = attributeBuf.readList(modifierBuf ->
              new AttributeModifier(
                      modifierBuf.readUUID(),
                      modifierBuf.readUtf(),   // ★ 读取真实 name
                      modifierBuf.readDouble(),
                      AttributeModifier.Operation.fromValue(modifierBuf.readByte())
              )
      );
      return new ClientboundUpdateAttributesPacket.AttributeSnapshot(attribute, base, modifiers);
    });


    return new ReClientboundUpdateAttributesPacket(entityId, attributes);
  }

  public static void handle(ReClientboundUpdateAttributesPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      Minecraft minecraft = Minecraft.getInstance();
      Level level = minecraft.level;
      if (level == null) return;

      Entity entity = level.getEntity(msg.getEntityId());
      if (entity == null) return;

      if (!(entity instanceof LivingEntity livingEntity)) {
        ModMain.LOGGER.warn("Server tried to update attributes of a non-living entity (actually: {})", entity);
        return;
      }

      AttributeMap attributeMap = livingEntity.getAttributes();
      for (ClientboundUpdateAttributesPacket.AttributeSnapshot snapshot : msg.getAttributes()) {
        AttributeInstance instance = attributeMap.getInstance(snapshot.getAttribute());
        if (instance == null) {
          ModMain.LOGGER.warn("Entity {} does not have attribute {}",
                  entity,
                  BuiltInRegistries.ATTRIBUTE.getKey(snapshot.getAttribute()));
          continue;
        }

        instance.setBaseValue(snapshot.getBase());
        instance.removeModifiers();

        for (AttributeModifier modifier : snapshot.getModifiers()) {
          instance.addTransientModifier(modifier);
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}