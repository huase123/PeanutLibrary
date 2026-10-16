package huase.peanutlibrary.api;

import huase.peanutlibrary.registers.ButtonMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import static huase.peanutlibrary.ModMain.ButtonMenusRegistry;

public class RegisterButtonApi {

    /**
     * 为一个菜单类注册自定义按钮。
     *
     * <p>按钮通过 {@link ButtonMenu.StyleData} 描述外观与点击行为，
     * buttonId 即为注册顺序的下标。
     *
     * @param menuClass  目标菜单类，不能为 null
     * @param x          按钮 X 坐标
     * @param y          按钮 Y 坐标
     * @param width      按钮宽度
     * @param height     按钮高度
     * @param xTexStart  贴图起始 X
     * @param yTexStart  贴图起始 Y
     * @param yDiffTex   按下状态贴图的 Y 偏移
     * @param tex        按钮贴图，不能为 null
     * @param onClick    点击回调（服务端执行），不能为 null
     * @throws NullPointerException 当 menuClass / tex / onClick 为 null 时
     */
    public static void register(Class<? extends AbstractContainerMenu> menuClass,
                                int x, int y, int width, int height,
                                int xTexStart, int yTexStart, int yDiffTex,
                                ResourceLocation tex,
                                BiConsumer<ServerPlayer, AbstractContainerMenu> onClick) {
        ButtonMenu.StyleData styleData = ButtonMenu.StyleData.of(
                x, y, width, height,
                xTexStart, yTexStart, yDiffTex,
                tex, onClick
        );
        ButtonMenusRegistry
                .computeIfAbsent(menuClass, k -> new ArrayList<>())
                .add(ButtonMenu.Create(styleData));
    }
}
