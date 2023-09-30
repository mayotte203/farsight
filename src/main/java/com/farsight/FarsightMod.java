package com.farsight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.common.Mod;
import java.util.Random;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import io.github.tox1cozz.mixinbooterlegacy.IEarlyMixinLoader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mod(modid = FarsightMod.MODID, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "*", version = "1.6")
public class FarsightMod implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final String MODID  = "farsight";
    public static final Logger LOGGER = LogManager.getLogger();

    public static Random rand = new Random();

    public FarsightMod()
    {
        LOGGER.info("Farsight initialized");
    }
    
    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("farsight.mixins.json");
    }
}
