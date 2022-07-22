package com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.managers;

import com.neomechanical.neoperformance.performanceOptimiser.config.PerformanceConfigurationSettings;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.DataGetter;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.dataGetters.ChunkData;
import com.neomechanical.neoperformance.performanceOptimiser.smart.smartNotifier.dataGetters.EntityClusterData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LagDataManager implements PerformanceConfigurationSettings {
    List<DataGetter> dataGetters = new ArrayList<>();

    public LagDataManager() {
        dataGetters.add(new ChunkData());
        dataGetters.add(new EntityClusterData());
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
