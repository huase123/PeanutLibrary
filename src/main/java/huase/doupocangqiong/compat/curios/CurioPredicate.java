package huase.doupocangqiong.compat.curios;

import huase.doupocangqiong.ModMain;
import huase.doupocangqiong.datagens.tag.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class CurioPredicate {
    public static ResourceLocation jingjie = ModMain.prefix("jingjie");

    public static void registerCurioPredicate(){

        CuriosApi.registerCurioPredicate(jingjie, (slotResult) -> {
            ItemStack stack = slotResult.stack();
            return stack.is(ItemTagsProvider.isjingjie) ;
        });
    }
}
