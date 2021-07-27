package net.kasmin.spawnplus;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.Map;

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
  private static final Boolean isDebug = false;
  private EntityPlayer player;

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
    EntityPlayer p = event.getHarvester();
    if (event.getState() == Blocks.MOB_SPAWNER.getDefaultState()) {

      ItemStack heldItemStack = p.getHeldItem(p.getActiveHand());
      Item heldItem = heldItemStack.getItem();
      Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(heldItemStack);
      Item dropItem = Item.getItemFromBlock(event.getState().getBlock());

      if (isDebug) debug("EnchantList:" + enchantList);
      enchantList.forEach((key, value) -> {
        if (key == Enchantments.SILK_TOUCH) {
          if (isDebug) debug("hasSilktouch:" + true);
          event.getDrops().clear();
          event.getDrops().add(new ItemStack(dropItem));
        }
      });

      if (isDebug) {
        debug("Drops:" + event.getDrops());
        debug("HarvestFrom:" + heldItem);
        debug("NBT:" + heldItem.getNBTShareTag(p.getHeldItem(p.getActiveHand())));
      }

    }
  }

  @SubscribeEvent
  public void onPlayerLoggedIn(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityPlayer) {
      this.player = (EntityPlayer) event.getEntity();
    }
  }

  /*
    Debug to in-game message and debug logs
   */
  private void debug(String msg) {
    LOGGER.debug(msg);
    this.player.sendMessage(new TextComponentString(msg));
  }
}
