package thaumictheory.deathlink;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Spigot extends JavaPlugin
{
	public BukkitScheduler scheduler = getServer().getScheduler();
	@Override
	public void onEnable()
	{ 
		new EntityDamageEvent();
		new EntityDamageByBlockEvent();
		new EntityRegainHealthEvent();
		
		new PlayerJoinEvent();
		new PlayerQuitEvent();
		new PlayerGameModeChangeEvent();
		
		new FoodLevelChangeEvent();
		new PlayerItemConsumeEvent();
		
		new PlayerExpChangeEvent();
		new PlayerLevelChangeEvent();
		
		new EntityPotionEffectEvent();
		
		for(Player player : Common.getPlayingPlayers()) Common.updateHealthPlayerJoin(player);
	}

	public void onLoad()
	{
		Common.spigot = this;
	}
	
	public void onDisable()
	{
		
	}
}
