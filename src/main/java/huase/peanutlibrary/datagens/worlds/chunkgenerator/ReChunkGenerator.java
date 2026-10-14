package huase.peanutlibrary.datagens.worlds.chunkgenerator;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ReChunkGenerator extends ChunkGenerator {

    public static final Codec<ReChunkGenerator> CODEC = RecordCodecBuilder.create((sourceInstance) -> sourceInstance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ReChunkGenerator::getBiomeSource)
    ).apply(sourceInstance, sourceInstance.stable(ReChunkGenerator::new)));
    public ReChunkGenerator(BiomeSource pBiomeSource) {
        super(pBiomeSource);
    }

//    public ReChunkGenerator(BiomeSource pBiomeSource, Function<Holder<Biome>, BiomeGenerationSettings> pGenerationSettingsGetter) {
//        super(pBiomeSource, pGenerationSettingsGetter);
//    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, RandomState pRandom, BiomeManager pBiomeManager, StructureManager pStructureManager, ChunkAccess pChunk, GenerationStep.Carving pStep) {

    }

    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {

    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion pLevel) {

    }

    @Override
    public int getGenDepth() {
        return 0;
    }


    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor pExecutor, Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk) {

        Set<LevelChunkSection> set = Sets.newHashSet();
        for (LevelChunkSection levelchunksection : pChunk.getSections()) {
            levelchunksection.acquire();
            set.add(levelchunksection);
        }


        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("wgen_fill_noise", () -> {
            return this.doFill(pBlender, pStructureManager, pRandom, pChunk);
        }), Util.backgroundExecutor()).whenCompleteAsync((p_224309_, p_224310_) -> {
            for(LevelChunkSection levelchunksection1 : set) {
                levelchunksection1.release();
            }

        }, pExecutor);
    }
    NormalNoise normalNoise = NormalNoise.create(new WorldgenRandom(new LegacyRandomSource(1)), -4, 1, 1, 1);
    private ChunkAccess doFill(Blender pBlender, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {
        Heightmap heightmap = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
        ChunkPos chunkpos = pChunk.getPos();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();




        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                int i1 = i + k;   // 世界 X
                int j1 = j + l;   // 世界 Z

                // 用 2D 噪声得到 -1..1 之间的值，映射到高度
                double noise = normalNoise.getValue(i1 / 100.0,0, j1 / 100.0);
                int height = 64 + (int) (noise * 20);  // 高度在 44~84 之间

                for (int y = pChunk.getMinBuildHeight(); y < pChunk.getMaxBuildHeight(); y++) {

                    LevelChunkSection levelchunksection = pChunk.getSection(pChunk.getSectionIndex(y));

                    BlockState state = null;
                    if (y < height - 4) {
                        state = Blocks.STONE.defaultBlockState();
                    } else if (y < height) {
                        state = Blocks.DIRT.defaultBlockState();
                    } else if (y == height) {
                        state = Blocks.GRASS_BLOCK.defaultBlockState();
                    }
                    if(state !=null){
                        levelchunksection.setBlockState(k,y & 15,l, state,false);
                    }
                }

                // 更新高度图
                heightmap.update(k, height & 15, l, Blocks.GRASS_BLOCK.defaultBlockState());
                heightmap1.update(k, height & 15, l, Blocks.GRASS_BLOCK.defaultBlockState());
            }
        }







//        for (int k = 0; k < 16; k++) {
//            for (int l = 0; l < 16; l++) {
//                int i1 = i + k;
//                int j1 = j + l;
//                int value = (int) Mth.abs((float) (normalNoise.getValue(i/100f+k/100f, 0, j/100f+l/100f)*200.0f));
//                for (int height = 0; height < value; height++) {
//                    LevelChunkSection levelchunksection = pChunk.getSection(pChunk.getSectionIndex(height ));
//                    double value1 = normalNoise.getValue(i1/10.0, height/10.0, j1/10.0);
//                    if( Math.abs(value1) > 0.1D){
//                        levelchunksection.setBlockState(k, height & 15, l, STONE, false);
//                    }
//                }
//
//                heightmap.update(k, value & 15, l, STONE);
//                heightmap1.update(k, value & 15, l, STONE);
//            }
//        }
        return pChunk;
    }
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState STONE = Blocks.STONE.defaultBlockState();

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int pX, int pZ, Heightmap.Types pType, LevelHeightAccessor pLevel, RandomState pRandom) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int pX, int pZ, LevelHeightAccessor pHeight, RandomState pRandom) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> pInfo, RandomState pRandom, BlockPos pPos) {

    }
}
