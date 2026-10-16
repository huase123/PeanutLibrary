package huase.peanutlibrary.registers;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.function.BiConsumer;

public class ButtonMenu implements ButtonMenuContext{
    public StyleData styleData;

    public ButtonMenu(StyleData styleData) {

        this.styleData = styleData;
    }
    public static ButtonMenu Create(StyleData styleData) {

        return new ButtonMenu(styleData);
    }

    @Override
    public GuiEventListener createListener(ButtonMenuContext buttonMenuContext, int buttonid, AbstractContainerMenu menu, AbstractContainerScreen abstractContainerScreen) {
        return new ImageButton(styleData.pX, styleData.pY, styleData.pWidth, styleData.pHeight, styleData.pXTexStart, styleData.pYTexStart, styleData.pYDiffTex, styleData.pResourceLocation,
                getOnPress(abstractContainerScreen,menu,buttonid)
        );
    }

    @Override
    public void clickButton(AbstractContainerScreen abstractContainerScreen, AbstractContainerMenu menu, int buttonid) {
        ButtonMenuContext.super.clickButton(abstractContainerScreen, menu, buttonid);
    }

    @Override
    public void clickMenuButton(ServerPlayer player, AbstractContainerMenu containerMenu) {
        ButtonMenuContext.super.clickMenuButton(player, containerMenu);
        styleData.clickMenuButton.accept(player,containerMenu);
    }

    public record StyleData(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation,BiConsumer<ServerPlayer, AbstractContainerMenu> clickMenuButton){
        public static StyleData of(int x, int y, int w, int h,
                                   int xTex, int yTex, int yDiff,
                                   ResourceLocation tex,
                                   BiConsumer<ServerPlayer, AbstractContainerMenu> onClick) {
            return new StyleData(x, y, w, h, xTex, yTex, yDiff, tex, onClick);
        }
    }
}
