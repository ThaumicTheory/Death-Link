package thaumictheory.deathlink;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener
{
	public PlayerQuitEvent()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) 
	{
		if(Common.playableGameMode(event.getPlayer().getGameMode())) 
		{
			Common.updateHealthPlayerLeave(event.getPlayer());
		}
	}
	
}
