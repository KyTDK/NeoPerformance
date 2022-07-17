package com.neomechanical.neoperformance.utils;

import net.minecraft.core.BlockPos;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;

import java.lang.reflect.Method;

public class PistonUtil {
    public static boolean movePiston(Block piston) throws Exception {
        BlockData bd = piston.getBlockData();
        Piston redstone = (Piston) bd;
        boolean extended = redstone.isExtended();
        BlockFace bf = ((Directional) bd).getFacing();
        Object nmsWorld = (getNmsClass("CraftWorld", true).cast(piston.getWorld())).getClass().getMethod("getHandle").invoke((getNmsClass("CraftWorld", true).cast(piston.getWorld())));
        Object pistonPosition = BlockPos.class.getConstructor(int.class, int.class, int.class).newInstance(piston.getX(), piston.getY(), piston.getZ());
        Object nmsBlockData = nmsWorld.getClass().getMethod("getType", pistonPosition.getClass()).invoke(nmsWorld, pistonPosition);
        Object nmsPiston = getNmsClass("BlockPiston", false).cast(nmsBlockData.getClass().getMethod("getBlock").invoke(nmsBlockData));

        try {
            Method method = nmsPiston.getClass().getDeclaredMethod("a", getNmsClass("World", false), getNmsClass("BlockPosition", false), getNmsClass("EnumDirection", false), boolean.class);
            method.setAccessible(true);
            if (extended) {
                redstone.setExtended(false);
                piston.setBlockData(redstone);
            }
            redstone.setExtended(true);
            piston.setBlockData(redstone);
            boolean bold = (boolean) method.invoke(nmsPiston, nmsWorld, pistonPosition, interchangeDirections(bf), !extended);
            if (!bold) {
                redstone.setExtended(false);
                piston.setBlockData(redstone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extended;
    }

    public static Object interchangeDirections(BlockFace bf) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class enumDirection = getNmsClass("EnumDirection", false);
        if (bf == BlockFace.DOWN) {
            return Enum.valueOf(enumDirection, "DOWN");
        } else if (bf == BlockFace.UP) {
            return Enum.valueOf(enumDirection, "UP");
        } else if (bf == BlockFace.EAST) {
            return Enum.valueOf(enumDirection, "EAST");
        } else if (bf == BlockFace.NORTH) {
            return Enum.valueOf(enumDirection, "NORTH");
        } else if (bf == BlockFace.SOUTH) {
            return Enum.valueOf(enumDirection, "SOUTH");
        } else if (bf == BlockFace.WEST) {
            return Enum.valueOf(enumDirection, "WEST");
        }
        return null;
    }

    public static Class<?> getNmsClass(String nmsClassName, boolean craftbukkit) throws ClassNotFoundException {
        String start = "net.minecraft.core.";
        if (craftbukkit) {
            start = "org.bukkit.craftbukkit.";
            return Class.forName(start
                    + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "."
                    + nmsClassName);
        }
        return Class.forName(start
                + nmsClassName);
    }
}
