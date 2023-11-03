package com.neomechanical.neoperformance.performance;

import org.bukkit.Chunk;

import java.util.List;

public interface FindOneCallback {

    void onChunkScanDone(List<Chunk> result);

}