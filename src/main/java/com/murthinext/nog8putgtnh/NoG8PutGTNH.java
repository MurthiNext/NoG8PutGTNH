package com.murthinext.nog8putgtnh;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.murthinext.nog8putgtnh.client.OffhandPlacementKeybind;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = NoG8PutGTNH.MODID,
    name = "NoG8PutGTNH",
    version = Tags.VERSION,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:backhand")
public class NoG8PutGTNH {

    public static final String MODID = "nog8putgtnh";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), MODID + ".cfg");
        OffhandPlacement.load(configFile);
        if (event.getSide()
            .isClient()) {
            OffhandPlacementKeybind.register();
        }
        LOG.info(
            "NoG8PutGTNH loaded, offhand block placement is {}",
            OffhandPlacement.isAllowed() ? "allowed" : "disabled");
    }
}
