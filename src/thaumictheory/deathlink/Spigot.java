package thaumictheory.deathlink;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Spigot extends JavaPlugin
{
	public BukkitScheduler scheduler = getServer().getScheduler();
	@Override
	public void onEnable()
	{ 
		new EntityDamageEvent();
		new EntityRegainHealthEvent();
		
		new PlayerJoinEvent();
		new PlayerQuitEvent();
		new PlayerGameModeChangeEvent();
		
		//new EntityExhaustionEvent();
		new FoodLevelChangeEvent();
	}

	public void onLoad()
	{
		Common.spigot = this;
	}
	
	public void onDisable()
	{
		
	}
}
