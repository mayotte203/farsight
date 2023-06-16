package farsight;

import farsight.compat.SodiumCompat;
import farsight.config.Configuration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarsightMod implements ModInitializer
{
    private static Configuration config;

    public static final String MODID  = "farsight";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize()
    {
        LOGGER.info(MODID + " mod initialized");

        if (FabricLoader.getInstance().isModLoaded("sodium"))
        {
            SodiumCompat.init();
        }
    }

    public static Configuration getConfig()
    {
        if (config == null)
        {
            config = new Configuration();
            config.load();
        }

        return config;
    }
}
