/*******************************************************************************
 * Copyright 2014-2017, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/
package com.sweetrpg.seasonings.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import com.sweetrpg.seasonings.config.json.BiomeData;
import com.sweetrpg.seasonings.util.SeasonColourUtil;
import com.sweetrpg.seasonings.util.config.JsonUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BiomeConfig
{
    public static Map<String, BiomeData> biomeDataMap = Maps.newHashMap();

    public static void init(File configDir)
    {
        Map<String, BiomeData> defaultBiomeData = Maps.newHashMap();
        addBlacklistedBiomes(defaultBiomeData);
        addTropicalBiomes(defaultBiomeData);
        biomeDataMap = JsonUtil.getOrCreateConfigFile(configDir, "biome_info.json", defaultBiomeData, new TypeToken<Map<String, BiomeData>>(){}.getType());
    }

    public static boolean hasSeasonalColoring(Biome biome)
    {
        String name = biome.getRegistryName().toString();

        if (biomeDataMap.containsKey(name))
        {
            return biomeDataMap.get(name).enableSeasonalColoring;
        }

        return true;
    }

    public static boolean usesTropicalSeasons(Biome biome)
    {
        String name = biome.getRegistryName().toString();

        if (biomeDataMap.containsKey(name))
        {
            return biomeDataMap.get(name).useTropicalSeasons;
        }

        return false;
    }

    private static void addBlacklistedBiomes(Map<String, BiomeData> map)
    {
        List<String> blacklistedBiomes = Lists.newArrayList("minecraft:mushroom_island", "minecraft:mushroom_island_shore",
                "biomesoplenty:mystic_grove", "biomesoplenty:ominous_woods", "biomesoplenty:wasteland",
                "biomesoplenty:flower_island", "biomesoplenty:origin_island", "biomeoplenty:origin_beach");

        for (String biomeName : blacklistedBiomes)
        {
            if (!map.containsKey(biomeName))
                map.put(biomeName, new BiomeData(false, false));
            else
                map.get(biomeName).enableSeasonalColoring = false;
        }
    }

    private static void addTropicalBiomes(Map<String, BiomeData> map)
    {
        List<String> tropicalBiomes = Lists.newArrayList("minecraft:desert", "minecraft:desert_hills",
                "minecraft:mutated_desert", "minecraft:jungle", "minecraft:jungle_hills", "minecraft:jungle_edge",
                "minecraft:mutated_jungle", "minecraft:mutated_jungle_edge", "minecraft:mesa", "minecraft:mesa_rock",
                "minecraft:mesa_clear_rock", "minecraft:mutated_mesa", "minecraft:mutated_mesa_rock",
                "minecraft:mutated_mesa_clear_rock", "minecraft:savanna", "minecraft:savanna_rock",
                "minecraft:mutated_savanna", "minecraft:mutated_savanna_rock", "minecraft:mushroom_island", "minecraft:mushroom_island_shore",
                
                "biomesoplenty:bamboo_forest", "biomesoplenty:bayou", "biomesoplenty:brushland",
                "biomesoplenty:eucalyptus_forest", "biomesoplenty:lush_desert", "biomesoplenty:mangrove",
                "biomesoplenty:outback", "biomesoplenty:overgrown_cliffs", "biomesoplenty:rainforest",
                "biomesoplenty:sacred_springs", "biomesoplenty:tropical_rainforest", "biomesoplenty:wasteland",
                "biomesoplenty:xeric_shrubland", "biomesoplenty:flower_island", "biomesoplenty:tropical_island",
                "biomesoplenty:volcanic_island", "biomesoplenty:oasis", "biomesoplenty:white_beach",
                
        		"traverse:arid_highland", "traverse:badlands", "traverse:canyon", "traverse:desert_shrubland", "traverse:mini_jungle",
        		"traverse:mountainous_desert", "traverse:red_desert", "traverse:rocky_plateau",
        		"conquest:bamboo_forest", "conquest:desert_mod", "conquest:jungle_mod", "conquest:mesa_extreme_mod", "conquest:red_desert");

        for (String biomeName : tropicalBiomes)
        {
            if (!map.containsKey(biomeName))
                map.put(biomeName, new BiomeData(true, true));
            else
                map.get(biomeName).useTropicalSeasons = true;
        }
    }
}
