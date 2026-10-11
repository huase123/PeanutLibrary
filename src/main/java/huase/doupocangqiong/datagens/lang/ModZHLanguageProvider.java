package huase.doupocangqiong.datagens.lang;


import huase.doupocangqiong.ModMain;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZHLanguageProvider extends LanguageProvider {
    public ModZHLanguageProvider(PackOutput output) {
        super(output, ModMain.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {

    }

    public void addBiome(ResourceKey<Biome> biome, String name) {
        this.add("biome."+ModMain.MOD_ID+"." + biome.location().getPath(), name);
    }
}
