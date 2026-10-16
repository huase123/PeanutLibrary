package huase.peanutlibrary.registers;

import huase.peanutlibrary.ModMain;
import huase.peanutlibrary.network.NetworkRegistries;
import huase.peanutlibrary.network.client.CPButtonPressPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

public interface ButtonMenuContext {

     default GuiEventListener createListener(ButtonMenuContext buttonMenuContext, int buttonid, AbstractContainerMenu menu, AbstractContainerScreen abstractContainerScreen){
        return new ImageButton(10, 10, 10, 10, 10, 10, 10, getResourceLocation(),
                getOnPress(abstractContainerScreen,menu,buttonid)
                );
    }



    default Button.OnPress getOnPress(AbstractContainerScreen abstractContainerScreen, AbstractContainerMenu menu, int buttonid) {

        return pButton -> {
            clickButton(abstractContainerScreen,menu,buttonid);
            if(toServer(abstractContainerScreen,menu,buttonid)){
                NetworkRegistries.INSTANCE.sendToServer(new CPButtonPressPacket(menu,buttonid));
            }
        };
    }

    default void clickButton(AbstractContainerScreen abstractContainerScreen, AbstractContainerMenu menu, int buttonid){

    }

    default boolean toServer(AbstractContainerScreen abstractContainerScreen, AbstractContainerMenu menu, int buttonid) {
        return true;
    }

    default void clickMenuButton(ServerPlayer player, AbstractContainerMenu containerMenu){

    };

    default ResourceLocation getResourceLocation() {
        return ModMain.prefix("");
    }
}
