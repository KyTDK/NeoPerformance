package com.neomechanical.neoperformance.performance.smart.smartNotifier.managers;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.dataGetters.ChunkData;
import com.neomechanical.neoperformance.performance.smart.smartNotifier.dataGetters.EntityClusterData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LagDataManager {
    List<DataGetter> dataGetters = new ArrayList<>();

    public LagDataManager(NeoPerformance plugin) {
        dataGetters.add(new ChunkData(plugin));
        dataGetters.add(new EntityClusterData(plugin.getDataManager()));
    }

    public void generateAll() {
        for (DataGetter dataGetter : dataGetters) {
            if (dataGetter.getNotifySize() > 0) {
                dataGetter.generate();
            }
        }
    }

    public List<LagData> getAllLagData(Player player) {
        List<LagData> lagDataList = new ArrayList<>();
        for (DataGetter dataGetter : dataGetters) {
            lagDataList.add(dataGetter.get(player));
        }
        return lagDataList;
    }
}
