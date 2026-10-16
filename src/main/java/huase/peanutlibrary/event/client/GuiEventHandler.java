package huase.peanutlibrary.event.client;

import huase.peanutlibrary.registers.ButtonMenuContext;
import huase.peanutlibrary.registers.RegisterButtonMenus;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static huase.peanutlibrary.ModMain.ButtonMenusRegistry;
import static huase.peanutlibrary.ModMain.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GuiEventHandler {

  @SubscribeEvent
  public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
    Screen screen = evt.getScreen();

    if(screen instanceof AbstractContainerScreen abstractContainerScreen){
      List<ButtonMenuContext> buttonMenuContextList = ButtonMenusRegistry.get(abstractContainerScreen.getMenu().getClass());
      if(buttonMenuContextList == null || buttonMenuContextList.isEmpty()){
        return;
      }
      for (int i = 0; i < buttonMenuContextList.size(); i++) {
        ButtonMenuContext buttonMenuContext = buttonMenuContextList.get(i);
        evt.addListener(
                buttonMenuContext.createListener(buttonMenuContext,i,abstractContainerScreen.getMenu(),abstractContainerScreen)
        );
      }


    }
/*



    if (!CuriosClientConfig.CLIENT.enableButton.get()) {
      return;
    }

    if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
      AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
      boolean isCreative = screen instanceof CreativeModeInventoryScreen;
      Tuple<Integer, Integer> offsets = CuriosScreen.getButtonOffset(isCreative);
      int x = offsets.getA();
      int y = offsets.getB();
      int size = isCreative ? 10 : 14;
      int textureOffsetX = isCreative ? 64 : 50;
      int yOffset = isCreative ? 68 : 83;
      evt.addListener(new CuriosButton(gui, gui.getGuiLeft() + x, gui.getGuiTop() + y + yOffset, size,
          size, textureOffsetX, 0, size, CuriosScreen.CURIO_INVENTORY));
    }

*/

  }

//  @SubscribeEvent
  public void onInventoryGuiDrawBackground(ScreenEvent.Render.Pre evt) {

    if (!(evt.getScreen() instanceof InventoryScreen gui)) {
      return;
    }
//    gui.xMouse = evt.getMouseX();
//    gui.yMouse = evt.getMouseY();
  }
/*
  @SubscribeEvent
  public void onMouseClick(ScreenEvent.MouseButtonPressed.Pre evt) {
    long handle = Minecraft.getInstance().getWindow().getWindow();
    boolean isLeftShiftDown = InputConstants.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_SHIFT);
    boolean isRightShiftDown = InputConstants.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT_SHIFT);
    boolean isShiftDown = isLeftShiftDown || isRightShiftDown;

    if (!(evt.getScreen() instanceof CreativeModeInventoryScreen gui) || !isShiftDown) {
      return;
    }

    if (!gui.isInventoryOpen()) {
      return;
    }
    Slot destroyItemSlot = gui.destroyItemSlot;
    Slot slot = gui.findSlot(evt.getMouseX(), evt.getMouseY());

    if (destroyItemSlot != null && slot == destroyItemSlot) {
      NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketDestroy());
    }
  }
  */
}