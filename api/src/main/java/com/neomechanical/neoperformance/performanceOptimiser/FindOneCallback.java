package com.neomechanical.neoperformance.performanceOptimiser;

import org.bukkit.Chunk;

import java.util.List;

public interface FindOneCallback {

    void onChunkScanDone(List<Chunk> result);

}