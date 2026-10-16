package huase.peanutlibrary.network.client;

import huase.peanutlibrary.ModMain;
import huase.peanutlibrary.registers.ButtonMenuContext;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

import static huase.peanutlibrary.ModMain.ButtonMenusRegistry;

public class CPButtonPressPacket {
  public int containerId;
  public int buttonid;
  public CPButtonPressPacket(int containerId, int buttonid) {
    this.containerId = containerId;
    this.buttonid = buttonid;
  }

  public CPButtonPressPacket(AbstractContainerMenu menu, int buttonid) {
    this.containerId = menu.containerId;
    this.buttonid = buttonid;

  }

  public static void encode(CPButtonPressPacket msg, FriendlyByteBuf buf) {
    buf.writeInt(msg.containerId);
    buf.writeInt(msg.buttonid);
  }

  public static CPButtonPressPacket decode(FriendlyByteBuf buf) {
    return new CPButtonPressPacket(buf.readInt(),buf.readInt());
  }

  public static void handle(CPButtonPressPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();

      if (player != null) {

        if (player.containerMenu.containerId == msg.containerId && !player.isSpectator()) {
          if (!player.containerMenu.stillValid(player)) {
            ModMain.LOGGER.warn("CPButtonPressPacket: Player {} interacted with invalid menu,invalid button id {} for menu {}",
                    msg.buttonid, player.containerMenu.getClass().getName());
          } else {

            List<ButtonMenuContext> buttonMenuContextList = ButtonMenusRegistry.get(player.containerMenu.getClass());

            if (buttonMenuContextList == null || msg.buttonid < 0 || msg.buttonid >= buttonMenuContextList.size()) {
              ModMain.LOGGER.warn("CPButtonPressPacket: invalid button id {} for menu {}",
                      msg.buttonid, player.containerMenu.getClass().getName());
              return;
            }
            ButtonMenuContext buttonMenuContext = buttonMenuContextList.get(msg.buttonid);
            buttonMenuContext.clickMenuButton(player,player.containerMenu);

          }
        }else {

          ModMain.LOGGER.warn("CPButtonPressPacket: id不一致 msgid {} containerMenu {}",
                  msg.buttonid, player.containerMenu.containerId);
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
