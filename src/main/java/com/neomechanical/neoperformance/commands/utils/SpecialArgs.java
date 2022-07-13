package com.neomechanical.neoperformance.commands.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialArgs {
    public Map<String, String> checkForArgs(List<String> args, String specialArgs) {
        HashMap<String, String> foundSpecialArgs = new HashMap<>();
        for (String arg : args) {
            if (arg.equals(specialArgs)) {
                //Get the next arg, not special arg
                String specialArgValue = args.get(args.indexOf(arg) + 1);
                foundSpecialArgs.put(arg, specialArgValue);
            }
        }
        return foundSpecialArgs;
    }
}
