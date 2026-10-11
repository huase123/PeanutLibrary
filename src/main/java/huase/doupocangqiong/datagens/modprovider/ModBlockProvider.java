package huase.doupocangqiong.datagens.modprovider;

import huase.doupocangqiong.ModMain;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * - @description:ModBlockProvider类
 */
public class ModBlockProvider extends BlockStateProvider {
    public ModBlockProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ModMain.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        simpleBlockWithItem(BlockInit.lianqiding0.get(), new ModelFile.UncheckedModelFile(modLoc("block/lianqiding0")));
//        simpleBlockWithItem(BlockInit.lianqiding1.get(), new ModelFile.UncheckedModelFile(modLoc("block/lianqiding1")));
//        simpleBlockWithItem(BlockInit.lianqiding2.get(), new ModelFile.UncheckedModelFile(modLoc("block/lianqiding2")));
//        simpleBlockWithItem(BlockInit.lianqiding3.get(), new ModelFile.UncheckedModelFile(modLoc("block/lianqiding3")));
//
//        simpleBlockWithItem(BlockInit.liandanlu0.get(), new ModelFile.UncheckedModelFile(modLoc("block/liandanlu0")));
//        simpleBlockWithItem(BlockInit.liandanlu1.get(), new ModelFile.UncheckedModelFile(modLoc("block/liandanlu1")));
//        simpleBlockWithItem(BlockInit.liandanlu2.get(), new ModelFile.UncheckedModelFile(modLoc("block/liandanlu2")));
//        simpleBlockWithItem(BlockInit.liandanlu3.get(), new ModelFile.UncheckedModelFile(modLoc("block/liandanlu3")));
//        simpleBlockWithItem(BlockInit.jvlingzhen0.get(), new ModelFile.UncheckedModelFile(modLoc("block/zhenpan")));
//        simpleBlockWithItem(BlockInit.jvlingzhen1.get(), new ModelFile.UncheckedModelFile(modLoc("block/zhenpan")));
//        simpleBlockWithItem(BlockInit.jvlingzhen2.get(), new ModelFile.UncheckedModelFile(modLoc("block/zhenpan")));
//
//        cubeTop(BlockInit.lingdirblock);
//        BlockInit.zhiwublocklist.stream().forEach(c-> this.makeCornCrop(c.get(),c.getId().getPath(),c.getId().getPath()));
//
//        logBlockWithItem( BlockInit.lingmu0_LOG);
//        logBlockWithItem( BlockInit.lingmu1_LOG);
//        logBlockWithItem( BlockInit.lingmu2_LOG);
//        logBlockWithItem( BlockInit.lingmu3_LOG);
//
//        simpleBlockWithItemFile(BlockInit.lingmu0_LEAVES);
//        simpleBlockWithItemFile(BlockInit.lingmu1_LEAVES);
//        simpleBlockWithItemFile(BlockInit.lingmu2_LEAVES);
//        simpleBlockWithItemFile(BlockInit.lingmu3_LEAVES);
//
//
//        cross(BlockInit.lingmu0_SAPLING);
//        cross(BlockInit.lingmu1_SAPLING);
//        cross(BlockInit.lingmu2_SAPLING);
//        cross(BlockInit.lingmu3_SAPLING);
//
//
//
//        BlockInit.OreBlocklist.stream().forEach(this::ore);
    }



}
