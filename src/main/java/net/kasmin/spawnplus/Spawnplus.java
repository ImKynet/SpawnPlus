package net.kasmin.spawnplus;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = Spawnplus.MOD_ID,
    name = Spawnplus.MOD_NAME,
    version = Spawnplus.VERSION
)
public class Spawnplus {

  public static final String MOD_ID = "spawnplus";
  public static final String MOD_NAME = "SpawnPlus";
  public static final String VERSION = "1.0.0";
  /**
   * This is the instance of your mod as created by Forge. It will never be null.
   */
  @Mod.Instance(MOD_ID)
  public static Spawnplus INSTANCE;
  private static Logger LOGGER;

  /**
   * This is the first initialization event. Register tile entities here.
   * The registry events below will have fired prior to entry to this method.
   */
  @Mod.EventHandler
  public void preinit(FMLPreInitializationEvent event) {
    LOGGER = event.getModLog();
    MinecraftForge.EVENT_BUS.register(this);
  }

  /**
   * This is the second initialization event. Register custom recipes
   */
  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    LOGGER.debug("Initializing");
  }

  /**
   * This is the final initialization event. Register actions from other mods here
   */
  @Mod.EventHandler
  public void postinit(FMLPostInitializationEvent event) {

  }

  @SubscribeEvent
  public void dropSpawner(BlockEvent.HarvestDropsEvent event) {
    if (event.getState() == Blocks.MOB_SPAWNER.getDefaultState()) {
      LOGGER.debug("DropChance:" + event.getDropChance());
      LOGGER.debug("Drops:" + event.getDrops());
      //event.getHarvester().getHeldItemMainhand().getItem().;
    }
  }
}
