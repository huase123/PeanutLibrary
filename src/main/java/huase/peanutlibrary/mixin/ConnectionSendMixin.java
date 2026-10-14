package huase.peanutlibrary.mixin;

import huase.peanutlibrary.network.NetworkRegistries;
import huase.peanutlibrary.network.server.ReClientboundUpdateAttributesPacket;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ConnectionSendMixin {
    @Inject(method = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V", at = @At("HEAD"), cancellable = true)
    private void interceptAttributePacket(Packet<?> packet, PacketSendListener pSendListener, CallbackInfo ci) {
        // 只处理属性包
        if ((packet instanceof ClientboundUpdateAttributesPacket attrPacket)) {
//            发送自定义包

            // 发给所有玩家
            NetworkRegistries.INSTANCE.send(
                    PacketDistributor.ALL.noArg(),
                    new ReClientboundUpdateAttributesPacket(attrPacket.getEntityId(), attrPacket.getValues())
            );
            ci.cancel();
        }

    }
}