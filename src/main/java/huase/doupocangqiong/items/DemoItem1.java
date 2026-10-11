package huase.doupocangqiong.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class DemoItem1 extends Item implements ICurioItem {
    public DemoItem1() {
        super(new Properties());
    }
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return true;
//        return slotContext.identifier().equals("test_slot");
    }


}
