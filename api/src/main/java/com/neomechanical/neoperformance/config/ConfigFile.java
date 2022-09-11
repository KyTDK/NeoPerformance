package com.neomechanical.neoperformance.config;

public interface ConfigFile {
    /**
     * Get the name of the file.
     *
     * @return the file name
     */
    default String getName() {
        return "performanceConfig.yml";
    }
}