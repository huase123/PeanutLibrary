package huase.doupocangqiong.registries;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * - @description:FirstJoinData类
 */
public class FirstJoinData extends SavedData {

    private final Set<UUID> visitedPlayers = new HashSet<>();

    // 从世界获取数据实例（核心方法）
    public static FirstJoinData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                FirstJoinData::load,      // 如何从NBT加载
                FirstJoinData::new,       // 如果没有数据，创建新的
                "first_join_data"         // 存档文件名
        );
    }

    // 构造方法
    public FirstJoinData() {}

    // 从NBT加载数据
    public static FirstJoinData load(CompoundTag tag) {
        FirstJoinData data = new FirstJoinData();
        ListTag list = tag.getList("VisitedPlayers", StringTag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) {
            data.visitedPlayers.add(UUID.fromString(list.getString(i)));
        }
        return data;
    }

    // 保存数据到NBT
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (UUID uuid : visitedPlayers) {
            list.add(StringTag.valueOf(uuid.toString()));
        }
        tag.put("VisitedPlayers", list);
        return tag;
    }

    // 判断是否首次进入
    public boolean isFirstTime(UUID playerUuid) {
        return !visitedPlayers.contains(playerUuid);
    }

    // 标记为已访问
    public void markAsVisited(UUID playerUuid) {
        visitedPlayers.add(playerUuid);
        setDirty(); // 标记数据已修改，会自动保存
    }
}