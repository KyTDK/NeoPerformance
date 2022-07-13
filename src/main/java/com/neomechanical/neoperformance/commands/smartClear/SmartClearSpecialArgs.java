package com.neomechanical.neoperformance.commands.smartClear;

public enum SmartClearSpecialArgs {
    //Remove all clusters
    ALL("-all"),
    //Bypass the confirmation prompt
    FORCE("-force"),
    //Set world
    WORLD("-world");

    private final String specialArg;

    SmartClearSpecialArgs(String specialArg) {
        this.specialArg = specialArg;
    }

    public String getSpecialArg() {
        return specialArg;
    }
}
