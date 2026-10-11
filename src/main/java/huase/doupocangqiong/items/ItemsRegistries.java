package huase.doupocangqiong.items;

import huase.doupocangqiong.ModMain;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * - @description:ItemsRegistries类
 */
public class ItemsRegistries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
    public static List<RegistryObject<Item>> simpleitemlist = new ArrayList();


    public static final RegistryObject<Item> doupojingjie = ITEMS.register("doupojingjie", () -> new DemoItem1());


    private static  <T extends Item> RegistryObject<Item> registersimpleitem(String string, Supplier<T> item) {
        RegistryObject<Item> register = ITEMS.register(string, item);
        simpleitemlist.add(register);
        return register;
    }
}
