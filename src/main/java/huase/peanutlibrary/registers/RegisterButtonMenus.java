package huase.peanutlibrary.registers;

import huase.peanutlibrary.ModMain;
import huase.peanutlibrary.api.RegisterButtonApi;
import net.minecraft.world.inventory.InventoryMenu;

public class RegisterButtonMenus {
    public static void register() {
        RegisterButtonApi.register(
                InventoryMenu.class,
                10, 20, 100, 20,
                0, 0, 0,
                ModMain.parse("textures/gui/buttons.png"),
                (player, menu) -> System.out.println("服务端成功触发")
        );
    }

}
