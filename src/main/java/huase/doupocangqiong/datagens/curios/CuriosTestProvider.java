package huase.doupocangqiong.datagens.curios;

import huase.doupocangqiong.compat.curios.CurioPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.concurrent.CompletableFuture;

public class CuriosTestProvider extends CuriosDataProvider {

    public CuriosTestProvider(String modId, PackOutput output,
                              ExistingFileHelper fileHelper,
                              CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, fileHelper, registries);
    }
    public static String jingjie_slot = "jingjie_slot";
    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        // Generation code here
        this.createSlot(jingjie_slot)
                .size(1)
                .dropRule(ICurio.DropRule.ALWAYS_KEEP)
                .addCosmetic(false)
                .addValidator(CurioPredicate.jingjie)
        ;


        this.createEntities("test_entities")
                .addPlayer()
                .addEntities(EntityType.ZOMBIE)
//                .addSlots("head", "ring", "necklace", "test_slot")
                .addSlots( jingjie_slot,"curio")
        ;
    }
}